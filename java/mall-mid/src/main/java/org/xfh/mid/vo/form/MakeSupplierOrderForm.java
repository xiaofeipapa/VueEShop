package org.xfh.mid.vo.form;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.vo.SupplierOrderVo;

public class MakeSupplierOrderForm{
	
	List<SupplierOrderVo> supplierProducts = new ArrayList<>();

	public List<SupplierOrderVo> getSupplierProducts() {
		return supplierProducts;
	}

	public void setSupplierProducts(List<SupplierOrderVo> supplierProducts) {
		this.supplierProducts = supplierProducts;
	}
	
}
