package org.xfh.mid.vo;

import java.util.List;

import org.xfh.mid.db.po.DeliverAddress;
import org.xfh.mid.db.po.UserOrderItemAllocInfoPackage;

/**
 * 
 * @author cys
 *
 */
public class UserOrderItemAllocInfoPackageVo extends UserOrderItemAllocInfoPackage{
	String warehouseName;		// 仓库名字
	List<UserOrderItemAllocInfoVo> mappingList;
	String createTimeStr;
	String createUserName;

	String finishUserName;
	String finishTimeStr;
	
	DeliverAddress addressData;
	String carPlate;
	String driverName;
	String driverPhone;
	String outLogisticsName;

	String logisticsCatLabel;
	String outLogicsticsPayCatLabel;
	

	public List<UserOrderItemAllocInfoVo> getMappingList() {
		return mappingList;
	}

	public void setMappingList(List<UserOrderItemAllocInfoVo> mappingList) {
		this.mappingList = mappingList;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public DeliverAddress getAddressData() {
		return addressData;
	}

	public void setAddressData(DeliverAddress addressData) {
		this.addressData = addressData;
	}

	public String getFinishUserName() {
		return finishUserName;
	}

	public void setFinishUserName(String finishUserName) {
		this.finishUserName = finishUserName;
	}

	public String getFinishTimeStr() {
		return finishTimeStr;
	}

	public void setFinishTimeStr(String finishTimeStr) {
		this.finishTimeStr = finishTimeStr;
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

	public String getOutLogisticsName() {
		return outLogisticsName;
	}

	public void setOutLogisticsName(String outLogisticsName) {
		this.outLogisticsName = outLogisticsName;
	}

	public String getLogisticsCatLabel() {
		return logisticsCatLabel;
	}

	public void setLogisticsCatLabel(String logisticsCatLabel) {
		this.logisticsCatLabel = logisticsCatLabel;
	}

	public String getOutLogicsticsPayCatLabel() {
		return outLogicsticsPayCatLabel;
	}

	public void setOutLogicsticsPayCatLabel(String outLogicsticsPayCatLabel) {
		this.outLogicsticsPayCatLabel = outLogicsticsPayCatLabel;
	}

}
