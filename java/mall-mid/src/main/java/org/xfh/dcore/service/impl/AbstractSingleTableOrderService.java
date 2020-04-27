package org.xfh.dcore.service.impl;

import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.ISingleTableOrderService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;

/**
 * 
 * 多了上移/下移方法
 * 
 * @author cys
 *
 */
public abstract class AbstractSingleTableOrderService<T> extends AbstractSingleTableService<T> 
	implements ISingleTableOrderService<T>, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(AbstractSingleTableOrderService.class);

	
	/**
	 * 和待操作记录位于同级的sql条件. 
	 * 
	 * 例如某个子节点的同级sql 肯定是 parentId = xxx 
	 * 
	 * @return
	 */
	abstract protected String sameParentFilter(T entity);
	
	
	@Transactional
	@Override
	public void moveUp(Long userId,Long id, String orderField) throws Exception {
		
		T entity = this.getById(id);
		int order = RefUtils.getInt(entity, orderField);
		logger.debug("=== moveup order: " + order);
		
		if(order == 1) {
			throw new LogicException("已经移到最顶端");
		}
		
		int toOrder = order -1;

		TreeMap<String, Object> param = new TreeMap<>();
		
		// 更新受影响的那条记录
		String sqlFilter = this.sameParentFilter(entity);
		sqlFilter += DSqlUtils.andEqNumber(orderField, toOrder, true);
		param.put(orderField, order);
		commonDao.updateBySql(tableName, param, sqlFilter);
		
		// 更新自己的order
		param = new TreeMap<>();
		param.put(orderField, toOrder);
		commonDao.updateById(tableName, param, id);

	}

	@Transactional
	@Override
	public void moveDown(Long userId,Long id, String orderField) throws Exception {

		T entity = this.getById(id);
		
		// 同级sql
		String sqlFilter = this.sameParentFilter(entity);
		int count = this.getCountBySql(sqlFilter);
		int order = RefUtils.getInt(entity, orderField);
				
		if(order == count) {
			throw new LogicException("已经移到最低部");
		}

		int toOrder = order + 1;

		TreeMap<String, Object> param = new TreeMap<>();
		
		// 更新受影响的那条记录
		sqlFilter += DSqlUtils.andEqNumber(orderField, toOrder, true);
		param.put(orderField, order);
		commonDao.updateBySql(tableName, param, sqlFilter);
		
		// 更新自己的order
		param = new TreeMap<>();
		param.put(orderField, toOrder);
		commonDao.updateById(tableName, param, id);
	}
	
}
