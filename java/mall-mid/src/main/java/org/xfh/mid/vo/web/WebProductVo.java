package org.xfh.mid.vo.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xfh.mid.vo.web.WebModalSpecGroupVo;

/**
 * 前台页面所需要的vo结构
 * @author cys
 *
 */
public class WebProductVo {

	Long id;							// 商品id
	Integer saleStock;					// 总库存
	String specValueIdString;			// 属性值的id逗号分隔字符串, 如 1000,1001
	String productImage;				// 商品本身的图片
	
	Long brandId;
	String brandName;					// 品牌名称
	
	String specValueString;				// 属性值的逗号分隔字符串. 如 蓝色,M码	
	String sku;
	
	Long modalId;						// 模型id
	String modalTitle;					// 商品模型title
	String modalImages;					// 模型图片
	
	BigDecimal marketPrice;				// 市场价
	BigDecimal retailPrice;				// 零售价(单价)
	BigDecimal vipPrice;				// VIP价格
	String dataStatus;

	// 关联的模型规格属性
	List<WebModalSpecGroupVo> groups = new ArrayList<>();
	
	// 当前商品的规格属性对应
	Map<Long, Long> currentSpecMap;
	
	// 收藏与否
	boolean likeGoods = false;
		
	public String getModalTitle() {
		return modalTitle;
	}
	public void setModalTitle(String modalTitle) {
		this.modalTitle = modalTitle;
	}
	public Integer getSaleStock() {
		return saleStock;
	}
	public void setSaleStock(Integer saleStock) {
		this.saleStock = saleStock;
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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModalImages() {
		return modalImages;
	}
	public void setModalImages(String modalImages) {
		this.modalImages = modalImages;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public BigDecimal getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}
	public List<WebModalSpecGroupVo> getGroups() {
		return groups;
	}
	public void setGroups(List<WebModalSpecGroupVo> groups) {
		this.groups = groups;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getModalId() {
		return modalId;
	}
	public void setModalId(Long modalId) {
		this.modalId = modalId;
	}
	@Override
	public String toString() {
		return "WebProductVo [id=" + id + ", modalId=" + modalId + ", modalTitle=" + modalTitle + ", saleStock="
				+ saleStock + ", specValueIdString=" + specValueIdString + ", brandId=" + brandId + ", brandName="
				+ brandName + ", modalImages=" + modalImages + ", specValueString=" + specValueString + ", sku=" + sku
				+ ", marketPrice=" + marketPrice + ", retailPrice=" + retailPrice + ", vipPrice=" + vipPrice
				+ ", groups=" + groups + "]";
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public Map<Long, Long> getCurrentSpecMap() {
		return currentSpecMap;
	}
	public void setCurrentSpecMap(Map<Long, Long> currentSpecMap) {
		this.currentSpecMap = currentSpecMap;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public boolean isLikeGoods() {
		return likeGoods;
	}
	public void setLikeGoods(boolean likeGoods) {
		this.likeGoods = likeGoods;
	}
	
}
