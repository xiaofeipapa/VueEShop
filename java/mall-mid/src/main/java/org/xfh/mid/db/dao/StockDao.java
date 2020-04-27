package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.SaleStockVo;
import org.xfh.mid.vo.WarehouseStockVo;

/**
 * 库存查询
 * 
 * @author cys
 *
 */
public interface StockDao {	

	// 根据商品模型, 获取模型下的所有规格属性组名和值. 
	List<SaleStockVo> getSaleStockBySql(@Param("sqlFilter")String sqlFilter);
	
	// 返回商品仓库库存总数
	int getWarehouseStockCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回商品仓库库存分页数据
	List<WarehouseStockVo> getWarehouseStockPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 更新仓库库存
	void updateWarehouseStock(@Param("id")Long id,@Param("productId")Long productId, @Param("stock")int stock);
	
	// 返回商品仓库某商品的库存数据
	Integer getWarehouseStock(@Param("id")Long id,@Param("productId")Long productId);
	
	// 该商品是真实库存, 但没有库存. 这类商品有多少
	int checkNoStockProduct(@Param("productIdStr")String productIdStr);
}
