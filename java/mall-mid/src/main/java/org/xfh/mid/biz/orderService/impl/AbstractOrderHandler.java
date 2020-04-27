package org.xfh.mid.biz.orderService.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.dao.CommonDao;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.BigDecimalUtils;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.mid.biz.helper.IOrderHelper;
import org.xfh.mid.biz.helper.IPackageNoteHelper;
import org.xfh.mid.biz.service.IOrderQueryService;
import org.xfh.mid.db.dao.ClientBuyerDao;
import org.xfh.mid.db.dao.SupplierDao;
import org.xfh.mid.db.dao.UserOrderItemAllocInfoDao;
import org.xfh.mid.db.dao.UserOrderQueryDao;
import org.xfh.mid.db.po.ClientCreditItem;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.PaymentLog;
import org.xfh.mid.db.po.UserOrderItemAllocInfoPackage;
import org.xfh.mid.enums.AllocInfoPackageStatus;
import org.xfh.mid.enums.CreditItemCats;
import org.xfh.mid.enums.PayCats;
import org.xfh.mid.vo.ClientBuyerVo;
import org.xfh.mid.vo.UserOrderVo;

public abstract class AbstractOrderHandler {
	
	@Autowired
	IOrderHelper orderHelper;
	
	@Autowired
	IOrderQueryService orderQueryService;
	
	@Autowired
	CommonDao commonDao;
	
	@Autowired
	UserOrderItemAllocInfoDao userOrderItemAllocInfoDao;
	
	@Autowired
	IPackageNoteHelper packageNoteHelper;

	@Autowired
	SupplierDao supplierDao;

	@Autowired
	UserOrderQueryDao userOrderQueryDao;

	@Autowired
	ClientBuyerDao creditDao;
	
	@Autowired
	protected CommonDaoHelper daoHelper;
			
	// 插入信用记录表数据
	@Transactional
	protected ClientCreditItem insertCreditItem(UserOrderVo orderVo, CreditItemCats itemCat, Date current, Long backUserId) {

		ClientCreditItem item = new ClientCreditItem();
		item.setClientId(orderVo.getBuyUserId());
		item.setOrderBizId(orderVo.getBizId());
		item.setItemCat(itemCat.getValue());
		item.setFee(orderVo.getTotalShouldPaid());
		
		if( ! CheckUtils.isEmpty(backUserId)) {
			item.setBackUserId(backUserId);
		}
		
		String sn = "oic-" + System.currentTimeMillis();
		item.setSn(sn);
		item.setRepayFlag("N");
		item.setCreateTime(current);
		TreeMap<String, Object> param = RefUtils.copyFieldsToMap(item, false, null, null);
		Long id = commonDao.insertOne(DBs.TABLE_CLIENT_CREDIT_ITEM, param);
		item.setId(id);
		
		return item;
	}
	
	// 更新订单状态
	@Transactional
	protected void updateOrderStatus(String newStatus, String bizId) {

		TreeMap<String, Object> param = new TreeMap<>();
		String sqlFilter = DSqlUtils.andEqString(DBs.BIZ_ID, bizId, true);
		param.put(DBs.DATA_STATUS, newStatus);
		commonDao.updateBySql(DBs.TABLE_USER_ORDER, param, sqlFilter);
	}
	
	// 插入付款表
	@Transactional
	protected PaymentLog insertPaymentLog(UserOrderVo orderVo, PayCats payCat, Date current, Long backUserId, String remark, String imageUrls) {

		PaymentLog plog = new PaymentLog();
		plog.setOrderBizId(orderVo.getBizId());
		plog.setPayTime(current);
		plog.setPayCat(payCat.getValue());
		
		if(backUserId != null) {
			plog.setBackUserId(backUserId);
		}

		plog.setFee(orderVo.getTotalShouldPaid());
		plog.setBuyUserId(orderVo.getBuyUserId());
		plog.setBuyUserCat(orderVo.getBuyUserCat());
		plog.setSuccFlag("Y");
		plog.setRemark(remark);
		plog.setImageUrls(imageUrls);
		TreeMap<String, Object> param = RefUtils.copyFieldsToMap(plog, false, null, null);
		Long id = commonDao.insertOne(DBs.TABLE_PAYMENT_LOG, param);
		plog.setId(id);
		
		return plog;
	}
	
	
	// 检查客户的余额是否超支
	@Transactional
	protected ClientBuyerVo checkRemainCreditAmount(Long clientId) throws LogicException{

		ClientBuyerVo client = creditDao.getClientFullDetail(clientId);
		
		BigDecimal useAmount = new BigDecimal(0);
		if(client.getUseCreditAmount() != null) {
			useAmount = client.getUseCreditAmount();
		}

		// 检查超支
		BigDecimal creditAmount = client.getCreditAmount();
		BigDecimal remain = creditAmount.subtract(useAmount);
		if(remain.compareTo(BigDecimal.ZERO) <= 0) {
			// 超支
			String msg = "信用超支了, 允许信用金额: " + BigDecimalUtils.toFixed2(creditAmount) 
			+ " , 已用信用金额: " + BigDecimalUtils.toFixed2(useAmount);
			
			throw new LogicException(msg);
		}
		
		return client;
	}
	
	/**
	 * 根据出库单找到订单下, 检查订单下的所有快递单是否具备某个状态
	 */
	@Transactional
	protected boolean allPackageMatch(Long packageId, AllocInfoPackageStatus checkStatus ) {

		// 检查该订单有多少个出库单
		UserOrderItemAllocInfoPackage vo = daoHelper.getById(UserOrderItemAllocInfoPackage.class, packageId);
		String sql = DSqlUtils.andEqString(DBs.ORDER_BIZ_ID, vo.getOrderBizId(), false);
		List<UserOrderItemAllocInfoPackage> voList = daoHelper.getListBySql(UserOrderItemAllocInfoPackage.class, sql);
		
		boolean allReceive = true;
		if(voList.size() == 1) {
			allReceive = true;
		}
		else {
			for(UserOrderItemAllocInfoPackage temp : voList) {
				
				// 有一条没处理完
				if( ! checkStatus.getValue().equalsIgnoreCase(temp.getDataStatus())) {
					allReceive = false;
					break;
				}
			}
		}
		
		return allReceive;
	}

}
