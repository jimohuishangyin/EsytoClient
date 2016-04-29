package com.ec2.yspay;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.PaytypeCountItem;
import com.ec2.yspay.http.response.PrintDayAllResponse;
import com.ec2.yspay.http.task.SettleAccountsTask;
import com.ec2.yspay.widget.MyHorizontalBarChart;
import com.ec2.yspay.widget.MyPieChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;

public class SettleAccountsActivity extends BaseActivity 
{
    private String userName;
    private String totalAmount;
    private String totalItems;
    private List<PaytypeCountItem> mList = new ArrayList<PaytypeCountItem>();
    private String cashMoney="0.00",cashCount="0";
    private String cardMoney="0.00",cardCount="0";
    private String bestMoney="0.00",bestCount="0";
    private String aliMoney="0.00",aliCount="0";
    private String wxMoney="0.00",wxCount="0";
    private String beginTime,endTime;
    private TextView tvUser,tvBeginTime,tvEndTime;
    private TextView tvTotal;
    private MyPieChart mPieChart;
    private MyHorizontalBarChart mBarChart;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_accounts);
        getOrderCollect();
    }
    private void initView(){
        tvUser = (TextView)findViewById(R.id.tv_all_user);
        tvBeginTime = (TextView)findViewById(R.id.tv_all_begintime);
        tvEndTime = (TextView)findViewById(R.id.tv_all_endtime);
        tvTotal = (TextView)findViewById(R.id.tv_totalMoney);
        
        tvUser.setText("收银员："+userName);
        tvBeginTime.setText("开始时间："+beginTime);
        tvEndTime.setText("结束时间："+endTime);
        tvTotal.setText("总金额："+totalAmount+"元（"+totalItems+"笔）");
        mBarChart = (MyHorizontalBarChart)findViewById(R.id.chart_bar1);
        mBarChart.setData(cashCount, cardCount, bestCount, aliCount, wxCount);
        mPieChart = (MyPieChart)findViewById(R.id.chart_pie1);
        mPieChart.setData(cashMoney, cardMoney, bestMoney, aliMoney, wxMoney);
        
    }
    private void getOrderCollect(){
        SettleAccountsTask task = new SettleAccountsTask(mContext);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                PrintDayAllResponse response = (PrintDayAllResponse)obj;
                totalAmount = response.getTotalAmount();
                totalItems = response.getTotalItems();
                mList = response.getCollectInfos();
                userName = response.getUserName();
                beginTime = response.getBeginTime();
                endTime = response.getEndTime();
                anaMsg();
                initView();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                PrintDayAllResponse response = (PrintDayAllResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
    private void anaMsg(){
        for(PaytypeCountItem item:mList){
            int payId = Integer.valueOf(item.getChannelType());
            switch (payId)
            {
                case PayTypeEntity.PAY_CASH:
                    cashMoney = item.getTotalAmount();
                    cashCount = item.getTotalItems();
                    break;
                case PayTypeEntity.PAY_UNION:
                    cardMoney = item.getTotalAmount();
                    cardCount = item.getTotalItems();
                    break;
                case PayTypeEntity.PAY_ALI:
                    aliMoney = item.getTotalAmount();
                    aliCount = item.getTotalItems();
                    break;
                case PayTypeEntity.PAY_BEST:
                    bestMoney = item.getTotalAmount();
                    bestCount = item.getTotalItems();
                    break;
                case PayTypeEntity.PAY_WX:
                    wxMoney = item.getTotalAmount();
                    wxCount = item.getTotalItems();
                    break;
                default:
                    break;
            }
        }
    }
    
}
