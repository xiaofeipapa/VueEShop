package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.mid.db.po.BackRole;

public interface IBackRoleService extends ISingleTableService<BackRole>{
		
	// 重设(先删除, 再增加) 角色的所有权限
	void resetRoleMenus(Long roleId, List<Long> menuList);
}
