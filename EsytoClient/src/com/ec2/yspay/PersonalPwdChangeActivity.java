package com.ec2.yspay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.activity.LoginActivity;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.UpdateCompanyMsgResponse;
import com.ec2.yspay.http.task.UpdatePersonalPwdTask;

public class PersonalPwdChangeActivity extends BaseActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_pwd_change);
        final EditText tvPwdOld = (EditText)findViewById(R.id.et_pwd_old);
        final EditText tvPwdnew = (EditText)findViewById(R.id.et_pwd_new);
        final EditText tvPwdsec = (EditText)findViewById(R.id.et_pwd_sec);
        Button rlSure = (Button)findViewById(R.id.btn_sure);
        rlSure.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                String pwdOld = tvPwdOld.getText().toString().trim();
                String pwdNew = tvPwdnew.getText().toString().trim();
                String pwdSec = tvPwdsec.getText().toString().trim();
                if(Toolkits.isStrEmpty(pwdOld)){
                    showToast("请输入旧密码！");
                }else if(Toolkits.isStrEmpty(pwdNew)){
                    showToast("请输入新密码！");
                }else if(Toolkits.isStrEmpty(pwdNew)){
                    showToast("确认密码不能为空！");
                }else if(!pwdNew.equals(pwdSec)){
                    showToast("两次输入密码有误！");
                }else{
                    updatePwd(pwdOld, pwdNew, pwdSec);
                }
                
            }
        });
    }
    /**
     * 修改密码
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月5日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    private void updatePwd(String oldPwd,String newPwd,String secPwd){
        UpdatePersonalPwdTask task = new UpdatePersonalPwdTask(mContext);
        task.setNewPwd(newPwd);
        task.setOldPwd(oldPwd);
        task.setSecPwd(secPwd);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                showToast("密码修改成功，请重新登录！");
                
                MyApplication.toLoginActivity(mContext);
//                finish();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                UpdateCompanyMsgResponse response = (UpdateCompanyMsgResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
      
}
