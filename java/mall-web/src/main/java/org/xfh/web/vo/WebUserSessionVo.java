package org.xfh.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录之后, 会将这个放到session.
 * 
 * @author cys
 *
 */
@ApiModel(value = "用户session信息", description = "用于保存用户信息到session")
public class WebUserSessionVo implements Serializable {

	private static final long serialVersionUID = 4436701773497079810L;

	public static final String SESSION_INFO_KEY = "WSSESS";

	@ApiModelProperty(value = "用户ID")
	Long userId;

	@ApiModelProperty(value = "真实名称")
	String realName;

	@ApiModelProperty(value = "用户名")
	String account;

	@ApiModelProperty(value = "登录ip")
	String loginIp;
	
	@ApiModelProperty(value = "登录时间")
	String loginTime;
	
	@ApiModelProperty(value = "头像地址")
	String avatarUrl;
	
	@ApiModelProperty(value = "手机号")
	String phone;
	
	@ApiModelProperty(value = "验证码")
	String vcode;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
