package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.buf.StringUtils;

/**
 * 商品实例表. 每一个模型对应多个实例, 两者是 1:n的关系
 * 
 * @author cys
 *
 */
public class Product {

	public static final int STOCK_IS_REAL = 1;			// 真实库存
	public static final int STOCK_IS_MOCK = 2;			// 虚拟库存

	protected Long id; 								// id

	protected Long modalId;							// 模型id
	protected BigDecimal marketPrice;				// 市场价
	protected BigDecimal retailPrice;				// 零售价(单价)
	protected BigDecimal wholePrice;				// 批发价(批发价和营销策略相关) 
	protected Integer stockCat;						// 库存类型, StockCats. 1表示真实库存, 2表示虚拟库存. 如果真实库存会在关联表保存数据. 
			
	protected Date createTime;					// 创建时间
	protected Long createUserId;				// 创建人
	protected Date updateTime;					// 更新时间
	protected Long updateUserId;				// 更新人
	protected Date checkTime;					// 盘点时间
	protected Long checkUserId;					// 盘点人
		
	// 继承 ProductModal 的上下架状态, 但允许保留自己的状态. 
	protected String dataStatus;			

	protected String sku;			
	
	/**
	 * 将逗号分隔的属性值排序, 确保值最小的属性id 在前. 例如  1002, 1000 会变成 1000, 1002
	 * @param specValueStringWithSep
	 * @return
	 */
	public static String sortSpecValueStringWithSep(String specValueStringWithSep) {
		
		if(specValueStringWithSep == null)return null;
		
		if( ! specValueStringWithSep.contains(",")) return specValueStringWithSep;
		
		String[] arrStrings = specValueStringWithSep.split(",");
		
		Arrays.sort(arrStrings);
		
		return StringUtils.join(arrStrings);
		
	}

	/**
	 * 将逗号分隔的属性值排序并转为Long列表
	 * @see convertToSpecValueStringWithSep
	 * @param specValueStringWithSep
	 * @return
	 */
	public static List<Long> convertToSpecValueList(String specValueStringWithSep) {
		
		List<Long> ids = new ArrayList<>(); 
		
		if(specValueStringWithSep == null)return ids;
		
		if( ! specValueStringWithSep.contains(",")) {
			ids.add(Long.valueOf(specValueStringWithSep));
			return ids;
		}
		
		String[] arrStrings = specValueStringWithSep.split(",");
		
		Arrays.sort(arrStrings);
		
		for(String arr : arrStrings ) {
			ids.add(Long.valueOf(arr));
		}
		return ids;
	}

	/**
	 * 将属性值列表转为逗号分隔的字符串. 
	 * @see convertToSpecValueList
	 * @param specValueStringWithSep
	 * @return
	 */
	public static String convertToSpecValueStringWithSep(List<Long> vidList) {
		
		if(vidList == null) {
			return "";
		}
				
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for(Long id : vidList) {
			if(index > 0) {
				sb.append(",");
			}
			sb.append(id);
			index++;
		}
		return sb.toString();
	}

	/**
	 * 比较两个商品的specValues 属性值是否相同(无视顺序)
	 * 
	 * 例如, 1000,1005  和 1005,1000 是同一个商品. 
	 * 
	 * @param str1
	 * @return
	 */
	public static boolean isEqSpecValueStrings(String first, String second) {

		if(first == null) {
			first = "";
		}
		
		if(second == null) {
			second = "";
		}
		
		if(first.equalsIgnoreCase(second)) {
			return true;
		}
		
		String[] arr1 = first.split(",");
		String[] arr2 = second.split(",");

		Arrays.sort(arr1);
		Arrays.sort(arr2);
				
		return Arrays.equals(arr1, arr2);
	}
	
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Long getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(Long checkUserId) {
		this.checkUserId = checkUserId;
	}

	public Long getModalId() {
		return modalId;
	}
	public void setModalId(Long modalId) {
		this.modalId = modalId;
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
	public BigDecimal getWholePrice() {
		return wholePrice;
	}
	public void setWholePrice(BigDecimal wholePrice) {
		this.wholePrice = wholePrice;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getStockCat() {
		return stockCat;
	}

	public void setStockCat(Integer stockCat) {
		this.stockCat = stockCat;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
}
