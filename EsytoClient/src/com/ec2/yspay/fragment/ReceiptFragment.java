package com.ec2.yspay.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ec2.yspay.CashCollectListActivity;
import com.ec2.yspay.OrderQueryActivity;
import com.ec2.yspay.R;
import com.ec2.yspay.activity.MainActivity;
import com.ec2.yspay.activity.PrintHomeActivity;
import com.ec2.yspay.activity.RefundTradingNumberActivity;
import com.ec2.yspay.adapter.PopupMenuAdapter;
import com.ec2.yspay.common.AsyncImageLoader;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.ChannelTypes;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.GetPayChanneResponse;
import com.ec2.yspay.http.task.GetPayChannelTask;
import com.ec2.yspay.pay.PayTypePopupWindow;
import com.ec2.yspay.widget.Calculator;
import com.ec2.yspay.widget.Calculator.OnKeyBoradClickListener;
import com.ec2.yspay.widget.DragLayout.Status;
import com.ec2.yspay.widget.DragLayout;
import com.ec2.yspay.widget.DragLayout.Status;
import com.ec2.yspay.widget.MyTitle;
import com.ec2.yspay.widget.PopupDialog;

/**
 * 收款
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月8日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReceiptFragment extends Fragment{
    private static final String TAG = "number";
    private Context mContext;
	private DragLayout mMenu;
    private View view;
    // 声明PopupWindow对象的引用  
    private PopupWindow popupWindow; 
    private LayoutInflater inflater;
    public ImageButton menuBtn,mesBtn;
    private MyTitle mTitle;
    private EditText rsText = null;  //显示器
    private TextView tvTotal;//总数
    private LinearLayout llNumber;
	private PopupWindow popMenu;
	private View layoutPopMenu;
	private ListView PopMenuList;
	PopupMenuAdapter adapter;
	private String mRemark="";
	private Calculator mCalculator;
	private Button btnRemark;
	private AsyncImageLoader asyImg;
	//支付方式
	private List<ChannelTypes> mChannelTypes = new ArrayList<ChannelTypes>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    this.inflater = inflater;
		view = inflater.inflate(R.layout.receipt_fragment, null);
		mContext = getActivity();
		getChannelTypeObj();
		initView();
		mMenu = (DragLayout) ((Activity) mContext).findViewById(R.id.main_drawer_layout);
		return view;
	}
	public void clearData(){
	    rsText.setText("0");
        tvTotal.setText("￥ 0.00");
        rsText.setSelection(rsText.getText().length());
	    mCalculator.clearData();
	    mRemark = "";
	    
	}
	//支付方式请求
	private void getChannelTypeObj(){
    	GetPayChannelTask task = new GetPayChannelTask(mContext);
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {
			
			@Override
			public void onSucc(Object obj) {
				GetPayChanneResponse response =(GetPayChanneResponse) obj;
				mChannelTypes = response.getChannelTypes();
			}
			
			@Override
			public void onFail(Object obj) {
				
			}
		});
		task.execute();
    }
	
	public void ShowHeadImage(boolean bool){
		if(bool){
			asyImg.LoadImage("","", mTitle.headImageView, getResources()
					.getDrawable(R.drawable.back));
		}else{
			asyImg.LoadImage(MyApplication.mDataCache.userImageUrl,
    				MyApplication.mDataCache.userImage, mTitle.headImageView, getResources()
    						.getDrawable(R.drawable.default_head));
		}
	}
    public void initView(){
		adapter = new PopupMenuAdapter(mContext); 
		asyImg = new AsyncImageLoader(mContext);
        mTitle = (MyTitle)view.findViewById(R.id.rl_top);
        mTitle.setLeftHeadOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	mMenu.toggle();
            }
        });
        mTitle.setRightOnclickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
            	clickPopupMenu();
            }
        });
        rsText = (EditText)view.findViewById(R.id.et_detail);
        tvTotal = (TextView)view.findViewById(R.id.tv_total);
        llNumber = (LinearLayout)view.findViewById(R.id.ll_number);
        
        mCalculator = (Calculator)view.findViewById(R.id.calculator);
        mCalculator.setPlusButtonEnabled(true);
        mCalculator.setKeyBoradClickListener(new OnKeyBoradClickListener()
        {
            @Override
            public void onResult(String exp, double value, boolean isPay)
            {
                // TODO Auto-generated method stub
                Log.i("onResult", "exp:"+exp+";value:"+value);
                if(isPay){//下单支付
                    if(value<=0){
			            ToastUtils.show(mContext, "请先输入金额");
                    }else{
                        try
                        {
                            PayTypePopupWindow payTypePopupWindow = new PayTypePopupWindow(getActivity(), inflater,mChannelTypes);
                            payTypePopupWindow.setMoney(value+"");
                            payTypePopupWindow.setRemark(mRemark);
                            popupWindow = payTypePopupWindow.getPopupWindow();
                            // 这里是位置显示方式,在屏幕的左侧  
                            popupWindow.showAtLocation(view.findViewById(R.id.ll_number), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                        
                        }
                        catch (Exception e)
                        {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                rsText.setText(exp);
                tvTotal.setText("￥ "+getFormatMoney(value));
                rsText.setSelection(rsText.getText().length());
            }
        });
        btnRemark = (Button)view.findViewById(R.id.btn_remark);
        btnRemark.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                addRemarkDialog();
            }
        });
        
    }
    private String getFormatMoney(double d){
        return Toolkits.doubleFormat(d); 
    }
    
	/**
	 * add remark popup dialog 
	 * 
	 * @param title
	 * @return
	 */
	private void addRemarkDialog() {

		final PopupDialog.Builder builder = new PopupDialog.Builder((MainActivity)getActivity());
		//builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
		builder.setTitle("收款备注");
		builder.setEditEnabled(true);
		builder.setMessage(mRemark);
		builder.setNegativeButton("重写",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						builder.setRemarkText("");
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mRemark = builder.getRemarkText();//这里取得备注信息并作处理
				dialog.dismiss();
			}
		});

		builder.create().show();

	}
	
    
    private void clickPopupMenu(){

		if (popMenu != null && popMenu.isShowing()) {
			popMenu.dismiss();
		} else {

			// 初始化数据项
			layoutPopMenu = ((Activity) mContext).getLayoutInflater().inflate(
					R.layout.pop_menulist, null);
			PopMenuList = (ListView) layoutPopMenu
					.findViewById(R.id.menulist);
			PopMenuList.setAdapter(adapter);

			// 点击listview中item的处理
			PopMenuList
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
//					    Toast.makeText(mContext, "点击处理"+arg2, Toast.LENGTH_SHORT).show();
    					    switch(arg2){
    				            case 0:
    				                Intent intent = new Intent(mContext, RefundTradingNumberActivity.class);
    				                intent.putExtra("title", "银行卡撤销");
                                    startActivity(intent);
    				                break;
    				            case 1:
    				                Intent intent1 = new Intent(mContext, RefundTradingNumberActivity.class);
    				                intent1.putExtra("title", "退款");
    				                startActivity(intent1);
    				                break;
    				            case 2://结账
    				                Intent intent2 = new Intent(mContext, PrintHomeActivity.class);
                                    startActivity(intent2);
    				                break;
    				            /*case 3://现金归集
    				                Intent intent3 = new Intent(mContext, CashCollectListActivity.class);
                                    startActivity(intent3);
                                    break;*/
    				            case 3://限额查询
    				                Intent intent4 = new Intent(mContext, OrderQueryActivity.class);
                                    startActivity(intent4);
                                    break;
    				        }    
							if (popMenu != null && popMenu.isShowing()) {
								popMenu.dismiss();
							}
						}
					});

	        final float scale = mContext.getResources().getDisplayMetrics().density; 
            popMenu = new PopupWindow(layoutPopMenu, Toolkits.dip2px(mContext, 185),
                   LayoutParams.WRAP_CONTENT);



			ColorDrawable cd = new ColorDrawable(-0000);
			popMenu.setBackgroundDrawable(cd);
			popMenu.setAnimationStyle(R.style.PopupMenuAnimation);
			popMenu.update();
			popMenu.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
			popMenu.setTouchable(true); // 设置popupwindow可点击
			popMenu.setOutsideTouchable(true); // 设置popupwindow外部可点击
			popMenu.setFocusable(true); // 获取焦点

			// 设置popupwindow的位置
			int topBarHeight = view.findViewById(R.id.rl_top).getBottom();
			popMenu.showAsDropDown(mTitle.getRightView(), 0,
					(topBarHeight - mTitle.getRightView().getHeight()) / 2);

			popMenu.setTouchInterceptor(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// 如果点击了popupwindow的外部，popupwindow也会消失
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						popMenu.dismiss();
						return true;
					}
					return false;
				}
			});
		}
    }
    
    public boolean isWindowShow(){
        if(popMenu!=null&&popMenu.isShowing()){
            return true;
        }
        if(popupWindow!=null&&popupWindow.isShowing())
            return true;
        return false;
    }
    public void closeWindow(){
        if(popMenu!=null)
            popMenu.dismiss();
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }
    
}