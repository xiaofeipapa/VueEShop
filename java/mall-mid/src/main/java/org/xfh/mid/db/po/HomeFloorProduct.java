package org.xfh.mid.db.po;

/**
 * 前台网站首页楼层
 * 
 * @author cys
 *
 */
public class HomeFloorProduct{

	protected Long floorId;
	protected Long productId;
	protected Integer order;
	public Long getFloorId() {
		return floorId;
	}
	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
