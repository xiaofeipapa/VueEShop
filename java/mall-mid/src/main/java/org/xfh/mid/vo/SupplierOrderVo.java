package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.SupplierOrder;

public class SupplierOrderVo extends SupplierOrder{

	protected String supplierName;	// 供应商名字
	protected String warehouseName;
	protected String createUserName;
	
	Integer itemCount;			// 采购总数量
		
	List<SupplierOrderItemVo> products = new ArrayList<>();
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public List<SupplierOrderItemVo> getProducts() {
		return products;
	}
	public void setProducts(List<SupplierOrderItemVo> products) {
		this.products = products;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	
}
