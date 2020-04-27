package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

public class ProductInBatchDetailView extends ProductInBatchVo{

	
	List<ProductInItemVo> damageProducts = new ArrayList<>();		// 报损商品
	String createTimeStr;

	public List<ProductInItemVo> getDamageProducts() {
		return damageProducts;
	}
	public void setDamageProducts(List<ProductInItemVo> damageProducts) {
		this.damageProducts = damageProducts;
	}
	
	public void addProduct(ProductInItemVo prod) {
		this.products.add(prod);
	}
	public void addDamage(ProductInItemVo prod) {
		this.damageProducts.add(prod);
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}
