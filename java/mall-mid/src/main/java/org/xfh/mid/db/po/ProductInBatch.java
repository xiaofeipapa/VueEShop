package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 商品入库批次
 * 
 * @author cys
 *
 */
public class ProductInBatch {

	protected Long id; 							// id
	protected Long warehouseId;					// 仓库id
	
	protected String LogisticsCat;					// 物流类型, 见 LogisticsCats
	protected String shipNo;					// 物流单号(自有物流是发车号)
	protected Long carId;						// 车辆id
	protected Long carDriverId;					// 司机id

	protected String sendUser;						//  发件人
	protected String sendUserPhone;					// 发件电话
	protected String sendUserPostal;				// 发件人邮编
	protected String sendUserDistrict;				// 发件人省市区行政区划
	protected String sendUserFullDistrict;			// 省市区中文
	protected String sendUserAddress;				// 发件人详细地址
	
	protected String remark;					// 额外备注
				
	protected Date createTime;					// 创建时间
	protected Long createUserId;				// 创建人
	protected String imageUrls;					// 物流单据图片
	protected String dataStatus;				// ProductInStatus
	
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

	public String getLogisticsCat() {
		return LogisticsCat;
	}
	public void setLogisticsCat(String logisticsCat) {
		LogisticsCat = logisticsCat;
	}
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public Long getCarDriverId() {
		return carDriverId;
	}
	public void setCarDriverId(Long carDriverId) {
		this.carDriverId = carDriverId;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getSendUserPhone() {
		return sendUserPhone;
	}
	public void setSendUserPhone(String sendUserPhone) {
		this.sendUserPhone = sendUserPhone;
	}
	public String getSendUserPostal() {
		return sendUserPostal;
	}
	public void setSendUserPostal(String sendUserPostal) {
		this.sendUserPostal = sendUserPostal;
	}
	public String getSendUserDistrict() {
		return sendUserDistrict;
	}
	public void setSendUserDistrict(String sendUserDistrict) {
		this.sendUserDistrict = sendUserDistrict;
	}
	public String getSendUserFullDistrict() {
		return sendUserFullDistrict;
	}
	public void setSendUserFullDistrict(String sendUserFullDistrict) {
		this.sendUserFullDistrict = sendUserFullDistrict;
	}
	public String getSendUserAddress() {
		return sendUserAddress;
	}
	public void setSendUserAddress(String sendUserAddress) {
		this.sendUserAddress = sendUserAddress;
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
	public String getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
}
