package org.xfh.mid.vo;

import org.xfh.mid.db.po.BankAccount;

public class BankAccountVo extends BankAccount{

	protected String bankName;
	protected String branchName;
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
