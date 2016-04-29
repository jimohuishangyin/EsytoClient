package com.ec2.yspay;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.adapter.StaffAddListAdapter;
import com.ec2.yspay.common.StaffManager;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.task.UpdateStaffStatusTask;
import com.ec2.yspay.widget.MyTitle;

public class PersonAddListActivity extends BaseActivity {
	private SwipeMenuListView mListView;
	private StaffAddListAdapter mAdapter;
	private StaffManager staffManager = StaffManager.getInstance(mContext);
	private Button btnAddContinue;
	private String mShopCode,mShopName;
	private MyTitle mTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_add_list);
		mListView = (SwipeMenuListView) findViewById(R.id.listView);
		btnAddContinue = (Button) findViewById(R.id.btn_add_continue);
		mTitle = (MyTitle) findViewById(R.id.rl_top);
		mTitle.setRightOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mShopCode = getIntent().getStringExtra("shopCode");
        mShopName = getIntent().getStringExtra("shopName");
		btnAddContinue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,PersonalAddActivity.class);
				intent.putExtra("shopCode", mShopCode);
            	intent.putExtra("shopName", mShopName);
				startActivity(intent);
				finish();
			}
		});
		mAdapter = new StaffAddListAdapter(mContext, staffManager.getStafflist());
		mListView.setAdapter(mAdapter);
		Toolkits.setListViewHeightBasedOnChildren(mListView);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xEA,
						0x2E, 0x49)));
				// set item width
				openItem.setWidth(Toolkits.dip2px(mContext, 100));
				// set item title
				openItem.setTitle("删除");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					
					deleteStaff(index);
					break;
				}
			}
		});
		
		// set SwipeListener
		mListView.setOnSwipeListener(new OnSwipeListener() {
			
			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}
			
			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), position + " long click", 0).show();
				return false;
			}
		});
	}
	private void deleteStaff(final int index){
        UpdateStaffStatusTask task = new UpdateStaffStatusTask(mContext);
        task.setAccount(staffManager.getStafflist().get(index).getAccount());
        task.setStatus("4");
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                ToastUtils.show(mContext, "删除成功");
                staffManager.delStaff(index);
				mAdapter.notifyDataSetChanged();
            }
            
            @Override
            public void onFail(Object obj)
            {
                BooleanResponse response = (BooleanResponse)obj;
                ToastUtils.showLong(mContext, response.getResultDesc());
            }
        });
        task.execute();
    }
}
