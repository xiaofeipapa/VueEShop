package org.xfh.mid.biz.orderService.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.AndFilter;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.orderService.IOrderMakeAllocInfoPackage;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.UserOrderItemAllocInfo;
import org.xfh.mid.db.po.UserOrderItemAllocInfoPackage;
import org.xfh.mid.enums.AllocCats;
import org.xfh.mid.enums.AllocInfoPackageStatus;
import org.xfh.mid.enums.UserOrderStatus;
import org.xfh.mid.vo.BizError;
import org.xfh.mid.vo.UserOrderItemAllocInfoPackageVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoVo;
import org.xfh.mid.vo.UserOrderItemVo;
import org.xfh.mid.vo.form.AllocBatchSaveForm;
import org.xfh.mid.vo.form.AllocEachData;
import org.xfh.mid.vo.form.TempAllocInfoPackageVo;

@Component
public class OrderMakeAllocInfoPackageImpl extends AbstractOrderHandler  implements IOrderMakeAllocInfoPackage{
	
	private Logger logger = LoggerFactory.getLogger(OrderMakeAllocInfoPackageImpl.class);

	@Override
	public void batchSaveAllocInfo(Long userId, AllocBatchSaveForm form) throws Exception {

		VUtils.checkCollection(form.getAddList(), "请至少输入一个调拨数据");
				
		UserOrderItemVo itemVo = userOrderQueryDao.getItemByItemId(form.getItemId());
		
		// 检查总数量
		int total = 0;
		for(AllocEachData alloc : form.getAddList()) {
			total += alloc.getQuantity();
		}
		
		if(total != itemVo.getQuantity()) {
			throw new LogicException("所需数量是"+itemVo.getQuantity() + ", 调拨总数是"+total + ", 两者不一致. ");
		}
		
		List<Long> toWareIds = new ArrayList<>();
		// 检查供应商的商品是否选择接收仓库
		for(AllocEachData each : form.getAddList()) {

			VUtils.checkM(each.getToWarehouseId(), "请选择供应商商品的接收仓库");
			if( ! toWareIds.contains(each.getToWarehouseId())) {
				toWareIds.add(each.getToWarehouseId());
			}
		}

		Date current = DateUtils.getChinaDate();
		// 检查是否已有出库单. 如果没有就生成
		for(Long wid : toWareIds) {
			
			UserOrderItemAllocInfoPackageVo packageVo = userOrderItemAllocInfoDao.getPackageByOrderAndWarehouse(
					itemVo.getOrderBizId(), wid);
			
			if(packageVo == null) {
				UserOrderItemAllocInfoPackage temp = this.makeTempPackage(userId, current, itemVo.getOrderBizId(), wid);
				logger.debug("== 已生成临时出库单, sn: " + temp.getSn());
			}
		}
		
		// 删除已有调拨数据
		String sql = DSqlUtils.andEqNumber(DBs.ITEM_ID, itemVo.getId(), false);
		commonDao.deleteBySql(DBs.TABLE_USER_ORDER_ITEM_ALLOC_INFO, sql);

		// 获取现存的调拨单
		AndFilter filter = new AndFilter("pn." + DBs.ORDER_BIZ_ID, itemVo.getOrderBizId());
		List<UserOrderItemAllocInfoPackageVo> packageList = userOrderItemAllocInfoDao.getPackageListOfOrder(filter.ok());

		for(UserOrderItemAllocInfoPackage pack : packageList) {
			
			long pwid = pack.getWarehouseId().longValue();

			// 此处我们假定仓库库存总是充足
			for(AllocEachData eachData : form.getAddList()) {
				
				if(eachData.getToWarehouseId().longValue() == pwid) {

					UserOrderItemAllocInfo alloc = new UserOrderItemAllocInfo();
					
					alloc.setItemId(form.getItemId());
					alloc.setProductId(itemVo.getProductId());
					alloc.setBackUserId(userId);
					alloc.setCreateTime(current);
					alloc.setPackageSn(pack.getSn());
					
					alloc.setAllocCat(eachData.getAllocCat());
					alloc.setFromId(eachData.getFromId());
					alloc.setQuantity(eachData.getQuantity());
					alloc.setToWarehouseId(eachData.getToWarehouseId());
					
					TreeMap<String,Object> param = RefUtils.copyFieldsToMap(alloc, false, null, null);
					commonDao.insertOne(DBs.TABLE_USER_ORDER_ITEM_ALLOC_INFO, param);
				}
				
			}
			
		}		

	}
	
