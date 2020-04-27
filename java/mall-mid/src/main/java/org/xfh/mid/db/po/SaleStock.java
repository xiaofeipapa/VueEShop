package org.xfh.mid.db.po;

/**
 * 
 * 真实世界的商品库存表. 仓库增加了库存之后, 都会汇总到这表.  
 * 独立出来的目的是方便购买业务操作库存, 不用考虑具体哪个仓库.
 *  
 * 所有涉及库存的订单处理完之后,   多仓库的warehouseStock 相加应该等于 SaleStock
 * 
 * @author cys
 *
 */
public class SaleStock {

	protected Long productId; 						// 商品id
	protected Integer stock;					// 库存
	protected Long version;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
}
