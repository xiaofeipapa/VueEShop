package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 客户等级
 *  
 * @author cys
 *
 */
public enum ClientGrades {
	
	NORMAL("一般","normal"),					// 包装损坏
	VIP("VIP","vip"),	
	GOLDEN("黄金VIP","golden"),	
	DIAMOND("钻石VIP","diamond");				

	private static final String NULL_MESSAGE = "用value[%]找不到对应的enum类型";
	private String label;
	private String value;

	private ClientGrades(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (ClientGrades c : ClientGrades.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getLabel(String value) {
		for (ClientGrades c : ClientGrades.values()) {
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
