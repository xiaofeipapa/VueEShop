package org.xfh.dcore.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static final String DB_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; //所有数据库表的日期字段格式
	
	public static Date getChinaDate() {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		return cal.getTime();
	}
	public static Date getPreviousDate(Date now) {
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(now);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
	}

	public static String changeDateFormat(String dateStr, String oriFormat,String newFormat){
		Date d = parse(dateStr, oriFormat);
		if(d == null){
			return null;
		}
		return format(d, newFormat);
	}
 
	/**
	 * 需要保证输入的dateStr 符合 yyyyMMddHHmmss 格式
	 * @param dateStr
	 * @param newFormat
	 * @return
	 */
	public static String changeDBDateFormat(String dateStr, String newFormat){
		return changeDateFormat(dateStr, DB_TIME_FORMAT, newFormat);
	}

	public static String formatDBTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DB_TIME_FORMAT);
		return sdf.format(date);
	}

	public static Date parse(String dateStr, String patterString){
		SimpleDateFormat sdf = new SimpleDateFormat(patterString);
		Date d = null;
		try {
			d = sdf.parse(dateStr);
		} catch (ParseException e) {
			//do nothing
			e.printStackTrace();
		}
		return d;
	}
	public static String format(Date d,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	/**
	 * 返回当前时间的字符串
	 * 	格式：yyyyMMddhhmmss
	 *  eg：20151007100104
	 */
	public static String currentTimeForString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * 返回当前年月日
	 * @return
	 */
	public static String getYearMonthDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	/**
	 * 返回当前年-月-日
	 * @return
	 */
	public static String getYearMonthDay2(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	/**
	 * 返回距离当前时间 的时间
	 * 20170701 一个月前a=1  返回20170601
	 * @param a
	 * @return
	 */
	public static String getYearMonthDayBefore(int a){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
	    c.add(Calendar.MONTH, -a);
	    Date m = c.getTime();
	    String mon = format.format(m);
	    return mon;
	}
	
	

    /**
     * 获取指定日期时间
     *
     * @param date     指定日期
     * @param dayCount 指定天数
     *                 为正整数，代表指定日期以后的日期；
     *                 为负整数，代表指定日期以前的日期；
     * @return the date
     */
    public static Date getDayDate(Date date,int dayCount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, dayCount);
		date = calendar.getTime();
		return date;
	}

    /**
     * 获取指定年日期时间
     *
     * @param date     指定日期
     * @param dayCount 指定年书
     *                 为正整数，代表指定日期以后的日期；
     *                 为负整数，代表指定日期以前的日期；
     * @return the date
     */
    public static Date getYearDate(Date date,int dayCount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, dayCount);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取指定的月份日期
     *
     * @param date     指定日期
     * @param dayCount 指定年书
     *                 为正整数，代表指定日期以后的日期；
     *                 为负整数，代表指定日期以前的日期；
     * @return the date
     */
    public static Date getMonthDate(Date date,int dayCount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, dayCount);
        date = calendar.getTime();
        return date;
    }

	/**
	 * 获取指定的周
	 *
	 * @param date     指定日期
	 * @param dayCount 指定年书
	 *                 为正整数，代表指定日期以后的日期；
	 *                 为负整数，代表指定日期以前的日期；
	 * @return the date
	 */
	public static Date getWeekDate(Date date,int dayCount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_MONTH, dayCount);
		date = calendar.getTime();
		return date;
	}

    /**
     * 用户指定获取某年某月某日的日期
     *
     * @param year  the year    指定年份
     * @param month the month   指定月份
     * @param day   the day     指定天数（在指定月份中的天数）
     * @return the date
     */
    public static Date getCustomerDate(int year,int month,int day){
		Date date = new Date();

        Calendar calendar = Calendar.getInstance();
		calendar.set(year,month,day);
		date = calendar.getTime();
		return date;
	}


    /**
     * 用户指定获取某年某月某日 某时某分某秒的日期
     *
     * @param year   指定年份
     * @param month  指定月份
     * @param day    指定天数（在指定月份中的天数）
     * @param hour   指定小时
     * @param minute 指定分钟
     * @param second 指定描述
     * @return the date
     */
    public static Date getCustomerDateTime(int year,int month,int day,int hour,int minute,int second){
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,hour,minute,second);
        date = calendar.getTime();
        return date;
    }
}	
