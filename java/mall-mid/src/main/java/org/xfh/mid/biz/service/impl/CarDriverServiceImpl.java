package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.ICarDriverService;
import org.xfh.mid.db.po.BackUser;
import org.xfh.mid.db.po.CarDriver;

@Component
public class CarDriverServiceImpl extends AbstractSingleTableService<CarDriver> 
	implements ICarDriverService{
	
	private Logger logger = LoggerFactory.getLogger(CarDriverServiceImpl.class);
	
	@Override
	protected void checkBeforeCreateOrUpdate(CarDriver entity, boolean isCreate) throws LogicException {

		VUtils.checkMobile(entity.getPhone(), "手机号码", true);
		VUtils.checkM(entity.getName(), "请输入司机姓名");
		VUtils.checkM(entity.getWarehouseId(), "请选择所属仓库");
				
		// 如果是新建
		if(isCreate) {
			entity.setDataStatus(BackUser.DATA_STATUS_NORMAL);			
		}		
	}


}
