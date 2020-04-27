package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 调拨类型
 *  
 * @author cys
 *
 */
public enum AllocCats {
	
	WAREHOUSE("仓库调拨","WAREHOUSE"),					
	SUPPLIER("供应商采购","SUPPLIER"); 	

	private String label;
	private String value;

	private AllocCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (AllocCats c : AllocCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getLabel(String value) {
		for (AllocCats c : AllocCats.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		return null;
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
