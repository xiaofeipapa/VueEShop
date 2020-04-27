package org.xfh.mid.biz.service;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.db.po.ProductModal;
import org.xfh.mid.vo.ProductModalFormVo;
import org.xfh.mid.vo.ProductModalVo;

public interface IProductModalService extends ISingleTableService<ProductModal>{
	
	// 返回列表数据
	public PageHolder<ProductModalVo> getModalIndexPage(IndexSearchForm searchForm) throws Exception;
	
	// 废弃或删除模型数据. 
	void updateStatus(Long userId, Long id, String opt) throws Exception ;
	
	// 保存/更新商品图片
	void updateImage(Long userId, Long id, String imageStr) throws Exception;
	
	// 创建或保存模型数据
	Long updateModalAndParams(Long userId, ProductModalFormVo entity, String[] avoidFieldsForUpdate) throws Exception;
	
	// 上下架
//	boolean toggleOnSale(Long userId, Long id) throws Exception;
}
