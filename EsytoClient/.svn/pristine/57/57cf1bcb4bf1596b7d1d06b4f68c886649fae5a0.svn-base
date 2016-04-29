package com.ec2.yspay.zxing.activity;

import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.ec2.yspay.R;
import com.ec2.yspay.activity.PayInputActivity;
import com.ec2.yspay.common.NetworkUtil;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.widget.ButtonRedFillParentCenter;
import com.ec2.yspay.zxing.BeepManager;
import com.ec2.yspay.zxing.camera.CameraManager;
import com.ec2.yspay.zxing.common.BitmapUtils;
import com.ec2.yspay.zxing.decode.BitmapDecoder;
import com.ec2.yspay.zxing.decode.DecodeThread;
import com.ec2.yspay.zxing.decode.RecodeUtils;
import com.ec2.yspay.zxing.utils.CaptureActivityHandler;
import com.ec2.yspay.zxing.utils.InactivityTimer;
import com.ffcs.tools.album.PickImageFromAlbum;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;

/**
 *zxing 摄像头扫码入口界面
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月11日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {
	private Context mContext = CaptureActivity.this;
	private static final String TAG = CaptureActivity.class.getSimpleName();

	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	private InactivityTimer inactivityTimer;
	private BeepManager beepManager;

	private SurfaceView scanPreview = null;
	private RelativeLayout scanContainer;
	private RelativeLayout scanCropView;
	private RelativeLayout rlTitle;
	private ImageView scanLine;

	private Rect mCropRect = null;
	private ImageView ivResult;
	private ImageView ivCover;
	private TextView tvNetTips;
	private Handler mHandler = new MyHandler();
	private boolean lastResult = false;
	private PayTypeEntity payType;
	private double money = 1;
	private Button btnInputByUser;
	private boolean isShowBtn = true;
	private String remark;
	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	private boolean isHasSurface = false;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_capture);
		money = getIntent().getDoubleExtra("money", 0);
		isShowBtn = getIntent().getBooleanExtra("isShowBtn", true);
		remark = getIntent().getStringExtra("remark");
        payType = (PayTypeEntity)getIntent().getSerializableExtra("payType");
		scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
		scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
		scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels*2/3;
        LayoutParams params = new LayoutParams(mScreenWidth, mScreenWidth);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);   
        params.addRule(RelativeLayout.BELOW, R.id.capture_mask_top); 
		scanCropView.setLayoutParams(params);
		scanLine = (ImageView) findViewById(R.id.capture_scan_line);
		rlTitle = (RelativeLayout) findViewById(R.id.rl_top);
		ivResult = (ImageView) findViewById(R.id.iv_result);
		ivCover = (ImageView) findViewById(R.id.iv_cover_gray);
		tvNetTips = (TextView) findViewById(R.id.tv_scan_network_tips);
		btnInputByUser = (Button)findViewById(R.id.btn_input_byuser);
		if(!isShowBtn){
		    btnInputByUser.setVisibility(View.GONE);
		}
		
		btnInputByUser.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                onclick_input();
            }
        });
		inactivityTimer = new InactivityTimer(this);
		beepManager = new BeepManager(this);

		scanLine.startAnimation(getAnimation());
		//开启网路监听
		IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
        isNetworkAcc = NetworkUtil.isNetworkAvailable(this);
        if(!isNetworkAcc){
			setNetworkErrorStatus(0);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// CameraManager must be initialized here, not in onCreate(). This is
		// necessary because we don't
		// want to open the camera driver and measure the screen size if we're
		// going to show the help on
		// first launch. That led to bugs where the scanning rectangle was the
		// wrong size and partially
		// off screen.
		cameraManager = new CameraManager(CaptureActivity.this);

		handler = null;
		lastResult = false;
		if (isHasSurface) {
			// The activity was paused but not stopped, so the surface still
			// exists. Therefore
			// surfaceCreated() won't be called, so init the camera here.
			initCamera(scanPreview.getHolder());
		} else {
			// Install the callback and wait for surfaceCreated() to init the
			// camera.
			scanPreview.getHolder().addCallback(this);
		}

		inactivityTimer.onResume();
	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		beepManager.close();
		cameraManager.closeDriver();
		if (!isHasSurface) {
			scanPreview.getHolder().removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if(inactivityTimer!=null)
			inactivityTimer.shutdown();
		try{
			unregisterReceiver(mReceiver);	
		}catch(Exception e){
		}
		super.onDestroy();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
			Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
		}
		if (!isHasSurface) {
			isHasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isHasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if ( lastResult && isNetworkAcc) { // 重新进行扫描
					restartPreviewAfterDelay(0L);
					resetStatusView();
					return true;
				}
				break;
			case KeyEvent.KEYCODE_FOCUS:
			case KeyEvent.KEYCODE_CAMERA:
				return true;
				
		}
		return super.onKeyDown(keyCode, event);
	}
	private TranslateAnimation getAnimation(){
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				0.9f);
		animation.setDuration(3500);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);

		return animation;
	}
	/**
	 * A valid barcode has been found, so give an indication of success and show
	 * the results.
	 * 
	 * @param rawResult
	 *            The contents of the barcode.
	 * 
	 * @param bundle
	 *            The extras
	 */
	public void handleDecode(Result rawResult, Bundle bundle) {
//		inactivityTimer.onActivity();
//		beepManager.playBeepSoundAndVibrate();
//		String result = RecodeUtils.recode(rawResult.getText());
//		bundle.putInt("width", mCropRect.width());
//		bundle.putInt("height", mCropRect.height());
//		bundle.putString("result", result);
//
//		startActivity(new Intent(CaptureActivity.this, ResultActivity.class).putExtras(bundle));
		beepManager.playBeepSoundAndVibrate();
		String result = RecodeUtils.recode(rawResult.getText());

		lastResult = true;

		// 把图片画到扫描框
		Bitmap barcode = null;
		byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
		if (compressedBitmap != null) {
			barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
			// Mutable copy:
			barcode = barcode.copy(Bitmap.Config.RGB_565, true);
		}
		ivResult.setImageBitmap(barcode);
		ivResult.setVisibility(View.VISIBLE);
		scanLine.setVisibility(View.GONE);
		scanLine.setAnimation(null);
		
		openQrCode(result);
	}
	/**
	 * 本地图片解码处理
	 * @param code
	 */
	private void handleDecode(String code) {
		beepManager.playBeepSoundAndVibrate();
		String result = code;

		lastResult = true;

		ivResult.setVisibility(View.GONE);
		scanLine.setVisibility(View.GONE);
		scanLine.setAnimation(null);
		
		openQrCode(result);
	}
	
	private String mqrCode = "";
	private void openQrCode(String qrCode){
		mqrCode = qrCode;
		isNetworkAcc = NetworkUtil.isNetworkAvailable(this);
        if(!isNetworkAcc){
			setNetworkErrorStatus(0);
			return;
		}
		if (qrCode==null)
			return;
		Intent mIntent = new Intent();  
        mIntent.putExtra("qrCodeFromScan", qrCode); 
		this.setResult(100, mIntent);
		finish();
//		if (qrCode.startsWith("http://")||qrCode.startsWith("https://")){
//			Uri uri = Uri.parse(qrCode);  
//			Intent it = new Intent(Intent.ACTION_VIEW, uri);  
//			startActivity(it);
//			finish();
//		}else if(qrCode.startsWith("YH#")){
//			//示例：YH#{"id":317,"typt":0}
//			String jsonStr = qrCode.substring(3);
//			
//			try {
//				JSONObject jsonObj = new JSONObject(jsonStr);
//				int id = jsonObj.getInt("id");
//				int type = jsonObj.getInt("type");
//				Intent intent = null;
//				if(type==1){//云币商品详情
//				    
//				}else{//0表示oto详情页
//				    
//				}
//				startActivity(intent);
//				finish();
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else{
//			showToast("解析成功：" + qrCode);
//			restartPreviewAfterDelay(3000L);
//			resetStatusView();
//		}
	}
	private void showToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			// Creating the handler starts the preview, which can also throw a
			// RuntimeException.
			if (handler == null) {
				handler = new CaptureActivityHandler(this, cameraManager
						, DecodeThread.ALL_MODE,"ISO-8859-1");
			}

			initCrop();
		} catch (IOException ioe) {
			Log.w(TAG, ioe.toString());
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			// Barcode Scanner has seen crashes in the wild of this variety:
			// java.?lang.?RuntimeException: Fail to connect to camera service
			Log.w(TAG, "Unexpected error initializing camera" + e.toString());
			displayFrameworkBugMessageAndExit();
		}
	}

	private void displayFrameworkBugMessageAndExit() {
		// camera error
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage("相机打开出错，请稍后重试");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}

		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		builder.show();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
	}
	private void resetStatusView() {
		lastResult = false;
		scanLine.setVisibility(View.VISIBLE);
		scanLine.setAnimation(getAnimation());
		ivResult.setVisibility(View.GONE);
		
		tvNetTips.setVisibility(View.GONE);
		ivCover.setVisibility(View.GONE);
	}

	public Rect getCropRect() {
		return mCropRect;
	}

	/**
	 * 初始化截取的矩形区域
	 */
	private void initCrop() {
		int cameraWidth = cameraManager.getCameraResolution().y;
		int cameraHeight = cameraManager.getCameraResolution().x;

		/** 获取布局中扫描框的位置信息 */
		int[] location = new int[2];
		scanCropView.getLocationInWindow(location);
		int titleHeight = rlTitle.getHeight();
		int cropLeft = location[0];
		int cropTop = location[1] - getStatusBarHeight()-titleHeight;

		int cropWidth = scanCropView.getWidth();
		int cropHeight = scanCropView.getHeight();

		/** 获取布局容器的宽高 */
		int containerWidth = scanContainer.getWidth();
		int containerHeight = scanContainer.getHeight();
		

		/** 计算最终截取的矩形的左上角顶点x坐标 */
		int x = cropLeft * cameraWidth / containerWidth;
		/** 计算最终截取的矩形的左上角顶点y坐标 */
		int y = cropTop * cameraHeight / containerHeight;

		/** 计算最终截取的矩形的宽度 */
		int width = cropWidth * cameraWidth / containerWidth;
		/** 计算最终截取的矩形的高度 */
		int height = cropHeight * cameraHeight / containerHeight;

		/** 生成最终的截取的矩形 */
		mCropRect = new Rect(x, y, width + x, height + y);
	}

	private int getStatusBarHeight() {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			return getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	String photoPath = "";
	ProgressDialog progressDialog;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				Log.i("lhx", "onActivityResult-->"+intent.getData());
				// 获取选中图片的路径
				Cursor cursor = getContentResolver().query(
						intent.getData(), null, null, null, null);
				if (cursor.moveToFirst()) {
					photoPath = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
				}
				cursor.close();

				recodeFromImg();
			}
			break;
		case 2://4.4以上的获取图片路径方法
			if (resultCode == RESULT_OK) {
				photoPath = PickImageFromAlbum.getPath(CaptureActivity.this, intent.getData());

				recodeFromImg();
			}
			break;

		case 3:
			if (resultCode == RESULT_OK) {
//				if (!Toolkits.isNullOrEmpty(mqrCode) && mqrCode.startsWith("189cn://")){
//					mqrCode = mqrCode.replace("189cn://", "");
//					confirmQrCode(mqrCode);
//				}
			}else{
				restartPreviewAfterDelay(0l);
	    		resetStatusView();
			}
			break;
	}

}
	private void recodeFromImg(){
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在扫描...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {

				Bitmap img = BitmapUtils
						.getCompressedBitmap(photoPath);

				BitmapDecoder decoder = new BitmapDecoder(
						CaptureActivity.this);
				Result result = decoder.getRawResult(img);
				if (result != null) {
					Message m = mHandler.obtainMessage();
					m.what = PARSE_BARCODE_SUC;
					m.obj = RecodeUtils.recode(ResultParser.parseResult(result)
							.toString());
					mHandler.sendMessage(m);
				}
				else {
//					Log.i("resultCode", "解析失败或者没有二维码图片");
					Message m = mHandler.obtainMessage();
					m.what = PARSE_BARCODE_FAIL;
					mHandler.sendMessage(m);
				}
				
			}
		}).start();
	}
	private static final int PARSE_BARCODE_FAIL = 300;
	private static final int PARSE_BARCODE_SUC = 200;
	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case PARSE_BARCODE_SUC: // 解析图片成功
					String qrCode = (String) msg.obj;
					if (qrCode==null||"".equals(qrCode)){
						showToast("二维码为空字符串");
						return;
					}
					handleDecode(qrCode);
					progressDialog.dismiss();
					break;

				case PARSE_BARCODE_FAIL:// 解析图片失败
					showToast("未发现二维码/充值卡，轻触屏幕继续扫描");
					//回到扫描态
					progressDialog.dismiss();
					
					break;

				default:
					break;
			}

			super.handleMessage(msg);
		}

	}
	public void onScanPic(View view){
		Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		innerIntent.setType("image/*");
		Intent wrapperIntent = Intent.createChooser(innerIntent,"选择二维码图片");
		if(android.os.Build.VERSION.SDK_INT>=19){//4.4android.os.Build.VERSION_CODES.KITKAT             
	        startActivityForResult(wrapperIntent, 2);    
		}else{
		    startActivityForResult(wrapperIntent, 1);   
		}
	}
	/**
	 * 0无网络、1网路请求失败
	 * @param status
	 */
	private void setNetworkErrorStatus(int status){
		tvNetTips.setVisibility(View.VISIBLE);
		scanLine.setVisibility(View.GONE);
		scanLine.setAnimation(null);
		ivCover.setVisibility(View.VISIBLE);
		ivResult.setVisibility(View.GONE);
		if(status==0){
			tvNetTips.setText(getResources().getString(R.string.scan_no_network));
		}else{
			tvNetTips.setText(getResources().getString(R.string.scan_slow_network));
		}
	}
	private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private boolean isNetworkAcc = true;
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//                Log.d("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager)      

                                         getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();  
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
//                    Log.d("mark", "当前网络名称：" + name);
                    if(!isNetworkAcc){
                    	isNetworkAcc = true;
                    	restartPreviewAfterDelay(0l);
                    	resetStatusView();
                    }
                } else {
//                    Log.d("mark", "没有可用网络");
                	if(isNetworkAcc){
                    	isNetworkAcc = false;
                    	setNetworkErrorStatus(0);
                    }
                }
            }
        }
    };
    private void onclick_input(){
        Intent intent = new Intent(mContext, PayInputActivity.class);
        intent.putExtra("money", money);
        intent.putExtra("remark", remark);
        intent.putExtra("payType", payType);
        startActivity(intent);
        finish();
    }
}