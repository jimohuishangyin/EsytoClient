package com.ec2.yspay.activity;

import com.ec2.yspay.R;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.response.ValidSmsCoceResponse;
import com.ec2.yspay.http.task.RegisterTask;
import com.ec2.yspay.http.task.ValidSmsCoceTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistNextActivity extends BaseNoLoginActivity {

	Button bRegisterDone;
	private EditText etPwd1,etPwd2;
	private String userName;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_next_activity);
		userName = getIntent().getStringExtra("userName");
		((TextView)findViewById(R.id.tv_userName)).setText(userName);
		bRegisterDone=(Button) findViewById(R.id.register_done);
		bRegisterDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    String msg = validPwd(etPwd1.getText().toString(), etPwd2.getText().toString());
			    if(msg==null){
			        register(etPwd1.getText().toString());
			    }else{
			        showToast(msg);
			    }
				
				
				
			}
		});
		findViewById(R.id.register_cancel).setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
            }
        });
		etPwd1 = (EditText)findViewById(R.id.et_pwd01);
		etPwd2 = (EditText)findViewById(R.id.et_pwd02);
	}
	private String validPwd(String pwd1,String pwd2){
	    if(Toolkits.isStrEmpty(pwd1)||Toolkits.isStrEmpty(pwd2)){
	        return "密码不能为空！";
	    }else if(pwd1.length()<6){
	        return "密码长度不能小于6位！";
	    }else if(!pwd1.equals(pwd2)){
	        return "两次输入密码不同！";
	    }
	    return null;
	}
	private void register(String userPwd){
	    RegisterTask task = new RegisterTask(mContext, userName, userPwd);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                Intent i=new Intent();
                i.putExtra("userName", userName);
                i.putExtra("userPsw", etPwd2.getText().toString());
                i.setClass(RegistNextActivity.this, RegisterSuc.class);
                startActivity(i);
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