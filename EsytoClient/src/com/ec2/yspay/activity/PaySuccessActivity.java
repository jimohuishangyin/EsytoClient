package com.ec2.yspay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.entity.PrintOrderEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.PrintByOrderidResponse;
import com.ec2.yspay.http.task.PrintByOrderidTask;
import com.ec2.yspay.print.PrintManager;
import com.ec2.yspay.widget.ButtonWhiteCenter;
import com.ec2.yspay.widget.MyTitle;

public class PaySuccessActivity extends BaseActivity
{
    private double money = -1;
    private TextView tvMoney;
    private MyTitle myTitle;
    private PayTypeEntity payType;
    private String orderId;
    private PrintManager mPrintManager;
    private ButtonWhiteCenter mPrintButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        mPrintManager = PrintManager.getInstance(mContext);
        money = getIntent().getDoubleExtra("money", 0);
        orderId = getIntent().getStringExtra("orderId");
        tvMoney = (TextView)findViewById(R.id.tv_money);
        tvMoney.setText("￥"+Toolkits.doubleFormat(money));
        payType = (PayTypeEntity)getIntent().getSerializableExtra("payType");
        myTitle = (MyTitle) findViewById(R.id.rl_top);
        myTitle.setTitleText(payType.getPayName()+"收款");
        mPrintButton =(ButtonWhiteCenter)findViewById(R.id.btn_print);
        MyTitle myTitle = (MyTitle)findViewById(R.id.rl_top);
        myTitle.setLeftOnclickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                MyApplication.toMainActivity();
            }
        });
        mPrintButton.setBtnOnclickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
            	getOrderMsg(orderId, mContext);
            }
        });
    }
    public void onclick_back(View v){
        MyApplication.toMainActivity();
    }
    
    private void getOrderMsg(String orderId, final Context mContext){

        PrintByOrderidTask task = new PrintByOrderidTask(mContext, orderId);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished() {

            @Override
            public void onSucc(Object obj) {
                PrintByOrderidResponse response = (PrintByOrderidResponse) obj;
                PrintOrderEntity mPrintOrderEntity = response.getPrintMsg();
                PrintOrderEntity entity = new PrintOrderEntity(mPrintOrderEntity.getTransactionId(), mPrintOrderEntity.getOrderId(),
                        mPrintOrderEntity.getOrderTime(), mPrintOrderEntity.getAccount(), mPrintOrderEntity.getAmount(),
                        mPrintOrderEntity.getRemark(), mPrintOrderEntity.getShopName(), mPrintOrderEntity.getChannelType());
                mPrintManager.setmPrintOrderEntity(entity);
                mPrintManager.printOneDetailNew();
            }

            @Override
            public void onFail(Object obj) {
                // TODO Auto-generated method stub
                PrintByOrderidResponse response = (PrintByOrderidResponse) obj;
                ToastUtils.show(mContext, response.getResultDesc());
            }
        });
        task.execute();
    }
}
