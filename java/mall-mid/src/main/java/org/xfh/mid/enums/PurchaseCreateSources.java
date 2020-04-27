package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 采购订单来源
 *  
 * @author cys
 *
 */
public enum PurchaseCreateSources {

	MANUAL("手动申请","MANUAL"),							
	FROM_ORDER("订单调拨","FROM_ORDER"); 

	private String label;
	private String value;

	private PurchaseCreateSources(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (PurchaseCreateSources c : PurchaseCreateSources.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}
	
	public static String getLabel(String value) {
		for (PurchaseCreateSources c : PurchaseCreateSources.values()) {
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
