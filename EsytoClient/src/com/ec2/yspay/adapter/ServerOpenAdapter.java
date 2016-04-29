package com.ec2.yspay.adapter;

import java.util.List;

import com.ec2.yspay.R;
import com.ec2.yspay.adapter.PrintListAdapter.ViewHolder;
import com.ec2.yspay.entity.ChannelTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServerOpenAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<ChannelTypes> mChannelTypes;
	
	public ServerOpenAdapter(Context mContext,List<ChannelTypes>channelTypes) {
		super();
		this.mContext = mContext;
		this.mChannelTypes = channelTypes;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mChannelTypes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mChannelTypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_server_open, null);          
			holder=new ViewHolder();
			holder.mNamePayTextView = (TextView) convertView.findViewById(R.id.tv_server_open);
			holder.mServerOpenImageView = (ImageView) convertView.findViewById(R.id.iv_server_open);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		ChannelTypes item = mChannelTypes.get(position);
		
		holder.mNamePayTextView.setText(item.getName());
		if(item.getIsOpen().equals("1")){
			holder.mServerOpenImageView.setBackgroundResource(R.drawable.server_true);
		}else{
			holder.mServerOpenImageView.setBackgroundResource(R.drawable.server_false);
		}
		
		return convertView;
	}
	
	class ViewHolder{
		TextView mNamePayTextView;
		ImageView mServerOpenImageView;
	}

}
