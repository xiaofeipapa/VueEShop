package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 调拨仓库单
 * @author cys
 *
 */
public class UserOrderItemAllocInfoPackage {
	protected Long id;
	protected String sn;				// 调拨单sn
	protected String orderBizId;
	protected Long warehouseId;
	protected Long createUserId;
	
	protected Date createTime;
	protected String dataStatus;		// 见 AllocInfoPackageStatus

	// ============= 快递单发出信息
	protected String logisticsCat;					// 物流类型		
	protected Long outLogicsticsId;					// 外部物流id
	protected String outLogicsticsNo;				// 外部物流单据
	protected String outLogicsticsImage;			// 外部物流单据拍照
	protected BigDecimal outLogicsticsFee;			// 外部物流收费
	protected String outLogicsticsPayCat;			// 现金/结算
	protected Long carId;							// 内部物流车辆id
	protected Long carDriverId;						// 内部物流司机id

	protected Long finishUserId;					// 处理人id
	protected Date finishTime;						// 处理时间

	// ============= 签收信息
	protected String receiveImage;					// 凭证
	protected String receiveRemark;			// 备注
	protected Long receiveCreateUserId;
	protected Date receiveCreateTime;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getOrderBizId() {
		return orderBizId;
	}
	public void setOrderBizId(String orderBizId) {
		this.orderBizId = orderBizId;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
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
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getLogisticsCat() {
		return logisticsCat;
	}
	public void setLogisticsCat(String logisticsCat) {
		this.logisticsCat = logisticsCat;
	}
	public Long getOutLogicsticsId() {
		return outLogicsticsId;
	}
	public void setOutLogicsticsId(Long outLogicsticsId) {
		this.outLogicsticsId = outLogicsticsId;
	}
	public String getOutLogicsticsNo() {
		return outLogicsticsNo;
	}
	public void setOutLogicsticsNo(String outLogicsticsNo) {
		this.outLogicsticsNo = outLogicsticsNo;
	}
	public String getOutLogicsticsImage() {
		return outLogicsticsImage;
	}
	public void setOutLogicsticsImage(String outLogicsticsImage) {
		this.outLogicsticsImage = outLogicsticsImage;
	}
	public BigDecimal getOutLogicsticsFee() {
		return outLogicsticsFee;
	}
	public void setOutLogicsticsFee(BigDecimal outLogicsticsFee) {
		this.outLogicsticsFee = outLogicsticsFee;
	}
	public String getOutLogicsticsPayCat() {
		return outLogicsticsPayCat;
	}
	public void setOutLogicsticsPayCat(String outLogicsticsPayCat) {
		this.outLogicsticsPayCat = outLogicsticsPayCat;
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
	public Long getFinishUserId() {
		return finishUserId;
	}
	public void setFinishUserId(Long finishUserId) {
		this.finishUserId = finishUserId;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getReceiveImage() {
		return receiveImage;
	}
	public void setReceiveImage(String receiveImage) {
		this.receiveImage = receiveImage;
	}

	public String getReceiveRemark() {
		return receiveRemark;
	}
	public void setReceiveRemark(String receiveRemark) {
		this.receiveRemark = receiveRemark;
	}
	public Long getReceiveCreateUserId() {
		return receiveCreateUserId;
	}
	public void setReceiveCreateUserId(Long receiveCreateUserId) {
		this.receiveCreateUserId = receiveCreateUserId;
	}
	public Date getReceiveCreateTime() {
		return receiveCreateTime;
	}
	public void setReceiveCreateTime(Date receiveCreateTime) {
		this.receiveCreateTime = receiveCreateTime;
	}
}
