package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 出货单状态
 *  
 * @author cys
 *
 */
public enum AllocInfoPackageStatus {
					
	TEMP("临时数据","TEMP"), 
	VALID("正式数据","VALID"), 
	SENT("已发出","SENT"), 
	RECEIVE("用户签收","RECEIVE"), 			// 签收和完成的区别: 一般有个退款周期, 如果退款周期到了用户没退款, 那才算最终完成. 
	FINISH("已完成","FINISH");

	private String label;
	private String value;

	private AllocInfoPackageStatus(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (AllocInfoPackageStatus c : AllocInfoPackageStatus.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getLabel(String value) {
		for (AllocInfoPackageStatus c : AllocInfoPackageStatus.values()) {
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
