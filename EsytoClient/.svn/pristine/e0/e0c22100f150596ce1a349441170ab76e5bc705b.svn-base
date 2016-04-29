package com.ec2.yspay.adapter;
import java.util.ArrayList; 
import java.util.List; 

import com.ec2.yspay.R;

   
   
import com.ec2.yspay.adapter.CityListAdapter.ViewHolder;
import com.ec2.yspay.entity.ProvinceItem;
import com.ec2.yspay.http.cash.ShopItem;

import android.content.Context; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.view.View.OnClickListener;
import android.widget.BaseAdapter; 
import android.widget.ImageView; 
import android.widget.LinearLayout;
import android.widget.TextView; 
   
/**
 * 为更多中listview提供数据
 * @author Cloay
 * 2012-3-5
 * 下午06:28:14
 */ 
public class PopupShopListAdapter extends BaseAdapter{ 
   
    private Context context; 
	private  List<ShopItem> shoplist ;// 为条目提供数据
	
    public PopupShopListAdapter(Context context,List<ShopItem> shoplist){ 
        this.context = context; 
        this.shoplist = shoplist;
    }
    @Override 
    public int getCount() { 
        return shoplist.size();  //条目数量 
    } 
   
    @Override 
    public Object getItem(int position) { 
        return shoplist.get(position); 
    } 
   
    @Override 
    public long getItemId(int position) { 
        return position; 
    } 
   
    @Override 
    public View getView(int position, View convertView, ViewGroup parent) { 
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.popup_shoplist_item, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_shopname);
            viewHolder.ll_parent = (LinearLayout)convertView.findViewById(R.id.ll_parent);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
        ShopItem item = shoplist.get(position);
        viewHolder.tv_name.setText(item.getShopName());    
       
        return convertView; 
    } 
    class ViewHolder {
        TextView tv_name;
        LinearLayout ll_parent;
    }
   
}