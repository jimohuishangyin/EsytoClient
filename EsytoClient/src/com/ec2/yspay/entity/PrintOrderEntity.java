/*
 * 类文件名:  PrintEntity.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月23日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.entity;

import java.io.Serializable;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 罗洪祥
 * @version V001Z0001
 * @date 2015年10月23日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PrintOrderEntity implements Serializable {
	private String orderId;
	private String orderTime;
	private String account;
	private String amount;
	private String remark;
	private String voucherId;
	private String shopName;
	private String transactionId;
	private String channelType;
	private String shopCode;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	/**
	 * 获取 orderId
	 * 
	 * @return 返回 orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 设置 orderId
	 * 
	 * @param 对orderId进行赋值
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取 orderTime
	 * 
	 * @return 返回 orderTime
	 */
	public String getOrderTime() {
		return orderTime;
	}

	/**
	 * 设置 orderTime
	 * 
	 * @param 对orderTime进行赋值
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * 获取 account
	 * 
	 * @return 返回 account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置 account
	 * 
	 * @param 对account进行赋值
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取 remark
	 * 
	 * @return 返回 remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置 remark
	 * 
	 * @param 对remark进行赋值
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取 voucherId
	 * 
	 * @return 返回 voucherId
	 */
	public String getVoucherId() {
		return voucherId;
	}

	/**
	 * 设置 voucherId
	 * 
	 * @param 对voucherId进行赋值
	 */
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public PrintOrderEntity(String transactionId, String orderId,
			String orderTime, String account, String amount, String remark,
			String shopName, String channelType) {
		this.transactionId = transactionId;
		this.orderId = orderId;
		this.orderTime = orderTime;
		this.account = account;
		this.amount = amount;
		this.remark = remark;
		this.shopName = shopName;
		this.channelType = channelType;
	}

	/**
	 * <默认构造函数>
	 */
	public PrintOrderEntity(String orderId, String orderTime, String account,
			String remark, String voucherId) {
		super();
		this.orderId = orderId;
		this.orderTime = orderTime;
		this.account = account;
		this.remark = remark;
		this.voucherId = voucherId;
	}

	public PrintOrderEntity(String orderId, String orderTime, String account,
			String remark, String transactionId, String channelType) {
		super();
		this.orderId = orderId;
		this.orderTime = orderTime;
		this.account = account;
		this.remark = remark;
		this.transactionId = transactionId;
		this.channelType = channelType;
	}
	
	/*
	    *添加构造方法
	    *
	    * */
	    public PrintOrderEntity(String transactionId, String orderId, String orderTime,
	                            String account, String amount, String remark, String shopName, String channelType, String shopCode) {
	        this.transactionId = transactionId;
	        this.orderId = orderId;
	        this.orderTime = orderTime;
	        this.account = account;
	        this.amount = amount;
	        this.remark = remark;
	        this.shopName = shopName;
	        this.channelType = channelType;
	        this.shopCode = shopCode;
	    }

	/**
	 * <默认构造函数>
	 */
	public PrintOrderEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
