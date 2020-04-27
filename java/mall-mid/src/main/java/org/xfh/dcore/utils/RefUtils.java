package org.xfh.dcore.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 反射工具类
 * @author cys
 *
 */
public class RefUtils {
	

	/**
	 * 注意
	 * 1. 这个方法通过 public getXxx 获得值, 然后通过 setXxx 设置到目的对象. (而不是通过字段)
	 * 2. 因此,如果传入的不是POJO 类对象, 结果有可能会不准确.
	 * 3. 如果字段少, 建议手动复制. 不要滥用这个工具
	 */
	public static void copyFieldsToObject(Object from,Object target){
		copyFieldsToObject(from, target, null, null);
	}


	/**
	 * 注意
	 * 	  1. 这个方法通过 public getXxx 获得值, 然后通过 setXxx 设置到目的对象. (而不是通过字段)
	 * 	  2. 因此,如果传入的不是POJO 类对象, 结果有可能会不准确.
	 * 	  3. 如果字段少, 建议手动复制. 不要滥用这个工具
	 *
	 * @param from        the from
	 * @param target      the target
	 * @param avoidFields the avoid fields
	 * @param converters  the converters
	 */
	public static void copyFieldsToObject(Object from,Object target, String[] avoidFields , List<FieldCustomConverter> converters){
	
		// 不需要复制的字段		
		List<String> af = new ArrayList<String>();
		if(avoidFields != null){
			af.addAll(Arrays.asList(avoidFields));
		}
		
		// source 对象待复制字段 和 get方法
		Map<String,Method> fromMap = findFieldWithGetter(from);
		
		// target 对象待复制字段 和 set 方法
		Map<String,Method> targetMap = findFieldWithSetter(target);
		
		for(String fieldName : fromMap.keySet()){
			
			// 目标对象没有, 忽略
			if( ! targetMap.containsKey(fieldName)){
				continue;
			}
			
			// 如果是需要忽略的字段
			if(af != null && af.contains(fieldName)){
				continue;
			}
			
			// 如果是自定义转换规则
			if(converters != null){
				for(FieldCustomConverter cont : converters){
					if(cont.getFieldName().equals(fieldName)){
						cont.convert(from, target);
					}
				}
			}
			
			// 用反射设置值
			Method getMethod = fromMap.get(fieldName);
			Method setMethod = targetMap.get(fieldName);
			try {

				//copy 字段控判断
				String fromClassName = from.getClass().toString();
				Object fieldValue = getMethod.invoke(from, null);
				if( fieldValue == null || fieldValue.equals("")){
					//logger.warn(String.format("-- 复制【%s】的字段【%s】失败，字段值为空", fromClassName, fieldName));
					continue;
				}

				setMethod.invoke(target, fieldValue);
			} catch (Exception e) {
//				logger.error("==== fieldName: " + fieldName);
//				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 查找对象以get 开头的方法, 从而获得字段
	 * @param from
	 * @return
	 */
	public static Map<String,Method> findFieldWithGetter(Object from){

		Map<String,Method> map = new HashMap<>();
		
		
		for(Method m : from.getClass().getMethods()){
			String methodName = m.getName();
			
			// 不访问静态字段
			if(Modifier.isStatic(m.getModifiers())) {
				continue;
			}			
			
			if("getClass".equalsIgnoreCase(methodName)){
				continue;
			}
			
			if(methodName.startsWith("get")){
				String fieldName = DStringUtils.lowerFirstLetter(methodName.substring(3));
				map.put(fieldName, m);
			}
			else if(methodName.startsWith("is")){
				String fieldName = DStringUtils.lowerFirstLetter(methodName.substring(2));		
				map.put(fieldName, m);	
			}
			
		}
		return map;
	}
	/**
	 * 查找对象以 set 开头的方法, 从而获得字段
	 * @param from
	 * @return
	 */
	public static Map<String,Method> findFieldWithSetter(Object from){

		Map<String,Method> map = new HashMap<>();
		
		for(Method m : from.getClass().getMethods()){
			String methodName = m.getName();

			// 不访问静态字段
			if(Modifier.isStatic(m.getModifiers())) {
				continue;
			}
			
			if(methodName.startsWith("set")){
				String fieldName = DStringUtils.lowerFirstLetter(methodName.substring(3));
				map.put(fieldName, m);
			}
			
		}
		return map;
	}
	
	/**
	 * 根据方法名返回方法
	 * @param from
	 * @return
	 */
	public static Method getMethodByName(Object from, String name){
		
		for(Method m : from.getClass().getMethods()){
			String methodName = m.getName();
			
			if(methodName.equals(name)){
				return m;
			}			
		}
		return null;
	}
	
	public static Object getPropertyObject(Object object, String name){
		try {
			return BeanUtils.getProperty(object, name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static String getPropertyString(Object object, String name){
		try {
			return BeanUtils.getSimpleProperty(object, name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	public static final Integer getInt(Object object, String name){
		try {
			String x = BeanUtils.getSimpleProperty(object, name);
			if(x == null || !CheckUtils.isNumber(x)){
				return -99;
			}
			return Integer.parseInt(x);
		} catch (Exception e) {
			e.printStackTrace();
			return -99;
		} 
	}

	public static final Long getLong(Object object, String name){
		try {
			String x = BeanUtils.getSimpleProperty(object, name);
			if(x == null || !CheckUtils.isNumber(x)){
				return 0l;
			}
			return Long.parseLong(x);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		} 
	}
	
	/**
	 * 
	 * @param object
	 * @param name
	 * @param value
	 */
	public static void setProperty(Object object, String name, Object value){
		if(CheckUtils.isEmpty(value)){
			return;
		}
		try {
			BeanUtils.setProperty(object, name, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void setPropertyNull(Object object, String name){
		
		try {
			BeanUtils.setProperty(object, name, null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 将 dto 变成map
	 *
	 * @param from        the from
	 * @param allowNull   the allow null
	 * @param avoidFields the avoid fields
	 * @param converters  the converters
	 * @return the tree map
	 */
	public static TreeMap<String, Object> copyFieldsToMap(Object from, boolean allowNull, String[] avoidFields, List<FieldCustomConverter> converters){

		TreeMap<String, Object> target = new TreeMap<String, Object>();

		// 不需要复制的字段		
		List<String> af = new ArrayList<String>();
		if(avoidFields != null){
			af.addAll(Arrays.asList(avoidFields));
		}

		// source 对象待复制字段 和 get方法
		Map<String,Method> fromMap = findFieldWithGetter(from);
				
		for(String fieldName : fromMap.keySet()){
						
			// 如果是需要忽略的字段
			if(af != null && af.contains(fieldName)){
				continue;
			}
			
//			System.out.println("================== fieldName: " + fieldName);
			
			// 如果是自定义转换规则
			if(converters != null){
				for(FieldCustomConverter cont : converters){
					if(cont.getFieldName().equals(fieldName)){
						cont.convert(from, target);
					}
				}
			}

			// 用反射设置值
			Method getMethod = fromMap.get(fieldName);
			try {
				Object value = getMethod.invoke(from, null);
				if(value == null){
					if(allowNull){
						target.put(fieldName, value);						
					}
				}
				else{
					target.put(fieldName, value);	
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
				
		return target;
	}


	/**
	 * 将 map 变成dto
	 *
	 * @param <T>         the type parameter
	 * @param fromMap     the from map
	 * @param clazz       the clazz
	 * @param allowNull   the allow null
	 * @param avoidFields the avoid fields
	 * @return the t
	 */
	public static <T> T copyFromMapToObject(Map<String, Object> fromMap, Class<T> clazz, boolean allowNull, String[] avoidFields){
		
		// 不需要复制的字段		
		List<String> af = new ArrayList<String>();
		if(avoidFields != null){
			af.addAll(Arrays.asList(avoidFields));
		}

		T target = null;
		try {
			target = (T)clazz.forName(clazz.getName()).newInstance();
			// source 对象待复制字段 和 get方法
			Map<String,Method> targetMap = findFieldWithSetter(target);
	
			for(String fieldName : fromMap.keySet()){
				
				// 如果是需要忽略的字段
				if(af != null && af.contains(fieldName)){
					continue;
				}
	
//				System.out.println("================== fieldName: " + fieldName);
	
				// 用反射设置值
				Object value = fromMap.get(fieldName);
				Method setMethod = targetMap.get(fieldName);
				
				// 如果数据库删了字段, 就可能出现这种情况. 
				if(setMethod == null){
					continue;
				}
				if(value == null){
					if(allowNull){
						setMethod.invoke(target, value);
					}
				}
				else{
					setMethod.invoke(target, value);						
				}
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
				
		return target;
	}

	/**
	 * 查找对象以get 开头的方法, 从而获得字段
	 * @param from
	 * @return
	 */
	public static List<FieldMeta> findFields(Object from){
	
		Method[] methods = null;
		if(from instanceof Class){
			methods = ((Class) from).getMethods();
		}
		else{
			methods = from.getClass().getMethods();			
		}
		
		List<FieldMeta> flist = new ArrayList<>();
		
		for(Method m : methods){
			String methodName = m.getName();
			Class type = m.getReturnType();
						
			if("getClass".equalsIgnoreCase(methodName)){
				continue;
			}
			
			if(methodName.startsWith("get")){
				String fieldName = DStringUtils.lowerFirstLetter(methodName.substring(3));
				FieldMeta meta = new FieldMeta(fieldName, type);
				flist.add(meta);
			}
			else if(methodName.startsWith("is")){
				String fieldName = DStringUtils.lowerFirstLetter(methodName.substring(2));	
				FieldMeta meta = new FieldMeta(fieldName, type);
				flist.add(meta);
			}
			
		}
		return flist;
	}

	// 新建/修改时自动排除外键字段
	public static String[] checkAndMakeProperAvoidFields(List<String> defaultUpdateAvoidFields, String[] inputAvoidFields) {
		List<String> result = new ArrayList<>(defaultUpdateAvoidFields);

		if(inputAvoidFields != null) {
			for(String n : inputAvoidFields) {
				result.add(n);
			}
		}
		
		return result.toArray(new String[result.size()]);
	}
}
