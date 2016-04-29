package com.ec2.yspay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ec2.yspay.R;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.CheckManagerPwdResponse;
import com.ec2.yspay.http.task.CheckManagerPwdTask;
import com.ec2.yspay.widget.MyTitle;

public class RefundManagerPwdActivity extends BaseActivity
{
    private String payType;
    private EditText etPwd;
    private MyTitle myTitle;
    private String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_manager_pwd);
        etPwd = (EditText)findViewById(R.id.et_pwd);
        payType = getIntent().getStringExtra("payType");
        mTitle = getIntent().getStringExtra("title");
        myTitle = (MyTitle)findViewById(R.id.rl_top);
        myTitle.setTitleText(mTitle);
    }
    public void onclick_sure(View v){
        checkPwd();
    }
    private void checkPwd(){
        CheckManagerPwdTask task = new CheckManagerPwdTask(mContext, etPwd.getText().toString());
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, RefundTradingNumberActivity.class);
                intent.putExtra("payType", payType);
                intent.putExtra("title", mTitle);
                startActivity(intent);
                finish();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                CheckManagerPwdResponse response = (CheckManagerPwdResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
}
