/*
 * 类文件名:  RegisterTask.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月14日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.task;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.LoginResponse;
import com.ec2.yspay.http.response.BooleanResponse;

import android.content.Context;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UpdateStoreTask extends BaseTask
{
    private static final String url_left = "/app/shop/updateShopInfo";
    private BooleanResponse response;
    private String shopCode;
    private String address;
    private String shopName;
    private String phone;
    private String province;
    private String city;
    /** 
     * <默认构造函数>
     */
    public UpdateStoreTask(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        
    }
    /**
     * 重载方法
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(String... params)
    {
        // TODO Auto-generated method stub
        response = new BooleanResponse();
        
        ClientResult cResult=client.build(getRequest()).post();
        response.parseResult(cResult);
        
        return response.isSuccess();
    }
    /**
     * 重载方法
     * @param rslt
     */
    @Override
    protected void onPostExecute(Boolean rslt) {
        // TODO Auto-generated method stub
        super.onPostExecute(rslt);
        //执行完做封装处理，取出自己要的字段
        
        if (mOnTaskFinished != null) {
            if (rslt) {
                mOnTaskFinished.onSucc(response);
            } else {
                mOnTaskFinished.onFail(response);
            }
        }
    }
    private ClientRequest getRequest(){
        ClientRequest request = new ClientRequest(url_left);
        request.addParam("shopCode",shopCode);
        try
        {
            request.addParam("address",URLEncoder.encode(address, "UTF-8"));
            request.addParam("shopName",URLEncoder.encode(shopName, "UTF-8"));
            request.addParam("phone",phone);
            request.addParam("province",URLEncoder.encode(province, "UTF-8"));
            request.addParam("city",URLEncoder.encode(city, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return request;
    }
    /**
     * 获取 response
     * @return 返回 response
     */
    public BooleanResponse getResponse()
    {
        return response;
    }
    /**
     * 设置 response
     * @param 对response进行赋值
     */
    public void setResponse(BooleanResponse response)
    {
        this.response = response;
    }
    /**
     * 获取 address
     * @return 返回 address
     */
    public String getAddress()
    {
        return address;
    }
    /**
     * 设置 address
     * @param 对address进行赋值
     */
    public void setAddress(String address)
    {
        this.address = address;
    }
    /**
     * 获取 shopName
     * @return 返回 shopName
     */
    public String getShopName()
    {
        return shopName;
    }
    /**
     * 设置 shopName
     * @param 对shopName进行赋值
     */
    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }
    /**
     * 获取 phone
     * @return 返回 phone
     */
    public String getPhone()
    {
        return phone;
    }
    /**
     * 设置 phone
     * @param 对phone进行赋值
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    /**
     * 获取 province
     * @return 返回 province
     */
    public String getProvince()
    {
        return province;
    }
    /**
     * 设置 province
     * @param 对province进行赋值
     */
    public void setProvince(String province)
    {
        this.province = province;
    }
    /**
     * 获取 city
     * @return 返回 city
     */
    public String getCity()
    {
        return city;
    }
    /**
     * 设置 city
     * @param 对city进行赋值
     */
    public void setCity(String city)
    {
        this.city = city;
    }
    /**
     * 获取 shopCode
     * @return 返回 shopCode
     */
    public String getShopCode()
    {
        return shopCode;
    }
    /**
     * 设置 shopCode
     * @param 对shopCode进行赋值
     */
    public void setShopCode(String shopCode)
    {
        this.shopCode = shopCode;
    }
    
}
