package org.xfh.mid.vo;

import java.util.List;

/**
 * 包括订单信息, 订单商品信息, 客户信息, 客户关于此订单的偿还信息. 
 * @author cys
 *
 */
public class ClientOrderFullDetailVo {
	
	protected UserOrderVo order;
	protected ClientBuyerVo clientBuyer;
	protected PaymentLogVo payment;
	protected List<UserOrderItemAllocInfoPackageVo> packageNoteList;
	protected List<UserOrderItemAllocInfoPackageVo> finishList; // 已完成快递出货单
	
	public UserOrderVo getOrder() {
		return order;
	}
	public void setOrder(UserOrderVo order) {
		this.order = order;
	}
	public ClientBuyerVo getClientBuyer() {
		return clientBuyer;
	}
	public void setClientBuyer(ClientBuyerVo clientBuyer) {
		this.clientBuyer = clientBuyer;
	}
	public PaymentLogVo getPayment() {
		return payment;
	}
	public void setPayment(PaymentLogVo payment) {
		this.payment = payment;
	}
	public List<UserOrderItemAllocInfoPackageVo> getPackageNoteList() {
		return packageNoteList;
	}
	public void setPackageNoteList(List<UserOrderItemAllocInfoPackageVo> packageNoteList) {
		this.packageNoteList = packageNoteList;
	}
	public List<UserOrderItemAllocInfoPackageVo> getFinishList() {
		return finishList;
	}
	public void setFinishList(List<UserOrderItemAllocInfoPackageVo> finishList) {
		this.finishList = finishList;
	}

}
