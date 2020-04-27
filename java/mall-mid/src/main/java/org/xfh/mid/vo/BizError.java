package org.xfh.mid.vo;

public class BizError {
	Long id;
	String msg;
	
	public BizError() {
		super();
	}
	public BizError(Long id, String msg) {
		super();
		this.id = id;
		this.msg = msg;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
