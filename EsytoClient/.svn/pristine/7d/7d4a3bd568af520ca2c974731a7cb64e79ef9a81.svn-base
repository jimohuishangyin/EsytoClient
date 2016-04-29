package com.ec2.yspay.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;

/**
 * 当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告
 * 需要在Application中注册，为了要在程序启动就监控整个程序。 
 * @author jiangwenxin
 * @createTime 2014年9月2日 下午3:05:56 
 * @version 5.0
 */
public class MyCrashHandler implements UncaughtExceptionHandler {      

	public static final String TAG = "MyCrashHandler";      
	private Thread.UncaughtExceptionHandler mDefaultHandler;      
	private static MyCrashHandler instance;  
	private Context mContext;      


	private MyCrashHandler() {}      

	/** 获取CrashHandler实例 ,单例模式 */      
	public static MyCrashHandler getInstance() {      
		if(instance == null)  
			instance = new MyCrashHandler();     
		return instance;      
	}      

	/**   
	 * 初始化   
	 */      
	public void init(Context context) {      
		mContext = context;      
		//获取系统默认的UncaughtException处理器      
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();      
		//设置该CrashHandler为程序的默认处理器      
		Thread.setDefaultUncaughtExceptionHandler(this);      
	}      

	/**   
	 * 当UncaughtException发生时会转入该函数来处理   
	 */      
	@Override      
	public void uncaughtException(Thread thread, Throwable ex) {
	    Log.i("app", "程序已奔溃！");
//		if (Log.dbgFlg) {
//			Toolkits.saveCrashLogcat(instance, ex);//将崩溃信息写入文件存放在ec2client文件夹下
//		}
		//        if (!handleException(ex) && mDefaultHandler != null) {      
		//如果用户没有处理则让系统默认的异常处理器来处理      
//		mDefaultHandler.uncaughtException(thread, ex);  
		//干掉当前的程序   
//        android.os.Process.killProcess(android.os.Process.myPid()); 
		MyApplication.toLoginActivity(mContext);
	}      


}    