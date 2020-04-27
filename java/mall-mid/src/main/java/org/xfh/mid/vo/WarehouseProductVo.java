package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xfh.mid.db.po.UserCheckProduct;

/**
 * 
 * 以仓库为维度的商品数据. 例如商品在两个仓库都有, 那么就是两条记录. 
 * 
 * @author cys
 *
 */
public class WarehouseProductVo{
	Long warehouseId;
	Long productId;
	String modalTitle;
	String sku;
	String specValueString;				// 属性值的逗号分隔字符串. 如 蓝色,M码
	
	String warehouseName;				// 仓库名字
	Integer warehouseStock;				// 该仓库库存
	Integer warehouseDamage;			// 仓库报损
	
	Long brandId;						// 品牌id
	Date updateTime;
	
	List<UserCheckProduct> itemList = new ArrayList<>();
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getModalTitle() {
		return modalTitle;
	}
	public void setModalTitle(String modalTitle) {
		this.modalTitle = modalTitle;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
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
	public Integer getWarehouseStock() {
		return warehouseStock;
	}
	public void setWarehouseStock(Integer warehouseStock) {
		this.warehouseStock = warehouseStock;
	}
	public Integer getWarehouseDamage() {
		return warehouseDamage;
	}
	public void setWarehouseDamage(Integer warehouseDamage) {
		this.warehouseDamage = warehouseDamage;
	}

	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public List<UserCheckProduct> getItemList() {
		return itemList;
	}
	public void setItemList(List<UserCheckProduct> itemList) {
		this.itemList = itemList;
	}
	public void addItem(UserCheckProduct item) {
		this.itemList.add(item);
	}
}
