package org.xfh.mid.vo;

import org.xfh.mid.db.po.ClientCreditItem;

public class ClientCreditItemVo extends ClientCreditItem{
	String clientName;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
}
