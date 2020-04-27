package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 供应商订单的状态
 *  
 * @author cys
 *
 */
public enum SupplierOrderStatus {

	Temp("草稿","Temp"),			
	Topaid("待付款","Topaid"),		// 已审批待付款
	Paid("已付款","Paid"), 
	Receive("已收货","Receive"); 

	private String label;
	private String value;

	private SupplierOrderStatus(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList(boolean withEmptyOptions) {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		
		if(withEmptyOptions) {
			LabelValue lv = new LabelValue();
			lv.setLabel("-- 所有 -- ");
			lv.setValue("");
			list.add(lv);
		}
		
		for (SupplierOrderStatus c : SupplierOrderStatus.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}
	
	public static String getLabel(String value) {
		for (SupplierOrderStatus c : SupplierOrderStatus.values()) {
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
