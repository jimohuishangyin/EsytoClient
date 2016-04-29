package com.ec2.yspay.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.CashCollectOtherResponse;
import com.ec2.yspay.http.task.CashCollectOtherTask;
import com.ec2.yspay.pay.PayConstants;
import com.ec2.yspay.pay.ali.PayResult;
import com.ec2.yspay.pay.ali.SignUtils;

public class CollectAliActivity extends FragmentActivity {

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	private Context mContext;
	Button aliCollectPay;
	private String money,transDate;
    private TextView tvUserName,tvShopName,tvMoney;
    private String orderId,payReqInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collect_ali);
		aliCollectPay=(Button) findViewById(R.id.ali_collect_pay);
		mContext=this;
		money = getIntent().getStringExtra("money");
		transDate = getIntent().getStringExtra("transDate");
        tvUserName = (TextView)findViewById(R.id.tv_username);
        tvShopName = (TextView)findViewById(R.id.tv_address);
        tvMoney = (TextView)findViewById(R.id.tv_money);
        tvUserName.setText(MyApplication.mDataCache.userName);
        tvShopName.setText(MyApplication.mDataCache.shopName);
        tvMoney.setText(money);
        
        getOrderMsg();
        
		aliCollectPay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pay();
			}
		});
		
	}
	private void getOrderMsg(){
      OrderInfo orderInfo = new OrderInfo(money);
//        OrderInfo orderInfo = new OrderInfo("0.01");
        CashCollectOtherTask task = new CashCollectOtherTask(mContext, PayTypeEntity.PAY_ALI, orderInfo,transDate);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                CashCollectOtherResponse response = (CashCollectOtherResponse)obj;
                orderId = response.getOrderId();
                payReqInfo = response.getPayReqInfo();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                CashCollectOtherResponse response = (CashCollectOtherResponse)obj;
                ToastUtils.showLong(mContext, response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay() {

		// 完整的符合支付宝参数规范的订单信息
//		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//				+ getSignType();
		final String payInfo = payReqInfo;

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(CollectAliActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case SDK_PAY_FLAG: {
                PayResult payResult = new PayResult((String) msg.obj);
                
                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                String resultInfo = payResult.getResult();
                
                String resultStatus = payResult.getResultStatus();

                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                	ToastUtils.show(CollectAliActivity.this, "支付成功");
                    
                    finish();
                } else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {

                    	ToastUtils.show(CollectAliActivity.this, "支付结果确认中");

                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误

                    	ToastUtils.show(CollectAliActivity.this, "支付失败");
                    }
                }
                break;
            }
            case SDK_CHECK_FLAG: {
            	ToastUtils.show(CollectAliActivity.this, "检查结果为：" + msg.obj);
                break;
            }
            default:
                break;
            }
        };
    };
	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(CollectAliActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

}
