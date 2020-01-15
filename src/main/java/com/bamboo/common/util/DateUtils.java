/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bamboo.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private final static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM"};

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
		long t = System.currentTimeMillis() -date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = System.currentTimeMillis() -date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取当前时间到过去时间的分钟
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
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	/**
	 * 获取指定日期的时间戳
	 * @param timeStr="1970-01-06 11:45:55";  
	 * @return
	 */
	public static long  getTimeStamp(String  timeStr) {
		//SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	   // String time="1970-01-06 11:45:55";  
	    Date date =  parseDate(timeStr); 
		return date.getTime()/1000;
	}
	
	/**
	 * 获取指定时间戳的日期
	 * @param timeStr="1970-01-06 11:45:55";  
	 * @return
	 */
	public static Date  getDateTime(Long  timeStr) {
		//SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	   // String time="1970-01-06 11:45:55";  
		Date date = new Date(timeStr);
		return date;
	}
	
	/***
	 * 获取指定时间的下一天时间
	 * @param date
	 * @return
	 */
	public static Date getNextDayTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		return date;
	}
	
	/***
	 * 获取指定时间的上一天时间
	 * @param date
	 * @return
	 */
	public static Date getLastDayTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}


	/**
	 * 获取本月开始时间
	 *
	 * @return
	 */
	public static Date getCurrentMonthStartTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		return date;
	}


	/**
	 * 获取当前时间点多少年之后的时间
	 *
	 * @param year
	 * @return
	 */
	public static Date getAfterByYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, year);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 获取当前时间点多少月之后的时间
	 *
	 * @param month
	 * @return
	 */
	public static Date getAfterByMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		Date date = calendar.getTime();
		return date;
	}


	/**
	 * 获取当前时间点多少天之后的时间
	 *
	 * @param day
	 * @return
	 */
	public static Date getAfterByDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		Date date = calendar.getTime();
		return date;
	}



	/**
	 * 获取当前时间点多少小时之后的时间
	 *
	 * @param hourse
	 * @return
	 */
	public static Date getAfterByHourse(int hourse) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, hourse);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 获取某个时间点后多少小时之后的时间
	 *
	 * @param hourse
	 * @return
	 */
	public static Date getAfterDateByHourse(Date  startDate,int hourse) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.HOUR, hourse);
		Date date = calendar.getTime();
		return date;
	}


	/**
	 * 获取多少分钟之前的时间
	 *
	 * @param minute
	 * @return
	 */
	public static Date getBeforeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, -minute);
		Date date = calendar.getTime();
		return date;
	}



	/**
	 * 获取开始时间到结束时间的剩余时间
	 *
	 * @return
	 */
	public static String getSurplusTime(Date startTime, Date endTime) {
		//计算剩余天数
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startTime);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endTime);
		long l = cal2.getTimeInMillis() - cal1.getTimeInMillis();

		Integer ss = 1000;//秒
		Integer mi = ss * 60;//分
		Integer hh = mi * 60;//时
		Integer dd = hh * 24;//天

		long day = l / dd;
		long hour = (l - day * dd) / hh;
		long minute = (l - day * dd - hour * hh) / mi;

		Map<String, Object> keyword5 = new HashMap<String, Object>();// 温馨提示
		StringBuffer timeStr = new StringBuffer();
		if (day > 0) {
			timeStr.append(day + "天");
		}
		if (hour > 0 && day <= 0) {
			timeStr.append(hour + "小时");
		}
		if (minute > 0 && day <= 0) {
			timeStr.append(minute + "分钟");
		}
		return timeStr.toString();
	}


	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = System.currentTimeMillis() -parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
		
		
//		System.out.println(getTimeStamp("2017-06-08 19:41:37"));
//		System.out.println(DateUtils.getDateTime(Long.parseLong("1490254294000")));
//		int a=(int) ((Long.parseLong("1498838399000"))/1000);
//		System.out.println(formatDateTime(getNextDayTime(new Date())));
		System.out.println(getAfterByHourse(2));

	}
}
