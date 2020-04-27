package org.xfh.mid.db.po;

/**
 * 这个表保存2类数据:
 * 
 * 1) 加入盘点的商品
 * 2) 盘点商品的结果
 * 
 * 是一个类似树形节点的结构
 * 
 * 正常的项和异常的项都放在同一个表. 
 * 异常的项会使用 errorCount
 * @author cys
 *
 */
public class UserCheckProduct {

	protected Long id; 							// id
	protected Long warehouseId; 				// 仓库id
	protected Long productId; 					// 产品id
	protected Long activityId; 					// 关联的盘点活动id

	protected Long parentId;					// 关联的 UserCheckProduct.id. 如果是空, 表示这条记录是父节点
	
	// ----- 当记录是盘点结果时
	protected String checkResultCat; 			// 盘点结果分类, CheckResultCats
	protected Integer quantity;				    // 检查时的数量
	
	// -- 异常的记录的字段
	protected String remark; 					// 备注
	protected String imageUrls; 				// 报损图片
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getCheckResultCat() {
		return checkResultCat;
	}
	public void setCheckResultCat(String checkResultCat) {
		this.checkResultCat = checkResultCat;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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


}
