package org.xfh.mid.biz.service;

import java.util.Collection;
import java.util.List;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.db.po.ProductCategory;
import org.xfh.mid.vo.Level3DataVo;
import org.xfh.mid.vo.ProductCategoryVo;

public interface IProductCategoryService extends ISingleTableService<ProductCategory>{
	
	// 删除前检查
	void checkAndDelete(Long loginUserId, Long id) throws Exception;
	
	// 返回孙子商品分类列表
	List<Level3DataVo> getProductChildCatPageList(IndexSearchFilter searchFilter);
	
	// 返回孙子商品分类
	Level3DataVo getProductChildCat(Long id);

	// 返回所有的商品分类列表(用于树形控件)
	Collection<ProductCategoryVo> getRootCategoryList();
	
	// 回显数据
	Level3DataVo getVoWithFullParent(Long id);
}
