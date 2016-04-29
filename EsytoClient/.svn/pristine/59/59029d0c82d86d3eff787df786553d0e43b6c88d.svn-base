package com.ec2.yspay;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.activity.MainActivity;
import com.ec2.yspay.adapter.StoreGridViewAdapter;
import com.ec2.yspay.common.StaffManager;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.response.GetShopListResponse;
import com.ec2.yspay.http.task.AddStoreTask;
import com.ec2.yspay.http.task.GetShopListTask;
import com.ec2.yspay.widget.MyGridView;
import com.ec2.yspay.widget.PopupDialog;
import com.ec2.yspay.widget.PopupStoreAddDialog;

public class StoreListAddActivity extends BaseActivity {
	private GridView mGridView;
	private StoreGridViewAdapter mAdapter;
	private List<ShopItem> shopList ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_list_add);
		mGridView = (GridView) findViewById(R.id.store_gridview);
		shopList = (List<ShopItem>) getIntent().getSerializableExtra("shopList");
		shopList.add(new ShopItem("", ""));
		mAdapter = new StoreGridViewAdapter(mContext, shopList);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==shopList.size()-1){
					addStoreDialog();
				}else{
					ShopItem item = shopList.get(position);
					Intent addIntent = new Intent(mContext, PersonalAddActivity.class);
					addIntent.putExtra("shopCode", item.getShopCode());
					addIntent.putExtra("shopName", item.getShopName());
					startActivity(addIntent);
				}
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StaffManager.getInstance(mContext).clearData();
	}
	private void addStoreDialog() {

		final PopupStoreAddDialog.Builder builder = new PopupStoreAddDialog.Builder(mContext);
		//builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
		builder.setTitle("添加门店");
		builder.setEditEnabled(true);
		builder.setMessage("");
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String shopName = builder.getRemarkText();//这里取得备注信息并作处理
				if(Toolkits.isStrEmpty(shopName)){
					showToast("请输入门店名称");
				}else{
					addStore(shopName);
				}
				dialog.dismiss();
			}
		});

		builder.create().show();

	}
	private void addStore(String shopName){
        AddStoreTask task = new AddStoreTask(mContext);
        task.setAddress("");
        task.setCity("");
        task.setProvince("");
        task.setPhone("");
        task.setShopName(shopName);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                showToast("门店添加成功！");
                getShopList();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                BooleanResponse response = (BooleanResponse)obj;
                showToastLong(response.getResultDesc());
            }
        });
        task.execute();
    }
	private void getShopList(){
        GetShopListTask task = new GetShopListTask(mContext);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
            	shopList.clear();
                GetShopListResponse response = (GetShopListResponse)obj;
                shopList.addAll(response.getShopList());
                shopList.add(new ShopItem("", ""));
                mAdapter.notifyDataSetChanged();
            }
            
            @Override
            public void onFail(Object obj)
            {
                GetShopListResponse response = (GetShopListResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
}
