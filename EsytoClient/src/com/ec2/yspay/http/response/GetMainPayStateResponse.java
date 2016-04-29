/*
 * 类文件名:  GetQrCodeResponse.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月11日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.response;

import org.json.JSONObject;

import com.ec2.yspay.common.Toolkits;
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
public class GetMainPayStateResponse extends Response
{
    private String totalAmount;
    private String totalItems;
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
            totalAmount = jsonObject.getString("totalAmount");
            double d = Double.valueOf(totalAmount);
            if(d>100000){
                d = d/10000d;
                totalAmount = Toolkits.doubleFormat(d)+"万";
            }
            totalItems = jsonObject.getString("totalItems");
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
    
}
