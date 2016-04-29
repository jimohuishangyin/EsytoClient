/*
 * 类文件名:  GetQrCodeTask.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月14日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.task;

import android.content.Context;

import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.ReportUserDefinedResponse;
/**
 * 报表--员工
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportUserDefinedTask extends BaseTask
{
    private static final String url_left = "/app/pos/customReportDetails";
    private ReportUserDefinedResponse response;
    private String account="";
    private String channelType="";
    private String beginTime="";
    private String endTime="";
    private String shopCode;
    /** 
     * <默认构造函数>
     */
    public ReportUserDefinedTask(Context context,String beginTime,String endTime)
    {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.beginTime = beginTime;
        this.endTime = endTime;
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
        response = new ReportUserDefinedResponse();
        
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
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
    private ClientRequest getRequest(){
        ClientRequest request = new ClientRequest(url_left);
        request.addParam("account",account);
        request.addParam("shopCode", shopCode);
        request.addParam("begin_time",beginTime);
        request.addParam("end_time",endTime);
        request.addParam("channel_type",channelType);
        return request;
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
     * 获取 channelType
     * @return 返回 channelType
     */
    public String getChannelType()
    {
        return channelType;
    }
    /**
     * 设置 channelType
     * @param 对channelType进行赋值
     */
    public void setChannelType(String channelType)
    {
        this.channelType = channelType;
    }
    /**
     * 获取 beginTime
     * @return 返回 beginTime
     */
    public String getBeginTime()
    {
        return beginTime;
    }
    /**
     * 设置 beginTime
     * @param 对beginTime进行赋值
     */
    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }
    /**
     * 获取 endTime
     * @return 返回 endTime
     */
    public String getEndTime()
    {
        return endTime;
    }
    /**
     * 设置 endTime
     * @param 对endTime进行赋值
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
}
