package org.xfh.mid.vo;

import org.xfh.mid.db.po.WarehouseStock;

public class WarehouseStockVo extends WarehouseStock{

	String modalTitle;		// 模型名称
	String specValueString;		// 规格属性
	String warehouseName;		// 仓库名称
	
	String newStock;			// 界面使用, 新的库存
	String error;				// 界面使用, 显示错误

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getModalTitle() {
		return modalTitle;
	}

	public void setModalTitle(String modalTitle) {
		this.modalTitle = modalTitle;
	}

	public String getSpecValueString() {
		return specValueString;
	}

	public void setSpecValueString(String specValueString) {
		this.specValueString = specValueString;
	}

	public String getNewStock() {
		return newStock;
	}

	public void setNewStock(String newStock) {
		this.newStock = newStock;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
