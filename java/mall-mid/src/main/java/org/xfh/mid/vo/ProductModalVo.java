package org.xfh.mid.vo;

import org.xfh.mid.db.po.ProductModal;

public class ProductModalVo extends ProductModal{

	protected Integer productCount;			// 已关联的商品实例个数

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
}
