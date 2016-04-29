package com.ec2.yspay.adapter;
import java.util.ArrayList; 
import java.util.List; 

import com.ec2.yspay.R;

   
   
import android.content.Context; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.BaseAdapter; 
import android.widget.ImageView; 
import android.widget.TextView; 
   
/**
 * 为更多中listview提供数据
 * @author Cloay
 * 2012-3-5
 * 下午06:28:14
 */ 
public class PopupMenuAdapter extends BaseAdapter{ 
   
    private LayoutInflater inflater; 
    private Context context; 
	public static final List<String> more_list = new ArrayList<String>();// 为条目提供数据
	int[] itemIcon = { 
//			R.drawable.shoukuan_icon,
			R.drawable.revoke_icon,
			R.drawable.chexiao_icon,
			R.drawable.dayin_icon,
//			R.drawable.xianjinguiji_icon,
			R.drawable.search_icon };
	String[] itemString = { 
//			"收款",
			"撤销",
			"退款",
			"打印",
//			"现金归集",
			"查询"};
	
    public PopupMenuAdapter(Context context){ 
        this.context = context; 
        inflater = LayoutInflater.from(this.context); 
       if(more_list.size()==0){//防止重复加载
        for(int i=0;i<itemString.length;i++){
        more_list.add(itemString[i]); 
       
        }
       }
    } 
    @Override 
    public int getCount() { 
        return more_list.size();  //条目数量 
    } 
   
    @Override 
    public Object getItem(int position) { 
        return null; 
    } 
   
    @Override 
    public long getItemId(int position) { 
        return 0; 
    } 
   
    @Override 
    public View getView(int position, View convertView, ViewGroup parent) { 
        if (convertView == null) {   
            convertView = inflater.inflate(   
                    R.layout.popup_menu_item, null);   
        }  
        
        ImageView icon = (ImageView) convertView.findViewById(R.id.item_icon);

       if(position<itemIcon.length)
        	 icon.setBackgroundResource(itemIcon[position]); 
       else
      	 icon.setBackgroundResource(R.drawable.xianechaxun_icon); 
       
        TextView text = (TextView) convertView.findViewById(R.id.more_item_text); 
        text.setText(more_list.get(position)); 
        return convertView; 
    } 
   
}