package org.xfh.mid.vo;

import java.math.BigDecimal;

import org.xfh.mid.db.po.Buyer;

public class ClientBuyerVo extends Buyer{

//	List<ClientCreditItem> creditItems;
	
	String gradeLabel;			// 等级的中文
	BigDecimal useCreditAmount;			// 已使用额度
	BigDecimal remainAmount;			// 剩余额度
	
	Integer noUserIncomeCount;		// 未处理进账数目

//	public List<ClientCreditItem> getCreditItems() {
//		return creditItems;
//	}
//
//	public void setCreditItems(List<ClientCreditItem> creditItems) {
//		this.creditItems = creditItems;
//	}

	public String getGradeLabel() {
		return gradeLabel;
	}

	public void setGradeLabel(String gradeLabel) {
		this.gradeLabel = gradeLabel;
	}

	public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}

	public Integer getNoUserIncomeCount() {
		return noUserIncomeCount;
	}

	public void setNoUserIncomeCount(Integer noUserIncomeCount) {
		this.noUserIncomeCount = noUserIncomeCount;
	}

	public BigDecimal getUseCreditAmount() {
		return useCreditAmount;
	}

	public void setUseCreditAmount(BigDecimal useCreditAmount) {
		this.useCreditAmount = useCreditAmount;
	}
}
