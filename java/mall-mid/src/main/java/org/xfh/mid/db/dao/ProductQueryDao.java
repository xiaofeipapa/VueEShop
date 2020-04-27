package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.db.po.Product;
import org.xfh.mid.db.po.ProductModal;
import org.xfh.mid.vo.ProductModalVo;
import org.xfh.mid.vo.ProductVo;

/**
 * 封装商品业务中单表service 无法满足的操作
 * 
 * @author cys
 *
 */
public interface ProductQueryDao {	

	// 获取商品模型
	ProductModal getModalById(@Param("modalId")Long modalId);
	
	// 返回商品模型的浓缩字段
	List<ProductModalVo> getModalIndexPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 带商品实例个数的分页数据
	List<Product> getInstanceInModalIds(@Param("idList")List<Long> idList);
	
	// 返回模型的商品数量
	int getProductCountByModalId(@Param("modalId")Long modalId);
	
	int getProductCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回商品实例的分页数据
	List<ProductVo> getProductVoPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回商品实例的数据
	List<ProductVo> getProductVoBySql(@Param("sqlFilter")String sqlFilter);
	
	// 返回商品实例待修改数据
	ProductVo getEditFormById(@Param("id")Long id);
	
	
}
