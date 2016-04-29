package com.ec2.yspay.pay.best;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bestpay.app.PaymentTask;
import com.bestpay.plugin.Plugin;
import com.ec2.yspay.R;
import com.ec2.yspay.common.ToastUtils;

public class TestMainActivity extends Activity {

	private Context context;
	EditText mMerchantId;
	EditText mMerchantPwd;
	EditText mAmmount;
	EditText mAccount;
	EditText merchantbank;

	Spinner mPayTypeSpinner;
	ViewGroup mBillPayLayout;

	EditText mBillAcount, mBillType, mGoodsCode, mGoodsName, mGoodsNum,
			mMerchantPhone;
	private static final String[] m = { "0", "1" };
	private boolean mBillPay = false;
	private final boolean mNeedOrder = true;

	static final int ORDER_FAIL = -1;
	static final int ORDER_SUCCESS = 0;
	PaymentTask task = new PaymentTask(TestMainActivity.this);
	Hashtable<String, String> paramsHashtable = new Hashtable<String, String>();

//	 private static final String LICENSE = "cE5QTmdiZERxZjJZZm93eHRnZUxvcEQwT3NWRzJRMnEwRDFpV1VWa1BuZ2lxYTJmQUVHV3ZNcmlGb3VKRlZSdFQ2VnZtbWwwUWRiSkZJemFsSXYwOWJGUlhBVGZCNHozZmk5aVpJbnRENk1lK2oxMm1Nb09CZ3U2endXRXJzdGFpY0RPc1o5TTdlN2E2Zzd5WU50RVR3VHpnME9yTWluODhleGhrbVJwV2VJPXsiaWQiOjAsInR5cGUiOiJwcm9kdWN0IiwicGFja2FnZSI6WyJjb20uYmVzdHBheS5kZW1vIl0sImFwcGx5bmFtZSI6WyLnv7zmlK/ku5hERU1PIl0sInBsYXRmb3JtIjoyfQ==";

//	private static final String LICENSE = "RTRyZENxdkpxakNaQjNGc0lhZ1NMRUJiclV5T2RlYittNzVOZU1qcjQrNGFzY0J4UGkrcnpZZkwxZjVNSlpKT2QrYUQ3SDQ1V2hrL241WWVzRUpZMXppcTduODNFRHUyK1pGNTJkMi96amlVSjN5bldOaHJrdWF1Ty95bCttTlM0bE9xeFV0enk3aEJxdlkwelByd3k0YU82cGs5a0NGN0YrTHZ4QWl0R3owPXsiaWQiOjAsInR5cGUiOiJ0ZXN0IiwicGxhdGZvcm0iOjIsIm5vdGJlZm9yZSI6IjIwMTUwNzA4Iiwibm90YWZ0ZXIiOiIyMDE1MTAwOCJ9";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_test);
		context = TestMainActivity.this;

		mMerchantId = (EditText) findViewById(R.id.merchant_id);
		mMerchantPwd = (EditText) findViewById(R.id.merchant_pwd);
		merchantbank = (EditText) findViewById(R.id.et_merchant);

		mMerchantId.setText("043101180050000");
		mMerchantPwd.setText("534231");

		mAccount = (EditText) findViewById(R.id.account_id);

		// 以元为单位
		mAmmount = (EditText) findViewById(R.id.amount);

