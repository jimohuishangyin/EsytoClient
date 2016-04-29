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
public class GetQrCodeResponse extends Response
{
    private String qrCode;
    private String orderId;
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
            qrCode = jsonObject.getString("code_url");
            orderId = jsonObject.getString("orderId");
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
     * 获取 qrCode
     * @return 返回 qrCode
     */
    public String getQrCode()
    {
        return qrCode;
    }
    /**
     * 设置 qrCode
     * @param 对qrCode进行赋值
     */
    public void setQrCode(String qrCode)
    {
        this.qrCode = qrCode;
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
    
    
}
