package org.xfh.mid.biz.service;

import org.xfh.mid.vo.form.CheckProductUpdateStatusForm;
import org.xfh.mid.vo.form.ProductBatchUpdateForm;

/**
 * 要改变商品状态的操作很多, 专门独立一个servcie出来. 
 * 
 * @author cys
 *
 */
public interface IProductUpdateService{
	
	// 商品上架的设置库存类型, 设置上架, 设置下架, 退回等操作. 因为比较简单, 都放在一个方法里. 
//	void updateWhenProductConfirm(Long userId, ProductModalIndexSearchForm form)throws Exception;
	
	// 盘点时完成盘点或退回
//	void updateStatusWhenCheckProduct(Long userId, CheckProductUpdateStatusForm form)throws Exception;
	
}
