package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 后台登录用户类
 * 
 * @author cys
 *
 */
public class BackUser {

	public static final int DATA_STATUS_NORMAL = 1;
	public static final int DATA_STATUS_DISABLE = 2;
	public static final int DATA_STATUS_DELETE = 3;
	
	private Long id;
		
	String password;	
	Long roleId;					// 角色id
	String realName;
	
	String phone;					// 手机号, 用作登录账号
	String email;
	String avatarUrl; 				// 缩略图头像url
	Integer dataStatus; 					// 状态 1表示正常 2表示封号 3表示删除
	
	Date createTime;		// 创建时间
	Long createUserId;		// 创建人
	Date lastLoginTime;		// 上次登录时间
	protected String loginIp;		// 上次登录ip
	
	// ===== ex 开头表示不需要持久化的字段
	String exRoleLabel;			// 权限名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getExRoleLabel() {
		return exRoleLabel;
	}

	public void setExRoleLabel(String exRoleLabel) {
		this.exRoleLabel = exRoleLabel;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
}
