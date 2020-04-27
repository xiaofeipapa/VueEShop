package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 订单状态. 带 INNER_ 的状态表示内部状态. 需要使用 OrderHelper 来转. 
 *  
 * @author cys
 *
 */
public enum UserOrderStatus {

	NEW("等待支付","NEW"),			
	PAID("已支付","PAID"),			
	PAY_EXPIRE("超时未付款","PAY_EXPIRE"),	
	INNER_PACKAGE("安排拣货","INNER_PACKAGE"),			
	INNER_WAIT_CAR("等候物流","INNER_WAIT_CAR"),		
	SEND_CAR("已发货等签收","SEND_CAR"),					
	RECEIVE("用户已签收","RECEIVE"),					
	COMMENT("用户已评价","COMMENT"),	
	USER_CANCEL("用户已取消","USER_CANCEL"),
	USER_REFUSE("用户已拒绝","USER_REFUSE"),
	FINISH("订单完成","FINISH"); 

	private String label;
	private String value;

	private UserOrderStatus(String label, String value) {
		this.label = label;
		this.value = value;
	}

	// 检查某状态是否在指定类型里
	public static boolean contains(String checkStatus, UserOrderStatus...statusList ) {
		
		for (UserOrderStatus c : statusList) {
			if(c.getValue().equalsIgnoreCase(checkStatus)) {
				return true;
			}
		}
		return false;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (UserOrderStatus c : UserOrderStatus.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}
	
	public static String getLabel(String value) {
		for (UserOrderStatus c : UserOrderStatus.values()) {
			if (c.getValue().equals(value)) {
				return c.getLabel();
			}
		}
		return "";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
