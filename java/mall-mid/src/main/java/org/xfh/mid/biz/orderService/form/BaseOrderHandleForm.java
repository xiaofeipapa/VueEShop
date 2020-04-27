package org.xfh.mid.biz.orderService.form;

public class BaseOrderHandleForm {

	protected String bizId;							// 订单业务id
	
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	@Override
	public String toString() {
		return "BaseOrderHandleForm [bizId=" + bizId + "]";
	}

}
