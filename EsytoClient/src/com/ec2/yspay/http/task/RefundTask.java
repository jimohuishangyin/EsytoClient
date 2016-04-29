/*
 * 类文件名:  GetQrCodeTask.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月14日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.task;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.RefundEntity;
import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.RefundResponse;

import android.content.Context;
/**
 * 申请退款,计流水号
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RefundTask extends BaseTask
{
    private static final String url_left = "/app/pos/recordRefund";
    private RefundResponse response;
    private String orderId;
    private String amount;
    private String remark;
    /** 
     * <默认构造函数>
     */
    public RefundTask(Context context,String orderId,String amount,String remark)
    {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.orderId = orderId;
        this.amount = amount;
        this.remark = remark;
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
        response = new RefundResponse();
        
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
        request.addParam("order_id",orderId);
        request.addParam("refund_amount",amount);
        request.addParam("op_user_id",MyApplication.mDataCache.UserPhoneNbr);
        request.addParam("client_ip",Toolkits.getLocalIpAddress(mContext));
        try
        {
            request.addParam("remark",URLEncoder.encode(remark, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return request;
    }
}
