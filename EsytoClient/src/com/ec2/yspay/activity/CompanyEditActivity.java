package com.ec2.yspay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ec2.yspay.CityListActivity;
import com.ec2.yspay.R;
import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.GetCompanyMsgResponse;
import com.ec2.yspay.http.response.UpdateCompanyMsgResponse;
import com.ec2.yspay.http.task.GetCompanyMsgTask;
import com.ec2.yspay.http.task.UpdateCompanyMsgTask;

public class CompanyEditActivity extends BaseActivity
{
    private TextView etCompanyName,etCompanyAddr,etCompanyCity,etPhone,etUserName;
    private String companyName;
    private String companyCode;
    private String userName;
    private String compPhone;
    private String compAddress;
    private String province;
    private String city;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_edit);
        etCompanyName = (TextView)findViewById(R.id.et_company_name);
        etCompanyAddr = (TextView)findViewById(R.id.et_company_addr);
        etCompanyCity = (TextView)findViewById(R.id.et_company_city);
        etPhone = (TextView)findViewById(R.id.et_phone_num);
        etUserName = (TextView)findViewById(R.id.et_user_name);
        Intent intent = getIntent();
        companyName = intent.getStringExtra("companyName");
        companyCode = intent.getStringExtra("companyCode");
        userName = intent.getStringExtra("userName");
        compPhone = intent.getStringExtra("compPhone");
        compAddress = intent.getStringExtra("compAddress");
        province = intent.getStringExtra("province");
        city = intent.getStringExtra("city");
        etCompanyName.setText(companyName);
        etUserName.setText(userName);
        etCompanyAddr.setText(compAddress);
        etCompanyCity.setText(province+"/"+city);
        etCompanyCity.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent cityIntent = new Intent(mContext, CityListActivity.class);
                startActivityForResult(cityIntent, 101);
            }
        });
        etPhone.setText(compPhone);
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
                etCompanyCity.setText(province+"/"+city);
                break;
            }
    }
    private void updateCompanyMsg(){
        UpdateCompanyMsgTask task = new UpdateCompanyMsgTask(mContext);
        task.setCompanyName(etCompanyName.getText().toString());
        task.setCompAddress(etCompanyAddr.getText().toString());
        task.setProvince(province);
        task.setCity(city);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                UpdateCompanyMsgResponse response = (UpdateCompanyMsgResponse)obj;
                showToast("修改成功！");
                finish();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                UpdateCompanyMsgResponse response = (UpdateCompanyMsgResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
    private void getCompanyMsg(){
        GetCompanyMsgTask task = new GetCompanyMsgTask(mContext);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                GetCompanyMsgResponse response = (GetCompanyMsgResponse)obj;
                companyName = response.getCompanyName();
                companyCode = response.getCompanyCode();
                compAddress = response.getCompAddress();
//                userName = response.getShopName();
                compPhone = response.getCompPhone();
                province = response.getProvince();
                city = response.getCity();
                
//                tvCompanyName.setText(response.getCompanyName());
//                tvCompanyCode.setText(response.getCompanyCode());
//                tvCompanyAddr.setText(response.getCompAddress());
//                tvShopName.setText(response.getShopName());
//                tvPhone.setText(response.getCompPhone());
//                tvCompanyCity.setText(response.getProvince()+"/"+response.getCity());
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                GetCompanyMsgResponse response = (GetCompanyMsgResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.setProgressVisiable(true);
        task.execute();
    }
    public void onclick_edit(View v){
        updateCompanyMsg();
    }
    
}
