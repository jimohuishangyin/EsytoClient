/*
 * 类文件名:  Calculator.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月8日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.widget;

import java.text.DecimalFormat;
import java.util.Arrays;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import bsh.Interpreter;

import com.ec2.yspay.R;
import com.ec2.yspay.activity.MainActivity;
import com.ec2.yspay.common.ToastUtils;

/**
 * 计算器
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月8日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Calculator extends LinearLayout implements OnClickListener
{
    private Context mContext;
    private int MAX_LENGTH = 7;//小数点前保留8位，百万
    private final static int MAX_LENGTH_POINT = 2;//小数点后保留两位;
    private final static String ERR_CAL="算数公式错误";
    private final static String ERR_LONG="已超过最大数字";
    private final static String EXP_START="start";
    private final static String EXP_POINT="point";
    private final static String EXP_OPERATION="operation";
    private final static String EXP_EQUAL="equal";
    private String opFlag=EXP_START;//前一位运算符标识
    private int currentLength=0;//当前输入数字的长度
    boolean isClear = false; //用于记录依稀
    private RelativeLayout llCal;
    private LinearLayout llNumber;
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnPoint,btnPlus,btnClear,btnEqual;
    private String sResult;
    private String errorRs;
    //键盘敲击监听事件
    private OnKeyBoradClickListener keyBoradClickListener;
    /**
     * 设置最大值，小数点前面的位数，默认为7
     * @Description: TODO 
     * @param @param value  
     * @return void
     * @author 罗洪祥
     * @date 2016年4月25日 下午2:52:42
     */
    public void setMaxValue(int value){
    	MAX_LENGTH = value;
    }
    /** 
     * <默认构造函数>
     */
    public Calculator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_calculator, this);
        sResult = "0";
        // 初始化控件
        initView();
    }
    public void clearData(){
        sResult = "0";
        opFlag=EXP_START;
    }
    public void initView(){
        btn0 = (Button)findViewById(R.id.btn_zero);
        btn1 = (Button)findViewById(R.id.btn_one);
        btn2 = (Button)findViewById(R.id.btn_two);
        btn3 = (Button)findViewById(R.id.btn_three);
        btn4 = (Button)findViewById(R.id.btn_four);
        btn5 = (Button)findViewById(R.id.btn_five);
        btn6 = (Button)findViewById(R.id.btn_six);
        btn7 = (Button)findViewById(R.id.btn_seven);
        btn8 = (Button)findViewById(R.id.btn_eight);
        btn9 = (Button)findViewById(R.id.btn_night);
        btnPoint = (Button)findViewById(R.id.btn_point);
        btnPlus = (Button)findViewById(R.id.btn_plus);
        btnClear = (Button)findViewById(R.id.btn_clear);
        btnEqual = (Button)findViewById(R.id.btn_equal);
        
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnPoint.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        llNumber = (LinearLayout)findViewById(R.id.ll_number);
        
        llCal = (RelativeLayout)findViewById(R.id.btn_cal);
        llCal.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
//                slideView(500);
                onResult(true);
                
            }
        });
        ImageButton ibDel = (ImageButton)findViewById(R.id.btn_del_one);
        ibDel.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
