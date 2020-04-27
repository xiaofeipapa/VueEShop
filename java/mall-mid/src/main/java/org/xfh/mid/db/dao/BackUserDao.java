package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.db.po.BackUser;
import org.xfh.mid.vo.BackMenuVo;

/**
 * 用户,权限相关的dao
 * 
 * @author cys
 *
 */
public interface BackUserDao {

	// 获取所有菜单
	List<BackMenuVo> getAllMenus();
	
	// 根据用户id获取权限id, 根据权限id获取菜单列表
	List<BackMenuVo> getUserMenuList(Long userId);
	
	// 根据角色id, 获取该角色id对应的权限id数据
	List<Long> getMenuIdByRoleId(Long roleId);
	
	BackUser getUserByPhone(String phone);

	// 删除角色的权限菜单
	void deleteMenuByRoleId(Long roleId);

	// 批量插入角色的权限菜单
	void insertMenuBatch(@Param("roleId")Long roleId, @Param("menuList")List<Long> menuList);

	// 根据子菜单id, 查询他们的父id
	List<Long> getParentMenuIdsByChild(@Param("menuList")List<Long> menuList);
	
	int getBackUserCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	List<BackUser> getBackUserPage(@Param("searchFilter")IndexSearchFilter searchFilter);
		
}
