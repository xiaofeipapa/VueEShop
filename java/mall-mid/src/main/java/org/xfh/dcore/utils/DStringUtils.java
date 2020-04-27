package org.xfh.dcore.utils;

import java.util.List;

import org.springframework.util.StringUtils;

public class DStringUtils extends StringUtils{
	/**
	 * 将首字母变成小写
	 * @param str
	 * @return
	 */
	public static final String lowerFirstLetter(String str){
		if(str == null || str.trim().equals("")){
			return null;
		}
		
		return str.substring(0,1).toLowerCase() + str.substring(1);
	}
	public static final boolean isNotEmpty(String str){
		return str!=null && !str.trim().equals("");
	}
	
	//TODO 转换特殊字符, 防止mybatis like 注入
	public static String removeSpecial(String str) {  
		if(str == null)return "";
        
		return str.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+","");
    }  
	
	// 保留固定位数, 如果不够前面加0
	public static final String makeFixFormat(int code, int num) {
        return String.format("%0" + num + "d", code);
    }
	
	// 例如, 将广东省 深圳市 南山区 拼接起来
	public static final String joinString(String sep, List<String> values) {
		
		if(isEmpty(values)) {
			return "";
		}
		if(isEmpty(sep)) {
			sep = " ";
		}
		
		String t = "";
		for(int i=0;i<values.size();i++) {
			if(isEmpty(values.get(i)))continue;
			t += values.get(i) + sep;
		}
		
		if(!" ".equalsIgnoreCase(sep)) {
			t = t.substring(0, t.length() -1);
		}
		return t;
	}
	
	// 例如, 将广东省 深圳市 南山区 拼接起来
	public static final String joinLong(String sep, List<Long> values) {

		if(isEmpty(values)) {
			return "";
		}
		if(isEmpty(sep)) {
			sep = " ";
		}
		
		String t = "";
		for(int i=0;i<values.size();i++) {
			if(isEmpty(values.get(i)))continue;
			t += values.get(i) + sep;
		}
		
		if(!" ".equalsIgnoreCase(sep)) {
			t = t.substring(0, t.length() -1);
		}
		return t;
	}
	
    public static String defaultIfEmpty(String str, String defaultStr) {
        return StringUtils.isEmpty(str) ? defaultStr : str;
    }
}
