/*
 * 类文件名:  FileTools.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年11月6日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import com.ec2.yspay.R;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年11月6日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileTools
{
    private final static String TAG = "FileTools";
    private Context mContext;
    public FileTools(Context context){
        this.mContext = context;
    }
    public void copyRawToSdcard(String fileName,String toPath){//name为sd卡下制定的路径  
        Field[] raw = R.raw.class.getFields();  
       for (Field r : raw) {  
            try {  
           //     System.out.println("R.raw." + r.getName());  
                int id=mContext.getResources().getIdentifier(r.getName(), "raw", mContext.getPackageName());  
                String tempName = r.getName();
                Log.i("file", "raw:"+tempName);
                  if(fileName.equals(tempName)){  
                    String path=toPath+"/"+fileName+".apk";  
                    File targetDir = new File(toPath);
                    //创建目录
                    if(!targetDir.exists())
                    {
                        targetDir.mkdirs();
                    } 
                    BufferedOutputStream bufEcrivain = new BufferedOutputStream((new FileOutputStream(new File(path))));  
                    BufferedInputStream VideoReader = new BufferedInputStream(mContext.getResources().openRawResource(id));  
                    byte[] buff = new byte[20*1024];  
                    int len;  
                    while( (len = VideoReader.read(buff)) > 0 ){  
                        bufEcrivain.write(buff,0,len);  
                    }  
                    bufEcrivain.flush();  
                    bufEcrivain.close();  
                    VideoReader.close();
                    Log.i("file", "插件拷贝成功！");
                    break;
                  }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
         
   }
    public void startCopyFile(){
        Thread t=new Thread(new MyRunnable());//这里比第一种创建线程对象多了个任务对象
        t.start();
    }
    public class MyRunnable implements Runnable{
        public void run(){
        //你需要实现的代码
            String path = Constants.PATH_FILE+"/"+Constants.QUANMIN_NAME+".apk";
            Log.i(TAG, "检查全民付插件："+path);
            if(!fileIsExists(path)){
                Log.i(TAG, "复制插件到sd卡："+Constants.QUANMIN_NAME);
                copyRawToSdcard(Constants.QUANMIN_NAME, Constants.PATH_FILE);
            }else{
                Log.i(TAG, "插件已存在SD卡");
            }
            
        }
    }
    public static boolean fileIsExists(String path){
        try{
                File f=new File(path);
                if(!f.exists()){
                        return false;
                }
                
        }catch (Exception e) {
                // TODO: handle exception
                return false;
        }
        return true;
    }
    public void InstallAPK(String apkname) {  
        // TODO Auto-generated method stub   
  
        //代码安装   
        
        String fileName = Constants.PATH_FILE + "/"+apkname+".apk"; 
        if(fileIsExists(fileName)){
            ToastUtils.show(mContext, "您还未安装全民付插件！");
            Intent intent = new Intent(Intent.ACTION_VIEW);   
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
    //       intent.setDataAndType(Uri.parse("file://"+fileName), "application/vnd.android.package-archive");    
             intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");   
            mContext.startActivity(intent);  
        }else{
            if(ExistSDCard()){
                if(getSDFreeSize()<5){
                    ToastUtils.showLong(mContext, "SD卡存储容量不足！");
                }else{
                    ToastUtils.showLong(mContext, "检查不到插件，请重新启动应用！");
                }
            }else{
                ToastUtils.showLong(mContext, "SD卡不存在！");
            }
        }
    }
    /**
     * 判断sd卡是否存在
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月9日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public boolean ExistSDCard() {  
        if (android.os.Environment.getExternalStorageState().equals(  
          android.os.Environment.MEDIA_MOUNTED)) {  
         return true;  
        } else  
         return false;  
    }
    /**
     * sd卡剩余空间大小，单位MB
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月9日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public long getSDFreeSize(){  
        //取得SD卡文件路径  
        File path = Environment.getExternalStorageDirectory();   
        StatFs sf = new StatFs(path.getPath());   
        //获取单个数据块的大小(Byte)  
        long blockSize = sf.getBlockSize();   
        //空闲的数据块的数量  
        long freeBlocks = sf.getAvailableBlocks();  
        //返回SD卡空闲大小  
        //return freeBlocks * blockSize;  //单位Byte  
        //return (freeBlocks * blockSize)/1024;   //单位KB  
        return (freeBlocks * blockSize)/1024 /1024; //单位MB  
      }
}
