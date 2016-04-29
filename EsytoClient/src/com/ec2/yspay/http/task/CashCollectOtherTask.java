/*
 * 类文件名:  GetQrCodeTask.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月14日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.task;

import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.http.MyEncoder;
import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.CashCollectOtherResponse;

import android.content.Context;

/**
 * 4.1  网关支付(支付宝/微信/翼支付)
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CashCollectOtherTask extends BaseTask
{
    private static final String url_left = "/app/trans";
    private CashCollectOtherResponse response;
    private int payType;
    private OrderInfo orderInfo;
    private String transDate;
    /** 
     * <默认构造函数>
     */
    public CashCollectOtherTask(Context context,int payType,OrderInfo orderInfo,String transDate)
    {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.payType = payType;
        this.orderInfo = orderInfo;
        this.transDate = transDate;
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
        response = new CashCollectOtherResponse();
        
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
        request.addParam("request_type","6609");
        request.addParam("channel_type",payType);
        request.addParam("amount",orderInfo.getAmount());
        request.addParam("subject",MyEncoder.getEncoderStr(orderInfo.getSubject()));
        request.addParam("body",MyEncoder.getEncoderStr(orderInfo.getBody()));
        request.addParam("req_source","1");
        request.addParam("trans_date",transDate);
        request.addParam("remark","");
        return request;
    }
}
