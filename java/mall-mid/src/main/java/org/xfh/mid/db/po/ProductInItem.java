package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 商品入库批次/盘点里的每一个细项. 有3个可能的插入途径: 
 * 
 *  1) 入库管理插入商品, 此时存在batchId
 *  2) 入库管理增加报损, 此时存在batchId
 *  3) 盘点商品更新库存, 此时不存在batchId
 *  4) 盘点商品增加报损, 此时不存在batchId
 * 
 * @see ProductInBatch 
 * 
 * @author cys
 *
 */
public class ProductInItem {

	protected Long id; 								// id
	protected Long batchId; 						// 关联 ProductInBatch.id
	protected String action;						// 行为, 见 InItemActions

	protected Long warehouseId;						// 仓库id, 冗余字段加快查询
	protected Long productId;						// 商品id	
	protected String modalTitle;					// 商品标题, 冗余字段加快查询
	protected String specValueString;				// 商品规格, 冗余字段加快查询
	protected int quantity;							// 增加的值

	protected String damageCat;						// 当入库类型是报损时, 保存报损类型
	protected String imageUrls;						// 当入库类型是报损时, 保存图片的地址 
	protected String remark;						// 备注
				
	protected Date createTime;					// 创建时间
	protected Long createUserId;				// 创建人
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	public String getModalTitle() {
		return modalTitle;
	}
	public void setModalTitle(String modalTitle) {
		this.modalTitle = modalTitle;
	}
	public String getSpecValueString() {
		return specValueString;
	}
	public void setSpecValueString(String specValueString) {
		this.specValueString = specValueString;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDamageCat() {
		return damageCat;
	}
	public void setDamageCat(String damageCat) {
		this.damageCat = damageCat;
	}
	public String getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
