package com.ec2.yspay;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.adapter.OrderQueryAdapter;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ObjList;
import com.ec2.yspay.http.response.OrderQueryListResponse;
import com.ec2.yspay.http.task.OrderQueryListTask;
import com.ec2.yspay.widget.PullToRefreshLayout;
import com.ec2.yspay.widget.PullToRefreshLayout.OnRefreshListener;
import com.ec2.yspay.widget.PullableListView;

public class OrderQueryActivity extends BaseActivity implements
		OnRefreshListener {

	private PullToRefreshLayout mPullToRefreshLayout;
	private PullableListView mListView;
	private ImageView mBackImageView;
	private int mpage = 1;
	private List<ObjList> mObjList = new ArrayList<ObjList>();
	private OrderQueryAdapter mAdapter;
	private TextView mKeyEditText;
	private ImageView mSearchImageView;
	private String keyContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_query);
		getOrderQueryMsg();
	}

	private void initview() {
		mKeyEditText = (TextView) findViewById(R.id.searchkey_edittext);
		mSearchImageView = (ImageView) findViewById(R.id.search_view);
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		mPullToRefreshLayout.setOnRefreshListener(this);
		mBackImageView = (ImageView) findViewById(R.id.ivTitleLeft);
		mKeyEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OrderQueryActivity.this,
						OrderQuerySearchActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		});

		mBackImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mListView = (PullableListView) findViewById(R.id.content_view);
		mAdapter = new OrderQueryAdapter(this, mObjList);
		mListView.setAdapter(mAdapter);
		mSearchImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OrderQueryActivity.this,
						OrderQuerySearchActivity.class);
				startActivity(intent);
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ObjList o = (ObjList) mAdapter.getItem(position);
				Intent intent = new Intent(OrderQueryActivity.this,
						OrderQueryDetailActivity.class);
				intent.putExtra("orderNo", o.getOrderNo());
				startActivity(intent);
			}
		});
	}

	private void getOrderQueryMsg() {
		OrderQueryListTask task = new OrderQueryListTask(mContext,
				String.valueOf(mpage));
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				OrderQueryListResponse response = (OrderQueryListResponse) obj;
				mObjList = response.getObjList();
				initview();
			}

			@Override
			public void onFail(Object obj) {
				OrderQueryListResponse response = (OrderQueryListResponse) obj;
				showToast(response.getResultDesc());
				finish();
			}
		});
		task.execute();
	}

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		mpage = 1;
		OrderQueryListTask task = new OrderQueryListTask(mContext,
				String.valueOf(mpage));
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				mObjList.clear();
				OrderQueryListResponse response = (OrderQueryListResponse) obj;
				mObjList.addAll(response.getObjList());
				mAdapter.notifyDataSetChanged();
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}

			@Override
			public void onFail(Object obj) {
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
				OrderQueryListResponse response = (OrderQueryListResponse) obj;
				showToast(response.getResultDesc());
				finish();
			}
		});
		task.execute();

	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		mpage++;
		OrderQueryListTask task = new OrderQueryListTask(mContext,
				String.valueOf(mpage));
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				OrderQueryListResponse response = (OrderQueryListResponse) obj;
				mObjList.addAll(response.getObjList());
				mAdapter.notifyDataSetChanged();
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}

			@Override
			public void onFail(Object obj) {
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
				OrderQueryListResponse response = (OrderQueryListResponse) obj;
				showToast(response.getResultDesc());
				finish();
			}
		});
		task.execute();
	}


}
