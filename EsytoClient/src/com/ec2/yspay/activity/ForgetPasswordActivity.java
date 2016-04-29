package com.ec2.yspay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ec2.yspay.R;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.SendSmsCodeResponse;
import com.ec2.yspay.http.response.ValidSmsCoceResponse;
import com.ec2.yspay.http.task.SendSmsCodeTask;
import com.ec2.yspay.http.task.ValidSmsCoceTask;

public class ForgetPasswordActivity extends BaseNoLoginActivity {
	private Button bNextStep;
	private Button btnGetCode;
	private TimeCount time;
	private EditText etUserName, etCode;
	private String sessionId;
	private boolean isGetSmsCode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
		bNextStep = (Button) findViewById(R.id.register_next_step);
		etUserName = (EditText) findViewById(R.id.et_userName);
		etUserName.setText(getIntent().getExtras().getString(
				LoginActivity.PHONE_NUM));
		etUserName.setSelection(etUserName.getText().length());
		etCode = (EditText) findViewById(R.id.et_code);
		bNextStep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mobiles = etUserName.getText().toString().trim();
				String code = etCode.getText().toString().trim();
				if (Toolkits.isMobileNO(mobiles)) {
					if (Toolkits.isStrEmpty(code)) {
						showToast("请输入短信验证码");
					} else {
						validSmsCode(code);
					}
				} else {
					showToast("请输入正确的手机号");
				}
			}
		});
		btnGetCode = (Button) findViewById(R.id.btn_getcode);
		if ((getIntent().getExtras().getString(LoginActivity.PHONE_NUM))
				.length() == 11) {
			btnGetCode.setEnabled(true);
		}else{
			btnGetCode.setEnabled(false);
		}
		etUserName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() == 11) {
					btnGetCode.setEnabled(true);
				} else {
					btnGetCode.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		btnGetCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mobiles = etUserName.getText().toString().trim();
				if (Toolkits.isMobileNO(mobiles)) {
					btnGetCode.setEnabled(false);
					sendSmsCode(mobiles);

				} else {
					showToast("请输入正确的手机号");
				}
			}
		});
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btnGetCode.setText("获取验证码");
			btnGetCode.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btnGetCode.setText(millisUntilFinished / 1000 + "秒");
		}
	}

	private void sendSmsCode(String mobile) {
		SendSmsCodeTask task = new SendSmsCodeTask(mContext, mobile,
				SendSmsCodeTask.TYPE_RESET_PASSWORD);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				// TODO Auto-generated method stub
				SendSmsCodeResponse response = (SendSmsCodeResponse) obj;
				sessionId = response.getSessionId();

				time.start();// 开始计时
				btnGetCode.setEnabled(false);
			}

			@Override
			public void onFail(Object obj) {
				// TODO Auto-generated method stub
				SendSmsCodeResponse response = (SendSmsCodeResponse) obj;
				showToast(response.getResultDesc());
				btnGetCode.setEnabled(true);
			}
		});
		task.execute();
	}

	private void validSmsCode(String smsCode) {
		ValidSmsCoceTask task = new ValidSmsCoceTask(mContext, sessionId,
				smsCode);
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("userName", etUserName.getText().toString().trim());
				i.setClass(ForgetPasswordActivity.this,
						ResetPasswordActivity.class);
				startActivity(i);
			}

			@Override
			public void onFail(Object obj) {
				// TODO Auto-generated method stub
				ValidSmsCoceResponse response = (ValidSmsCoceResponse) obj;
				showToast(response.getResultDesc());
			}
		});
		task.execute();
	}
}
