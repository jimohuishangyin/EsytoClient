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

import com.ec2.yspay.entity.PrintOne;
import com.ec2.yspay.entity.PrintOrderEntity;
import com.ec2.yspay.http.cash.PaytypeCountItem;
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
public class PrintDayAllResponse extends Response
{
    private String beginTime;
    private String endTime;
    private String totalAmount;
    private String totalItems;
    private String userName;
    private List<PaytypeCountItem> collectInfos = new ArrayList<PaytypeCountItem>();
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
            userName = jsonObject.getString("userName");
                
            beginTime = jsonObject.getString("beginTime");
            endTime = jsonObject.getString("endTime");
            totalAmount = jsonObject.getString("totalAmount");
            totalItems = jsonObject.getString("totalItems");
            JSONArray jsonArray = jsonObject.getJSONArray("collectInfos");
            if(jsonArray!=null){
                
                for(int i = 0; i < jsonArray.length() ; i++){ 
                    JSONObject jsonObj = ((JSONObject)jsonArray.opt(i));
                    String amountRate = jsonObj.getString("amountRate");
                    String channelType = jsonObj.getString("channelType");
                    String totalAmount = jsonObj.getString("totalAmount");
                    String totalItems = jsonObj.getString("totalItems");
                    PaytypeCountItem item = new PaytypeCountItem(amountRate, channelType, totalAmount, totalItems);
                    collectInfos.add(item);
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
     * 获取 beginTime
     * @return 返回 beginTime
     */
    public String getBeginTime()
    {
        return beginTime;
    }
    /**
     * 获取 endTime
     * @return 返回 endTime
     */
    public String getEndTime()
    {
        return endTime;
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
     * 获取 totalItems
     * @return 返回 totalItems
     */
    public String getTotalItems()
    {
        return totalItems;
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
     * 获取 collectInfos
     * @return 返回 collectInfos
     */
    public List<PaytypeCountItem> getCollectInfos()
    {
        return collectInfos;
    }
    
}
