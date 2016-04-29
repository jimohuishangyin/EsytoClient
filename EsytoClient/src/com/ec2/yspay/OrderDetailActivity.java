package com.ec2.yspay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.OrderDetailResponse;
import com.ec2.yspay.http.response.QrOrderMsgResponse;
import com.ec2.yspay.http.task.GetOrderDetailTask;
import com.ec2.yspay.http.task.QrOrderMsgTask;

public class OrderDetailActivity extends BaseActivity
{
	public final static String ORDER_ID="order_id";
    private String orderId;
    private String orderTime;
    private String channelType;
    private String voucherId;
    private String amount;
    private String transaction_id;
    private String remark;
    private  OrderDetailResponse response;
    private TextView tvOrderId,tvOrderTime,tvOrderChannelType,tvVoucherId,tvAmount,tvRemark;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        
        tvOrderId = (TextView)findViewById(R.id.tv_orderid);
        tvOrderTime = (TextView)findViewById(R.id.tv_orderTime);
        tvOrderChannelType = (TextView)findViewById(R.id.tv_channelType);
        tvVoucherId = (TextView)findViewById(R.id.tv_voucherId);
        tvAmount = (TextView)findViewById(R.id.tv_amount);
        tvRemark = (TextView)findViewById(R.id.tv_remark);
       // getOrderMsg();
        
        
        orderId = getIntent().getExtras().getString(ORDER_ID);  
        getOrderDetailObj(orderId);



    
    }
    private void initView(){
        tvOrderId.setText("交易流水:   "+orderId);
        tvOrderTime.setText(orderTime);//(orderTime+"   "+response.getWeek());
        tvOrderChannelType.setText("支付方式:   "+PayTypeEntity.getPayName(channelType));
        tvVoucherId.setText(voucherId);
        tvAmount.setText("金额:   ￥"+amount);
        tvRemark.setText(remark);
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
		        voucherId = response.getVoucher_id();
		        amount = response.getAmount();
		        transaction_id = response.getTransaction_id();
		        remark = response.getRemark();
		        if(Toolkits.isStrEmpty(voucherId)){
		            voucherId = transaction_id;
		        }
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