	private UserOrderItemAllocInfoPackage makeTempPackage(Long userId, Date current, String orderBizId, Long warehouseId) {

		UserOrderItemAllocInfoPackage pn = new UserOrderItemAllocInfoPackage();
		pn.setSn("pn-" + System.currentTimeMillis());
		pn.setCreateUserId(userId);
		pn.setCreateTime(current);
		pn.setDataStatus(AllocInfoPackageStatus.TEMP.getValue());
		pn.setOrderBizId(orderBizId);
		pn.setWarehouseId(warehouseId);
		
		TreeMap<String, Object> dataMap = RefUtils.copyFieldsToMap(pn, false, null, null);
		commonDao.insertOne(DBs.TABLE_USER_ORDER_ITEM_ALLOC_INFO_PACKAGE, dataMap);
		return pn;
	}

	@Transactional
	@Override
	public TempAllocInfoPackageVo showAllocPackageInfo(Long userId, String bizId) throws Exception {
				
		// 返回出库单信息
		String sql = DSqlUtils.andEqString("pn." + DBs.ORDER_BIZ_ID, bizId, false);
		sql += DSqlUtils.andEqString("pn." + DBs.DATA_STATUS, AllocInfoPackageStatus.TEMP.getValue(), false);
		List<UserOrderItemAllocInfoPackageVo> noteList = userOrderItemAllocInfoDao.getPackageListOfOrder(sql);
		
		// 返回供应商采购信息
		List<UserOrderItemAllocInfoVo> suItemList = userOrderItemAllocInfoDao.getAllocInfoListOfOrder(bizId, AllocCats.SUPPLIER.getValue());
		
		TempAllocInfoPackageVo vo = new TempAllocInfoPackageVo();
		vo.setNoteList(noteList);
		vo.setSupplierOrders(suItemList);
		
		return vo;
	}
	
	

	@Transactional
	@Override
	public void confirmPackageData(Long backUserId, String bizId) throws Exception {
		
		// 返回出库单信息
		String sql = DSqlUtils.andEqString(DBs.ORDER_BIZ_ID, bizId, false);
		userOrderItemAllocInfoDao.updateNoteStatusBySql(sql, AllocInfoPackageStatus.VALID.getValue());
		
		// ====================== 更新订单状态
		super.updateOrderStatus(UserOrderStatus.INNER_PACKAGE.getValue(), bizId);
		
		logger.info("=== 出货单已顺利生成. 订单bizId: " + bizId);
	}

	@Override
	public List<BizError> checkPackageOK(Long backUserId, String bizId) throws Exception {
		
		List<UserOrderItemVo> itemList = userOrderQueryDao.getItemListByBizId(bizId);
		List<UserOrderItemAllocInfoVo> allocList = userOrderItemAllocInfoDao.getAllocInfoListOfOrder(bizId, null);
		
		List<BizError> errors = new ArrayList<>();
		// 逐个比对
		for(UserOrderItemVo itemVo : itemList) {
			int totalQuantity = itemVo.getQuantity();
			int check = this.sumTotal(itemVo.getId(), allocList);
			if(totalQuantity != check) {
				String msg = "所需数量是"+itemVo.getQuantity() + ", 已调拨数量是"+check + ", 两者不一致. ";
				BizError error = new BizError(itemVo.getId(), msg);
				errors.add(error);
			}
		}
		
		return errors;		
	}
	
	private int sumTotal(Long itemId, List<UserOrderItemAllocInfoVo> allocList) {

		int check = 0;
		for(UserOrderItemAllocInfoVo alloc : allocList) {
			if(alloc.getItemId().longValue() == itemId.longValue()) {
				check += alloc.getQuantity();
			}
		}
		return check;
		
	}

	

}
