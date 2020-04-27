package org.xfh.mid.db.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * 每一项商品可能都由若干个调拨信息组成. 
 * 
 * @author cys
 *
 */
public class UserOrderItemAllocInfo{

	@ApiModelProperty(value="id")
	protected Long id;			

	@ApiModelProperty(value="itemId")
	protected Long itemId;				// orderItem id	

	@ApiModelProperty(value="productId")
	protected Long productId;			// 商品id id	

	@ApiModelProperty(value="调拨类型, 见 AllocCats")
	protected String allocCat;
	
	@ApiModelProperty(value="仓库或供应商id")
	protected Long fromId;			
	
	@ApiModelProperty(value="调拨数量")
	protected Integer quantity;			
	
	@ApiModelProperty(value="操作人")
	protected Long backUserId;		
	
	@ApiModelProperty(value="操作时间")
	protected Date createTime;
	
	@ApiModelProperty(value="调拨单sn")
	protected String packageSn;

	@ApiModelProperty(value="接收仓库id")
	protected Long toWarehouseId;				// 接收仓库id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAllocCat() {
		return allocCat;
	}

	public void setAllocCat(String allocCat) {
		this.allocCat = allocCat;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getBackUserId() {
		return backUserId;
	}

	public void setBackUserId(Long backUserId) {
		this.backUserId = backUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPackageSn() {
		return packageSn;
	}

	public void setPackageSn(String packageSn) {
		this.packageSn = packageSn;
	}

	public Long getToWarehouseId() {
		return toWarehouseId;
	}

	public void setToWarehouseId(Long toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}

}
