package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.HomeFloor;

public class HomeFloorVo extends HomeFloor{
	
	int productCount;			// 关联商品数量
	List<ProductVo> products = new ArrayList<>();
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public List<ProductVo> getProducts() {
		return products;
	}
	public void setProducts(List<ProductVo> products) {
		this.products = products;
	}
}
