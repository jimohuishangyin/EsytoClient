package com.ec2.yspay.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ec2.yspay.R;
import com.ec2.yspay.adapter.PrintListAdapter;
import com.ec2.yspay.entity.PrintOrderEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.GetOrderListAllResponse;
import com.ec2.yspay.http.task.GetOrderListAllTask;
import com.ec2.yspay.widget.PullToRefreshLayout;
import com.ec2.yspay.widget.PullToRefreshLayout.OnRefreshListener;

public class PrintListActivity extends BaseActivity implements OnRefreshListener
{
    ListView listView;
    PrintListAdapter adapter;
    List<PrintOrderEntity> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_list);
        PullToRefreshLayout layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        layout.setPullDownAble(false);
        layout.setPullUpAble(false);
        layout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.content_view);
        getOrderList();
    }
    /**
     * ListView初始化方法
     */
    private void initListView()
    {
        adapter = new PrintListAdapter(this, orderList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Intent intent =new Intent(mContext, PrintPreviewActivity.class);
                intent.putExtra("orderItem", orderList.get(position));
                startActivity(intent);
                
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
//                items.clear();
//                getData();
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
//                getData();
                adapter.notifyDataSetChanged();
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }
    private void getOrderList(){
        GetOrderListAllTask task = new GetOrderListAllTask(mContext);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                GetOrderListAllResponse response = (GetOrderListAllResponse)obj;
                orderList = response.getOrderList();
                initListView();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                GetOrderListAllResponse response = (GetOrderListAllResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
}
