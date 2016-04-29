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

import com.ec2.yspay.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

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
public class MyHorizontalBarChart extends LinearLayout implements OnChartValueSelectedListener
{
    private Context mContext;
    protected HorizontalBarChart mBarChart;
    private Typeface tf;
    protected String[] mParties = new String[] {"现金", "银行卡", "支付宝", "微信支付", "翼支付"};
    private String cashCount="0";
    private String cardCount="0";
    private String bestCount="0";
    private String aliCount="0";
    private String wxCount="0";
    /** 
     * <默认构造函数>
     */
    public MyHorizontalBarChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_horizontal_bar_chart, this);
    }
    public void setData(String cashCount,String cardCount,String bestCount,String aliCount,String wxCount){
        this.cashCount = cashCount;
        this.cardCount = cardCount;
        this.bestCount = bestCount;
        this.aliCount = aliCount;
        this.wxCount = wxCount;
        initBarChart();
    }
    private void initBarChart(){
        mBarChart = (HorizontalBarChart) findViewById(R.id.chart_barchart);
        // mChart.setHighlightEnabled(false);
        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);

        mBarChart.setDrawValueAboveBar(true);

        mBarChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mBarChart.setDrawGridBackground(false);

        // mChart.setDrawYLabels(false);
        mBarChart.setAutoScaleMinMaxEnabled(false);
        mBarChart.setDoubleTapToZoomEnabled(false);
        mBarChart.setDragEnabled(false);
        mBarChart.setClickable(false);
        mBarChart.setTouchEnabled(false);

        XAxis xl = mBarChart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mBarChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
        yl.setInverted(false);
        yl.setDrawLabels(false);
//
        YAxis yr = mBarChart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setInverted(false);
        yr.setDrawLabels(false);

        setData2(5, 50);
        mBarChart.animateY(2500);

        // setting data

        Legend l = mBarChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(10f);
        l.setXEntrySpace(0f);
        mBarChart.getLegend().setEnabled(false);
    }
    private void setData2(int count, float range) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        /*for (int i = 0; i < count; i++) {
            xVals.add(mParties[i]);
            yVals1.add(new BarEntry((float) (Math.random() * range), i));
        }*/
        //"现金", "银联", "支付宝", "微信支付", "翼支付"
        float cashNum = Float.valueOf(cashCount);
        if(cashNum>=0){
            xVals.add(mParties[0]);
            yVals1.add(new BarEntry(cashNum, 0));
        }
        
        float cardNum = Float.valueOf(cardCount);
        if(cardNum>=0){
            xVals.add(mParties[1]);
            yVals1.add(new BarEntry(cardNum, 1));
        }
        
        float aliNum = Float.valueOf(aliCount);
        if(aliNum>=0){
            xVals.add(mParties[2]);
            yVals1.add(new BarEntry(aliNum, 2));
        }
        
        float wxNum = Float.valueOf(wxCount);
        if(wxNum>=0){
            xVals.add(mParties[3]);
            yVals1.add(new BarEntry(wxNum, 3));
        }
        
        float bestNum = Float.valueOf(bestCount);
        if(bestNum>=0){
            xVals.add(mParties[4]);
            yVals1.add(new BarEntry(bestNum, 4));
//            yVals1.add(new BarEntry(3, 4));
        }
        
        BarDataSet set1 = new BarDataSet(yVals1, "DataSet 1");
        
        set1.setColors(getColors());

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(format);
        data.setValueTextSize(10f);
        data.setValueTypeface(tf);

        mBarChart.setData(data);
    }
    private ArrayList<Integer> getColors(){
        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(getResources().getColor(R.color.chart_cash));
        colors.add(getResources().getColor(R.color.chart_card));
        colors.add(getResources().getColor(R.color.chart_ali));
        colors.add(getResources().getColor(R.color.chart_wx));
        colors.add(getResources().getColor(R.color.chart_best));
        return colors;
    }
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
    ValueFormatter format = new ValueFormatter()
    {
        
        @Override
        public String getFormattedValue(float arg0, Entry arg1, int arg2, ViewPortHandler arg3)
        {
            // TODO Auto-generated method stub
            int value = Math.round(arg0);
            return value+"笔";
        }
    };
}
