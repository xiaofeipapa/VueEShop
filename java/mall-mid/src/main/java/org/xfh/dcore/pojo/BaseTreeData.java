package org.xfh.dcore.pojo;

public abstract class BaseTreeData {

	protected Long id;
	protected String name;
	protected Long parentId;					// 上级分类id
	protected Integer level;					// 几级分类
	protected Integer order;					// 同一个上级时候的排序
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
	
	
}
