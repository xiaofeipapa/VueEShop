package org.xfh.mid.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IProductModalSpecValueService;
import org.xfh.mid.db.po.ModalSpecValue;

@Component
public class ProductModalSpecValueServiceImpl extends AbstractSingleTableService<ModalSpecValue> 
	implements IProductModalSpecValueService{
	
	private Logger logger = LoggerFactory.getLogger(ProductModalSpecValueServiceImpl.class);

	@Override
	protected void checkBeforeCreateOrUpdate(ModalSpecValue entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getGroupId(), "请选择规格属性组");
		VUtils.checkM(entity.getValue(), "请输入规格属性值");
		
		String error = "同一组名的属性值已经存在: " + entity.getValue();
		
		// 同一组名的属性值已经存在
		String sqlFilter = DSqlUtils.andEqNumber("groupId", entity.getGroupId(), true);
		sqlFilter += DSqlUtils.andEqString("value", entity.getValue(), true);

		if(isCreate) {
			// 新增的情况只需要检查重名
			int existCount = commonDao.getCountBySql(this.tableName, sqlFilter);
			if(existCount > 0) {
				throw new LogicException(error);
			}
			
			// 设置排序
			String orderSql = DSqlUtils.andEqNumber("groupId", entity.getGroupId(), true);
						
			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", orderSql);
			entity.setOrder(maxOrder + 1);
			
			return;
		}

		// 更新的情况, 如果记录数大于1肯定重复, 如果只是1, 检查是否本记录. 
		
		List<Map> existList = commonDao.getListBySql(this.tableName, sqlFilter);
		if(CheckUtils.isEmptyCollection(existList)) {
			return;
		}
		
		if(existList.size() > 1) {
			throw new LogicException(error);				
		}
		
		Long existId = RefUtils.getLong(existList.get(0), "id");
		if(existId.intValue() != entity.getId().intValue()) {
			throw new LogicException(error);	
			
		}
	}
	
}
