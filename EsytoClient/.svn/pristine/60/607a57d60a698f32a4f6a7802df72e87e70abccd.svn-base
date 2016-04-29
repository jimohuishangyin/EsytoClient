package com.ec2.yspay.activity;

import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.DateUtils;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.QrOrderMsgResponse;
import com.ec2.yspay.http.task.QrOrderMsgTask;
import com.ec2.yspay.print.PrintManager;
import com.ec2.yspay.widget.ButtonWhiteCenter;
import com.ec2.yspay.widget.MyTitle;

public class RefundSuccessActivity extends BaseActivity
{
    private String orderId;
    private String orderTime;
    private String channelType;
    private String amount;
    private boolean isCeXiao;
    private TextView tvOrderId,tvOrderTime,tvOrderChannelType,tvAmount,tvRemark,tvRefund;
    private MyTitle myTitle;
    private String mTitle;
    private String remark;
    private ButtonWhiteCenter mPrintButton;
    private PrintManager mPrintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_success);
        mPrintManager = PrintManager.getInstance(mContext);
        tvOrderId = (TextView)findViewById(R.id.tv_orderid);
        tvOrderTime = (TextView)findViewById(R.id.tv_orderTime);
        tvOrderChannelType = (TextView)findViewById(R.id.tv_channelType);
        tvAmount = (TextView)findViewById(R.id.tv_amount);
        tvRefund = (TextView) findViewById(R.id.tv_refund_succ);
        orderId = getIntent().getStringExtra("orderId");
        orderTime = getIntent().getStringExtra("orderTime");
        channelType = getIntent().getStringExtra("channelType");
        amount = getIntent().getStringExtra("amount");
        remark = getIntent().getStringExtra("remark");
        isCeXiao = getIntent().getBooleanExtra("isCeXiao", false);
        tvRemark = (TextView)findViewById(R.id.tv_remark);
        tvRemark.setText(remark);
        mPrintButton = (ButtonWhiteCenter)findViewById(R.id.btn_print);
        initView();
        mTitle = getIntent().getStringExtra("title");
        myTitle = (MyTitle)findViewById(R.id.rl_top);
        myTitle.setTitleText(mTitle+"详情");
        tvRefund.setText(mTitle+"成功！");
        myTitle.setLeftOnclickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MyApplication.toMainActivity();
            }
        });
        mPrintButton.setBtnOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getOrderMsg(orderId);
			}
		});
    }
    private void initView(){
        tvOrderId.setText(orderId);
        tvOrderTime.setText(orderTime);
        tvOrderChannelType.setText(PayTypeEntity.getPayName(channelType));
        tvAmount.setText("￥"+amount);
    }
    public void onclick_back(View v){
      MyApplication.toMainActivity();
    }
  //交易号查询及列表查询
    private void getOrderMsg(String orderId){
        QrOrderMsgTask task = new QrOrderMsgTask(mContext, orderId);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                QrOrderMsgResponse response = (QrOrderMsgResponse)obj;
                Date tDate = new Date();
                String printTime = DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", tDate);
                mPrintManager.setmResponse(response);
                mPrintManager.setPrintTime(printTime);
                mPrintManager.printRefund();
            }
            @Override
            public void onFail(Object obj)
            {
                QrOrderMsgResponse response = (QrOrderMsgResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
}
