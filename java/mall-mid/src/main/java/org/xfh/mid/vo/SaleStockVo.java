package org.xfh.mid.vo;

import org.xfh.mid.db.po.SaleStock;

public class SaleStockVo extends SaleStock{

	Integer stockCat;

	public Integer getStockCat() {
		return stockCat;
	}

	public void setStockCat(Integer stockCat) {
		this.stockCat = stockCat;
	}
	
}
