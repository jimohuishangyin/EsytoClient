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
import com.ec2.yspay.entity.PrintOrderEntity;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;
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
public class GetStaffListResponse extends Response
{
    private List<ShopItem> shopList = new ArrayList<ShopItem>();
    private List<StaffItem> staffList = new ArrayList<StaffItem>();
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
            JSONArray jsonArray = jsonObject.getJSONArray("accounts");
            if(jsonArray!=null){
                
                for(int i = 0; i < jsonArray.length() ; i++){ 
                    JSONObject jsonObj = ((JSONObject)jsonArray.opt(i));
                    String account = jsonObj.getString("account");
                    int duty ;
                    String sduty = jsonObj.getString("duty");
                    if(Toolkits.isStrEmpty(sduty)){
                    	duty = 3;
                    }else{
                    	duty = Integer.valueOf(sduty);
                    }
                    String status = jsonObj.getString("status");
                    String userName = jsonObj.getString("userName");
                    String shopCode = jsonObj.getString("shopCode");
                    StaffItem item = new StaffItem(account, duty, status, userName);
                    item.setShopCode(shopCode);
                    item.setUserImage(jsonObj.getString("userImage"));
                    staffList.add(item);
                }
            }
            JSONArray jsonArray2 = jsonObject.getJSONArray("shops");
            if(jsonArray2!=null){
                for(int i = 0; i < jsonArray2.length() ; i++){
                    JSONObject jsonObj = ((JSONObject)jsonArray2.opt(i));
                    String shopName = jsonObj.getString("shopName");
                    String shopCode = jsonObj.getString("shopCode");
                    ShopItem item = new ShopItem(shopName,shopCode);
                    shopList.add(item);
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
     * 获取 staffList
     * @return 返回 staffList
     */
    public List<StaffItem> getStaffList()
    {
        return staffList;
    }
    /**
     * 设置 staffList
     * @param 对staffList进行赋值
     */
    public void setStaffList(List<StaffItem> staffList)
    {
        this.staffList = staffList;
    }
    /**
     * 获取 shopList
     * @return 返回 shopList
     */
    public List<ShopItem> getShopList()
    {
        return shopList;
    }
    /**
     * 设置 shopList
     * @param 对shopList进行赋值
     */
    public void setShopList(List<ShopItem> shopList)
    {
        this.shopList = shopList;
    }
    
    
    
}