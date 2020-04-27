package org.xfh.dcore.utils;

import java.math.BigDecimal;
import java.util.Collection;

import org.xfh.dcore.ex.LogicException;

public class VUtils {

	public static void checkRequiredAndLength(String value, int lenMin, int lenMax, String label)
			throws LogicException {
		if (CheckUtils.isEmpty(value) || value.length() > lenMax || value.length() < lenMin) {
			throw new LogicException(
					"请输入" + label + "，并保证长度为" + lenMin + (lenMin == lenMax ? "" : ("到" + lenMax)) + "位！");
		}
	}

	public static void checkRequiredAndLength(String value, int lenMax, String label) throws LogicException {
		if (CheckUtils.isEmpty(value) || value.length() > lenMax) {
			throw new LogicException("请输入" + label + "，并保证字段长度小于" + lenMax);
		}
	}

	public static void checkSelectedValue(String value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException("请选择" + label + "！");
		}
	}

	public static void checkSelectedValue(Object value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException("请选择" + label + "！");
		}
	}

	public static void checkSelectedValue(Integer value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value) || 0 == value) {
			throw new LogicException("请选择" + label + "！");
		}
	}

	public static void checkRequired(String value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException("请输入" + label + "！");
		}
	}

	public static void checkIdCard(String value, String label) throws LogicException {
		if (!CheckIdCardUtils.validateCard(value)) {
			throw new LogicException("请输入正确的" + label + "！");
		}
	}
