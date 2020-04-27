package org.xfh.dcore.vo;

import java.io.Serializable;

public class AjaxResultVo implements Serializable{

	private static final long serialVersionUID = -1691725645750389254L;

	public static final String CODE_SESSION_EXPIRE = "CODE_SESSION_EXPIRE";		// session 超时
	public static final String CODE_BIZ_EX = "CODE_BIZ_EX";		// 逻辑异常

	protected String errorCode;				// 如果出错 可以通过errorCode 定制各种出错情况
	protected String error;					// 如果error不为空,则表示有错误. 为空表示正常
	protected Object data ;//返回数据结果集
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}	

}
