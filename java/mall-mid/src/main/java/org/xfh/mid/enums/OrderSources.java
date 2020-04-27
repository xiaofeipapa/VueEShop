package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 订单来源
 *  
 * @author cys
 *
 */
public enum OrderSources {
	
	FROM_WEB("前台web商城","FROM_WEB"),					
	FROM_BACK("后台下单","FROM_BACK"); 

	private String label;
	private String value;

	private OrderSources(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (OrderSources c : OrderSources.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getName(String value) {
		for (OrderSources c : OrderSources.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		return "";
	}

	public static String getLabel(String value) {
		for (OrderSources c : OrderSources.values()) {
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
