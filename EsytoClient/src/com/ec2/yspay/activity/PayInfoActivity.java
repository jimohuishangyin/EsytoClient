package com.ec2.yspay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.OrderInfo;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.PayWithCodeResponse;
import com.ec2.yspay.http.task.PayWithCodeTask;
import com.ec2.yspay.http.task.TradeCancelTask;
import com.ec2.yspay.widget.PayLinearLayoutButton;
import com.ec2.yspay.zxing.activity.CaptureActivity;

public class PayInfoActivity extends BaseActivity implements OnClickListener
{
    private Context mContext = this;
    private double money = 1;
    private TextView tvPayTitle;
    private TextView tvMoney,tvUserName,tvShopName;
    private PayTypeEntity payType;
    private PayLinearLayoutButton llbtnPaika,llbtnSaoma,llbtnQrcode,llbtnShare;
    private ImageView ivPayIcon;
    private String mRemark;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_info);
        money = getIntent().getDoubleExtra("money", 0);
        mRemark = getIntent().getStringExtra("remark");
        payType = (PayTypeEntity)getIntent().getSerializableExtra("payType");
        tvPayTitle = (TextView)findViewById(R.id.tv_paytitle);
        llbtnPaika = (PayLinearLayoutButton)findViewById(R.id.llbtn_paika);
        llbtnSaoma = (PayLinearLayoutButton)findViewById(R.id.llbtn_saoma);
        llbtnQrcode = (PayLinearLayoutButton)findViewById(R.id.llbtn_qrcode);
        llbtnShare = (PayLinearLayoutButton)findViewById(R.id.llbtn_share);
        llbtnPaika.setBtnOnclickListener(this);
        llbtnSaoma.setBtnOnclickListener(this);
        llbtnQrcode.setBtnOnclickListener(this);
        llbtnShare.setBtnOnclickListener(this);
        tvMoney = (TextView)findViewById(R.id.tv_money);
        tvPayTitle.setText(payType.getPayName());
        tvMoney.setText("￥"+Toolkits.doubleFormat(money));
        ivPayIcon = (ImageView)findViewById(R.id.iv_payicon);
        ivPayIcon.setImageResource(payType.getSmallImgId());
        if(payType.getPayId()==PayTypeEntity.PAY_BEST){
            llbtnQrcode.setVisibility(View.GONE);
            llbtnPaika.setVisibility(View.GONE);
        }
        llbtnSaoma.setTitleText("扫用户"+payType.getPayName()+"二维码");
        tvUserName = (TextView)findViewById(R.id.tv_username);
        tvShopName = (TextView)findViewById(R.id.tv_shopname);
        tvUserName.setText(MyApplication.mDataCache.userName);
        tvShopName.setText(MyApplication.mDataCache.shopName);
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
            case R.id.llbtn_paika:
                Intent intent2 = new Intent(mContext, PayPaikaActivity.class);
                intent2.putExtra("money", money);
                intent2.putExtra("payType", payType);
                startActivity(intent2);
                break;
            case R.id.llbtn_qrcode:
                Intent intent = new Intent(mContext, UserScanActivity.class);
                intent.putExtra("remark", mRemark);
                intent.putExtra("money", money);
                intent.putExtra("payType", payType);
                startActivity(intent);
                break;
            case R.id.llbtn_saoma:
                Intent saomaIntent = new Intent(mContext, CaptureActivity.class);
                saomaIntent.putExtra("money", money);
                saomaIntent.putExtra("remark", mRemark);
                saomaIntent.putExtra("payType", payType);
                startActivityForResult(saomaIntent, 101);
                break;
            case R.id.llbtn_share:
                break;
        }
    }
    /**
     * 重载方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        if(requestCode==101&&resultCode==100){
            String codeMsg = data.getStringExtra("qrCodeFromScan");
            payWithCode(codeMsg);
        }
    }
    private void payWithCode(String code){
        OrderInfo orderInfo = new OrderInfo(Toolkits.doubleFormat(money));
        orderInfo.setBody("测试订单详情明细。。。");
        orderInfo.setSubject("测试订单标题");
        PayWithCodeTask task = new PayWithCodeTask(mContext, payType.getPayId(), orderInfo, code,mRemark);
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
