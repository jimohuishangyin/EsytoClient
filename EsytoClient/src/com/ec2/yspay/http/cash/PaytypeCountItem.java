/*
 * 类文件名:  PrintAllItem.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月26日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.cash;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月26日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PaytypeCountItem
{
    private String amountRate;
    private String channelType;
    private String totalAmount;
    private String totalItems;
    /**
     * 获取 amountRate
     * @return 返回 amountRate
     */
    public String getAmountRate()
    {
        return amountRate;
    }
    /**
     * 设置 amountRate
     * @param 对amountRate进行赋值
     */
    public void setAmountRate(String amountRate)
    {
        this.amountRate = amountRate;
    }
    /**
     * 获取 channelType
     * @return 返回 channelType
     */
    public String getChannelType()
    {
        return channelType;
    }
    /**
     * 设置 channelType
     * @param 对channelType进行赋值
     */
    public void setChannelType(String channelType)
    {
        this.channelType = channelType;
    }
    /**
     * 获取 totalAmount
     * @return 返回 totalAmount
     */
    public String getTotalAmount()
    {
        return totalAmount;
    }
    /**
     * 设置 totalAmount
     * @param 对totalAmount进行赋值
     */
    public void setTotalAmount(String totalAmount)
    {
        this.totalAmount = totalAmount;
    }
    /**
     * 获取 totalItems
     * @return 返回 totalItems
     */
    public String getTotalItems()
    {
        return totalItems;
    }
    /**
     * 设置 totalItems
     * @param 对totalItems进行赋值
     */
    public void setTotalItems(String totalItems)
    {
        this.totalItems = totalItems;
    }
    /** 
     * <默认构造函数>
     */
    public PaytypeCountItem(String amountRate, String channelType, String totalAmount, String totalItems)
    {
        super();
        this.amountRate = amountRate;
        this.channelType = channelType;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }
    
}
