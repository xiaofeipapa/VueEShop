package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 最重要的表之一: 用户订单表. 
 * 
 * 当用户"提交订单"之后, 订单就会保存.(无论他支付与否) 
 * 
 * 暂定订单的保留有效期为7天. 
 * 
 * 注意!!!!! ===================
 * 在任何其他的表, 请使用 bizId 而不是 id. 这保证数据库的可迁移性和满足运营业务需求. 
 * @author cys
 *
 */
public class UserOrder extends AbstractUserOrder{
	
	protected String bizId;					// 业务id, 为了跨平台, 请使用这个bizId而不是主键id
	protected String parentBizId;			// 如果是拆分订单, 保存父订单		
	protected String dataStatus;			// 订单状态, 见 UserOrderStatus
	protected Long backUserId;						// 登录用户id
	protected Date createTime;				// 订单时间

	protected BigDecimal totalProductFee = new BigDecimal(0.00);			// 商品金额
	protected BigDecimal deliveryFee = new BigDecimal(0.00);				// 运费
	protected BigDecimal totalShouldPaid = new BigDecimal(0.00);			// 应付总额   

	protected String invoiceFlag;			// 是否开发票
	protected String invoiceCat;			// 发票类型, InvoiceCats	
	protected Long goodAddressId;					// 送货地址    DeliveryAddress表的id	
	protected Long invoiceAddressId;					// 发票寄送地址     DeliveryAddress表的id	
	protected String orderRemark;       				//备注说明
	
	protected String source;						// 订单来源, 见 OrderSources
	protected String lackChoice;					// 缺货选择, 见 LackChoices
	
	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getParentBizId() {
		return parentBizId;
	}

	public void setParentBizId(String parentBizId) {
		this.parentBizId = parentBizId;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getTotalProductFee() {
		return totalProductFee;
	}

	public void setTotalProductFee(BigDecimal totalProductFee) {
		this.totalProductFee = totalProductFee;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public BigDecimal getTotalShouldPaid() {
		return totalShouldPaid;
	}

	public void setTotalShouldPaid(BigDecimal totalShouldPaid) {
		this.totalShouldPaid = totalShouldPaid;
	}

	public String getInvoiceFlag() {
		return invoiceFlag;
	}

	public void setInvoiceFlag(String invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
	}

	public String getInvoiceCat() {
		return invoiceCat;
	}

	public void setInvoiceCat(String invoiceCat) {
		this.invoiceCat = invoiceCat;
	}

	public Long getGoodAddressId() {
		return goodAddressId;
	}

	public void setGoodAddressId(Long goodAddressId) {
		this.goodAddressId = goodAddressId;
	}

	public Long getInvoiceAddressId() {
		return invoiceAddressId;
	}

	public void setInvoiceAddressId(Long invoiceAddressId) {
		this.invoiceAddressId = invoiceAddressId;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public Long getBackUserId() {
		return backUserId;
	}

	public void setBackUserId(Long backUserId) {
		this.backUserId = backUserId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLackChoice() {
		return lackChoice;
	}

	public void setLackChoice(String lackChoice) {
		this.lackChoice = lackChoice;
	}
}
