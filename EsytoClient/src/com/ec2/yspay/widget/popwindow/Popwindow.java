package com.ec2.yspay.widget.popwindow;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.City;
import com.ec2.yspay.common.CityManager;
import com.ec2.yspay.common.Province;
import com.ec2.yspay.widget.ScrollerNumberPicker;
import com.ec2.yspay.widget.ScrollerNumberPicker.OnSelectListener;

public class Popwindow {
	protected PopupWindow popupWindow;
	protected Activity mContext;
	public Popwindow(Activity mContext){
		this.mContext = mContext;
	}
	/**
	 * 弹框复用方法
	 * 
	 * @author 罗洪祥
	 * @version V001Z0001
	 * @date 2015年11月4日
	 * @see [相关类/方法]
	 * @since [产品/模块版本]
	 */
	protected void showPopupwindow(View outerView,View parent) {
		popupWindow = new PopupWindow(outerView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置PopupWindow弹出软键盘模式（此处为覆盖式）
		popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
		popupWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		// //产生背景变暗效果
		popupWindow.setBackgroundDrawable(dw);
		WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
		lp.alpha = 0.4f;
		mContext.getWindow().setAttributes(lp);
		// 设置SelectPicPopupWindow弹出窗体的背景
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
				lp.alpha = 1f;
				mContext.getWindow().setAttributes(lp);

				//
			}
		});
		popupWindow.showAtLocation(parent, Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	
}
