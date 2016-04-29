package com.ec2.yspay.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ec2.yspay.R;
import com.ec2.yspay.StoreAddActivity;
import com.ec2.yspay.adapter.StoreListAdapter;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.response.GetShopListResponse;
import com.ec2.yspay.http.task.GetShopListTask;
import com.ec2.yspay.widget.MyTitle;

public class StoreManagerActivity extends BaseActivity {
	
	private ListView lvStore;
	private StoreListAdapter mAapter;
	private List<ShopItem> mShopList = new ArrayList<ShopItem>();
	private MyTitle mTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_storemanager);
		String phone = getIntent().getStringExtra("phoneNum");
		initview();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getShopList();
	}
	
	private void initview(){
		lvStore = (ListView)findViewById(R.id.lv_storelist);
		mAapter = new StoreListAdapter(mContext, mShopList);
		lvStore.setAdapter(mAapter);
		getShopList();
		lvStore.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent addIntent = new Intent(mContext, StoreDetailActivity.class);
                addIntent. putExtra("shopItem", (Serializable)mShopList.get(position));  
                startActivity(addIntent);
			}
		});
		mTitle = (MyTitle) findViewById(R.id.rl_top);
		mTitle.setRightOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, StoreAddActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void getShopList(){
		
        GetShopListTask task = new GetShopListTask(mContext);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {	
            	mShopList.clear();
                GetShopListResponse response = (GetShopListResponse)obj;
                mShopList.addAll(response.getShopList());
                
                mAapter.notifyDataSetChanged();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                GetShopListResponse response = (GetShopListResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }

}
