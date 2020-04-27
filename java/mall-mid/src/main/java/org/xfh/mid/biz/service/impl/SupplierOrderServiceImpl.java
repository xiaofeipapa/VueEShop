package org.xfh.mid.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.BigDecimalUtils;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.excel.ColumnProp;
import org.xfh.mid.biz.service.ISupplierOrderExcelService;
import org.xfh.mid.biz.service.ISupplierOrderService;
import org.xfh.mid.db.dao.SupplierDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.SupplierOrder;
import org.xfh.mid.db.po.SupplierOrderItem;
import org.xfh.mid.enums.SupplierOrderStatus;
import org.xfh.mid.vo.BizError;
import org.xfh.mid.vo.ExcelExportVo;
import org.xfh.mid.vo.SupplierOrderItemVo;
import org.xfh.mid.vo.SupplierOrderVo;
import org.xfh.mid.vo.form.MakeSupplierOrderForm;

@Component
public class SupplierOrderServiceImpl implements ISupplierOrderService{

    static final Logger logger = LoggerFactory.getLogger(SupplierOrderServiceImpl.class);
    
    @Autowired
    SupplierDao supplierDao;
    
    @Autowired
    CommonDaoHelper daoHelper;
    
    @Value("${supplierOrderDailyRange}")
    String supplierOrderDailyRange;
    
    @Autowired
    ISupplierOrderExcelService excelService;

    @Transactional
	@Override
	public List<BizError> createPlanOrder(Long userId, MakeSupplierOrderForm form) throws Exception {
		
		List<SupplierOrderVo> supplierProducts = form.getSupplierProducts();
		if(supplierProducts.size() < 1) {
			throw new LogicException("没有商品居然能提交? 服务器拒绝! ");
		}
		
		List<BizError> errors = this.checkAndParseInput(supplierProducts);
		if(errors.size() > 0)return errors;
		
		Date now = DateUtils.getChinaDate();
		
		// 保存订单数据
		this.saveOrders(userId, supplierProducts, now);
		
		// 保存订单项数据
		this.saveItems(userId, supplierProducts, now);
		
		return null;
		
	}

    @Transactional
	private void saveItems(Long userId,List<SupplierOrderVo> formList, Date now) {

		for(SupplierOrderVo form : formList) {

			for(SupplierOrderItemVo itemVo : form.getProducts()) {

				// 计算总商品费用
				BigDecimal totalProduct = itemVo.getSupplierPrice().multiply(new BigDecimal(itemVo.getQuantity()));
				
				SupplierOrderItem item = new SupplierOrderItem();
				
				RefUtils.copyFieldsToObject(itemVo, item);
				
				item.setId(null);
				item.setOrderId(form.getId());
				item.setCreateUserId(userId);
				item.setCreateTime(now);
				item.setTotalProductFee(totalProduct);
								
				daoHelper.insertOne(SupplierOrderItem.class, item);
				
			}
		}
				
	}

    @Transactional
	private void saveOrders(Long userId,List<SupplierOrderVo> formList, Date now) {

		for(SupplierOrderVo form : formList) {
						
			SupplierOrder order = new SupplierOrder();
			order.setCreateTime(now);
			order.setCreateUserId(userId);
			order.setDataStatus(SupplierOrderStatus.Temp.getValue());
			order.setSupplierId(form.getSupplierId());
			order.setWarehouseId(form.getWarehouseId());
			
			// 计算总商品费用
			BigDecimal totalShouldPaid = new BigDecimal(0);
			for(SupplierOrderItemVo itemVo : form.getProducts()) {
				
				BigDecimal totalProduct = itemVo.getSupplierPrice().multiply(new BigDecimal(itemVo.getQuantity()));
				
				totalShouldPaid = totalShouldPaid.add(totalProduct);
				
			}
			order.setExportExcel(false);
			
			order.setTotalShouldPaid(totalShouldPaid);
			
			Long id = daoHelper.insertOne(SupplierOrder.class, order);
			
			// 插入到form, 方便操作item
			form.setId(id);
			
		}
		
		
	}
    
    // 检查数量和仓库这两个输入项是否正确
	private List<BizError> checkAndParseInput(List<SupplierOrderVo> formList) throws LogicException {
		
		List<BizError> errors = new ArrayList<>();
				
		for(SupplierOrderVo form : formList) {
			
			boolean hasError = false;
			for(SupplierOrderItemVo itemVo : form.getProducts()) {
				
				if( ! CheckUtils.isIntGtEqZero(itemVo.getQuantity())) {
					hasError = true;
					break;
				}
			}
			
			if(hasError) {
				
				BizError error = new BizError();
				error.setId(form.getSupplierId());
				error.setMsg("部分商品的购买数量没有填写,请修改");
				errors.add(error);
			}
			
		}
		
		return errors;
		
	}

