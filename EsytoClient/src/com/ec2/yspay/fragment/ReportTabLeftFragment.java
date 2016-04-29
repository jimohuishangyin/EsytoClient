package com.ec2.yspay.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.ec2.yspay.OrderDetailActivity;
import com.ec2.yspay.OrderQueryDetailActivity;
import com.ec2.yspay.OrderQuerySearchActivity;
import com.ec2.yspay.R;
import com.ec2.yspay.adapter.ReportExpandableAdapter;
import com.ec2.yspay.entity.ReportExpandableListItem;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.OrderItem;
import com.ec2.yspay.http.response.OrderDetailResponse;
import com.ec2.yspay.http.task.GetOrderDetailTask;

/**
 * 明细
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月8日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportTabLeftFragment extends Fragment{
    private static final String TAG = "number";
    private Context mContext;
    private View view;
    private LayoutInflater inflater;
    private ExpandableListView elvList;
    private List<ReportExpandableListItem> mList = new ArrayList<ReportExpandableListItem>();
    ReportExpandableAdapter adapter;
    public ReportTabLeftFragment(){
        
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    this.inflater = inflater;
		view = inflater.inflate(R.layout.fragment_report_tableft, null);
		mContext = getActivity();
		elvList = (ExpandableListView)view.findViewById(R.id.elv_list);
		adapter = new ReportExpandableAdapter(mContext, mList,mHandler);
		
		elvList.setGroupIndicator(null);
		elvList.setAdapter(adapter);
		elvList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
			    List<OrderItem> orderList = mList.get(groupPosition).getOrderList();
			    if(orderList!=null&&orderList.size()>childPosition){
    				String orderId=orderList.get(childPosition).getOrderNo();
    				
    				Intent intent = new Intent(mContext,
    						OrderQueryDetailActivity.class);
    				intent.putExtra("orderNo", orderId);
    				startActivity(intent);
//					 Intent mIntent = new Intent(mContext,OrderDetailActivity.class);  
//					 mIntent.putExtra(OrderDetailActivity.ORDER_ID,orderId); 
//				        startActivity(mIntent);  
    				
    			
			    }
				return false;
			}
		});

//		elvList.expandGroup(0);
		return view;
	}
	public void setList(List<ReportExpandableListItem> mList){
	    for(int i=0;i<mList.size();i++){
	        elvList.collapseGroup(i); 
	        
	    }
	    this.mList = mList;
	    adapter.notifyDataSetChanged();
	    adapter.setDateList(mList);
	    elvList.collapseGroup(0);  
	    elvList.expandGroup(0); 
	    elvList.smoothScrollToPosition(0);
	}
	private Handler mHandler = new Handler(){

        /**
         * 重载方法
         * @param msg
         */
        @Override
        public void handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int position = msg.arg1;
            elvList.collapseGroup(position);  
            elvList.expandGroup(position); 
        }
	    
	};
}
