/*
 * 类文件名:  Setting.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年11月10日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年11月10日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Setting
{
    private static final String CRYPT_KEY = "esyto2015mobile";
    
    public static void setUserName(Context context, String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("UserName", value);
        editor.commit();
    }
    
    public static String getUserName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("UserName", null);
    }
    
    public static void setUserPwd(Context context, String value){
        String dstValue;
        boolean cryptFlg;
        
        try {
            CryptToolkit des = new CryptToolkit();
            dstValue = des.desedeEncoder(value, CRYPT_KEY);
            cryptFlg = true;
        } catch (Exception e) {
            //重置密码时, 要设置为null, null会导致异常, 但又不想看到这个日常日志...
            if (value!=null)
                e.printStackTrace();
            
            dstValue = value;
            cryptFlg = false;
        }
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("UserPwd", dstValue);
        editor.putBoolean("PwdCrypt", cryptFlg);
        editor.commit();
    }
    
    public static String getUserPwd(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String pwd = prefs.getString("UserPwd", null);
        boolean crypt = prefs.getBoolean("PwdCrypt", false);
        if (crypt==false || pwd == null){
            return pwd;
        }
        
        try {
            CryptToolkit des = new CryptToolkit();
            return  des.desedeDecoder(pwd, CRYPT_KEY);
        } catch (Exception e) {
            return pwd;
        }
    }
    public static void setToken(Context context, String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("token", value);
        editor.commit();
    }
    
    public static String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("token", null);
    }
}
