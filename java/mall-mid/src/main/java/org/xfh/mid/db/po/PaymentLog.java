package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付记录表
 * 
 * @author cys
 *
 */
public class PaymentLog  extends AbstractUserOrder{

	protected String orderBizId;  	// 订单号
	protected String succFlag;		// Y 成功 N失败, 如果失败在 failReason说明
	protected String failReason;	// 失败原因	
	
	protected BigDecimal fee;		//订单金额,单位为元
	protected Date payTime;			//交易完成时间
	protected String PayCat; 		// PayCats 支付类型

	protected String remark;  		// 备注说明
	protected String imageUrls;  	// 沟通图片(例如后台下单支付的时候) 
	protected Long backUserId;		// 如果是后台代支付, 保存后台用户id

	public String getOrderBizId() {
		return orderBizId;
	}
	public void setOrderBizId(String orderBizId) {
		this.orderBizId = orderBizId;
	}
	public String getSuccFlag() {
		return succFlag;
	}
	public void setSuccFlag(String succFlag) {
		this.succFlag = succFlag;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getPayCat() {
		return PayCat;
	}
	public void setPayCat(String payCat) {
		PayCat = payCat;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}
	public Long getBackUserId() {
		return backUserId;
	}
	public void setBackUserId(Long backUserId) {
		this.backUserId = backUserId;
	}
	@Override
	public String toString() {
		return "PaymentLog [id=" + id + ", orderBizId=" + orderBizId + ", succFlag=" + succFlag + ", failReason="
				+ failReason + ", fee=" + fee + ", payTime=" + payTime + ", PayCat=" + PayCat + ", remark=" + remark
				+ ", imageUrls=" + imageUrls + ", backUserId=" + backUserId + "]";
	}
		


}
