package com.ec2.yspay.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.activity.CashCollectActivity;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.cash.CashCollectItem;

public class CashCollectListAdapter extends BaseAdapter {
	List<CashCollectItem> items;
	Context context;
	public CashCollectListAdapter(Context context, List<CashCollectItem> items) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_cashcollect, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_state_guiji = (TextView)convertView.findViewById(R.id.tv_state_guiji);
            viewHolder.tv_guiji_no = (TextView)convertView.findViewById(R.id.tv_guiji_no);
            viewHolder.tv_guiji_ed = (TextView)convertView.findViewById(R.id.tv_guiji_ed);
            viewHolder.tv_type = (TextView)convertView.findViewById(R.id.tv_type);
            viewHolder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            viewHolder.btn_state_guiji = (Button)convertView.findViewById(R.id.btn_state_guiji);
            viewHolder.ll_parent = (LinearLayout)convertView.findViewById(R.id.ll_parent);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, Toolkits.dip2px(context, 45));
	    viewHolder.ll_parent.setLayoutParams(params);
	    final CashCollectItem item = items.get(position);
        viewHolder.tv_time.setText(item.getClearDate()); 
        viewHolder.tv_guiji_ed.setText(Toolkits.fen2Yuan(item.getAlreadyClear())); 
        viewHolder.tv_guiji_no.setText(Toolkits.fen2Yuan(item.getNotClear()));
        viewHolder.btn_state_guiji.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if(item.getNotClear()==0){
                    ToastUtils.show(context, "归集金额必须大于0");
                    return;
                }
                Intent intent = new Intent(context, CashCollectActivity.class);
                intent.putExtra("money", Toolkits.fen2Yuan(item.getNotClear()));
                intent.putExtra("transDate", item.getClearDate());
                context.startActivity(intent);
            }
        });
        if(1==item.getStatus()){//已归集，风格置灰
            viewHolder.tv_state_guiji.setVisibility(View.VISIBLE);
            viewHolder.btn_state_guiji.setVisibility(View.GONE);
            viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.text_gray));
            viewHolder.tv_type.setTextColor(context.getResources().getColor(R.color.text_gray));
            viewHolder.tv_guiji_ed.setTextColor(context.getResources().getColor(R.color.text_gray));
            viewHolder.tv_guiji_no.setTextColor(context.getResources().getColor(R.color.text_gray));
        }else{//未归集
            viewHolder.tv_state_guiji.setVisibility(View.GONE);
            viewHolder.btn_state_guiji.setVisibility(View.VISIBLE);
            viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.text_black));
            viewHolder.tv_type.setTextColor(context.getResources().getColor(R.color.text_black));
            viewHolder.tv_guiji_ed.setTextColor(context.getResources().getColor(R.color.text_black));
            viewHolder.tv_guiji_no.setTextColor(context.getResources().getColor(R.color.text_black));
        }
        return convertView;
	}
	class ViewHolder {
	    Button btn_state_guiji;
        TextView tv_state_guiji,tv_guiji_no,tv_guiji_ed,tv_type,tv_time;
        LinearLayout ll_parent;
	}

}
