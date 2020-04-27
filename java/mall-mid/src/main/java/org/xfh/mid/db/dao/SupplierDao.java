package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.SupplierOrderItemVo;
import org.xfh.mid.vo.SupplierOrderVo;
import org.xfh.mid.vo.SupplierProductVo;

/**
 * 供应商查询相关
 * @author cys
 *
 */
public interface SupplierDao {
	
	// 返回商品关联的供应商信息	
	List<SupplierProductVo> getSupplierProductList(@Param("productId")Long productId);

	List<SupplierOrderVo> getSupplierOrderPageList(@Param("searchFilter")IndexSearchFilter searchFilter);	
	int getSupplierOrderCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	SupplierOrderVo getOrderForEdit(@Param("orderId")Long orderId);
	
	List<SupplierOrderItemVo> getOrderItemList(@Param("orderId")Long orderId);
}
