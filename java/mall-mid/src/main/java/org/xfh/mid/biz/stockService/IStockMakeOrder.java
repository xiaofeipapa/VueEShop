package org.xfh.mid.biz.stockService;

import org.xfh.mid.vo.BatchOptResult;
import org.xfh.mid.vo.form.MakeOrderForm;

/**
 * 下单. 包括后台下单, 前台(web/移动端) 下单
 * @author cys
 *
 */
public interface IStockMakeOrder {
	
	// 后台下单
	BatchOptResult makeOrderForBack(Long backUserId, MakeOrderForm form) throws Exception;
	
	// 前台下单
	BatchOptResult makeOrderForFront(Long userId, MakeOrderForm form) throws Exception;
}