	@Override
	public PageHolder<SupplierOrderVo> getSupplierOrderPage(Long userId, IndexSearchForm form) throws Exception {

		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		 filter.setLikeKeys(new String[]{"name"});
		 
		 String moreSql = "";
		 if( CheckUtils.isNotEmpty(form.getSearchStatus())){
			 moreSql += DSqlUtils.andEqString("a." + DBs.DATA_STATUS, form.getSearchStatus(), false);
		 }		 
		 
		 boolean hasStartDate = CheckUtils.isNotEmpty(form.getStartDate());
		 boolean hasEndDate = CheckUtils.isNotEmpty(form.getEndDate());
		 if( hasStartDate || hasEndDate ) {
			 moreSql += DSqlUtils.andDateBetween("a.createTime", form.getStartDate(), form.getEndDate(), null);
		 }
		
		filter.setMoreSql(moreSql);

		int totalCount = supplierDao.getSupplierOrderCount(filter);
		List<SupplierOrderVo> dataList = supplierDao.getSupplierOrderPageList(filter);
		
		return daoHelper.makePage(SupplierOrderVo.class, totalCount, dataList, filter);
		
	}

	@Override
	public SupplierOrderVo getOrderForEdit(Long userId, Long orderId) throws Exception {
		return supplierDao.getOrderForEdit(orderId);
	}

    @Transactional
	@Override
	public void updateSupplierOrder(Long userId, SupplierOrderVo form) throws Exception {
		
		boolean isTemp = SupplierOrderStatus.Temp.getValue().equalsIgnoreCase(form.getDataStatus());
		if( ! isTemp) {
			throw new LogicException("此订单数据不是草稿状态, 不能修改");
		}
		
		// 检查仓库
		VUtils.checkM(form.getWarehouseId(), "请选择仓库");

		for(SupplierOrderItemVo itemVo : form.getProducts()) {
			
			if( ! CheckUtils.isIntGtEqZero(itemVo.getQuantity())) {
				throw new LogicException("部分商品的购买数量没有填写,请修改");
			}
		}
		// 删除所有子订单
		String sql = DSqlUtils.andEqNumber(DBs.ORDER_ID, form.getId(), false);
		daoHelper.deleteBySql(SupplierOrderItem.class, sql);
		
		// 重新插入数据
		Date now = DateUtils.getChinaDate();
		// 计算总商品费用
		BigDecimal totalShouldPaid = new BigDecimal(0);
		for(SupplierOrderItemVo itemVo : form.getProducts()) {
			
			BigDecimal totalProduct = itemVo.getSupplierPrice().multiply(new BigDecimal(itemVo.getQuantity()));
			
			totalShouldPaid = totalShouldPaid.add(totalProduct);
			
			SupplierOrderItem item = new SupplierOrderItem();
			RefUtils.copyFieldsToObject(itemVo, item);
			item.setId(null);			
			item.setCreateTime(now);
			item.setCreateUserId(userId);
			
			daoHelper.insertOne(SupplierOrderItem.class, item);
			
		}
		
		SupplierOrder order = new SupplierOrder();
		order.setId(form.getId());		
		order.setTotalShouldPaid(totalShouldPaid);
		order.setWarehouseId(form.getWarehouseId());
		
		daoHelper.updateById(SupplierOrder.class, order);
				
	}

    @Transactional
	@Override
	public void confirmAll(Long userId, String ids) {
		

		String sql = DSqlUtils.andFieldInSepStrings(DBs.ID, ids);
		
		sql += DSqlUtils.andEqString(DBs.DATA_STATUS, SupplierOrderStatus.Temp.getValue(), false);

		SupplierOrder order = new SupplierOrder();
		order.setDataStatus(SupplierOrderStatus.Topaid.getValue());
		
		daoHelper.updateBySql(SupplierOrder.class, order, sql);
				
	}

