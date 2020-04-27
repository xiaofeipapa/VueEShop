package org.xfh.mid.vo.index;

import org.xfh.dcore.vo.IndexSearchForm;

public class OrderIndexSearchForm extends IndexSearchForm{

	public final static int SHOW_ALL = 1;		// 返回所有订单
	public final static int SHOW_TO_PAY = 2;	// 返回待付款订单
	public final static int SHOW_TO_GET = 3;	// 返回待签收订单
	
	String buyUserCat;
	
	// 前台属性
	boolean isFront;

	public String getBuyUserCat() {
		return buyUserCat;
	}

	public void setBuyUserCat(String buyUserCat) {
		this.buyUserCat = buyUserCat;
	}

	public boolean isFront() {
		return isFront;
	}

	public void setFront(boolean isFront) {
		this.isFront = isFront;
	}
}	
