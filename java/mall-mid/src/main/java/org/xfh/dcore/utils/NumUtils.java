package org.xfh.dcore.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.util.StringUtils;

/**
 * 处理数字的工具类
 * 
 * @author diego
 * 
 */
public class NumUtils {

    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
    private static Random randGen = new Random();

	private static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
			"十亿", "百亿", "千亿", "万亿" };
	private static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

	public static Integer parseInt(Object obj,int defaultValue){
		if(!isDigit(obj)){
			return new Integer(defaultValue);
		}
		return Integer.valueOf(obj.toString().trim());
	}

	public static Integer parseInt(Object obj){
		int defaultValue = 0;
		if(!isDigit(obj)){
			return new Integer(defaultValue);
		}
		return Integer.valueOf(obj.toString().trim());
	}
	
	public static long parseLong(String obj){
		long val = 0;
		try{
			val = Long.parseLong(obj);
		} catch (Exception e){
			val = 0l;
		}
		return val;
	}

	public static float parseFloat(String obj){
		float val = 0;
		try{
			val = Float.parseFloat(obj);
		}catch(Exception ex){
			val = 0;
		}
		return val;
	}
	
	   public static String randomString(int length) {
	        if (length < 1) {
	            return null;
	        }
	        // Create a char buffer to put random letters and numbers in.
	        char[] randBuffer = new char[length];
	        for (int i = 0; i < randBuffer.length; i++) {
	            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
	        }
	        return new String(randBuffer);
	    }

	public static boolean isDigit(Object obj) {
		if (obj == null) {
			return false;
		}
		String str = obj.toString().trim();
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static Integer safeParse(Object obj,int defaultValue){
		if(!isDigit(obj)){
			return new Integer(defaultValue);
		}
		return Integer.valueOf(obj.toString().trim());
	}

	public static Integer safeParse(Object obj){
		int defaultValue = 0;
		if(!isDigit(obj)){
			return new Integer(defaultValue);
		}
		return Integer.valueOf(obj.toString().trim());
	}
	
	public static String safeParseIntToString(Object obj){
		try{
			Double objDouble=Double.valueOf(obj.toString().trim());
			return String.valueOf(objDouble.longValue());
		}catch(Exception e){
			return obj.toString();
		}

	}
	
	public static int safeParseFloatToInt(String  value){
		if(StringUtils.isEmpty(value)){
			return 0;
		}

		try{
			float floatValue=Float.valueOf(value);
			return Integer.valueOf((int)floatValue);
		}catch(Exception e){
			return 0;
		}
		
	}
	
	public static BigDecimal safeParseFloatToBigDecimal(String  value){
		if(StringUtils.isEmpty(value)){
			return new BigDecimal(0);
		}

		try{
			float floatValue=Float.valueOf(value);
			return new BigDecimal(floatValue);
		}catch(Exception e){
			 return new BigDecimal(0);
		}
		
	}
	
	/**
	 * 货币兑换方法
	 * 
	 * @param currency		原始货币
	 * @param rate				汇率
	 * @return						兑换后货币
	 */
	public static BigDecimal exchange(BigDecimal currency, BigDecimal rate){
		return currency.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal USToCH(BigDecimal us, BigDecimal rate){
		if(us == null)
			return BigDecimal.ZERO;
		BigDecimal price = NumUtils.exchange(us, rate);
		if(price != null)
			price.setScale(2, RoundingMode.HALF_UP);
		return price;//人民币兑换为美元
	}
	
	public static BigDecimal CHToUS(BigDecimal ch, BigDecimal rate){
		if(ch == null)
			return BigDecimal.ZERO;
		if(rate == null)
			return ch;
		BigDecimal price = NumUtils.exchange(ch, new BigDecimal(1).divide(rate, 20, BigDecimal.ROUND_HALF_UP));
		if(price != null)
			price.setScale(2, RoundingMode.HALF_UP);
		return price;//人民币兑换为美元
	}
	
	public static final boolean isFloat(String value){
		if(StringUtils.isEmpty(value)){
			return false;
		}
		
		try{
			Float.valueOf(value);
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	
	public static Float safeParseFloat(String value){
		if(!isFloat(value)){
			return 0f;
		}
		return Float.valueOf(value);
	}


	/**
	 * 带四舍五入的格式化保留N位小数点的小数方法
	 *
	 * @param number       保留几位小数点
	 * @param targetNumber 要格式化的小数
	 * @return 四舍五入的格式化保留N位小数点的小数
	 */
	public static double formatKeepDecimalPointForNumberWithRound(int number, double targetNumber){

		StringBuffer pointNumberText = new StringBuffer("######0.");
		for(int index = 0; index < number; index++){
			pointNumberText.append("0");
		}

		DecimalFormat df = new DecimalFormat(pointNumberText.toString());

		return Double.valueOf(df.format(targetNumber));
	}

	/**
	 * 格式化金额
	 *
	 * @param number       需要保留的小数点位数
	 * @param targetNumber the target number
	 * @param isRMB true 代表是人民币，前边会添加￥符号;false 代表是美元，前边会添加$符号
	 * @return 格式化后的金额
	 */
	public static String formatMoneyReturnString(int number, double targetNumber, boolean isRMB){

		StringBuffer pointNumberText = new StringBuffer();
		if(isRMB){
			pointNumberText.append("￥###,###.");
		}else{
			pointNumberText.append("$###,###.");
		}
		for(int index = 0; index < number; index++){
			pointNumberText.append("#");
		}

		DecimalFormat df = new DecimalFormat(pointNumberText.toString());

		return df.format(targetNumber);
	}

	public static String formatIntegerToChines(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
			// not need process if the last digital bits is 0
					continue;
				} else {
			// no unit for 0
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}
	public static boolean greaterThanZero(BigDecimal value) {
		return value != null && value.compareTo(BigDecimal.ZERO) > 0;
	}
	
	public static final BigDecimal showBigDecimal(BigDecimal db, int scale){
		return db.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
	
	// 显示小数点后2位
	public static final BigDecimal toFix2(BigDecimal db){
		return db.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static final BigDecimal toFix(BigDecimal db, int num){
		return db.setScale(num, BigDecimal.ROUND_HALF_UP);
	}
	
	public static final boolean isEuqal(Integer one, Integer two){
		if(one == null || two == null)return false;
		
		return one.intValue() == two.intValue();
	}

	public static final boolean isEmpty(BigDecimal data){
		return data == null || data.intValue() == 0;
	}

	
	// 生成6位随机码
	public static final String makeRandom6Digit() {
		int num = 6;
		String code="";
        Random r = new Random(new Date().getTime());
        for(int i=0;i< num ;i++){
            code = code+r.nextInt(10);
        }
        return code;
	}
	
	// 显示小数点后2位,不四舍五入
	public static final BigDecimal toFix2NotRound(BigDecimal db){
		return db.setScale(2, BigDecimal.ROUND_DOWN);
	}

	public static final BigDecimal parse(String s){
		try {
			return new BigDecimal(s.trim());
		}catch (Exception e) {

		}
		return null;
	}	
	
	
}
