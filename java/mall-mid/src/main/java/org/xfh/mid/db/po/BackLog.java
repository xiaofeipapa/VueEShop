package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 后台log
 * 
 * @author cys
 *
 */
public class BackLog {

	// 修改核心数据的业务
	public static final String BIZ_PRODUCT_IN_ADD_STOCK = "商品入库";
	public static final String BIZ_PRODUCT_CHECK_ACTIVITY = "商品盘点";
	
	private Long id;
		
	String detail;			// log 细节	
	Date createTime;		// 创建时间
	Long createUserId;		// 创建人
	String phone;			// 用户手机号
	String realName;		// 用户真名
	String loginIp;			// 登录ip
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	
}
