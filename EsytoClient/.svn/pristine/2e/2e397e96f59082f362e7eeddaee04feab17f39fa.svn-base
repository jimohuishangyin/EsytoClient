package com.ec2.yspay.activity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bestpay.app.PaymentTask;
import com.bestpay.plugin.Plugin;
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
import com.ec2.yspay.pay.best.CryptTool;
import com.ec2.yspay.pay.best.TestMainActivity;
import com.ec2.yspay.pay.best.Util;

public class CollectionBestActivity extends BaseActivity implements OnClickListener {
	Button collectPay;	
	static final int ORDER_FAIL = -1;
	static final int ORDER_SUCCESS = 0;
	private final boolean mNeedOrder = true;
	EditText collectPhoneNun;
	PaymentTask task = new PaymentTask(CollectionBestActivity.this);
	Hashtable<String, String> paramsHashtable = new Hashtable<String, String>();
	
	private Context mContext;
	private String money,transDate;
    private TextView tvUserName,tvShopName,tvMoney;
    private String orderId,payReqInfo;
	
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_best);
        collectPay=(Button) findViewById(R.id.collect_pay);
        collectPay.setOnClickListener(this);
        collectPhoneNun=(EditText) findViewById(R.id.collect_phone_nun);
        mContext=this;
        money = getIntent().getStringExtra("money");
        transDate = getIntent().getStringExtra("transDate");
        tvUserName = (TextView)findViewById(R.id.tv_username);
        tvShopName = (TextView)findViewById(R.id.tv_address);
        tvMoney = (TextView)findViewById(R.id.tv_money);
        tvUserName.setText(MyApplication.mDataCache.userName);
        tvShopName.setText(MyApplication.mDataCache.shopName);
        tvMoney.setText(money);

    }
	private void getOrderMsg(){
      OrderInfo orderInfo = new OrderInfo(money);
//        OrderInfo orderInfo = new OrderInfo("0.01");
        CashCollectOtherTask task = new CashCollectOtherTask(mContext, PayTypeEntity.PAY_BEST, orderInfo, transDate);
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
//                getParams();
                toPay();
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==collectPay){
		    getOrderMsg();
//		    toPay();
//		    Intent intent = new Intent(mContext, TestMainActivity.class);
//		    startActivity(intent);
		}
	}
	private void toPay(){
//	    paramsHashtable.put(Plugin.MERCHANTID, PayConstants.BEST_MID);
//        paramsHashtable.put(Plugin.SUBMERCHANTID, "");
//        paramsHashtable.put(Plugin.MERCHANTPWD, PayConstants.BEST_PASSWORK);
//        paramsHashtable.put(Plugin.ORDERSEQ,
//                String.valueOf(System.currentTimeMillis()));
//        
//        //订单金额 =产品金额+附加金额
//        paramsHashtable.put(Plugin.ORDERAMOUNT, "0.01");
//        
//        paramsHashtable.put(Plugin.ORDERTIME,
//                new SimpleDateFormat("yyyyMMddHHmmss")
//                        .format(new Date(System
//                                .currentTimeMillis())));
//        paramsHashtable.put(Plugin.ORDERVALIDITYTIME,
//                new SimpleDateFormat("yyyyMMddHHmmss")
//                        .format(new Date(System
//                                .currentTimeMillis()
//                                + 60
//                                * 1000 * 60 * 24)));
//        
//        //商品描述
//        paramsHashtable.put(Plugin.PRODUCTDESC, "Test");
//        
//        //商户登录的手机号 
//        paramsHashtable.put(Plugin.CUSTOMERID, "170091956785");
//        
//        //产品金额
//        paramsHashtable.put(Plugin.PRODUCTAMOUNT, "0.01");
//        paramsHashtable.put(Plugin.ATTACHAMOUNT, "0");
//        paramsHashtable.put(Plugin.CURTYPE, "RMB");
//        
//        paramsHashtable.put(Plugin.BACKMERCHANTURL, "http://127.0.0.1:8040/wapBgNotice.action");
//        
//        paramsHashtable.put(Plugin.ATTACH, "");
//        
//        paramsHashtable.put(Plugin.PRODUCTID, "04");
//        paramsHashtable.put(Plugin.USERIP, "192.168.11.130");
//
//        paramsHashtable.put(Plugin.DIVDETAILS, "");
//
//        paramsHashtable.put(Plugin.KEY, PayConstants.BEST_CKEY);
//        paramsHashtable.put(Plugin.ACCOUNTID, collectPhoneNun.getText().toString());
//        paramsHashtable.put(Plugin.BUSITYPE, "04");
//
//        paramsHashtable.put(Plugin.ORDERREQTRANSEQ,
//                System.currentTimeMillis() + "00001");
//        paramsHashtable.put(Plugin.SERVICE, "mobile.security.pay");
//        paramsHashtable.put(Plugin.SIGNTYPE, "MD5");
//
//        String mac = "MERCHANTID="
//                + paramsHashtable.get(Plugin.MERCHANTID)
//                + "&ORDERSEQ="
//                + paramsHashtable.get(Plugin.ORDERSEQ)
//                + "&ORDERREQTRNSEQ="
//                + paramsHashtable.get(Plugin.ORDERREQTRANSEQ)
//                + "&ORDERTIME="
//                + paramsHashtable.get(Plugin.ORDERTIME)
//                + "&KEY=" + PayConstants.BEST_CKEY;
//
//        try {
//            mac = CryptTool.md5Digest(mac);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        paramsHashtable.put(Plugin.MAC, mac);
        getParams();
        
        
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
	private void getParams(){
	    JSONObject jsonObj;
        try
        {
            jsonObj = new JSONObject(payReqInfo);
            for (Iterator iter = jsonObj.keys(); iter.hasNext();) { //先遍历整个 people 对象  
                String key = (String)iter.next(); 
                String value = jsonObj.getString(key);
                paramsHashtable.put(key, value);
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	}
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ORDER_FAIL:
				ToastUtils.show(CollectionBestActivity.this, "下单失败");
				break;
			case ORDER_SUCCESS:
				task.pay((Hashtable<String, String>) msg.obj);
			//	Toast.makeText(CollectionInfoActivity.this, "支付成功"+msg.obj, Toast.LENGTH_SHORT)
				//.show();
				//Log.e("zzm", "支付成功"+msg.obj);
//				finish();
				break;

			default:
				break;
			}
		}
	};
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if(data!=null)
        {
            Log.i("========",resultCode+"；"+data.getExtras().getString("result"));
            Toolkits.esytoLongToast(mContext,data.getExtras().getString("result"));
            if(resultCode==-1){//支付成功
                finish();
            }
        }
    }
}
