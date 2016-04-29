package com.ec2.yspay.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.ErrorCode;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.BestPayReverseResponse;
import com.ec2.yspay.http.response.CloseOrderResponse;
import com.ec2.yspay.http.response.TradeCancelResponse;
import com.ec2.yspay.http.response.TradeQueryResponse;
import com.ec2.yspay.http.task.BestPayReverseTask;
import com.ec2.yspay.http.task.CloseOrderTask;
import com.ec2.yspay.http.task.TradeCancelTask;
import com.ec2.yspay.http.task.TradeQueryTask;
import com.ec2.yspay.widget.MyTitle;
import com.ec2.yspay.widget.WarnPopupOneDialog;
import com.ec2.yspay.widget.WarnPopupWhiteRedDialog;
/**
 * 等待支付页面
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PayWaittingActivity extends BaseActivity
{
    private double money = 1;
    private TextView tvPayTitle;
    private TextView tvMoney,tvUserName,tvShopName;
    private PayTypeEntity payType;
    private ImageView ivPayIcon;
    private String orderId;
    private TextView tvOrderId;
    private MyTitle mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_waitting);
        money = getIntent().getDoubleExtra("money", 0);
        orderId = getIntent().getStringExtra("orderId");
        payType = (PayTypeEntity)getIntent().getSerializableExtra("payType");
        tvPayTitle = (TextView)findViewById(R.id.tv_paytitle);
        tvMoney = (TextView)findViewById(R.id.tv_money);
        tvPayTitle.setText(payType.getPayName());
        tvMoney.setText("￥"+Toolkits.doubleFormat(money));
        ivPayIcon = (ImageView)findViewById(R.id.iv_payicon);
        ivPayIcon.setImageResource(payType.getSmallImgId());
        tvOrderId = (TextView)findViewById(R.id.tv_orderId);
        tvOrderId.setText(orderId);
        tvUserName = (TextView)findViewById(R.id.tv_username);
        tvShopName = (TextView)findViewById(R.id.tv_shopname);
        tvUserName.setText(MyApplication.mDataCache.userName);
        tvShopName.setText(MyApplication.mDataCache.shopName);
        mTitle = (MyTitle)findViewById(R.id.rl_top);
        mTitle.setLeftOnclickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                showWarnDialog();
            }
        });
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }
    private Handler mHandler = new Handler(){

        /**
         * 重载方法
         * @param msg
         */
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            qrState();
        }
        
    };
    private void qrState(){
        TradeQueryTask task = new TradeQueryTask(mContext, "00000000", orderId);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
            	TradeQueryResponse response = (TradeQueryResponse)obj;
                String result = response.getTranResult();
//                response.getCode()
                Log.i(TAG, "订单状态："+result);
                if("TRADE_SUCCESS".equals(result)){
                    showToast("交易成功");
                    Intent intent = new Intent(mContext, PaySuccessActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("payType", payType);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                }else if("WAIT_BUYER_PAY".equals(result)){
                  //2秒一次，查询交易状态
                    if(mHandler!=null)
                        mHandler.sendEmptyMessageDelayed(1, 2000);
                }else{
                    showPayFailDialog("支付失败，"+response.getResultDesc());
                }
            }
            
            @Override
            public void onFail(Object obj)
            {
            	TradeQueryResponse response = (TradeQueryResponse)obj;
                String code = response.getCode();
                String result = response.getTranResult();
                Log.e("pay","查询失败，"+response.getResultDesc()+";"+response.getCode());
                if(code.equals("8810016")&&"REQUEST_RETRY".equals(result)){
                    if(mHandler!=null)
                        mHandler.sendEmptyMessageDelayed(1, 2000);
                }else if(code.equals(ErrorCode.CONNECT_ERROR)){
                    if(mHandler!=null)
                        mHandler.sendEmptyMessageDelayed(1, 2000);
                }else if(code.equals(ErrorCode.NETWORK_ERROR)){
                    if(mHandler!=null)
                        mHandler.sendEmptyMessageDelayed(1, 2000);
                }else{
                    showPayFailDialog("支付失败，"+response.getResultDesc());
                }
            }
        });
        task.execute();
    }
    /**
     * 重载方法
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mHandler.removeMessages(1);
        mHandler = null;
    }
    /**
     * 重载方法
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            showWarnDialog();
        }
        return false;
    }
    private void showWarnDialog() {

        final WarnPopupWhiteRedDialog.Builder builder = new WarnPopupWhiteRedDialog.Builder(mContext);
        //builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
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
              //支付失败后支付宝微信调用撤销订单，翼支付调用冲正接口
                if(payType.getPayId()==PayTypeEntity.PAY_BEST){
                    reverseForBestPay();
                }else{
                    reverseForWxAli();
                }
            }
        });

        builder.create().show();

    }
    private void reverseForWxAli(){
    	CloseOrderTask task = new CloseOrderTask(mContext, orderId);
		task.setProgressMsg("关闭订单中，请稍后。。。");
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {
			@Override
			public void onSucc(Object obj) {
					MyApplication.toMainActivityNoClear();
			}

			@Override
			public void onFail(Object obj) {
				CloseOrderResponse response = (CloseOrderResponse) obj;
				MyApplication.toMainActivityNoClear();
			}
        });
        task.execute();
    }
    private void reverseForBestPay(){
        BestPayReverseTask task = new BestPayReverseTask(mContext, payType.getPayId(), orderId);
        task.setProgressMsg("关闭订单中，请稍后。。。");
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                showToast("订单已关闭");
//                finish();
                MyApplication.toMainActivityNoClear();
            }

            @Override
            public void onFail(Object obj)
            {
                BestPayReverseResponse response = (BestPayReverseResponse)obj;
//                showToast("订单关闭失败"+response.getResultDesc());
//                finish();
                MyApplication.toMainActivityNoClear();
            }
            
        });
        task.execute();
    }
    private void showPayFailDialog(String msg) {

        final WarnPopupOneDialog.Builder builder = new WarnPopupOneDialog.Builder(mContext);
        //builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
        builder.setTitle("支付提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
                dialog.dismiss();
                payReset();
            }
        });
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		Log.i("test", cn.getClassName());
		if (cn.getClassName().contains("PayWaittingActivity")) {
			builder.create().show();
		}
    }
    /**
     * 撤销，冲正
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2016年3月10日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    protected void payReset()
    {
      //支付失败后支付宝微信调用撤销订单，翼支付调用冲正接口
        if(payType.getPayId()==PayTypeEntity.PAY_BEST){
            reverseForBestPay();
        }else{
            reverseForWxAli();
        }
    }
}
