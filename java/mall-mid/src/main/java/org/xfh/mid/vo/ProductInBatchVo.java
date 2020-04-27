package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.ProductInBatch;

public class ProductInBatchVo extends ProductInBatch{

	Integer inCount;					// 商品入库种类个数
	Integer damageCount;				// 商品报损种类个数
	protected String createUserName;
	protected String sendUserDistrictLabel;
	protected String warehouseName;
	protected List<ProductInItemVo> products = new ArrayList<>();
	
	protected String carPlate;
	String driverName;
	String driverPhone;
	
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Integer getDamageCount() {
		return damageCount;
	}

	public void setDamageCount(Integer damageCount) {
		this.damageCount = damageCount;
	}

	public List<ProductInItemVo> getProducts() {
		return products;
	}

	public void setProducts(List<ProductInItemVo> products) {
		this.products = products;
	}

	public Integer getInCount() {
		return inCount;
	}

	public void setInCount(Integer inCount) {
		this.inCount = inCount;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getSendUserDistrictLabel() {
		return sendUserDistrictLabel;
	}

	public void setSendUserDistrictLabel(String sendUserDistrictLabel) {
		this.sendUserDistrictLabel = sendUserDistrictLabel;
	}
	
}
