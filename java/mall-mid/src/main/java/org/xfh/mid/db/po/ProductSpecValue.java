package org.xfh.mid.db.po;

/**
 * 商品 和规格属性值的关联表. 
 * 
 * @author cys
 *
 */
public class ProductSpecValue{

	protected Long productId; 							
	protected Long specValueId;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getSpecValueId() {
		return specValueId;
	}
	public void setSpecValueId(Long specValueId) {
		this.specValueId = specValueId;
	}
}
