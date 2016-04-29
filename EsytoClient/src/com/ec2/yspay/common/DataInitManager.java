/*
 * 类文件名:  DataInitManager.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年12月9日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import android.content.Context;

import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.GetAppPayConfResponse;
import com.ec2.yspay.http.task.GetAppPayConfTask;

/**
 * 数据准备
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年12月9日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataInitManager
{
    private Context mContext;
    private final static String TAG = "DataInitManager";
    public DataInitManager(Context context){
        mContext = context;
    }
    public void initData(){
        getAppPayConf("1005");
    }
    /**
     * 交易参数，银行卡支付参数获取channel_type 1005
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年12月9日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    private void getAppPayConf(String channel_type){
        GetAppPayConfTask task = new GetAppPayConfTask(mContext);
        task.setChannel_type(channel_type);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                GetAppPayConfResponse response = (GetAppPayConfResponse)obj;
                MyApplication.mDataCache.BILLS_MID = response.getMer_id();
                MyApplication.mDataCache.BILLS_TID = response.getTerm_id();
                Log.i(TAG, "银行卡参数 MID:"+MyApplication.mDataCache.BILLS_MID
                    +" ; TID:"+MyApplication.mDataCache.BILLS_TID);
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                GetAppPayConfResponse response = (GetAppPayConfResponse)obj;
                Log.e(TAG, "银行卡参数获取失败："+response.getResultDesc());
            }
        });
        task.execute();
    }
}
