package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 发票类型
 *  
 * @author cys
 *
 */
public enum InvoiceCats {
	
	COMPANY("企业发票","company"),					
	PERSON("个人发票","person");		

	private static final String NULL_MESSAGE = "用value[%]找不到对应的enum类型";
	private String label;
	private String value;

	private InvoiceCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (InvoiceCats c : InvoiceCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getName(String value) {
		for (InvoiceCats c : InvoiceCats.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		throw new NullPointerException(String.format(NULL_MESSAGE, value));
	}

	public static InvoiceCats getFromValue(String value) {
		for (InvoiceCats c : InvoiceCats.values()) {
			if (c.getValue().equals(value)) {
				return c;
			}
		}
		throw new NullPointerException(String.format(NULL_MESSAGE, value));
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
