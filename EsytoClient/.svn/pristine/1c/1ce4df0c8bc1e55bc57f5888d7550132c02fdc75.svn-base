/*
 * 类文件名:  MyPieChart.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月31日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.widget;

import java.util.ArrayList;
import java.util.List;

import com.ec2.yspay.R;
import com.ec2.yspay.common.Log;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.entity.ReportEntity;
import com.ec2.yspay.entity.ReportFormEntity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月31日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyCircleGroupChart extends RelativeLayout
{
	private final static String TAG = "MyCircleGroupChart";
    private Context mContext;
    private HalfRoundProgressBar mRoundProgressBar1;
    private List<HalfRoundProgressBar> progressBarList =new ArrayList<HalfRoundProgressBar>();
    private List<ReportEntity> reportList = new ArrayList<ReportEntity>();
    private int maxCircleRate = 750;
    /** 
     * <默认构造函数>
     */
    public MyCircleGroupChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_circlegroup_chart, this);
    }
    public void setData(List<ReportEntity> reportList){
    	this.reportList = reportList;
        initChart();
    }
    private void initChart(){
    	progressBarList.clear();
    	progressBarList.add((HalfRoundProgressBar) findViewById(R.id.roundProgressBar1));
    	progressBarList.add((HalfRoundProgressBar) findViewById(R.id.roundProgressBar2));
    	progressBarList.add((HalfRoundProgressBar) findViewById(R.id.roundProgressBar3));
    	progressBarList.add((HalfRoundProgressBar) findViewById(R.id.roundProgressBar4));
    	progressBarList.add((HalfRoundProgressBar) findViewById(R.id.roundProgressBar5));
    	progressBarList.add((HalfRoundProgressBar) findViewById(R.id.roundProgressBar6));
    	start();
    }
    
    private void start(){
    	if(reportList==null||reportList.size()<1)
    		return;
    	mRoundProgressBar1 = progressBarList.get(0);
    	ReportEntity entity01 = reportList.get(0);
    	float money1 = Float.valueOf(entity01.getPayMoney());
    	double d1 = Double.valueOf(entity01.getPayMoney());
		String totalAmount1 = entity01.getPayMoney();
        if(d1>100000){
            d1 = d1/10000d;
            totalAmount1 = Toolkits.doubleFormat(d1)+"万";
        }
    	mRoundProgressBar1.setPayMoney("￥"+totalAmount1);
    	mRoundProgressBar1.setPayName(entity01.getPayName());
    	mRoundProgressBar1.startAutoProgress(maxCircleRate);
    	for(int i=1;i<reportList.size();i++){
    		if(i>=progressBarList.size()){
    			Log.e(TAG, "超出使用progressBarList范围");
    			break;
    		}
    		ReportEntity entity = reportList.get(i);
    		float money = Float.valueOf(entity.getPayMoney());
    		HalfRoundProgressBar mRoundProgressBar = progressBarList.get(i);
    		double d = Double.valueOf(entity.getPayMoney());
    		String totalAmount = entity.getPayMoney();
            if(d>100000){
                d = d/10000d;
                totalAmount = Toolkits.doubleFormat(d)+"万";
            }
    		mRoundProgressBar.setPayMoney("￥"+totalAmount);
    		mRoundProgressBar.setPayName(entity.getPayName());
    		int progress = (int) (money/money1*maxCircleRate);
    		mRoundProgressBar.startAutoProgress(progress);
    	}
	}
}
