package com.ec2.yspay.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.Province;

public class CityListAdapter extends BaseAdapter {
	ArrayList<Province> mProvinceList;
	Context context;
	private int parentPositon;
	public CityListAdapter(Context context, ArrayList<Province> provinceList) {
		this.context = context;
		this.mProvinceList = provinceList;
	}

	@Override
	public int getCount() {
		return mProvinceList.size();
	}

	@Override
	public Object getItem(int position) {
		return mProvinceList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    ViewHolder viewHolder = null;
	    if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_city, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.ll_parent = (LinearLayout)convertView.findViewById(R.id.ll_parent);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    Province mProvinc = mProvinceList.get(position);
        viewHolder.tv_name.setText(mProvinc.getName());    
        return convertView;
	}
	class ViewHolder {
        TextView tv_name;
        LinearLayout ll_parent;
	}
	
    

}
