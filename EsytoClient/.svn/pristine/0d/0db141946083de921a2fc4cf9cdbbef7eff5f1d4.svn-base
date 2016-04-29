package com.ec2.yspay.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

public class Common {

	 /**
   * traversal bundle with string values.
   * 
   * @param bundle
   * @return
   */
  public static String printBundle(Bundle bundle) {
    StringBuilder sb = new StringBuilder("");
    Set<String> keys = bundle.keySet();
    for (String key : keys) {
      sb.append(key).append(":").append(bundle.get(key)).append(";\n");
    }
    return sb.toString();
  }
  /**
   * 判断插件是否已安装
   * @param context
   * @param packageName 插件包名
   * @return
   */
  public static boolean checkApkExist(Context context, String packageName) {
	  if (TextUtils.isEmpty(packageName))
	   return false;
	  try {
	   ApplicationInfo info = context.getPackageManager()
	     .getApplicationInfo(packageName,
	       PackageManager.GET_UNINSTALLED_PACKAGES);
	   if (info == null) {
	    return false;
	   } else {
	    return true;
	   }
	  } catch (NameNotFoundException e) {
	   return false;
	  }
	}
  

	/**
	 * 判断SD卡
	 * @return
	 */
	public static String hasSdCard() {
		String file_path = "";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			file_path = Environment.getExternalStorageDirectory() + "/mpos/tmp";
		} else {
			file_path = "/data/data/com.chinaums.mpos/files";
		}
		return file_path;
	}
	

	/**
	 * 资源文件拷贝
	 * @param context
	 * @param fileName
	 * @param path
	 * @return
	 */
	public static boolean copyApkFromAssets(Context context, String fileName, String path) {
	     boolean copyIsFinish = false;
	     try {
	       InputStream is = context.getAssets().open(fileName);
	       File file = new File(path);
	       file.createNewFile();
	       FileOutputStream fos = new FileOutputStream(file);
	       byte[] temp = new byte[1024];
	       int i = 0;
	       while ((i = is.read(temp)) > 0) {
	         fos.write(temp, 0, i);
	       }
	       fos.close();
	       is.close();
	       copyIsFinish = true;
	     } catch (IOException e) {
	       e.printStackTrace();
	     }
	     return copyIsFinish;
	   }
	
	/**
	 * 安装新应用
	 */
	public static void installApk(Context context, Uri apk) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(apk,"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
