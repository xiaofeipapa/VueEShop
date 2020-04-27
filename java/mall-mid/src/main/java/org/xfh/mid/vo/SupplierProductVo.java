package org.xfh.mid.vo;

import org.xfh.mid.db.po.SupplierProduct;

public class SupplierProductVo extends SupplierProduct{
	protected String name;	// 供应商名字

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
