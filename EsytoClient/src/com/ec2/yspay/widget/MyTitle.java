package com.ec2.yspay.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.AsyncImageLoader;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;


public class MyTitle extends RelativeLayout{
	
	private Context context;
	private Bitmap bmLeft,bmRight;
	private ImageView ivLeft;
	private ImageView ivRight;
	public HalfRoundImageView headImageView;
	private TextView tvTitle,tvRight;
	private boolean isShowLeft,isShowRight,isShowLeftHead;
	private OnClickListener mLeftOnclckListener,mRightOnclckListener,mLeftHeadOnclckListener;
	private AsyncImageLoader asyImg;
	public MyTitle(Context context){
		super(context);
		this.context = context;
        control();
	}
	public MyTitle(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		control();
		// 获得自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTitle);
 
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = ta.getIndex(i); // 获取某个属性的Id值
            switch (itemId) {
            case R.styleable.MyTitle_titleName: // 设置当前按钮的状态
            	String name = ta.getString(itemId);
            	tvTitle.setText(name);
//                currentState = ta.getBoolean(itemId, false);
                break;
            case R.styleable.MyTitle_rightText: // 设置当前按钮的状态
            	String name2 = ta.getString(itemId);
            	tvRight.setText(name2);
//                currentState = ta.getBoolean(itemId, false);
                break;
            case R.styleable.MyTitle_srcTitleLeft: // 左边图标
                int titleLeftId = ta.getResourceId(itemId, -1);
                if (titleLeftId == -1)
                    throw new RuntimeException("资源没有被找到，请设置图片");
                ivLeft.setImageResource(titleLeftId);
                break;
            case R.styleable.MyTitle_srcTitleRight: // 右边图标
                int titleRightId = ta.getResourceId(itemId, -1);
                if (titleRightId == -1)
                    throw new RuntimeException("资源没有被找到，请设置图片");
                ivRight.setImageResource(titleRightId);
                break;
            case R.styleable.MyTitle_srctitleLeftHead:
            	int titleLeftHeadId = ta.getResourceId(itemId, -1);
            	if (titleLeftHeadId == -1)
                    throw new RuntimeException("资源没有被找到，请设置图片");
            	asyImg.LoadImage(MyApplication.mDataCache.userImageUrl,
        				MyApplication.mDataCache.userImage, headImageView, getResources()
        						.getDrawable(R.drawable.default_head));
            	break;
            case R.styleable.MyTitle_isShowLeft:
            	isShowLeft = ta.getBoolean(itemId, false);
            	if(isShowLeft)
            		ivLeft.setVisibility(View.VISIBLE);
            	else
            		ivLeft.setVisibility(View.GONE);
            	break;
            	
            case R.styleable.MyTitle_isShowLeftHead:
            	isShowLeftHead = ta.getBoolean(itemId, false);
            	if(isShowLeftHead)
            		headImageView.setVisibility(VISIBLE);
            	else
            		headImageView.setVisibility(GONE);
            	break;
            case R.styleable.MyTitle_isShowRight:
            	isShowRight = ta.getBoolean(itemId, true);
            	if(isShowRight){
            		ivRight.setVisibility(View.VISIBLE);
            		tvRight.setVisibility(View.VISIBLE);
            	}
            	else{
            		ivRight.setVisibility(View.GONE);
            		tvRight.setVisibility(View.GONE);
            	}
            	break;
            default:
                break;
            }
        }
	}
	/**
     * 初始化
     */
    private void control(){
        initView();
        setViewListener();
    }
	private void setViewListener() {
		// TODO Auto-generated method stub
		ivLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mLeftOnclckListener!=null)
					mLeftOnclckListener.onClick(v);
				else{
					((Activity)context).finish();
				}
					
			}
		});
		ivRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mRightOnclckListener!=null)
					mRightOnclckListener.onClick(v);
			}
		});
		headImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mLeftHeadOnclckListener!=null)
					mLeftHeadOnclckListener.onClick(v);
			}
		});
		tvRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mRightOnclckListener!=null)
					mRightOnclckListener.onClick(v);
			}
		});
	}
	/**
     * 初始化view
     */
    private void initView(){
        View view= LayoutInflater.from(context).inflate(R.layout.top_bar, null);
        asyImg = new AsyncImageLoader(context);
        ivLeft =(ImageView)view.findViewById(R.id.ivTitleLeft);
        ivRight =(ImageView)view.findViewById(R.id.ivTitleRight);
        tvTitle =(TextView)view.findViewById(R.id.tvTitle);
        tvRight = (TextView) view.findViewById(R.id.ivTitleTextRight);
        headImageView = (HalfRoundImageView) view.findViewById(R.id.ivTitleLeftHead);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, Toolkits.dip2px(context, 45));//45dp
        params.setMargins(0, 0, 0, 0);
        addView(view, params);
        setPadding(0, 0, 0, 0);
    }
    /**
     * @Description: 设置标题文字
     * @author   罗洪祥
     * @date     2015年9月16日
     * @param @param title
     * @return void
     * @throws
     */
    public void setTitleText(String title){
    	if(tvTitle!=null)
    		tvTitle.setText(title);
    }
    /**
     * @Description: 是否显示左边的返回按钮，默认不显示
     * @author   罗洪祥
     * @date     2015年9月16日
     * @param @param show
     * @return void
     * @throws
     */
    public void showLeft(boolean show){
    	if(show)
    		ivLeft.setVisibility(View.VISIBLE);
    	else
    		ivLeft.setVisibility(View.GONE);
    }
    
    public void showChance(){
    	
    }
    
    public void setLeftOnclickListener(OnClickListener clickListener){
    	this.mLeftOnclckListener = clickListener;
    }
    public void setLeftHeadOnclickListener(OnClickListener clickListener){
    	this.mLeftHeadOnclckListener = clickListener;
    }
    public void setRightOnclickListener(OnClickListener clickListener){
    	this.mRightOnclckListener = clickListener;
    }
    public View getRightView(){
    	return ivRight;
    }
    public void setRightViewVisible(int visible){
    	tvRight.setVisibility(visible);
    }
    public void setRightText(String text){
    	tvRight.setText(text);
    }
}
