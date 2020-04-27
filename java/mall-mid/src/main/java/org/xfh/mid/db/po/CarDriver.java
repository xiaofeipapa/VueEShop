package org.xfh.mid.db.po;

/**
 * 司机信息
 * @author cys
 *
 */
public class CarDriver {

	protected Long id;
	protected String name;		// 姓名
	protected String phone;		// 手机号码
	protected Integer dataStatus;		// 数据状态
	protected Long warehouseId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
}
