package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.web.WebModalSpecGroupVo;
import org.xfh.mid.vo.web.WebProductVo;

/**
 * 前台页面查询dao
 * @author cys
 *
 */
public interface WebProductDao {
	
	// 获取前台网站的商品结构
	WebProductVo getProductById(@Param("id")Long id);
	
	// 获取前台网站的商品结构列表
	List<WebProductVo> getProducts(@Param("idList")List<Long> idList);
	
	// 获取前台网站的商品结构列表
	List<WebProductVo> getProductsByModalId(@Param("modalId")Long modalId);

	// 获取商品关联的规格属性组和值
	List<WebModalSpecGroupVo> getModalSpecGroupWithValues(@Param("modalId")Long modalId);

	// 获取被收藏商品数量
	int getLikeProductCount(@Param("searchFilter")IndexSearchFilter searchFilter);

	// 获取被收藏商品列表
	List<Long> getLikeProducts(@Param("searchFilter")IndexSearchFilter searchFilter);
	
}
