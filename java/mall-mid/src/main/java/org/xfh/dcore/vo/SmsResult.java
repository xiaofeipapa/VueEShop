package org.xfh.dcore.vo;

public class SmsResult {
	String code;		// 短信验证码code
	String error;		// 调用不成功时, 保留error
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
