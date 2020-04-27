package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 信用记录类型
 *  
 * @author cys
 *
 */
public enum CreditItemCats {
	
	DEBT("借款","DEBT"),					// 借款 
	GOOD_BACK("退款","GOOD_BACK"),		// 先借款, 然后退货了, 此时要退款
	REPAY("还款","REPAY");			    // 已借款之后的还款

	private static final String NULL_MESSAGE = "用value[%]找不到对应的enum类型";
	private String label;
	private String value;

	private CreditItemCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (CreditItemCats c : CreditItemCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getName(String value) {
		for (CreditItemCats c : CreditItemCats.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		throw new NullPointerException(String.format(NULL_MESSAGE, value));
	}

	public static CreditItemCats getFromValue(String value) {
		for (CreditItemCats c : CreditItemCats.values()) {
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
