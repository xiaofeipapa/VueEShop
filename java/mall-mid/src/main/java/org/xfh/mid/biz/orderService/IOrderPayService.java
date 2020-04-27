package org.xfh.mid.biz.orderService;

import org.xfh.dcore.ex.LogicException;
import org.xfh.mid.biz.orderService.form.PayOrderByCreditForm;
import org.xfh.mid.enums.PayCats;
import org.xfh.mid.vo.OrderHandleResult;

/**
 * 付款, 包括后台付款和前台付款
 * @author cys
 *
 */
public interface IOrderPayService {
	
	// 后台人员代付款
	OrderHandleResult mockPayByBackUser(Long backUserId, PayOrderByCreditForm form) throws Exception;

	// 前台网站用户付款
	void payOrder(Long userId,  String bizId, PayCats payCat) throws LogicException;
}
