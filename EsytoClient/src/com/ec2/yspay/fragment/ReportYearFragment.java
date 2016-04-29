package com.ec2.yspay.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ec2.yspay.R;
import com.ec2.yspay.activity.ShopListViewActivity;
import com.ec2.yspay.common.DateUtils;
import com.ec2.yspay.common.ShopManager;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.entity.ReportFormEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.PaytypeCountItem;
import com.ec2.yspay.http.response.ReportFormResponse;
import com.ec2.yspay.http.task.ReportFormTask;
import com.ec2.yspay.widget.MyHorizontalBarChart;
import com.ec2.yspay.widget.MyPieChart;
import com.ec2.yspay.widget.YearPickerDialog;

/**
 * 年报表
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月8日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportYearFragment extends Fragment{
    private static final String TAG = "ReportYearFragment";
    private Context mContext;
    private View view;
    private LinearLayout llYear;
    private LayoutInflater inflater;
    private String totalAmount;
    private String totalItems;
    private TextView tvTotalMoney,tvTotalCount,tvYear2;
    private int mYear;
    private ReportTabRightFragment reportYearFragment;
    private FrameLayout framLayout;
    private RelativeLayout rlShopLayout;
    private TextView tvShopName;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    this.inflater = inflater;
		view = inflater.inflate(R.layout.fragment_report_year, null);
		mContext = getActivity();
		mYear = DateUtils.getYear();
		initView();
		
		getDate();
		return view;
	}
	/**
	 * 重载方法
	 */
	@Override
	public void onResume()
	{
	    // TODO Auto-generated method stub
	    super.onResume();
	    
	}
	private void getDate(){
	    reportYearFragment.setIsLoading();
	    ReportFormTask task = new ReportFormTask(mContext, ""+mYear);
	    task.setProgressVisiable(false);
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
                totalAmount = response.getTotalAmount();
                totalItems = response.getTotalItems();
                initDate();
                reportYearFragment.setFormEntieyUpdate(formEntity);
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                ReportFormResponse response = (ReportFormResponse)obj;
                ToastUtils.show(mContext,response.getResultDesc());
                reportYearFragment.setNoData();
            }
        });
	    task.execute();
	}
	private void initView(){
        tvTotalMoney = (TextView)view.findViewById(R.id.tv_total_money);
        tvTotalCount = (TextView)view.findViewById(R.id.tv_total_count);
        llYear = (LinearLayout)view.findViewById(R.id.ll_year);
        llYear.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                showMonPicker();
            }
        });
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
        tvYear2 = (TextView)view.findViewById(R.id.tv_year2);
        tvYear2.setText(mYear+"年");
        framLayout = (FrameLayout)view.findViewById(R.id.fl_fragment);
        reportYearFragment = new ReportTabRightFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_fragment, reportYearFragment).commit();  
        
	}
	private void initDate(){
	    tvTotalMoney.setText(totalAmount);
	    tvTotalCount.setText(totalItems);
	}
	public void showMonPicker() {
        final Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(DateUtils.strToDate("yyyy", mYear+""));
        new YearPickerDialog(mContext,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
//                        localCalendar.set(1, year);
//                        localCalendar.set(2, monthOfYear);
//                        mon_date_filter.setText(clanderTodatetime(localCalendar, "yyyy-MM"));
                        mYear = year;
                        tvYear2.setText(mYear+"年");
                        getDate();
                        
                    }
                }, localCalendar.get(1), localCalendar.get(2),localCalendar.get(5)).show();
    }
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==101&&resultCode==100){
    		tvShopName.setText(ShopManager.getInstance(mContext).getCurrentShopName());
    		getDate();
    	}
    }
}
