package org.xfh.dcore.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.MethodUtils;

//CheckUtils , 用于各种验证
public class CheckUtils {

	static final String pattern1 = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	static final Pattern pattern = Pattern.compile(pattern1);
	static String regEx = "[\u4e00-\u9fa5]";
	static Pattern pat = Pattern.compile(regEx);

	// 检查是否大于等于0的整数类型
	public static boolean isIntGtEqZero(Object value) {
		if (CheckUtils.isEmpty(value) || !CheckUtils.isNumber(value)) {
			return false;
		}		
		
		int v = Integer.parseInt(value.toString());
		if(v < 0)return false;
		
		return true;
	}

	public static boolean mightBePhone(String phone) {
		try {
			Long.valueOf(phone);
		}catch(Exception ex) {
			return false;
		}
		return true;
	}

	public static boolean isEmailValid(String email) {
		boolean valid = true;
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			valid = false;
		}
		return valid;
	}

	public static boolean isDateValid(String dateFormat, String datestr) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			sdf.parse(datestr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static final boolean isEnumValueValid(Object[] enumValues, int v) {
		try {

			for (Object enumo : enumValues) {
				if (v == (Integer) MethodUtils.invokeMethod(enumo, "getValue",
						null)) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	// 暂不考虑中文因素
	public static final boolean isLenghLess(String v, int len) {
		if (v != null && v.length() <= len) {
			return true;
		}
		return false;
	}

	public static final boolean isEmpty(Object o) {
		if(o == null)return true;
		String s = o.toString().trim();
		return s.equals("") || s.equalsIgnoreCase("null") || s.equalsIgnoreCase("undefined");

	}

	public static final void isEmptyThrowException(Object o, String msg)
			throws Exception {
		if (o == null || "".equals(o.toString().trim()))
			throw new Exception(msg);
	}

	public static final boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}


	public static final boolean isNotEmpty(Collection c) {
		return c != null && !c.isEmpty();
	}

	public static final boolean isEmptyCollection(Collection c) {
		return c == null || c.isEmpty();
	}

	public static final boolean hasChinese(String str) {
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find()) {
			flg = true;
		}
		return flg;
	}

	public static final boolean isNumber(String str) {
		
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static final boolean isNumber(Object str) {
		
		if(str == null)return false;
		String theStr = str.toString().trim();
		
		return isNumber(theStr);
	}

	public static boolean isMobileValid(String value) {
		// https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%9B%BD%E5%86%85%E5%9C%B0%E7%A7%BB%E5%8A%A8%E7%BB%88%E7%AB%AF%E9%80%9A%E8%AE%AF%E5%8F%B7%E7%A0%81
		// Pattern p = Pattern.compile("^1[3,4,5,7,8]\\d{9}$"); //简单实现
		if(value.length() != 11){
			return false;
		}
		Pattern p = Pattern
				.compile("(^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[3-8])|(18[0-9])|(16[0-9]))\\d{8}$)|(^((170[0,1,5,7-9])|(171[8,9]))\\d{7}$)");// 完整实现
		return p.matcher(value).matches();
	}

	// 检查万元是否ok
	public static boolean isAmountWanValid(String value) {
		
		if(isNumber(value)){
			return true;
		}
		
		Pattern p = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		return p.matcher(value).matches();
	}

	public static void main(String[] args) {
		// http://www.233.com/Java/zhuanye/20100924/102546666.html
		// Pattern p =
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		// https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%9B%BD%E5%86%85%E5%9C%B0%E7%A7%BB%E5%8A%A8%E7%BB%88%E7%AB%AF%E9%80%9A%E8%AE%AF%E5%8F%B7%E7%A0%81
		// Pattern p = Pattern.compile("^1[3,4,5,7,8]\\d{9}$"); //简单实现
		Pattern p = Pattern
				.compile("(^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[6-8])|(18[0-9]))\\d{8}$)|(^((170[0,1,5,7-9])|(171[8,9]))\\d{7}$)");// 完整实现

		System.out.println(p.matcher("18663998216").matches());
		System.out.println(p.matcher("17053998212").matches());
		System.out.println(p.matcher("17063998212").matches());
		System.out.println(p.matcher("17153998212").matches());
		System.out.println(p.matcher("17183998212").matches());
		System.out.println(p.matcher("w17053998212").matches());
	}
	
	public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
	
	 public static boolean isIDNumber(String IDNumber) {
	        if (IDNumber == null || "".equals(IDNumber)) {
	            return false;
	        }
	        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
	        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
	                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
	        boolean matches = IDNumber.matches(regularExpression);

	        //判断第18位校验值
	        if (matches) {
	            if (IDNumber.length() == 18) {
	                try {
	                    char[] charArray = IDNumber.toCharArray();
	                    //前十七位加权因子
	                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
	                    //这是除以11后，可能产生的11位余数对应的验证码
	                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
	                    int sum = 0;
	                    for (int i = 0; i < idCardWi.length; i++) {
	                        int current = Integer.parseInt(String.valueOf(charArray[i]));
	                        int count = current * idCardWi[i];
	                        sum += count;
	                    }
	                    char idCardLast = charArray[17];
	                    int idCardMod = sum % 11;
	                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
	                        return true;
	                    } else {
	                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() + 
	                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
	                        return false;
	                    }

	                } catch (Exception e) {
	                    e.printStackTrace();
	                    System.out.println("异常:" + IDNumber);
	                    return false;
	                }
	            }

	        }
	        return matches;
	    }
	 
		 /**
		  * 校验银行卡
		  * @param bankCard
		  * @return
		  */
	 	public static boolean checkBankCard(String bankCard) {  
	                  if(bankCard.length() < 15 || bankCard.length() > 19) {
	                      return false;
	                  }
	                  char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));  
	                  if(bit == 'N'){  
	                  return false;  
	              }  
	              return bankCard.charAt(bankCard.length() - 1) == bit;  
	     }  
		  
         /** 
          * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位 
          * @param nonCheckCodeBankCard 
          * @return 
          */  
         public static char getBankCardCheckCode(String nonCheckCodeBankCard){  
             if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0  
                     || !nonCheckCodeBankCard.matches("\\d+")) {  
                 //如果传的不是数据返回N  
                 return 'N';  
             }  
             char[] chs = nonCheckCodeBankCard.trim().toCharArray();  
             int luhmSum = 0;  
             for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {  
                 int k = chs[i] - '0';  
                 if(j % 2 == 0) {  
                     k *= 2;  
                     k = k / 10 + k % 10;  
                 }  
                 luhmSum += k;             
             }  
             return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');  
         }  
	 

     	public static final boolean isNotEmptyInteger(Integer data) {
     		return data != null && data > 0;
     	}
     	

     	// 检查字符串是否在某个数组里
    	public static final boolean checkStringIn(String checkStr, String[] strList) {
    		
    		for(String checkRole : strList){
    			if(checkRole.equalsIgnoreCase(checkStr)){
    				return true;
    			}
    		}
    		
    		return false;
    	}
	 
}
