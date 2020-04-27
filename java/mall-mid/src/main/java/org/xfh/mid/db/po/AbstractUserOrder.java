package org.xfh.mid.db.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="抽象用户订单信息", description="抽象用户订单信息")
public abstract class AbstractUserOrder {

	@ApiModelProperty(value="id")
	protected Long id;
	
	@ApiModelProperty(value="购买者id")
	protected Long buyUserId;							// 前台用户id	

	@ApiModelProperty(value="购买者类型,企业用户还是个人用户")
	protected String buyUserCat;						// 用户类型
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBuyUserId() {
		return buyUserId;
	}
	public void setBuyUserId(Long buyUserId) {
		this.buyUserId = buyUserId;
	}
	public String getBuyUserCat() {
		return buyUserCat;
	}
	public void setBuyUserCat(String buyUserCat) {
		this.buyUserCat = buyUserCat;
	}
	
	
}
