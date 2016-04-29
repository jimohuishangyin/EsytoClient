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

import com.ec2.yspay.entity.PrintOne;
import com.ec2.yspay.entity.PrintOrderEntity;
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
public class PrintByOrderidResponse extends Response
{
	private PrintOrderEntity orderItem;
    private String companyName;
    private String companyCode;
    private String orderId;
    private String orderTime;
    private String transactionId;
    private String userName;
    private String channelType;
    private String amount;
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
            String orderId = jsonObject.getString("order_id");
            String orderTime = jsonObject.getString("order_time");
            String account = jsonObject.getString("account");
            String amount = jsonObject.getString("amount");
            String shopName = jsonObject.getString("shopName");
            String remark = "";
            try
            {
                remark = jsonObject.getString("remark");
            }
            catch (Exception e)
            {
                // TODO: handle exception
                e.printStackTrace();
            }
            String transactionId = jsonObject.getString("transaction_id");
            String channelType = jsonObject.getString("channel_type");
            String shopCode = jsonObject.getString("shopCode");
            orderItem = new PrintOrderEntity(transactionId,orderId,orderTime,account,
                    amount,remark,shopName,channelType,shopCode);
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
    public PrintOrderEntity getPrintMsg() {
        return orderItem;
    }
}
