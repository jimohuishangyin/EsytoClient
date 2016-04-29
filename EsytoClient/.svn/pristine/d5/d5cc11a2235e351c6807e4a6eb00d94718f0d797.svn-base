package com.ec2.yspay.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.ec2.yspay.R;
import com.ec2.yspay.common.Toolkits;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 * @author xiaanming
 *
 */
public class HalfRoundProgressBar extends View {
	/**
	 * 画笔对象的引用
	 */
	private Paint paint;
	
	/**
	 * 圆环的颜色
	 */
	private int roundColor;
	
	/**
	 * 圆环进度的颜色
	 */
	private int roundProgressColor;
	
	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int textColor;
	
	/**
	 * 中间进度百分比的字符串的字体
	 */
	private float textSize;
	
	/**
	 * 圆环的宽度
	 */
	private float roundWidth;
	
	/**
	 * 最大进度
	 */
	private int max;
	
	/**
	 * 当前进度
	 */
	private int progress;
	/**
	 * 是否显示中间的进度
	 */
	private boolean textIsDisplayable;
	
	/**
	 * 进度的风格，实心或者空心
	 */
	private int style;
	/**
	 * 支付方式间距
	 */
	private int textMargin1 = Toolkits.dip2px(getContext(), 10);
	private int textMargin2 = Toolkits.dip2px(getContext(), 70);
	private float radius ; //圆环的半径
	private String payName,payMoney;
	public static final int STROKE = 0;
	public static final int FILL = 1;
	
	public HalfRoundProgressBar(Context context) {
		this(context, null);
	}

	public HalfRoundProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public HalfRoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		paint = new Paint();

		
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundProgressBar);
		
		//获取自定义属性和默认值
		roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
		roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
		textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
		textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
		roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
		max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 1000);
		textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
		style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
		radius = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundRadius, 100);
		mTypedArray.recycle();
	}
	

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		/**
		 * 画最外层的大圆环
		 */
		int centre = getWidth()/2; //获取圆心的x坐标
		
		paint.setColor(roundColor); //设置圆环的颜色
		paint.setStyle(Paint.Style.STROKE); //设置空心
		paint.setStrokeWidth(roundWidth); //设置圆环的宽度
		paint.setAntiAlias(true);  //消除锯齿 
		canvas.drawCircle(centre, centre, radius, paint); //画出圆环
		
		/**
		 * 画进度百分比
		 */
		paint.setStrokeWidth(0); 
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT); //设置字体
		
		float textWidth1 = paint.measureText(payName); 
		paint.setColor(getResources().getColor(R.color.black));
		canvas.drawText(payName, centre - textWidth1 - textMargin1, centre - radius + textSize/2, paint); 
		
		
		float textWidth2 = paint.measureText(payMoney); 
		paint.setColor(roundProgressColor);
		canvas.drawText(payMoney, centre - textWidth2 - textMargin2, centre - radius + textSize/2, paint); 
		/**
		 * 画圆弧 ，画圆环的进度
		 */
		
		//设置进度是实心还是空心
		paint.setStrokeWidth(roundWidth); //设置圆环的宽度
		paint.setColor(roundProgressColor);  //设置进度的颜色
		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius);  //用于定义的圆弧的形状和大小的界限
		
		switch (style) {
		case STROKE:{
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, -90, 360 * progress / max, false, paint);  //根据进度画圆弧
			break;
		}
		case FILL:{
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if(progress !=0)
				canvas.drawArc(oval, -90, 360 * progress / max, true, paint);  //根据进度画圆弧
			break;
		}
		}
		
	}
	
	
	public synchronized int getMax() {
		return max;
	}

	/**
	 * 设置进度的最大值
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	/**
	 * 获取进度.需要同步
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
	 * 刷新界面调用postInvalidate()能在非UI线程刷新
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if(progress < 0){
			throw new IllegalArgumentException("progress not less than 0");
		}
		if(progress > max){
			progress = max;
		}
		if(progress <= max){
			this.progress = progress;
			postInvalidate();
		}
		
	}
	private int progress1;
	public void startAutoProgress(final int max){
		progress1 = 0;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(progress1 <= max){
					progress1 += 5;
					
					
					setProgress(progress1);
//					mRoundProgressBar2.setProgress(progress);
					try {
						Thread.sleep(13);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
	public int getCricleColor() {
		return roundColor;
	}

	public void setCricleColor(int cricleColor) {
		this.roundColor = cricleColor;
	}

	public int getCricleProgressColor() {
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.roundProgressColor = cricleProgressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}



}
