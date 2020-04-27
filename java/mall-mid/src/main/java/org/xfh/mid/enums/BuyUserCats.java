package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 购买商品的用户分类
 *  
 * @author cys
 *
 */
public enum BuyUserCats {
	
	ClientBuyer("企业用户","ClientBuyer"),					// 即客户表	
	FrontUser("个人消费者","FrontUser"); 					// 即前端用户表

	private static final String NULL_MESSAGE = "用value[%]找不到对应的enum类型";
	private String label;
	private String value;

	private BuyUserCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (BuyUserCats c : BuyUserCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getName(String value) {
		for (BuyUserCats c : BuyUserCats.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		throw new NullPointerException(String.format(NULL_MESSAGE, value));
	}

	public static BuyUserCats getFromValue(String value) {
		for (BuyUserCats c : BuyUserCats.values()) {
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
