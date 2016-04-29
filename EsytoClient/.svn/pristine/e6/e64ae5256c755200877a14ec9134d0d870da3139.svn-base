package com.ec2.yspay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Setting;
import com.ec2.yspay.http.ErrorCode;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.LoginResponse;
import com.ec2.yspay.http.task.LoginTask;

public class RegisterSuc extends BaseNoLoginActivity {
	private Button mLoginButton;
	private String username;
	private String userpwd;
	private Class targetActivity = null;
	private Intent targetIntent = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registersuc);
		targetActivity = (Class)((Activity)mContext).getIntent().getSerializableExtra("activity");
        targetIntent =  this.getIntent();
		username = getIntent().getStringExtra("userName");
		userpwd = getIntent().getStringExtra("userPsw");
		mLoginButton = (Button) findViewById(R.id.login);
		mLoginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				login(username,userpwd);
			}
		});
	}
	
	private void login(final String userName,final String userPwd){
	    LoginTask task = new LoginTask(mContext, userName, userPwd);
	    task.setProgressVisiable(true);
	    task.setProgressMsg("努力登录中...");
	    task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                LoginResponse response = (LoginResponse)obj;
                MyApplication.mDataCache.token = response.getToken();
                MyApplication.mDataCache.companyCode = response.getCompanyCode();
                MyApplication.mDataCache.UserPhoneNbr = response.getAccount();
                MyApplication.mDataCache.companyCode = response.getCompanyCode();
                MyApplication.mDataCache.companyName = response.getCompanyName();
                MyApplication.mDataCache.duty = response.getDuty();
                MyApplication.mDataCache.shopCode = response.getShopCode();
                MyApplication.mDataCache.shopName = response.getShopName();
                MyApplication.mDataCache.userImage = response.getUserImage();
                MyApplication.mDataCache.userImageUrl = response.getUserImageUrl();
                MyApplication.mDataCache.userName = response.getUserName();
                MyApplication.mDataCache.lastLoginTime = response.getLastLoginTime();
                MyApplication.mDataCache.companyLogoImage = response.getCompanyLogoImage();
                MyApplication.mDataCache.companyLogoImageUrl = response.getCompanyLogoImageUrl();
                MyApplication.mDataCache.shopLogoImage = response.getShopLogoImage();
                MyApplication.mDataCache.shopLogoImageUrl = response.getShopLogoImageUrl();
                Setting.setUserName(mContext, userName);
                Setting.setUserPwd(mContext, userPwd);
                Setting.setToken(mContext, MyApplication.mDataCache.token);
                
                if(targetActivity!=null){//回到之前的页面
//                    targetIntent.setClass(mContext, targetActivity);
                    targetIntent.setClass(mContext, LoginSuc.class);
                    mContext.startActivity(targetIntent);
                }else{
                    Intent i=new Intent();
                    i.setClass(RegisterSuc.this, LoginSuc.class);
                    startActivity(i);
                }
                finish();
            }
            
            @Override
            public void onFail(Object obj)
            {
                LoginResponse response = (LoginResponse)obj;
                showToast("登录失败："+response.getResultDesc());
            }
        });
	    task.execute();
	}

}
