package org.xfh.mid.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.mid.biz.service.IBackRoleService;
import org.xfh.mid.db.dao.BackUserDao;
import org.xfh.mid.db.po.BackRole;

@Component
public class BackRoleServiceImpl extends AbstractSingleTableService<BackRole> 
	implements IBackRoleService{
	
	private Logger logger = LoggerFactory.getLogger(BackRoleServiceImpl.class);
	
	@Autowired
	BackUserDao backUserDao;
	
	@Override
	protected void checkBeforeCreateOrUpdate(BackRole entity, boolean isCreate) throws LogicException {

		super.checkDuplicateByField("name", entity.getName(), "英文的角色名称已经存在: " + entity.getName(), entity.getId(), isCreate);
		
		super.checkDuplicateByField("label", entity.getLabel(), "中文的角色名称已经存在: " + entity.getLabel(), entity.getId(), isCreate);

	}

	@Transactional
	@Override
	public void resetRoleMenus(Long roleId, List<Long> menuList) {
		
		backUserDao.deleteMenuByRoleId(roleId);
		
		// el-element 的 getCheckKeys 只会返回子节点.所以要找出子节点的父节点, 再重新插入.  
		List<Long> parentIds = backUserDao.getParentMenuIdsByChild(menuList);

		backUserDao.insertMenuBatch(roleId, parentIds);
		backUserDao.insertMenuBatch(roleId, menuList);
	}

}
