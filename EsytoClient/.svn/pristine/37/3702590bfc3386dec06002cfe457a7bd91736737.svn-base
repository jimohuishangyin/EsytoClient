package com.ec2.yspay.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ec2.yspay.R;
import com.ec2.yspay.adapter.ReportGridViewAdapter;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.entity.ReportEntity;
import com.ec2.yspay.entity.ReportFormEntity;
import com.ec2.yspay.widget.MyCircleGroupChart;
import com.ec2.yspay.widget.MyGridView;
import com.ec2.yspay.widget.MyHorizontalBarChart;
import com.ec2.yspay.widget.MyPieChart;

/**
 * 报表
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月8日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportTabRightFragment extends Fragment {
    private static final String TAG = "number";
    private Context mContext;
    private View view;
    private LayoutInflater inflater;
    private MyCircleGroupChart myCircleGroupChart;
    private MyGridView myGridView;
    private ReportGridViewAdapter mAdapter;
    private ReportFormEntity formEntity;
    private boolean isLoading = true;
    private LinearLayout llData,llNoData,llLoading;
    private boolean isNeedUpdate=false;
    private List<ReportEntity> reportList = new ArrayList<ReportEntity>();
    public void setFormEntiey(ReportFormEntity formEntity){
        isLoading = false;
        this.formEntity = formEntity;
        isNeedUpdate = true;
        updateData();
    }
    public void setFormEntieyUpdate(ReportFormEntity formEntity){
        isLoading = false;
        this.formEntity = formEntity;
        updateData();
    }
    public void setNoData(){
        isLoading = false;
        formEntity = null;
    }
    public void setIsLoading(){
        isLoading = true;
    }
   
    private void updateData(){
    	reportList.clear();
    	if(formEntity==null)return;
    	reportList.clear();
    	ReportEntity entity1 = new ReportEntity(PayTypeEntity.PAYNAME_ALI, formEntity.aliMoney, 
    			formEntity.aliCount,formEntity.getCountPercent(formEntity.aliCount));
    	reportList.add(entity1);
    	ReportEntity entity2 = new ReportEntity(PayTypeEntity.PAYNAME_BAIDU, formEntity.baiduMoney, 
    			formEntity.baiduCount,formEntity.getCountPercent(formEntity.baiduCount));
    	reportList.add(entity2);
    	ReportEntity entity3 = new ReportEntity(PayTypeEntity.PAYNAME_BEST, formEntity.bestMoney, 
    			formEntity.bestCount,formEntity.getCountPercent(formEntity.bestCount));
    	reportList.add(entity3);
    	ReportEntity entity4 = new ReportEntity(PayTypeEntity.PAYNAME_CASH, formEntity.cashMoney, 
    			formEntity.cashCount,formEntity.getCountPercent(formEntity.cashCount));
    	reportList.add(entity4);
    	ReportEntity entity5 = new ReportEntity(PayTypeEntity.PAYNAME_UNION, formEntity.cardMoney, 
    			formEntity.cardCount,formEntity.getCountPercent(formEntity.cardCount));
    	reportList.add(entity5);
    	ReportEntity entity6 = new ReportEntity(PayTypeEntity.PAYNAME_WX, formEntity.wxMoney, 
    			formEntity.wxCount,formEntity.getCountPercent(formEntity.wxCount));
    	reportList.add(entity6);
    	MyCompartor compartor = new MyCompartor();
    	Collections.sort(reportList, compartor);
    	initDate();
    }
    /**
     * @ClassName: MyCompartor 
     * @Description: 金额排序,从大到小
     * @author 罗洪祥 luohx@esyto.com 
     * @date 2016年4月5日 下午3:29:56 
     */
    class MyCompartor implements Comparator
    {
         @Override
         public int compare(Object o1, Object o2)
        {

        	 ReportEntity sdto1= (ReportEntity )o1;

        	 ReportEntity sdto2= (ReportEntity )o2;
        	 
        	 float money1 = Float.valueOf(sdto1.getPayMoney());
        	 float money2 = Float.valueOf(sdto2.getPayMoney());
        	 if(money2>money1)
        		 return 1;
        	 else if(money2==money1)
        		 return 0;
        	 else
        		 return -1;

        }
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    this.inflater = inflater;
		view = inflater.inflate(R.layout.fragment_report_tableright, null);
		mContext = getActivity();
		initView();
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
	    initDate();
	    
	}
    private void initView(){
    	myCircleGroupChart = (MyCircleGroupChart)view.findViewById(R.id.chart1);
        mAdapter = new ReportGridViewAdapter(mContext, reportList);
        myGridView = (MyGridView) view.findViewById(R.id.report_gridview);
        myGridView.setAdapter(mAdapter);
        llData = (LinearLayout)view.findViewById(R.id.ll_data);
        llNoData = (LinearLayout)view.findViewById(R.id.ll_nodata);
        llLoading = (LinearLayout)view.findViewById(R.id.ll_loading);
        changeState(0);
    }
    /**
     * 状态：0 加载中，1 无数据，2 正常。
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月19日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    private void changeState(int state){
        if(state==0){
            llLoading.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
            llData.setVisibility(View.GONE);
        }else if(state==1){
            llLoading.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
            llData.setVisibility(View.GONE);
        }else if(state==2){
            llLoading.setVisibility(View.GONE);
            llNoData.setVisibility(View.GONE);
            llData.setVisibility(View.VISIBLE);
        }
    }
    private void initDate(){
        if(isLoading){
            changeState(0);
        }else if(formEntity==null){
            
        }else{
            if(formEntity.getTotalMoney()<=0){
                changeState(1);
            }else{
                changeState(2);
                myCircleGroupChart.setData(reportList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
