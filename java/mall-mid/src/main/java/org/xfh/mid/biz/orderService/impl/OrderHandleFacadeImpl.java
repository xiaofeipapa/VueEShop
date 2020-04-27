package org.xfh.mid.biz.orderService.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.mid.biz.orderService.IOrderHandleFacade;
import org.xfh.mid.biz.orderService.IOrderMakeAllocInfoPackage;
import org.xfh.mid.biz.orderService.IOrderMakePackageOut;
import org.xfh.mid.biz.orderService.IOrderPayService;
import org.xfh.mid.biz.orderService.IOrderReceiveService;
import org.xfh.mid.biz.orderService.form.PayOrderByCreditForm;
import org.xfh.mid.db.po.UserOrderItemAllocInfoPackage;
import org.xfh.mid.enums.PayCats;
import org.xfh.mid.vo.BizError;
import org.xfh.mid.vo.OrderHandleResult;
import org.xfh.mid.vo.form.AllocBatchSaveForm;
import org.xfh.mid.vo.form.ProductOutFinishPackageForm;
import org.xfh.mid.vo.form.TempAllocInfoPackageVo;

@Component
public class OrderHandleFacadeImpl extends AbstractOrderHandler implements IOrderHandleFacade{
	
	@Autowired
	IOrderMakeAllocInfoPackage orderMakeAllocInfoPackage;

	@Autowired
	IOrderPayService orderPayService;	

	@Autowired
	IOrderMakePackageOut orderMakePackageOut;	

	@Autowired
	IOrderReceiveService OrderReceiveServiceImpl;	
	
	@Override
	public OrderHandleResult mockPayByBackUser(Long backUserId, PayOrderByCreditForm form)
			throws Exception {
		return orderPayService.mockPayByBackUser(backUserId, form);
	}

	@Override
	public void batchSaveAllocInfo(Long userId, AllocBatchSaveForm form) throws Exception {
		orderMakeAllocInfoPackage.batchSaveAllocInfo(userId, form);
	}

	@Override
	public TempAllocInfoPackageVo showAllocPackageInfo(Long userId, String bizId) throws Exception {
		return orderMakeAllocInfoPackage.showAllocPackageInfo(userId, bizId);
	}

	@Override
	public void confirmPackageData(Long backUserId, String bizId) throws Exception {
		orderMakeAllocInfoPackage.confirmPackageData(backUserId, bizId);
	}

	@Override
	public void sendPackage(Long backUserId, ProductOutFinishPackageForm form) throws Exception {
		orderMakePackageOut.sendPackage(backUserId, form);
	}

	@Override
	public List<BizError> checkPackageOK(Long backUserId, String bizId) throws Exception {
		return orderMakeAllocInfoPackage.checkPackageOK(backUserId, bizId);
	}

	@Override
	public void makeReceiveStatusForOwnCar(Long backUserId, UserOrderItemAllocInfoPackage form) throws Exception {
		OrderReceiveServiceImpl.makeReceiveStatusForOwnCar(backUserId, form);
	}

	@Override
	public void payOrder(Long userId, String bizId, PayCats payCat) throws LogicException {
		orderPayService.payOrder(userId, bizId, payCat);
	}
    
}
