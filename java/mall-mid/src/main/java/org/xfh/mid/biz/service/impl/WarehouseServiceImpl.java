package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IWarehouseService;
import org.xfh.mid.db.po.Warehouse;

@Service
public class WarehouseServiceImpl extends AbstractSingleTableService<Warehouse> implements IWarehouseService{

	private Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	@Override
	protected void checkBeforeCreateOrUpdate(Warehouse entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getName(), "请输入名称");
		VUtils.checkM(entity.getDistrictCode(), "请选择省市区");
		VUtils.checkM(entity.getAddress(), "请输入街道地址");
		VUtils.checkM(entity.getContactUser(), "请输入联系人");
		VUtils.checkM(entity.getContactUserPhone(), "请输入联系人电话");

		
		super.checkDuplicateByField("name", entity.getName(), "名称重复", entity.getId(), isCreate);
	}

}


