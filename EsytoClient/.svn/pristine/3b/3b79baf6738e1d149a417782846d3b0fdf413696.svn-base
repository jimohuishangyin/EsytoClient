/*
 * 类文件名:  OrderInfo.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月21日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ec2.yspay.common.DateUtils;
import com.ec2.yspay.http.cash.OrderItem;

/**
 * 订单详情
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月21日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportExpandableListItem
{
    private int year,month,day;
    private String date;
    private List<OrderItem> orderList;
    /**
     * 获取 date
     * @return 返回 date
     */
    public String getDate()
    {
        return date;
    }
    /**
     * 设置 date
     * @param 对date进行赋值
     */
    public void setDate(String date)
    {
        this.date = date;
    }
    /**
     * 获取 orderList
     * @return 返回 orderList
     */
    public List<OrderItem> getOrderList()
    {
        return orderList;
    }
    /**
     * 设置 orderList
     * @param 对orderList进行赋值
     */
    public void setOrderList(List<OrderItem> orderList)
    {
        this.orderList = orderList;
    }
    /** 
     * <默认构造函数>
     */
    public ReportExpandableListItem()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    /** 
     * <默认构造函数>
     */
    public ReportExpandableListItem(int year,int month,int day, List<OrderItem> orderList)
    {
        super();
        this.date = getStrDate(year, month, day);
        this.orderList = orderList;
        this.year = year;
        this.month = month;
        this.day = day;
    }
    /** 
     * <默认构造函数>
     */
    public ReportExpandableListItem(int year,int month,int day)
    {
        super();
        this.date = getStrDate(year, month, day);
        this.orderList=null;
        this.year = year;
        this.month = month;
        this.day = day;
    }
    private String getStrDate(int mYear,int mMonth,int mDay){
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date=DateUtils.strToDate("yyyy-MM-dd", mYear+"-"+mMonth+"-"+mDay);
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return mDay+"日 - "+weekDays[w];
    }
    /**
     * 获取 year
     * @return 返回 year
     */
    public int getYear()
    {
        return year;
    }
    /**
     * 设置 year
     * @param 对year进行赋值
     */
    public void setYear(int year)
    {
        this.year = year;
    }
    /**
     * 获取 month
     * @return 返回 month
     */
    public int getMonth()
    {
        return month;
    }
    /**
     * 设置 month
     * @param 对month进行赋值
     */
    public void setMonth(int month)
    {
        this.month = month;
    }
    /**
     * 获取 day
     * @return 返回 day
     */
    public int getDay()
    {
        return day;
    }
    /**
     * 设置 day
     * @param 对day进行赋值
     */
    public void setDay(int day)
    {
        this.day = day;
    }
    
}
