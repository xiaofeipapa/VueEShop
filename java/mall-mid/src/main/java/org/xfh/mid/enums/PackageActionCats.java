package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 签收/回退类型
 *  
 * @author cys
 *
 */
public enum PackageActionCats {
	
	RECEIVE("正常签收","RECEIVE"),					
	RETURN_ALL("全部退货","RETURN_ALL"); 

	private String label;
	private String value;

	private PackageActionCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (PackageActionCats c : PackageActionCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getName(String value) {
		for (PackageActionCats c : PackageActionCats.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		return "";
	}

	public static String getLabel(String value) {
		for (PackageActionCats c : PackageActionCats.values()) {
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
