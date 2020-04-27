package org.xfh.mid.db.po;

/**
 * 前台网站底部的其他链接
 * 
 * @author cys
 *
 */
public class BottomLink{
	
	protected Long id;
	protected String name;
	protected String link;			// 如果不为空表示跳转, 否则表示文章
	protected String imageUrl;		// 是否有图片
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isShowFront() {
		return showFront;
	}
	public void setShowFront(boolean showFront) {
		this.showFront = showFront;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
