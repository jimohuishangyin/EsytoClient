package com.ec2.yspay;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.adapter.PopupShopListAdapter;
import com.ec2.yspay.common.Constants;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.ScreenUtils;
import com.ec2.yspay.common.StaffManager;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;
import com.ec2.yspay.http.response.BooleanResponse;
import com.ec2.yspay.http.task.AddStaffTask;
import com.ec2.yspay.pay.card.MPosPay;

public class PersonalAddActivity extends BaseActivity implements OnClickListener
{
    private PopupWindow popMenu;
    private View layoutPopMenu;
    private ListView PopMenuList;
//    private List<ShopItem> mShopList = new ArrayList<ShopItem>();
    private String mShopCode,mShopName;
    private PopupShopListAdapter shopAdapter;
    private TextView tvShopName;
    private EditText etAccount,etUserName;
    private int shopPosition=0,dutyPosition=0;
    private RadioGroup rgDuty;
    private RadioButton rbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_add);
        mShopCode = getIntent().getStringExtra("shopCode");
        mShopName = getIntent().getStringExtra("shopName");
        tvShopName = (TextView)findViewById(R.id.tv_shop_name);
//        tvShopName.setOnClickListener(this);
        findViewById(R.id.btn_addstaff_send).setOnClickListener(this);
        etAccount = (EditText)findViewById(R.id.et_num);
        etUserName = (EditText)findViewById(R.id.et_name);
        rgDuty = (RadioGroup) findViewById(R.id.rg_duty);
        rbManager = (RadioButton) findViewById(R.id.rb_duty_manager);
        if(MyApplication.mDataCache.duty==Constants.DUTY_BOSS)
        	rbManager.setVisibility(View.VISIBLE);
        else
        	rbManager.setVisibility(View.GONE);
        tvShopName.setText(mShopName);
        rgDuty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				//根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton)PersonalAddActivity.this.findViewById(radioButtonId);
				//更新文本内容，以符合选中项
				String dutyName = rb.getText().toString();
				if("员工".equals(dutyName)){
					dutyPosition = 0;
				}else{
					dutyPosition = 1;
				}
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
        switch (v.getId())
        {
            case R.id.btn_addstaff_send:
                validMsg(true);
                break;
            default:
                break;
        }
    }
    private void validMsg(boolean isSendSms){
        String userName = etUserName.getText().toString().trim();
        String account = etAccount.getText().toString().trim();
        if(shopPosition<0){
            showToast("请选择门店");
            return;
        }else if(dutyPosition<0){
            showToast("请选择员工类型"
                + "");
            return;
        }else if(Toolkits.isStrEmpty(userName)){
            showToast("请输入用户姓名");
            return;
        }else if(Toolkits.isStrEmpty(account)){
            showToast("请输入员工手机号");
            return;
        }
        if(!Toolkits.isMobileNO(account)){
            showToast("请输入正确的手机号码");
            return;
        }
        int duty = 3;
        if(dutyPosition==1)
            duty = 2;
        addStaff(userName, mShopCode, account, duty, isSendSms);
    }
    private void addStaff(String userName,String shopCode,String account,int duty,boolean isSendSms){
    	final StaffItem item = new StaffItem(account, duty, "1", userName);
        AddStaffTask task = new AddStaffTask(mContext, userName, shopCode, account, duty+"", isSendSms);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
//                showToast("添加成功！");
//                finish();
            	StaffManager.getInstance(mContext).addStaff(item);
            	Intent intent = new Intent(mContext, PersonAddListActivity.class);
            	intent.putExtra("shopCode", mShopCode);
            	intent.putExtra("shopName", mShopName);
            	startActivity(intent);
            	finish();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                BooleanResponse response = (BooleanResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
}
