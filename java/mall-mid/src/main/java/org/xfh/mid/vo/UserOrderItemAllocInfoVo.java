package org.xfh.mid.vo;

import org.xfh.mid.db.po.UserOrderItemAllocInfo;

public class UserOrderItemAllocInfoVo extends UserOrderItemAllocInfo{

	String fromName;		// 调拨的名字 (仓库/供应商)
	String modalTitle;				// 商品名字
	String specValueString;			// 规格属性
	int totalQuantity;				// 同一个商品的合计数量
	String imagePath;			// 商品第1张图片

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
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

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
