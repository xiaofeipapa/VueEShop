package org.xfh.mid.db.po;

/**
 * 仓库. 
 * @author cys
 *
 */
public class Warehouse {

	protected Long id;						// 数据库id
	protected String name;					// 如xx1号仓
	protected String districtCode;			// 区域行政区划
	protected String fullDistrict;			// 冗余字段, 保存省市区信息, 避免数据库再查. 
	protected String address;				// 街道信息(不包含省市区) 
	protected String landline;				// 固定联系电话(如果有) 
	
	protected String postalCode;			// 邮政编码
	protected String contactUser;			// 第一联系人
	protected String contactUserPhone;		// 第一联系人电话
	
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
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLandline() {
		return landline;
	}
	public void setLandline(String landline) {
		this.landline = landline;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getContactUser() {
		return contactUser;
	}
	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}
	public String getContactUserPhone() {
		return contactUserPhone;
	}
	public void setContactUserPhone(String contactUserPhone) {
		this.contactUserPhone = contactUserPhone;
	}
	public String getFullDistrict() {
		return fullDistrict;
	}
	public void setFullDistrict(String fullDistrict) {
		this.fullDistrict = fullDistrict;
	}	

}
