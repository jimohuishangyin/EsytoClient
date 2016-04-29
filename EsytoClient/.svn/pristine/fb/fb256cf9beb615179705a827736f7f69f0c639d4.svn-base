package com.ec2.yspay.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.ec2.yspay.R;
/**
 * 撤销与退款选择支付方式自定义按钮
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月16日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RefundLinearLayoutButton extends LinearLayout{
	
	private Context context;
	private TextView tvTitle;
	private ImageView ivIcon;
	private ImageView ivArrow;
	private RelativeLayout llParent;
	private OnClickListener mOnclckListener;
	private int imgIdIconNormal,imgIdIconPressed;
	
	public RefundLinearLayoutButton(Context context){
		super(context);
		this.context = context;
        control();
	}
	public RefundLinearLayoutButton(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public RefundLinearLayoutButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		control();
		// 获得自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LlBtn);
 
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = ta.getIndex(i); // 内容
            switch (itemId) {
            case R.styleable.LlBtn_btnContent: 
            	String name = ta.getString(itemId);
            	tvTitle.setText(name);
//                currentState = ta.getBoolean(itemId, false);
                break;
            case R.styleable.LlBtn_btnIcon: // 图标
                imgIdIconNormal = ta.getResourceId(itemId, -1);
                if (imgIdIconNormal == -1)
                    throw new RuntimeException("资源没有被找到，请设置图片");
                ivIcon.setImageResource(imgIdIconNormal);
                break;
            case R.styleable.LlBtn_btnIconPessed: // 图标
                imgIdIconPressed = ta.getResourceId(itemId, -1);
                if (imgIdIconPressed == -1)
                    throw new RuntimeException("资源没有被找到，请设置图片");
//                ivIcon.setImageResource(imgIdIconPressed);
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
        setListener();
        
    }
    private float y1=0;
    private float y2=0;
    private void setListener(){
        llParent.setOnTouchListener(new OnTouchListener()
        {
            
            @Override
            public boolean onTouch(View v, MotionEvent mEvent)
            {
                // TODO Auto-generated method stub
                
                switch (v.getId()) {  
                    case R.id.ll_parent:  
                    if(mEvent.getAction() == MotionEvent.ACTION_DOWN) {  
                        y1 = mEvent.getY(); 
                        llParent.setBackgroundResource(R.drawable.tkcx_bg948_144_over);
//                        tvTitle.setTextColor(context.getResources().getColor(R.color.text_black));
//                        ivIcon.setImageResource(imgIdIconPressed);
                    } else if(mEvent.getAction() == MotionEvent.ACTION_UP){  
                        llParent.setBackgroundResource(R.drawable.tkcx_bg948_144);
//                        tvTitle.setTextColor(context.getResources().getColor(R.color.text_gray));
//                        ivIcon.setImageResource(imgIdIconNormal);
                        y2 = mEvent.getY(); 
                        if(Math.abs(y1 - y2) <30){  // onclick事件
//                            Toast.makeText(context, "222222", 2).show();
                            onclick();
                        } 
                    }
                    return true;  
                   default:  
                     return true;  
                }  
            }
        });
        
    }
	
	/**
     * 初始化view
     */
    private void initView(){
        View view= LayoutInflater.from(context).inflate(R.layout.widget_llbtn_refund, null);
        llParent =(RelativeLayout)view.findViewById(R.id.ll_parent);
        ivIcon =(ImageView)view.findViewById(R.id.iv_icon);
        ivArrow = (ImageView)view.findViewById(R.id.iv_arrow_right);
        tvTitle =(TextView)view.findViewById(R.id.tv_content);
//        addView(view);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);//45dp
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
    
    private void onclick(){
        
        if(mOnclckListener!=null)
            mOnclckListener.onClick(this);
    }
    public void setBtnOnclickListener(OnClickListener listener){
        this.mOnclckListener = listener;
    }

}
