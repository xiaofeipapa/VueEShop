package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 关联线下付款(FinanceIncome) 和 ClientCreditItem
 * 
 * 比较麻烦的是这种情况, 假设欠款3笔,  进账2 笔, 如下: 
 * 
 * 1000 				1300
 * 500 					1100
 * 900					
 * 
 * 左右正好相等. 但并不是简单的一对一或者多对多关系.  而是"总数相等"的关系. 
 * 
 * 所以将id合并放到一个字段, 其中: 
 * 
 * creditIds: 保存 ClientCreditItem 的id字符串, 形如 111,222,333
 * incomeIds: 保存 FinanceIncome 的id字符串, 形如 555,666,777
 * 
 * 
 * @author cys
 *
 */
public class CreditOfflineRepayInfo {
	
	protected Long id;
	protected String creditIds;		// 用,分隔的ClientCreditItem.id
	protected String incomeIds;		// 用,分隔的FinanceIncome.id
	protected BigDecimal handleAmount;		// 总处理金额

	protected Long createUserId;
	protected Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreditIds() {
		return creditIds;
	}
	public void setCreditIds(String creditIds) {
		this.creditIds = creditIds;
	}
	public String getIncomeIds() {
		return incomeIds;
	}
	public void setIncomeIds(String incomeIds) {
		this.incomeIds = incomeIds;
	}
	public BigDecimal getHandleAmount() {
		return handleAmount;
	}
	public void setHandleAmount(BigDecimal handleAmount) {
		this.handleAmount = handleAmount;
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

}