//                llCal.setVisibility(View.GONE);
//                btnEqual.setVisibility(View.VISIBLE);
                //回删，应为有做长度限制，所以要先判断当前的操作符是什么
                String exp = sResult;
                
                exp = exp.substring(0, exp.length()-1);
                if(exp==null || exp.trim().length()==0){
                    exp="0";
                    currentLength=1;
                    opFlag = EXP_START;
                }
                sResult = exp;
                getState();
                onResult(false);
                
            }
        });
    }
    /**
     * 重载方法
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
        Button btn = (Button)v;
        String exp = sResult;
        // TODO Auto-generated method stub
        if(isClear){
          sResult = "0";
          isClear = false;
          opFlag = EXP_START;
          currentLength = 0;
        }
        if(btn.getText().equals("=")){
          if(exp==null || exp.trim().length()==0)
              return; 
          if(exp.endsWith("+")||exp.endsWith("×")){
              exp=exp.substring(0, exp.length()-1);
          }
          exp = exp.replaceAll("×", "*");
          double d = getRs(exp);
          if(d>9999999){
              ToastUtils.show(mContext,"金额过大");
              return;
          }
          if(d>=0){
              exp = String.valueOf(d);
          }
          if(exp.endsWith(".0")){
              exp = exp.substring(0, exp.indexOf(".0"));
          }
          onResult(false);
//          sResult = exp;
          llCal.setVisibility(View.VISIBLE);
          btnEqual.setVisibility(View.GONE);
          opFlag = EXP_EQUAL;
          currentLength = 0;
        }else if(btn.getText().equals("+")||btn.getText().equals("×")){
            
       /*   zzm 2015.12.11 去掉计算器等于功能，优化收款 llCal.setVisibility(View.GONE);
           btnEqual.setVisibility(View.VISIBLE);
*/            if(exp==null || exp.trim().length()==0)
                return; 
            if(exp.endsWith("+")||exp.endsWith("×")){
                if(exp.length()<2)return;
                exp = exp.substring(0, exp.length()-1);
            }
            String sValue = exp.replaceAll("×", "*");
            
            getRs(sValue);
            
            sResult = exp+""+btn.getText();
            opFlag = EXP_OPERATION;
            currentLength = 0;
            onResult(false);
        }else if(btn.getText().equals(".")){
            if(exp.endsWith(".")||opFlag.equals(EXP_POINT)||opFlag.equals(EXP_EQUAL)){
                return;
            }else if(exp.endsWith("+")||exp.endsWith("×")){
                exp+="0";
            }
            
            sResult = exp+""+btn.getText();
            opFlag = EXP_POINT;
            currentLength = 0;
            onResult(false);
        }else if(btn.getId()==R.id.btn_clear){
            sResult = "0";
            opFlag = EXP_START;
            currentLength = 1;
            onResult(false);
        }else{
          //输入数字，限制数字长度，整数部分不超过9位数，小数部分只能输入两位数
            if(opFlag.equals(EXP_POINT)){
                if(currentLength>=MAX_LENGTH_POINT){
                    return;
                }
            }else if(opFlag.equals(EXP_OPERATION)){
                if(currentLength>=MAX_LENGTH){
                    return;
                }
            }else if(opFlag.equals(EXP_START)){
                String value = sResult;
                if(value.length()==1&&value.equals("0")){
                    sResult = "";  
                    currentLength = 0;
                }
                if(currentLength>=MAX_LENGTH){
                    return;
                }
            }else if(opFlag.equals(EXP_EQUAL)){
                sResult = ""; 
                currentLength = 0;
                opFlag = EXP_START;
            }
            currentLength++;
            sResult = sResult+""+btn.getText();
            onResult(false);
        }
      
    }
    /**
     * 回删时获取当前状态，判断当前的操作符和数字长度
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年10月8日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    private void getState(){
        if(isClear){
            currentLength=1;
            opFlag = EXP_START;
            sResult = "";
            return;
        }
        String exp = sResult;
        exp = exp.replaceAll("×", "*");
        opFlag = EXP_START;
        
        for(int i = exp.length()-1;i>0;i--){
            if(exp.charAt(i)=='+'||exp.charAt(i)=='*'){
                opFlag = EXP_OPERATION;
                currentLength=exp.length()-1-i;
                break;
            }else if(exp.charAt(i)=='.'){
                opFlag = EXP_POINT;
                currentLength=exp.length()-1-i;
                break;
            }
        }
        if(opFlag.equals(EXP_START)){
            currentLength=exp.length();
        }
        
    }
    /**
     * @param  exp 算数表达式
     * @return 根据表达式返回结果
     */
    private double getRs(String exp){
        double dValue = -1;
        Interpreter bsh = new Interpreter();
        Number result = null;
        try {
            exp = filterExp(exp);
            result = (Number)bsh.eval(exp);
            double double_value = result.doubleValue();
            if(double_value>10000000000d){
                errorRs = ERR_LONG;
                isClear = true;
            }else{
                dValue = double_value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isClear = true;
            errorRs = ERR_CAL;
        }
        
//            if(exp.endsWith(".0"))
//                exp = exp.substring(0, exp.indexOf(".0"));
        return dValue;
    }
    public String getErrorRS(){
        return errorRs;
    }
         
    /**
     * 因为计算过程中,全程需要有小数参与,所以需要过滤一下
     * @param exp 算数表达式
     * @return 
     */
    private String filterExp(String exp) {
        String num[] = exp.split("");
        String temp = null;
        int begin=0,end=0;
         for (int i = 1; i < num.length; i++) {
             temp = num[i];
             if(temp.matches("[+-/()*]")){
                 if(temp.equals(".")) continue;
                 end = i - 1;  
                 temp = exp.substring(begin, end);
                 if(temp.trim().length() > 0 && temp.indexOf(".")<0)
                     num[i-1] = num[i-1]+".0";
                 begin = end + 1;
             }
         }
         return Arrays.toString(num).replaceAll("[\\[\\], ]", "");
    }
    
    /**
     * 获取 keyBoradClickListener
     * @return 返回 keyBoradClickListener
     */
    public OnKeyBoradClickListener getKeyBoradClickListener()
    {
        return keyBoradClickListener;
    }
    /**
     * 设置 keyBoradClickListener
     * @param 对keyBoradClickListener进行赋值
     */
    public void setKeyBoradClickListener(OnKeyBoradClickListener keyBoradClickListener)
    {
        this.keyBoradClickListener = keyBoradClickListener;
    }
    private void onResult(boolean isPay){
        String exp = sResult;
        if(exp==null || exp.trim().length()==0)
            return; 
        if(exp.endsWith("+")||exp.endsWith("×")){
            exp=exp.substring(0, exp.length()-1);
        }
        exp = exp.replaceAll("×", "*");
        if(keyBoradClickListener!=null){
            keyBoradClickListener.onResult(sResult, getRs(exp),isPay);
        }
    }
    public interface OnKeyBoradClickListener {
        /**
         * 每次键盘点击之后就促发此事件，exp表达式；result计算结果；isPay为true时促发付款动作
         * 
         * @author   罗洪祥
         * @version  V001Z0001
         * @date     2015年10月10日
         * @see  [相关类/方法]
         * @since  [产品/模块版本]
         */
        public void onResult(String exp, double value, boolean isPay);
    }
    public void setPlusButtonEnabled(boolean enabled){
        btnPlus.setEnabled(enabled);
        if(enabled){
            btnPlus.setBackgroundResource(R.drawable.btn_calculator);
        }else{
            btnPlus.setBackgroundResource(R.drawable.btn_calculator_pressed);
        }
    }
    
}
