package com.ec2.yspay.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.task.UpdateStaffStatusTask;

public class StaffAddListAdapter extends BaseAdapter {
	List<StaffItem> items;
	Context mContext;
	public StaffAddListAdapter(Context context, List<StaffItem> items) {
		this.mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_staff_add, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_phone = (TextView)convertView.findViewById(R.id.tv_phone);
            viewHolder.tv_duty = (TextView)convertView.findViewById(R.id.tv_duty);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    StaffItem item = items.get(position);
        viewHolder.tv_name.setText(item.getUserName());
        viewHolder.tv_phone.setText(item.getAccount());
        viewHolder.tv_duty.setText(item.getDuty()==3?"员工":"主管");
        return convertView;
	}
	class ViewHolder {
        TextView tv_name,tv_phone,tv_duty;
	}
	

}
