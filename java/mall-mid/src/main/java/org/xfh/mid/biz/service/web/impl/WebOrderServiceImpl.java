package org.xfh.mid.biz.service.web.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.mid.biz.helper.IOrderHelper;
import org.xfh.mid.biz.service.IOrderQueryService;
import org.xfh.mid.biz.service.web.IWebOrderService;
import org.xfh.mid.biz.stockService.IStockRecoverService;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.UserOrder;
import org.xfh.mid.enums.UserOrderStatus;
import org.xfh.mid.vo.UserOrderVo;

@Component
public class WebOrderServiceImpl implements IWebOrderService{

    static final Logger logger = LoggerFactory.getLogger(WebOrderServiceImpl.class);
	
	@Autowired
	IOrderQueryService orderQueryService;
	
	@Autowired
	IStockRecoverService stockRecoverService;

	@Autowired
	CommonDaoHelper daoHelper;
	
	@Autowired
	IOrderHelper orderHelper;

	@Transactional
	@Override
	public void deleteOrder(Long userId, String bizId) {
		
		// 可被删除的订单: 已经超时的, 还没付款的, 已经结束的
		UserOrderVo order = orderQueryService.getOrderWithItems(userId, bizId);
		
		if(!UserOrderStatus.contains(order.getDataStatus(), UserOrderStatus.NEW, UserOrderStatus.PAY_EXPIRE, UserOrderStatus.FINISH)) {
			return;
		}
		
		String sql = DSqlUtils.andEqString(DBs.BIZ_ID, bizId, false);
		daoHelper.deleteBySql(UserOrder.class, sql);

		// 这两种状态需要恢复库存
		if(UserOrderStatus.contains(order.getDataStatus(), UserOrderStatus.NEW, UserOrderStatus.PAY_EXPIRE)) {
			
			stockRecoverService.recoverSaleStock(userId, order.getItemList(), true);
			
		}
		
	}

}
