package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableOrderService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IBottomLinkService;
import org.xfh.mid.db.po.BottomLink;
import org.xfh.mid.db.po.DBs;

@Component
public class BottomLinkServiceImpl extends AbstractSingleTableOrderService<BottomLink> 
	implements IBottomLinkService{
	
	private Logger logger = LoggerFactory.getLogger(BottomLinkServiceImpl.class);


	@Override
	protected void checkBeforeCreateOrUpdate(BottomLink entity, boolean isCreate) throws LogicException {
		
		VUtils.checkMandMax(entity.getName(), "标题", 40);
		super.checkDuplicateByField(DBs.NAME, entity.getName(), "标题已经重复", entity.getId(), isCreate);
		
		if(isCreate) {
			entity.setShowFront(false);

			// 设置排序						
			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", null);
			entity.setOrder(maxOrder + 1);
		}
	}
	
	
	@Override
	protected String sameParentFilter(BottomLink entity) {
		return "";
	}

	@Override
	public void toggleShow(Long userId, Long id) {
		
		BottomLink data = daoHelper.getById(BottomLink.class, id);
		
		boolean toSet = ! data.isShowFront();
		
		BottomLink param = new BottomLink();
		param.setId(data.getId());
		param.setShowFront(toSet);
		daoHelper.updateById(BottomLink.class, param);
		
	}


}
