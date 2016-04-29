package com.ec2.yspay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.AsyncImageLoader;
import com.ec2.yspay.common.DataInitManager;
import com.ec2.yspay.common.MyApplication;

public class LoginSuc extends Activity {
	private TextView tvName,tvUserName,tvLastLoginName;
	private ImageView mCompanyImageView;
	private AsyncImageLoader asyImg = new AsyncImageLoader(this);
	private Button mLoginButton;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_suc);
		tvName = (TextView) findViewById(R.id.tv_companyname);
		tvUserName = (TextView) findViewById(R.id.loginname);
		tvUserName.setText(MyApplication.mDataCache.userName);
		tvLastLoginName = (TextView) findViewById(R.id.lastlogintime);
		tvLastLoginName.setText(MyApplication.mDataCache.lastLoginTime);
		mLoginButton = (Button) findViewById(R.id.login);
		mLoginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(LoginSuc.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		tvName.setText(MyApplication.mDataCache.companyName+"欢迎您！");
		mCompanyImageView = (ImageView) findViewById(R.id.company_logo);
		asyImg.LoadImage(MyApplication.mDataCache.companyLogoImageUrl,
				MyApplication.mDataCache.companyLogoImage, mCompanyImageView,
				getResources().getDrawable(R.drawable.nopic));
		mHandler.sendEmptyMessageDelayed(1, 600);
		/**
		 * 数据准备
		 */
		DataInitManager dataInitManager = new DataInitManager(this);
		dataInitManager.initData();
	}

	private Handler mHandler = new Handler() {

		/**
		 * 重载方法
		 * 
		 * @param msg
		 */
		@Override
		public void handleMessage(Message msg) {
			Intent i = new Intent();
			i.setClass(LoginSuc.this, MainActivity.class);
			startActivity(i);
			finish();
		}

	};
}