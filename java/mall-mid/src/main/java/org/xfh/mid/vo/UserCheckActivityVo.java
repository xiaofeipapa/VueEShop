package org.xfh.mid.vo;

import java.util.List;

import org.xfh.mid.db.po.UserCheckActivity;

/**
 * 
 * 
 * @author cys
 *
 */
public class UserCheckActivityVo extends UserCheckActivity{
	
	String warehouseName;
	String checkUserName;
	String checkUserPhone;
	String checkTimeStr;
	
	List<UserCheckProductVo> products ;
	
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getCheckUserName() {
		return checkUserName;
	}
	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	public String getCheckUserPhone() {
		return checkUserPhone;
	}
	public void setCheckUserPhone(String checkUserPhone) {
		this.checkUserPhone = checkUserPhone;
	}
	public String getCheckTimeStr() {
		return checkTimeStr;
	}
	public void setCheckTimeStr(String checkTimeStr) {
		this.checkTimeStr = checkTimeStr;
	}
	public List<UserCheckProductVo> getProducts() {
		return products;
	}
	public void setProducts(List<UserCheckProductVo> products) {
		this.products = products;
	}

}
