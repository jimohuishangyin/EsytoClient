package com.ec2.yspay.widget.popwindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ec2.yspay.R;
import com.ec2.yspay.common.Constants;
import com.ec2.yspay.common.Log;
import com.ec2.yspay.common.ToastUtils;
/**
 * 城市列表级联弹窗效果
 * @ClassName: CityPopwindow 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 罗洪祥 luohx@esyto.com 
 * @date 2016年4月12日 下午3:16:10 
 *
 */
public class PicPopwindow extends Popwindow{
	public static final int RESULT_STARTALBUM = 1;
	public static final int RESULT_SARTCAMERA = 2;
	public static final int RESULT_CAMERA_QIETU = 3;
	public static final int REQUEST_ALBUM_QIETU = 4;
	public static final String TMP_PATH = "clip_temp.jpg";
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final String COMM_PARAMS_CAMERE_SUFFIX = ".jpg";
	private static final int outputXY = 250;
	public PicPopwindow(Activity mContext){
		super(mContext);
	}
	/**
	 * @Description: 图片修改上传弹窗，可以选择拍照或从相册中选择
	 * @param @param parent  
	 * @return void
	 * @author 罗洪祥
	 * @date 2016年4月13日 上午11:39:49
	 */
	public void showPicPopwindow(View parent) {
		View outerView = LayoutInflater.from(mContext).inflate(
				R.layout.layout_pop_update_touxiang, null);
		Button btnLocal = (Button) outerView.findViewById(R.id.btn_pic_local);
		Button btnTakePhoto = (Button) outerView
				.findViewById(R.id.btn_pic_takephoto);
		Button btnCancel = (Button) outerView.findViewById(R.id.btn_pic_cancel);
		btnLocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startAlbum(RESULT_STARTALBUM);
				popupWindow.dismiss();
			}
		});
		btnTakePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startCapture(RESULT_SARTCAMERA);
				popupWindow.dismiss();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		showPopupwindow(outerView,parent);
	}
	
	/**
	 * @Description: 从相册中选择一张图片进行裁切
	 * @param   
	 * @return void
	 * @author 罗洪祥
	 * @date 2016年4月13日 下午2:51:43
	 */
	public void startAlbum(int requestCode) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		mContext.startActivityForResult(intent, requestCode);
		
