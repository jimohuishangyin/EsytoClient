package com.ec2.yspay;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.OrderDetailResponse;
import com.ec2.yspay.http.task.GetOrderDetailTask;

public class OrderQueryDetailActivity extends BaseActivity {

	private TextView mMoneyTextView,mTypeTextView,mPaymentTextView,mOrderIdTextView,mTimeTextView,mShopTextView,mOperNameTextView,mStutaTextView,mRemarkTextView,mReturnPayTime;
	private ImageView mTypeImageView;
	private String orderId;
    private String orderTime;
    private String channelType;
    private String amount;
    private String statu;
    private String remark;
    private String shopname;
    private String opername;
    private String week;
    private String order_time;
    private String refund_time;
    private  OrderDetailResponse response;
    private PayTypeEntity mPayTypeEnTity;
    private LinearLayout mReturnLinearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvity_order_query_detail);
		String order_id = getIntent().getStringExtra("orderNo");
		mMoneyTextView = (TextView) findViewById(R.id.money);
		mPaymentTextView = (TextView) findViewById(R.id.payment_textview);
		mOrderIdTextView = (TextView) findViewById(R.id.order_id);
		mTimeTextView = (TextView) findViewById(R.id.paytime);
		mShopTextView = (TextView) findViewById(R.id.shoptextview);
		mOperNameTextView = (TextView) findViewById(R.id.oper_name);
		mStutaTextView = (TextView) findViewById(R.id.status);
		mRemarkTextView = (TextView) findViewById(R.id.remark);
		mTypeImageView = (ImageView) findViewById(R.id.type_iamge);
		mTypeTextView = (TextView) findViewById(R.id.type_text);
		mReturnLinearLayout = (LinearLayout) findViewById(R.id.ruturn_back);
		mReturnPayTime = (TextView) findViewById(R.id.returnpaytime);
		getOrderDetailObj(order_id);
	}
	
	private void initView(){
		mMoneyTextView.setText("￥"+amount);
		mPaymentTextView.setText(mPayTypeEnTity.getPayName(channelType));
		mOrderIdTextView.setText(orderId);
		mTimeTextView.setText(orderTime);
		mShopTextView.setText(shopname);
		mOperNameTextView.setText(opername);
		mStutaTextView.setText("");
		mRemarkTextView.setText(remark);
		if(statu.equals("1")){
        }else if(statu.equals("2")){
		}else if(statu.equals("3")){
			mStutaTextView.setText("收款");
			mTypeTextView.setText("已付款");
			mTypeImageView.setImageResource(R.drawable.successed_72_72);
		}else if(statu.equals("4")){
		}else if(statu.equals("5")){
			mStutaTextView.setText("退款");
			mReturnLinearLayout.setVisibility(View.VISIBLE);
			mReturnPayTime.setText(refund_time);
			mTypeTextView.setText("已退款");
			mTypeImageView.setImageResource(R.drawable.yituikuan_icon_72_72);
		}else if(statu.equals("6")){
			mStutaTextView.setText("撤销");
			mTypeTextView.setText("已撤款");
			mTypeImageView.setImageResource(R.drawable.yichexiao_icon_72_72);
		}
	}
	
	
	private void getOrderDetailObj(String detailOrderId){
		
		if(Toolkits.isStrEmpty(detailOrderId))return;
		GetOrderDetailTask getOrderDetail=new GetOrderDetailTask(mContext, detailOrderId);
		getOrderDetail.setProgressVisiable(true);
		getOrderDetail.setProgressMsg("加载中．．．");
		getOrderDetail.setOnTaskFinished(new OnTaskFinished() {
			@Override
			public void onSucc(Object obj) {
				// TODO Auto-generated method stub
				response = (OrderDetailResponse)obj;
		        orderId=response.getOrder_id();
		        orderTime = response.getOrder_time();
		        channelType = response.getChannel_type();
		        amount = response.getAmount();
		        remark = response.getRemark();
		        statu = response.getStatus();
		        shopname = response.getShop_name();
		        opername = response.getOper_name();
		        week = response.getWeek();
		        refund_time = response.getRefund_time();
		        initView();
			}
			
			@Override
			public void onFail(Object obj) {
				// TODO Auto-generated method stub
				OrderDetailResponse response = (OrderDetailResponse)obj;
                Toast.makeText(mContext, response.getResultDesc(), Toast.LENGTH_SHORT).show();
			}
		});
		getOrderDetail.execute();
		
	}
	
	
	
	
}
