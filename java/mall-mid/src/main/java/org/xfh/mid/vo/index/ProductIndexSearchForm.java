package org.xfh.mid.vo.index;

import org.xfh.dcore.vo.IndexSearchForm;

public class ProductIndexSearchForm extends IndexSearchForm {
	String searchSupplyCat;
	String searchStockCat;
	String searchRealStock;
	String setSupplier;
	
	public String getSearchSupplyCat() {
		return searchSupplyCat;
	}

	public void setSearchSupplyCat(String searchSupplyCat) {
		this.searchSupplyCat = searchSupplyCat;
	}

	public String getSearchStockCat() {
		return searchStockCat;
	}

	public void setSearchStockCat(String searchStockCat) {
		this.searchStockCat = searchStockCat;
	}

	public String getSearchRealStock() {
		return searchRealStock;
	}

	public void setSearchRealStock(String searchRealStock) {
		this.searchRealStock = searchRealStock;
	}

	public String getSetSupplier() {
		return setSupplier;
	}

	public void setSetSupplier(String setSupplier) {
		this.setSupplier = setSupplier;
	}
	
}