		mPayTypeSpinner = (Spinner) findViewById(R.id.pay_type);
		mBillPayLayout = (LinearLayout) findViewById(R.id.bill_pay_layout);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mPayTypeSpinner.setAdapter(adapter);
		mPayTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				if (position == 1) {
					mBillPayLayout.setVisibility(View.VISIBLE);
					mBillPay = true;
				} else {
					mBillPayLayout.setVisibility(View.GONE);
					mBillPay = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		mBillType = (EditText) findViewById(R.id.bill_type);
		mBillAcount = (EditText) findViewById(R.id.userAcount);
		mMerchantPhone = (EditText) findViewById(R.id.merchant_phone);

		/*
		 * 提交订单；
		 */

		findViewById(R.id.payBtnView).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
					    bestPay(String.valueOf(System.currentTimeMillis()));
					}
				});
	}
	
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ORDER_FAIL:
	            ToastUtils.show(TestMainActivity.this, "下单失败");
				break;
			case ORDER_SUCCESS:
				task.pay((Hashtable<String, String>) msg.obj);
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("========",resultCode+"");
		if(data!=null)
		{
            ToastUtils.show(context,data.getExtras().getString("result"));
		}
	}
	
	
	private void bestPay(String orderId){
	    paramsHashtable.put(Plugin.MERCHANTID, mMerchantId
            .getText().toString());
        paramsHashtable.put(Plugin.SUBMERCHANTID, "");
        paramsHashtable.put(Plugin.MERCHANTPWD, mMerchantPwd
                .getText().toString());
        paramsHashtable.put(Plugin.ORDERSEQ,
                orderId);
        
        //订单金额 =产品金额+附加金额
        paramsHashtable.put(Plugin.ORDERAMOUNT, mAmmount
                .getText().toString());
        
        paramsHashtable.put(Plugin.ORDERTIME,
                new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date(System
                                .currentTimeMillis())));
        paramsHashtable.put(Plugin.ORDERVALIDITYTIME,
                new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date(System
                                .currentTimeMillis()
                                + 60
                                * 1000 * 60 * 24)));
        
        //商品描述
        paramsHashtable.put(Plugin.PRODUCTDESC, "Test");
        
        //商户登录的手机号 
        paramsHashtable.put(Plugin.CUSTOMERID, "170091956785");
        
        //产品金额
        paramsHashtable.put(Plugin.PRODUCTAMOUNT, mAmmount
                .getText().toString());
        paramsHashtable.put(Plugin.ATTACHAMOUNT, "0");
        paramsHashtable.put(Plugin.CURTYPE, "RMB");
        
        paramsHashtable.put(Plugin.BACKMERCHANTURL, "http://127.0.0.1:8040/wapBgNotice.action");
        
        paramsHashtable.put(Plugin.ATTACH, "");
        
        paramsHashtable.put(Plugin.PRODUCTID, "04");
        paramsHashtable.put(Plugin.USERIP, "192.168.11.130");
    
        paramsHashtable.put(Plugin.DIVDETAILS, "");
    
        paramsHashtable.put(Plugin.KEY, TestConstant.CKEY);
        paramsHashtable.put(Plugin.ACCOUNTID, mAccount
                .getText().toString());
        paramsHashtable.put(Plugin.BUSITYPE, "04");
    
        paramsHashtable.put(Plugin.ORDERREQTRANSEQ,
                System.currentTimeMillis() + "00001");
        paramsHashtable.put(Plugin.SERVICE, "mobile.security.pay");
        paramsHashtable.put(Plugin.SIGNTYPE, "MD5");
    
        String mac = "MERCHANTID="
                + paramsHashtable.get(Plugin.MERCHANTID)
                + "&ORDERSEQ="
                + paramsHashtable.get(Plugin.ORDERSEQ)
                + "&ORDERREQTRNSEQ="
                + paramsHashtable.get(Plugin.ORDERREQTRANSEQ)
                + "&ORDERTIME="
                + paramsHashtable.get(Plugin.ORDERTIME)
                + "&KEY=" + TestConstant.CKEY;
    
        try {
            mac = CryptTool.md5Digest(mac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        paramsHashtable.put(Plugin.MAC, mac);
        
        
        
        if (mNeedOrder) {
            final Hashtable<String, String> paramsHashtable2 = paramsHashtable;
            // 增加下单流程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String orderResult = new Util().order(paramsHashtable2);
                    if (orderResult != null
                            && "00".equals((orderResult.split("&"))[0])) {
                        Message msg = new Message();
                        msg.what = ORDER_SUCCESS;
                        msg.obj = paramsHashtable2;
                        mHandler.sendMessage(msg);
                    } else {
                        mHandler.sendEmptyMessage(ORDER_FAIL);
                    }
                }
            }).start();
        } else {
            task.pay(paramsHashtable);
        }
        


	}
}
