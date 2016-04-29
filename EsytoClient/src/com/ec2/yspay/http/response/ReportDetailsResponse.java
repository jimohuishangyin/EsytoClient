/*
 * 类文件名:  GetQrCodeResponse.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月11日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.cash.OrderItem;
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
public class ReportDetailsResponse extends Response
{
    private String totalAmount;
    private String totalItems;
    private String week;
    private List<OrderItem> orderList = new ArrayList<OrderItem>();
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
            orderList.clear();
            String inputJson = getResultJson();
            JSONObject jsonObject = new JSONObject(inputJson);
            totalAmount = jsonObject.getString("totalAmount");
            totalAmount = jsonObject.getString("totalAmount");
            double d = Double.valueOf(totalAmount);
            if(d>100000){
                d = d/10000d;
                totalAmount = Toolkits.doubleFormat(d)+"万";
            }
            totalItems = jsonObject.getString("totalItems");
            week = jsonObject.getString("week");
            JSONArray jsonArray = jsonObject.getJSONArray("posDetail");
            if(jsonArray!=null){
                for(int i = 0; i < jsonArray.length() ; i++){ 
                    JSONObject jsonObj = ((JSONObject)jsonArray.opt(i));
                    String amount = jsonObj.getString("amount");
                    String channelType = jsonObj.getString("channelType");
                    String orderNo = jsonObj.getString("orderNo");
                    String orderTime = jsonObj.getString("orderTime");
                    String subject = jsonObj.getString("subject");
                    OrderItem item = new OrderItem(amount, channelType, orderNo, orderTime, subject);
                    orderList.add(item);
                }
            }
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
     * 获取 week
     * @return 返回 week
     */
    public String getWeek()
    {
        return week;
    }
    /**
     * 设置 week
     * @param 对week进行赋值
     */
    public void setWeek(String week)
    {
        this.week = week;
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
    
    
}
