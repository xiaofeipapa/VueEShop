package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 库存类型
 *  
 * @author cys
 *
 */
public enum StockCats {

	REAL_STOCK("真实库存", 1),
	VIRTUAL_STOCK("虚拟库存", 2); 

	private String label;
	private int value;

	private StockCats(String label, int value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (StockCats c : StockCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}
	
	public static String getLabel(int value) {
		for (StockCats c : StockCats.values()) {
			if (c.getValue() == value) {
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
