package org.xfh.mid.db.po;

/**
 * 商品模型规格属性分组. 例如"颜色"是一个分组, 它的"属性" 有"红色", "蓝色" .. 等.
 * 
 *  对于电商我们人为定义两种属性: 普通参数和规格属性. 规格属性是用户在界面可以选取的属性(例如买华为mate30手机, 可以选尺寸, 颜色, 这两个就是规格属性)
 *  
 *  不同商品模型的规格属性名称可能相同, 为了避免这种情况, 必须要把商品模型id也引入到字段. 
 * 
 * @author cys
 *
 */
public class ModalSpecGroup{

	protected Long id;	
	protected String name;				// 属性组名称
	protected String remark;			// 备注
	protected Long modalId;			// 商品模型id
	
	public Long getModalId() {
		return modalId;
	}

	public void setModalId(Long modalId) {
		this.modalId = modalId;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
