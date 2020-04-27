package org.xfh.mid.vo;

import org.xfh.mid.db.po.ModalSpecValue;

/**
 * 商品实例的属性关联表.   
 * 
 * 关联关系:  商品实例  vs  属性关联表  =  1 : n
 * 
 * @author cys
 *
 */
public class ProductSpecValueVo extends ModalSpecValue{

	protected Long productId;			// 商品实例id

	public Long getInstanceId() {
		return productId;
	}

	public void setInstanceId(Long instanceId) {
		this.productId = instanceId;
	}
}
