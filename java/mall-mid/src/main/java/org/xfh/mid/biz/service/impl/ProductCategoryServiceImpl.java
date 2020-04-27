package org.xfh.mid.biz.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.biz.service.IProductCategoryService;
import org.xfh.mid.db.dao.ProductCategoryDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.DistrictData;
import org.xfh.mid.db.po.ProductCategory;
import org.xfh.mid.vo.DistrictDataVo;
import org.xfh.mid.vo.DistrictWithParentVo;
import org.xfh.mid.vo.Level3DataVo;
import org.xfh.mid.vo.ProductCategoryVo;

@Component
public class ProductCategoryServiceImpl extends AbstractSingleTableService<ProductCategory> 
	implements IProductCategoryService{
	
	private Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	
	@Autowired
	ProductCategoryDao productCategoryDao;

	@Override
	protected void checkBeforeCreateOrUpdate(ProductCategory entity, boolean isCreate) throws LogicException {


		VUtils.checkM(entity.getName(), "请输入分类名称");
		if(entity.getLevel() != ProductCategory.LEVEL_ROOT) {
			VUtils.checkM(entity.getParentId(), "请选择父分类");			
		}
		
		String error = "同一层级的分类名字已经存在: " + entity.getName();
		
		// 同一层级的分类名字不能重复
		String sqlFilter = DSqlUtils.andEqNumber("level", entity.getLevel(), true);
		sqlFilter += DSqlUtils.andEqString("name", entity.getName(), true);
		sqlFilter += DSqlUtils.andEqNumber("parentId", entity.getParentId(), true);
				
		if(isCreate) {
			// 新增的情况只需要检查重名
			int existCount = commonDao.getCountBySql(this.tableName, sqlFilter);
			if(existCount > 0) {
				throw new LogicException(error);
			}
			
			// 设置排序
			String orderSql = DSqlUtils.andEqNumber("level", entity.getLevel(), true);
			orderSql += DSqlUtils.andEqNumber("parentId", entity.getParentId(), true);
						
			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", orderSql);
			entity.setOrder(maxOrder + 1);
			
			// 设置默认数据
			entity.setShowFront(ProductCategory.SHOW_FRONT_NO);
			
			return;
		}

		// 更新的情况, 如果记录数大于1肯定重复, 如果只是1, 检查是否本记录. 
		
		Map exist = commonDao.getOneBySql(this.tableName, sqlFilter);
		
		if(exist == null || exist.get("id") == null) {
			return;
		}
		
		long existId = Long.valueOf(exist.get("id").toString());
		if(existId != entity.getId().intValue()) {
			throw new LogicException(error);	
			
		}
		
	}
	
	public void checkAndDelete(Long loginUserId, Long id) throws Exception{

		// 检查是否有子分类, 是否有关联商品
		
		// 检查子分类
		String sqlFilter = "and parentId=" + id;
		int existCount = commonDao.getCountBySql(this.tableName, sqlFilter);
		if(existCount > 0) {
			throw new LogicException("该分类存在子分类, 请先删除子分类数据");
		}
		
		// FIXME 检查商品数量
		
		super.deleteById(loginUserId, id);
	}

	@Override
	public List<Level3DataVo> getProductChildCatPageList(IndexSearchFilter searchFilter) {
		
		return productCategoryDao.getProductChildCatPageList(searchFilter);
		
	}

	@Override
	public Level3DataVo getProductChildCat(Long id) {
		
		return productCategoryDao.getProductChildCat(id);
	}

	@Override
	public Collection<ProductCategoryVo> getRootCategoryList() {

		List<ProductCategory> allData = this.getAll();
		
		Map<Long, ProductCategoryVo> rootMap = new HashMap<>();
		Map<Long, ProductCategoryVo> parentMap = new HashMap<>();
		List<ProductCategoryVo> childList = new ArrayList<>();
		
		// 先找出根节点
		for(ProductCategory data : allData) {

			ProductCategoryVo vo = new ProductCategoryVo();
			RefUtils.copyFieldsToObject(data, vo);

			boolean isRoot = data.getLevel() == ProductCategoryVo.LEVEL_ROOT;
			boolean isParent = data.getLevel() == ProductCategoryVo.LEVEL_PARENT;
			boolean isChild = data.getLevel() == ProductCategoryVo.LEVEL_CHILD;
			
			if(isRoot) {
				rootMap.put(vo.getId(), vo);
			}
			
			if(isParent) {
				parentMap.put(data.getId(), vo);
			}
			
			if(isChild) {
				childList.add(vo);
			}
		}

		// 找出二级节点并添加		
		for(ProductCategoryVo root : rootMap.values()) {

			for(ProductCategoryVo vo : parentMap.values()) {
	
				Long parentId = vo.getParentId();
				
				if(root.getId().longValue() == parentId.longValue()) {
					root.addChild(vo);;
				}
				
			}
			
		}


		// 找出三级节点并添加
		for(ProductCategoryVo city : parentMap.values()) {

			for(ProductCategoryVo vo : childList) {

				Long parentId = vo.getParentId();

				if(city.getId().longValue() == parentId.longValue()) {
					city.addChild(vo);;
				}
				
			}
			
		}
		
		return rootMap.values();
	}

	@Override
	public Level3DataVo getVoWithFullParent(Long id) {

		// 找出 
		ProductCategory child = this.getById(id);
		ProductCategory parent  = this.getById(child.getParentId());
		ProductCategory root  = this.getById(parent.getParentId());
		
		// 找出同一parent的child列表
		String sql = DSqlUtils.andEqNumber(DBs.PARENT_ID, child.getParentId(), false);
		List<ProductCategory> childList = daoHelper.getListBySql(ProductCategory.class, sql);
		
		// 找出当前root下的parent列表
		sql = DSqlUtils.andEqNumber(DBs.PARENT_ID, parent.getParentId(), false);
		List<ProductCategory> parentList = daoHelper.getListBySql(ProductCategory.class, sql);
		
		Level3DataVo childVo = new Level3DataVo();
		RefUtils.copyFieldsToObject(child, childVo);
		childVo.setRootId(root.getId());
		childVo.setRootName(root.getName());
		childVo.setParentId(parent.getId());
		childVo.setParentName(parent.getName());

		List<Level3DataVo> childOptions = new ArrayList<>();
		for(ProductCategory entity : childList) {
			Level3DataVo vo = new Level3DataVo();
			RefUtils.copyFieldsToObject(entity, vo);
			childOptions.add(vo);
		}

		List<Level3DataVo> parentOptions = new ArrayList<>();
		for(ProductCategory entity : parentList) {
			Level3DataVo vo = new Level3DataVo();
			RefUtils.copyFieldsToObject(entity, vo);
			parentOptions.add(vo);
		}
		
		childVo.setChildOptions(childOptions);
		childVo.setParentOptions(parentOptions);
				
		return childVo;
	}

}
