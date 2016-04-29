package com.ec2.yspay.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.ec2.yspay.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActivity extends Activity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.welcome_activity);
	final Intent it = new Intent(this, LoginActivity.class);
	Timer timer = new Timer(); 
	TimerTask task = new TimerTask() {  
	    @Override  
	    public void run() {   
	    startActivity(it); 
	    finish();
	     } 
	 };
	timer.schedule(task, 1000 * 1); //10秒后
}

}
