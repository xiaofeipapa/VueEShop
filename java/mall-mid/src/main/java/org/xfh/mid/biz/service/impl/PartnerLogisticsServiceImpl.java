package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IPartnerLogisticsService;
import org.xfh.mid.db.po.PartnerLogistics;

@Component
public class PartnerLogisticsServiceImpl extends AbstractSingleTableService<PartnerLogistics> 
	implements IPartnerLogisticsService{
	
	private Logger logger = LoggerFactory.getLogger(PartnerLogisticsServiceImpl.class);
	
	@Override
	protected void checkBeforeCreateOrUpdate(PartnerLogistics entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getName(), "请输入物流名称");

	}


}
