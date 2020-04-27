package org.xfh.mid.biz.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableOrderService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.biz.service.IHelpArticleService;
import org.xfh.mid.db.dao.ProductCategoryDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.HelpArticle;
import org.xfh.mid.vo.HelpArticleVo;

@Component
public class HelpArticleServiceImpl extends AbstractSingleTableOrderService<HelpArticle> 
	implements IHelpArticleService{
	
	private Logger logger = LoggerFactory.getLogger(HelpArticleServiceImpl.class);
	
	@Autowired
	ProductCategoryDao productCategoryDao;

	@Override
	protected void checkBeforeCreateOrUpdate(HelpArticle entity, boolean isCreate) throws LogicException {

		boolean isCat = entity.getLevel() == HelpArticle.LEVEL_CAT;
		if( isCat ) {		
			VUtils.checkMandMax(entity.getName(), "分类名称", 20);
		}
		else {	
			VUtils.checkMandMax(entity.getName(), "文章标题", 20);		
		}
		

		String sqlFilter = DSqlUtils.andEqNumber("level", entity.getLevel(), true);
		sqlFilter += DSqlUtils.andEqString("name", entity.getName(), true);
		if( ! isCreate) {
			sqlFilter += DSqlUtils.andNe(DBs.ID, entity.getId(), true);
		}

		int existCount = commonDao.getCountBySql(this.tableName, sqlFilter);
		if(existCount > 0) {
			if(isCat) {
				throw new LogicException("同一分类名称已经存在");
			}else {
				throw new LogicException("同一文章标题已经存在");				
			}
		}
		
				
		if(isCreate) {
			
			// 设置排序
			String orderSql = DSqlUtils.andEqNumber("level", entity.getLevel(), true);
			orderSql += DSqlUtils.andEqNumber("parentId", entity.getParentId(), true);
						
			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", orderSql);
			entity.setOrder(maxOrder + 1);
			
			// 设置默认数据
			entity.setShowFront(false);
			
			return;
		}
		
	}
	
	

	@Override
	public void checkAndDelete(Long loginUserId, Long id) throws Exception {
		daoHelper.deleteById(HelpArticle.class, id);
	}

	@Override
	public List<HelpArticleVo> getParentWithChildren(IndexSearchFilter searchFilter) {
		
		if(searchFilter == null) {
			searchFilter = new IndexSearchFilter();
		}
		
		searchFilter.setOrderBy("`order` asc");
		
		List<HelpArticle> dataList = daoHelper.getListByFilter(HelpArticle.class, searchFilter);
		List<HelpArticleVo> parents = new ArrayList<>();

		for(HelpArticle art : dataList) {
			if(art.getLevel() == HelpArticle.LEVEL_CAT) {
				HelpArticleVo vo = new HelpArticleVo();
				RefUtils.copyFieldsToObject(art, vo);
				parents.add(vo);
			}
		}

		for(HelpArticle art : dataList) {
			if(art.getLevel() == HelpArticle.LEVEL_CAT) {
				continue;
			}
			
			for(HelpArticleVo parent: parents) {
				if(art.getParentId().longValue() == parent.getId().longValue()) {
					HelpArticleVo vo = new HelpArticleVo();
					RefUtils.copyFieldsToObject(art, vo);
					parent.getChildren().add(vo);
				}
			}
		}
		
		return parents;		
	}

	@Override
	public HelpArticleVo getVoById(Long id) {
		HelpArticle data = daoHelper.getById(HelpArticle.class, id);
		HelpArticleVo vo = new HelpArticleVo();
		RefUtils.copyFieldsToObject(data, vo);
		return vo;
	}



	@Override
	protected String sameParentFilter(HelpArticle entity) {
		return DSqlUtils.andEqNumber("parentId", entity.getParentId(), true);
	}



	@Override
	public void toggleShow(Long userId, Long id) {
		
		HelpArticle data = daoHelper.getById(HelpArticle.class, id);
		
		boolean toSet = ! data.isShowFront();
		
		HelpArticle param = new HelpArticle();
		param.setId(data.getId());
		param.setShowFront(toSet);
		daoHelper.updateById(HelpArticle.class, param);
				
		boolean isCat = data.getLevel() == HelpArticle.LEVEL_CAT;
		if(isCat) {
			
			// 更新所有分类下的文章
			String sql = DSqlUtils.andEqNumber(DBs.PARENT_ID, data.getId(), false);
			param = new HelpArticle();
			param.setShowFront(toSet);			
			daoHelper.updateBySql(HelpArticle.class, param, sql);
			
		}
		
	}
	

}
