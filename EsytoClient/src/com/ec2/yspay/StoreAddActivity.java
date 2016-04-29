package com.ec2.yspay;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.task.AddStoreTask;
import com.ec2.yspay.widget.MyTitle;
import com.ec2.yspay.widget.popwindow.CityPopwindow;
import com.ec2.yspay.widget.popwindow.CityPopwindow.OnCitySelectedListener;

public class StoreAddActivity extends BaseActivity implements
	OnClickListener
{
    private String province,city;
    private TextView tvShopCity;
    private TextView etShopName,etAddress,etPhone;
    private PopupWindow popupWindow;
    private MyTitle mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_add);
        etShopName = (TextView)findViewById(R.id.tv_store_name);
        etAddress = (TextView)findViewById(R.id.tv_shop_address);
        etPhone = (TextView)findViewById(R.id.tv_shop_phone);
        tvShopCity = (TextView)findViewById(R.id.tv_store_city);
        tvShopCity.setOnClickListener(this);
        etShopName.setOnClickListener(this);
        etAddress.setOnClickListener(this);
        etPhone.setOnClickListener(this);
        mTitle = (MyTitle) findViewById(R.id.rl_top);
        mTitle.setRightOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isValid()){
                    addStore();
                }
			}
		});
    }
    private boolean isValid(){
        if(Toolkits.isStrEmpty(etShopName.getText().toString())){
            showToast("请输入门店名称！");
            return false;
        }
        if(Toolkits.isStrEmpty(etAddress.getText().toString())){
            showToast("请输入地址！");
            return false;
        }
        if(Toolkits.isStrEmpty(etPhone.getText().toString())){
            showToast("请输入联系电话！");
            return false;
        }
        if(Toolkits.isStrEmpty(province)){
            showToast("请选择区域！");
            return false;
        }
        return true;
    }
    /**
     * 重载方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 101://城市列表
                province = data.getStringExtra("province");
                city = data.getStringExtra("city");
                tvShopCity.setText(province+"/"+city);
                break;
            }
    }
    private void addStore(){
        AddStoreTask task = new AddStoreTask(mContext);
        task.setAddress(etAddress.getText().toString());
        task.setCity(city);
        task.setProvince(province);
        task.setPhone(etPhone.getText().toString());
        task.setShopName(etShopName.getText().toString());
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                showToast("门店添加成功！");
                finish();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                BooleanResponse response = (BooleanResponse)obj;
                showToastLong(response.getResultDesc());
            }
        });
        task.execute();
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_store_city:
			showCityPopwindow();
			break;
		case R.id.tv_store_name:
			showNamePopwindow("请输入门店名称", 0);
			break;
		case R.id.tv_shop_address:
			showNamePopwindow("请输入门店地址", 1);
			break;
		case R.id.tv_shop_phone:
			showPhonePopwindow();
			break;
		default:
			break;
		}
	}
	/**
	 * 单行名称修改 state:0 门店名称,1 门店地址；
	 * 
	 * @author 罗洪祥
	 * @version V001Z0001
	 * @date 2015年11月5日
	 * @see [相关类/方法]
	 * @since [产品/模块版本]
	 */
	private void showNamePopwindow(String hint, final int state) {
		View outerView = LayoutInflater.from(mContext).inflate(
				R.layout.layout_pop_update_name, null);
		final EditText tvName = (EditText) outerView
				.findViewById(R.id.et_update_name);
		tvName.setHint(hint);
		if(state==0){
			tvName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)}); 
		}else if(state==1){
			tvName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)}); 
		}
		TextView tvSure = (TextView) outerView.findViewById(R.id.tv_sure);
		TextView tvCancel = (TextView) outerView.findViewById(R.id.tv_cancel);
		tvSure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!tvName.getText().toString().isEmpty()) {
					if (state == 0) {
						etShopName.setText(tvName.getText().toString());
					} else if (state == 1) {
						etAddress.setText(tvName.getText().toString());
					}
					popupWindow.dismiss();
				} else {
					showToast("内容不能为空");
				}
			}
		});
		tvCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		showPopupwindow(outerView);
	}

	/**
	 * 电话号码修改
	 * 
	 * @author 罗洪祥
	 * @version V001Z0001
	 * @date 2015年11月5日
	 * @see [相关类/方法]
	 * @since [产品/模块版本]
	 */
	private void showPhonePopwindow() {
		View outerView = LayoutInflater.from(mContext).inflate(
				R.layout.layout_pop_update_name, null);
		final EditText tvName = (EditText) outerView
				.findViewById(R.id.et_update_name);
		tvName.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_VARIATION_NORMAL);
		InputFilter[] filters = { new InputFilter.LengthFilter(12) };
		tvName.setFilters(filters);
		tvName.setHint("请输入联系电话");
		TextView tvSure = (TextView) outerView.findViewById(R.id.tv_sure);
		TextView tvCancel = (TextView) outerView.findViewById(R.id.tv_cancel);
		tvSure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!tvName.getText().toString().isEmpty()) {
					etPhone.setText(tvName.getText().toString());
					popupWindow.dismiss();
				} else {
					showToast("内容不能为空");
				}

			}
		});
		tvCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		showPopupwindow(outerView);
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
	private void showPopupwindow(View outerView) {
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
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		// 设置SelectPicPopupWindow弹出窗体的背景
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);

				//
			}
		});
		popupWindow.showAtLocation(findViewById(R.id.ll_main), Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	private void showCityPopwindow(){
    	CityPopwindow cityTest = new CityPopwindow(this);
    	cityTest.setOnCitySelectedListener(new OnCitySelectedListener() {
			
			@Override
			public void onResult(String city1, String province1) {
				province = province1;
                city = city1;
                tvShopCity.setText(province+"/"+city);
			}
		});
    	cityTest.showCityPopwindow(findViewById(R.id.ll_main));
    }
}
