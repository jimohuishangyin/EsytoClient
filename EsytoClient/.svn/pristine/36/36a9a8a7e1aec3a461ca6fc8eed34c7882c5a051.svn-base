package com.ec2.yspay.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.entity.PrintOrderEntity;

public class PrintListAdapter extends BaseAdapter {
	List<PrintOrderEntity> items;
	Context context;

	public PrintListAdapter(Context context, List<PrintOrderEntity> items) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder viewHolder = null;
	    if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_print, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            viewHolder.tv_orderId = (TextView)convertView.findViewById(R.id.tv_orderId);
            viewHolder.tv_pznum = (TextView)convertView.findViewById(R.id.tv_pznum);
            viewHolder.tv_money = (TextView)convertView.findViewById(R.id.tv_money);
            viewHolder.tv_remark = (TextView)convertView.findViewById(R.id.tv_remark);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    PrintOrderEntity item = items.get(position);
        viewHolder.tv_time.setText(item.getOrderTime());    
        viewHolder.tv_orderId.setText("交易流水号："+item.getOrderId()); 
        viewHolder.tv_pznum.setText("支付方式："+PayTypeEntity.getPayName(item.getChannelType()));  
        viewHolder.tv_money.setText("金额："+item.getAmount());  
        viewHolder.tv_remark.setText("备注："+item.getRemark());  
        
        return convertView;
	}
	class ViewHolder {
        TextView tv_time,tv_orderId,tv_pznum,tv_money,tv_remark;
	}

}
