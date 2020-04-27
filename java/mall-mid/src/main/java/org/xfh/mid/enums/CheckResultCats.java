package org.xfh.mid.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.xfh.dcore.vo.LabelValue;

/**
 * 盘点活动分类
 *  
 * @author cys
 *
 */
public enum CheckResultCats {

	OK("正常","OK"),			
	DAMAGE("报损","DAMAGE"),			
	LESS_COUNT("缺少数量","LESS_COUNT"),	
	MORE_COUNT("增多数量","MORE_COUNT");

	private String label;
	private String value;

	/**
	 * 当类型是枚举的其中一种是, 库存是应该增加还是减少. 
	 * 把这个业务方法放在这里, 方便当枚举类型变更时能找出关联代码. 
	 * @param cat
	 * @return
	 */
	public static int checkAddOrSubtract (String cat, int quantity){

		boolean isDamage = CheckResultCats.DAMAGE.getValue().equalsIgnoreCase(cat);
		boolean isLess = CheckResultCats.LESS_COUNT.getValue().equalsIgnoreCase(cat);
		boolean isMore = CheckResultCats.MORE_COUNT.getValue().equalsIgnoreCase(cat);
		boolean isOk = CheckResultCats.OK.getValue().equalsIgnoreCase(cat);
		
		if(isDamage)return -1 * quantity;
		
		if(isLess)return -1 * quantity;
		
		if(isMore)return quantity;
		
		if(isOk) return 0;
		
		throw new RuntimeException("遗漏的检查结果: " + cat);
	}

	private CheckResultCats(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static Collection<LabelValue> getLabelValueList() {
		Collection<LabelValue> list = new ArrayList<LabelValue>();
		for (CheckResultCats c : CheckResultCats.values()) {
			LabelValue lv = new LabelValue();
			lv.setLabel(c.label);
			lv.setValue(c.value);
			list.add(lv);
		}
		return list;		
	}
	
	public static String getLabel(String value) {
		for (CheckResultCats c : CheckResultCats.values()) {
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
