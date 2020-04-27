package org.xfh.mid.vo.form;

import java.util.List;

import org.xfh.mid.db.po.UserCheckProduct;

/**
 * 盘点 / 增加盘点商品
 * @author cys
 *
 */
public class CheckActivityAddProductForm {
	protected Long activityId; 					// 盘点活动id
	protected Long warehouseId; 				// 仓库id
	protected List<Long> productIds;
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public List<Long> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}


}
