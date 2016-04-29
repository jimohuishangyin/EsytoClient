/*
 * 类文件名:  GetQrCodeTask.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月14日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.task;

import java.net.URLEncoder;

import android.content.Context;

import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.GetCompanyMsgResponse;
import com.ec2.yspay.http.response.UpdateCompanyMsgResponse;

/**
 * 预下单请求,获取二维码信息
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UpdateCompanyMsgTask extends BaseTask
{
    private static final String url_left = "/app/company/updateCompany";
    private String companyName;
    private String compAddress;
    private String province;
    private String city;
    private String linkman;
    private String compPhone;
    private UpdateCompanyMsgResponse response;
    /** 
     * <默认构造函数>
     */
    public UpdateCompanyMsgTask(Context context)
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
        response = new UpdateCompanyMsgResponse();
        
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
        try
        {
            request.addParam("companyName", URLEncoder.encode(companyName, "UTF-8"));
            request.addParam("compAddress", URLEncoder.encode(compAddress, "UTF-8"));
            request.addParam("province", URLEncoder.encode(province, "UTF-8"));
            request.addParam("city", URLEncoder.encode(city, "UTF-8"));
            request.addParam("linkman", URLEncoder.encode(linkman, "UTF-8"));
            request.addParam("compPhone", compPhone);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        return request;
    }
    /**
     * 获取 companyName
     * @return 返回 companyName
     */
    public String getCompanyName()
    {
        return companyName;
    }
    /**
     * 设置 companyName
     * @param 对companyName进行赋值
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    /**
     * 获取 compAddress
     * @return 返回 compAddress
     */
    public String getCompAddress()
    {
        return compAddress;
    }
    /**
     * 设置 compAddress
     * @param 对compAddress进行赋值
     */
    public void setCompAddress(String compAddress)
    {
        this.compAddress = compAddress;
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
     * 获取 response
     * @return 返回 response
     */
    public UpdateCompanyMsgResponse getResponse()
    {
        return response;
    }
    /**
     * 设置 response
     * @param 对response进行赋值
     */
    public void setResponse(UpdateCompanyMsgResponse response)
    {
        this.response = response;
    }
    /**
     * 获取 urlLeft
     * @return 返回 urlLeft
     */
    public static String getUrlLeft()
    {
        return url_left;
    }
    /**
     * 获取 linkman
     * @return 返回 linkman
     */
    public String getLinkman()
    {
        return linkman;
    }
    /**
     * 设置 linkman
     * @param 对linkman进行赋值
     */
    public void setLinkman(String linkman)
    {
        this.linkman = linkman;
    }
    /**
     * 获取 compPhone
     * @return 返回 compPhone
     */
    public String getCompPhone()
    {
        return compPhone;
    }
    /**
     * 设置 compPhone
     * @param 对compPhone进行赋值
     */
    public void setCompPhone(String compPhone)
    {
        this.compPhone = compPhone;
    }
    
}
