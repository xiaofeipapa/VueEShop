package org.xfh.mid.biz.stockService.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.dao.CommonDao;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.mid.biz.service.IBackLogService;
import org.xfh.mid.db.dao.StockDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.SaleStock;
import org.xfh.mid.db.po.WarehouseStock;

public abstract class AbstractStockService {
	private Logger logger = LoggerFactory.getLogger(AbstractStockService.class);
	
	@Autowired
	protected CommonDao commonDao;
	
	@Autowired
	protected CommonDaoHelper daoHelper;
	
	@Autowired
	protected StockDao stockDao;
	
	@Autowired
	protected IBackLogService backLogService;
	
	// 同步销售库存
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void syncSaleStock(List<Long> pids) {
		
		/**
		 * 销售库存并不是个实体的数量, 理论上可以随意增加. 所以修改的地方有两处: 
		 * 1) 仓库盘点的时候自动增减(业务见库存业务设计) 
		 * 2) 商品模块增加可以让运营人员手动更新的功能 
		 */
				
		for(Long pid : pids) {
			String sql = DSqlUtils.andEqNumber(DBs.PRODUCT_ID, pid, false);
			int allWarehouseStock = daoHelper.sumFieldBySql(WarehouseStock.class, DBs.STOCK, sql);
//			logger.debug("################ allWarehouseStock: " + allWarehouseStock);
			SaleStock ss = new SaleStock();
			ss.setStock(allWarehouseStock);
			ss.setVersion(System.currentTimeMillis());
			
			int count = daoHelper.getCountBySql(SaleStock.class, sql);
			if(count > 0) {
				// 更新
				daoHelper.updateBySql(SaleStock.class, ss, sql);
			}
			else {
				// 新增
				ss.setProductId(pid);
				daoHelper.insertOne(SaleStock.class, ss);
			}
			
		}
		logger.debug("################ 销售库存已更新: " + pids);
				
	}
	
}
