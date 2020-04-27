package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 商品草稿/正式/在用/上架/下架/删除等状态合集. 
 *  
 * @author cys
 *
 */
public enum ProductStatus {
	
	NOT_SALE("未上架","notSale"),					// 正常状态未上架
	ON_SALE("上架","onSale"),	
	TRASH("已废弃","trash");						// 标记废弃并不实际删除, 给用户后悔的机会. 类似windows的回收桶. 

	private String label;
	private String value;

	private ProductStatus(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (ProductStatus c : ProductStatus.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getLabel(String value) {
		for (ProductStatus c : ProductStatus.values()) {
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
