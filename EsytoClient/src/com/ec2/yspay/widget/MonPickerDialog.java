/*
 * 类文件名:  MonPickerDialog.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年11月1日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ec2.yspay.common.DateUtils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年11月1日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MonPickerDialog extends DatePickerDialog
{
    private Context mContext ;
    /** 
     * <默认构造函数>
     */
    public MonPickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
    {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.setTitle(year + "年" + (monthOfYear + 1) + "月");  
        
        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0))
            .getChildAt(0)).getChildAt(2).setVisibility(View.GONE);  
        this.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS); 
    }
    @Override  
    public void onDateChanged(DatePicker view, int year, int month, int day) {  
        super.onDateChanged(view, year, month, day);  
        this.setTitle(year + "年" + (month + 1) + "月");  
    }
    public void showMonPicker() {
        final Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(DateUtils.strToDate("yyyy-MM", ""));
        new MonPickerDialog(mContext,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
//                        localCalendar.set(1, year);
//                        localCalendar.set(2, monthOfYear);
//                        mon_date_filter.setText(clanderTodatetime(localCalendar, "yyyy-MM"));
                    }
                }, localCalendar.get(1), localCalendar.get(2),localCalendar.get(5)).show();
    }
  
    
}
