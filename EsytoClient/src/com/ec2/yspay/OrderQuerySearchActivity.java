package com.ec2.yspay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.adapter.OrderQueryAdapter;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ObjList;
import com.ec2.yspay.http.response.OrderQueryListResponse;
import com.ec2.yspay.http.task.OrderQueryByKeyListTask;
import com.ec2.yspay.widget.PullToRefreshLayout;
import com.ec2.yspay.widget.PullToRefreshLayout.OnRefreshListener;
import com.ec2.yspay.widget.PullableListView;

public class OrderQuerySearchActivity extends BaseActivity implements
		OnRefreshListener {

	private PullToRefreshLayout mPullToRefreshLayout;
	private PullableListView mListView;
	private ImageView mBackImageView;
	private AutoCompleteTextView mKeyEditText;
	private List<ObjList> mObjList = new ArrayList<ObjList>();;
	private OrderQueryAdapter mAdapter;
	private ImageView mSearchImageView;
	String[] str = new String[] { "支付宝", "微信支付", "现金", "翼支付", "银行卡" };
	private String keyContext;
	// 判断是否为搜索
	private boolean mSearch = false;
	private int mPageBySearch = 1;
	private String EncoderKey = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_query_search);
		initview();
	}

	private void initview() {
		mKeyEditText = (AutoCompleteTextView) findViewById(R.id.searchkey_edittext);
		mKeyEditText.setFocusable(true);
		mKeyEditText.setFocusableInTouchMode(true);
		mKeyEditText.requestFocus();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			public void run() {
				InputMethodManager inputManager = (InputMethodManager) mKeyEditText
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(mKeyEditText, 0);
			}

		}, 500);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, str);
		mKeyEditText.setAdapter(adapter);
		mSearchImageView = (ImageView) findViewById(R.id.search_view);
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		mPullToRefreshLayout.setOnRefreshListener(this);
		mBackImageView = (ImageView) findViewById(R.id.ivTitleLeft);
		mListView = (PullableListView) findViewById(R.id.content_view);
		mAdapter = new OrderQueryAdapter(this, mObjList);
		mListView.setAdapter(mAdapter);
		mKeyEditText.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView listview = (ListView) parent;
				ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent
						.getAdapter();
				TextView textview = (TextView) view;
				String textviewKey = textview.getText().toString();
				if (textviewKey.equals("支付宝")) {
					EncoderKey = "1001";
				} else if (textviewKey.equals("微信支付")) {
					EncoderKey = "1002";
				} else if (textviewKey.equals("翼支付")) {
					EncoderKey = "1003";
				} else if (textviewKey.equals("现金")) {
					EncoderKey = "1004";
				} else if (textviewKey.equals("银行卡")) {
					EncoderKey = "1005";
				}
				getOrderQueryByKeyMsg(EncoderKey);
			}
		});

		mBackImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mSearchImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				keyContext = mKeyEditText.getText().toString();
				if (keyContext.isEmpty() || keyContext.equals("")) {
					Toast.makeText(OrderQuerySearchActivity.this, "请输入关键字",
							Toast.LENGTH_LONG).show();
				} else {
					if (keyContext.equals("支付宝")) {
						EncoderKey = "1001";
					} else if (keyContext.equals("微信支付")) {
						EncoderKey = "1002";
					} else if (keyContext.equals("翼支付")) {
						EncoderKey = "1003";
					} else if (keyContext.equals("现金")) {
						EncoderKey = "1004";
					} else if (keyContext.equals("银行卡")) {
						EncoderKey = "1005";
					} else {
						try {
							EncoderKey = URLEncoder.encode(keyContext, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					getOrderQueryByKeyMsg(EncoderKey);
				}

			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ObjList o = (ObjList) mAdapter.getItem(position);
				Intent intent = new Intent(OrderQuerySearchActivity.this,
						OrderQueryDetailActivity.class);
				intent.putExtra("orderNo", o.getOrderNo());
				startActivity(intent);
			}
		});
	}

	private void getOrderQueryByKeyMsg(String key) {
		mObjList.clear();
		mAdapter.notifyDataSetChanged();
		OrderQueryByKeyListTask task = new OrderQueryByKeyListTask(mContext,
				String.valueOf(mPageBySearch), key);
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {
			@Override
			public void onSucc(Object obj) {
				OrderQueryListResponse response = (OrderQueryListResponse) obj;
				mObjList.addAll(response.getObjList());
				mAdapter.notifyDataSetChanged();
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
		mPageBySearch = 1;
		OrderQueryByKeyListTask task = new OrderQueryByKeyListTask(mContext,
				String.valueOf(mPageBySearch), EncoderKey);
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
		mPageBySearch++;
		OrderQueryByKeyListTask task = new OrderQueryByKeyListTask(mContext,
				String.valueOf(mPageBySearch), EncoderKey);
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
