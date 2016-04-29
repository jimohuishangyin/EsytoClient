package com.ec2.yspay.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ec2.yspay.PersonalAddActivity;
import com.ec2.yspay.R;
import com.ec2.yspay.StoreListAddActivity;
import com.ec2.yspay.adapter.PersonalListAdapter;
import com.ec2.yspay.adapter.StoreGridViewAdapter;
import com.ec2.yspay.common.Constants;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.ShopManager;
import com.ec2.yspay.common.StaffManager;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;
import com.ec2.yspay.http.response.GetShopListResponse;
import com.ec2.yspay.http.response.GetStaffListResponse;
import com.ec2.yspay.http.task.GetShopListTask;
import com.ec2.yspay.http.task.GetStaffListTask;

public class PersonalManagerActivity extends BaseActivity implements OnClickListener{
	
	private ImageView mBackImageView,mAddPersonalImageView,mSearchPersonImageView;
	private TextView mShopPersonalTextView;
	private RelativeLayout mShopPersonalRelative;
	private ListView mPersonalListView;
	private List<StaffItem> mStaffList = new ArrayList<StaffItem>();
	private String shopCode = "";
	private String shopName = "";
	private PersonalListAdapter mAdapter;
	private List<ShopItem> mShopList = new ArrayList<ShopItem>() ;
	private ShopManager shopManager = ShopManager.getInstance(mContext);
	public static boolean isNeedUpdate = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personalmanager);
		String phone = getIntent().getStringExtra("phoneNum");
        mShopPersonalTextView = (TextView) findViewById(R.id.tv_shopName);
        shopCode = getIntent().getStringExtra("shopCode");
        shopName = getIntent().getStringExtra("shopName");
        if(shopCode!=null){
        	mShopPersonalTextView.setText(shopName);
        }
		initview();
	}
	
	
	
	private void initview(){
		mBackImageView = (ImageView) findViewById(R.id.back);
		mAddPersonalImageView = (ImageView) findViewById(R.id.add_personal);
		mSearchPersonImageView = (ImageView) findViewById(R.id.search_personal);
		mPersonalListView = (ListView) findViewById(R.id.lv_personallist);
		mShopPersonalRelative = (RelativeLayout) findViewById(R.id.rl_shop);
		mBackImageView.setOnClickListener(this);
		mAddPersonalImageView.setOnClickListener(this);
		mSearchPersonImageView.setOnClickListener(this);
		mShopPersonalRelative.setOnClickListener(this);
		mAdapter = new PersonalListAdapter(mContext, mStaffList,mShopList);
		mPersonalListView.setAdapter(mAdapter);
		mPersonalListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				StaffItem item = mStaffList.get(position);
                Intent intent = new Intent(mContext, PersonalUpdateActivity.class);
                intent.putExtra("staff", item);
                intent.putExtra("shopCode", shopCode);
                intent.putExtra("shopList", (Serializable)mShopList); 
                mContext.startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getShopList();
		StaffManager.getInstance(mContext).clearData();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		shopManager.clearData();
	}
	
	private void getStaffList(){
        GetStaffListTask task = new GetStaffListTask(mContext);
        task.setProgressVisiable(true);
        task.setShopCode(shopCode);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                GetStaffListResponse response = (GetStaffListResponse)obj;
                mStaffList.clear();
                List<StaffItem> tempList = response.getStaffList();
                for(int i=0;i<tempList.size();i++){
                    if(MyApplication.mDataCache.duty>=tempList.get(i).getDuty()){
                        tempList.remove(i);
                        i--;
                    }
                }
                mStaffList.addAll(tempList);
                mAdapter.notifyDataSetChanged();
            }
            
            @Override
            public void onFail(Object obj)
            {
                GetStaffListResponse response = (GetStaffListResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
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
                shopManager.setShoplist(response.getShopList());
                getStaffList();
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
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
    }
	
	
	public void showCityPicker() {
		Intent intent = new Intent(mContext, ShopListViewActivity.class);
		startActivityForResult(intent, 101);
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.add_personal:
			if(MyApplication.mDataCache.duty==Constants.DUTY_SHOP){
				Intent addIntent = new Intent(mContext, PersonalAddActivity.class);
				addIntent.putExtra("shopCode", mShopList.get(0).getShopCode());
				addIntent.putExtra("shopName", mShopList.get(0).getShopName());
				startActivity(addIntent);
			}else{
				if(Toolkits.isStrEmpty(shopCode)){//选择门店
					Intent storeIntent = new Intent(mContext, StoreListAddActivity.class);
					storeIntent.putExtra("shopList", (Serializable)mShopList);
					startActivity(storeIntent);
				}else{
					Intent addIntent = new Intent(mContext, PersonalAddActivity.class);
					addIntent.putExtra("shopCode", shopCode);
					addIntent.putExtra("shopName", shopName);
					startActivity(addIntent);
				}
			}
			
			break;
		case R.id.search_personal:
			Intent searchIntent = new Intent(mContext, PersonalSearchActivity.class);
			searchIntent.putExtra("shopList", (Serializable)mShopList); 
			startActivity(searchIntent);
			break;
		case R.id.rl_shop:
			showCityPicker();
			break;

		default:
			break;
		}
		
	}
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==101&&resultCode==100){
    		mShopPersonalTextView.setText(ShopManager.getInstance(mContext).getCurrentShopName());
    		shopName = ShopManager.getInstance(mContext).getCurrentShopName();
    		shopCode = ShopManager.getInstance(mContext).getCurrentShopCode();
            getStaffList();
    	}
    }
}
