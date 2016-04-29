package com.ec2.yspay.activity;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.CashCollectOtherResponse;
import com.ec2.yspay.http.task.CashCollectOtherTask;
import com.ec2.yspay.pay.PayConstants;
import com.ec2.yspay.pay.wchat.MD5;
import com.ec2.yspay.pay.wchat.Util;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CollectWChatActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "MicroMsg.SDKSample.PayActivity";
	private Context mContext;
	
	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(CollectWChatActivity.this, null);
	//TextView show;
	Map<String,String> resultunifiedorder;
	StringBuffer sb;
	Button wchatCollectPay;
	private String money,transDate;
	private TextView tvUserName,tvShopName,tvMoney;
	private String orderId,payReqInfo;
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_wchat);
        mContext=this;
		req = new PayReq();
		sb=new StringBuffer();
		msgApi.registerApp(PayConstants.APP_ID);
		
		money = getIntent().getStringExtra("money");
		transDate = getIntent().getStringExtra("transDate");
		tvUserName = (TextView)findViewById(R.id.tv_username);
		tvShopName = (TextView)findViewById(R.id.tv_address);
		tvMoney = (TextView)findViewById(R.id.tv_money);
		tvUserName.setText(MyApplication.mDataCache.userName);
		tvShopName.setText(MyApplication.mDataCache.shopName);
		tvMoney.setText(money);
		
		getOrderMsg();
		wchatCollectPay=(Button) findViewById(R.id.wchat_collect_pay);
		wchatCollectPay.setOnClickListener(this);

    }
	private void getOrderMsg(){
	    OrderInfo orderInfo = new OrderInfo(money);
//	    OrderInfo orderInfo = new OrderInfo("0.01");
	    CashCollectOtherTask task = new CashCollectOtherTask(mContext, PayTypeEntity.PAY_WX, orderInfo,transDate);
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
                GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
                getPrepayId.execute();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                CashCollectOtherResponse response = (CashCollectOtherResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
	    task.execute();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==wchatCollectPay){
			genPayReq();
			sendPayReq();
			finish();
		}
	}


	
	/**
	 生成签名
	 */

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(PayConstants.API_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",appSign);
		return appSign;
	}

	private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {

		private ProgressDialog dialog;


		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(CollectWChatActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String,String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
			//show.setText(sb.toString());
			resultunifiedorder=result;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String,String>  doInBackground(Void... params) {

			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
//			String entity = genProductArgs();
			String entity = payReqInfo;

			Log.e("orion",entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String,String> xml=decodeXml(content);

			return xml;
		}
	}

	public Map<String,String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if("xml".equals(nodeName)==false){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion",e.toString());
		}
		return null;

	}


	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	


//	private String genOutTradNo() {
//		Random random = new Random();
//		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//	}
//	
//
//   //
//	private String genProductArgs() {
//		StringBuffer xml = new StringBuffer();
//
//		try {
//			String	nonceStr = genNonceStr();
//
//
//			xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//			packageParams.add(new BasicNameValuePair("appid", PayConstants.APP_ID));
//			packageParams.add(new BasicNameValuePair("body", "weixin"));
//			packageParams.add(new BasicNameValuePair("mch_id", PayConstants.MCH_ID));
//			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//			packageParams.add(new BasicNameValuePair("notify_url", "http://121.40.35.3/test"));
//			packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
//			packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
//			packageParams.add(new BasicNameValuePair("total_fee", "1"));
//			packageParams.add(new BasicNameValuePair("trade_type", "APP"));
//
//
//			String sign = genPackageSign(packageParams);
//			packageParams.add(new BasicNameValuePair("sign", sign));
//
//
//		   String xmlstring =toXml(packageParams);
//
//			return xmlstring;
//
//		} catch (Exception e) {
//			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
//			return null;
//		}
//		
//
//	}
	public void genPayReq() {

		req.appId = PayConstants.APP_ID;
		req.partnerId = PayConstants.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());


		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n"+req.sign+"\n\n");

		//show.setText(sb.toString());

		Log.e("orion", signParams.toString());
	}
	public void sendPayReq() {

		msgApi.registerApp(PayConstants.APP_ID);
		msgApi.sendReq(req);
	}




}

