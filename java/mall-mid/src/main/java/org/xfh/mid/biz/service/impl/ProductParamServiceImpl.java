package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.mid.biz.service.IProductParamService;
import org.xfh.mid.db.po.ProductParam;

@Component
public class ProductParamServiceImpl extends AbstractSingleTableService<ProductParam> 
implements IProductParamService{
	
	private Logger logger = LoggerFactory.getLogger(ProductParamServiceImpl.class);

	@Override
	protected void checkBeforeCreateOrUpdate(ProductParam entity, boolean isCreate) throws LogicException {
		// TODO Auto-generated method stub
		
	}

}
