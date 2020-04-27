package org.xfh.mid.db.po;

/**
 * 前台网站首页广告配置. 面包屑和居中的放一起
 * 
 * @author cys
 *
 */
public class FrontHomeAdConfig{

	public static final String CAT_BREAD_CUMP = "Bread";		// 首页屑导航
	public static final String CAT_HORI = "Hori";				// 横幅广告
	
	protected Long id;
	protected String name;			// 标题
	protected String cat;			// 分类
	protected String imageUrl;		// 是否有图片
	protected Integer order;
	protected boolean showFront;				// 是否显示在前台 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
