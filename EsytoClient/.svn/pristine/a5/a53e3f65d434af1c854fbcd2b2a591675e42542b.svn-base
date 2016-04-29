/*
 * 类文件名:  GetQrCodeTask.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月14日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.task;

import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.http.MyEncoder;
import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.CashOrCardResponse;

import android.content.Context;

/**
 * 现金支付，银行卡支付，或者现金归集银行卡支付
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CashOrCardTask extends BaseTask
{
    private static final String url_left = "/app/trans";
    private CashOrCardResponse response;
    private int payType;
    private OrderInfo orderInfo;
    private String remark;
    //现金归集字段
    private String trans_date;
    private boolean isCollect = false;
    private String posId;

    /** 
     * <默认构造函数>
     */
    public CashOrCardTask(Context context,int payType,OrderInfo orderInfo,String remark)
    {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.payType = payType;
        this.orderInfo = orderInfo;
        this.remark = remark;
    }
    
    /**
     * 获取 trans_date
     * @return 返回 trans_date
     */
    public String getTrans_date()
    {
        return trans_date;
    }

    /**
     * 设置 trans_date
     * @param 对trans_date进行赋值
     */
    public void setTrans_date(String trans_date)
    {
        this.trans_date = trans_date;
    }

    /**
     * 获取 isCollect
     * @return 返回 isCollect
     */
    public boolean isCollect()
    {
        return isCollect;
    }

    /**
     * 设置 isCollect
     * @param 对isCollect进行赋值
     */
    public void setCollect(boolean isCollect)
    {
        this.isCollect = isCollect;
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
        response = new CashOrCardResponse();
        
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
    
    
    /**
     * 设置 posId
     * @param 对posId进行赋值
     */
    public void setPosId(String posId)
    {
        this.posId = posId;
    }

    private ClientRequest getRequest(){
        ClientRequest request = new ClientRequest(url_left);
        request.addParam("mer_id",MyApplication.mDataCache.companyCode);
        request.addParam("request_type","6608");
        request.addParam("channel_type",payType);
        request.addParam("amount",orderInfo.getAmount());
        request.addParam("subject",MyEncoder.getEncoderStr(orderInfo.getSubject()));
        request.addParam("body",MyEncoder.getEncoderStr(orderInfo.getBody()));
        request.addParam("goods_detail",orderInfo.getGoods_detail());
        request.addParam("operator_id",MyApplication.mDataCache.UserPhoneNbr);
        request.addParam("store_id",orderInfo.getStore_id());
        request.addParam("terminal_id",Toolkits.getIMEI(mContext));
        request.addParam("client_ip",Toolkits.getLocalIpAddress(mContext));
        request.addParam("remark",MyEncoder.getEncoderStr(remark));
        request.addParam("transaction_id",posId);
        if(isCollect){
            request.addParam("trans_date",trans_date);
            request.addParam("req_source","1");
        }
        
        return request;
    }
}
