package org.xfh.mid.biz.orderService.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.orderService.IOrderPayService;
import org.xfh.mid.biz.orderService.form.PayOrderByCreditForm;
import org.xfh.mid.enums.BuyUserCats;
import org.xfh.mid.enums.CreditItemCats;
import org.xfh.mid.enums.PayCats;
import org.xfh.mid.enums.UserOrderStatus;
import org.xfh.mid.vo.ClientBuyerVo;
import org.xfh.mid.vo.OrderHandleResult;
import org.xfh.mid.vo.UserOrderVo;

/**
 * 运营人员代替客户, 从后台使用信用付款. 
 * 
 * @author cys
 *
 */
@Component
public class OrderPayServiceImpl extends AbstractOrderHandler implements IOrderPayService{

	private Logger logger = LoggerFactory.getLogger(OrderPayServiceImpl.class);

	@Transactional
	@Override
	public OrderHandleResult mockPayByBackUser(Long backUserId, PayOrderByCreditForm form) throws Exception {

		/**
		 * 业务流程: 
		 * 1) 检查信用额度
		 * 2) 更改支付状态
		 */

		UserOrderVo orderVo = userOrderQueryDao.getOrderByBizId(form.getBizId());

		boolean isClientBuyer = BuyUserCats.ClientBuyer.getValue().equalsIgnoreCase(orderVo.getBuyUserCat());
		boolean isNew = UserOrderStatus.NEW.getValue().equalsIgnoreCase(orderVo.getDataStatus());
		boolean isNotExpire = ! orderHelper.checkExpire(orderVo.getCreateTime());
		
		if(! (isClientBuyer && isNew && isNotExpire)) {
			throw new LogicException("订单状态不正确");
		}
		
		VUtils.checkM(form.getImageUrls(), "请上传至少一张沟通图片");
		VUtils.checkM(form.getRemark(), "请输入备注");
		
		logger.info("==== 正在使用客户信用付款");
		
		OrderHandleResult result = new OrderHandleResult();
		
		// 找出客户的余额
		ClientBuyerVo client;
			
		try {
			client =super.checkRemainCreditAmount(orderVo.getBuyUserId());
		}catch(LogicException ex) {

			// 超支
			result.setSuccFlag("N");
			result.setMsg(ex.getMessage());			
			return result;
		}
		
		Date current = DateUtils.getChinaDate();
		
		// ====================== 插入负债表
		super.insertCreditItem(orderVo, CreditItemCats.DEBT, current, backUserId);
		
		// ====================== 更新订单状态
		super.updateOrderStatus(UserOrderStatus.PAID.getValue(), orderVo.getBizId());
		
		// ====================== 插入付款记录
		super.insertPaymentLog(orderVo, PayCats.CREDIT, current, backUserId, form.getRemark(), form.getImageUrls());
		
		result.setSuccFlag("Y");
		return result;
		
	}


	@Override
	public void payOrder(Long userId, String bizId, PayCats payCat) throws LogicException {
		
		VUtils.checkM(payCat.getValue(), "请选择支付方式");

		UserOrderVo orderVo = orderQueryService.getOrderWithItems(userId, bizId);
		
		boolean isNew = orderVo.getDataStatus().equalsIgnoreCase(UserOrderStatus.NEW.getValue());
		boolean isNotExpire = ! orderHelper.checkExpire(orderVo.getCreateTime());
		if( ! isNew) {
			throw new LogicException("该订单不是待付款状态");
		}
		
		if( ! isNotExpire) {
			throw new LogicException("该订单已超时");			
		}
		
		Date current = DateUtils.getChinaDate();

		// ====================== 更新订单状态
		super.updateOrderStatus(UserOrderStatus.PAID.getValue(), orderVo.getBizId());
		
		// ====================== 插入付款记录
		super.insertPaymentLog(orderVo, payCat, current, null, null, null);
		
	}
}
