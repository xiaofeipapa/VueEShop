package org.xfh.mid.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.BigDecimalUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DStringUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IBackProductService;
import org.xfh.mid.biz.service.IProductQueryService;
import org.xfh.mid.db.dao.ProductQueryDao;
import org.xfh.mid.db.dao.SpecQueryDao;
import org.xfh.mid.db.dao.StockDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.ModalSpecValue;
import org.xfh.mid.db.po.Product;
import org.xfh.mid.db.po.SupplierProduct;
import org.xfh.mid.enums.ProductStatus;
import org.xfh.mid.vo.ModalSpecGroupVo;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.form.ProductBatchSetSupplierPriceForm;
import org.xfh.mid.vo.form.ProductBatchUpdateForm;

@Component
public class BackProductServiceImpl extends AbstractSingleTableService<Product> 
	implements IBackProductService{
	
	private Logger logger = LoggerFactory.getLogger(BackProductServiceImpl.class);
	
	@Autowired
	ProductQueryDao productQueryDao;
	
	@Autowired
	SpecQueryDao specQueryDao;
	
	@Autowired
	IProductQueryService productQueryService;
	
	@Autowired
	StockDao stockDao;

	@Override
	protected void checkBeforeCreateOrUpdate(Product entity, boolean isCreate) throws LogicException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeAll(Long userId, Long modalId) throws Exception {
						
		/**
		 * 循环, 形成 instance 数据. 数据特征是一个 n * n 的表格. 
		 * 
		 * 例如颜色是红色, 蓝色, 尺寸是 10cm, 20cm, 那么会生成4条记录: 
		 * 
		 * 红色 10cm
		 * 红色 20cm
		 * 蓝色 10cm
		 * 蓝色 20cm 
		 * 
		 * 如果规格属性组有3个, 那就更复杂. 方法已经封装到 IProductLogic
		 */
		
		// 查找已有的实例				
		List<ProductVo> dbInstances = productQueryService.getExistProductListEqModalId(userId, modalId);
		
		Collection<ModalSpecGroupVo> specValueList = specQueryDao.getSpecGroupsWithChildren(modalId);

		List<List<Long>> toAddSpecValues = this.makeUniqueProductSpecValueStrings(specValueList);
		
		for(List<Long> uniqueValueComposit : toAddSpecValues) {
			
			boolean has = false;
			String toCheckStr = DStringUtils.joinLong(",", uniqueValueComposit);
			
			for(ProductVo existData : dbInstances) {
				
				if(Product.isEqSpecValueStrings(toCheckStr, existData.getSpecValueIdString())) {
					has = true;
				}				
			}
			
			// 已经重复, 不用再增加了. 
			if(has)continue;
			
			// 不重复, 插入主表和子表. 
			Product pi = new Product();
			pi.setModalId(modalId);
			pi.setCreateUserId(userId);
			pi.setCreateTime(new Date());
			Long productId = this.insertNoCheck(userId, pi, null);
			
			// 保存关联表
			for(Long valueId : uniqueValueComposit) {
				TreeMap<String, Object> dataMap = new TreeMap<>();
				dataMap.put(DBs.PRODUCT_ID, productId);
				dataMap.put(DBs.SPEC_VALUE_ID, valueId);
				
				commonDao.insertOne(DBs.TABLE_PRODUCT_SPEC_VALUE, dataMap);
			}

			// 保存记录
			logger.debug("==== 已保存商品数据.modalId: " + modalId + ", productId: " + productId + ", spec属性值字符串: " + toCheckStr);
						
		}
	}

//	@Override
//	public void batchUpdateField(Long userId,String idstr, String fieldName, String fieldValue) throws Exception {
//
//		VUtils.checkMandatory(idstr, "请选择记录");
//		VUtils.checkMandatory(fieldName, "请选择待更新的数据项");
//		VUtils.checkMandatory(fieldValue, "请输入数据项");
//		
//		TreeMap<String, Object> dataMap = new TreeMap<>();
//		
//		// 更新库存
//		if("stock".equalsIgnoreCase(fieldName)) {
//			VUtils.checkParseIntGtZero(fieldValue, "请输入有效的数字");		
//		}
//		else if ("marketPrice".equalsIgnoreCase(fieldName)) {
//			VUtils.checkParseBigDecimal(fieldValue, "请输入有效的市场价");
//		}
//		else if ("retailPrice".equalsIgnoreCase(fieldName)) {
//			VUtils.checkParseBigDecimal(fieldValue, "请输入有效的零售价");
//		}
//		else {
//			throw new LogicException("字段不合法: " + fieldName);
//		}
//		
//		dataMap.put(fieldName, fieldValue);
//		List<Long> idList = DSqlUtils.parseToIdList(idstr, ",");
//		
//		super.updateEntityInIds(dataMap, idList);
//	}


	@Override
	public void batchUpdateOneField(Long userId,String idstr, String fieldName, String fieldValue) throws Exception {

		VUtils.checkM(idstr, "请选择记录");
		
		TreeMap<String, Object> dataMap = new TreeMap<>();

		dataMap.put(fieldName, fieldValue);
		dataMap.put(DBs.UPDATE_USER_ID, userId);
		dataMap.put(DBs.UPDATE_TIME, new Date());
		List<Long> idList = DSqlUtils.parseToIdList(idstr, ",");
		
		super.updateInIds(dataMap, idList);
	}

	@Transactional
	@Override
	public void batchDelete(Long userId, String idstr) throws Exception {

		VUtils.checkM(idstr, "请选择记录");
		
		String sqlFilter = DSqlUtils.andFieldInSepStrings("id", idstr);
				
		super.deleteBySql(userId, sqlFilter);
		
		// 测试事务
//		Long.valueOf("aaa");
		
		// 删除关联的库存数据
		commonDao.deleteBySql(DBs.TABLE_WAREHOUSE_STOCK, sqlFilter);
		
		// 删除关联的属性值数据
		sqlFilter = DSqlUtils.andFieldInSepStrings(DBs.PRODUCT_ID, idstr);
		commonDao.deleteBySql(DBs.TABLE_PRODUCT_SPEC_VALUE, sqlFilter);
		
	}

//	@Override
//	public List<Long> batchSubmitToConfirm(Long userId, String idstr) throws Exception {
//
//		VUtils.checkMandatory(idstr, "请选择记录");
//		String sql = DSqlUtils.andInStrings("id", idstr);
//				
//		TreeMap<String, Object> dataMap = new TreeMap<>();
//		dataMap.put("dataStatus", ProductStatus.WAIT_CHECK.getValue());
//		super.updateEntityByFilter(entity, filter, inputAvoidFields);
//		
//		return ids;
//	}
	

	/**
	 * 用递归的方式, 形成商品属性值的多维组合. 例如商品模型的规格如下: 
	 * 
	 * 颜色:  红色(1001), 蓝色(1002)
	 * 长度:  5寸(2001), 10寸(2002)
	 * 
	 * 那么会形成 2 * 2 个规格属性值组合, 分别如下: 
	 * 
	 * 红色5寸: 1001,2001   红色10寸:  1001, 2002
	 * 蓝色5寸: 1002,2001   蓝色10寸: 1002,2002
	 * 
	 * 所以返回的数据形式是:
	 * 
	 * [
	 * 	[1001,2001], 
	 * 	[1001,2002], 
	 * 	[1002,2001], 
	 * 	[1002,2002], 
	 * 
	 * ]
	 * 
	 * 
	 * 
	 */
	@Override
	public List<List<Long>> makeUniqueProductSpecValueStrings(
			Collection<ModalSpecGroupVo> specValueList) {
		
		int totalCount = specValueList.size();
		List<List<Long>> resultList = new ArrayList<>();
		
		// 如果只有一个属性组就简单了. 
		if(totalCount == 1) {
			
			for(ModalSpecValue valueVo : specValueList.iterator().next().getChildren()) {
				List<Long> values = new ArrayList<>();
				values.add(valueVo.getId());
				resultList.add(values);
			}
			
			return resultList;
		}

		// 支持多属性值的n维组合就比较复杂了. 
		
		// 
		/**
		 * index 表示 specGroup的数量. 如果规格属性有n个, 那么index 就是n. 如果是1个, 那就是1个. (1个的情况最简单)
		 * 
		 * 这个map的结构是: 
		 * 
		 * {
		 * 		1: [1001,1002]
		 * 		2: [2001,2002]
		 * }
		 */
		int index = 1;
		TreeMap<Integer, List<Long>> holder = new TreeMap<>();
		
		for(ModalSpecGroupVo groupVo : specValueList) {
			
			List<Long> valueIds = new ArrayList<>();
			
			for(ModalSpecValue valueVo : groupVo.getChildren()) {
				valueIds.add(valueVo.getId());
			}
			
			holder.put(index, valueIds);
			
			index++;
			
		}

		List<Long> firstArr = holder.get(1);

		int startIndex = 2;
		for(Long startVal : firstArr) {
			
			List<Long> eachRow = new ArrayList<>();
			eachRow.add(startVal);
						
			this.joinNext(startIndex, holder, eachRow, resultList);
			
		}
		
		return resultList;
		
	}
	
	private void joinNext(int hoderIndex, TreeMap<Integer, List<Long>> holder, List<Long> previous, List<List<Long>> results) {
		
		for(Long y : holder.get(hoderIndex)) {
			
			List<Long> currentRow = new ArrayList<>(previous);
			
			currentRow.add(y);
			
//			String temp = prefix + "," + String.valueOf(String.valueOf(y));
			
			boolean hasNext = holder.get(hoderIndex+1) != null;
			
			if( hasNext ) {
				this.joinNext(hoderIndex + 1, holder, currentRow, results);
			}else {
				// 已经到达最后
				results.add(currentRow);
				logger.debug("-- 顺利获得规格属性组合的唯一值: " + DStringUtils.joinLong(",", currentRow));

			}
						
		}
		
	}

	@Override
	public void batchUpdatePrice(Long userId, ProductBatchUpdateForm form) throws Exception {

		VUtils.checkM(form.getIdstr(), "请选择记录");

		BigDecimal markePrice = VUtils.checkBigDecimalMustGtZero(form.getMarketPrice(), "市场价必须是大于0的数字");
		BigDecimal retailPrice = VUtils.checkBigDecimalMustGtZero(form.getRetailPrice(), "零售价必须是大于0的数字");
		
		if( BigDecimalUtils.isGt(retailPrice, markePrice)){
			throw new LogicException("零售价不可能大于市场价, 请修改");
		}
		
		TreeMap<String, Object> dataMap = new TreeMap<>();

		dataMap.put(DBs.MARKET_PRICE, markePrice);
		dataMap.put(DBs.RETAIL_PRICE, retailPrice);
		
		List<Long> idList = DSqlUtils.parseToIdList(form.getIdstr(), ",");
		
		super.updateInIds(dataMap, idList);
	}
	
	@Transactional
	@Override
	public void batchToggleSale(Long userId, String idstr, String status) throws Exception {

		VUtils.checkM(idstr, "请选择记录");
		
		if(ProductStatus.ON_SALE.getValue().equalsIgnoreCase(status)) {
			
			// 商品必须设置库存类型才能上架. 
			String sql = DSqlUtils.andFieldInSepStrings("id", idstr);			
			sql += DSqlUtils.andIsNull(DBs.STOCK_CAT, true);
			
			int count = commonDao.getCountBySql(DBs.TABLE_PRODUCT, sql);
			if(count > 0) {
				throw new LogicException("部分商品的库存类型还没有设置, 请先设置再执行上架操作. ");
			}
			
			// 真实的库存类型必须有库存才能上架
			int noStockCount = stockDao.checkNoStockProduct(idstr);
			if(noStockCount > 0) {
				throw new LogicException("库存类型为真实的商品必须有库存才能上架. ");				
			}
			

			// 商品必须设置市场价/零售价才能上架
			sql = DSqlUtils.andFieldInSepStrings("id", idstr);		
			sql += " and (" + DBs.MARKET_PRICE + " is null or " + DBs.RETAIL_PRICE + " is null)";
			count = commonDao.getCountBySql(DBs.TABLE_PRODUCT, sql);
			if(count > 0) {
				throw new LogicException("部分商品的市场价/零售价还没有设置, 请先设置再执行上架操作. ");
			}

			// 商品的模型必须有图片
			sql = DSqlUtils.andFieldInSepStrings("id", idstr);		
			sql += " and (" + DBs.MARKET_PRICE + " is null or " + DBs.RETAIL_PRICE + " is null)";
			count = commonDao.getCountBySql(DBs.TABLE_PRODUCT, sql);
			if(count > 0) {
				throw new LogicException("部分商品的市场价/零售价还没有设置, 请先设置再执行上架操作. ");
			}
			
		}
		
		String sql = DSqlUtils.andFieldInSepStrings("id", idstr);
		TreeMap<String, Object> param = new TreeMap<>();
		param.put(DBs.DATA_STATUS, status);
		commonDao.updateBySql(DBs.TABLE_PRODUCT, param, sql);
		
	}

	@Override
	public ProductVo getEditFormData(Long userId, Long productId) throws Exception {
		return productQueryDao.getEditFormById(productId);
	}

	@Override
	public void setNotSale(Long userId, Long productId) throws Exception {
		Product prod = new Product();
		prod.setDataStatus(ProductStatus.NOT_SALE.getValue());
		prod.setId(productId);
		this.updateById(prod, null);
	}

	@Override
	public void saveEach(Long userId, ProductVo vo) throws Exception {
	
		VUtils.checkBigDecimalMustGtZero(vo.getMarketPrice(), "请输入有效的市场价");
		VUtils.checkBigDecimalMustGtZero(vo.getRetailPrice(), "请输入有效的零售价");
		VUtils.checkMandatory(vo.getStockCat(), "请选择库存类型");
		
//		if(!justOwn) {
//			Long productId = vo.getId();
//			List<SupplierProduct> spList = new ArrayList<>();
//			for(SupplierProductVo spVo : vo.getSuppliers()) {
//				SupplierProduct sp = new SupplierProduct();
//				RefUtils.copyFieldsToObject(spVo, sp, null, null);
//				spList.add(sp);
//			}
//			this.addSupplierProductMapping(productId, spList);
//		}
				
		Product param = new Product();
		param.setId(vo.getId());
		param.setMarketPrice(vo.getMarketPrice());
		param.setRetailPrice(vo.getRetailPrice());
		param.setUpdateUserId(userId);
		param.setUpdateTime(DateUtils.getChinaDate());
		param.setSku(vo.getSku());
		
		this.updateById(param, null);
	}
	
	@Transactional
	@Override
	public void batchSetSupplierProduct(Long userId, ProductBatchSetSupplierPriceForm form) throws Exception {

		VUtils.checkM(form.getSpList(), "请选择供应商");

		for(SupplierProduct sp : form.getSpList()) {

			VUtils.checkBigDecimalMustGtZero(sp.getOnePrice(), "采购价必须是大于0的数字");
			
		}

		Long productId = form.getProductId();
		for(SupplierProduct sp : form.getSpList()) {
			
			String sql = DSqlUtils.andEqNumber(DBs.PRODUCT_ID, productId, true);
			
			// 删除已有数据
			commonDao.deleteBySql(DBs.TABLE_SUPPLIER_PRODUCT, sql);
			
			// 新建数据
			TreeMap<String, Object> param = new TreeMap<>();
			param.put(DBs.SUPPLIER_ID, sp.getSupplierId());
			param.put(DBs.PRODUCT_ID, productId);
			param.put(DBs.ONE_PRICE, sp.getOnePrice());
			commonDao.insertOne(DBs.TABLE_SUPPLIER_PRODUCT, param);
		}
		
		
	}
	
	@Override
	public void saveSku(Long userId, Long id, String sku) throws Exception {
		Product prod = new Product();
		prod.setId(id);
		prod.setSku(sku);
		daoHelper.updateById(Product.class, prod);
	}
	

//	@Override
//	public void deleteWithCheck(Long id) throws LogicException {
//		
//		Map dataMap = commonDao.getById(this.tableName, id);
//		String dataStatus = dataMap.get("dataStatus").toString();
//		
//		boolean isOnSale = ProductModal.DATA_STATUS_ON_SALE.equalsIgnoreCase(dataStatus);
//		if(isOnSale) {
//			throw new LogicException("无法删除已上架的模型, 请先设置为下架状态");
//		}
//		
//		// 不实际删除, 只是更新. 
//		TreeMap param = new TreeMap();
//		param.put("dataStatus", ProductModal.DATA_STATUS_DELETE);
//		commonDao.updateById(this.tableName, param, id);
//		
//	}

}
