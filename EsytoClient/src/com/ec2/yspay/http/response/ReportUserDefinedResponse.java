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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ec2.yspay.common.DateUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.ReportExpandableListItem;
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
public class ReportUserDefinedResponse extends Response
{
    private String totalAmount;
    private String totalItems;
    private List<ReportExpandableListItem> detailList = new ArrayList<ReportExpandableListItem>();
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
            detailList.clear();
            String inputJson = getResultJson();
            JSONObject jsonObject = new JSONObject(inputJson);
            totalAmount = jsonObject.getString("totalAmount");
            totalItems = jsonObject.getString("totalItems");
            String jsonStr = jsonObject.getString("posDetailMap");
            if(!Toolkits.isStrEmpty(jsonStr)&&!"null".equals(jsonStr)){
                JSONObject jsonArray = new JSONObject(jsonStr);
                for (Iterator iter = jsonArray.keys(); iter.hasNext();) { //先遍历整个 people 对象  
                    String tempDate = "";
                    List<OrderItem> orderList = new ArrayList<OrderItem>();
                    String key = (String)iter.next(); 
                    tempDate = key;
                    JSONArray jsonList = jsonArray.getJSONArray(key);
                    for(int k = 0; k < jsonList.length() ; k++){ 
                        JSONObject jsonItem = ((JSONObject)jsonList.opt(k));
                        String amount = jsonItem.getString("amount");
                        String channelType = jsonItem.getString("channelType");
                        String orderNo = jsonItem.getString("orderNo");
                        String orderTime = jsonItem.getString("orderTime");
                        String subject = jsonItem.getString("subject");
                        OrderItem item = new OrderItem(amount, channelType, orderNo, orderTime, subject);
                        orderList.add(item);
                    }
                    Date date = DateUtils.strToDate("yyyy-MM-dd", tempDate);
                    Calendar a=Calendar.getInstance();
                    a.setTime(date);
                    int year = a.get(Calendar.YEAR);
                    int month = a.get(Calendar.MONTH)+1;
                    int day = a.get(Calendar.DATE);
                    ReportExpandableListItem parentItem = new ReportExpandableListItem(year, month, day, orderList);
                    detailList.add(parentItem);
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
     * 获取 detailList
     * @return 返回 detailList
     */
    public List<ReportExpandableListItem> getDetailList()
    {
        return detailList;
    }
    /**
     * 设置 detailList
     * @param 对detailList进行赋值
     */
    public void setDetailList(List<ReportExpandableListItem> detailList)
    {
        this.detailList = detailList;
    }
    
    
    
}
