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

import com.ec2.yspay.entity.PrintOrderEntity;
import com.ec2.yspay.http.cash.CashCollectItem;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;
import com.ec2.yspay.http.request.ClientResult;

/**
 * 7.4  获取商户对应所有门店信息
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月11日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GetCashCollectResponse extends Response
{
    private List<CashCollectItem> collectList = new ArrayList<CashCollectItem>();
    private String totalAmount;
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
            JSONArray jsonArray = jsonObject.getJSONArray("cashSets");
            if(jsonArray!=null){
                
                for(int i = 0; i < jsonArray.length() ; i++){ 
                    JSONObject jsonObj = ((JSONObject)jsonArray.opt(i));
                    String clearDate = jsonObj.getString("clearDate");
                    long alreadyClear = jsonObj.getLong("alreadyClear");
                    long notClear = jsonObj.getLong("notClear");
                    int status = jsonObj.getInt("status");
                    CashCollectItem item = new CashCollectItem(alreadyClear, notClear, clearDate, status);
                    collectList.add(item);
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
     * 获取 collectList
     * @return 返回 collectList
     */
    public List<CashCollectItem> getCollectList()
    {
        return collectList;
    }
    /**
     * 设置 collectList
     * @param 对collectList进行赋值
     */
    public void setCollectList(List<CashCollectItem> collectList)
    {
        this.collectList = collectList;
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
   
    
}
