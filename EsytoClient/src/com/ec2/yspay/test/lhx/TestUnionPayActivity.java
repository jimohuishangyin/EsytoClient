package com.ec2.yspay.test.lhx;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.chinaums.mpos.activity.UmsMposManager;
import com.chinaums.mpos.app.UmsMposContext;
import com.chinaums.mpos.callback.MposCallback;
import com.ec2.yspay.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestUnionPayActivity extends Activity {
	private Button btnPay;
	private boolean clickable = true;
	private UmsMposManager entrance = UmsMposManager.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_union_pay);
		UmsMposContext.getInstance().init(this, UmsMposContext.TYPE_PHONE);	
		entrance.checkCurrentEnv(this, null, new MposCallback() {
			
			@Override
			public void umsTransactionResult(Bundle result) {
				String environment = result.getString("environment");
				if("1".endsWith(environment)){
				}
			}
		} );
		btnPay = (Button) findViewById(R.id.btn_test_pay);
		btnPay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!clickable)
					return;
				else
					clickable = false;
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						clickable = true;
					}
				}, 1500);
				bookOrderAndPay();
			}
		});
	}
	public void bookOrderAndPay() {
		String timeStr = new Date().getTime()+"";
		Bundle args = new Bundle();
		args.putString("billsMID", "898310072994003");
		args.putString("billsTID", "00000001");
		args.putString("merOrderId",  timeStr);
		args.putString("merOrderDesc",  "");
		args.putString("amount",  "1");
		args.putString("operator", "000001");
		args.putString("consumerPhone", "");
		args.putBoolean("isShowOrderInfo", false);
		args.putString("memo", "beizhu");
		entrance.pay(this, args, new PayListner());
		//.pay(this, args, new PayListner());

	}
	
	class PayListner implements MposCallback{

		@Override
		public void umsTransactionResult(final Bundle result) {

			runOnUiThread(new Runnable() {
				public void run() {
					String receive = Common.printBundle(result);
					String resultStatus = result.getString("resultStatus");
					String resultInfo = result.getString("resultInfo");
					String payStatus = result.getString("payStatus");
					String signatureStatus = result.getString("signatureStatus");
					Log.i("======", resultStatus + "=====-" + resultInfo);
					Log.i("TAG", "返回信息--->"+receive);
					
				}
			});
		}
		
	}
}
