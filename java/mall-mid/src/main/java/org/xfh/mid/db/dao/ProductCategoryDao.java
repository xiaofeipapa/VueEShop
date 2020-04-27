package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.Level3DataVo;

/**
 * 封装商品分类特殊的查询操作
 * 
 * @author cys
 *
 */
public interface ProductCategoryDao {	

	// 返回孙子商品分类列表
	List<Level3DataVo> getProductChildCatPageList(@Param("searchFilter")IndexSearchFilter searchFilter);

	// 返回孙子商品分类数据
	Level3DataVo getProductChildCat(@Param("id")Long id);
}
