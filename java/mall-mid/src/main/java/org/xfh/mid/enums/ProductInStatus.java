package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 入库批次 
 * 
 * 草稿/正式等状态
 *  
 * @author cys
 *
 */
public enum ProductInStatus {
	
	TEMP("草稿","TEMP"),				
	VALID("正式","VALID");

	private String label;
	private String value;

	private ProductInStatus(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (ProductInStatus c : ProductInStatus.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getLabel(String value) {
		for (ProductInStatus c : ProductInStatus.values()) {
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
