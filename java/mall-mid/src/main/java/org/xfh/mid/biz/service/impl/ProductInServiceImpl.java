package org.xfh.mid.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.service.IProductInService;
import org.xfh.mid.db.dao.ProductInQueryDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.ProductInBatch;
import org.xfh.mid.db.po.ProductInItem;
import org.xfh.mid.enums.InItemActions;
import org.xfh.mid.enums.LogisticsCats;
import org.xfh.mid.enums.ProductDamageCats;
import org.xfh.mid.enums.ProductInStatus;
import org.xfh.mid.vo.ProductInBatchDetailView;
import org.xfh.mid.vo.ProductInBatchVo;
import org.xfh.mid.vo.ProductInItemVo;
import org.xfh.mid.vo.form.ProductInAddDamageForm;
import org.xfh.mid.vo.form.ProductInAddStockForm;

/**
 * 商品入库表
 * @author cys
 *
 */
@Component
public class ProductInServiceImpl extends AbstractSingleTableService<ProductInBatch> 
implements IProductInService {

	private Logger logger = LoggerFactory.getLogger(ProductInServiceImpl.class);
	
	@Autowired
	ProductInQueryDao productInQueryDao;
	
	@Autowired
	ProductInQueryDao productInItemQueryDao;                              

	@Override
	protected void checkBeforeCreateOrUpdate(ProductInBatch form, boolean isCreate) throws LogicException {

		VUtils.checkM(form.getWarehouseId(), "请选择仓库");
		VUtils.checkM(form.getLogisticsCat(), "请选择物流类型");
				
		if(LogisticsCats.OTHER.getValue().equalsIgnoreCase(form.getLogisticsCat())) {
			// 外部物流
			VUtils.checkMandMax(form.getShipNo(), "物流单号", 40);
			
			// TODO 外部物流应该可以通过订单号获得发件人信息 			
		}
		else {
			VUtils.checkM(form.getCarId(), "请选择车牌号");		
			VUtils.checkM(form.getCarDriverId(), "请选择司机");		
			VUtils.checkMax(form.getShipNo(), 40, "发车单号");
		}

		VUtils.checkMandMax(form.getSendUser(), "发件人", 40);
		VUtils.checkMobile(form.getSendUserPhone(), "发件电话", true);		
		VUtils.checkM(form.getSendUserDistrict(), "请选择发件省市");	
		VUtils.checkMandMax(form.getSendUserAddress(), "发件地址", 40);

		VUtils.checkM(form.getImageUrls(), "请上传物流单据");
		VUtils.checkMandMax(form.getRemark(), "备注", 1000);

		if(isCreate) {
			form.setCreateTime(DateUtils.getChinaDate());
			form.setCreateUserId(form.getCreateUserId());
			form.setDataStatus(ProductInStatus.TEMP.getValue());
		}
	}

	@Override
	public PageHolder<ProductInBatchVo> getProductInIndexPage(Long userId, IndexSearchForm form)
			throws Exception {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));


		int totalCount = super.getCountByFilter(filter);
		List<ProductInBatchVo> dataList = productInQueryDao.getProductInIndexPageList(filter);
		
		PageHolder<ProductInBatchVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());

		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
				
		return page;

	}

	@Override
	public ProductInBatchDetailView getBatchDetailWithItems(Long userId, Long batchId) throws Exception {
		
		ProductInBatchVo entity = productInQueryDao.getBatchDetailById(batchId);
		
		ProductInBatchDetailView view = new ProductInBatchDetailView();
		RefUtils.copyFieldsToObject(entity, view, null, null);

		
//		logger.debug("=== entity.imageUrls: " + entity.getImageUrls());		
//		logger.debug("=== view.imageUrls: " + view.getImageUrls());
		
		List<ProductInItemVo> itemList = productInItemQueryDao.getInItemListByBatchId(batchId);
		for(ProductInItemVo item : itemList) {
			if(InItemActions.STOCK.getValue().equalsIgnoreCase(item.getAction())){
				view.addProduct(item);
			}else {
				view.addDamage(item);
			}
		}
		
		if(view.getCreateTime() != null) {
			view.setCreateTimeStr(DateUtils.format(view.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
		}
		
		return view;		
	}
	
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void batchAddInItems(Long userId, ProductInAddStockForm form) throws Exception {
		
		Date now = DateUtils.getChinaDate();
		String msg1 = "请录入商品: %s %s 的库存数据"; 
		String msg2 = "商品: %s %s 的库存数据应该是大于0的整数"; 
		List<ProductInItem> itemList = new ArrayList<>();
		for(ProductInItem product : form.getProducts()) {

			VUtils.checkM(product.getQuantity(), String.format(msg1, product.getModalTitle(), product.getSpecValueString()));	
			
			Integer addInt = VUtils.checkParseIntGtZero(product.getQuantity(), String.format(msg2, product.getModalTitle(), product.getSpecValueString()));
		
			ProductInItem item = new ProductInItem();
			
			item.setProductId(product.getProductId());
			item.setQuantity(addInt);
			item.setModalTitle(product.getModalTitle());
			item.setSpecValueString(product.getSpecValueString());
			item.setWarehouseId(form.getWarehouseId());
			item.setAction(InItemActions.STOCK.getValue());
			
			item.setCreateTime(now);
			item.setCreateUserId(userId);
			
			itemList.add(item);
			
		}

		for(ProductInItem item : itemList) {
			item.setBatchId(form.getId());
						
			// 如果同一商品和批次已经存在, 更新. 否则插入.
			ProductInItem exist = this.getExistItem(form.getId(), item.getProductId(), InItemActions.STOCK);
			
			TreeMap<String, Object> dataMap;
			if(exist != null) {
				// 更新
				dataMap = new TreeMap<>();
				dataMap.put(DBs.QUANTITY, item.getQuantity());
				commonDao.updateById(DBs.TABLE_PRODUCT_IN_ITEM, dataMap, exist.getId());
				
			}
			else {
				// 插入入库细项
				dataMap = RefUtils.copyFieldsToMap(item, false, null, null);			
				commonDao.insertOne(DBs.TABLE_PRODUCT_IN_ITEM, dataMap);		
			}
							
		}
		
	}
	
	private ProductInItem getExistItem(Long batchId, Long productId, InItemActions action) {

		String sql = DSqlUtils.andEqNumber(DBs.BATCH_ID, batchId, false);
		sql += DSqlUtils.andEqNumber(DBs.PRODUCT_ID, productId, false);
		sql += DSqlUtils.andEqString(DBs.ACTION, action.getValue(), true);
		return daoHelper.getOneBySql(ProductInItem.class, sql);
	}

	@Override
	public PageHolder<ProductInItemVo> getBatchInItemIndexPage(Long userId, IndexSearchForm form) throws Exception {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		int totalCount = productInItemQueryDao.getInItemCount(filter);
		List<ProductInItemVo> dataList = productInItemQueryDao.getInItemPageList(filter);
		
		PageHolder<ProductInItemVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());

		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
				
		return page;
	}

	@Override
	public PageHolder<ProductInItemVo> getCheckProductInItemIndexPage(Long userId, IndexSearchForm form) throws Exception {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		filter.setMoreSql("and a.batchId is null");

		int totalCount = productInItemQueryDao.getInItemCount(filter);
		List<ProductInItemVo> dataList = productInItemQueryDao.getInItemPageList(filter);
		
		PageHolder<ProductInItemVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());

		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
				
		return page;
	}

	@Override
	public ProductInItemVo getInItemById(Long userId, Long id) throws Exception {

		IndexSearchFilter filter = new IndexSearchFilter("", 
				new PageInfo(1, 10));

		String moreSql = "and a.batchId is null";
		moreSql += DSqlUtils.andEqNumber("a.id", id, false);
		filter.setMoreSql(moreSql);

		List<ProductInItemVo> dataList = productInItemQueryDao.getInItemPageList(filter);
				
		ProductInItemVo vo = dataList.get(0);
		if(vo.getDamageCat() != null) {
			vo.setDamageCatLabel(ProductDamageCats.getFromValue(vo.getDamageCat()).getLabel());
		}
		return vo;
	}



	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public Long addDamageOfBatch(Long userId, ProductInAddDamageForm form) throws Exception {
		
		/**
		 * 需求解释: 
		 * 
		 * 这里是"入库管理/增加报损"功能, 也就是说, 入库的商品原来在仓库并不存在, 所以不需要扣减原有仓库库存. 
		 * 
		 */

		VUtils.checkM(form.getProductId(), "请选择商品");
		VUtils.checkParseIntGtZero(form.getQuantity(), "报损数据应该是大于0的整数");
		VUtils.checkM(form.getDamageCat(), "请选择报损类型");
		VUtils.checkM(form.getImageUrls(), "请至少上传一张证明图片");
		VUtils.checkM(form.getRemark(), "请输入备注");
		

		// 如果同一商品和批次已经存在, 更新. 否则插入.
		// 如果同一商品和批次已经存在, 更新. 否则插入.
		ProductInItem exist = this.getExistItem(form.getBatchId(), form.getProductId(), InItemActions.DAMAGE);
		if(exist == null) {
			form.setAction(InItemActions.DAMAGE.getValue());
			form.setCreateTime(DateUtils.getChinaDate());
			form.setCreateUserId(userId);
			return daoHelper.insertOne(ProductInItem.class, form);
		}
		TreeMap<String, Object> dataMap = new TreeMap<>();

		dataMap.put(DBs.QUANTITY, form.getQuantity());
		commonDao.updateById(DBs.TABLE_PRODUCT_IN_ITEM, dataMap, exist.getId());
				
		return exist.getId();
	}

	@Override
	public List<ProductInItemVo> deleteDamage(Long userId, Long id) throws Exception {
		
		ProductInItem data = daoHelper.getById(ProductInItem.class, id);
		
		Long batchId = data.getBatchId();
		daoHelper.deleteById(ProductInItem.class, id);
		
		ProductInBatchDetailView view = this.getBatchDetailWithItems(userId, batchId);
		
		return view.getDamageProducts();
	}

	@Override
	public void deleteProduct(Long userId, Long batchId, Long productId) throws Exception {
		
		ProductInItem data = this.getExistItem(batchId, productId, InItemActions.STOCK);
		if(data == null)return;
		
		daoHelper.deleteById(ProductInItem.class, data.getId());
		
	}

}