    @Transactional
	@Override
	public void deleteAll(Long userId, String ids) throws Exception {
		
		String sql = DSqlUtils.andFieldInSepStrings(DBs.ID, ids);		
		sql += DSqlUtils.andNe(DBs.DATA_STATUS, SupplierOrderStatus.Temp.getValue(), false);
		
		// 只有临时数据才可以删除
		int count = daoHelper.getCountBySql(SupplierOrder.class, sql);
		if(count > 0) {
			throw new LogicException("删除失败! 部分数据并不是草稿状态");
		}
		
		sql = DSqlUtils.andFieldInSepStrings(DBs.ID, ids);		
		sql += DSqlUtils.andEqString(DBs.DATA_STATUS, SupplierOrderStatus.Temp.getValue(), false);
		
		daoHelper.deleteBySql(SupplierOrder.class, sql);
		
		// 删除订单子项
		sql = DSqlUtils.andFieldInSepStrings(DBs.ORDER_ID, ids);
		daoHelper.deleteBySql(SupplierOrderItem.class, sql);
	}

	@Override
	public ExcelExportVo exportIt(Long userId, Long orderId) throws Exception {
		
		// 查找关联的数据
		List<SupplierOrderItemVo> itemList = supplierDao.getOrderItemList(orderId);
		
		List<Map<String, String>> dataList = new ArrayList<>();
		String name = null;
		for(SupplierOrderItemVo vo : itemList) {
			Map<String, String> data = new HashMap<>();
			
			data.put(DBs.ORDER_ID, String.valueOf(vo.getOrderId()));
			data.put(DBs.MODAL_TITLE, vo.getModalTitle());
			data.put(DBs.SPEC_VALUE_STRING, vo.getSpecValueString());
			data.put(DBs.SUPPLIER_PRICE, BigDecimalUtils.toFixed2(vo.getSupplierPrice()));
			data.put(DBs.QUANTITY, String.valueOf(vo.getQuantity()));
			
			data.put(DBs.TOTAL_PRODUCT_FEE, BigDecimalUtils.toFixed2(vo.getTotalProductFee()));
			data.put(DBs.CREATE_TIME, DateUtils.format(vo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			
			dataList.add(data);
			
			if(name == null) {
				name = vo.getSupplierName();
			}
		}
		
		// 标题头的位置要与数据的增加顺序一致
		List<ColumnProp> columnList = new ArrayList<>();
		columnList.add(new ColumnProp("订单id", DBs.ORDER_ID));
		columnList.add(new ColumnProp("商品名称", DBs.MODAL_TITLE));
		columnList.add(new ColumnProp("规格属性", DBs.SPEC_VALUE_STRING));
		columnList.add(new ColumnProp("采购单价", DBs.SUPPLIER_PRICE));
		columnList.add(new ColumnProp("采购数量", DBs.QUANTITY));

		columnList.add(new ColumnProp("合计价格", DBs.TOTAL_PRODUCT_FEE));
		columnList.add(new ColumnProp("订单时间", DBs.CREATE_TIME));
		
		// 生成excel 
		SXSSFWorkbook book = excelService.export(name, dataList, columnList);
		
		ExcelExportVo vo = new ExcelExportVo(name, book);
		
		return vo;		
	}

	@Transactional
	@Override
	public void confirmPaid(Long userId, Long orderId, String imageUrl) throws Exception {
		
		SupplierOrder order = new SupplierOrder();
		
		order.setId(orderId);
		order.setPaidImageUrl(imageUrl);
		order.setDataStatus(SupplierOrderStatus.Paid.getValue());
		order.setPaidTime(DateUtils.getChinaDate());
		order.setPaidUserId(userId);
		
		daoHelper.updateById(SupplierOrder.class, order);
		
	}

	@Override
	public void confirmReceive(Long userId, Long orderId) throws Exception {
		SupplierOrder order = new SupplierOrder();
		
		order.setId(orderId);
		order.setDataStatus(SupplierOrderStatus.Receive.getValue());
		order.setReceiveTime(DateUtils.getChinaDate());
		order.setReceiveUserId(userId);
		
		daoHelper.updateById(SupplierOrder.class, order);
		
	}
    
    
    

//    @Transactional
//	@Override
//	public void createDailySupplierOrderFinanceRecord() {
//		
//    	String dateFormat = "YYYY-MM-dd";
//    	String mysqlFormat = "%Y-%M-%d %H%I";
//
//		// 统计 [前一天10:01,今天10:00]
//		Date now = DateUtils.getChinaDate();
//		Date previous = DateUtils.getPreviousDate(now);
//
//		String startDate = DateUtils.format(previous, dateFormat) + " " + supplierOrderDailyRange.split(",")[0];
//		String endDate = DateUtils.format(now, dateFormat) + " " + supplierOrderDailyRange.split(",")[1];
//
//		String dateSql = DSqlUtils.andDateBetween(DBs.CREATE_TIME, startDate, endDate, mysqlFormat);
//		
//		// 分两次查找,  查找计划订单里的正式数据,  查找即时订单
//
//		
//		
//	}
//	
}
