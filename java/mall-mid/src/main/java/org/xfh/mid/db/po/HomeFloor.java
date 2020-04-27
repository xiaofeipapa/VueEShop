package org.xfh.mid.db.po;

/**
 * 前台网站首页楼层关联的商品
 * 
 * @author cys
 *
 */
public class HomeFloor{

	protected Long id;
	protected String name;
	protected Integer order;
	protected boolean showFront;				// 是否显示在前台
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public boolean isShowFront() {
		return showFront;
	}
	public void setShowFront(boolean showFront) {
		this.showFront = showFront;
	}


}
