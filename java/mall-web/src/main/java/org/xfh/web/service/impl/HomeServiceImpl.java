package org.xfh.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.helper.IProductHelper;
import org.xfh.mid.biz.service.IHelpArticleService;
import org.xfh.mid.biz.service.IProductCategoryService;
import org.xfh.mid.db.dao.HomeFloorDao;
import org.xfh.mid.db.dao.ProductQueryDao;
import org.xfh.mid.db.po.BottomLink;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.FrontHomeAdConfig;
import org.xfh.mid.db.po.HomeFloor;
import org.xfh.mid.db.po.HomeFloorProduct;
import org.xfh.mid.vo.HelpArticleVo;
import org.xfh.mid.vo.HomeFloorVo;
import org.xfh.mid.vo.ProductVo;
import org.xfh.web.service.IHomeService;
import org.xfh.web.vo.WebTopNav;

@Component
public class HomeServiceImpl extends BaseFrontService implements IHomeService{

    static final Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);

    @Autowired
    IHelpArticleService helpService;

    @Autowired
    HomeFloorDao floorDao;

    @Autowired
    ProductQueryDao productQueryDao;
    
    @Autowired
    IProductHelper productHelper;
    
    @Autowired
    IProductCategoryService catService;

	@Override
	public List<HelpArticleVo> getAllHelpArticle() {
		IndexSearchFilter filter = new IndexSearchFilter(null, new PageInfo(1, 1000));
		filter.setMoreSql(DSqlUtils.andEqNumber(DBs.SHOW_FRONT, 1, false));
		filter.setOrderBy("`order` asc");
		
		return helpService.getParentWithChildren(filter);
	}

	@Override
	public List<BottomLink> getAllBottomLink() {
		IndexSearchFilter filter = new IndexSearchFilter(null, new PageInfo(1, 1000));
		filter.setMoreSql(DSqlUtils.andEqNumber(DBs.SHOW_FRONT, 1, false));
		filter.setOrderBy("`order` asc");
		return daoHelper.getListByFilter(BottomLink.class, filter);
	}
	
	@Override
	public List<HomeFloorVo> getAllHomeFloor() {
		
		// 获取所有关联商品
		List<HomeFloorProduct> hfProducts = daoHelper.getAll(HomeFloorProduct.class);
		List<Long> productIds = new ArrayList<>();
		for(HomeFloorProduct hfp : hfProducts) {
			if( ! productIds.contains(hfp.getProductId())) {
				productIds.add(hfp.getProductId());
			}
		}
		
		// 获取商品
		String sql = DSqlUtils.andInValues(DBs.ID, productIds);
		List<ProductVo> prods = productQueryDao.getProductVoBySql(sql);
		productHelper.convertLabelList(prods);
		
		// 获取所有楼层
		List<HomeFloorVo> floorList = this.getAllFloorVoList();
		
		// 归组
		for(ProductVo prod: prods) {

			for(HomeFloorProduct hfp : hfProducts) {
				if(hfp.getProductId().longValue() == prod.getId().longValue()) {
					this.findAddFloorProduct(floorList, hfp.getFloorId(), prod);
				}
			}
			
		}
		
		return floorList;
		
	}
	
	private List<HomeFloorVo> getAllFloorVoList(){
		
		IndexSearchFilter filter = new IndexSearchFilter(null, new PageInfo(1, 1000));
		filter.setMoreSql(DSqlUtils.andEqNumber(DBs.SHOW_FRONT, 1, false));
		filter.setOrderBy("`order` asc");
		List<HomeFloor> floors = daoHelper.getListByFilter(HomeFloor.class, filter);

    	List<HomeFloorVo> floorList = new ArrayList<>();
    	
    	for(HomeFloor hf : floors) {
    		HomeFloorVo vo = new HomeFloorVo();
    		RefUtils.copyFieldsToObject(hf, vo);
    		floorList.add(vo);
    	}
    	
    	return floorList;		
	}
    
    private void findAddFloorProduct(List<HomeFloorVo> floorList, Long floorId, ProductVo prod) {
    	    	
    	for(HomeFloorVo vo : floorList) {
    		if(vo.getId().longValue() == floorId.longValue()) {
    			vo.getProducts().add(prod);
    		}
    	}
    	
    }

	@Override
	public WebTopNav getTopNavData() {
		String sql = DSqlUtils.andEqNumber(DBs.SHOW_FRONT, 1, false);
		sql += DSqlUtils.andEqString(DBs.CAT, FrontHomeAdConfig.CAT_BREAD_CUMP, false);
		
		WebTopNav nav = new WebTopNav();
		nav.setAdList(daoHelper.getListBySql(FrontHomeAdConfig.class, sql));
		
		// 获取分类
		nav.setCatList(catService.getRootCategoryList());
		
		return nav;
	}
    

}
