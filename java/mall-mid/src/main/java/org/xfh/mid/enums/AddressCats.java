package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 地址类型
 *  
 * @author cys
 *
 */
public enum AddressCats {
					
	INVOICE_ADDRESS("发票地址","invoiceAddress"), 
	GOOD_ADDRESS("收货地址","goodAddress");

	private String label;
	private String value;

	private AddressCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (AddressCats c : AddressCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getLabel(String value) {
		for (AddressCats c : AddressCats.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
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
