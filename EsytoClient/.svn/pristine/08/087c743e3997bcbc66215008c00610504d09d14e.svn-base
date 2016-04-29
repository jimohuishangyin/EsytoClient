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
public class OrderDetailResponse extends Response
{
    private String token;
    private String message;
    private String order_id;
    private String transaction_id;
    private String order_time;
    private String channel_type;
    private String voucher_id;
    private String amount;
    private String remark;
    private String week;
    private String shop_name;
    private String oper_name;
    private String status;
    private String refund_time;
    
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
            token = jsonObject.getString("token");
            message = jsonObject.getString("message");
            order_id = jsonObject.getString("order_id");
            transaction_id = jsonObject.getString("transaction_id");
            amount = jsonObject.getString("amount");
            order_time = jsonObject.getString("order_time");
            channel_type = jsonObject.getString("channel_type");
            voucher_id = jsonObject.getString("voucher_id");
            remark = jsonObject.getString("remark");
            week = jsonObject.getString("week");
            shop_name = jsonObject.getString("shop_name");
            oper_name = jsonObject.getString("oper_name");
            status = jsonObject.getString("status");
            refund_time =jsonObject.getString("refund_time");
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
    
    
	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMeeeage() {
		return message;
	}
	public void setMeeeage(String message) {
		this.message = message;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	public String getVoucher_id() {
		return voucher_id;
	}
	public void setVoucher_id(String voucher_id) {
		this.voucher_id = voucher_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
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


	public String getRefund_time() {
		return refund_time;
	}


	public void setRefund_time(String refund_time) {
		this.refund_time = refund_time;
	}
	
	
  
    
}
