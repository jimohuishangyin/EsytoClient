package com.ec2.yspay.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.adapter.PrintDetailAdapter;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.PrintDetailItem;
import com.ec2.yspay.http.response.PrintDetailResponse;
import com.ec2.yspay.http.task.PrintDetailTask;
import com.ec2.yspay.print.PrintDetailEntity;
import com.ec2.yspay.print.PrintManager;
import com.ec2.yspay.widget.PullToRefreshLayout;
import com.ec2.yspay.widget.PullToRefreshLayout.OnRefreshListener;

public class PrintDetailActivity extends BaseActivity implements OnRefreshListener
{
    ListView listView;
    PrintDetailAdapter adapter;
    private String userName;
    private String totalAmount;
    private String totalItems;
    private String beginTime,endTime;
    private List<PrintDetailItem> mList = new ArrayList<PrintDetailItem>();
    private TextView tvYear,tvDate,tvUser,tvTime,tvTotalCount,tvTotalMoney;
    private Button btnSure;
    private PrintManager printManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_detail);
        printManager = PrintManager.getInstance(mContext);
        PullToRefreshLayout layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        btnSure = (Button)findViewById(R.id.btn_sure);
        layout.setOnRefreshListener(this);
        layout.setPullDownAble(false);
        layout.setPullUpAble(false);
        listView = (ListView) findViewById(R.id.content_view);
//        initListView();
        getData2();
        btnSure.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
            	print();
            }
        });
    }
    
    
    
    private void print(){
    	Date tDate = new Date();
        PrintDetailEntity entity = new PrintDetailEntity(userName,tDate,totalAmount,totalItems,mList);
        printManager.setmPrintDetailEntity(entity);
        printManager.printDetail();
    }
    private void getData2(){
        PrintDetailTask task = new PrintDetailTask(mContext);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                PrintDetailResponse response = (PrintDetailResponse)obj;
                totalAmount = response.getTotalAmount();
                totalItems = response.getTotalItems();
                mList = response.getPosDetail();
                userName = response.getUserName();
                beginTime = response.getBeginTime();
                endTime = response.getEndTime();
                initListView();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                PrintDetailResponse response = (PrintDetailResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
    /**
     * ListView初始化方法
     */
    private void initListView()
    {
        tvYear = (TextView)findViewById(R.id.tv_year);
        tvYear.setText(beginTime.substring(0, 10));
        tvUser = (TextView)findViewById(R.id.tv_operator); 
        tvUser.setText(userName);
        tvTotalCount = (TextView)findViewById(R.id.tv_zmmx);
        tvTotalMoney = (TextView)findViewById(R.id.tv_totalMoney);
        tvTotalCount.setText(totalItems+"笔");
        tvTotalMoney.setText(totalAmount+"元");
        adapter = new PrintDetailAdapter(this, mList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
            }
        });
    }
    /**
     * 重载方法
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
    {
        // TODO Auto-generated method stub
     // 下拉刷新操作
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                adapter.notifyDataSetChanged();
                // 千万别忘了告诉控件刷新完毕了哦！
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }
    /**
     * 重载方法
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
    {
        // TODO Auto-generated method stub
     // 加载操作
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                // 千万别忘了告诉控件加载完毕了哦！
                adapter.notifyDataSetChanged();
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }
    
}
