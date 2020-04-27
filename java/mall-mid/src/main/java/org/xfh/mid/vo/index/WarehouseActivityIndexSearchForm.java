package org.xfh.mid.vo.index;

import org.xfh.dcore.vo.IndexSearchForm;

public class WarehouseActivityIndexSearchForm extends IndexSearchForm{

	Long searchWarehouseId;
	String searchUserName;
	String searchUserPhone;

	public Long getSearchWarehouseId() {
		return searchWarehouseId;
	}

	public void setSearchWarehouseId(Long searchWarehouseId) {
		this.searchWarehouseId = searchWarehouseId;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public String getSearchUserPhone() {
		return searchUserPhone;
	}

	public void setSearchUserPhone(String searchUserPhone) {
		this.searchUserPhone = searchUserPhone;
	}
	
	
}
