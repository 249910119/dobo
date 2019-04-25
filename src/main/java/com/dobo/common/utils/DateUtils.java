/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyyMM" ,"yyyyMMdd" ,"yyyy年MM月" ,"yyyy年MM月dd日"};

	/** 
     * 得到某日期的上个月的最后一天 
     * 
     * @Methods Name getLastDayOfLastMonth 
     * @return Date 
     */  
    public static Date getLastDayOfLastMonth(Date date) {
    	Date firstDayOfMonth = getFirstDayOfMonth(date);
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(firstDayOfMonth);
        cDay.add(Calendar.DAY_OF_MONTH, -1);
        return cDay.getTime();     
    }

	/** 
     * 得到本月第一天的日期 
     * @Methods Name getFirstDayOfMonth 
     * @return Date 
     */  
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cDay = Calendar.getInstance();     
        cDay.setTime(date);  
        cDay.set(Calendar.DAY_OF_MONTH, 1);  
        return cDay.getTime();     
    }
    
    /** 
     * 得到本月最后一天的日期 
     * @Methods Name getLastDayOfMonth 
     * @return Date 
     */  
    public static Date getLastDayOfMonth(Date date)   {     
        Calendar c = Calendar.getInstance();     
        c.setTime(date);  
        c.set(Calendar.DAY_OF_MONTH, getActualDaysByDate(c.getTime()));  
        return c.getTime();     
    }
    
	/**
	 * 返回某一日期所在月的实际天数
	 * 	1、3、5、7、8、10、12月：31天
	 * 	4、6、9、11月：30天
	 * 	2月：闰年29天，平年28天
	 * @param date 输入日期
	 * @return 天数
	 */
	public static int getActualDaysByDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		
		if((c.get(Calendar.MONTH) + 1) == 2){
			return new GregorianCalendar().isLeapYear(c.get(Calendar.YEAR)) ? 29 : 28;
		}else{
			return c.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24) + 1;
	}
	
	/**
	 * 获得两个日期之间的所有月份
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMonthBetween(Date minDate, Date maxDate) {
		ArrayList<String> result = new ArrayList<String>();

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(minDate);
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		
		max.setTime(maxDate);
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");//格式化为年月
		while (min.before(max)) {
			result.add(sdf1.format(min.getTime()));
			min.add(Calendar.MONTH, 1);
		}

		return result;
	}
	
	/**
	 * 获得加上数量的月份
	 * @param dateStr
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static String getAddMonth(String dateStr, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月

		Calendar calendar = Calendar.getInstance();

		try {
			calendar.setTime(sdf.parse(dateStr));
			calendar.add(Calendar.MONTH, num);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = System.currentTimeMillis()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
		System.out.println(formatDate(DateUtils.getLastDayOfLastMonth(DateUtils.parseDate("201606", "yyyyMM"))));
		System.out.println(getDistanceOfTwoDate(DateUtils.parseDate("2016/6/27"), DateUtils.parseDate("2017/6/26")));
		System.out.println(getMonthBetween(DateUtils.parseDate("2016/6/01"), DateUtils.parseDate("2016/8/27")));
		System.out.println(getDistanceOfTwoDate(DateUtils.parseDate("2016/6/01"), DateUtils.parseDate("2016/6/27")));
		System.out.println(getDistanceOfTwoDate(DateUtils.parseDate("2016/7/01"), DateUtils.parseDate("2016/7/31")));

		Date lastDayOfLastMonth = DateUtils.getLastDayOfLastMonth(DateUtils.parseDate("2016/06/27", "yyyy/MM/dd"));
		System.out.println(DateUtils.formatDate(lastDayOfLastMonth, "yyyyMM"));
	}
}
