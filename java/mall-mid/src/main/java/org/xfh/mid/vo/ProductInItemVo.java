package org.xfh.mid.vo;

import org.xfh.mid.db.po.ProductInItem;

public class ProductInItemVo extends ProductInItem{
	protected String createUserName;
	protected String warehouseName;
	protected String shipNo;
	protected String shipType;
	protected String damageCatLabel;
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getShipType() {
		return shipType;
	}
	public void setShipType(String shipType) {
		this.shipType = shipType;
	}
	public String getDamageCatLabel() {
		return damageCatLabel;
	}
	public void setDamageCatLabel(String damageCatLabel) {
		this.damageCatLabel = damageCatLabel;
	}
}
