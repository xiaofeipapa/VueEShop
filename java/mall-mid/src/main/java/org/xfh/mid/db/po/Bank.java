package org.xfh.mid.db.po;

import java.io.Serializable;
import java.util.Date;

import org.xfh.dcore.pojo.BaseTreeData;

/**
 * 银行数据
 *
 */
public class Bank extends BaseTreeData implements Serializable{

	private static final long serialVersionUID = 2690988060792438312L;
	// 分类层级, 如1,2级
	public static final int PARENT = 1;
	public static final int CHILD = 2;
	
	protected Long createUserId;				
	protected Date createTime;
	
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
