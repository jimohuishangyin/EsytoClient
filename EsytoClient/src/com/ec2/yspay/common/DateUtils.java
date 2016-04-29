/*
 * 类文件名:  DateUtils.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年11月1日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年11月1日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DateUtils
{
    public static int getYear(){
        Calendar a=Calendar.getInstance();
        return a.get(Calendar.YEAR);
//        System.out.println(a.get(Calendar.YEAR));//得到年
//        System.out.println(a.get(Calendar.MONTH)+1);//由于月份是从0开始的所以加1
//        System.out.println(a.get(Calendar.DATE));
    }
    public static int getMonth(){
        Calendar a=Calendar.getInstance();
        return (a.get(Calendar.MONTH)+1);
    }
    public static int getDay(){
        Calendar a=Calendar.getInstance();
        return a.get(Calendar.DATE);
    }
 // 字符串类型日期转化成date类型  
    public static Date strToDate(String style, String date) {  
        SimpleDateFormat formatter = new SimpleDateFormat(style);  
        try {  
            return formatter.parse(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return new Date();  
        }  
    }  
      
    public static String dateToStr(String style, Date date) {  
        SimpleDateFormat formatter = new SimpleDateFormat(style);  
        return formatter.format(date);  
    } 
    public static int getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(lastDate);
        return localCalendar.get(Calendar.DAY_OF_MONTH);
    }
    public static Long getDaysBetween(Date startDate, Date endDate) {  
        Calendar fromCalendar = Calendar.getInstance();  
        fromCalendar.setTime(startDate);  
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        fromCalendar.set(Calendar.MINUTE, 0);  
        fromCalendar.set(Calendar.SECOND, 0);  
        fromCalendar.set(Calendar.MILLISECOND, 0);  
  
        Calendar toCalendar = Calendar.getInstance();  
        toCalendar.setTime(endDate);  
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        toCalendar.set(Calendar.MINUTE, 0);  
        toCalendar.set(Calendar.SECOND, 0);  
        toCalendar.set(Calendar.MILLISECOND, 0);  
  
        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);  
    }
    
}
