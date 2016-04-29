package com.ec2.yspay.adapter;

import java.util.ArrayList;
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
import com.ec2.yspay.entity.ReportEntity;
import com.ec2.yspay.widget.RoundProgressBar;

public class ReportGridViewAdapter extends BaseAdapter {
	private List<ReportEntity> reportList;
	private int[] colors = {R.color.circle_one,R.color.circle_two,R.color.circle_three,R.color.circle_four
			,R.color.circle_five,R.color.circle_six};
	Context context;
	public ReportGridViewAdapter(Context context, List<ReportEntity> reportList) {
		this.context = context;
		this.reportList = reportList;
	}

	@Override
	public int getCount() {
		return reportList.size();
	}

	@Override
	public Object getItem(int position) {
		return reportList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    ViewHolder viewHolder = null;
	    if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_report, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_paynum);
            viewHolder.roundProgressBar = (RoundProgressBar)convertView.findViewById(R.id.roundProgressBar);
            
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    ReportEntity entity = reportList.get(position);
        viewHolder.tv_name.setText(entity.getPayCount()+"笔");    
        float progress = entity.getPercent()/100.0f*RoundProgressBar.defaultMax;
        
        viewHolder.roundProgressBar.setPayName(entity.getPayName());
        viewHolder.roundProgressBar.setRoundProgressColor(context.getResources().getColor(colors[position]));
        viewHolder.roundProgressBar.startAutoProgress(progress);
        return convertView;
	}
	class ViewHolder {
        RoundProgressBar roundProgressBar;
        TextView tv_name;
	}
	
    

}
