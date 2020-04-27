package org.xfh.mid.biz.stockService.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.AndFilter;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.mid.biz.stockService.IStockMakeCheckActivityValid;
import org.xfh.mid.db.po.BackLog;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.UserCheckActivity;
import org.xfh.mid.db.po.UserCheckProduct;
import org.xfh.mid.db.po.WarehouseStock;
import org.xfh.mid.enums.CheckActivityStatus;
import org.xfh.mid.enums.CheckResultCats;
import org.xfh.mid.vo.ChangeFieldDataVo;

@Component
public class StockMakeCheckActivityValidImpl extends AbstractStockService implements IStockMakeCheckActivityValid{

	private Logger logger = LoggerFactory.getLogger(StockMakeCheckActivityValidImpl.class);
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void makeCheckActivityValid(Long userId, Long activityId) throws Exception{

		/**
		 * 找出盘点活动的入库商品, 业务逻辑: 
		 * 
		 * 1) 计算所有损失类型, 原库存减少
		 * 2) 计算增加的类型, 原库存增加(小概率事件) 
			
		 */

		// 所有关联的检查记录
		List<UserCheckProduct> checkResultList = this.getCheckProducts(activityId);
		
		// key : product Id
		Map<Long, SumQuantity> cache = this.getResultMap(checkResultList);
		
		// 检查是否所有商品都输入了检查记录
		this.checkNoResultRecord(cache, activityId);

		// 合并商品id
		List<Long> pids = new ArrayList<>();
		for(UserCheckProduct prod : checkResultList) {
			if( ! pids.contains(prod.getProductId())) {
				pids.add(prod.getProductId());
			}
		}
		
		// 找出库存记录并逐条更新
		List<WarehouseStock> wsList = this.getWarehouseStockList(activityId, pids);
		
		// 逐个更新库存
		Date now = DateUtils.getChinaDate();
		for(WarehouseStock ws : wsList) {
			
			this.doUpdateEach(cache, ws, userId, now);
			
		}
		
		// 更改盘点状态
		this.updateStatus(activityId);
		
		// 同步销售库存
		this.syncSaleStock(pids);
		
		logger.info("==== 完成盘点, activity id: " + activityId);
	
	}
	

	@Transactional
	private void updateStatus(Long activityId) {
		UserCheckActivity data = new UserCheckActivity();
		data.setId(activityId);
		data.setDataStatus(CheckActivityStatus.VALID.getValue());
		daoHelper.updateById(UserCheckActivity.class, data);		
	}
	
	@Transactional
	private void doUpdateEach(Map<Long, SumQuantity> cache, WarehouseStock ws, Long userId, Date now) {
		
		for(Long pid : cache.keySet()) {
			if(pid.longValue() == ws.getProductId().longValue()) {
				
				SumQuantity qh = cache.get(pid);
				
				int originValue = ws.getStock();
				int toValue = qh.quantity + originValue;
				
				// 更新库存表
				WarehouseStock update = new WarehouseStock();
				update.setId(ws.getId());
				update.setStock(toValue);
				update.setUpdateUserId(userId);
				update.setUpdateTime(now);
				daoHelper.updateById(WarehouseStock.class, update);

//				logger.debug("=== 即将修改库存, ws.getId(): " + ws.getId() + " ,原值: " + originValue + ", 新值: " + toValue);
								
				backLogService.insertCriticalLog(BackLog.BIZ_PRODUCT_CHECK_ACTIVITY, DBs.TABLE_WAREHOUSE_STOCK, ws.getId(), userId, now
						, ChangeFieldDataVo.makeOne(DBs.STOCK, originValue, toValue));
				
			}
		}

	}

	@Transactional
	private List<WarehouseStock> getWarehouseStockList(Long activityId, List<Long> pids){
		
		// 盘点数据
		UserCheckActivity activity = daoHelper.getById(UserCheckActivity.class, activityId);

		// 查出库存数据
		AndFilter filter = new AndFilter(DBs.WAREHOUSE_ID, activity.getWarehouseId(), false);		
		filter.andInList(DBs.PRODUCT_ID, pids, false);
		
		return daoHelper.getListBySql(WarehouseStock.class, filter.ok());
	}

	@Transactional
	private List<UserCheckProduct> getCheckProducts(Long activityId){

		AndFilter filter = new AndFilter(DBs.ACTIVITY_ID, activityId, false);
		filter.andNotNull(DBs.PARENT_ID, false);
				
		return daoHelper.getListBySql(UserCheckProduct.class, filter.ok());
	}

	@Transactional
	private Map<Long, SumQuantity> getResultMap(List<UserCheckProduct> checkResultList){

		
		/**
		 * key : product Id
		 * value: 应该加或者减的数量 
		 */
		Map<Long, SumQuantity> cache = new HashMap<>();
		
		for(UserCheckProduct prod : checkResultList) {
			
			Long pid = prod.getProductId();
			
			SumQuantity qh = null;
			if(cache.containsKey(pid)) {
				qh = cache.get(pid);
			}
			else {
				qh = new SumQuantity(0, prod.getId());
			}
			
			// 检查记录类型
			int result = CheckResultCats.checkAddOrSubtract(prod.getCheckResultCat(), prod.getQuantity());

			// result 可能正也可能-
			qh.quantity += result;			
			cache.put(pid, qh);			
		}
		
		return cache;
	}
	
	@Transactional
	private void checkNoResultRecord(final Map<Long, SumQuantity> cache, Long activityId) throws LogicException {

		AndFilter filter = new AndFilter(DBs.ACTIVITY_ID, activityId, false);
		filter.andIsNull(DBs.PARENT_ID, false);
		int productCount = daoHelper.getCountBySql(UserCheckProduct.class, filter.ok());
		
		if(productCount != cache.keySet().size()) {
			throw new LogicException("有些商品还没有输入检查结果,无法提交! ");
		}
	}
	
	private class SumQuantity{
		public int quantity;
		public Long dataId;		// UserCheckProduct 主键id
		
		public SumQuantity(int q, long d) {
			this.quantity = q;
			this.dataId = d;
		}
	}

}
