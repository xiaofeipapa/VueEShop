package org.xfh.mid.biz.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IClientBuyerService;
import org.xfh.mid.db.po.Buyer;
import org.xfh.mid.enums.BuyUserCats;

@Component
public class ClientBuyerServiceImpl extends AbstractSingleTableService<Buyer> 
	implements IClientBuyerService{
	
	private Logger logger = LoggerFactory.getLogger(ClientBuyerServiceImpl.class);
	
	@Override
	protected void checkBeforeCreateOrUpdate(Buyer entity, boolean isCreate) throws LogicException {
		
		VUtils.checkM(entity.getName(), "请输入客户名称");
		VUtils.checkM(entity.getUser(), "请输入客户联系人");
		VUtils.checkM(entity.getUserPhone(), "请输入客户联系人电话");
		VUtils.checkM(entity.getGrade(), "请选择客户等级");

		super.checkDuplicateByField("name", entity.getName(), "客户名称已经存在: " + entity.getName(), entity.getId(), isCreate);
		
		if(isCreate) {
			entity.setDataStatus(Buyer.DATA_STATUS_IN_USE);
		}
		
		entity.setUserCat(BuyUserCats.ClientBuyer.getValue());
	}

	@Override
	public void updateStatus(Long userId, Long id, String opt) throws Exception {

		if("recover".equalsIgnoreCase(opt)){			
			Buyer param = new Buyer();
			param.setDataStatus(Buyer.DATA_STATUS_IN_USE);
			param.setId(id);		
			this.updateById(param, null);
			return;
		}
				
		if("trash".equalsIgnoreCase(opt)){
			Buyer param = new Buyer();
			param.setDataStatus(Buyer.DATA_STATUS_TRASH);
			param.setId(id);	
			this.updateById(param, null);
			return;
		}
		
		
		// 真正删除		
		commonDao.deleteById(this.tableName, id);
	}


	

}
