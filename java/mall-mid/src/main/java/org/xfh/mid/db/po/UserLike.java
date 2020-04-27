package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 用户关注的商品
 * @author cys
 *
 */
public class UserLike {
	Long userId;
	Long productId;
	Date createTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	
}
