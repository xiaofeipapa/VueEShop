package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.SupplierProductVo;
import org.xfh.mid.vo.WarehouseProductVo;
import org.xfh.mid.vo.index.ProductIndexSearchForm;
import org.xfh.mid.vo.index.WarehouseProductIndexSearchForm;

/**
 * 因为商品的结构较为复杂, 多模块都要使用, 而代码又有很高的重复度, 所以专门独立一个商品查询类来封装业务, 减轻工作量和重复代码. 
 * @author cys
 *
 */
public interface IProductQueryService{
	
	// 返回所有商品的分页数据
	PageHolder<ProductVo> getProductIndexPage(Long userId, ProductIndexSearchForm searchForm);
	
	// 返回上架商品的分页数据
	PageHolder<ProductVo> getOnSaleProductPage(Long userId, IndexSearchForm searchForm);
	
	// 返回盘点列表的分页数据
	PageHolder<ProductVo> getCheckProductIndexPage(Long userId, IndexSearchForm searchForm);
	
	// 返回商品模型已有的商品
	List<ProductVo> getExistProductListEqModalId(Long userId,Long modalId);
	
	// 返回单个商品的数据, 带关联表信息
	ProductVo getProductVoById(Long userId, Long productId);
	
	// 根据商品id, 返回关联的供应商价格信息
	List<SupplierProductVo> getSupplierProductList(Long userId, Long productId);
	
	// 返回渠道是供应商供货的商品列表
	PageHolder<ProductVo> getProductHasSupplierPage(Long userId, ProductIndexSearchForm searchForm);
		
}
