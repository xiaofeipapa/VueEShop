package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 银行账户, 包括账户(如622292929) , 姓名, 开户行, 支行. 
 *
 */
public class BankAccount {

	public static final String CAT_CLIENT = "Client";
	public static final String CAT_ME = "Me";

	protected Long id;
	protected String userName;
	protected String account;				// 银行账号, 如 6226 0976 xxxx xxxx
	protected Long bankId;					// 银行
	protected Long branchId;				// 分行/支行
	protected String cat;					// 类型, 运营方公司账户或客户的账户

	protected Long createUserId;
	protected Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}					
	
}
