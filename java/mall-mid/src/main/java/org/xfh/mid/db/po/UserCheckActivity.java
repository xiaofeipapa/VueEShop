package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 
 * 用户盘点活动. 
 * 
 * 1 个盘点活动对应多个盘点商品, 1个盘点商品对应多个盘点结果. 
 * 
 * @author cys
 *
 */
public class UserCheckActivity {

	protected Long id; 							// id
	protected Long warehouseId; 				// 仓库id
	protected Long checkUserId;					// 盘点人
	protected Date checkTime;					// 盘点时间

	protected Date createTime;					// 创建时间
	protected Long createUserId;				// 创建人
	protected String remark;					// 备注
	protected String dataStatus;				// 数据状态, CheckActivityStatus
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Long getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(Long checkUserId) {
		this.checkUserId = checkUserId;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
}
