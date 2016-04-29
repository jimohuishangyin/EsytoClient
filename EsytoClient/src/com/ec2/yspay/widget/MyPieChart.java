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
import com.ec2.yspay.common.Toolkits;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
public class MyPieChart extends LinearLayout implements OnChartValueSelectedListener
{
    private Context mContext;
    private PieChart mChart;
    private Typeface tf;
    protected String[] mParties = new String[] {"现金", "银行卡", "支付宝", "微信支付", "翼支付"};
    private String cashMoney="0.00";
    private String cardMoney="0.00";
    private String bestMoney="0.00";
    private String aliMoney="0.00";
    private String wxMoney="0.00";
    private float mTotal=0;;
    /** 
     * <默认构造函数>
     */
    public MyPieChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_pie_chart, this);
    }
    public void setData(String cashMoney,String cardMoney,String bestMoney,String aliMoney,String wxMoney){
        this.cashMoney = cashMoney;
        this.cardMoney = cardMoney;
        this.bestMoney = bestMoney;
        this.aliMoney = aliMoney;
        this.wxMoney = wxMoney;
        initChart();
    }
    private void initChart(){
        mChart = (PieChart) findViewById(R.id.chart_pie);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText(generateCenterSpannableText("", 0));

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);//内圈半径
        mChart.setTransparentCircleRadius(61f);//渐变半透明半径

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        setData(4, 100);
        
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.ABOVE_CHART_RIGHT);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(6f);
        l.setYOffset(6f);
        l.setEnabled(false);
    }
    private SpannableString generateCenterSpannableText(String title,float value) {
        float percent=0;
        Log.i("lhx", "mTotal:"+mTotal);
        if(Toolkits.isStrEmpty(title)){
            SpannableString s = new SpannableString("");
            return s;
        }else{
            if(mTotal!=0){
                percent = value/mTotal;
            }
            percent = percent*100;
            String strValue = Toolkits.doubleFormat(value);
            if(value>100000){
                value = value/10000;
                strValue = Toolkits.doubleFormat(value)+"万";
            }
            
            SpannableString s = new SpannableString(title+"\n 金额:￥"+strValue+"\n 比例："+Toolkits.doubleFormat(percent)+"%");
            int firstIndex = title.length();
            String secondStr = title+"\n 金额:￥"+strValue;
            int secondIndex = secondStr.length();
            
            s.setSpan(new RelativeSizeSpan(1.7f), 0, firstIndex, 0);
            
            s.setSpan(new StyleSpan(Typeface.NORMAL), firstIndex+1, secondIndex, 0);
            s.setSpan(new ForegroundColorSpan(Color.GRAY), firstIndex+1, secondIndex, 0);
            s.setSpan(new RelativeSizeSpan(1.3f), firstIndex+1, secondIndex, 0);
            
            s.setSpan(new StyleSpan(Typeface.NORMAL), secondIndex+1, s.length(), 0);
            s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), secondIndex+1, s.length(), 0);
            s.setSpan(new RelativeSizeSpan(1.3f), secondIndex+1, s.length(), 0);
            return s;
        }
    }
    ArrayList<String> xVals = new ArrayList<String>();
    private void setData(int count, float range) {
        xVals.clear();
        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        int index = 0;
        mTotal = 0;
        ArrayList<Integer> colors = new ArrayList<Integer>();
      //"现金", "银联", "支付宝", "微信支付", "翼支付"
        float cash = Float.valueOf(cashMoney);
        if(cash>0){
            xVals.add(mParties[0]);
            yVals1.add(new BarEntry(cash, index++));
            mTotal+=cash;
            colors.add(getResources().getColor(R.color.chart_cash));
        }
        float card = Float.valueOf(cardMoney);
        if(card>0){
            xVals.add(mParties[1]);
            yVals1.add(new BarEntry(card, index++));
            mTotal+=card;
            colors.add(getResources().getColor(R.color.chart_card));
        }
        
        float ali = Float.valueOf(aliMoney);
        if(ali>0){
            xVals.add(mParties[2]);
            yVals1.add(new BarEntry(ali, index++));
            mTotal+=ali;
            colors.add(getResources().getColor(R.color.chart_ali));
        }
        float wx = Float.valueOf(wxMoney);
        if(wx>0){
            xVals.add(mParties[3]);
            yVals1.add(new BarEntry(wx, index++));
            mTotal+=wx;
            colors.add(getResources().getColor(R.color.chart_wx));
        }
        
        float best = Float.valueOf(bestMoney);
        if(best>0){
            xVals.add(mParties[4]);
            yVals1.add(new BarEntry(best, index++));
            mTotal+=best;
            colors.add(getResources().getColor(R.color.chart_best));
        }
        
        
        PieDataSet dataSet = new PieDataSet(yVals1, "");//右边注释
        dataSet.setSliceSpace(2f);//设置个饼状图之间的距离
        dataSet.setSelectionShift(5f);// 选中态多出的长度

        // add a lot of colors


        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(formatter);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
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
        
        mChart.setCenterText(generateCenterSpannableText(xVals.get(e.getXIndex()),e.getVal()));
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
        mChart.setCenterText(generateCenterSpannableText("", 0));
    }
    PercentFormatter formatter = new PercentFormatter(){
        /**
         * 重载方法
         * @param value
         * @param entry
         * @param dataSetIndex
         * @param viewPortHandler
         * @return
         */
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
        {
            // TODO Auto-generated method stub
            return super.getFormattedValue(value, entry, dataSetIndex, viewPortHandler);
        }
        
    };
    
}
