package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.service.ISingleTableOrderService;
import org.xfh.mid.db.po.BackMenu;
import org.xfh.mid.vo.BackMenuVo;

public interface IBackMenuService extends ISingleTableOrderService<BackMenu>{

	// 将菜单按照排序变成树形结构
	List<BackMenuVo> convertToTreeNodeWithOrder(List<BackMenuVo> userMenus) throws Exception;

	// 返回所有菜单(只有管理员可以)
	List<BackMenuVo> getAllMenu(Long userId) throws Exception;

	// 返回用户关联的菜单
	List<BackMenuVo> getUserMenuList(Long userId) throws Exception;

	// 返回用户关联的菜单id
	List<Long> getUserMenuIdList(Long userId) throws Exception;

	// 根据角色id, 获取该角色id对应的权限id数据
	List<Long> getMenuIdByRoleId(Long roleId);
	
	// 插入新的菜单数据, 并保存到管理员权限关联表.
	Long checkSaveUserMenu(Long userId, BackMenu entity) throws Exception ;
}
