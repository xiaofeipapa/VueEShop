package org.xfh.mid.db.po;

/**
 * 角色和权限的关联表
 * 
 * @author cys
 *
 */
public class BackRoleMenu {

	Long roleId; 		// 角色表id
	Long menuId; 		// 菜单或按钮 index
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	
}
