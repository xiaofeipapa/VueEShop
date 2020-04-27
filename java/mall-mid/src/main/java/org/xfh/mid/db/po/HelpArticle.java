package org.xfh.mid.db.po;

import java.io.Serializable;

/**
 * 前台帮助说明文章
 * 
 * @author cys
 *
 */
public class HelpArticle implements Serializable{

	private static final long serialVersionUID = -5930890395703450867L;

	// 分类层级, 如1,2,3级
	public static final int LEVEL_CAT = 1; 			// 分类
	public static final int LEVEL_ARTICLE = 2; 		// 文章
	
	protected Long id;
	protected String name;
	protected Long parentId;					// 上级分类id
	protected Integer level;					// 几级分类
	protected Integer order;					// 同一个上级时候的排序
	
	protected String content;					// 文章内容
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isShowFront() {
		return showFront;
	}
	public void setShowFront(boolean showFront) {
		this.showFront = showFront;
	}


}
