package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 标记某些核心数据是否修改
 * @author cys
 *
 */
public class ChangeFieldDataVo {
	
	String fieldName;
	Object originValue;
	Object toValue;
	
	public static List<ChangeFieldDataVo> makeOne(String name, Object ov, Object tv){
		ChangeFieldDataVo vo = new ChangeFieldDataVo();
		vo.setFieldName(name);
		vo.setOriginValue(ov);
		vo.setToValue(tv);
		
		List<ChangeFieldDataVo> volist = new ArrayList<>();
		volist.add(vo);
		return volist;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getOriginValue() {
		return originValue;
	}
	public void setOriginValue(Object originValue) {
		this.originValue = originValue;
	}
	public Object getToValue() {
		return toValue;
	}
	public void setToValue(Object toValue) {
		this.toValue = toValue;
	}
	@Override
	public String toString() {
		return "字段名称: " + fieldName + ", 原值: " + originValue + ", 修改后: " + toValue ;
	}
	
	
}
