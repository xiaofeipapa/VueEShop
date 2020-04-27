package org.xfh.mid.vo;

import java.util.List;

import org.xfh.mid.db.po.UserOrderItem;

public class UserOrderItemVo extends UserOrderItem{
	
	String supplyCat;		// 商品供应渠道
	String supplyCatLabel;	// 供货渠道中文 
	
	// 调拨
	List<UserOrderItemAllocInfoVo> allocList;

	public List<UserOrderItemAllocInfoVo> getAllocList() {
		return allocList;
	}

	public void setAllocList(List<UserOrderItemAllocInfoVo> allocList) {
		this.allocList = allocList;
	}

	public String getSupplyCat() {
		return supplyCat;
	}

	public void setSupplyCat(String supplyCat) {
		this.supplyCat = supplyCat;
	}

	public String getSupplyCatLabel() {
		return supplyCatLabel;
	}

	public void setSupplyCatLabel(String supplyCatLabel) {
		this.supplyCatLabel = supplyCatLabel;
	}
}
