package org.xfh.web.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 前台静态配置
 * 
 * @author cys
 *
 */
public class WebStaticConfig {

	@ApiModelProperty(value="线下汇款收款单位名称")
	String offlineBankUser;

	@ApiModelProperty(value="线下汇款收款单位开户行和支行信息")
	String offlineBankFullName;

	@ApiModelProperty(value="线下汇款收款单位账号信息")
	String offlineBankAccount;

	public String getOfflineBankUser() {
		return offlineBankUser;
	}

	public void setOfflineBankUser(String offlineBankUser) {
		this.offlineBankUser = offlineBankUser;
	}

	public String getOfflineBankFullName() {
		return offlineBankFullName;
	}

	public void setOfflineBankFullName(String offlineBankFullName) {
		this.offlineBankFullName = offlineBankFullName;
	}

	public String getOfflineBankAccount() {
		return offlineBankAccount;
	}

	public void setOfflineBankAccount(String offlineBankAccount) {
		this.offlineBankAccount = offlineBankAccount;
	}
	
	
	
}
