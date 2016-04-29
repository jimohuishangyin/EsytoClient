package com.ec2.yspay;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.TextView;

import com.ec2.yspay.R.integer;
import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.LimitItem;
import com.ec2.yspay.http.response.QrLimitMoneyResponse;
import com.ec2.yspay.http.task.QrLimitMoneyTask;

public class LimitMoneyActivity extends BaseActivity
{
    private TextView tvLimitCardDay,tvLimitCardMonth,tvLimitBestDay,tvLimitBestMonth;
    private TextView tvLimitAliDay,tvLimitAliMonth,tvLimitWxDay,tvLimitWxMonth;
    private List<LimitItem> limitList = new ArrayList<LimitItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_money);
        tvLimitCardDay = (TextView)findViewById(R.id.tv_limit_card_day);
        tvLimitCardMonth = (TextView)findViewById(R.id.tv_limit_card_month);
        tvLimitBestDay = (TextView)findViewById(R.id.tv_limit_best_day);
        tvLimitBestMonth = (TextView)findViewById(R.id.tv_limit_best_month);
        tvLimitAliDay = (TextView)findViewById(R.id.tv_limit_ali_day);
        tvLimitAliMonth = (TextView)findViewById(R.id.tv_limit_ali_month);
        tvLimitWxDay = (TextView)findViewById(R.id.tv_limit_wx_day);
        tvLimitWxMonth = (TextView)findViewById(R.id.tv_limit_wx_month);
        getLimitMsg();
    }
    private void initView(){
        for(LimitItem item:limitList){
            int payId = Integer.valueOf(item.getChannelType());
            switch (payId)
            {
                case PayTypeEntity.PAY_ALI:
                    tvLimitAliDay.setText("￥"+item.getDayLimit());
                    tvLimitAliMonth.setText("/￥"+item.getMonthLimit());
                    break;
                case PayTypeEntity.PAY_UNION:
                    tvLimitCardDay.setText("￥"+item.getDayLimit());
                    tvLimitCardMonth.setText("/￥"+item.getMonthLimit());
                    break;
                case PayTypeEntity.PAY_BEST:
                    tvLimitBestDay.setText("￥"+item.getDayLimit());
                    tvLimitBestMonth.setText("/￥"+item.getMonthLimit());
                    break;
                case PayTypeEntity.PAY_WX:
                    tvLimitWxDay.setText("￥"+item.getDayLimit());
                    tvLimitWxMonth.setText("/￥"+item.getMonthLimit());
                    break;
                default:
                    break;
            }
        }
    }
    private void getLimitMsg(){
        QrLimitMoneyTask task = new QrLimitMoneyTask(mContext);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                QrLimitMoneyResponse response = (QrLimitMoneyResponse)obj;
                limitList = response.getLimitList();
                initView();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                QrLimitMoneyResponse response = (QrLimitMoneyResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
}
