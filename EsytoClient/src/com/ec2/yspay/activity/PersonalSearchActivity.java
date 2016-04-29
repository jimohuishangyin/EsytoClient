package com.ec2.yspay.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ec2.yspay.R;
import com.ec2.yspay.adapter.PersonalListAdapter;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;
import com.ec2.yspay.http.response.SearchStaffListResponse;
import com.ec2.yspay.http.task.SearchStaffListTask;

public class PersonalSearchActivity extends BaseActivity implements OnClickListener{
	
	private ImageView mBackImageView,mSerachImageView;
	private EditText mSearchEditText;
	private ListView mSearchPersonalListView;
	private PersonalListAdapter mAdapter;
	private List<ShopItem> mShopList;
	private List<StaffItem> mStaffList = new ArrayList<StaffItem>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_search);
		mShopList = (List<ShopItem>)getIntent().getSerializableExtra("shopList");
		initview();
	}
	/**
	 * 重载方法
	 */
	@Override
	protected void onResume()
	{
	    super.onResume();
	    String keyWord = mSearchEditText.getText().toString();
	    if(!Toolkits.isStrEmpty(keyWord))
	        getPersonalSearchList(keyWord);
	}
	private void initview(){
		mSearchEditText = (EditText) findViewById(R.id.searchkey_edittext);
		mSearchEditText.setFocusable(true);
		mSearchEditText.setFocusableInTouchMode(true);
		mSearchEditText.requestFocus();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			public void run() {
				InputMethodManager inputManager = (InputMethodManager) mSearchEditText
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(mSearchEditText, 0);
			}

		}, 500);
		mBackImageView = (ImageView) findViewById(R.id.ivTitleLeft);
		mSerachImageView = (ImageView) findViewById(R.id.search_view);
		mSearchPersonalListView = (ListView) findViewById(R.id.lv_personallist);
		mAdapter = new PersonalListAdapter(mContext, mStaffList, mShopList);
		mSearchPersonalListView.setAdapter(mAdapter);
		mBackImageView.setOnClickListener(this);
		mSerachImageView.setOnClickListener(this);
		mSearchPersonalListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                StaffItem item = mStaffList.get(position);
                Intent intent = new Intent(mContext, PersonalUpdateActivity.class);
                intent.putExtra("staff", item);
                intent.putExtra("shopList", (Serializable)mShopList); 
                mContext.startActivity(intent);
            }
        });
	}
	
	private void getPersonalSearchList(String keyWord){
		SearchStaffListTask task = new SearchStaffListTask(mContext);
		task.setProgressVisiable(true);
		task.setKeyWord(keyWord);
		task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                SearchStaffListResponse response = (SearchStaffListResponse)obj;
                mStaffList.clear();
                mStaffList.addAll(response.getStaffList());
                mAdapter.notifyDataSetChanged();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                SearchStaffListResponse response = (SearchStaffListResponse)obj;
                showToast(response.getResultDesc());
                
            }
        });
		task.execute();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivTitleLeft:
			finish();
			break;
			
		case R.id.search_view:
		    String keyWord = mSearchEditText.getText().toString();
		    getPersonalSearchList(keyWord);
			break;

		default:
			break;
		}
		
	}

}
