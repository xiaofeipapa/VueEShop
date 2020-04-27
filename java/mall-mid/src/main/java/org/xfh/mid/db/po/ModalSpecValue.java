package org.xfh.mid.db.po;

/**
 * 规格属性的值
 * 每个 group对象 对应多个 value对象
 * 
 * @author cys
 *
 */
public class ModalSpecValue implements Comparable<ModalSpecValue>{

	protected Long id;	
		
	protected String value;				// 属性值
	protected Long groupId;				// 外键, AbstractProductSpecGroup 的id,
	protected Integer order;			// 同一外键下的排序
	protected String imageUrl;			// 属性图片
	
	// 外键字段
	protected String exGroupName;		// 所属分组名称
	protected String exGroupCat;		// 所属分组分类
	String modalId;		// 商品模型id

	public String getModalId() {
		return modalId;
	}

	public void setModalId(String modalId) {
		this.modalId = modalId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getExGroupName() {
		return exGroupName;
	}

	public void setExGroupName(String exGroupName) {
		this.exGroupName = exGroupName;
	}

	public String getExGroupCat() {
		return exGroupCat;
	}

	public void setExGroupCat(String exGroupCat) {
		this.exGroupCat = exGroupCat;
	}

	@Override
	public int compareTo(ModalSpecValue o) {

		long currentGroupId = this.getGroupId().longValue();
		long targetGid = o.getGroupId().longValue();
		if(currentGroupId != targetGid ) {
			return currentGroupId < targetGid ? -1 : 1;
		}
		
		// 同组的情况
		long currentAttrId = this.getId().longValue();
		long targetAid = o.getId().longValue();
		
		if(currentAttrId == targetAid) {
			return 0;
		}
		
		return currentAttrId < targetAid ? -1 : 1;
		
	}	
}
