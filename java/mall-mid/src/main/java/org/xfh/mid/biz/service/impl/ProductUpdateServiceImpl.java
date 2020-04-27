package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.dao.CommonDao;
import org.xfh.mid.biz.service.IProductUpdateService;


@Component
public class ProductUpdateServiceImpl implements IProductUpdateService{
	
	private Logger logger = LoggerFactory.getLogger(ProductUpdateServiceImpl.class);
	
	@Autowired
	CommonDao commonDao;

//	@Override
//	public void updateStatusWhenCheckProduct(Long userId, CheckProductUpdateStatusForm form) throws Exception {
//
//
//		VUtils.checkMandatory(form.getIdstr(), "请选择记录");
//		
//		boolean isConfirm = "confirm".equalsIgnoreCase(form.getOpt());
//		boolean isReturn = "return".equalsIgnoreCase(form.getOpt());
//		
//		if( ! isConfirm && ! isReturn) {
//			throw new LogicException("未知操作");
//		}
//		
//		if(isReturn) {
//			VUtils.checkMandatory(form.getRemark(), "请输入退回理由");			
//		}
//
//		String sql = DSqlUtils.andIdInStrings("id", form.getIdstr());
//
//		TreeMap<String, Object> dataMap = new TreeMap<>();
//		if( isConfirm ) {
//			dataMap.put(DBs.DATA_STATUS, ProductStatus.NOT_SALE.getValue());
//		}else {
//			dataMap.put(DBs.DATA_STATUS, ProductStatus.DRAFT.getValue());			
//		}
//		dataMap.put(DBs.CHECK_REMARK, form.getRemark());
//		
//		commonDao.updateBySql(DBs.TABLE_PRODUCT, dataMap, sql);
//		
//		
//	}

	/**
	 * 商品上架的设置库存类型, 设置上架, 设置下架, 退回等操作. 因为比较简单, 都放在一个方法里. 
	 */
//	@Override
//	public void updateWhenProductConfirm(Long userId, ProductModalIndexSearchForm form) throws Exception {
//
//		VUtils.checkMandatory(form.getIdstr(), "请选择记录");
//		
//		boolean isUpdateStockCat = "stockCat".equalsIgnoreCase(form.getOpt());
//		boolean isReturn = "return".equalsIgnoreCase(form.getOpt());
//		boolean isOnSale = "onSale".equalsIgnoreCase(form.getOpt());
//		boolean notSale = "notSale".equalsIgnoreCase(form.getOpt());
//		
//		if( isReturn ) {
//			VUtils.checkMandatory(form.getRemark(), "请输入理由");		
//		}else if (isUpdateStockCat) {
//			VUtils.checkMandatory(form.getStockCat(), "请选择库存类型");				
//		}
//
//		TreeMap<String, Object> dataMap = new TreeMap<>();
//		
//		if(isUpdateStockCat) {
//			dataMap.put(DBs.STOCK_CAT, form.getStockCat());
//		}
//		else if (notSale) {
//			dataMap.put(DBs.DATA_STATUS, ProductStatus.NOT_SALE.getValue());
//		}
//		else if (isReturn) {
//			dataMap.put(DBs.CONFIRM_REMARK, form.getRemark());
//			dataMap.put(DBs.DATA_STATUS, ProductStatus.DRAFT.getValue());
//		}
//		else if (isOnSale) {
//			dataMap.put(DBs.DATA_STATUS, ProductStatus.ON_SALE.getValue());
//		}
//
//		dataMap.put(DBs.CONFIRM_USER_ID, userId);
//		dataMap.put(DBs.CONFIRM_TIME, new Date());
//		
//		String sql = DSqlUtils.andIdInStrings("id", form.getIdstr());
//		commonDao.updateBySql(DBs.TABLE_PRODUCT, dataMap, sql);
//	}


}
