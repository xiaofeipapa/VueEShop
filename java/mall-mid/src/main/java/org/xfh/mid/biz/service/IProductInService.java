package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.db.po.ProductInBatch;
import org.xfh.mid.vo.ProductInBatchDetailView;
import org.xfh.mid.vo.ProductInBatchVo;
import org.xfh.mid.vo.ProductInItemVo;
import org.xfh.mid.vo.form.ProductInAddDamageForm;
import org.xfh.mid.vo.form.ProductInAddStockForm;

public interface IProductInService extends ISingleTableService<ProductInBatch>{
	
	// 入库管理列表数据
	PageHolder<ProductInBatchVo> getProductInIndexPage(Long userId, IndexSearchForm form)throws Exception;
		
	// 在入库批次下新增入库商品
	void batchAddInItems(Long userId, ProductInAddStockForm form) throws Exception;
	
	// 返回批次记录
	ProductInBatchDetailView getBatchDetailWithItems(Long userId, Long recordId) throws Exception;
	
	// 和批次相关的入库细项列表
	PageHolder<ProductInItemVo> getBatchInItemIndexPage(Long userId, IndexSearchForm form)throws Exception;
	
	// 和盘点相关的入库细项列表
	PageHolder<ProductInItemVo> getCheckProductInItemIndexPage(Long userId, IndexSearchForm form)throws Exception;

	// 返回盘点细节单个记录
	ProductInItemVo getInItemById(Long userId, Long id)throws Exception;
	
	// 入库管理/ 报损入库
	Long addDamageOfBatch(Long userId, ProductInAddDamageForm form) throws Exception;
	
	// 删除报损
	List<ProductInItemVo> deleteDamage(Long userId, Long id) throws Exception;
	
	// 删除库存商品
	void deleteProduct(Long userId, Long batchId, Long productId) throws Exception;


}
