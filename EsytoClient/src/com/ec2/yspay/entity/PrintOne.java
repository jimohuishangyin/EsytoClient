/*
 * 类文件名:  PrintOne.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月22日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.entity;

import com.ec2.yspay.common.Toolkits;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月22日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PrintOne
{
    private String companyName;
    private String companyCode;
    private String orderId;
    private String orderTime;
    private String transactionId;
    private String userName;
    private String channelType;
    private String amount;
    private String account;
    
    public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	/**
     * 获取 companyName
     * @return 返回 companyName
     */
    public String getCompanyName()
    {
        return companyName;
    }
    /**
     * 设置 companyName
     * @param 对companyName进行赋值
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    /**
     * 获取 companyCode
     * @return 返回 companyCode
     */
    public String getCompanyCode()
    {
        return companyCode;
    }
    /**
     * 设置 companyCode
     * @param 对companyCode进行赋值
     */
    public void setCompanyCode(String companyCode)
    {
        this.companyCode = companyCode;
    }
    /**
     * 获取 orderId
     * @return 返回 orderId
     */
    public String getOrderId()
    {
        return orderId;
    }
    /**
     * 设置 orderId
     * @param 对orderId进行赋值
     */
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    /**
     * 获取 orderTime
     * @return 返回 orderTime
     */
    public String getOrderTime()
    {
        return orderTime;
    }
    /**
     * 设置 orderTime
     * @param 对orderTime进行赋值
     */
    public void setOrderTime(String orderTime)
    {
        this.orderTime = orderTime;
    }
    /**
     * 获取 transactionId
     * @return 返回 transactionId
     */
    public String getTransactionId()
    {
        return transactionId;
    }
    /**
     * 设置 transactionId
     * @param 对transactionId进行赋值
     */
    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }
    /**
     * 获取 userName
     * @return 返回 userName
     */
    public String getUserName()
    {
        return userName;
    }
    /**
     * 设置 userName
     * @param 对userName进行赋值
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
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
     * 获取 amount
     * @return 返回 amount
     */
    public String getAmount()
    {
        return amount;
    }
    /**
     * 设置 amount
     * @param 对amount进行赋值
     */
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    /** 
     * <默认构造函数>
     */
    public PrintOne(String companyName, String companyCode, String orderId, String orderTime, String transactionId,
        String userName, String channelType, String amount)
    {
        super();
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.transactionId = transactionId;
        this.userName = userName;
        
        this.channelType = PayTypeEntity.getPayName(channelType);
        this.amount = amount;
        if(Toolkits.isStrEmpty(this.companyName)){
            this.companyName = "未设置商户名";
        }
        if(Toolkits.isStrEmpty(this.userName)){
            this.userName = "未设置";
        }
    }
    public PrintOne(String companyName, String companyCode, String orderId, String orderTime, String transactionId,
            String userName, String channelType, String amount,String account)
        {
            super();
            this.companyName = companyName;
            this.companyCode = companyCode;
            this.orderId = orderId;
            this.orderTime = orderTime;
            this.transactionId = transactionId;
            this.userName = userName;
            this.account = account;
            this.channelType = PayTypeEntity.getPayName(channelType);
            this.amount = amount;
            if(Toolkits.isStrEmpty(this.companyName)){
                this.companyName = "未设置商户名";
            }
            if(Toolkits.isStrEmpty(this.userName)){
                this.userName = "未设置";
            }
        }
    /** 
     * <默认构造函数>
     */
    public PrintOne()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
}
