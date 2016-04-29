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

import android.util.Log;

import com.ec2.yspay.entity.PrintOrderEntity;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.ShopManagerItem;
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
public class GetShopListResponse extends Response
{
    private List<ShopItem> shopList = new ArrayList<ShopItem>();
    private String companyLogoImageUrl;
    private String companyLogoImage;
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
            companyLogoImage = jsonObject.getString("companyLogoImage");
            companyLogoImageUrl = jsonObject.getString("companyLogoImageUrl");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if(jsonArray!=null){
                
                for(int i = 0; i < jsonArray.length() ; i++){ 
                    JSONObject jsonObj = ((JSONObject)jsonArray.opt(i));
                    String shopName = jsonObj.getString("shopName");
                    String shopCode = jsonObj.getString("shopCode");
                    String address = jsonObj.getString("address");
                    String city = jsonObj.getString("city");
                    String logoImage = jsonObj.getString("logoImage");
                    String phone = jsonObj.getString("phone");
                    String province = jsonObj.getString("province");
                    String headcount = jsonObj.getString("headcount");
                    String shoplogoImageurl = jsonObj.getString("shoplogoImageurl");
                    ShopItem item = new ShopItem(shopName, shopCode, address, city, logoImage, phone, province,headcount,shoplogoImageurl);
                    List<ShopManagerItem> managers = new ArrayList<ShopManagerItem>();
                    JSONArray managerArray = jsonObj.getJSONArray("accounts");
                    if(managerArray!=null){
                    	for(int j = 0; j < managerArray.length() ; j++){ 
                    		JSONObject obj = ((JSONObject)managerArray.opt(j));
                    		if(obj==null)continue;
                    		String userName = obj.getString("userName");
                    		String account = obj.getString("account");
                    		ShopManagerItem mItem = new ShopManagerItem(userName, account, "");
                    		managers.add(mItem);
                    	}
                    }
                    item.setManagerList(managers);
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
    /**
     * 获取 companyLogoImage
     * @return 返回 companyLogoImage
     */
    public String getCompanyLogoImage()
    {
        return companyLogoImage;
    }
    /**
     * 设置 companyLogoImage
     * @param 对companyLogoImage进行赋值
     */
    public void setCompanyLogoImage(String companyLogoImage)
    {
        this.companyLogoImage = companyLogoImage;
    }
    /**
     * 获取 companyLogoImageUrl
     * @return 返回 companyLogoImageUrl
     */
    public String getCompanyLogoImageUrl()
    {
        return companyLogoImageUrl;
    }
    /**
     * 设置 companyLogoImageUrl
     * @param 对companyLogoImageUrl进行赋值
     */
    public void setCompanyLogoImageUrl(String companyLogoImageUrl)
    {
        this.companyLogoImageUrl = companyLogoImageUrl;
    }
    
    
}
