package org.xfh.mid.vo.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台下单 / 生成订单
 * @author cys
 *
 */
public class MakeOrderForm {
	protected Long clientId;
	protected List<UserBuyProduct> products = new ArrayList<>();
	protected BigDecimal totalProductFee;
	protected BigDecimal deliveryFee;
	protected BigDecimal totalShouldPaid;
	protected Long goodAddressId;
	protected String invoiceAddressIdStr;
	protected String orderRemark;       				//备注说明
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public List<UserBuyProduct> getProducts() {
		return products;
	}
	public void setProducts(List<UserBuyProduct> products) {
		this.products = products;
	}
	public Long getGoodAddressId() {
		return goodAddressId;
	}
	public void setGoodAddressId(Long goodAddressId) {
		this.goodAddressId = goodAddressId;
	}
	public String getInvoiceAddressIdStr() {
		return invoiceAddressIdStr;
	}
	public void setInvoiceAddressIdStr(String invoiceAddressIdStr) {
		this.invoiceAddressIdStr = invoiceAddressIdStr;
	}
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public BigDecimal getTotalProductFee() {
		return totalProductFee;
	}
	public void setTotalProductFee(BigDecimal totalProductFee) {
		this.totalProductFee = totalProductFee;
	}
	public BigDecimal getTotalShouldPaid() {
		return totalShouldPaid;
	}
	public void setTotalShouldPaid(BigDecimal totalShouldPaid) {
		this.totalShouldPaid = totalShouldPaid;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
}	
