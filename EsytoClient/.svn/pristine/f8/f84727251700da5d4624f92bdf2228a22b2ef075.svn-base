package com.ec2.yspay.adapter;

import java.util.List;

import com.ec2.yspay.R;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.entity.ProvinceItem;
import com.ec2.yspay.http.cash.OrderItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RefundListViewdapter extends BaseAdapter {
	List<OrderItem> items;
	Context context;
	private int parentPositon;
	public RefundListViewdapter(Context context, List<OrderItem> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    ViewHolder viewHolder = null;
	    if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_refund, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_orderid = (TextView)convertView.findViewById(R.id.tv_orderid);
            viewHolder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            viewHolder.tv_amount = (TextView)convertView.findViewById(R.id.tv_amount);
            viewHolder.tv_paytype = (TextView)convertView.findViewById(R.id.tv_paytype);
            viewHolder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    OrderItem item = items.get(position);
        viewHolder.tv_orderid.setText(item.getOrderNo());  
        viewHolder.tv_time.setText(item.getOrderTime().length()>16?item.getOrderTime().substring(11):item.getOrderTime()); 
        viewHolder.tv_amount.setText(item.getAmount()); 
        viewHolder.tv_paytype.setText(PayTypeEntity.getPayName(item.getChannelType())+""); 
        viewHolder.iv_icon.setImageResource(PayTypeEntity.get96ImgId(item.getChannelType()));
        return convertView;
	}
	class ViewHolder {
	    ImageView iv_icon;
        TextView tv_orderid,tv_time,tv_amount,tv_paytype;
        LinearLayout ll_parent;
	}

}
