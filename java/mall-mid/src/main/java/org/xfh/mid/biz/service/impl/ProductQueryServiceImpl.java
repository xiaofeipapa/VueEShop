package org.xfh.mid.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.service.IProductQueryService;
import org.xfh.mid.db.dao.ProductQueryDao;
import org.xfh.mid.db.dao.SpecQueryDao;
import org.xfh.mid.db.dao.SupplierDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.Product;
import org.xfh.mid.enums.ProductStatus;
import org.xfh.mid.enums.StockCats;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.SupplierProductVo;
import org.xfh.mid.vo.index.ProductIndexSearchForm;

@Component
public class ProductQueryServiceImpl extends AbstractSingleTableService<Product> 
	implements IProductQueryService{
	
	private Logger logger = LoggerFactory.getLogger(ProductQueryServiceImpl.class);
	
	@Autowired
	ProductQueryDao productQueryDao;
	
	@Autowired
	SpecQueryDao specQueryDao;
	
	@Autowired
	SupplierDao supplierDao;
	
	@Override
	protected void checkBeforeCreateOrUpdate(Product entity, boolean isCreate) throws LogicException {
		// 此方法不使用
	}

	private PageHolder<ProductVo> getProductVoPage(Long userId, IndexSearchFilter searchFilter) {

		int totalCount = productQueryDao.getProductCount(searchFilter);
		List<ProductVo> dataList = productQueryDao.getProductVoPageList(searchFilter);
		
		PageHolder<ProductVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(searchFilter.getPageSize());
		page.setPageNo(searchFilter.getPageNo());

		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, searchFilter.getPageSize()));
				
		return page;
	}


	@Override
	public List<ProductVo> getExistProductListEqModalId(Long userId, Long modalId) {
		
		PageInfo pageInfo = new PageInfo(1, 1000);
		IndexSearchFilter searchFilter = new IndexSearchFilter("", pageInfo);
		
		String moreSql = DSqlUtils.andEqNumber(DBs.MODAL_ID, modalId, true);
		searchFilter.setMoreSql(moreSql);
		
		return this.getProductVoPage(userId, searchFilter).getDataList();
	}


	@Override
	public PageHolder<ProductVo> getCheckProductIndexPage(Long userId, IndexSearchForm form) {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));
//		String moreSql = "";
//		String waitCheck = ProductStatus.ON_SALE.getValue();
//		String valid = ProductStatus.NOT_SALE.getValue();
//		if( CheckUtils.isEmpty(searchForm.getSearchStatus())) {
//			moreSql = DSqlUtils.andInValues(DBs.DATA_STATUS, waitCheck, valid);
//		}
//		else {
//			moreSql = DSqlUtils.andEqString(DBs.DATA_STATUS, searchForm.getSearchStatus());
//		}
//		searchFilter.setMoreSql(moreSql);
		filter.setLikeKeys(new String[]{"modalTitle"});
				
		return this.getProductVoPage(userId, filter);
	}


	@Override
	public PageHolder<ProductVo> getProductIndexPage(Long userId, ProductIndexSearchForm form) {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		String moreSql = "";
		if( CheckUtils.isNotEmpty(form.getSearchStatus())) {
			moreSql += DSqlUtils.andEqString(DBs.DATA_STATUS, form.getSearchStatus(), true);			
		}

		if( CheckUtils.isNotEmpty(form.getSetSupplier())) {
			
			if("Y".equalsIgnoreCase(form.getSetSupplier())) {
				moreSql += DSqlUtils.andGtNumber(DBs.SUPPLIER_COUNT, 0, false);
			}else {
				moreSql += DSqlUtils.andEqNumber(DBs.SUPPLIER_COUNT,0, false);
			}	
		}

		if( CheckUtils.isNotEmpty(form.getSearchStockCat())) {
			moreSql += DSqlUtils.andEqNumber(DBs.STOCK_CAT, Integer.valueOf(form.getSearchStockCat()), false);			
		}

		if( CheckUtils.isNotEmpty(form.getSearchRealStock())) {
			
			if("gt".equalsIgnoreCase(form.getSearchRealStock())) {
				moreSql += DSqlUtils.andGtNumber(DBs.WAREHOUSE_STOCK, 0, false);
			}else {
				moreSql += DSqlUtils.andIsNull(DBs.WAREHOUSE_STOCK, false);
			}	
		}

		if( CheckUtils.isNotEmpty(form.getSearchRealStock())) {
			
			if("gt".equalsIgnoreCase(form.getSearchRealStock())) {
				moreSql += DSqlUtils.andGtNumber(DBs.WAREHOUSE_STOCK, 0, false);
			}else {
				moreSql += DSqlUtils.andIsNull(DBs.WAREHOUSE_STOCK, false);
			}	
		}
		
		if(CheckUtils.isNotEmpty(moreSql)) {
			filter.setMoreSql(moreSql);
		}
		
//		logger.debug("######################### product sql : " + moreSql);
		
		filter.setLikeKeys(new String[] {"modalTitle"});
				
		return this.getProductVoPage(userId, filter);
	}
	

	@Override
	public PageHolder<ProductVo> getOnSaleProductPage(Long userId, IndexSearchForm form) {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		String moreSql = DSqlUtils.andEqString(DBs.DATA_STATUS, ProductStatus.ON_SALE.getValue(), true);
		
		// 对于真实库存类型的, 需要有库存才显示. 对于虚拟库存, 直接显示. 
		moreSql += " and (";
		moreSql += 		"( " + DBs.STOCK_CAT + " = " +  StockCats.REAL_STOCK.getValue();
		moreSql += 		      DSqlUtils.andGtNumber(DBs.WAREHOUSE_STOCK, 0, false);
		moreSql += 		") ";
		moreSql += 		"or (";
		moreSql += 		      DBs.STOCK_CAT + " = " +  StockCats.VIRTUAL_STOCK.getValue();
		moreSql += 		") ";
		moreSql += 	") ";
				
		filter.setMoreSql(moreSql);
		
		filter.setLikeKeys(new String[] {"modalTitle"});
				
		return this.getProductVoPage(userId, filter);
	}

	@Override
	public PageHolder<ProductVo> getProductHasSupplierPage(Long userId, ProductIndexSearchForm form) {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		String moreSql = "";

		if( CheckUtils.isNotEmpty(form.getSetSupplier())) {
			
			if("Y".equalsIgnoreCase(form.getSetSupplier())) {
				moreSql += DSqlUtils.andGtNumber(DBs.SUPPLIER_COUNT, 0, false);
			}else {
				moreSql += DSqlUtils.andEqNumber(DBs.SUPPLIER_COUNT,0, false);
			}	
		}
		
		if(CheckUtils.isNotEmpty(moreSql)) {
			filter.setMoreSql(moreSql);
		}
		
		filter.setLikeKeys(new String[] {"modalTitle"});
		return this.getProductVoPage(userId, filter);
	}

	@Override
	public ProductVo getProductVoById(Long userId, Long productId) {

		IndexSearchFilter filter = new IndexSearchFilter(null, 
				new PageInfo(1, 10));
		
		String moreSql = DSqlUtils.andEqNumber("prod_view.id", productId, false);
		filter.setMoreSql(moreSql);

		return productQueryDao.getProductVoPageList(filter).get(0);
		
	}

	@Override
	public List<SupplierProductVo> getSupplierProductList(Long userId, Long productId) {
		return supplierDao.getSupplierProductList(productId);
	}
}
