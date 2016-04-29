package com.ec2.yspay.test;

import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.response.GetQrCodeResponse;
import com.ec2.yspay.http.response.TestResponse;
import com.ec2.yspay.http.task.AddFeedbackTask;
import com.ec2.yspay.http.task.GetQrCodeTask;
import com.ec2.yspay.http.task.TestTask;

import android.content.Context;
import android.widget.Toast;

/**
 * 接口测试类
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月15日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestInterface {

	private Context mContext;
	
	public TestInterface(Context context) {
		mContext = context;
	}
	private void showToast(String str){
        ToastUtils.show(mContext,str);
	}
	public void test(){
	    TestTask task = new TestTask(mContext);
	    task.setProgressVisiable(true);
	    task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                TestResponse response = (TestResponse)obj;
                showToast(response.getResultDesc());
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                TestResponse response = (TestResponse)obj;
                showToast(response.getResultDesc());
            }
        });
	    task.execute();
	}
	public void loginTest(){
        TestTask task = new TestTask(mContext);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                TestResponse response = (TestResponse)obj;
                showToast(response.getResultDesc());
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                TestResponse response = (TestResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
	//2.1    预下单请求
	public void getQRCodeTest(){
	    OrderInfo orderInfo = new OrderInfo("1");
	    GetQrCodeTask task = new GetQrCodeTask(mContext, 1001, orderInfo,"");
	    task.setProgressVisiable(true);
	    task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                GetQrCodeResponse response = (GetQrCodeResponse)obj;
                showToast(response.getResultDesc());
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                GetQrCodeResponse response = (GetQrCodeResponse)obj;
                showToast(response.getResultDesc());
            }
        });
	    task.execute();
	}
	//2.1    预下单请求
		public void feedbackTest(){
		    AddFeedbackTask task = new AddFeedbackTask(mContext, "测试内容");
		    task.setProgressVisiable(true);
		    task.setOnTaskFinished(new OnTaskFinished()
	        {
	            
	            @Override
	            public void onSucc(Object obj)
	            {
	                // TODO Auto-generated method stub
	            	BooleanResponse response = (BooleanResponse)obj;
	                showToast(response.getResultDesc());
	            }
	            
	            @Override
	            public void onFail(Object obj)
	            {
	                // TODO Auto-generated method stub
	            	BooleanResponse response = (BooleanResponse)obj;
	                showToast(response.getResultDesc());
	            }
	        });
		    task.execute();
		}
}