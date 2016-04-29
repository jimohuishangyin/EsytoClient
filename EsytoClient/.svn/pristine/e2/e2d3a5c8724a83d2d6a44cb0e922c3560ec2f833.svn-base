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

import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.RefundEntity;
import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.RefundResponse;
import com.ec2.yspay.http.response.TradeRefundResponse;
/**
 * 申请退款
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TradeRefundTask extends BaseTask
{
    private static final String url_left = "/app/trans";
    private TradeRefundResponse response;
    private RefundEntity refundEntity;
    private String payType;
    /** 
     * <默认构造函数>
     */
    public TradeRefundTask(Context context,RefundEntity refundEntity,String payType)
    {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.refundEntity = refundEntity;
        this.payType = payType;
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
        response = new TradeRefundResponse();
        
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
        request.addParam("mer_id",MyApplication.mDataCache.companyCode);
        request.addParam("request_type","6605");
//        request.addParam("channel_type",payType);
        request.addParam("order_id",refundEntity.getOrderId());
        request.addParam("refund_amount",refundEntity.getRefundAmount());
        request.addParam("refund_reason",refundEntity.getRefundReason());
        request.addParam("store_id",refundEntity.getStoreId());
        request.addParam("terminal_id",refundEntity.getTerminalId());
        request.addParam("op_user_id",MyApplication.mDataCache.UserPhoneNbr);
//        request.addParam("client_ip",Toolkits.getLocalIpAddress(mContext));
        return request;
    }
}
