package org.xfh.mid.biz.orderService;

import org.xfh.mid.db.po.UserOrderItemAllocInfoPackage;

/**
 * 订单签收/退货服务
 * @author cys
 *
 */
public interface IOrderReceiveService {

	// 自有物流的增加签收记录
	void makeReceiveStatusForOwnCar(Long backUserId, UserOrderItemAllocInfoPackage form) throws Exception;
}