//	public static void checkMobileNum(String value, String label) throws LogicException {
//		if (!CheckUtils.isMobileValid(value)) {
//			throw new LogicException("请输入正确的" + label + "！");
//		}
//	}

	public static void checkNumAndLength(double value,double lenMin, double lenMax, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)|| value <= 0D || value > lenMax || value < lenMin) {
			throw new LogicException(
					"请输入" + label + "，并保证长度为" + lenMin + (lenMin == lenMax ? "" : ("到" + lenMax)) + "位！");
		}
	}
	public static void checkNumAndLength(int value,int lenMin, int lenMax, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)|| value <= 0 || value > lenMax || value < lenMin) {
			throw new LogicException(
					"请输入" + label + "，并保证长度为" + lenMin + (lenMin == lenMax ? "" : ("到" + lenMax)) + "位！");
		}
	}
	public static void checkNumAndLength(long value,long lenMin, long lenMax, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)|| value <= 0L || value > lenMax || value < lenMin) {
			throw new LogicException(
					"请输入" + label + "，并保证长度为" + lenMin + (lenMin == lenMax ? "" : ("到" + lenMax)) + "位！");
		}
	}

	public static void checkNum(Double value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)|| value <= 0D) {
			throw new LogicException("请输入" + label + "！");
		}
	}
	public static void checkNum(int value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)|| value <= 0) {
			throw new LogicException("请输入" + label + "！");
		}
	}
	public static void checkNum2(int value, String msg) throws LogicException {
		if (CheckUtils.isEmpty(value)|| value <= 0) {
			throw new LogicException(msg);
		}
	}
	public static void checkNum(long value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)|| value <= 0L) {
			throw new LogicException("请输入" + label + "！");
		}
	}
	public static void checkNum2(long value, String msg) throws LogicException {
		if (CheckUtils.isEmpty(value)|| value <= 0L) {
			throw new LogicException(msg);
		}
	}

	public static void checkObject(Object value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException("请输入" + label + "！");
		}
	}

	public static void checkCollection(Collection value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value) || value.size() == 0) {
			throw new LogicException( label + "！");
		}
	}

	public static void checkMax(String value, int len, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			return;
		}
		if (value.length() > len) {
			throw new LogicException(label + "的长度不能大于" + len + "个字符");
		}

	}
	public static void checkMobileVerifyCode(String value) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			return;
		}
		String regEx = "[0-9]{6}";
		boolean flg = value.matches(regEx);


		if(flg == false){
			throw new LogicException("请输入正确的6位短信验证码");
		}
	}

	public static void checkMobile(String value, String fieldName, boolean mandatory) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			if(mandatory) {
				throw new LogicException("请输入"+fieldName);
			}
			return;
		}

		if (!CheckUtils.isMobileValid(value)) {
			throw new LogicException(fieldName + "无效，请检查");
		}
	}

	public static void checkEmail(String value, int len) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			return;
		}

		if (!CheckUtils.isEmailValid(value) || value.length() > len) {
			throw new LogicException("邮箱格式必须正确，并且长度不能大于" + len);
		}
	}

	// 邮政编码
	public static void checkPost(String post) throws LogicException {
		if (CheckUtils.isEmpty(post)) {
			return;
		}
		
		if(post.length() != 6) {
			throw new LogicException("邮政编码必须是6位数字");
		}
		
		try {
			Long l = Long.valueOf(post);
		}catch(Exception e) {
			throw new LogicException("邮政编码必须是6位数字");			
		}

	}

	public static void checkEnum(Object[] enumValues, int value, String msg) throws LogicException {
		if (!CheckUtils.isEnumValueValid(enumValues, value)) {
			throw new LogicException(msg);
		}
	}

	public static void checkStringEqualsWith(String target, String withTarget, String message) throws LogicException {

		if (CheckUtils.isEmpty(target) || CheckUtils.isEmpty(withTarget)) {
			return;
		}

		if (!target.equals(withTarget)) {

			System.out.println(String.format("\n参数【%s】与【%s】不一致，请重新填写\n", target, withTarget));
			throw new LogicException(message);

		}
	}

	public static void checkStringEqualsIgnoreCaseWith(String target, String withTarget, String message) throws LogicException {

		if (CheckUtils.isEmpty(target) || CheckUtils.isEmpty(withTarget)) {
			return;
		}

		if (!target.equalsIgnoreCase(withTarget)) {

			System.out.println(String.format("\n参数【%s】与【%s】不一致，请重新填写\n", target, withTarget));
			throw new LogicException(message);

		}
	}

	public static void checkPassword(String password) throws LogicException{
		if (CheckUtils.isEmpty(password)) {
			return;
		}

		String regEx = "[0-9a-z_]{6,16}";
		boolean flg = password.matches(regEx);

		if(flg == false){
			throw new LogicException("密码长度需在6到16位间，只含数字,字母和_");
		}
	}

	public static void checkSpecial(String value, String fieldLabel) throws LogicException{
		if (CheckUtils.isEmpty(value)) {
			return;
		}

		String regEx = "[0-9a-zA-Z_-]{4,16}";
		boolean flg = value.matches(regEx);

		if(flg == false){
			throw new LogicException( fieldLabel + "需在4到16个字符之间，只含数字,字母和_-");
		}
	}

	public static void checkUserName(String userName, String msg) throws LogicException{
		if (CheckUtils.isEmpty(userName)) {
			return;
		}
		String regEx = "^[\\u0391-\\uFFE5\\w]{1,}$";
		boolean flg = userName.matches(regEx);

		if(flg == false){
			throw new LogicException(msg+"格式不正确");
		}
	}

	public static void checkM(Object value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException(label);
		}		
	}


	// mandatory and max
	public static void checkMandMax(String value, String fieldName, int length) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException("请输入"+fieldName);
		}		
		checkMax(value, length, fieldName);
	}

	public static void checkM(Long value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException(label);
		}		
	}

	public static void checkGtZero(Integer value, String fieldName) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException("请输入"+fieldName);
		}		
		if(value.intValue() <=0 ) {
			throw new LogicException(fieldName+"必须大于0");			
		}
	}

	public static BigDecimal checkBigDecimalMustGtZero(String value, String error) throws LogicException {
		if (CheckUtils.isEmpty(value) ) {
			throw new LogicException(error);
		}
		
		BigDecimal v;
		try {
			v = new BigDecimal(value);
		}catch (Exception e) {
			throw new LogicException(error);
		}
		
		if ( v.compareTo(BigDecimal.ZERO) <= 0) {
			throw new LogicException(error);
		}
		
		return v;
		
	}

	public static void checkBigDecimalMustGtZero(BigDecimal value, String error) throws LogicException {
		if (CheckUtils.isEmpty(value) ) {
			throw new LogicException(error);
		}
				
		if ( value.compareTo(BigDecimal.ZERO) <= 0) {
			throw new LogicException(error);
		}
		
	}

	public static void checkMandatory(Integer value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value) || value.intValue() <= 0) {
			throw new LogicException(label);
		}		
	}

	// 检查长度区间
	public static void checkLengthRange(String value, int min, int max, String msg) throws LogicException {
		if (CheckUtils.isEmpty(value) || value.length() < min || value.length() > max) {
			throw new LogicException(msg);
		}
	}

	// 检查是否整数类型且大于0
	public static int checkParseIntGtZero(Object value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException(label);
		}		
		
		if( !CheckUtils.isNumber(value))throw new LogicException(label);
		
		int v = Integer.parseInt(value.toString());
		if(v < 1)throw new LogicException(label);
		
		return v;
	}

	// 检查是否大于等于0的整数类型
	public static int checkParseIntGtEqZero(Object value, String label) throws LogicException {
		if (CheckUtils.isEmpty(value)) {
			throw new LogicException(label);
		}		
		
		if( !CheckUtils.isNumber(value))throw new LogicException(label);
		
		int v = Integer.parseInt(value.toString());
		if(v < 0)throw new LogicException(label);
		
		return v;
	}

}
