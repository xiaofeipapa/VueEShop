package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 供应商订单项表. 
 * 
 * @author cys
 *
 */
@ApiModel(value="供应商订单项表", description="供应商订单项表")
public class SupplierOrderItem extends AbstractSupplierOrder{

	@ApiModelProperty(value="关联SupplierOrder表的主键id")
	protected Long orderId;			// SupplierOrder.id 

	@ApiModelProperty(value="商品id")
	protected Long productId;   

	@ApiModelProperty(value="模型名称")
	protected String modalTitle;		// 模型名称

	@ApiModelProperty(value="属性值的逗号分隔字符串. 如 蓝色,M码")
	protected String specValueString;				// 属性值的逗号分隔字符串. 如 蓝色,M码

	@ApiModelProperty(value="该商品金额=采购单价*采购数量")
	protected BigDecimal totalProductFee;			// 商品金额

	@ApiModelProperty(value="零售价")
	protected BigDecimal retailPrice;				// 原价

	@ApiModelProperty(value="采购单价")
	protected BigDecimal supplierPrice;					// 实际价格

	@ApiModelProperty(value="采购数量")
	protected Integer quantity;					// 数量		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

	public String getSpecValueString() {
		return specValueString;
	}

	public void setSpecValueString(String specValueString) {
		this.specValueString = specValueString;
	}

	public BigDecimal getTotalProductFee() {
		return totalProductFee;
	}

	public void setTotalProductFee(BigDecimal totalProductFee) {
		this.totalProductFee = totalProductFee;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public BigDecimal getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(BigDecimal supplierPrice) {
		this.supplierPrice = supplierPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
