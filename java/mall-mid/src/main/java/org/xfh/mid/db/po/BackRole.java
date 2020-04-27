package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 后台角色
 * 
 * @author cys
 *
 */
public class BackRole {
	
	public static final String ADMIN = "admin";		// 管理员必须是admin

	Long id;

	String name; 		// 英文的角色名称, 在数据表里唯一
	String label; 		// 中文的角色名称, 在数据表里唯一
	
	Date createTime; // 创建时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
