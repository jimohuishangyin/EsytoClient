package com.ec2.yspay.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PersonalListAdapter extends BaseAdapter {
	List<StaffItem> items;
	Context mContext;
	List<ShopItem> mShopList;
	public ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mOptions;
	public PersonalListAdapter(Context context,List<StaffItem> items,List<ShopItem> shoplist) {
		this.items = items;
		mContext = context;
		mShopList = shoplist;
		mOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.head_portrait)
		.showImageForEmptyUri(R.drawable.head_portrait)
		.showImageOnFail(R.drawable.head_portrait).cacheInMemory(true)
		.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.personal_list_item, null);          
            viewHolder=new ViewHolder();
            viewHolder.personal_name = (TextView)convertView.findViewById(R.id.personal_name);
            viewHolder.personal_num = (TextView)convertView.findViewById(R.id.personal_num);
            viewHolder.personal_shop = (TextView)convertView.findViewById(R.id.personal_shop);
            viewHolder.iv_staff = (ImageView)convertView.findViewById(R.id.iv_staff_icon);
            viewHolder.personal_duty = (TextView)convertView.findViewById(R.id.tv_duty);
            viewHolder.iv_state = (ImageView)convertView.findViewById(R.id.iv_state);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    StaffItem item = items.get(position);
	    viewHolder.personal_num.setText(item.getAccount());
	    for(int i = 0 ;i<mShopList.size();i++){
	    	if(item.getShopCode().equals(mShopList.get(i).getShopCode())){
	    		viewHolder.personal_shop.setText("所属门店："+mShopList.get(i).getShopName());
	    	}
	    }
	    viewHolder.personal_name.setText(item.getUserName());
	    if(item.getDuty()==2){
	    	viewHolder.personal_duty.setText(" (主管)");
	    }else{
	    	viewHolder.personal_duty.setText(" (员工)");
	    }
	    if(item.getStatus().equals("1")){
	    	viewHolder.iv_state.setImageResource(0);
	    }else if(item.getStatus().equals("2")){
	    	viewHolder.iv_state.setImageResource(R.drawable.lock);
	    }else if(item.getStatus().equals("3")){
	    	viewHolder.iv_state.setImageResource(R.drawable.stop);
	    }else if(item.getStatus().equals("4")){
	    	viewHolder.iv_state.setImageResource(R.drawable.stop);
	    }
	    mImageLoader.displayImage(item.getUserImage(),viewHolder.iv_staff,mOptions);
		return convertView;
	}
	
	class ViewHolder {
        TextView personal_name,personal_num,personal_shop,personal_duty;
        LinearLayout ll_parent;
        ImageView iv_staff,iv_state;
	}

}
