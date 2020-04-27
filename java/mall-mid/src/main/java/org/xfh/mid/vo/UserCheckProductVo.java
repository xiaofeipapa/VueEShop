package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.UserCheckProduct;

/**
 * 
 * 
 * @author cys
 *
 */
public class UserCheckProductVo extends UserCheckProduct{
	
	String checkResultCatLabel;
	String modalTitle;		// 模型名称
	String specValueString;		// 规格属性
	String warehouseName;		// 仓库名称
	String sku;
	Integer warehouseStock;
	
	List<UserCheckProductVo> resultList = new ArrayList<>();

	public String getCheckResultCatLabel() {
		return checkResultCatLabel;
	}

	public void setCheckResultCatLabel(String checkResultCatLabel) {
		this.checkResultCatLabel = checkResultCatLabel;
	}

	public List<UserCheckProductVo> getResultList() {
		return resultList;
	}

	public void setResultList(List<UserCheckProductVo> resultList) {
		this.resultList = resultList;
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

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getWarehouseStock() {
		return warehouseStock;
	}

	public void setWarehouseStock(Integer warehouseStock) {
		this.warehouseStock = warehouseStock;
	}
}
