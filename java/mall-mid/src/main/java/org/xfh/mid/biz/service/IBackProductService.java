package org.xfh.mid.biz.service;

import java.util.Collection;
import java.util.List;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.mid.db.po.Product;
import org.xfh.mid.vo.ModalSpecGroupVo;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.form.ProductBatchSetSupplierPriceForm;
import org.xfh.mid.vo.form.ProductBatchUpdateForm;

public interface IBackProductService extends ISingleTableService<Product>{
	
	// 生成商品实例表格. 
	void makeAll(Long userId,Long modalId) throws Exception;
		
	// 批量删除
	void batchDelete(Long userId, String idstr)throws Exception;
	
	// 批量提交审核
//	List<Long> batchSubmitToConfirm(Long userId, String idstr)throws Exception;
	
	// 将商品实例的规格属性值形成db所需要的结构
	List<List<Long>> makeUniqueProductSpecValueStrings(Collection<ModalSpecGroupVo> specValueList);
	
	// 批量更新属性
	void batchUpdateOneField(Long userId, String idstr, String fieldName, String fieldValue)throws Exception;
	
	// 批量更新价格
	void batchUpdatePrice(Long userId, ProductBatchUpdateForm form)throws Exception;
	
	// 批量更新上架/下架
	void batchToggleSale(Long userId, String idstr, String status)throws Exception;
	
	// 获取商品数据
	ProductVo getEditFormData(Long userId, Long productId)throws Exception;
	
	// 单个的时候设置下架
	void setNotSale(Long userId, Long productId)throws Exception;
	
	// 保存单个商品
	void saveEach(Long userId, ProductVo vo)throws Exception;
	
	// 批量设置价格
	void batchSetSupplierProduct(Long userId, ProductBatchSetSupplierPriceForm form) throws Exception;
	
	// 更新sku
	void saveSku(Long userId, Long id, String sku) throws Exception;
		
}
