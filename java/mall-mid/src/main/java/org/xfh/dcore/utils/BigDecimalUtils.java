package org.xfh.dcore.utils;

import java.math.BigDecimal;

/**
 * 财务类(BigDecimal) 类工具方法.  
 * @author cys
 *
 */
public class BigDecimalUtils {
	
	/**
	 * 保留小数点后两位
	 * @return
	 */
	public static String toFixed2(BigDecimal data) {
		if(data == null)return "0";
		
		return String.valueOf(data.setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * 除法
	 * @param up
	 * @param down
	 * @return
	 */
	public static BigDecimal devide(BigDecimal up , BigDecimal divisor, int numOfDigit) {
		return up.divide(divisor, numOfDigit, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal devide(String up , String divisor, int numOfDigit) {
		BigDecimal d1 = new BigDecimal(up);
		BigDecimal d2 = new BigDecimal(divisor);
		
		return devide(d1, d2, numOfDigit);
	}
	
	// 是否大于0
	public static boolean isGtZero(BigDecimal data) {
		return data.compareTo(BigDecimal.ZERO) > 0;
	}
	
	// 比较第一个是否大于第二个
	public static boolean isGt(BigDecimal first, BigDecimal second) {
		return first.compareTo(second) == 1;
	}
	
	public static void main(String[] args) {
		String d1 = "11"; 
		String d2 = "3"; 
		
		BigDecimal result = devide(d1, d2, 3);
		
		System.out.println(result);
	}
}
