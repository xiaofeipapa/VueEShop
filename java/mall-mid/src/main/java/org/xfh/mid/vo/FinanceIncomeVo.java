package org.xfh.mid.vo;

import org.xfh.mid.db.po.FinanceIncome;

public class FinanceIncomeVo extends FinanceIncome{

    protected String fromBankName;
    protected String fromBranchName;		// 发起银行支行
    protected String toBankName;
    protected String toBranchName;			//收款银行支行
    protected String createUserName;
    
	public String getFromBankName() {
		return fromBankName;
	}
	public void setFromBankName(String fromBankName) {
		this.fromBankName = fromBankName;
	}
	public String getFromBranchName() {
		return fromBranchName;
	}
	public void setFromBranchName(String fromBranchName) {
		this.fromBranchName = fromBranchName;
	}
	public String getToBankName() {
		return toBankName;
	}
	public void setToBankName(String toBankName) {
		this.toBankName = toBankName;
	}
	public String getToBranchName() {
		return toBranchName;
	}
	public void setToBranchName(String toBranchName) {
		this.toBranchName = toBranchName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}		
        
}
