package com.ec2.yspay.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.task.UpdateStaffStatusTask;

public class StaffListAdapter extends BaseAdapter {
	List<StaffItem> items;
	Context mContext;
	private PopupWindow popMenu;
    private View layoutPopMenu;
    private ListView PopMenuList;
    private PopupShopListAdapter shopAdapter;
    private List<ShopItem> mShopList = new ArrayList<ShopItem>();
    private View parentView;
	public StaffListAdapter(Context context, List<StaffItem> items,View parentView) {
		this.mContext = context;
		this.items = items;
		mShopList.add(new ShopItem("正常", "1"));
        mShopList.add(new ShopItem("锁定", "1"));
        mShopList.add(new ShopItem("停用", "1"));
	    this.parentView = parentView;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.staff_expandable_list_child, null);          
            viewHolder=new ViewHolder();
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_account = (TextView)convertView.findViewById(R.id.tv_account);
            viewHolder.tv_state = (TextView)convertView.findViewById(R.id.tv_state);
            viewHolder.ll_parent = (LinearLayout)convertView.findViewById(R.id.ll_parent);
            viewHolder.iv_staff = (ImageView)convertView.findViewById(R.id.iv_staff);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        } 
	    StaffItem item = items.get(position);
        viewHolder.tv_name.setText(item.getUserName());
        viewHolder.tv_account.setText(item.getAccount());
        viewHolder.tv_state.setText(item.getStatusName());
        int state = Integer.valueOf(item.getDuty());
        if(state<=2){
            viewHolder.iv_staff.setImageResource(R.drawable.personnel2_icon_48);
        }else{
            viewHolder.iv_staff.setImageResource(R.drawable.personnel_icon_48);
	    }
        viewHolder.tv_state.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickPopupMenu(v,position);
            }
        });
        return convertView;
	}
	class ViewHolder {
        TextView tv_name,tv_account,tv_state;
        LinearLayout ll_parent;
        ImageView iv_staff;
	}
	private void clickPopupMenu(View v,final int childPosition){

        if (popMenu != null && popMenu.isShowing()) {
            popMenu.dismiss();
        } else {
            
            // 初始化数据项
            layoutPopMenu = ((Activity) mContext).getLayoutInflater().inflate(
                    R.layout.pop_shoplist, null);
            PopMenuList = (ListView) layoutPopMenu
                    .findViewById(R.id.menulist);
            shopAdapter = new PopupShopListAdapter(mContext, mShopList);
            PopMenuList.setAdapter(shopAdapter);
            popMenu = new PopupWindow(layoutPopMenu, Toolkits.dip2px(mContext, 76),
                Toolkits.dip2px(mContext, 135));
            
            // 点击listview中item的处理
            PopMenuList
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                View arg1, int arg2, long arg3) {
                        
                            if (popMenu != null && popMenu.isShowing()) {
                                popMenu.dismiss();
                            }
//                            Toast.makeText(mContext, "position:"+arg2, 3).show();
                            updateStatus( childPosition, (arg2+1)+"");
                        }
                    });

            ColorDrawable cd = new ColorDrawable(-0000);
            popMenu.setBackgroundDrawable(cd);
            popMenu.setAnimationStyle(R.style.PopupMenuAnimation);
            popMenu.update();
            popMenu.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popMenu.setTouchable(true); // 设置popupwindow可点击
            popMenu.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popMenu.setFocusable(true); // 获取焦点
            
            popMenu.showAsDropDown(v, 0,0);

        }
    }
	private void updateStatus(final int childPosition,final String status){
        UpdateStaffStatusTask task = new UpdateStaffStatusTask(mContext);
        task.setAccount(items.get(childPosition).getAccount());
        task.setStatus(status);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                ToastUtils.show(mContext, "修改成功");
                items.get(childPosition).setStatus(status);
                notifyDataSetChanged();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                BooleanResponse response = (BooleanResponse)obj;
                ToastUtils.showLong(mContext, response.getResultDesc());
            }
        });
        task.execute();
    }

}
