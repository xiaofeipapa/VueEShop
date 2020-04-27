package org.xfh.mid.vo;

/**
 * 订单处理结果
 * @author cys
 *
 */
public class OrderHandleResult{

	String bizId;				// 订单id
	String succFlag;			// Y 成功 N 失败
	String msg;					// N 时的附加说明
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	public String getSuccFlag() {
		return succFlag;
	}
	public void setSuccFlag(String succFlag) {
		this.succFlag = succFlag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
