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
import com.ec2.yspay.http.response.UpdateCompanyMsgResponse;

/**
 * 修改用户密码
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UpdatePersonalPwdTask extends BaseTask
{
    private static final String url_left = "/app/account/modifyPass";
    private String oldPwd;
    private String newPwd;
    private String secPwd;
    private UpdateCompanyMsgResponse response;
    /** 
     * <默认构造函数>
     */
    public UpdatePersonalPwdTask(Context context)
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
            request.addParam("old_pass", oldPwd);
            request.addParam("new_pass", newPwd);
            request.addParam("sec_pass", secPwd);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        return request;
    }
    /**
     * 获取 oldPwd
     * @return 返回 oldPwd
     */
    public String getOldPwd()
    {
        return oldPwd;
    }
    /**
     * 设置 oldPwd
     * @param 对oldPwd进行赋值
     */
    public void setOldPwd(String oldPwd)
    {
        this.oldPwd = oldPwd;
    }
    /**
     * 获取 newPwd
     * @return 返回 newPwd
     */
    public String getNewPwd()
    {
        return newPwd;
    }
    /**
     * 设置 newPwd
     * @param 对newPwd进行赋值
     */
    public void setNewPwd(String newPwd)
    {
        this.newPwd = newPwd;
    }
    /**
     * 获取 secPwd
     * @return 返回 secPwd
     */
    public String getSecPwd()
    {
        return secPwd;
    }
    /**
     * 设置 secPwd
     * @param 对secPwd进行赋值
     */
    public void setSecPwd(String secPwd)
    {
        this.secPwd = secPwd;
    }
    
}
