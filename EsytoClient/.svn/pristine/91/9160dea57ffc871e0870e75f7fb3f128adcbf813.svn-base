package com.ec2.yspay.adapter;
  

import java.util.ArrayList;

import com.ec2.yspay.DeviceConnectActivity.SiriListItem;
import com.ec2.yspay.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeviceListAdapter extends BaseAdapter {
    private ArrayList<SiriListItem> list;
    private LayoutInflater mInflater;
  
    public DeviceListAdapter(Context context, ArrayList<SiriListItem> list2) {
    	list = list2;
		mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder viewHolder = null;
    	SiriListItem  item=list.get(position);
        if(convertView == null){
        	convertView = mInflater.inflate(R.layout.item_listview_device, null);          
        	viewHolder=new ViewHolder(
        			(TextView) convertView.findViewById(R.id.tv_deviceid)
        	       );
        	convertView.setTag(viewHolder);
        }
        else{
        	viewHolder = (ViewHolder)convertView.getTag();
        }       
        
        viewHolder.msg.setText(item.message);    
        
        return convertView;
    }
    
    class ViewHolder {
          protected TextView msg;
  
          public ViewHolder( TextView msg){
              this.msg = msg;
              
          }
    }
}
