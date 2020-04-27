package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IProductModalSpecGroupService;
import org.xfh.mid.db.po.ModalSpecGroup;

@Component
public class ProductModalSpecGroupServiceImpl extends AbstractSingleTableService<ModalSpecGroup> 
	implements IProductModalSpecGroupService{
	
	private Logger logger = LoggerFactory.getLogger(ProductModalSpecGroupServiceImpl.class);

	@Override
	protected void checkBeforeCreateOrUpdate(ModalSpecGroup entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getName(), "请输入属性组名称");
		String error = "属性组名称已经存在: " + entity.getName();

		// 同一个模型id, 属性组名不能相同
		String sqlFilter = DSqlUtils.andEqNumber("modalId", entity.getModalId(), true);
		sqlFilter += DSqlUtils.andEqString("name", entity.getName(), true);

		if(isCreate) {
			// 新增的情况只需要检查重名
			int existCount = commonDao.getCountBySql(this.tableName, sqlFilter);
			if(existCount > 0) {
				throw new LogicException(error);
			}
			
			// FIXME 未来可能会需要排序, 先保留示例代码. 
//			String orderSql = DSqlUtils.andEqInteger("level", entity.getLevel());
//			orderSql += DSqlUtils.andEqLong("parentId", entity.getParentId());
//						
//			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", orderSql);
//			entity.setOrder(maxOrder + 1);
//						
			return;
		}
	}
}
