package com.ec2.yspay.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinaums.mpos.callback.MposCallback;
import com.ec2.yspay.R;
import com.ec2.yspay.activity.CashPayActivity;
import com.ec2.yspay.activity.PayInfoActivity;
import com.ec2.yspay.activity.PaySuccessActivity;
import com.ec2.yspay.activity.UserScanActivity;
import com.ec2.yspay.common.Common;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.CashOrCardResponse;
import com.ec2.yspay.http.task.CashOrCardTask;
import com.ec2.yspay.pay.PayTypePopupWindow;
import com.ec2.yspay.pay.card.MPosPay;

public class PayTypeGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<PayTypeEntity> mList;
	private PayTypePopupWindow window;
	private String money, remark;
	public PayTypeGridViewAdapter(Context mContext, PayTypePopupWindow window,
			List<PayTypeEntity> mList, String money, String remark
			) {
		this.mContext = mContext;
		this.mList = mList;
		this.money = money;
		this.window = window;
		this.remark = remark;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mList == null) {
			return 0;
		} else {
			return mList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (mList == null) {
			return null;
		} else {
			return mList.get(position);
		}

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.item_gridview_pay, null, false);
			holder.tvName = (TextView) convertView.findViewById(R.id.item_name);
			holder.ivType = (ImageView) convertView.findViewById(R.id.item_image);
			holder.llParent = (RelativeLayout) convertView.findViewById(R.id.rl_pay);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (this.mList != null) {
			final PayTypeEntity item = mList.get(position);
//			final ChannelTypes item = mChannelTypes.get(position);
			holder.tvName.setText(item.getPayName());
			holder.ivType.setImageResource(item.getImgId());
			final String isOpen = item.getIsOpen();
			
			

//			
			holder.llParent.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Toast.makeText(mContext, item.getPayName()+" "+money,
					// Toast.LENGTH_SHORT).show();
					switch (item.getPayId()) {
					case PayTypeEntity.PAY_CASH:// 现金
						Intent cashIntent = new Intent(mContext,
								CashPayActivity.class);
						cashIntent.putExtra("money", Double.valueOf(money));
						cashIntent.putExtra("remark", remark);
						mContext.startActivity(cashIntent);
						window.dismiss();
						break;
					case PayTypeEntity.PAY_UNION:// 银行卡
						if(isOpen.equals("1")){
							getPosOrderId(item);
							window.dismiss();
						}else{
							ToastUtils.show(mContext, "未开通此服务");
						}
						
						break;
					case PayTypeEntity.PAY_BAIDU:// 百度
					case PayTypeEntity.PAY_ALI:// 支付宝
					case PayTypeEntity.PAY_BEST:// 翼支付
					case PayTypeEntity.PAY_WX:// 微信
						if(isOpen.equals("1")){
							Intent wxIntent = new Intent(mContext,
									UserScanActivity.class);
							wxIntent.putExtra("money", Double.valueOf(money));
							wxIntent.putExtra("payType", item);
							wxIntent.putExtra("remark", remark);
							mContext.startActivity(wxIntent);
							window.dismiss();
						}else{
							ToastUtils.show(mContext, "未开通此服务");
						}
						
						break;
//					case PayTypeEntity.PAY_BEST:// 翼支付
//						if(isOpen.equals("1")){
//							Intent bestIntent = new Intent(mContext,
//									PayInfoActivity.class);
//							bestIntent.putExtra("money", Double.valueOf(money));
//							bestIntent.putExtra("payType", item);
//							bestIntent.putExtra("remark", remark);
//							mContext.startActivity(bestIntent);
//							window.dismiss();
//						}else{
//							ToastUtils.show(mContext, "未开通此服务");
//						}
//						
//						break;

					default:
						break;
					}
				}
			});
		}
		return convertView;
	}

	/**
	 * <一句话功能简述> <功能详细描述>
	 * 
	 * @author 罗洪祥
	 * @version V001Z0001
	 * @param item
	 * @date 2015年11月12日
	 * @see [相关类/方法]
	 * @since [产品/模块版本]
	 */
	protected void getPosOrderId(final PayTypeEntity item) {
		// TODO Auto-generated method stub
		OrderInfo orderInfo = new OrderInfo(money);
		CashOrCardTask task = new CashOrCardTask(mContext,
				PayTypeEntity.PAY_UNION, orderInfo, remark);
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {
			@Override
			public void onSucc(Object obj) {
				// TODO Auto-generated method stub
				CashOrCardResponse response = (CashOrCardResponse) obj;
//				if (Toolkits.isPkgInstalled(mContext, Constants.QUANMIN_PCK)) {
//					new MPosPay(mContext).bookOrderAndPay(money, response
//							.getOrderId(), new BookOrderAndPayResultListener(
//							item, response.getOrderId()));
//				} else {
//					FileTools fileTools = new FileTools(mContext);
//					fileTools.InstallAPK(Constants.QUANMIN_NAME);
//				}
				new MPosPay(mContext).bookOrderAndPay(money, response
						.getOrderId(), new BookOrderAndPayResultListener(
						item, response.getOrderId()));
			}

			@Override
			public void onFail(Object obj) {
				// TODO Auto-generated method stub
				CashOrCardResponse response = (CashOrCardResponse) obj;
				ToastUtils.show(mContext, response.getResultDesc());
			}
		});
		task.execute();
	}

	class ViewHolder {
		TextView tvName;
		ImageView ivType;
		RelativeLayout llParent;
	}

	/**
	 * 
	 * 银行卡刷卡返回信息
	 * 
	 * @param money
	 *            支付金额 卓10.29
	 */
	class BookOrderAndPayResultListener implements MposCallback {
		PayTypeEntity item;
		private String mOrderId;

		public BookOrderAndPayResultListener(PayTypeEntity item, String orderId) {
			this.item = item;
			this.mOrderId = orderId;
		}

		@Override
		public void umsTransactionResult(final Bundle result) {
			// handler到主线程
			String receive = Common.printBundle(result);
			Log.i("lhx", "返回信息--->" + receive);

			result.putSerializable("item", item);
			result.putString("mOrderId", mOrderId);
			Message msg = new Message();
			msg.setData(result);
			mPosResultHandler.sendMessage(msg);
		}

	}

	private Handler mPosResultHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Bundle result = msg.getData();
			PayTypeEntity item = (PayTypeEntity) result.getSerializable("item");
			String mOrderId = result.getString("mOrderId");
			String receive = Common.printBundle(result);
			String resultStatus = result.getString("resultStatus");
			String resultInfo = result.getString("resultInfo");
			String payStatus = result.getString("payStatus");
			String signatureStatus = result.getString("signatureStatus");
			Log.i("posPay", "resultStatus:" + resultStatus + ";resultInfo:"
					+ resultInfo + ";payStatus:" + payStatus);
			Log.i("TAG", "返回信息--->" + receive);
			// ToastUtils.showLong(mContext, resultInfo);
			if ("success".equals(payStatus)) {
				String orderId = result.getString("orderId");

				String printStatus = result.getString("printStatus");

				if ("success".equals(payStatus)
						&& !"success".equals(signatureStatus)) {
					// payMessageDialog(
					// mContext.getString(R.string.pay_success_voucher_fail),
					// receive);
				} else if ("success".equals(payStatus)) {
					// payMessageDialog(
					// mContext.getString(R.string.pay_success),
					// receive);
				}
				Intent intent = new Intent(mContext, PaySuccessActivity.class);
				intent.putExtra("money", Double.valueOf(money));
				intent.putExtra("orderId", mOrderId);
				intent.putExtra("payType", item);
				mContext.startActivity(intent);
				// ToastUtils.showLong(mContext,"下单成功！");
				// payByCard(item,mOrderId,orderId);
			} else if ("havetopay".equals(resultStatus)) {
				// payMessageDialog(
				// mContext.getResources().getString(
				// R.string.pay_success), receive);
				ToastUtils.showLong(mContext, resultInfo);

			} else {
				// payMessageDialog(mContext.getString(R.string.pay_fail),
				// receive);
				ToastUtils.showLong(mContext, resultInfo);
			}
		};
	};

	private void payByCard(final PayTypeEntity item, String orderId,
			String posId) {
		OrderInfo orderInfo = new OrderInfo(money);
		CashOrCardTask task = new CashOrCardTask(mContext,
				PayTypeEntity.PAY_UNION, orderInfo, remark);
		task.setPosId(posId);
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				// TODO Auto-generated method stub
				CashOrCardResponse response = (CashOrCardResponse) obj;
				Intent intent = new Intent(mContext, PaySuccessActivity.class);
				intent.putExtra("money", Double.valueOf(money));
				intent.putExtra("orderId", response.getOrderId());
				intent.putExtra("payType", item);
				mContext.startActivity(intent);
				ToastUtils.showLong(mContext, "下单成功！");
			}

			@Override
			public void onFail(Object obj) {
				// TODO Auto-generated method stub
				CashOrCardResponse response = (CashOrCardResponse) obj;
				ToastUtils.showLong(mContext,
						"下单失败：" + response.getResultDesc());
			}
		});
		task.execute();
	}

}
