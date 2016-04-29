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
public class UpdatePersonalMsgTask extends BaseTask
{
    private static final String url_left = "/app/account/updateAccount";
    private String userName;
    private String account;
    private String sex;
    private String province;
    private String city;
    private String birthday;
    private String nickName;
    private UpdateCompanyMsgResponse response;
    /** 
     * <默认构造函数>
     */
    public UpdatePersonalMsgTask(Context context)
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
            request.addParam("userName", URLEncoder.encode(userName, "UTF-8"));
            request.addParam("account", account);
            request.addParam("sex", URLEncoder.encode(sex, "UTF-8"));
            request.addParam("province", URLEncoder.encode(province, "UTF-8"));
            request.addParam("city", URLEncoder.encode(city, "UTF-8"));
            request.addParam("birthday", birthday);
            request.addParam("nickName", URLEncoder.encode(nickName, "UTF-8"));
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        return request;
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
     * 设置 userName
     * @param 对userName进行赋值
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    /**
     * 获取 account
     * @return 返回 account
     */
    public String getAccount()
    {
        return account;
    }
    /**
     * 设置 account
     * @param 对account进行赋值
     */
    public void setAccount(String account)
    {
        this.account = account;
    }
    /**
     * 获取 sex
     * @return 返回 sex
     */
    public String getSex()
    {
        return sex;
    }
    /**
     * 设置 sex
     * @param 对sex进行赋值
     */
    public void setSex(String sex)
    {
        this.sex = sex;
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
     * 获取 birthday
     * @return 返回 birthday
     */
    public String getBirthday()
    {
        return birthday;
    }
    /**
     * 设置 birthday
     * @param 对birthday进行赋值
     */
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
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
     * 获取 nickName
     * @return 返回 nickName
     */
    public String getNickName()
    {
        return nickName;
    }
    /**
     * 设置 nickName
     * @param 对nickName进行赋值
     */
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    
}
