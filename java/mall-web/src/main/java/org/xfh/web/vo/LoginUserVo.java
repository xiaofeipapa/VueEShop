package org.xfh.web.vo;

/**
 * 不要暴露id, 电话等关键信息
 * @author cys
 *
 */
public class LoginUserVo {
	String account;
	String userCat;
		
	public LoginUserVo(String account, String userCat) {
		super();
		this.account = account;
		this.userCat = userCat;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserCat() {
		return userCat;
	}
	public void setUserCat(String userCat) {
		this.userCat = userCat;
	}
	
}
