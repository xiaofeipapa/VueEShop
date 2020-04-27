package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.mid.biz.service.IBackRoleMenuService;
import org.xfh.mid.db.po.BackRoleMenu;

@Component
public class BackRoleMenuServiceImpl2 extends AbstractSingleTableService<BackRoleMenu> 
	implements IBackRoleMenuService{
	
	private Logger logger = LoggerFactory.getLogger(BackRoleMenuServiceImpl2.class);

	@Override
	protected void checkBeforeCreateOrUpdate(BackRoleMenu entity, boolean isCreate) throws LogicException {
		
	}

}
