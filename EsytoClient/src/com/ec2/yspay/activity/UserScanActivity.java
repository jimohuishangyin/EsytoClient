package com.ec2.yspay.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.ErrorCode;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.CloseOrderResponse;
import com.ec2.yspay.http.response.GetQrCodeResponse;
import com.ec2.yspay.http.response.PayWithCodeResponse;
import com.ec2.yspay.http.response.TradeQueryResponse;
import com.ec2.yspay.http.task.CloseOrderTask;
import com.ec2.yspay.http.task.GetQrCodeTask;
import com.ec2.yspay.http.task.PayWithCodeTask;
import com.ec2.yspay.http.task.TradeQueryTask;
import com.ec2.yspay.widget.ButtonRedCenter;
import com.ec2.yspay.widget.MyTitle;
import com.ec2.yspay.widget.WarnPopupWhiteRedDialog;
import com.ec2.yspay.zxing.activity.CaptureActivity;
import com.ec2.yspay.zxing.common.BitmapUtils;
import com.google.zxing.WriterException;

public class UserScanActivity extends BaseActivity {
	private String codeMsg = null;
	private ImageView ivCode;
	private String orderId;
	private double money = -1;
	private TextView tvMoney;
	private PayTypeEntity payType;
	private MyTitle mTitle;
	private Bitmap bitmap;
	private String remark;
	private ButtonRedCenter btnScan;
	private boolean isStopQuery = false;
	private PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_scan);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		ivCode = (ImageView) findViewById(R.id.iv_code);
		mTitle = (MyTitle) findViewById(R.id.rl_top);
		mTitle.setLeftOnclickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showWarnDialog();
			}
		});
		money = getIntent().getDoubleExtra("money", 0);
		remark = getIntent().getStringExtra("remark");
		payType = (PayTypeEntity) getIntent().getSerializableExtra("payType");
		mTitle.setTitleText(payType.getPayName() + "收款");
		tvMoney = (TextView) findViewById(R.id.tv_money);
		tvMoney.setText("￥" + Toolkits.doubleFormat(money));
		ivCode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				showAllScreenPic();
			}
		});
		btnScan = (ButtonRedCenter) findViewById(R.id.btn_scan);
		btnScan.setBtnOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 先关闭订单，再跳转到扫码页面
				Intent saomaIntent = new Intent(mContext, CaptureActivity.class);
				saomaIntent.putExtra("money", money);
				saomaIntent.putExtra("remark", remark);
				saomaIntent.putExtra("payType", payType);
				startActivityForResult(saomaIntent, 101);
			}
		});
		mHandler.sendEmptyMessageDelayed(10001, 600000);
		// getQRCode(payType.getPayId());
		// mHandler.sendEmptyMessageDelayed(10001, 600000);
	}

	// 卓仲明　2015/12/3
	private void showAllScreenPic() {
		View outerView = LayoutInflater.from(mContext).inflate(
				R.layout.activity_pic_show_all_screen, null);
		ImageView ivCode = (ImageView) outerView.findViewById(R.id.iv_code);
		ivCode.setImageBitmap(bitmap);
		popupWindow = new PopupWindow(outerView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0f;
		getWindow().setAttributes(lp);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		popupWindow.showAtLocation(findViewById(R.id.ll_use_scan),
				Gravity.CENTER, 0, 0);
		ivCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
	}

	/**
	 * 重载方法
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 101 && resultCode == 100) {
			String codeMsg = data.getStringExtra("qrCodeFromScan");
			payWithCode(codeMsg);
		}
	}

	/**
	 * 重载方法
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isStopQuery = false;
		if(Toolkits.isStrEmpty(orderId))
			getQRCode(payType.getPayId());
		else
			mHandler.sendEmptyMessageDelayed(1, 2000);
		if (popupWindow != null)
			popupWindow.dismiss();

	}

	/**
	 * 重载方法
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isStopQuery = true;
		 mHandler.removeMessages(1);
		// mHandler = null;
	}

	private void showQRCode() {
		if (codeMsg != null) {
			try {
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				int mScreenWidth = dm.widthPixels * 3 / 4;
				bitmap = BitmapUtils.createQRCode(
						codeMsg,
						mScreenWidth,
						BitmapFactory.decodeResource(getResources(),
								payType.getErWeiMaIcon()));

				if (bitmap != null) {
					ivCode.setImageBitmap(bitmap);
					ivCode.setPadding(0, 0, 0, 0);
				}

			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private Handler mHandler = new Handler() {
		/**
		 * 重载方法
		 * 
		 * @param msg
		 */
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 10001) {
				if (payType.getPayId() == PayTypeEntity.PAY_WX) {
					closeOrder(true);
				} else {
					finish();
				}
			}
			if (!isStopQuery)
				qrState();
		}

	};

	private void getQRCode(int type) {
		OrderInfo orderInfo = new OrderInfo(Toolkits.doubleFormat(money));
		// orderInfo.setBody("测试订单详情明细。。。");
		// orderInfo.setSubject("测试订单标题");
		GetQrCodeTask task = new GetQrCodeTask(mContext, type, orderInfo,
				remark);
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				GetQrCodeResponse response = (GetQrCodeResponse) obj;
				codeMsg = response.getQrCode();
				orderId = response.getOrderId();
				mHandler.sendEmptyMessageDelayed(1, 2000);
				showQRCode();
			}

			@Override
			public void onFail(Object obj) {
				GetQrCodeResponse response = (GetQrCodeResponse) obj;
				showToast("获取二维码信息失败：" + response.getResultDesc());
				finish();
			}
		});
		task.execute();
	}

	/**
	 * 查询订单状态
	 * 
	 * @author 罗洪祥
	 * @version V001Z0001
	 * @date 2015年9月24日
	 * @see [相关类/方法]
	 * @since [产品/模块版本]
	 */
	private void qrState() {
		TradeQueryTask task = new TradeQueryTask(mContext, "00000000", orderId);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				TradeQueryResponse response = (TradeQueryResponse) obj;
				String result = response.getTranResult();
				Log.i(TAG, "订单状态：" + result);
				if ("TRADE_SUCCESS".equals(result)) {
					showToast("交易成功");
					Intent intent = new Intent(mContext,
							PaySuccessActivity.class);
					intent.putExtra("money", money);
					intent.putExtra("orderId", orderId);
					intent.putExtra("payType", payType);
					startActivity(intent);
				} else if ("WAIT_BUYER_PAY".equals(result)) {
					// 2秒一次，查询交易状态
					if (mHandler != null)
						mHandler.sendEmptyMessageDelayed(1, 2000);
				} else {

					showPayFailDialog("支付失败，" + response.getResultDesc());
				}
			}

			@Override
			public void onFail(Object obj) {
				TradeQueryResponse response = (TradeQueryResponse) obj;
				String code = response.getCode();
				String result = response.getTranResult();
				Log.e("pay", "查询失败，" + response.getResultDesc() + ";"
						+ response.getCode());
				if (code.equals("8810016") && "REQUEST_RETRY".equals(result)) {
					if (mHandler != null)
						mHandler.sendEmptyMessageDelayed(1, 2000);
				} else if (code.equals(ErrorCode.CONNECT_ERROR)) {
					if (mHandler != null)
						mHandler.sendEmptyMessageDelayed(1, 2000);
				} else if (code.equals(ErrorCode.NETWORK_ERROR)) {
					if (mHandler != null)
						mHandler.sendEmptyMessageDelayed(1, 2000);
				} else {
					showPayFailDialog("支付失败，" + response.getResultDesc());
				}
			}
		});
		task.execute();
	}

	/**
	 * 重载方法
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeOrder(false);
		mHandler.removeMessages(1);
		mHandler.removeMessages(10001);
		mHandler = null;

	}

	private void showPayFailDialog(String msg) {

		final WarnPopupWhiteRedDialog.Builder builder = new WarnPopupWhiteRedDialog.Builder(
				mContext);
		// builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
		builder.setTitle("支付提示");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		Log.i("test", cn.getClassName());
		if (cn.getClassName().contains("UserScanActivity")) {
			builder.create().show();
		}

	}

	private void showWarnDialog() {

		final WarnPopupWhiteRedDialog.Builder builder = new WarnPopupWhiteRedDialog.Builder(
				mContext);
		// builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
		builder.setTitle("提示");
		builder.setMessage("是否取消本订单？");
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				// 调用关闭订单接口
				if (payType.getPayId() == PayTypeEntity.PAY_WX)
					closeOrder(true);
				else
					finish();

			}
		});

		builder.create().show();
	}

	/**
	 * 重载方法
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showWarnDialog();
		}
		return false;
	}

	private void closeOrder(final boolean isProgressVisiable) {
		CloseOrderTask task = new CloseOrderTask(mContext, orderId);
		task.setProgressMsg("关闭订单中，请稍后。。。");
		task.setProgressVisiable(isProgressVisiable);
		task.setOnTaskFinished(new OnTaskFinished() {
			@Override
			public void onSucc(Object obj) {
				// showToast("订单已关闭");
				if (isProgressVisiable)
					finish();
			}

			@Override
			public void onFail(Object obj) {
				CloseOrderResponse response = (CloseOrderResponse) obj;
				// showToast("订单关闭失败"+response.getResultDesc());
				if (isProgressVisiable)
					finish();
			}
		});
		task.execute();
	}

	private void payWithCode(String code) {
		OrderInfo orderInfo = new OrderInfo(Toolkits.doubleFormat(money));
		PayWithCodeTask task = new PayWithCodeTask(mContext,
				payType.getPayId(), orderInfo, code, remark);
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				// TODO Auto-generated method stub
				PayWithCodeResponse response = (PayWithCodeResponse) obj;
				String orderId = response.getOrderId();
				String result = response.getTranResult();
				if ("TRADE_SUCCESS".equals(result)) {
					Intent intent = new Intent(mContext,
							PaySuccessActivity.class);
					intent.putExtra("money", money);
					intent.putExtra("orderId", orderId);
					intent.putExtra("payType", payType);
					startActivity(intent);
				} else {
					Intent intent = new Intent(mContext,
							PayWaittingActivity.class);
					intent.putExtra("money", money);
					intent.putExtra("orderId", orderId);
					intent.putExtra("payType", payType);
					startActivity(intent);
				}
			}

			@Override
			public void onFail(Object obj) {
				// TODO Auto-generated method stub
				PayWithCodeResponse response = (PayWithCodeResponse) obj;
				showToast("支付失败" + response.getResultDesc());

			}
		});
		task.execute();
	}
}
