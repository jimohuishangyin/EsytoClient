package com.ec2.yspay.activity;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.CashOrCardResponse;
import com.ec2.yspay.http.response.GetQrCodeResponse;
import com.ec2.yspay.http.task.CashOrCardTask;
import com.ec2.yspay.http.task.GetQrCodeTask;
import com.ec2.yspay.widget.Calculator;
import com.ec2.yspay.widget.PopupDialog;
import com.ec2.yspay.widget.Calculator.OnKeyBoradClickListener;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class CashPayActivity extends BaseActivity
{
    private TextView tvYingShou,tvZhaoLing;
    private EditText etShiShou;
    private double money;
    private Calculator mCalculator;
    private String mRemark;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_pay);
        initView();
        money = getIntent().getDoubleExtra("money", 0);
        mRemark = getIntent().getStringExtra("remark");
        tvYingShou.setText(getFormatMoney(money));
        findViewById(R.id.btn_remark).setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                addRemarkDialog();
            }
        });
        
    }
    private void initView(){
        tvYingShou = (TextView)findViewById(R.id.tv_yingshou);
        mCalculator = (Calculator)findViewById(R.id.calculator);
        etShiShou = (EditText)findViewById(R.id.et_shishou);
        tvZhaoLing = (TextView)findViewById(R.id.tv_zhaoling);
        mCalculator.setPlusButtonEnabled(false);
        mCalculator.setMaxValue(9);
        mCalculator.setKeyBoradClickListener(new OnKeyBoradClickListener()
        {
            @Override
            public void onResult(String exp, double value, boolean isPay)
            {
                // TODO Auto-generated method stub
                if(isPay){//下单支付
                    if(value>=money)
                        payByCash(getFormatMoney(value),getFormatMoney(value-money));
                    else
                        showToast("实收金额需大于等于应收金额!");;
                    return;
                }
                etShiShou.setText(exp);
                etShiShou.setSelection(etShiShou.getText().length());
                if(value<=money)
                    tvZhaoLing.setText("0.00");
                else
                    tvZhaoLing.setText(getFormatMoney(value-money));
            }
        });
    }
    private String getFormatMoney(double d){
        return Toolkits.doubleFormat(d); 
    }
    private void payByCash(final String shishou,final String zhaoling){
        OrderInfo orderInfo = new OrderInfo(Toolkits.doubleFormat(money));
        CashOrCardTask task = new CashOrCardTask(mContext, PayTypeEntity.PAY_CASH, orderInfo,mRemark);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                CashOrCardResponse response = (CashOrCardResponse)obj;
                Intent intent = new Intent(mContext, PayCashSuccessActivity.class);
                intent.putExtra("orderId", response.getOrderId());
                intent.putExtra("yingshou",Toolkits.doubleFormat(money));
                intent.putExtra("shishou",shishou);
                intent.putExtra("zhaoling",zhaoling);
                startActivity(intent);
                finish();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                CashOrCardResponse response = (CashOrCardResponse)obj;
                showToast("下单失败："+response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
    private void addRemarkDialog() {

        final PopupDialog.Builder builder = new PopupDialog.Builder(this);
        //builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
        builder.setTitle("收款备注");
        builder.setEditEnabled(true);
        builder.setMessage(mRemark);
        builder.setNegativeButton("重写",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setRemarkText("");
                    
                        
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mRemark = builder.getRemarkText();//这里取得备注信息并作处理
                dialog.dismiss();
                
                
            }
        });

        builder.create().show();

    }
}
