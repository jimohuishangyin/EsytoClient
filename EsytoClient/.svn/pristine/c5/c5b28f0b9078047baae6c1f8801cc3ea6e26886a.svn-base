package com.ec2.yspay.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.AsyncImageListLoader;
import com.ec2.yspay.common.AsyncImageLoader;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.ShopManagerItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class StoreListAdapter extends BaseAdapter {
	List<ShopItem> items;
	Context context;
	private int parentPositon;
	private AsyncImageListLoader imageLoader;
//	public ImageLoader mImageLoader = ImageLoader.getInstance();
//	private DisplayImageOptions mOptions;
//	private AsyncImageLoader asyImg ;
	public StoreListAdapter(Context context, List<ShopItem> items) {
		this.context = context;
		this.items = items;
		imageLoader = new AsyncImageListLoader(context);
//		asyImg = new AsyncImageLoader(context);
//		mOptions = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.company_default_logo)
//		.showImageForEmptyUri(R.drawable.company_default_logo)
//		.showImageOnFail(R.drawable.company_default_logo).cacheInMemory(true)
//		.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_store, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_manager = (TextView)convertView.findViewById(R.id.tv_managers);
            viewHolder.headcount = (TextView) convertView.findViewById(R.id.head_count);
            viewHolder.shoplogoImageView = (ImageView) convertView.findViewById(R.id.iv_store_icon);
            viewHolder.ll_parent = (LinearLayout)convertView.findViewById(R.id.ll_parent);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    ShopItem item = items.get(position);
        viewHolder.tv_name.setText(item.getShopName());
        viewHolder.tv_manager.setText(getManagerNames(item.getManagerList()));
        viewHolder.headcount.setText(item.getHeadcount()+"人");
//        loadImage(item.getShoplogoImageurl(),viewHolder.shoplogoImageView,mOptions);
//        asyImg.LoadImage(item.getShoplogoImageurl(),item.getLogoImage(), viewHolder.shoplogoImageView,context.getResources().getDrawable(R.drawable.company_default_logo));
        imageLoader.loadImage(viewHolder.shoplogoImageView, item.getShoplogoImageurl(), item.getLogoImage()
        		, context.getResources().getDrawable(R.drawable.company_default_logo));
        return convertView;
	}
	class ViewHolder {
        TextView tv_name,headcount,tv_manager;
        ImageView shoplogoImageView;
        LinearLayout ll_parent;
	}
	
//	public void loadImage(String uri, ImageView iv, DisplayImageOptions options) {
//		if (options == null) {
//			options = mOptions;
//		}
//		mImageLoader.displayImage(uri, iv, options);
//		
//	}
	private String getManagerNames(List<ShopManagerItem> list){
		String names = "门店暂无主管";
		if(list.size()>0){
			names = "主管：";
			for(int i=0;i<list.size();i++){
				names = names + list.get(i).getUserName()+",";
			}
			names = names.substring(0, names.length()-1);
		}
		
		return names;
	}
	

}
