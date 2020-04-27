package org.xfh.mid.biz.stockService.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.mid.biz.stockService.IStockRecoverService;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.Product;
import org.xfh.mid.db.po.SaleStock;
import org.xfh.mid.db.po.UserOrderItem;
import org.xfh.mid.vo.SaleStockVo;

@Component
public class StockRecoverServiceImpl extends AbstractStockService implements IStockRecoverService{

	private Logger logger = LoggerFactory.getLogger(StockRecoverServiceImpl.class);
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void recoverSaleStock(Long userId, List<UserOrderItem> itemList, boolean isFront) {
		
		List<Long> pids = new ArrayList<>();
		for(UserOrderItem item : itemList) {
			pids.add(item.getProductId());
		}

		// 获取商品销售库存
		String sql = DSqlUtils.andInValues(DBs.PRODUCT_ID, pids);
		List<SaleStockVo> stockList = stockDao.getSaleStockBySql(sql);

		// 检查销售库存
		for(SaleStockVo stockVo : stockList) {
			
			if(stockVo.getStockCat() == Product.STOCK_IS_MOCK) {
				// 虚拟不需要检查库存
				continue;
			}

			Long productId = stockVo.getProductId();
			
			for(UserOrderItem item : itemList) {
				if(item.getProductId().longValue() == productId.longValue()) {
					
					int count = item.getQuantity();
					
					// 恢复库存
					SaleStock update = new SaleStock();
					update.setStock(count);
					update.setVersion(System.currentTimeMillis());
					
					String wsql = DSqlUtils.andEqNumber(DBs.PRODUCT_ID, productId, false);
					daoHelper.updateBySql(SaleStock.class, update, wsql);
				}
			}
			
		}
		
		logger.info("=== 已恢复销售库存, 关联pids: " + pids);
		
	}
	

}
