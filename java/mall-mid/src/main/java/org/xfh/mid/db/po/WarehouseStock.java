package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 
 * 仓库库存表. 

	每个仓库有唯一的库存记录. 
	例如客户有一个仓库, 那么这个表只有1条记录 
	如果客户有n个仓库, 那么有n个记录. 
 * 
 * @author cys
 *
 */
public class WarehouseStock {

	protected Long id; 								// id
	protected Long productId; 						// 商品id
	protected Long warehouseId; 					// 仓库id
	protected Integer stock;						// 库存

	protected Date updateTime;						// 更新时间
	protected Long updateUserId;					// 更新人
	protected String remark;						// 备注
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}	
	
}
