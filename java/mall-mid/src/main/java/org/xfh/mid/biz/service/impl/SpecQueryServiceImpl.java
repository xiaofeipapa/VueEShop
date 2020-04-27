package org.xfh.mid.biz.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.mid.biz.service.ISpecQueryService;
import org.xfh.mid.db.dao.SpecQueryDao;
import org.xfh.mid.vo.ModalSpecGroupVo;

@Component
public class SpecQueryServiceImpl implements ISpecQueryService{
	
	private Logger logger = LoggerFactory.getLogger(SpecQueryServiceImpl.class);
	
	@Autowired
	SpecQueryDao specQueryDao;

	@Override
	public Collection<ModalSpecGroupVo> getModalSpecGroupAndValues(Long loginUserId, Long modalId) {
		return specQueryDao.getSpecGroupsWithChildren(modalId);
	}

}
