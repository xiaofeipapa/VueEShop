package org.xfh.mid.vo;

import java.math.BigDecimal;

import org.xfh.mid.vo.web.WebStaticConfig;

/**
 * 待支付的时候, 带倒计时信息
 * @author cys
 *
 */
public class PayCountdownVo {
	String endTime;		// 结束日期
	String bizId;
	BigDecimal totalShouldPaid = new BigDecimal(0.00);			// 应付总额
	WebStaticConfig config;	
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	public BigDecimal getTotalShouldPaid() {
		return totalShouldPaid;
	}
	public void setTotalShouldPaid(BigDecimal totalShouldPaid) {
		this.totalShouldPaid = totalShouldPaid;
	}
	public WebStaticConfig getConfig() {
		return config;
	}
	public void setConfig(WebStaticConfig config) {
		this.config = config;
	}
	
}	
