package org.xfh.mid.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.AndFilter;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.helper.IWarehouseHelper;
import org.xfh.mid.biz.service.ICheckActivityService;
import org.xfh.mid.db.dao.CheckActivityDao;
import org.xfh.mid.db.dao.ProductQueryDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.UserCheckActivity;
import org.xfh.mid.db.po.UserCheckProduct;
import org.xfh.mid.enums.CheckActivityStatus;
import org.xfh.mid.enums.CheckResultCats;
import org.xfh.mid.vo.UserCheckActivityVo;
import org.xfh.mid.vo.UserCheckProductVo;
import org.xfh.mid.vo.WarehouseProductVo;
import org.xfh.mid.vo.form.CheckActivityAddProductForm;
import org.xfh.mid.vo.index.WarehouseActivityIndexSearchForm;
import org.xfh.mid.vo.index.WarehouseProductIndexSearchForm;

@Component
public class CheckActivityServiceImpl extends AbstractSingleTableService<UserCheckActivity> 
	implements ICheckActivityService{
	
	private Logger logger = LoggerFactory.getLogger(CheckActivityServiceImpl.class);
	
	@Autowired
	CheckActivityDao checkDao;
	
	@Autowired
	ProductQueryDao productQueryDao;
	
	@Autowired
	IWarehouseHelper warehouseHelper;


	@Override
	public PageHolder<WarehouseProductVo> getWarehouseProductPage(Long userId, WarehouseProductIndexSearchForm form)
			throws Exception {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		String moreSql = "";
		
		if(form.getSearchWarehouseId() != null) {
			moreSql += DSqlUtils.andEqNumber(DBs.WAREHOUSE_ID, form.getSearchWarehouseId(), false);
		}
		
		filter.setMoreSql(moreSql);
		
		int totalCount = checkDao.getWarehouseProductCount(filter);
		List<WarehouseProductVo> dataList = checkDao.getWarehouseProductPageList(filter);
		
		PageHolder<WarehouseProductVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());

		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
				
		return page;
	}
	
	@Override
	protected void checkBeforeCreateOrUpdate(UserCheckActivity entity, boolean isCreate) throws LogicException {
		
		VUtils.checkM(entity.getWarehouseId(), "请选择仓库");
		VUtils.checkM(entity.getCheckUserId(), "请选择盘点人");
		VUtils.checkM(entity.getCheckTime(), "请选择盘点时间");
		VUtils.checkM(entity.getRemark(), "请输入备注");
		
		if(isCreate) {
			entity.setDataStatus(CheckActivityStatus.TEMP.getValue());
			entity.setCreateTime(DateUtils.getChinaDate());
		}
		
	}

	@Override
	public PageHolder<UserCheckActivityVo> getCheckActivityPage(Long userId,
			WarehouseActivityIndexSearchForm form) {


		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		String moreSql = "";
		
		if(form.getSearchWarehouseId() != null) {
			moreSql += DSqlUtils.andEqNumber(DBs.WAREHOUSE_ID, form.getSearchWarehouseId(), false);
		}
		
		filter.setMoreSql(moreSql);
		
		filter.setLikeKeys(new String[] {DBs.CHECK_USER_NAME, DBs.CHECK_USER_PHONE});
		
		int totalCount = checkDao.getCheckActivityCount(filter);
		List<UserCheckActivityVo> dataList = checkDao.getCheckActivityPageList(filter);
		
		PageHolder<UserCheckActivityVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());

		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
				
		return page;
	}

	@Override
	public UserCheckActivityVo getDetailById(Long userId, Long id, boolean withProducts) {

		IndexSearchFilter filter = new IndexSearchFilter(null, 
				new PageInfo(1, 10));
		
		String moreSql = DSqlUtils.andEqNumber("a.id", id, false);
		filter.setMoreSql(moreSql);

		List<UserCheckActivityVo> dataList = checkDao.getCheckActivityPageList(filter);
		UserCheckActivityVo detail = dataList.get(0);
		
		
		List<UserCheckProductVo> products = this.getCheckProducts(userId, id);
		detail.setProducts(products);
				
		return detail;
	}

	@Transactional
	@Override
	public void addCheckProducts(Long userId, CheckActivityAddProductForm form) {

		// 查找已经存在的记录. 
		String sql = DSqlUtils.andEqNumber(DBs.ACTIVITY_ID, form.getActivityId(), false);
		sql += DSqlUtils.andIsNull(DBs.PARENT_ID, false);
		sql += DSqlUtils.andInValues(DBs.PRODUCT_ID, form.getProductIds());
		
		List<UserCheckProduct> existList = daoHelper.getListBySql(UserCheckProduct.class, sql);
		
		List<Long> shouldAdd = new ArrayList<>();

		for(Long productId : form.getProductIds()){
			boolean has = false;
			for(UserCheckProduct exist : existList) {
				if(exist.getProductId().longValue() == productId.longValue()) {
					has = true;
				}				
			}
			
			if(!has) {
				shouldAdd.add(productId);
			}
		}

		for(Long productId : shouldAdd){
			
			UserCheckProduct item = new UserCheckProduct();
			item.setWarehouseId(form.getWarehouseId());
			item.setActivityId(form.getActivityId());
			item.setProductId(productId);

			daoHelper.insertOne(UserCheckProduct.class, item);		
		}
	}

	@Transactional
	@Override
	public void deleteProduct(Long userId, Long checkProductId, String level) {
		
		// 如果level是parent, 那么要把关联的子记录也删掉
		daoHelper.deleteById(UserCheckProduct.class, checkProductId);
		
		if("parent".equalsIgnoreCase(level)) {
			String sql = DSqlUtils.andEqNumber(DBs.PARENT_ID, checkProductId, false);
			daoHelper.deleteBySql(UserCheckProduct.class, sql);
		}
		
	}

	@Transactional
	@Override
	public Long addCheckResult(Long userId, UserCheckProduct form) throws Exception {
		
		String addCat = form.getCheckResultCat();
		
		VUtils.checkM(addCat, "请选择检查结果");
		VUtils.checkGtZero(form.getQuantity(), "数量");
		
		boolean isDamage = CheckResultCats.DAMAGE.getValue().equalsIgnoreCase(addCat);
		if(isDamage && CheckUtils.isEmpty(form.getImageUrls())) {
			throw new LogicException("请上传报损图片");
		}
		
		boolean isOk = CheckResultCats.OK.getValue().equalsIgnoreCase(addCat);
		if( !isOk && CheckUtils.isEmpty(form.getRemark()) ) {
			throw new LogicException("请输入备注");			
		}
		
		// 检查兄弟节点数据的值
		String sql = DSqlUtils.andEqNumber(DBs.PARENT_ID, form.getParentId(), false);
		List<UserCheckProduct> siblings = daoHelper.getListBySql(UserCheckProduct.class, sql);
				
		if( siblings.isEmpty()) {
			// 可以插入
			return daoHelper.insertOne(UserCheckProduct.class, form);
		}
		
		// 数量减少和数量增多不能同时出现
		for(UserCheckProduct exist: siblings) {
			String existCat = exist.getCheckResultCat();
			
			if(existCat.equalsIgnoreCase(addCat)){
				throw new LogicException("不能增加重复的检查结果: " + CheckResultCats.getLabel(addCat));
			}
			
			boolean newAddIsLess = CheckResultCats.LESS_COUNT.getValue().equalsIgnoreCase(addCat);
			boolean existIsMore = CheckResultCats.MORE_COUNT.getValue().equalsIgnoreCase(existCat);
			
			if( newAddIsLess && existIsMore) {
				throw new LogicException("缺少数量或增加数量这两种检查结果不能同时存在");				
			}

			boolean newAddIsMore = CheckResultCats.MORE_COUNT.getValue().equalsIgnoreCase(addCat);
			boolean existIsLess = CheckResultCats.LESS_COUNT.getValue().equalsIgnoreCase(existCat);

			if( newAddIsMore && existIsLess) {
				throw new LogicException("缺少数量或增加数量这两种检查结果不能同时存在");				
			}
			
		}

		return daoHelper.insertOne(UserCheckProduct.class, form);
		
	}

	@Override
	public List<UserCheckProductVo> getCheckProducts(Long userId, Long activityId){
		
		List<UserCheckProductVo> products = checkDao.getCheckProducts(activityId);
		for(UserCheckProductVo vo : products) {
			warehouseHelper.convertLabelList(vo.getResultList());
		}
		return products;
	}

	@Override
	public PageHolder<UserCheckProductVo> getCheckProductPage(Long userId, WarehouseProductIndexSearchForm form) {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		String moreSql = "";
		
		if(form.getSearchWarehouseId() != null) {
			moreSql += DSqlUtils.andEqNumber(DBs.WAREHOUSE_ID, form.getSearchWarehouseId(), false);
		}
		
		filter.setMoreSql(moreSql);
		
		filter.setLikeKeys(new String[] {DBs.MODAL_TITLE});
		
		int totalCount = checkDao.getCheckProductCount(filter);
		List<UserCheckProductVo> dataList = checkDao.getCheckProductPageList(filter);
		for(UserCheckProductVo vo : dataList) {
			warehouseHelper.convertLabelList(vo.getResultList());
		}
		
		PageHolder<UserCheckProductVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());

		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
				
		return page;
	}

	@Transactional
	@Override
	public void deleteActivityAndProducts(Long userId, Long activityId) throws Exception {
		
		UserCheckActivity activity = daoHelper.getById(UserCheckActivity.class, activityId);
		if(CheckActivityStatus.VALID.getValue().equalsIgnoreCase(activity.getDataStatus())) {
			throw new LogicException("你不能删除正式的盘点数据: " + activityId);
		}
			
		daoHelper.deleteById(UserCheckActivity.class, activityId);
		
		AndFilter filter = new AndFilter(DBs.ACTIVITY_ID, activityId);
		daoHelper.deleteBySql(UserCheckProduct.class, filter.ok());
		
	}
	
	
	
}
