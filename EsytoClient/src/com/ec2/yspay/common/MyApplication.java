/*
 * 类文件名:  MyApplication.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月11日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.ec2.yspay.activity.LoginActivity;
import com.ec2.yspay.activity.MainActivity;
import com.ec2.yspay.http.DataCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 系统application
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月11日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyApplication extends Application
{
    private static String TAG = "MyApplication";
    public static String mUserAgent;
    private static Context mContext;
    public static DataCache mDataCache;
    public static String mAppVersion;
    private static List<Activity> mActivityList = new LinkedList<Activity>();// 页面堆栈管理
    private FileTools fileTools ;
    /**
     * 重载方法
     */
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        mContext = getApplicationContext();
//        mUserAgent = Toolkits.getPhoneModelLong() + "/"
//            + Toolkits.getCurAppVerName(mContext);
        mDataCache = new DataCache();
//        mAppVersion = getAppVerName();
//        fileTools = new FileTools(mContext);
//        fileTools.startCopyFile();
//        citysManager = CitysManager.getInstance(mContext);
//        MyCrashHandler catchHandler = MyCrashHandler.getInstance();  
//        catchHandler.init(mContext);
        initImageLoader(this);
    }
    public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		ImageLoader.getInstance().init(config);
	}
    private String getAppVerName() {
        String version = Toolkits.getCurAppVerName(mContext);
        String channel = Toolkits.getChannelId(mContext);
        String model = Toolkits.getPhoneModelLong();
        version = "#" + version + "#" + channel + "#" + model + "#";
        return version;
    }
    /**
     * 添加页面到堆栈
     */
    public static void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * 从堆栈删除页面
     */
    public static void removeActivity(Activity activity) {
        mActivityList.remove(activity);
    }
    
    public static void setBuleTooth(Context context, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("buletoothname", value);
        editor.commit();
    }

    public static String getBuleTooth(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("buletoothname", null);
    }

    public static void exit() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
        
        System.exit(0);
    }
    public static int getActivitySize() {
        return mActivityList.size();
    }
    
    public static boolean hasMainActivity(){
        for (Activity activity : mActivityList) {
            if (activity instanceof MainActivity)
                return true;
        }
        
        return false;
    }
    public static void toMainActivityNoClear(){
        for (Activity activity : mActivityList) {
            if (activity instanceof MainActivity){
//                ((MainActivity)activity).receiptFragment.clearData();
            }else{
                activity.finish();
            }
        }
    }
    public static void toMainActivity(){
        for (Activity activity : mActivityList) {
            if (activity instanceof MainActivity){
                ((MainActivity)activity).receiptFragment.clearData();
            }else{
                activity.finish();
            }
        }
    }
    public static void toLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        for (Activity activity : mActivityList) {
            if (activity instanceof LoginActivity){
            }else{
                activity.finish();
            }
        }
    }
    private static Handler mHandler = new Handler();
    public static void onAppForgroundChanged() {
        mHandler.removeCallbacks(CheckAppForground);
        mHandler.postDelayed(CheckAppForground, 300);// 加个延迟过滤一下
    }

    private static Runnable CheckAppForground = new Runnable() {

        @Override
        public void run() {
            // 触发动作为"切换到后台"或者"关闭屏幕":
            if (Toolkits.isAppOnForeground(mContext) == false
                    || Toolkits.isScreenOn(mContext) == false) {
                mHandler.postDelayed(ExistAppDelay, 300000);
            }
            // 触发动作为"切换到前台":
            else {
                mHandler.removeCallbacks(ExistAppDelay);
            }
        }
    };
    private static Runnable ExistAppDelay = new Runnable() {

        @Override
        public void run() {
            Log.v(TAG, "软件退出");
            MyApplication.exit();
        }
    };
    
}
