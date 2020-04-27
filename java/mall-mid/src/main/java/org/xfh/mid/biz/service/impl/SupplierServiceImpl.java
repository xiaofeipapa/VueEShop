package org.xfh.mid.biz.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.ISupplierService;
import org.xfh.mid.db.po.Buyer;
import org.xfh.mid.db.po.Supplier;

@Component
public class SupplierServiceImpl extends AbstractSingleTableService<Supplier> 
	implements ISupplierService{
	
	private Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Override
	protected void checkBeforeCreateOrUpdate(Supplier entity, boolean isCreate) throws LogicException {
		
		VUtils.checkM(entity.getName(), "请输入供应商名称");
		VUtils.checkM(entity.getUser(), "请输入联系人");
		VUtils.checkM(entity.getUserPhone(), "请输入联系人电话");
		VUtils.checkM(entity.getCat(), "请选择供应商类别");

		super.checkDuplicateByField("name", entity.getName(), "供应商名称已经存在: " + entity.getName(), entity.getId(), isCreate);
		
		if(isCreate) {
			entity.setDataStatus(Buyer.DATA_STATUS_IN_USE);
		}
	}

	@Override
	public void updateStatus(Long userId, Long id, String opt) throws Exception {

		if("recover".equalsIgnoreCase(opt)){			
			Supplier param = new Supplier();
			param.setDataStatus(Buyer.DATA_STATUS_IN_USE);
			param.setId(id);		
			this.updateById(param, null);
			return;
		}
				
		if("trash".equalsIgnoreCase(opt)){
			Supplier param = new Supplier();
			param.setDataStatus(Buyer.DATA_STATUS_TRASH);
			param.setId(id);	
			this.updateById(param, null);
			return;
		}
		
		
		// 真正删除		
		commonDao.deleteById(this.tableName, id);
	}

}
