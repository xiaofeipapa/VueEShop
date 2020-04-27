package org.xfh.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.biz.helper.IProductHelper;
import org.xfh.mid.db.dao.ProductQueryDao;
import org.xfh.mid.db.dao.WebProductDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.Product;
import org.xfh.mid.db.po.ProductSpecValue;
import org.xfh.mid.db.po.UserLike;
import org.xfh.mid.enums.ProductStatus;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.web.ProductCatSearchForm;
import org.xfh.mid.vo.web.WebModalSpecGroupVo;
import org.xfh.mid.vo.web.WebModalSpecValueVo;
import org.xfh.mid.vo.web.WebProductVo;
import org.xfh.web.service.IWebProductService;
import org.xfh.web.vo.ModalSpecMapVo;

@Component
public class ProductServiceImpl extends BaseFrontService implements IWebProductService{

    static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductQueryDao productQueryDao;
    
    @Autowired
    IProductHelper productHelper;
    
    @Autowired
    WebProductDao webProductDao;

	@Override
	public PageHolder<ProductVo> getProductPage(ProductCatSearchForm form) {

		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		filter.setLikeKeys(new String[]{"modalTitle"});
		
		String moreSql = DSqlUtils.andEqString(DBs.DATA_STATUS, ProductStatus.ON_SALE.getValue(), false);
		
		if( CheckUtils.isNotEmpty(form.getCat())) {
			moreSql += DSqlUtils.andEqNumber(DBs.CAT_1, form.getCat(), false);
		}
		
		filter.setMoreSql(moreSql);

		int totalCount = productQueryDao.getProductCount(filter);
		List<ProductVo> dataList = productQueryDao.getProductVoPageList(filter);
		
		productHelper.convertLabelList(dataList);
		
		return daoHelper.makePage(ProductVo.class, totalCount, dataList, filter);
		
	}

	@Override
	public WebProductVo getWebProductDetail(Long userId, Long productId) {

		 WebProductVo vo = webProductDao.getProductById(productId);
		
		// 获取模型的规格属性组
		 List<WebModalSpecGroupVo> groups = webProductDao.getModalSpecGroupWithValues(vo.getModalId());
		 
		 vo.setGroups(groups);
			
		/**
		 * FIXME 2020-4-8
		 * product 应该可以上传自己的照片, 要不然前端只能随机取modal的一张图片(以多种颜色的苹果为例就知道, 这明显不对)
		 */
		if(CheckUtils.isEmpty(vo.getProductImage())) {
			vo.setProductImage(vo.getModalImages().split(",")[0]);
		}
		
		// 检查选中的商品
		String sql = DSqlUtils.andEqNumber(DBs.PRODUCT_ID, productId, false);
		List<ProductSpecValue> pspecs = daoHelper.getListBySql(ProductSpecValue.class, sql);
		
		// 当前商品的规格属性组和值
		Map<Long, Long> currentSpecMap = new HashMap<>();
		for(WebModalSpecGroupVo group : groups) {
			
			for(WebModalSpecValueVo specVo : group.getChildren()) {
				
				for(ProductSpecValue psv : pspecs) {
					
					if(specVo.getId().longValue() == psv.getSpecValueId().longValue()) {
						specVo.setCheck(true);
						currentSpecMap.put(group.getId(), specVo.getId());
					}
					
				}
				
			}
			
		}
		vo.setCurrentSpecMap(currentSpecMap);
		
		// 收藏与否
		sql = DSqlUtils.andEqNumber(DBs.USER_ID, userId, false);
		sql += DSqlUtils.andEqNumber(DBs.PRODUCT_ID, productId, false);
		
		int like = daoHelper.getCountBySql(UserLike.class, sql);
		vo.setLikeGoods(like > 0);
		
		 
		return vo;
	}

	@Override
	public Long getProductIdBySpec(Long userId, ModalSpecMapVo form) {
		
		List<WebProductVo> products = webProductDao.getProductsByModalId(form.getModalId());
		
		String specStrings = Product.convertToSpecValueStringWithSep(form.getSpecIds());
		for(WebProductVo prod : products) {
			
			if(Product.isEqSpecValueStrings(specStrings, prod.getSpecValueIdString())) {
				return prod.getId();
			}
			
		}
		
		throw new RuntimeException("商品不存在");
	}

	@Override
	public List<WebProductVo> getProductByIds(Long userId, List<Long> ids) {
		List<WebProductVo> voList = webProductDao.getProducts(ids);
		productHelper.convertWebProducts(voList);
		return voList;
	}

	@Override
	public void toggleLike(Long userId, Long productId, boolean flag) {
		
		String sql = DSqlUtils.andEqNumber(DBs.USER_ID, userId, false);
		sql += DSqlUtils.andEqNumber(DBs.PRODUCT_ID, productId, false);
		
		UserLike userLike = daoHelper.getOneBySql(UserLike.class, sql);
		
		if(flag && userLike == null) {
			userLike = new UserLike();
			userLike.setCreateTime(DateUtils.getChinaDate());
			userLike.setProductId(productId);
			userLike.setUserId(userId);
			daoHelper.insertOne(UserLike.class, userLike);
			return;			
		}
		
		if( !flag && userLike != null) {
			daoHelper.deleteBySql(UserLike.class, sql);
		}		
	}

	@Override
	public PageHolder<ProductVo> getProductLikePage(Long userId, ProductCatSearchForm form) {

		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		
		String moreSql = DSqlUtils.andEqNumber(DBs.USER_ID, userId, false);
				
		filter.setMoreSql(moreSql);
		filter.setOrderBy("createTime desc");

		int totalCount = webProductDao.getLikeProductCount(filter);
		List<Long> pids = webProductDao.getLikeProducts(filter);
		
		List<ProductVo> dataList = new ArrayList<>();
		if(totalCount < 1) {
			return daoHelper.makePage(ProductVo.class, totalCount, dataList, filter);
		}
		
		// 拼出商品
		String sql = DSqlUtils.andInValues(DBs.ID, pids);
		dataList = productQueryDao.getProductVoBySql(sql);		
		productHelper.convertLabelList(dataList);				
		
		return daoHelper.makePage(ProductVo.class, totalCount, dataList, filter);
	}
	
}
