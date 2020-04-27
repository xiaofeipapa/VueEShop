package org.xfh.mid.biz.stockService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.mid.biz.stockService.IStockChangeFacade;
import org.xfh.mid.biz.stockService.IStockMakeCheckActivityValid;
import org.xfh.mid.biz.stockService.IStockMakeOrder;
import org.xfh.mid.biz.stockService.IStockMakeProductValid;
import org.xfh.mid.vo.BatchOptResult;
import org.xfh.mid.vo.form.MakeOrderForm;

@Component
public class StockChangeFacadeImpl extends AbstractStockService implements IStockChangeFacade{
	
	@Autowired
	IStockMakeProductValid makeProductInValid;

	@Autowired
	IStockMakeCheckActivityValid stockMakeCheckActivityValid;

	@Autowired
	IStockMakeOrder backClientBuy;

	@Override
	public void makeProductInBatchValid(Long userId, Long inId)  throws Exception{
		makeProductInValid.makeProductInBatchValid(userId, inId);
	}

	@Override
	public void makeCheckActivityValid(Long userId, Long activityId) throws Exception{
		stockMakeCheckActivityValid.makeCheckActivityValid(userId, activityId);
	}

	@Override
	public BatchOptResult makeOrderForBack(Long backUserId, MakeOrderForm form) throws Exception {
		return backClientBuy.makeOrderForBack(backUserId, form);
	}

	@Override
	public BatchOptResult makeOrderForFront(Long userId, MakeOrderForm form) throws Exception {
		return backClientBuy.makeOrderForFront(userId, form);
	}



}
