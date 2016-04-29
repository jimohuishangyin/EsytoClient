package com.ec2.yspay.pay.card;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.chinaums.mpos.activity.UmsMposManager;
import com.chinaums.mpos.callback.MposCallback;
import com.chinaums.mpos.service.IUmsMposResultListener;
import com.ec2.yspay.common.Common;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.widget.PopupDialog;

public class MPosPay {
    private final static String TAG = "MPosPay";
	private Context mContext;
	private UmsMposManager entrance ;
	public MPosPay(Context contest) {
		mContext = contest;
		entrance = UmsMposManager.getInstance();
	}

	/**
	 * 
	 * 蓝牙管理
	 * 
	 * @param null 卓10.29
	 */
	public void setupDevice() {
		Bundle args = new Bundle();
		args.putString("billsMID", MyApplication.mDataCache.BILLS_MID);
		args.putString("billsTID", MyApplication.mDataCache.BILLS_TID);
		try {
			UmsMposManager.getInstance().setDevice(mContext,args,
					new SetupDeviceResultListener());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 蓝牙连接返连裆裤信息
	 * 
	 * @param null 卓10.29
	 */
	class SetupDeviceResultListener implements MposCallback {
		@Override
		public void umsTransactionResult(final Bundle result){
			((Activity) mContext).runOnUiThread(new Runnable() {
				public void run() {
					String receive = Common.printBundle(result);

					String resultStatus = result.getString("resultStatus");
					String resultInfo = result.getString("resultInfo");
//					ToastUtils.show(mContext, resultInfo);
//					payMessageDialog(resultInfo, resultStatus);
					Log.i("SetupDeviceResultListener", resultInfo);
				}
			});
		}
	}

	/**
	 * 
	 * 银行卡刷卡支付
	 * 
	 * @param money
	 *            支付金额 卓10.29
	 */
	public void bookOrderAndPay(String money,String orderId,MposCallback stub) {
//	    bookOrder(money, orderId, stub);
		final Bundle args = new Bundle();
		args.putString("billsMID", MyApplication.mDataCache.BILLS_MID);
        args.putString("billsTID", MyApplication.mDataCache.BILLS_TID);
		args.putString("merOrderId", orderId);
//		args.putString("merOrderDesc", "无描述");
		args.putString("amount", ((int) (Double.parseDouble(money) * 100)) + "");
		args.putString("operator", "000001");
//		args.putString("consumerPhone", MyApplication.mDataCache.UserPhoneNbr);
		// if(hasValue)
		// args.putBoolean("isShowOrderInfo", isShowOrderInfo);
		args.putString("memo", "无备忘录");
		UmsMposManager.getInstance().pay(mContext,args,stub);
//		try {
//			if (ServiceManager.getInstance().mUmsMposService == null) {
//			    Log.e(TAG, "银行卡刷卡支付 ServiceManager.getInstance().mUmsMposService is null");
//				ServiceManager.getInstance().bindMpospService(mContext);
//				return;
//			}
//			Log.e(TAG, "进入全面支付页面！");
//			UmsMposManager.getInstance().pay(args,
//			    stub);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}

	}

	
	/**
	 * 
	 * 银行卡支付撤消
	 * 
	 * @param orderId
	 *            订单ID 卓10.29
	 */
	public void revocatonOrder(String orderId,String merOrderId,MposCallback listener) {
		Bundle args = new Bundle();
		args.putString("billsMID", MyApplication.mDataCache.BILLS_MID);
        args.putString("billsTID", MyApplication.mDataCache.BILLS_TID);
		args.putString("orderId", orderId);
		args.putString("merOrderId", merOrderId);
		args.putString("salesSlipType", "1");
		args.putString("operator", "000001");
		args.putString("memo", "无描述");
		try {
			
			entrance.cancelTransaction(mContext,
					args, listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	private void payMessageDialog(String title, String msg) {

		final PopupDialog.Builder builder = new PopupDialog.Builder(mContext);
		// builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// builder.setRemarkText("");
						dialog.dismiss();

					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();

			}
		});

		builder.create().show();

	}
}
