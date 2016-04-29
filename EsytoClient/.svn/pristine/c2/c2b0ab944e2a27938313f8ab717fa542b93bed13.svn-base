package com.ec2.yspay;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.task.AddFeedbackTask;

public class FeedBackActivity extends BaseActivity {
	
	private EditText mfeedbackEditText;
	private Button mSubmitButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		mfeedbackEditText = (EditText) findViewById(R.id.feedback_et);
		mfeedbackEditText.setFocusable(true);
		mfeedbackEditText.setFocusableInTouchMode(true);
		mfeedbackEditText.requestFocus();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) mfeedbackEditText
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(mfeedbackEditText, 0);
			}
		}, 500);
		mSubmitButton = (Button) findViewById(R.id.submit);
		mSubmitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String mfeedback = mfeedbackEditText.getText().toString().trim();
				if (isEmpty(mfeedback)) {
					ToastUtils.show(mContext, "请输入您的意见及建议");
					return;
				}
				Feedbacksubmit(mfeedback);
			}
		});
	}
	
	private void Feedbacksubmit(String feedback){
		    AddFeedbackTask task = new AddFeedbackTask(mContext, feedback);
		    task.setProgressVisiable(true);
		    task.setOnTaskFinished(new OnTaskFinished()
	        {
	            @Override
	            public void onSucc(Object obj)
	            {
	            	BooleanResponse response = (BooleanResponse)obj;
	                showToast("提交成功");
	                finish();
	            }
	            @Override
	            public void onFail(Object obj)
	            {
	            	BooleanResponse response = (BooleanResponse)obj;
	                showToast(response.getResultDesc());
	            }
	        });
		    task.execute();
		
	}
	
	
	private boolean isEmpty(String str) {
		if (str == null || "".equals(str))
			return true;
		else
			return false;
	}

}
