package org.xfh.mid.vo.form;

import java.util.List;

import org.xfh.mid.db.po.ProductInBatch;
import org.xfh.mid.db.po.ProductInItem;

/**
 * 新增入库表单
 * 
 * @author cys
 *
 */
public class ProductInAddStockForm extends ProductInBatch{
		
	List<ProductInItem> products;		// 关联的商品

	public List<ProductInItem> getProducts() {
		return products;
	}

	public void setProducts(List<ProductInItem> products) {
		this.products = products;
	}

}
