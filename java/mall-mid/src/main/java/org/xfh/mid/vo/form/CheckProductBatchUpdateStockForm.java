package org.xfh.mid.vo.form;

import java.util.List;

import org.xfh.mid.vo.WarehouseStockVo;

/**
 * 盘点界面更新商品的库存. 
 * 
 * @author cys
 *
 */
public class CheckProductBatchUpdateStockForm {
	
	String remark;			// 更改原因
	List<WarehouseStockVo> stockList;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<WarehouseStockVo> getStockList() {
		return stockList;
	}
	public void setStockList(List<WarehouseStockVo> stockList) {
		this.stockList = stockList;
	}
}
