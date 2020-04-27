package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 供应商订单表.  其中: 
 * 
 * 1) 所有商品的应付款从 SupplierOrderItem 汇总而来, 不用字段记录. 
 * 
 * @author cys
 *
 */
@ApiModel(value="供应商订单表", description="供应商订单表")
public class SupplierOrder extends AbstractSupplierOrder{

	@ApiModelProperty(value="数据状态, 见SupplierOrderStatus")
	protected String dataStatus;			// 订单状态, 见 SupplierOrderStatus

	@ApiModelProperty(value="接收仓库id")
	protected Long warehouseId;   

	@ApiModelProperty(value="商品金额+快递费用的总和")
	protected BigDecimal totalShouldPaid ;			// 商品金额

	@ApiModelProperty(value="快递费用")
	protected BigDecimal deliveryFee;				// 

	@ApiModelProperty(value="其他费用, 这是一个保留字段")
	protected BigDecimal otherFee;				// 

	@ApiModelProperty(value="是否导出过excel")
	protected boolean exportExcel;								

	@ApiModelProperty(value="备注, 可以是空")
	protected BigDecimal remark;				// 					

	@ApiModelProperty(value="付款凭据")
	protected String paidImageUrl;				

	@ApiModelProperty(value="付款用户id(财务人员id)")
	protected Long paidUserId;			

	@ApiModelProperty(value="付款时间")
	protected Date paidTime;			

	@ApiModelProperty(value="确认收货的操作者")
	protected Long receiveUserId;			

	@ApiModelProperty(value="确认收货时间")
	protected Date receiveTime;				

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public BigDecimal getTotalShouldPaid() {
		return totalShouldPaid;
	}

	public void setTotalShouldPaid(BigDecimal totalShouldPaid) {
		this.totalShouldPaid = totalShouldPaid;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getRemark() {
		return remark;
	}

	public void setRemark(BigDecimal remark) {
		this.remark = remark;
	}

	public boolean isExportExcel() {
		return exportExcel;
	}

	public void setExportExcel(boolean exportExcel) {
		this.exportExcel = exportExcel;
	}

	public String getPaidImageUrl() {
		return paidImageUrl;
	}

	public void setPaidImageUrl(String paidImageUrl) {
		this.paidImageUrl = paidImageUrl;
	}

	public Long getPaidUserId() {
		return paidUserId;
	}

	public void setPaidUserId(Long paidUserId) {
		this.paidUserId = paidUserId;
	}

	public Date getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Date paidTime) {
		this.paidTime = paidTime;
	}

	public Long getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
}
