package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.ICarInfoService;
import org.xfh.mid.db.po.CarInfo;

@Component
public class CarInfoServiceImpl extends AbstractSingleTableService<CarInfo> 
	implements ICarInfoService{
	
	private Logger logger = LoggerFactory.getLogger(CarInfoServiceImpl.class);
	
	@Override
	protected void checkBeforeCreateOrUpdate(CarInfo entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getPlate(), "请输入车牌");
		VUtils.checkM(entity.getWarehouseId(), "请选择所属仓库");
	}


}
