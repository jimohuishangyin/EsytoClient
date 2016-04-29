package com.ec2.yspay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.activity.OpenServerActivity;
import com.ec2.yspay.adapter.ServerOpenAdapter;
import com.ec2.yspay.adapter.StoreListAdapter;
import com.ec2.yspay.common.AsyncImageLoader;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.entity.ChannelTypes;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.response.GetPayChanneResponse;
import com.ec2.yspay.http.response.GetShopListResponse;
import com.ec2.yspay.http.task.GetPayChannelTask;
import com.ec2.yspay.http.task.GetShopListTask;
import com.ec2.yspay.widget.ButtonRedCenter;
import com.ec2.yspay.widget.ButtonWhiteCenter;
import com.ec2.yspay.widget.RoundImageViewNoBorder;

public class BusinessManagementActivity extends BaseActivity
{
    private ListView lvStore;
    private StoreListAdapter mAapter;
    private RelativeLayout btnAddStore;
    private RelativeLayout btnAddStaff;
    private List<ShopItem> mShopList = new ArrayList<ShopItem>();
    private LinearLayout llCompany;
    private String companyLogoImage,companyLogoImageUrl;
    private RoundImageViewNoBorder ivLogo;
    private AsyncImageLoader asyImg = new AsyncImageLoader(this);
    private TextView tvCompanyName,tvCompanyCode;
    private GridView mServerGridView;
    private ServerOpenAdapter mServerAdapter;
    private List<ChannelTypes> mChannelTypes = new ArrayList<ChannelTypes>();
    private TextView mOpenServerTextView;
    private TextView mPlayNumPhoneTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_management);
        initView();
    }
    /**
     * 重载方法
     */
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        tvCompanyName.setText(MyApplication.mDataCache.companyName);
        tvCompanyCode.setText("企业号:"+MyApplication.mDataCache.companyCode);
        getShopList();
        
    }
    private void updateView(){
        mAapter.notifyDataSetChanged();
        asyImg.LoadImage(companyLogoImageUrl,companyLogoImage
            , ivLogo,getResources().getDrawable(R.drawable.company_default_logo));
        
    }
  //支付方式请求
  	private void getChannelTypeObj(){
      	GetPayChannelTask task = new GetPayChannelTask(mContext);
  		task.setProgressVisiable(true);
  		task.setOnTaskFinished(new OnTaskFinished() {
  			
  			@Override
  			public void onSucc(Object obj) {
  				GetPayChanneResponse response =(GetPayChanneResponse) obj;
  				mChannelTypes = response.getChannelTypes();
  				mServerAdapter = new ServerOpenAdapter(mContext,mChannelTypes);
  		        mServerGridView.setAdapter(mServerAdapter);
  			}
  			
  			@Override
  			public void onFail(Object obj) {
  				
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
                // TODO Auto-generated method stub
                GetShopListResponse response = (GetShopListResponse)obj;
                mShopList.clear();
                mShopList.addAll(response.getShopList());
                companyLogoImageUrl = response.getCompanyLogoImageUrl();
                companyLogoImage = response.getCompanyLogoImage();
                updateView();
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
    private void initView(){
        lvStore = (ListView)findViewById(R.id.lv_storelist);
        ivLogo = (RoundImageViewNoBorder)findViewById(R.id.iv_logo);
        mAapter = new StoreListAdapter(mContext, mShopList);
        lvStore.setAdapter(mAapter);
        btnAddStore = (RelativeLayout)findViewById(R.id.btn_add_store);
        tvCompanyName = (TextView)findViewById(R.id.tv_company_name);
        tvCompanyCode = (TextView)findViewById(R.id.tv_company_code);
        mServerGridView = (GridView) findViewById(R.id.gv_server_open);
        getChannelTypeObj();
        
        btnAddStore.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent addIntent = new Intent(mContext, StoreAddActivity.class);
                startActivity(addIntent);
            }
        });
        btnAddStaff = (RelativeLayout)findViewById(R.id.btn_add_staff); 
        btnAddStaff.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent addIntent = new Intent(mContext, PersonalAddActivity.class);
                addIntent. putExtra("shopList", (Serializable)mShopList);  
                startActivity(addIntent);
            }
        });
        lvStore.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // TODO Auto-generated method stub
                Intent addIntent = new Intent(mContext, StoreManagementActivity.class);
                addIntent. putExtra("shopItem", (Serializable)mShopList.get(position));  
                addIntent.putExtra("shopList", (Serializable)mShopList); 
                startActivity(addIntent);
            }
        });
        llCompany = (LinearLayout)findViewById(R.id.ll_company);
        llCompany.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent addIntent = new Intent(mContext, CompanyMainActivity.class);
                startActivity(addIntent);
            }
        });
        mOpenServerTextView = (TextView)findViewById(R.id.open_server);
        mOpenServerTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,OpenServerActivity.class);
				startActivity(intent);
				
			}
		});
        mPlayNumPhoneTextView =(TextView) findViewById(R.id.phone_num);
        mPlayNumPhoneTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "400-6602036"));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
    }
}
