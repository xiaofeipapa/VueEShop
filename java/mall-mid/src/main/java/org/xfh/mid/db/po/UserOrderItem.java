package org.xfh.mid.db.po;

import java.math.BigDecimal;

/**
 * 订单表里的每一项商品. 以product id 作为区分. 
 * 
 * @author cys
 *
 */
public class UserOrderItem extends AbstractUserOrder{

	protected String orderBizId;			// UserOrder.bizId
	protected String parentBizId;			// 如果是拆分订单, 保存父订单	
	protected Long productId;			// 商品id
	protected String modalTitle;		// 模型名称
	protected Long modalId;				// 模型id
	protected String specValueString;				// 属性值的逗号分隔字符串. 如 蓝色,M码
	protected String sku;
	
	protected String imagePath;			// 商品第1张图片
	protected Long brandId;				// 品牌id
	
	protected BigDecimal marketPrice;				// 原价
	protected BigDecimal retailPrice;					// 实际价格
	protected Integer quantity;					// 数量
	protected BigDecimal totalProductFee;  		//商品总金额
	public String getOrderBizId() {
		return orderBizId;
	}
	public void setOrderBizId(String orderBizId) {
		this.orderBizId = orderBizId;
	}
	public String getParentBizId() {
		return parentBizId;
	}
	public void setParentBizId(String parentBizId) {
		this.parentBizId = parentBizId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getModalTitle() {
		return modalTitle;
	}
	public void setModalTitle(String modalTitle) {
		this.modalTitle = modalTitle;
	}
	public Long getModalId() {
		return modalId;
	}
	public void setModalId(Long modalId) {
		this.modalId = modalId;
	}
	public String getSpecValueString() {
		return specValueString;
	}
	public void setSpecValueString(String specValueString) {
		this.specValueString = specValueString;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getTotalProductFee() {
		return totalProductFee;
	}
	public void setTotalProductFee(BigDecimal totalProductFee) {
		this.totalProductFee = totalProductFee;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
}
