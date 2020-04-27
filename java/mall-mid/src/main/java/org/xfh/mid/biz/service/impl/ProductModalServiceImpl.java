package org.xfh.mid.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.service.IProductModalService;
import org.xfh.mid.db.dao.ProductQueryDao;
import org.xfh.mid.db.dao.SpecQueryDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.ProductModal;
import org.xfh.mid.db.po.ProductParam;
import org.xfh.mid.enums.ProductStatus;
import org.xfh.mid.vo.ProductModalFormVo;
import org.xfh.mid.vo.ProductModalVo;

@Component
public class ProductModalServiceImpl extends AbstractSingleTableService<ProductModal> 
	implements IProductModalService{
	
	private Logger logger = LoggerFactory.getLogger(ProductModalServiceImpl.class);
	
	@Autowired
	ProductQueryDao productQueryDao;
	
	@Autowired
	SpecQueryDao specQueryDao;

	@Override
	protected void checkBeforeCreateOrUpdate(ProductModal entity, boolean isCreate) throws LogicException {
		
		if(isCreate) {
			VUtils.checkM(entity.getTitle(), "请输入商品标题");
			VUtils.checkM(entity.getBrandId(), "请选择商品品牌");
			VUtils.checkM(entity.getImageStr(), "请至少上传一张商品图片");
			VUtils.checkM(entity.getRemark(), "请输入商品详情");
			
		}
		// 更新不使用这个方法, 所以不需代码. 
		
	}

	@Override
	public Long updateModalAndParams(Long userId, ProductModalFormVo formVo, String[] avoidFieldsForUpdate) throws Exception {
		
		ProductModal entity = new ProductModal();
		RefUtils.copyFieldsToObject(formVo, entity);
		
		Long id = super.saveWithCheck(userId, entity, avoidFieldsForUpdate);
		
		String paramStr = formVo.getParamStr();
		if(CheckUtils.isEmpty(paramStr))return id;
		
		String[] paramArr = paramStr.split(",");
		for(String param : paramArr) {
			
			String[] pstr = param.split("_");
			
			String name = pstr[0];
			String value = "";
			if(pstr.length > 1) {
				value = pstr[1];
			}
			
			String checkSql = DSqlUtils.andEqNumber("dataId", id, true);
			checkSql += DSqlUtils.andEqString("cat", ProductParam.CAT_MODAL, true);
			checkSql += DSqlUtils.andEqString("name", name, true);
			
			TreeMap<String, Object> dataMap = new TreeMap<>();

			dataMap.put("dataId", id);
			dataMap.put("cat", ProductParam.CAT_MODAL);
			dataMap.put("name", name);
			dataMap.put("value", value);

			Map existData = commonDao.getOneBySql(ProductParam.TABLE_NAME, checkSql);
			
			if(existData != null) {
				// 更新模式
				commonDao.updateById(ProductParam.TABLE_NAME, dataMap, Long.valueOf(existData.get("id").toString()));
			}else {
				// 新建模式
				commonDao.insertOne(ProductParam.TABLE_NAME, dataMap);
			}
		}
		
		return id;

	}
	

	@Override
	public PageHolder<ProductModalVo> getModalIndexPage(IndexSearchForm form) throws Exception {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));
		
		filter.setLikeKeys(new String[]{"title"});
		
		if( CheckUtils.isNotEmpty(form.getSearchStatus())) {
			String moreSql = DSqlUtils.andEqString(DBs.DATA_STATUS, form.getSearchStatus(), true);
			filter.setMoreSql(moreSql);
		}
				
		PageHolder<ProductModalVo> page = new PageHolder<ProductModalVo>();
		
		int totalCount = super.getCountByFilter(filter);
		List<ProductModalVo> dataList = productQueryDao.getModalIndexPageList(filter);

		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());
		page.setDataList(dataList);
		page.setTotalCount(totalCount);
		
		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
				
		return page;
	}

	@Transactional
	@Override
	public void updateStatus(Long userId, Long id, String opt) throws Exception {

		
		if("recover".equalsIgnoreCase(opt)){
			ProductModal param = new ProductModal();
			param.setDataStatus(ProductStatus.NOT_SALE.getValue());
			param.setId(id);		
			this.updateById(param, null);
			return;
		}

		ProductModal entity = this.getById(id);
		
//		if(ProductStatus.ON_SALE.getValue().equalsIgnoreCase(entity.getDataStatus())) {
//			throw new LogicException("该模型已经上架, 请先设置为下架状态才能废弃或删除");				
//		}
		
		if("trash".equalsIgnoreCase(opt)){
			ProductModal param = new ProductModal();
			param.setDataStatus(ProductStatus.TRASH.getValue());
			param.setId(id);
			param.setTrashTime(new Date());
			param.setTrashUserId(userId);			
			this.updateById(param, null);
			return;
		}
		
		
		// 真正删除
		
		int productCount = productQueryDao.getProductCountByModalId(id);
		if(productCount > 0) {
			throw new LogicException("该模型已有"+productCount+"个关联商品,请先删除商品");			
		}
		
		// 删除规格属性组和值的数据
		String sqlFilter = DSqlUtils.andEqNumber(DBs.MODAL_ID, id, true);
		commonDao.deleteBySql(DBs.TABLE_MODAL_SPEC_GROUP, sqlFilter);

		commonDao.deleteBySql(DBs.TABLE_MODAL_SPEC_VALUE, sqlFilter);
		
		// 删除参数
		sqlFilter = DSqlUtils.andEqNumber(DBs.DATA_ID, id, true);
		sqlFilter += DSqlUtils.andEqString(DBs.CAT, ProductParam.CAT_MODAL, true);
		
		commonDao.deleteById(this.tableName, id);
		
	}

	@Override
	public void updateImage(Long userId, Long id, String imageStr) throws LogicException {

		TreeMap<String, Object> param = new TreeMap<>();
		param.put("imageStr", imageStr);
		param.put("updateUserId", userId);
		commonDao.updateById(this.tableName, param, id);
	}

	/**
	 * 暂时没想好. 觉得上架在商品那里做更方便. 所以先保留代码
	 */
//	@Override
//	public boolean toggleOnSale(Long userId, Long id) throws Exception {
//
//		ProductModal entity = this.getById(id);
//		TreeMap<String, Object> param = new TreeMap<>();
//		
//		boolean isOnSale = false;
//		if(ProductStatus.ON_SALE.getValue().equalsIgnoreCase(entity.getDataStatus())) {
//			param.put(DBs.DATA_STATUS, ProductStatus.NOT_SALE.getValue());
//		}else {
//			
//			//  有商品才能上架
//			int productCount = productQueryDao.getProductCountByModalId(id);
//			if(productCount < 1) {
//				throw new LogicException("请先在[编辑商品模型]的[规格库存]栏目增加商品, 才能设置上架");			
//			}
//			
//			param.put(DBs.DATA_STATUS, ProductStatus.ON_SALE.getValue());		
//			isOnSale = true;
//		}
//		commonDao.updateById(this.tableName, param, id);
//		
//		
//		return isOnSale;
//	}
	
}
