package com.ec2.yspay.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ec2.yspay.R;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.SubmitResponse;
import com.ec2.yspay.http.task.SubmitTask;

public class OpenServerActivity extends BaseActivity {

	protected static String EncoderName = null;
	private EditText mNameEditText,mNumEditText,mQQEditText,mEmailEditext;
	private Button mSubmitButton,mServerButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_server);
		initview();
	}
	
	private void initview(){
		mNameEditText = (EditText) findViewById(R.id.name_edittext);
		mNumEditText = (EditText) findViewById(R.id.num_edittext);
		mQQEditText = (EditText) findViewById(R.id.qq_edittext);
		mEmailEditext = (EditText) findViewById(R.id.email_edittext);
		mSubmitButton = (Button) findViewById(R.id.submit);
		mServerButton = (Button) findViewById(R.id.server);
		mServerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext,SubmitServerActivity.class));
			}
		});
		mSubmitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = mNameEditText.getText().toString().trim();
				String num = mNumEditText.getText().toString().trim();
				String qq = mQQEditText.getText().toString().trim();
				String email = mEmailEditext.getText().toString().trim();
				if(isEmpty(name)){
					ToastUtils.show(mContext, "请输入您的姓名");
					return;
				}
				if (isEmpty(num)||(num.length()!=11)) {
					ToastUtils.show(mContext, "请输入正确的手机号");
					return;
				}
				try {
					EncoderName = URLEncoder.encode(name, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				submit(EncoderName, num, qq, email);
			}
		});
	}
	
	private boolean isEmpty(String str){
	    if(str==null||"".equals(str))
	        return true;
	    else return false;
	}
	
	private void submit(String name,String num,String qq,String email){
		SubmitTask task  = new SubmitTask(mContext, name, num, qq, email);
		task.setOnTaskFinished(new OnTaskFinished() {
			
			@Override
			public void onSucc(Object obj) {
				SubmitResponse response = (SubmitResponse)obj;
				if(response.getProcessId().equals("1")){
					ToastUtils.show(mContext, "提交中");
				}else if(response.getProcessId().equals("2")){
					ToastUtils.show(mContext, "提交成功，等待客服联系");
				}else if(response.getProcessId().equals("3")){
					ToastUtils.show(mContext, "结单");
				}else if(response.getProcessId().equals("4")){
					ToastUtils.show(mContext, "挂起");
				}
				finish();
			}
			
			@Override
			public void onFail(Object obj) {
				ToastUtils.show(mContext, "提交失败");
				finish();
			}
		});
		task.execute();
	}
	
}