//		Intent intent = new Intent(Intent.ACTION_PICK);
////		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
////				IMAGE_UNSPECIFIED);
//		intent.setType("image/*");
//		intent.putExtra("crop", "true");
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1);
//		intent.putExtra("outputX", 300);
//		intent.putExtra("outputY", 300);
//		intent.putExtra("scale", true);
//		intent.putExtra("return-data", true);
//		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//		intent.putExtra("noFaceDetection", true); 
//		mContext.startActivityForResult(intent, requestCode);
//		mContext.startActivityForResult(intent, START_ALBUM_REQUESTCODE);
	}
	public void startCaijian(Intent data,int requestCode){
		Uri mUri = data.getData();
		if(null == mUri)return;  
        
        Intent intent = new Intent();  
          
        intent.setAction("com.android.camera.action.CROP");  
        intent.setDataAndType(mUri, "image/*");// mUri是已经选择的图片Uri  
        intent.putExtra("crop", "true");  
        intent.putExtra("aspectX", 1);// 裁剪框比例  
        intent.putExtra("aspectY", 1);  
        intent.putExtra("outputX", outputXY);// 输出图片大小  
        intent.putExtra("outputY", outputXY);  
        intent.putExtra("return-data", true);  
          
        mContext.startActivityForResult(intent, requestCode);
	}
	/**
	 * @Description: 启动拍照模式 
	 * @param  
	 * @return void
	 * @author 罗洪祥
	 * @date 2016年4月13日 下午3:15:55
	 */
	public void startCapture(int requestCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
		mContext.startActivityForResult(intent, requestCode);
//		mContext.startActivityForResult(intent, CAMERA_WITH_DATA);
	}
	private Uri getTempUri(){
		return Uri.fromFile(new File(
				Constants.PATH_FILE, TMP_PATH));
	}
	/**
	 * 拍完照之后退图片进行裁切
	 * @Description: TODO 
	 * @param @param requestCode  
	 * @return void
	 * @author 罗洪祥
	 * @date 2016年4月13日 下午3:16:28
	 */
	public void cropImageUri( int requestCode){
		 Intent intent = new Intent("com.android.camera.action.CROP");
		 intent.setDataAndType(getTempUri(), "image/*");
		 intent.putExtra("crop", "true");
		 intent.putExtra("aspectX", 1);
		 intent.putExtra("aspectY", 1);
		 intent.putExtra("outputX", outputXY);
		 intent.putExtra("outputY", outputXY);
		 intent.putExtra("scale", true);
		 intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
		 intent.putExtra("return-data", false);
		 intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		 intent.putExtra("noFaceDetection", true); // no face detection
		 mContext.startActivityForResult(intent, requestCode);
		}
		/**
		 * 获取图片地址，进行上传
		 * @Description: TODO 
		 * @param @param data  
		 * @return String
		 * @author 罗洪祥
		 * @date 2016年4月13日 下午3:18:10
		 */
		public String getPicPathFromData(Intent data) {
			String picPath = "";
			if (data != null){
//				Uri selectedimg = data.getData();
				Bitmap bitmap=null;
				try {
//					selectedimg.getPath();
					bitmap = data.getParcelableExtra("data");
//					Log.e("lhx", getFilePath(data.getData()));
//					bitmap = BitmapFactory.decodeFile(getFilePath(data.getData()));
//					bitmap = BitmapFactory.decodeFile(selectedimg.getPath());
//					bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), selectedimg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//data.getParcelableExtra("data");
				if(bitmap==null){
					ToastUtils.show(mContext, "头像设置未获取到图片！");
//					Log.e("getPicPathFromData","头像设置未获取到图片！");
					return null;
				}
				String path = Constants.PATH_FILE + "/"
						+ TMP_PATH;
				saveBitmap(bitmap, path);
//				uploadTouxiang(path);
				picPath = path;
			}else{
				Log.e("头像设置", "返回数据为空");
			}
			return picPath;

		}
		public String getPathData() {
			String picPath = "";
			Bitmap bitmap = decodeUriAsBitmap(getTempUri());
			String path = Constants.PATH_FILE + "/"
					+ TMP_PATH;
			saveBitmap(bitmap, path);
			picPath = path;
			return picPath;

		}
		private Bitmap decodeUriAsBitmap(Uri uri){
			 Bitmap bitmap = null;
			 try {
			  bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(uri));
			 } catch (FileNotFoundException e) {
			  e.printStackTrace();
			  return null;
			 }
			 return bitmap;
			}
		public  void saveBitmap(Bitmap bitmap, String path) {
			File f = new File(path);
			if (f.exists()) {
				f.delete();
			}

			FileOutputStream fOut = null;
			try {
				f.createNewFile();
				fOut = new FileOutputStream(f);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
				fOut.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (fOut != null)
						fOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/**
		 * 通过uri获取文件路径
		 * 
		 * @param mUri
		 * @return
		 */
		public String getFilePath(Uri mUri) {
			try {
				return getFilePathByUri(mUri);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}

		private String getFilePathByUri(Uri mUri) throws Exception {
			if (!TextUtils.isEmpty(mUri.getAuthority())) {
				Cursor cursor = mContext.getContentResolver().query(mUri,
						new String[] { MediaStore.Images.Media.DATA }, null, null,
						null);
				if (null == cursor) {
					return null;
				}
				cursor.moveToFirst();
				String path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				cursor.close();
				return path;
			} else {
				return mUri.getPath();
			}
		}
	public void copyTouxiangToCache(String imgName){
		String path = Constants.PATH_FILE + "/"
				+ TMP_PATH;
		String destPath = Constants.PATH_FILE + "/image/" + imgName;
		copyFile(path, destPath, false);
	}
	private boolean copyFile(String srcFileName, String destFileName,  
            boolean overlay) {  
		String MESSAGE;
        File srcFile = new File(srcFileName);  
  
        // 判断源文件是否存在  
        if (!srcFile.exists()) {  
            MESSAGE = "源文件：" + srcFileName + "不存在！";  
//            JOptionPane.showMessageDialog(null, MESSAGE);  
            return false;  
        } else if (!srcFile.isFile()) {  
            MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";  
//            JOptionPane.showMessageDialog(null, MESSAGE);  
            return false;  
        }  
  
        // 判断目标文件是否存在  
        File destFile = new File(destFileName);  
        if (destFile.exists()) {  
            // 如果目标文件存在并允许覆盖  
            if (overlay) {  
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
                new File(destFileName).delete();  
            }  
        } else {  
            // 如果目标文件所在目录不存在，则创建目录  
            if (!destFile.getParentFile().exists()) {  
                // 目标文件所在目录不存在  
                if (!destFile.getParentFile().mkdirs()) {  
                    // 复制文件失败：创建目标文件所在目录失败  
                    return false;  
                }  
            }  
        }  
  
        // 复制文件  
        int byteread = 0; // 读取的字节数  
        InputStream in = null;  
        OutputStream out = null;  
  
        try {  
            in = new FileInputStream(srcFile);  
            out = new FileOutputStream(destFile);  
            byte[] buffer = new byte[1024];  
  
            while ((byteread = in.read(buffer)) != -1) {  
                out.write(buffer, 0, byteread);  
            }  
            return true;  
        } catch (FileNotFoundException e) {  
            return false;  
        } catch (IOException e) {  
            return false;  
        } finally {  
            try {  
                if (out != null)  
                    out.close();  
                if (in != null)  
                    in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
