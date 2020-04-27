package org.xfh.mid.vo;

import java.math.BigDecimal;
import java.util.List;

import org.xfh.mid.db.po.UserOrder;
import org.xfh.mid.db.po.UserOrderItem;

public class UserOrderVo extends UserOrder{

	String frontStatusLabel;		// 给前台用户看的状态
	String backStatusLabel;			// 给后台运营看的状态
	String sourceLabel;				// 来源中文
	String lackChoiceLabel;			// 缺货选择
	String buyerName;				// 购买用户/企业
	
	String payEndTime;			// 当需要用户付款时, 表示结束时间
	
	String clientName;
	BigDecimal creditAmount;
	BigDecimal useCreditAmount;
	String createTimeStr;
	
	// 子订单细节
	List<UserOrderItem> itemList;
	
	String deliveryUser;
	
	public String getFrontStatusLabel() {
		return frontStatusLabel;
	}
	public void setFrontStatusLabel(String frontStatusLabel) {
		this.frontStatusLabel = frontStatusLabel;
	}
	public String getBackStatusLabel() {
		return backStatusLabel;
	}
	public void setBackStatusLabel(String backStatusLabel) {
		this.backStatusLabel = backStatusLabel;
	}
	public String getPayEndTime() {
		return payEndTime;
	}
	public void setPayEndTime(String payEndTime) {
		this.payEndTime = payEndTime;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	public BigDecimal getUseCreditAmount() {
		return useCreditAmount;
	}
	public void setUseCreditAmount(BigDecimal useCreditAmount) {
		this.useCreditAmount = useCreditAmount;
	}
	public List<UserOrderItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<UserOrderItem> itemList) {
		this.itemList = itemList;
	}
	public String getSourceLabel() {
		return sourceLabel;
	}
	public void setSourceLabel(String sourceLabel) {
		this.sourceLabel = sourceLabel;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getLackChoiceLabel() {
		return lackChoiceLabel;
	}
	public void setLackChoiceLabel(String lackChoiceLabel) {
		this.lackChoiceLabel = lackChoiceLabel;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getDeliveryUser() {
		return deliveryUser;
	}
	public void setDeliveryUser(String deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	
}
