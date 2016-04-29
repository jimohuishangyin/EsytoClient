package com.ec2.yspay.common;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;


/**
 * 首页双击退出
 */
public class DoubleClickExitUtils {

	private final static int DOUBLE_CLICK_GAP = 3000;
	private final Activity mActivity;
	private boolean mIsBackKeyWorking;
	private Handler mHandler;
	
	public DoubleClickExitUtils(Activity activity) {
		mActivity = activity;
		mHandler = new Handler(Looper.getMainLooper());
	}
	/**
	 * Activity onKeyDown事件
	 * */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//不是返回键则退出
		if(keyCode != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		
		//处理回退按钮
		return handleBackKey();
	}
	
	/**
	 * Activity dispatchKeyEvent事件
	 * */
	public boolean dispatchKeyEvent(KeyEvent event) {
		//不是返回键则退出
		if(event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		
		//处理回退按钮
		return handleBackKey();
	}
	
	/**
	 * Activity onBackPressed事件
	 * */
	public void onBackPressed() {
		//处理回退按钮
		handleBackKey();
	}
	
	/**
	 * 实际的处理代码
	 * 
	 * @return
	 */
	private boolean handleBackKey(){
		//如果是短时间内连续两次back键,则关闭页面/退出软件
		if(mIsBackKeyWorking) {
			mHandler.removeCallbacks(onBackKeyClear);
			mActivity.finish();
			MyApplication.exit();
			return true;
		} 
		//如果是第一次,则做个标志
		else {
			mIsBackKeyWorking = true;
			ToastUtils.show(mActivity, "再按一次退出软件");
			mHandler.postDelayed(onBackKeyClear, DOUBLE_CLICK_GAP);
			return true;
		}
	}
	
	
	/**
	 * 延迟任务: 清空标志
	 */
	private Runnable onBackKeyClear = new Runnable() {
		
		@Override
		public void run() {
			mIsBackKeyWorking = false;
		}
	};
}
