package org.xfh.mid.vo.form;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.SupplierProduct;

/**
 * 商品列表 / 批量更新表单
 * @author cys
 *
 */
public class ProductBatchSetSupplierPriceForm {
	protected Long productId;
	protected List<SupplierProduct> spList = new ArrayList<>();
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public List<SupplierProduct> getSpList() {
		return spList;
	}
	public void setSpList(List<SupplierProduct> spList) {
		this.spList = spList;
	}
}
