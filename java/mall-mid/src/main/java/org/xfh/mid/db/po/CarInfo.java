package org.xfh.mid.db.po;

/**
 * 车辆信息
 * @author cys
 *
 */
public class CarInfo {

	protected Long id;
	protected Long warehouseId;
	protected String plate;		// 车牌号
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
}
