package com.ec2.yspay.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ec2.yspay.R;
import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.adapter.CityListAdapter;
import com.ec2.yspay.adapter.ShopListAdapter;
import com.ec2.yspay.common.City;
import com.ec2.yspay.common.CityManager;
import com.ec2.yspay.common.Province;
import com.ec2.yspay.common.ShopManager;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.response.GetShopListResponse;
import com.ec2.yspay.http.task.GetShopListTask;


public class ShopListViewActivity extends BaseActivity
{
	private ListView shopListView;
    private List<ShopItem> mShopList;
    private ShopManager mShopManager;
    private boolean isShowAll;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        shopListView = (ListView)findViewById(R.id.lv_shoplist);
        mShopManager = ShopManager.getInstance(mContext);
        isShowAll = getIntent().getBooleanExtra("isShowAll", true);
        if(mShopManager.isShopListEmpty()){
        	getShopList();
        }else{
        	initView();
        }
    }
    private void initView(){
    	mShopList = new ArrayList<ShopItem>();
    	if(isShowAll)
    		mShopList.add(new ShopItem("全部", ""));
        mShopList.addAll(mShopManager.getShoplist());
        ShopListAdapter adapter = new ShopListAdapter(mContext, mShopList);
        shopListView.setAdapter(adapter);
        shopListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShopItem mItem = mShopList.get(position);
				mShopManager.setCurrentShop(mItem);
				Intent data = new Intent();
				data.putExtra("index", position);
				setResult(100, data);
				finish();
			}
		});
    }
    /**
     * 重载方法
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
    private void getShopList(){
        GetShopListTask task = new GetShopListTask(mContext);
        task.setProgressVisiable(false);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                GetShopListResponse response = (GetShopListResponse)obj;
                mShopManager.setShoplist(response.getShopList());
                initView();
            }

            @Override
            public void onFail(Object obj)
            {
            	GetShopListResponse response = (GetShopListResponse)obj;
                ToastUtils.show(mContext, response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
}
