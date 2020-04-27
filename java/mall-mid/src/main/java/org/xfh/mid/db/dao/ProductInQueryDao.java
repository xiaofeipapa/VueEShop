package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.ProductInBatchVo;
import org.xfh.mid.vo.ProductInItemVo;

/**
 * 入库批次查询dao
 * 
 * @author cys
 *
 */
public interface ProductInQueryDao {	
	
	// 返回入库列表页数据
	List<ProductInBatchVo> getProductInIndexPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
		
	// 返回入库批次细节
	ProductInBatchVo getBatchDetailById(Long batchId);
	
	// 返回入库记录所关联的细项
	List<ProductInItemVo> getInItemListByBatchId(Long batchId);
	
	// 返回入库记录所关联的细项总数
	int getInItemCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回入库记录所关联的细项分页
	List<ProductInItemVo> getInItemPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回某个批次关联的商品的总库存
	int sumProductStockByBatchId(Long batchId);

}
