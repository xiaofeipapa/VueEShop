package org.xfh.mid.vo;

import java.math.BigDecimal;

/**
 * 客户欠款
 * @author cys
 *
 */
public class ClientDebitVo{
	Long clientId;
	BigDecimal useCreditAmount;
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public BigDecimal getUseCreditAmount() {
		return useCreditAmount;
	}
	public void setUseCreditAmount(BigDecimal useCreditAmount) {
		this.useCreditAmount = useCreditAmount;
	}
}
