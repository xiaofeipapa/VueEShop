package org.xfh.mid.vo.form;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.SupplierProduct;

/**
 * 商品修改数据表单
 * @author cys
 *
 */
public class ProductEditForm {

	protected String idstr;
	protected String marketPrice;
	protected String retailPrice;

	protected String supplyCat;
	protected List<SupplierProduct> suppliers = new ArrayList<>();
	
	protected String status;
	
	public String getIdstr() {
		return idstr;
	}
	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getSupplyCat() {
		return supplyCat;
	}
	public void setSupplyCat(String supplyCat) {
		this.supplyCat = supplyCat;
	}
	public List<SupplierProduct> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(List<SupplierProduct> suppliers) {
		this.suppliers = suppliers;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
