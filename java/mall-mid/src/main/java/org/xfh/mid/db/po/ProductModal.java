package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 商品模型表
 * 
 * @author cys
 *
 */
public class ProductModal{

	protected Long id; 							// id
	
	protected Date createTime;					// 创建时间
	protected Long createUserId;				// 创建人
	protected Date updateTime;					// 更新时间
	protected Long updateUserId;				// 更新人
	protected Date trashTime;					// 废弃时间
	protected Long trashUserId;					// 废弃人
	
	protected String imageStr;					// 用,分隔的图片url
	protected String title;						// 标题
	protected String subTitle;					// 副标题
	protected Long brandId;						// 品牌
	
	protected String remark;
	protected String dataStatus;				// 见 ProductStatus
	
	// 每一个商品模型都可以从属于最多3个分类, 方便在前台能搜索
	protected Long childCat1;						// 分类1, 只需记录3级分类
	protected Long childCat2;
	protected Long childCat3;		
	
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

	public String getImageStr() {
		return imageStr;
	}

	public void setImageStr(String imageStr) {
		this.imageStr = imageStr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getChildCat1() {
		return childCat1;
	}

	public void setChildCat1(Long childCat1) {
		this.childCat1 = childCat1;
	}

	public Long getChildCat2() {
		return childCat2;
	}

	public void setChildCat2(Long childCat2) {
		this.childCat2 = childCat2;
	}

	public Long getChildCat3() {
		return childCat3;
	}

	public void setChildCat3(Long childCat3) {
		this.childCat3 = childCat3;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Date getTrashTime() {
		return trashTime;
	}

	public void setTrashTime(Date trashTime) {
		this.trashTime = trashTime;
	}

	public Long getTrashUserId() {
		return trashUserId;
	}

	public void setTrashUserId(Long trashUserId) {
		this.trashUserId = trashUserId;
	}
	
}
