package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购买用户表. c端用户和b端用户都共用一个表, 通过buyUserCat 区分
 * 
 * @author cys
 *
 */
public class Buyer {

	public static final String DATA_STATUS_IN_USE = "inUse";
	public static final String DATA_STATUS_TRASH = "trash";
	
	protected Long id;
	protected String name;					// 客户名称. 如果c端用户表示真名
	protected String user;					// 联系人. 如果c端用户表示账号
	protected String userPhone;				// 联系人电话, 如果c端用户表示手机号
	protected String comanyPhone;			// 公司电话
	protected String email;					// 公司邮箱
	protected String grade;					// 客户等级, 见 ClientGrades
	protected String dataStatus;					// 数据状态
	String password;

	protected BigDecimal creditAmount;						// 信用额度	
	protected String userCat;			// 见 BuyUserCats
	protected Date createTime;
	
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getUserCat() {
		return userCat;
	}
	public void setUserCat(String userCat) {
		this.userCat = userCat;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
