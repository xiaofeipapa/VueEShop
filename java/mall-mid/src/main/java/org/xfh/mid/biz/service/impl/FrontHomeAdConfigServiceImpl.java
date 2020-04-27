package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableOrderService;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IFrontHomeAdConfigService;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.FrontHomeAdConfig;
import org.xfh.mid.db.po.HomeFloorProduct;

@Component
public class FrontHomeAdConfigServiceImpl extends AbstractSingleTableOrderService<FrontHomeAdConfig> 
	implements IFrontHomeAdConfigService{
	
	private Logger logger = LoggerFactory.getLogger(FrontHomeAdConfigServiceImpl.class);


	@Override
	protected void checkBeforeCreateOrUpdate(FrontHomeAdConfig entity, boolean isCreate) throws LogicException {
		
		VUtils.checkM(entity.getCat(), "请选择广告类型");
		if(CheckUtils.isNotEmpty(entity.getName())) {
			VUtils.checkLengthRange(entity.getName(), 1, 40, "标题不要超过40个字符");
		}
		VUtils.checkM(entity.getImageUrl(), "请上传广告图片");
				
		if(isCreate) {
			entity.setShowFront(false);

			// 设置排序						
			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", null);
			entity.setOrder(maxOrder + 1);
		}
	}
	
	
	@Override
	protected String sameParentFilter(FrontHomeAdConfig entity) {
		return "";
	}

	@Override
	public void toggleShow(Long userId, Long id) throws LogicException{
		
		FrontHomeAdConfig data = daoHelper.getById(FrontHomeAdConfig.class, id);
		
		boolean toSet = ! data.isShowFront();
		
		FrontHomeAdConfig param = new FrontHomeAdConfig();
		param.setId(data.getId());
		param.setShowFront(toSet);
		daoHelper.updateById(FrontHomeAdConfig.class, param);
		
	}

	@Transactional
	public void deleteData(Long userId, Long id) throws LogicException {
		
		FrontHomeAdConfig adConfig = daoHelper.getById(FrontHomeAdConfig.class, id);
		
		int order = adConfig.getOrder();
		
		// 删除这条记录
		daoHelper.deleteById(FrontHomeAdConfig.class, id);
		
		// 比这条记录order大的记录, 全部-1
		String sql = DSqlUtils.andGtNumber(DBs.ORDER, order, true);		
		
		daoHelper.subtractFieldBySql(HomeFloorProduct.class, DBs.ORDER, 1, sql);
		
	}



}
