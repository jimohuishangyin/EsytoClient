package com.ec2.yspay.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ec2.yspay.R;
import com.ec2.yspay.activity.ShopListViewActivity;
import com.ec2.yspay.common.DateUtils;
import com.ec2.yspay.common.ShopManager;
import com.ec2.yspay.entity.ReportExpandableListItem;
import com.ec2.yspay.entity.ReportFormEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.ReportDetailsResponse;
import com.ec2.yspay.http.response.ReportFormResponse;
import com.ec2.yspay.http.task.ReportDetailsTask;
import com.ec2.yspay.http.task.ReportFormTask;
import com.ec2.yspay.widget.DateTimePickDialogUtil;
import com.ec2.yspay.widget.DateTimePickDialogUtil.OnDatePickListener;

/**
 * 日报表
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月8日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportDayFragment extends Fragment implements OnClickListener{
    private static final String TAG = "number";
    private Context mContext;
    private View view;
    private LayoutInflater inflater;
    private TabHost tabHost;
    private RelativeLayout rlTabLeft,rlTabRight;
    Fragment frag; 
    private TextView tvMingxi,tvBaobiao;
    private int mYear,mMonth,mDay;
    private TextView tvYear,tvMonth;
    private LinearLayout llDate;
    private ReportTabRightFragment rightFragment;
    private ReportTabLeftFragment leftFragment;
    private TextView tvMoney,tvCount;
    private RelativeLayout rlShopLayout;
    private TextView tvShopName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_report_day, null);
        mContext = getActivity();
        mYear = DateUtils.getYear();
        mMonth = DateUtils.getMonth();
        mDay = DateUtils.getDay();
        rlTabLeft = (RelativeLayout)view.findViewById(R.id.rl_tab_left);
        rlTabRight = (RelativeLayout)view.findViewById(R.id.rl_tab_right);
        tvMoney = (TextView)view.findViewById(R.id.tv_money);
        tvCount = (TextView)view.findViewById(R.id.tv_count);
        rlTabLeft.setOnClickListener(this);
        rlTabRight.setOnClickListener(this);
        tvYear = (TextView)view.findViewById(R.id.tv_year);
        tvMonth = (TextView)view.findViewById(R.id.tv_day);
        rlShopLayout = (RelativeLayout) view.findViewById(R.id.rl_shop);
        rlShopLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, ShopListViewActivity.class);
				startActivityForResult(intent, 101);
			}
		});
        tvShopName = (TextView) view.findViewById(R.id.tv_shopName);
        tvShopName.setText(ShopManager.getInstance(mContext).getCurrentShopName());
        tvYear.setText(mYear+"年"+mMonth+"月");
        tvMonth.setText(mDay+"");
        llDate = (LinearLayout)view.findViewById(R.id.ll_date);
        llDate.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                showMonPicker();
            }
        });
        tvMingxi = (TextView)view.findViewById(R.id.tv_mingxi);
        tvBaobiao = (TextView)view.findViewById(R.id.tv_baobiao);
        leftFragment = new ReportTabLeftFragment();
        rightFragment = new ReportTabRightFragment();
        tabHost = (TabHost)view.findViewById(android.R.id.tabhost);
        //调用 TabHost.setup()
        tabHost.setup();
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {  
            
            @Override  
            public void onTabChanged(String tabId) {  
                FragmentTransaction ft = getFragmentManager().beginTransaction();  
                Fragment frag = null;
                if (TextUtils.equals("first", tabId)) {  
                    //add/replace fragment first  
                    frag = leftFragment;  
                } else if (TextUtils.equals("second", tabId)) {  
                    //add/replace fragment second  
                    frag = rightFragment;  
                }
                ft.replace(android.R.id.tabcontent, frag, "frag");  
                ft.commit();
            }  
        });  
        tabHost.addTab(tabHost.newTabSpec("first").setIndicator("First")  
                .setContent(new DummyTabFactory(mContext)));  
        tabHost.addTab(tabHost.newTabSpec("second").setIndicator("Second")//setIndicator 设置标签样式  
                .setContent(new DummyTabFactory(mContext))); //setContent 点击标签后触发  
        // tabHost.setCurrentTab(0);//zzm设置默认值ＴＡＢ
        onClick(rlTabRight);//zzm设置默认值ＴＡＢ
        getDayDetails();
        getFormMsg();
        return view;
    }
    static class DummyTabFactory implements TabHost.TabContentFactory {  
        private Context context;  
        public DummyTabFactory(Context ctx) {
            this.context = ctx;  
        }
        @Override  
        public View createTabContent(String tag) {//创建宽高均为0的view   
            View v = new ImageView(context);  
            v.setMinimumWidth(0);  
            v.setMinimumHeight(0);  
            return v;  
        }  
          
    }
    /**
     * 重载方法
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.rl_tab_left:
                tvMingxi.setTextColor(getResources().getColor(R.color.white));
                tvBaobiao.setTextColor(getResources().getColor(R.color.text_gray));
                rlTabLeft.setBackgroundColor(getResources().getColor(R.color.report_purple));
                rlTabRight.setBackgroundColor(getResources().getColor(R.color.white));
                tabHost.setCurrentTab(0);
                break;
            case R.id.rl_tab_right:
                tvMingxi.setTextColor(getResources().getColor(R.color.text_gray));
                tvBaobiao.setTextColor(getResources().getColor(R.color.white));
                rlTabLeft.setBackgroundColor(getResources().getColor(R.color.white));
                rlTabRight.setBackgroundColor(getResources().getColor(R.color.report_purple));
                tabHost.setCurrentTab(1);
                break;
            default:
                break;
        }
    } 
    public void showMonPicker() {
        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(mContext, mYear+"年"+mMonth+"月"+mDay+"日 14:44");
        dateTimePicKDialog.setOnDatePickListener(new OnDatePickListener()
        {
            
            @Override
            public void onResult(int year, int month, int day)
            {
                // TODO Auto-generated method stub
                mYear = year;
                mMonth = month+1;
                mDay = day;
                tvYear.setText(mYear+"年"+mMonth+"月");
                tvMonth.setText(mDay+"");
                getFormMsg();
                getDayDetails();
            }
        });
        dateTimePicKDialog.dateTimePicKDialog();
    }
    private void getDayDetails(){
        ReportDetailsTask task = new ReportDetailsTask(mContext, mYear+"-"+mMonth+"-"+mDay);
        task.setProgressVisiable(true);
        task.setShopCode(ShopManager.getInstance(mContext).getCurrentShopCode());
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                ReportDetailsResponse response = (ReportDetailsResponse)obj;
                tvMoney.setText(response.getTotalAmount());
                tvCount.setText(response.getTotalItems());
                List<ReportExpandableListItem> mList = new ArrayList<ReportExpandableListItem>();
                ReportExpandableListItem item = new ReportExpandableListItem(
                    mYear,mMonth,mDay,response.getOrderList());
                mList.add(item);
                leftFragment.setList(mList);
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                ReportDetailsResponse response = (ReportDetailsResponse)obj;
                Toast.makeText(mContext, response.getResultDesc(), Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }
    private void getFormMsg(){
        rightFragment.setIsLoading();
        ReportFormTask task = new ReportFormTask(mContext, mYear+"-"+mMonth+"-"+mDay);
        task.setShopCode(ShopManager.getInstance(mContext).getCurrentShopCode());
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                ReportFormResponse response = (ReportFormResponse)obj;
                ReportFormEntity formEntity = new ReportFormEntity();
                formEntity.setFormList(response.getCollectInfos());
                if(tabHost.getCurrentTab()==1){
                    rightFragment.setFormEntieyUpdate(formEntity);
                }else{
                    rightFragment.setFormEntiey(formEntity);
                }
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                rightFragment.setNoData();
                ReportFormResponse response = (ReportFormResponse)obj;
                Toast.makeText(mContext, response.getResultDesc(), Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==101&&resultCode==100){
    		tvShopName.setText(ShopManager.getInstance(mContext).getCurrentShopName());
    		getFormMsg();
            getDayDetails();
    	}
    }
}
