package com.ec2.yspay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.R.id;
import com.ec2.yspay.R.layout;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.PayWithCodeResponse;
import com.ec2.yspay.http.task.PayWithCodeTask;
import com.ec2.yspay.widget.MyTitle;

public class PayInputActivity extends BaseActivity implements OnClickListener
{
    private PayTypeEntity payType;
    private double money = 1;
    private TextView tvMoney;
    private Button btnSure;
    private EditText etPayCode;
    private MyTitle myTitle;
    private String remark;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_input);
        money = getIntent().getDoubleExtra("money", 0);
        remark = getIntent().getStringExtra("remark");
        payType = (PayTypeEntity)getIntent().getSerializableExtra("payType");
        myTitle = (MyTitle) findViewById(R.id.rl_top);
        myTitle.setTitleText(payType.getPayName()+"收款");
        tvMoney = (TextView)findViewById(R.id.tv_money);
        tvMoney.setText("￥"+Toolkits.doubleFormat(money));
        btnSure = (Button)findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);
        etPayCode = (EditText)findViewById(R.id.et_paycode);
    }
    /**
     * 重载方法
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.btn_sure:
                String code = etPayCode.getText().toString().trim();
                if(code==null||code.length()<3){
                    showToast("请先输入付款码！");
                }else{
                    payWithCode(code);
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
    private void payWithCode(String code){
        OrderInfo orderInfo = new OrderInfo(Toolkits.doubleFormat(money));
//        orderInfo.setBody("测试订单详情明细。。。");
//        orderInfo.setSubject("测试订单标题");
        PayWithCodeTask task = new PayWithCodeTask(mContext, payType.getPayId(), orderInfo, code,remark);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                PayWithCodeResponse response = (PayWithCodeResponse)obj;
                String orderId = response.getOrderId();
                String result = response.getTranResult();
                if("TRADE_SUCCESS".equals(result)){
                    Intent intent = new Intent(mContext, PaySuccessActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("payType", payType);
                    startActivity(intent);
                } else{
                    Intent intent = new Intent(mContext, PayWaittingActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("payType", payType);
                    startActivity(intent); 
                }
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                PayWithCodeResponse response = (PayWithCodeResponse)obj;
                showToast("支付失败"+response.getResultDesc());
            }
        });
        task.execute();
    }
}