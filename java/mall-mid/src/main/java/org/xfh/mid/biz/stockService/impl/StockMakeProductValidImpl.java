package org.xfh.mid.biz.stockService.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.mid.biz.stockService.IStockMakeProductValid;
import org.xfh.mid.db.po.BackLog;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.ProductInBatch;
import org.xfh.mid.db.po.ProductInItem;
import org.xfh.mid.db.po.WarehouseStock;
import org.xfh.mid.enums.InItemActions;
import org.xfh.mid.enums.ProductInStatus;
import org.xfh.mid.vo.ChangeFieldDataVo;

@Component
public class StockMakeProductValidImpl extends AbstractStockService implements IStockMakeProductValid{

	private Logger logger = LoggerFactory.getLogger(StockMakeProductValidImpl.class);
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void makeProductInBatchValid(Long userId, Long batchId) throws Exception{
		
		/**
		 * 找出批次下的入库商品
		 * 注意: 报损入库不统计库存. 
		 */
		
		ProductInBatch batch = daoHelper.getById(ProductInBatch.class, batchId);
		
		String sql = DSqlUtils.andEqNumber(DBs.BATCH_ID, batchId, false);
		sql += DSqlUtils.andEqString(DBs.ACTION, InItemActions.STOCK.getValue(), false);
		List<ProductInItem> itemList = daoHelper.getListBySql(ProductInItem.class, sql);
		
		Date now = DateUtils.getChinaDate();
		Long warehouseId =  batch.getWarehouseId();
		
		// 更新仓库库存表数据
		List<Long> pids = new ArrayList<>();
		for(ProductInItem item : itemList) {
			
			pids.add(item.getProductId());
			
			sql = DSqlUtils.andEqNumber(DBs.WAREHOUSE_ID, warehouseId, true);
			sql += DSqlUtils.andEqNumber(DBs.PRODUCT_ID, item.getProductId(), true);

			WarehouseStock exist = daoHelper.getOneBySql(WarehouseStock.class, sql);
			
			TreeMap<String, Object> stockMap = new TreeMap<>();
			stockMap.put(DBs.REMARK, "批次入库商品");

			int originValue = exist == null ? 0 : exist.getStock();
			int toValue = originValue + item.getQuantity();
			
			if(exist != null) {
				// 更新
				stockMap.put(DBs.STOCK, toValue);
				stockMap.put(DBs.UPDATE_TIME, now);
				stockMap.put(DBs.UPDATE_USER_ID, userId);				
				commonDao.updateBySql(DBs.TABLE_WAREHOUSE_STOCK, stockMap, sql);
			}
			else {
				stockMap.put(DBs.STOCK, item.getQuantity());
				stockMap.put(DBs.PRODUCT_ID, item.getProductId());
				stockMap.put(DBs.WAREHOUSE_ID, warehouseId);
				stockMap.put(DBs.UPDATE_TIME, now);
				stockMap.put(DBs.UPDATE_USER_ID, userId);				
				commonDao.insertOne(DBs.TABLE_WAREHOUSE_STOCK, stockMap);
			}
			
			// 同步销售库存
			super.syncSaleStock(pids);
			
			// 插入关键数据记录表
			Long dataId = exist == null ? null : exist.getId();

			backLogService.insertCriticalLog(BackLog.BIZ_PRODUCT_IN_ADD_STOCK, DBs.TABLE_WAREHOUSE_STOCK, dataId, userId, now
					, ChangeFieldDataVo.makeOne(DBs.STOCK, originValue, toValue));
			
		}
		
		// 修改batch 状态
		TreeMap<String, Object> data = new TreeMap<>();
		data.put(DBs.DATA_STATUS, ProductInStatus.VALID.getValue());
		commonDao.updateById(DBs.TABLE_PRODUCT_IN_BATCH, data, batchId);
		
		
	}

}
