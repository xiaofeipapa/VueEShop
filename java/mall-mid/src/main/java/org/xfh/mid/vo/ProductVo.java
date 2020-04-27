package org.xfh.mid.vo;

import java.util.List;

import org.xfh.mid.db.po.Product;

/**
 * 
 * 用于列表查询的对象. 下面是例子: 
 *  
 * 商品名称(模型名称)  规格属性        库存类型   状态
 * 
 * switch游戏机      续航版红蓝色      未定		  等候盘点
 * switch游戏机      续航版灰色        未定		  等候盘点
 * 
 * 一个更复杂的例子是(多个规格属性): 
 *  
 * 商品名称(模型名称)    规格属性        库存类型   状态
 * 
 * 李宁男士运动长裤      灰色,M码        未定		  等候盘点
 * 李宁男士运动长裤      黑色,M码        未定		  等候盘点
 * 
 * @author cys
 *
 */
public class ProductVo extends Product{
	
//	Collection<IdNameVo> specGroups = new ArrayList<>();		// 规格属性组名
//	
//	List<ProductSpecValueVo> specValueList = new ArrayList<>();
	
	String modalTitle;
	Integer warehouseStock;					// 仓库库存
	Integer saleStock;					// 总库存
	Integer totalDamage;				// 总报损
	
	String specValueIdString;			// 属性值的id逗号分隔字符串, 如 1000,1001
	String specValueString;				// 属性值的逗号分隔字符串. 如 蓝色,M码
	String modalImages;					// 模型图片
	Long brandId;						// 品牌id
	
	Long cat1;							// 分类1
	
	String productImage;				// 通常是modal image的第一张
	
	List<WarehouseStockVo> stockList;
	
	int supplierCount; 
	List<SupplierProductVo> suppliers ;			// 如果由供应商供货, 返回供应商的列表	
		
	public List<WarehouseStockVo> getStockList() {
		return stockList;
	}

	public void setStockList(List<WarehouseStockVo> stockList) {
		this.stockList = stockList;
	}

	public Integer getTotalDamage() {
		return totalDamage;
	}

	public void setTotalDamage(Integer totalDamage) {
		this.totalDamage = totalDamage;
	}

	public Integer getWarehouseStock() {
		return warehouseStock;
	}

	public void setWarehouseStock(Integer warehouseStock) {
		this.warehouseStock = warehouseStock;
	}

	public Integer getSaleStock() {
		return saleStock;
	}

	public void setSaleStock(Integer saleStock) {
		this.saleStock = saleStock;
	}

	public String getModalTitle() {
		return modalTitle;
	}

	public void setModalTitle(String modalTitle) {
		this.modalTitle = modalTitle;
	}

	public String getSpecValueIdString() {
		return specValueIdString;
	}

	public void setSpecValueIdString(String specValueIdString) {
		this.specValueIdString = specValueIdString;
	}

	public String getSpecValueString() {
		return specValueString;
	}

	public void setSpecValueString(String specValueString) {
		this.specValueString = specValueString;
	}

	public String getModalImages() {
		return modalImages;
	}

	public void setModalImages(String modalImages) {
		this.modalImages = modalImages;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public List<SupplierProductVo> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<SupplierProductVo> suppliers) {
		this.suppliers = suppliers;
	}

	public int getSupplierCount() {
		return supplierCount;
	}

	public void setSupplierCount(int supplierCount) {
		this.supplierCount = supplierCount;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Long getCat1() {
		return cat1;
	}

	public void setCat1(Long cat1) {
		this.cat1 = cat1;
	}

}
