package org.xfh.dcore.utils;

import java.util.ArrayList;
import java.util.List;

public class DSqlUtils {

	// 用于 检查数据库 int 字段
	public static final boolean isValid(Integer value) {
		return value != null && value > 0;
	}

	// 用于 检查数据库 long 字段
	public static final boolean isValid(Long value) {
		return value != null && value.intValue() > 0;
	}

//	public static void appendValue(StringBuilder sb , Object value){
//		if(value instanceof String){
//			sb.append(" '" + value + "' ");			
//		}
//		else if (value instanceof Number){
//			sb.append(" " + value + " ");		
//		}
//		else throw new RuntimeException("不支持的参数类型: " + value.getClass());
//	}

	// 如果sql出现 xx = null的情况结果会不正确, 应该设置为 is null;  
	public static String andEqString(String name, String value, boolean escapeName){

		if(escapeName) {
			name = " `" + name + "`";
		}
		
		if(CheckUtils.isEmpty(value)) {
			return " and " + name + " is null";
		}else {
			return " and " + name + " = '" + value + "'";			
		}
	}

	public static String andNe(String name, Object value, boolean escapeName){

		if(escapeName) {
			name = " `" + name + "`";
		}
		
		if(CheckUtils.isEmpty(value)) {
			return " and " + name + " <> null";
		}else {
			if(value instanceof Number) {
				return " and " + name + " <> " + value + "";					
			}		
			else {
				return " and " + name + " <> '" + value + "'";						
			}
		}
	}

	// 如果sql出现 xx = null的情况结果会不正确, 应该设置为 is null;  
	public static String andEqNumber(String name, Number value, boolean escapeName){
		
		if(escapeName) {
			name = " `" + name + "`";
		}
		
		if( value == null) {
			return " and " + name + " is null";
		}else {
			return " and " + name + " = " + value;			
		}
	}

	// 如果sql出现 xx = null的情况结果会不正确, 应该设置为 is null;  
	public static String andEqNull(String name, boolean escapeName){
		
		if(escapeName) {
			name = " `" + name + "`";
		}
		return " and " + name + " is null";
	}

	// 比某个值大
	public static String andGtNumber(String name, Integer value, boolean escapeName){

		if(escapeName) {
			name = " `" + name + "`";
		}
		
		return " and " + name + " > " + value;	
	}

	public static String andGtZero(String name, boolean escapeName){

		if(escapeName) {
			name = " `" + name + "`";
		}
		
		return " and " + name + " > 0";	
	}

	public static String andIsNull(String name, boolean escapeName){
		
		if(escapeName) {
			name = " `" + name + "`";
		}

		return " and " + name + " is null";
	}

	public static String andIsNotNull(String name, boolean escapeName){
		
		if(escapeName) {
			name = " `" + name + "`";
		}

		return " and " + name + " is not null";
	}

//	public static String appendEqString(String name, String value){
//		String sql = " " + name + " = '" + value + "'";
//		return sql;
//	}	

//	public static String appendEqEmpty(String name){
//		String sql = " (" + name + " is Null or " + name + " = '')";
//		return sql;
//	}	
//	public static String appendLikeText(String name, String text){
//		String sql = " " + name + " like '" + text + "'";
//		return sql;
//	}	

	public static String andInValues(String name, List<Long> values){
		
		String sql = " and " + name + " in ( ";
		for(int i=0; i< values.size(); i++){
			Object v = values.get(i);
			
			if(v instanceof Number){
				sql += v;
			}
			
			if( i < values.size() - 1){
				sql += ",";
			}
		}
		
		sql += ")";
		return sql;
	}

	public static String andInValues(String name, Object... values){
		
		String sql = " and " + name + " in ( ";
		for(int i=0; i< values.length; i++){
			Object v = values[i];
			
			if(i > 0) {
				sql += ",";
			}
			
			if(v instanceof String){
				sql += "'" + v + "'";
			}
			else {
				sql += v;				
			}
		}
		
		sql += ")";
		return sql;
	}

	public static String andFieldInSepStrings(String name, String strWithSep){
		
		String sql = " and " + name + " in ( ";
		
		sql += strWithSep;
		
		sql += ")";
		return sql;
	}

	public static  int calculatePageCount(int totalCount, int pageSize) {
		if(totalCount < pageSize) {
			return 1;
		}

	    int t = totalCount / pageSize;
	    if(totalCount % pageSize != 0) {
	    	t += 1;
	    }
	    return t;
	}
	
	// 从数组对象获得 integer列表
	public static List<Integer> getListOfProperty(List olist, String propertyName){
		List<Integer> alist = new ArrayList<>();
		if(olist == null || olist.isEmpty()) {
			return alist;
		}
		for(Object o : olist) {
			alist.add(RefUtils.getInt(o, propertyName));
		}
		return alist;
	}

    
    public static List<Long> parseToIdList(String str, String sep){
    	
    	String[] arr = str.split(sep);
    	List<Long> ids = new ArrayList<>();
    	for(String s : arr) {
    		ids.add(Long.parseLong(s));
    	}
    	return ids;
    }
    // 日期在开始,结束区间
	public static String andDateBetween(String name, String start, String end, String format){

		if(format == null) {
			format = "%Y-%m-%d";
		}
		
		String sql = " and ";
		
		boolean hasStart = CheckUtils.isNotEmpty(start);
		if( hasStart ) {
			sql += "DATE_FORMAT(" + name + ", '" +  format + "') >= '" + start + "'";
		}
		
		if(CheckUtils.isNotEmpty(end)) {
			if(hasStart) {
				sql += " and ";
			}
			sql += "DATE_FORMAT(" + name + ", '" +  format + "') <= '" + start + "'";
			
		}
		
		return sql;		
	}
}
