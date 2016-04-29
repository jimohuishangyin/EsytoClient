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
import com.ec2.yspay.http.cash.StaffItem;

/**
 * 订单详情
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月21日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StaffExpandableListItem
{
    private int duty;
    private List<StaffItem> staffList;
    /**
     * 获取 duty
     * @return 返回 duty
     */
    public int getDuty()
    {
        return duty;
    }
    public String getDutyName(){
        String name="";
        if(duty==1)
            name = "管理员";
        else if(duty==2)
            name = "主管";
        else if(duty==3)
            name = "员工";
        return name;
    }
    /**
     * 设置 duty
     * @param 对duty进行赋值
     */
    public void setDuty(int duty)
    {
        this.duty = duty;
    }
    /**
     * 获取 staffList
     * @return 返回 staffList
     */
    public List<StaffItem> getStaffList()
    {
        return staffList;
    }
    /**
     * 设置 staffList
     * @param 对staffList进行赋值
     */
    public void setStaffList(List<StaffItem> staffList)
    {
        this.staffList = staffList;
    }
    /** 
     * <默认构造函数>
     */
    public StaffExpandableListItem(int duty, List<StaffItem> staffList)
    {
        super();
        this.duty = duty;
        this.staffList = staffList;
    }
    /** 
     * <默认构造函数>
     */
    public StaffExpandableListItem()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
}
