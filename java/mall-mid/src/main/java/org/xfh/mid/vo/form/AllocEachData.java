package org.xfh.mid.vo.form;

public class AllocEachData {

	Long fromId;
	String allocCat;
	String fromName;
	int quantity;
	Long toWarehouseId;
	
	public Long getFromId() {
		return fromId;
	}
	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}
	public String getAllocCat() {
		return allocCat;
	}
	public void setAllocCat(String allocCat) {
		this.allocCat = allocCat;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Long getToWarehouseId() {
		return toWarehouseId;
	}
	public void setToWarehouseId(Long toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}
}
