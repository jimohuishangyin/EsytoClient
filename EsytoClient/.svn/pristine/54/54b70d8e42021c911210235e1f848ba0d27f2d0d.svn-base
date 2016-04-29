/*
 * 类文件名:  UploadFileTask.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年11月5日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import java.io.File;

import com.ec2.yspay.http.OnTaskFinished;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * 上传文件任务
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年11月5日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UploadFileTask extends AsyncTask<String, Integer, Boolean>
{
    private  ProgressDialog mProgressDialog;  
    private  Context context=null; 
    protected OnTaskFinished mOnTaskFinished;
    private String mProgressMsg = "请稍候...";
    private boolean mIsProgressVisiable = false;
    private String filePath,uploadUrl;
    
     public UploadFileTask(Context mContext){
         this.context=mContext;  
         mProgressDialog=new ProgressDialog(context);  
     }
     /**
     * 重载方法
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        try {
            if (mProgressDialog != null)
                mProgressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mOnTaskFinished != null) {
            if (result) {
                mOnTaskFinished.onSucc(result);
            } else {
                mOnTaskFinished.onFail(result);
            }
        }
    }
    /**
     * 重载方法
     */
    @Override
    protected void onPreExecute()
    {
        // TODO Auto-generated method stub
        super.onPreExecute();
        try {
            if (mIsProgressVisiable) {
                mProgressDialog.setCancelable(true);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setMessage(mProgressMsg);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        
        File file=new File(filePath);  
        return UploadFileTools.uploadFile(file, uploadUrl); 
    }
    public void setOnTaskFinished(OnTaskFinished onTaskFinished) {
        mOnTaskFinished = onTaskFinished;
    }
    public void setProgressVisiable(boolean enable) {
        mIsProgressVisiable = enable;
    }

    public void setProgressMsg(String msg) {
        mProgressMsg = msg;
    }
    /**
     * 获取 filePath
     * @return 返回 filePath
     */
    public String getFilePath()
    {
        return filePath;
    }
    /**
     * 设置 filePath
     * @param 对filePath进行赋值
     */
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    /**
     * 获取 uploadUrl
     * @return 返回 uploadUrl
     */
    public String getUploadUrl()
    {
        return uploadUrl;
    }
    /**
     * 设置 uploadUrl
     * @param 对uploadUrl进行赋值
     */
    public void setUploadUrl(String uploadUrl)
    {
        this.uploadUrl = uploadUrl;
    }
    
}
