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
public class UpdateStaffTask extends BaseTask
{
    private static final String url_left = "/app/account/updateEmployeeInfo";
    private BooleanResponse response;
    private String userName;
    private String shopCode;
    private String account;
    private String duty;
    private String status;
    /** 
     * <默认构造函数>
     */
    public UpdateStaffTask(Context context,String userName,String shopCode,String account,String duty)
    {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.userName = userName;
        this.shopCode = shopCode;
        this.account = account;
        this.duty = duty;
        
    }
    

	public void setStatus(String status) {
		this.status = status;
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
            request.addParam("userName",URLEncoder.encode(userName, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.addParam("account",account);
        request.addParam("duty",duty);
        request.addParam("status", status);
        return request;
    }
}
