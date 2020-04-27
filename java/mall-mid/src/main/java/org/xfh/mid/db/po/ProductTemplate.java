package org.xfh.mid.db.po;

/**
 * 商品模板
 * 
 * @author cys
 *
 */
public class ProductTemplate {

	Long id;	
		
	String name;		// 模板名称
	Long groupId;		// 外键, ModalSpecGroup 的id,
	Integer order;			// 同一外键下的排序
	String imageUrl;	// 属性图片
	
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
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
