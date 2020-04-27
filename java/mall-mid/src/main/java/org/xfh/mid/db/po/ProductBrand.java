package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 商品品牌类
 * 
 * @author cys
 *
 */
public class ProductBrand {

	public static final int RECOMMEND_YES = 1;	// 是否推荐
	public static final int RECOMMEND_NO = 2;

	public static final int DATA_STATUS_NORMAL = 1;
	public static final int DATA_STATUS_DELETE = 2;

	public static final int ON_SALE_YES = 1;		// 是否上架
	public static final int ON_SALE_NO = 2;
	

	/**品牌id 主键**/
	Long id;
	
	String englishName;			// 品牌英文名
	String name;			// 品牌中文名
	String logoUrl;	// 品牌图片url	
	Integer recommend;		/**是否推荐  1是 2否  默认1**/
		
	
	String remark;			/**备注说明**/
	Integer dataStatus;		/**数据状态 1正常  2已删除**/
	Date createTime;
	String createUserId;
	Integer onSale;			// 上架, 下架
	
	//2018年7月19日17:26:25
	String menuColor;	//菜单栏颜色
	String bgImageUrl;	//品牌背景长图
		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getMenuColor() {
		return menuColor;
	}

	public void setMenuColor(String menuColor) {
		this.menuColor = menuColor;
	}

	public String getBgImageUrl() {
		return bgImageUrl;
	}

	public void setBgImageUrl(String bgImageUrl) {
		this.bgImageUrl = bgImageUrl;
	}

	public Integer getOnSale() {
		return onSale;
	}

	public void setOnSale(Integer onSale) {
		this.onSale = onSale;
	}


	
}
