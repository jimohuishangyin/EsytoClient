package com.ec2.yspay.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.Province;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.ShopManagerItem;

public class ManagerListAdapter extends BaseAdapter {
	List<ShopManagerItem> mManagerList;
	Context context;
	public ManagerListAdapter(Context context, List<ShopManagerItem> mManagerList) {
		this.context = context;
		this.mManagerList = mManagerList;
	}

	@Override
	public int getCount() {
		return mManagerList.size();
	}

	@Override
	public Object getItem(int position) {
		return mManagerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    ViewHolder viewHolder = null;
	    if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_manager, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.ll_parent = (LinearLayout)convertView.findViewById(R.id.ll_parent);
            viewHolder.tv_manager = (TextView)convertView.findViewById(R.id.tv_manager);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    ShopManagerItem mItem = mManagerList.get(position);
        viewHolder.tv_name.setText( "主管 ");    
        viewHolder.tv_manager.setText( mItem.getUserName()+","+mItem.getAccount());   
        return convertView;
	}
	class ViewHolder {
        TextView tv_name,tv_manager;
        LinearLayout ll_parent;
	}
}
