package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 支付类型
 *  
 * @author cys
 *
 */
public enum PayCats {
	
	OFFLINE("线下付款","OFFLINE"),					
	ZHIFUBAO("支付宝","ZHIFUBAO"),					
	CREDIT("信用付款","CREDIT"),					
	WX("微信支付","WX");		

	private static final String NULL_MESSAGE = "用value[%]找不到对应的enum类型";
	private String label;
	private String value;

	private PayCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (PayCats c : PayCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getLabel(String value) {
		for (PayCats c : PayCats.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
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
