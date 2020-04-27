package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 商品报损分类
 *  
 * @author cys
 *
 */
public enum ProductDamageCats {
	
	BOX_DAMAGE("包装损坏","boxDamage"),					// 包装损坏
	DIRTY("有脏污","dirty"),	
	EXPIRE("已过保质期","expire"),	
	FUNC_DISABLE("无法使用","funcDisable"),		
	OTHER("其他原因","other");						

	private static final String NULL_MESSAGE = "用value[%]找不到对应的enum类型";
	private String label;
	private String value;

	private ProductDamageCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (ProductDamageCats c : ProductDamageCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}

	public static String getName(String value) {
		for (ProductDamageCats c : ProductDamageCats.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		throw new NullPointerException(String.format(NULL_MESSAGE, value));
	}

	public static ProductDamageCats getFromValue(String value) {
		for (ProductDamageCats c : ProductDamageCats.values()) {
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
