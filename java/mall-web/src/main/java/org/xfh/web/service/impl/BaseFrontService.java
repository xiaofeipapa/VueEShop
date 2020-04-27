package org.xfh.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.mid.db.dao.ProductQueryDao;

public abstract class BaseFrontService {

	@Autowired
	protected CommonDaoHelper daoHelper;
}
