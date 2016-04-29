/*
 * 类文件名:  ReportExpandableAdapter.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月31日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ec2.yspay.R;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.entity.ReportExpandableListItem;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.OrderItem;
import com.ec2.yspay.http.response.ReportDetailsResponse;
import com.ec2.yspay.http.task.ReportDetailsMonthTask;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月31日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportExpandableAdapter extends BaseExpandableListAdapter
{
    LayoutInflater group;
    LayoutInflater child;
    private List<ReportExpandableListItem> mList;
    private Context mContext ;
    private Handler mHandler;
    
    public ReportExpandableAdapter(Context c,List<ReportExpandableListItem> mList,Handler mHandler){
        this.group = LayoutInflater.from(c);
        this.child = LayoutInflater.from(c);
        this.mList = mList;
        this.mContext = c;
        this.mHandler = mHandler;
    }
    public void setDateList(List<ReportExpandableListItem> mList){
        this.mList = mList;
    }
    /**
     * 重载方法
     * @return
     */
    @Override
    public int getGroupCount()
    {
        // TODO Auto-generated method stub
        return mList.size();
    }

    /**
     * 重载方法
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition)
    {
        // TODO Auto-generated method stub
        if(mList.size()<1)
            return 0;
        List<OrderItem> list = mList.get(groupPosition).getOrderList();
        if(list==null||list.size()==0)
            return 1;
        else
            return list.size();
    }

    /**
     * 重载方法
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition)
    {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    /**
     * 重载方法
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        // TODO Auto-generated method stub
        return childPosition;
    }

    /**
     * 重载方法
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition)
    {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    /**
     * 重载方法
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        // TODO Auto-generated method stub
        return childPosition;
    }

    /**
     * 重载方法
     * @return
     */
    @Override
    public boolean hasStableIds()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * 重载方法
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        View myView = group.inflate(R.layout.report_expandable_list_parent, null);
        ImageView iv = (ImageView)myView.findViewById(R.id.iv_arrow);
        TextView tv = (TextView)myView.findViewById(R.id.exp_name);
        ReportExpandableListItem item = mList.get(groupPosition);
        tv.setText(item.getDate());
        if(isExpanded){
            iv.setImageResource(R.drawable.up_icon);
        }else{
            iv.setImageResource(R.drawable.down_icon);
        }
//        tv.setText(groupString[groupPosition]);
        myView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, Toolkits.dip2px(mContext, 50)));
        return myView;
    }

    /**
     * 重载方法
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
        ViewGroup parent)
    {
        // TODO Auto-generated method stub
        View myView = child.inflate(R.layout.report_expandable_list_child, null);
        TextView tvType = (TextView)myView.findViewById(R.id.tv_payname);
        TextView tv = (TextView)myView.findViewById(R.id.tv_orderid);
        TextView tvMoney = (TextView)myView.findViewById(R.id.tv_money);
        ImageView ivIcon = (ImageView)myView.findViewById(R.id.iv_payicon);
        RelativeLayout llNormal = (RelativeLayout)myView.findViewById(R.id.ll_state_normal);
        LinearLayout llNoData = (LinearLayout)myView.findViewById(R.id.ll_state_nodata);
        LinearLayout llLoading = (LinearLayout)myView.findViewById(R.id.ll_state_loading);
        if(childPosition==0){
            List<OrderItem> orders = mList.get(groupPosition).getOrderList();
            if(orders==null){
                getDayDetails(groupPosition);
                tv.setText("正在加载中。。。");
                llLoading.setVisibility(View.VISIBLE);
                llNormal.setVisibility(View.GONE);
            }else if(orders.size()<1){
                tv.setText("无数据");
                llNoData.setVisibility(View.VISIBLE);
                llNormal.setVisibility(View.GONE);
            }else{
                OrderItem item = mList.get(groupPosition).getOrderList().get(childPosition);
                tv.setText(item.getOrderNo());
                tvType.setText(PayTypeEntity.getPayName(item.getChannelType()));
                tvMoney.setText(item.getAmount()+"元");
                llNormal.setVisibility(View.VISIBLE);
                llNoData.setVisibility(View.GONE);
                
                ivIcon.setImageResource(PayTypeEntity.get72ImgId(item.getChannelType()));
            }
        }else{
            OrderItem item = mList.get(groupPosition).getOrderList().get(childPosition);
            tv.setText(item.getOrderNo());
            tvType.setText(PayTypeEntity.getPayName(item.getChannelType()));
            tvMoney.setText(item.getAmount()+"元");
            llNormal.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
            ivIcon.setImageResource(PayTypeEntity.get72ImgId(item.getChannelType()));
        }
        return myView;
    }

    /**
     * 重载方法
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        // TODO Auto-generated method stub
        return true;
    }
    private void getDayDetails(final int groupPosition){
        ReportExpandableListItem item = mList.get(groupPosition);
        ReportDetailsMonthTask task = new ReportDetailsMonthTask(mContext, item.getYear()
            +"-"+item.getMonth()+"-"+item.getDay());
//        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                ReportDetailsResponse response = (ReportDetailsResponse)obj;
//                ReportExpandableListItem item = new ReportExpandableListItem(mDay+"日 - "
//                +response.getWeek(), response.getOrderList());
//                mList.add(item);
                mList.get(groupPosition).setOrderList(response.getOrderList());;
//                leftFragment.setList(mList);
                Message msg = new Message();
                msg.arg1 = groupPosition;
                mHandler.sendMessage(msg);
            }
            
            

            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                ReportDetailsResponse response = (ReportDetailsResponse)obj;
                ToastUtils.show(mContext,  response.getResultDesc());
            }
        });
        task.execute();
    }
    
}
