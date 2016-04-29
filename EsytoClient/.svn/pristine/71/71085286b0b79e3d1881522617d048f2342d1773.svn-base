package com.ec2.yspay.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ec2.yspay.common.Log;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;

public class BaseNoLoginActivity extends Activity
{
    protected Context mContext = this;
    protected String TAG = "BaseActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        String ACTIVITY_NAME = this.getClass().getSimpleName() + ":";
        Log.v(TAG, ACTIVITY_NAME + " onCreate");
        
        MyApplication.addActivity(this);
    }
    /**
     * 重载方法
     */
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        MyApplication.onAppForgroundChanged();
    }
    /**
     * 重载方法
     */
    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        MyApplication.onAppForgroundChanged();
    }
    /**
     * 重载方法
     */
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        MyApplication.removeActivity(this);
    }
    protected void showToast(String msg){
    	Toolkits.esytoToast(mContext, msg);
    }
    protected void showToastLong(String msg){
    	Toolkits.esytoLongToast(mContext, msg);
    }
    @Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
  
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）  
            View v = getCurrentFocus();  
  
            if (isShouldHideInput(v, ev)) {  
                hideSoftInput(v.getWindowToken());  
            }  
        }  
        return super.dispatchTouchEvent(ev);  
    }  
  
    /** 
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏 
     *  
     * @param v 
     * @param event 
     * @return 
     */  
    private boolean isShouldHideInput(View v, MotionEvent event) {  
        if (v != null && (v instanceof EditText)) {  
            int[] l = { 0, 0 };  
            v.getLocationInWindow(l);  
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left  
                    + v.getWidth();  
            if (event.getRawX() > left && event.getRawX() < right  
                    && event.getY() > top && event.getY() < bottom) {  
                // 点击EditText的事件，忽略它。  
                return false;  
            } else {  
                return true;  
            }  
        }  
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点  
        return false;  
    }  
  
    /** 
     * 多种隐藏软件盘方法的其中一种 
     *  
     * @param token 
     */  
    private void hideSoftInput(IBinder token) {  
        if (token != null) {  
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
            im.hideSoftInputFromWindow(token,  
                    InputMethodManager.HIDE_NOT_ALWAYS);  
        }  
    }  
}
