package org.xfh.mid.vo;

import org.xfh.mid.db.po.PaymentLog;

public class PaymentLogVo extends PaymentLog{

	String backUserName;	// 后台用户中文
	String payCatLabel;		// 支付中文
	String succLabel;		// 成功失败中文
	String payTimeStr;		
	
	public String getBackUserName() {
		return backUserName;
	}
	public void setBackUserName(String backUserName) {
		this.backUserName = backUserName;
	}
	public String getPayCatLabel() {
		return payCatLabel;
	}
	public void setPayCatLabel(String payCatLabel) {
		this.payCatLabel = payCatLabel;
	}
	public String getSuccLabel() {
		return succLabel;
	}
	public void setSuccLabel(String succLabel) {
		this.succLabel = succLabel;
	}
	public String getPayTimeStr() {
		return payTimeStr;
	}
	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}
}
