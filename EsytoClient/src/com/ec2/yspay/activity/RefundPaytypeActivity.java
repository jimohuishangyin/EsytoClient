package com.ec2.yspay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.ec2.yspay.R;

public class RefundPaytypeActivity extends BaseActivity implements OnClickListener
{
    private RelativeLayout rllBtnOther;
    private RelativeLayout rllBtnCash;
    private RelativeLayout rllBtnCard;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_paytype);
        rllBtnOther = (RelativeLayout)findViewById(R.id.llbtn_refund_other);
        rllBtnCash = (RelativeLayout)findViewById(R.id.llbtn_refund_cash);
        rllBtnCard = (RelativeLayout)findViewById(R.id.llbtn_refund_card);
        rllBtnOther.setOnClickListener(this);
        rllBtnCash.setOnClickListener(this);
        rllBtnCard.setOnClickListener(this);
    }
    
    /**
     * 重载方法
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        Intent intent = new Intent(mContext, RefundManagerPwdActivity.class);
        switch (v.getId())
        {
            
            case R.id.llbtn_refund_cash:
                intent.putExtra("payType", "cash");
                break;
            case R.id.llbtn_refund_card:
                intent.putExtra("payType", "card");
                break;
            case R.id.llbtn_refund_other:
                intent.putExtra("payType", "other");
                
                break;
            default:
                break;
                
        }
        startActivity(intent);
        
    }
}
