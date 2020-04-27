package org.xfh.mid.db.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public abstract class AbstractSupplierOrder {

	@ApiModelProperty(value="id")
	protected Long id;

	@ApiModelProperty(value="操作用户id")
	protected Long createUserId;			// 登录用户id

	@ApiModelProperty(value="操作时间")
	protected Date createTime;				// 订单时间

	@ApiModelProperty(value="供应商id")
	protected Long supplierId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}   
}
