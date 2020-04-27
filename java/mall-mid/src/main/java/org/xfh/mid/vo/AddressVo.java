package org.xfh.mid.vo;

import org.xfh.mid.db.po.DeliverAddress;

public class AddressVo {

	DeliverAddress goodAddress;			// 默认收货地址
	DeliverAddress invoiceAddress;		// 默认发票地址
	
	public DeliverAddress getGoodAddress() {
		return goodAddress;
	}
	public void setGoodAddress(DeliverAddress goodAddress) {
		this.goodAddress = goodAddress;
	}
	public DeliverAddress getInvoiceAddress() {
		return invoiceAddress;
	}
	public void setInvoiceAddress(DeliverAddress invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
		
}
