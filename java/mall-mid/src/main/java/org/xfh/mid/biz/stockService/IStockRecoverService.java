package org.xfh.mid.biz.stockService;

import java.util.List;

import org.xfh.mid.db.po.UserOrderItem;

/**
 * 恢复库存的工具
 * @author cys
 *
 */
public interface IStockRecoverService {
	
	// 恢复销售库存
	void recoverSaleStock(Long userId, List<UserOrderItem> itemList, boolean isFront);
}
