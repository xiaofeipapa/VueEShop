package org.xfh.mid.db.po;

import java.math.BigDecimal;

/**
 * 供应商-产品关联表
 * 
 * 供应商能够提供多个产品信息, 每一个产品信息有唯一的购买价格和批发价格. 
 * 
 * @author cys
 *
 */
public class SupplierProduct {

	protected Long productId;				// 
	protected Long supplierId;				// 
	protected BigDecimal onePrice;				// 供应商提供给平台的采购零售价
	protected BigDecimal batchPrice;			// 供应商提供给平台的采购批发价(批发价是有条件的) 
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public BigDecimal getOnePrice() {
		return onePrice;
	}
	public void setOnePrice(BigDecimal onePrice) {
		this.onePrice = onePrice;
	}
	public BigDecimal getBatchPrice() {
		return batchPrice;
	}
	public void setBatchPrice(BigDecimal batchPrice) {
		this.batchPrice = batchPrice;
	}
}
