/*
 * 类文件名:  AsyncImageLoader.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年11月4日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import com.ec2.yspay.R;
/**
 * 异步加载图片使用方法：
 * private AsyncImageLoader asyImg = new AsyncImageLoader();
 * asyImg.LoadImage(productItems.get(position).getPic(), (ImageView)view.findViewById(R.id.pic));
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年11月4日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AsyncImageLoader {
    private final static String TAG = "AsyncImageLoader";
    public static  Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
    private ExecutorService executorService = Executors.newFixedThreadPool(5); // 固定五个线程来执行任务
    private final Handler handler = new Handler();
    // SD卡上图片储存地址
    private final String path = Constants.PATH_FILE;
    private String fileName;
    
    private ProgressDialog mProgressDialog;
    private String mProgressMsg = "请稍候...";
    private boolean mIsProgressVisiable = false;
    private Context mContext;
    public AsyncImageLoader(Context context){
        this.mContext = context;
    }
    /**
     * 
     * 返回内存中缓存的图像，第一次加载返回null
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月4日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public void LoadImage(final String url, final String fileName,final ImageView iv,Drawable defaultDrawable) {
        Log.i(TAG, "url:"+url+" ; fileName:"+fileName);
        mProgressDialog = new ProgressDialog(mContext);
        if (mIsProgressVisiable) {

            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(mProgressMsg);
            mProgressDialog.show();
        }
        this.fileName = fileName;
        if (iv.getImageMatrix() == null) {
            iv.setImageResource(R.drawable.loading);
        }
        // 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
        Drawable cacheImage = loadDrawable(url,
            new AsyncImageLoader.ImageCallback() {
            // 请参见实现：如果第一次加载url时下面方法会执行
                public void imageLoaded(Drawable imageDrawable) {
                    iv.setImageDrawable(imageDrawable);
                    try {
                        if (mProgressDialog != null)
                            mProgressDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                },defaultDrawable);
        if (cacheImage != null) {
            iv.setImageDrawable(cacheImage);
            try {
                if (mProgressDialog != null)
                    mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public Drawable loadDrawable(final String imageUrl,
        final ImageCallback callback,final Drawable defaultDrawable) {
        // 如果缓存过就从缓存中取出数据
        if (imageCache.containsKey(fileName)) {
            SoftReference<Drawable> softReference = imageCache.get(fileName);
            if (softReference.get() != null) {
                Log.i(TAG, "缓存中找到");
                return softReference.get();
            }
            //内存中没有，则去本地找
        } else if (useTheImage(fileName) != null) {
            Log.i(TAG, "本地找到");
            //加入到内存
            Drawable drawable = useTheImage(fileName);
            imageCache.put(fileName, new SoftReference<Drawable>(
                drawable));
            return drawable;
        }
        Log.i(TAG, "缓存和本地中都未找到，网络加载");
        // 缓存和sd卡中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    //获取drawable
                    final Drawable drawable = Drawable.createFromStream(
                        new URL(imageUrl).openStream(), "image.png");
                    //加入到内存
                    imageCache.put(fileName, new SoftReference<Drawable>(
                        drawable));
                    //返回结果
                    handler.post(new Runnable() {
                        public void run() {
                            callback.imageLoaded(drawable);
                      }
                    });
                    //保存到本地
                    saveFile(drawable, fileName);
                    if(drawable==null){
                        Log.i(TAG, "图片为空！");
                        handler.post(new Runnable() {
                            public void run() {
                                callback.imageLoaded(defaultDrawable);
                          }
                        });
                    }else{
                        Log.i(TAG, "网络加载成功，"+fileName);
                    }
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        public void run() {
                            callback.imageLoaded(defaultDrawable);
                      }
                    });
                    
                    throw new RuntimeException(e);
                }
            }
        });
    return null;
    }

    // 从网络上取数据方法
    public Drawable loadImageFromUrl(String imageUrl) {
        try {
            return Drawable.createFromStream(new URL(imageUrl).openStream(),
                "image.png");
            } catch (Exception e) {
                throw new RuntimeException(e);
                }
        }
    // 对外界开放的回调接口
    public interface ImageCallback {
        // 注意 此方法是用来设置目标对象的图像资源
        public void imageLoaded(Drawable imageDrawable);
    }
    
    /**
     * 保存到本地
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月4日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public void saveFile(Drawable dw, String url) {
        try {
            Log.i(TAG, "saveFile: try");
            BitmapDrawable bd = (BitmapDrawable) dw;
            Bitmap bm = bd.getBitmap();
            // 获得文件名字
            
            File file = new File(path + "/image/" + fileName);
            // 创建图片缓存文件夹
            boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
            if (sdCardExist) {
                File maiduo = new File(path);
                File ad = new File(path + "/image");
                // 如果文件夹不存在
              if (!maiduo.exists()) {
                      // 按照指定的路径创建文件夹
                    maiduo.mkdir();
                    // 如果文件夹不存在
                    } else if (!ad.exists()) {
                        // 按照指定的路径创建文件夹
                        ad.mkdir();
                        }
                  // 检查图片是否存在
              if (!file.exists()) {
                  file.createNewFile();
                  }
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i(TAG, "saveFile:"+path + "/image/" + fileName);
            } catch (Exception e){
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    /**
     * 
     * 使用SD卡上的图片
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月4日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public Drawable useTheImage(String imageUrl) {
        Log.i(TAG, "useFile: try");
        Bitmap bmpDefaultPic = null;
        // 获得文件路径
        String imageSDCardPath = path
            + "/image/"+fileName;
        
        File file = new File(imageSDCardPath);
        // 检查图片是否存在
        if (!file.exists()) {
            return null;
            }
        
        bmpDefaultPic = BitmapFactory.decodeFile(imageSDCardPath, null);
        if (bmpDefaultPic != null && bmpDefaultPic.toString().length() > 3) {
            Drawable drawable = new BitmapDrawable(bmpDefaultPic);
            Log.i(TAG, "useFile:"+imageSDCardPath);
            return drawable;
            } else
                return null;
    }
    /**
     * 设置 mIsProgressVisiable
     * @param 对mIsProgressVisiable进行赋值
     */
    public void setmIsProgressVisiable(boolean mIsProgressVisiable)
    {
        this.mIsProgressVisiable = mIsProgressVisiable;
    }
    
}