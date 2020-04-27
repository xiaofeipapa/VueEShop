package org.xfh.mid.db.po;

/**
 * 供应商表
 * 
 * @author cys
 *
 */
public class Supplier {

	public static final String DATA_STATUS_IN_USE = "inUse";
	public static final String DATA_STATUS_TRASH = "trash";
	
	protected Long id;
	protected String name;					// 供应商名称
	protected String user;					// 联系人
	protected String userPhone;				// 联系人电话
	protected String comanyPhone;			// 公司电话
	protected String email;					// 公司邮箱
	protected String cat;					// 供应商种类, 见 SupplierCats
	protected String dataStatus;			// 数据状态
	protected String fileUrls;					// 附件
		
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getComanyPhone() {
		return comanyPhone;
	}
	public void setComanyPhone(String comanyPhone) {
		this.comanyPhone = comanyPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getFileUrls() {
		return fileUrls;
	}
	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}

}
