package org.xfh.mid.db.po;

import java.io.Serializable;

/**
 * 商品分类
 * @author cys
 *
 */
public class ProductCategory implements Serializable{

	private static final long serialVersionUID = -5930890395703450867L;

	// 分类层级, 如1,2,3级
	public static final int LEVEL_ROOT = 1;
	public static final int LEVEL_PARENT = 2;
	public static final int LEVEL_CHILD = 3;

	// 是否在前台显示
	public static final int SHOW_FRONT_YES = 1;
	public static final int SHOW_FRONT_NO = 2;
	
	Long id;
	String name;
	Long parentId;					// 上级分类id
//	Long rootId;					// 祖分类id
	Integer level;					// 几级分类
	Integer order;					// 同一个上级时候的排序
	Integer showFront;				// 是否显示在前台 SHOW_FRONT_YES
	
//	String exRootName;				// 根分类名字
//	String exParentName;			// 父分类名字
	
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
	public Integer getShowFront() {
		return showFront;
	}
	public void setShowFront(Integer showFront) {
		this.showFront = showFront;
	}

//	public String getExRootName() {
//		return exRootName;
//	}
//	public void setExRootName(String exRootName) {
//		this.exRootName = exRootName;
//	}
//	public String getExParentName() {
//		return exParentName;
//	}
//	public void setExParentName(String exParentName) {
//		this.exParentName = exParentName;
//	}
//	public Long getRootId() {
//		return rootId;
//	}
//	public void setRootId(Long rootId) {
//		this.rootId = rootId;
//	}

}
