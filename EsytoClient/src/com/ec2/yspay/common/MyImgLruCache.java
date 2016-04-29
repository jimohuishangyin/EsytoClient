/*
 * 类文件名:  MyImgLruCache.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年11月12日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 图片缓存到内存中
 * 主要用途：头像
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年11月12日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyImgLruCache
{
    private static final String TAG = "LruCache";
    private static LruCache<String, Bitmap> mMemoryCache;
    private static String mDataRootPath = null;
    private static MyImgLruCache myImgLruCache =null;
    /**
     * 单例，获取缓存对象
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月12日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static synchronized MyImgLruCache getInstance(){
        if(myImgLruCache==null){
            myImgLruCache = new MyImgLruCache();
        }
        return myImgLruCache;
    }
    private MyImgLruCache(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() / 1024;
            }
            
        };
    }
    /**
     * 添加Bitmap到内存缓存
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {  
            mMemoryCache.put(key, bitmap);  
        }  
        Log.i(TAG, "缓存大小:"+(mMemoryCache.size()/1024.0f)+" KB");
    }  
     
    /**
     * 从内存缓存中获取一个Bitmap
     * @param key
     * @return
     */
    public Bitmap getBitmapFromMemCache(String key) {  
        return mMemoryCache.get(key);  
    } 
}
