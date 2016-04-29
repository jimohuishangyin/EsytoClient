/*
 * 类文件名:  GetQrCodeResponse.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月11日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ec2.yspay.common.Log;
import com.ec2.yspay.http.request.ClientResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月11日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QrOrderMsgResponse extends Response
{
    
    private String orderId;
    private String orderTime;
    private String channelType;
    private String voucherId;
    private String amount;
    private String transaction_id;
    private String remark;
    private String shop_name;
    private String oper_name;
    private String status;
    private String week;
    /**
     * 重载方法
     * @param result
     * @return
     */
    @Override
    public boolean parseResult(ClientResult result)
    {
        // TODO Auto-generated method stub
        boolean res = parseCR(result);
        if(isSuccess())
        try {
            String inputJson = getResultJson();
            JSONObject jsonObject = new JSONObject(inputJson);
            orderTime = jsonObject.getString("order_time");
            orderId = jsonObject.getString("order_id");
            channelType = jsonObject.getString("channel_type");
            voucherId = jsonObject.getString("voucher_id");
            amount = jsonObject.getString("amount");
            transaction_id = jsonObject.getString("transaction_id");
            remark = jsonObject.getString("remark");
            shop_name = jsonObject.getString("shop_name");
            oper_name = jsonObject.getString("oper_name");
            status = jsonObject.getString("status");
            week = jsonObject.getString("week");
            res = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            res = false;
            setIsSuccess(false);
            setResultCode("002");
            setResultDesc("解析失败");
            return false;
        }
        return res;
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
     * 获取 voucherId
     * @return 返回 voucherId
     */
    public String getVoucherId()
    {
        return voucherId;
    }
    /**
     * 设置 voucherId
     * @param 对voucherId进行赋值
     */
    public void setVoucherId(String voucherId)
    {
        this.voucherId = voucherId;
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
     * 获取 transaction_id
     * @return 返回 transaction_id
     */
    public String getTransaction_id()
    {
        return transaction_id;
    }
    /**
     * 设置 transaction_id
     * @param 对transaction_id进行赋值
     */
    public void setTransaction_id(String transaction_id)
    {
        this.transaction_id = transaction_id;
    }
    /**
     * 获取 remark
     * @return 返回 remark
     */
    public String getRemark()
    {
        return remark;
    }
    public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	/**
     * 设置 remark
     * @param 对remark进行赋值
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    
    
}
