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

import com.ec2.yspay.http.cash.LimitItem;
import com.ec2.yspay.http.cash.PrintDetailItem;
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
public class QrLimitMoneyResponse extends Response
{
    private List<LimitItem> limitList = new ArrayList<LimitItem>();
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
            JSONArray jsonArray = jsonObject.getJSONArray("quotaLists");
            if(jsonArray!=null){
                
                for(int i = 0; i < jsonArray.length() ; i++){ 
                    JSONObject jsonObj = ((JSONObject)jsonArray.opt(i));
//                    String companyCode = jsonObj.getString("companyCode");
                    String channelType = jsonObj.getString("channelType");
                    String dayLimit = jsonObj.getString("dayLimit");
                    String monthLimit = jsonObj.getString("monthLimit");
                    LimitItem item = new LimitItem(channelType, dayLimit, monthLimit);
                    limitList.add(item);
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
     * 获取 limitList
     * @return 返回 limitList
     */
    public List<LimitItem> getLimitList()
    {
        return limitList;
    }
    /**
     * 设置 limitList
     * @param 对limitList进行赋值
     */
    public void setLimitList(List<LimitItem> limitList)
    {
        this.limitList = limitList;
    }
    
}
