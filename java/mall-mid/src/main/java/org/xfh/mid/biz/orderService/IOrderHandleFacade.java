package org.xfh.mid.biz.orderService;

/**
 *  为了将订单处理业务封装好, 方便修改/增加需求, 采用 facade 模式进行包装. 
 * 
 * @author cys
 *
 */
public interface IOrderHandleFacade extends IOrderPayService, IOrderMakeAllocInfoPackage, IOrderMakePackageOut
	,IOrderReceiveService{
	
}
