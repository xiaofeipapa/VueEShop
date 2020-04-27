package org.xfh.mid.biz.orderService.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.orderService.IOrderReceiveService;
import org.xfh.mid.db.po.UserOrderItemAllocInfoPackage;
import org.xfh.mid.enums.AllocInfoPackageStatus;
import org.xfh.mid.enums.UserOrderStatus;

@Component
public class OrderReceiveServiceImpl extends AbstractOrderHandler implements IOrderReceiveService{

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void makeReceiveStatusForOwnCar(Long backUserId, UserOrderItemAllocInfoPackage form) throws Exception {
		
		/**
		 * 1. 创建签收记录
		 * 2. 更改订单状态
		 */

		VUtils.checkM(form.getReceiveImage(), "请上传签收凭据");
		VUtils.checkM(form.getReceiveRemark(), "请输入备注");
		
		form.setDataStatus(AllocInfoPackageStatus.RECEIVE.getValue());
		form.setReceiveCreateTime(DateUtils.getChinaDate());
		form.setReceiveCreateUserId(backUserId);
						
		daoHelper.updateById(UserOrderItemAllocInfoPackage.class, form);
		
		boolean allReceive = super.allPackageMatch(form.getId(), AllocInfoPackageStatus.RECEIVE);
		// 更新订单状态
		if(allReceive) {
			// 更新为已签收
			
			UserOrderItemAllocInfoPackage packageEntity = daoHelper.getById(UserOrderItemAllocInfoPackage.class, form.getId());			
			super.updateOrderStatus(UserOrderStatus.RECEIVE.getValue(), packageEntity.getOrderBizId());			
		}
		
	}

}
