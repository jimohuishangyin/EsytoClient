package com.ec2.yspay.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Setting;
import com.ec2.yspay.common.VersionInfo;
import com.ec2.yspay.http.ErrorCode;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.LoginResponse;
import com.ec2.yspay.http.task.LoginTask;
import com.ec2.yspay.widget.WarnPopupOneDialog;
import com.ec2.yspay.widget.WarnPopupWhiteRedDialog;

public class LoginActivity extends BaseNoLoginActivity {

	Button enteryRegister,btLogin,btForgetPassword;
	public static final String PHONE_NUM="phone_num";
	private EditText etUserName,etUserPwd;
	private Class targetActivity = null;
    private Intent targetIntent = new Intent();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		VersionInfo.GetVersion(this);
		targetActivity = (Class)((Activity)mContext).getIntent().getSerializableExtra("activity");
        targetIntent =  this.getIntent();
		etUserName = (EditText)findViewById(R.id.et_userName);
		etUserPwd = (EditText)findViewById(R.id.et_userPwd);
        btForgetPassword=(Button) findViewById(R.id.forget_password);
        btForgetPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				i.setClass(LoginActivity.this, ForgetPasswordActivity.class);
				i.putExtra(PHONE_NUM, etUserName.getText().toString().trim());
				startActivity(i);
				
			}
		});
		String phone = getIntent().getStringExtra("userName");
		if(phone!=null){
			etUserName.setText(phone);
		}
		else{
		    etUserName.setText(Setting.getUserName(mContext));
		    
		}
		etUserName.setSelection(etUserName.getText().length());
		etUserPwd.setText("");
		enteryRegister=(Button) findViewById(R.id.entery_register);
		enteryRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				i.setClass(LoginActivity.this, RegisterActivity.class);
				i.putExtra(PHONE_NUM, etUserName.getText().toString().trim());
				startActivity(i);
				
			}
		});
		
		btLogin=(Button) findViewById(R.id.login);
		btLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    String userName = etUserName.getText().toString().trim();
			    String userPwd = etUserPwd.getText().toString().trim();
			    if(isEmpty(userName)){
			        showToast("请输入手机号");
			        return;
			    }
			    if(isEmpty(userPwd)){
			        showToast("请输入密码");
                    return;
			    }
			    login(userName, userPwd);
				
				
			}
		});
	}
	/**
	 * 重载方法
	 */
	@Override
	protected void onResume()
	{
	    // TODO Auto-generated method stub
	    super.onResume();
	}
	private boolean isEmpty(String str){
	    if(str==null||"".equals(str))
	        return true;
	    else return false;
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
                // TODO Auto-generated method stub
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
                    i.setClass(LoginActivity.this, LoginSuc.class);
                    startActivity(i);
                }
                finish();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                LoginResponse response = (LoginResponse)obj;
                String code = response.getCode();
                if(ErrorCode.PWD_ERROR.equals(code)){
                    String snum = response.getFailNum();
                    int failNum = Integer.valueOf(snum);
                    showPwdErrorDialog(5-failNum);
                }else if(ErrorCode.USERLOCKED_ERROR.equals(code)){
                    showLockedDialog();
                }else if(ErrorCode.USERSTOP_ERROR.equals(code)){
                    String time = response.getDisableTimes();
                    showStopedDialog(time);
                }else{
                    showToast("登录失败："+response.getResultDesc());
                }
            }
        });
	    task.execute();
	}
	private void showPwdErrorDialog(int time) {

        final WarnPopupOneDialog.Builder builder = new WarnPopupOneDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("密码错误，您还可以输入"+time+"次。\n"
            + "密码错误超过5次系统将自动锁定该账号。");
        builder.setPositiveButton("重新输入", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
                dialog.dismiss();
                etUserPwd.setText("");
                etUserPwd.requestFocus();
                
            }
        });

        builder.create().show();
    }
	private void showLockedDialog() {

        final WarnPopupWhiteRedDialog.Builder builder = new WarnPopupWhiteRedDialog.Builder(mContext);
        //builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
        builder.setTitle("提示");
        builder.setMessage("您的账号已被锁定！\n"
            + "请联系管理员或通过手机验证码进行解锁。");
        builder.setNegativeButton("确定",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    
                        
                    }
                });
        builder.setPositiveButton("验证码解锁", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
                dialog.dismiss();
                Intent intent = new Intent(mContext, ValidCodeActivity.class);
                intent.putExtra("state", ValidCodeActivity.STATE_LOCK);
                startActivity(intent);
            }
        });

        builder.create().show();
    }
	private void showStopedDialog(String time) {

        final WarnPopupWhiteRedDialog.Builder builder = new WarnPopupWhiteRedDialog.Builder(mContext);
        //builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
        builder.setTitle("提示");
        builder.setMessage("您的账号于"+time
            +"由管理员停用！请联系管理员恢复账号使用或者通过手机验证码进行账号解绑。");
        builder.setNegativeButton("确定",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    
                        
                    }
                });
        builder.setPositiveButton("验证码解绑", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
                dialog.dismiss();
                Intent intent = new Intent(mContext, ValidCodeActivity.class);
                intent.putExtra("state", ValidCodeActivity.STATE_UNBUND);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
	
}
