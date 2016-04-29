package com.ec2.yspay.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ec2.yspay.R;
import com.ec2.yspay.activity.ShopListViewActivity;
import com.ec2.yspay.common.DateUtils;
import com.ec2.yspay.common.ShopManager;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.ReportExpandableListItem;
import com.ec2.yspay.entity.ReportFormEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.StaffItem;
import com.ec2.yspay.http.response.GetStaffListResponse;
import com.ec2.yspay.http.response.ReportDetailsResponse;
import com.ec2.yspay.http.response.ReportFormResponse;
import com.ec2.yspay.http.task.GetStaffListTask;
import com.ec2.yspay.http.task.ReportStaffFormTask;
import com.ec2.yspay.http.task.ReportStaffTask;
import com.ec2.yspay.widget.MarqueeText;

/**
 * 日报表
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月8日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportPersonFragment extends Fragment implements OnClickListener{
    private static final String TAG = "number";
    private Context mContext;
    private View view;
    private LayoutInflater inflater;
    private TabHost tabHost;
    private RelativeLayout rlTabLeft,rlTabRight;
    Fragment frag; 
    private TextView tvMingxi,tvBaobiao;
    private int mYear,mMonth,mDay;  
    private MarqueeText tvName;
    private RelativeLayout llDate;
    private ReportTabRightFragment rightFragment;
    private ReportTabLeftFragment leftFragment;
    private TextView tvMoney,tvCount;
    private List<StaffItem> staffList;
    private String[] nameStrs ; 
    private RelativeLayout rlShopLayout;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_report_person, null);
        mContext = getActivity();
        mYear = DateUtils.getYear();
        mMonth = DateUtils.getMonth();
        mDay = DateUtils.getDay();
        rlTabLeft = (RelativeLayout)view.findViewById(R.id.rl_tab_left);
        rlTabRight = (RelativeLayout)view.findViewById(R.id.rl_tab_right);
        tvMoney = (TextView)view.findViewById(R.id.tv_money);
        tvCount = (TextView)view.findViewById(R.id.tv_count);
        rlShopLayout = (RelativeLayout) view.findViewById(R.id.rl_shop);
        rlShopLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, ShopListViewActivity.class);
				startActivityForResult(intent, 101);
			}
		});
        rlTabLeft.setOnClickListener(this);
        rlTabRight.setOnClickListener(this);
        tvName = (MarqueeText)view.findViewById(R.id.tv_name);
        llDate = (RelativeLayout)view.findViewById(R.id.ll_date);
        llDate.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                showMonPicker();
            }
        });
        tvMingxi = (TextView)view.findViewById(R.id.tv_mingxi);
        tvBaobiao = (TextView)view.findViewById(R.id.tv_baobiao);
        leftFragment = new ReportTabLeftFragment();
        rightFragment = new ReportTabRightFragment();
        tabHost = (TabHost)view.findViewById(android.R.id.tabhost);
        //调用 TabHost.setup()
        tabHost.setup();
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {  
            
            @Override  
            public void onTabChanged(String tabId) {  
                FragmentTransaction ft = getFragmentManager().beginTransaction();  
                Fragment frag = null;
                if (TextUtils.equals("first", tabId)) {  
                    //add/replace fragment first  
                    frag = leftFragment;  
                } else if (TextUtils.equals("second", tabId)) {  
                    //add/replace fragment second  
                    frag = rightFragment;  
                }
                ft.replace(android.R.id.tabcontent, frag, "frag");  
                ft.commit();
            }  
        });  
        tabHost.addTab(tabHost.newTabSpec("first").setIndicator("First")  
                .setContent(new DummyTabFactory(mContext)));  
        tabHost.addTab(tabHost.newTabSpec("second").setIndicator("Second")//setIndicator 设置标签样式  
                .setContent(new DummyTabFactory(mContext))); //setContent 点击标签后触发  
        // tabHost.setCurrentTab(0);//zzm设置默认值ＴＡＢ
        onClick(view.findViewById(R.id.rl_tab_right));//zzm设置默认值ＴＡＢ
//        getDayDetails();
        getAccountInfos();
        return view;
    }
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年11月2日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    private void getAccountInfos()
    {
        // TODO Auto-generated method stub
        GetStaffListTask task = new GetStaffListTask(mContext);
        task.setProgressVisiable(true);
        task.setShopCode(ShopManager.getInstance(mContext).getCurrentShopCode());
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                GetStaffListResponse response = (GetStaffListResponse)obj;
                staffList = response.getStaffList();
                nameStrs = new String[staffList.size()];
                for(int i = 0;i<staffList.size();i++){
                    StaffItem item = staffList.get(i);
                    if(Toolkits.isStrEmpty(item.getUserName())){
                        nameStrs[i]="未定义";
                    }else{
                        nameStrs[i]=item.getUserName();
                    }
                }
                tvName.setText(nameStrs[0]);
                getDayDetails(0);
                getFormMsg(0);
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                GetStaffListResponse response = (GetStaffListResponse)obj;
	            ToastUtils.show(mContext, response.getResultDesc());
            }
        });
        task.execute();
    }
    static class DummyTabFactory implements TabHost.TabContentFactory {  
        private Context context;  
        public DummyTabFactory(Context ctx) {  
            this.context = ctx;  
        }  
        @Override  
        public View createTabContent(String tag) {//创建宽高均为0的view   
            View v = new ImageView(context);  
            v.setMinimumWidth(0);  
            v.setMinimumHeight(0);  
            return v;  
        }  
          
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
            case R.id.rl_tab_left:
                tvMingxi.setTextColor(getResources().getColor(R.color.white));
                tvBaobiao.setTextColor(getResources().getColor(R.color.text_gray));
                rlTabLeft.setBackgroundColor(getResources().getColor(R.color.report_purple));
                rlTabRight.setBackgroundColor(getResources().getColor(R.color.white));
                tabHost.setCurrentTab(0);
                break;
            case R.id.rl_tab_right:
                tvMingxi.setTextColor(getResources().getColor(R.color.text_gray));
                tvBaobiao.setTextColor(getResources().getColor(R.color.white));
                rlTabLeft.setBackgroundColor(getResources().getColor(R.color.white));
                rlTabRight.setBackgroundColor(getResources().getColor(R.color.report_purple));
                tabHost.setCurrentTab(1);
                break;
            default:
                break;
        }
    } 
    private RadioOnClick radioOnClick = new RadioOnClick(1); 
    public void showMonPicker() {
        AlertDialog ad =new AlertDialog.Builder(mContext).setTitle("选择用户")  
            .setSingleChoiceItems(nameStrs,radioOnClick.getIndex(),radioOnClick).create();  
            ad.show();
    }
    class RadioOnClick implements DialogInterface.OnClickListener{  
        private int index;  
        
        public RadioOnClick(int index){  
         this.index = index;  
        }  
        public void setIndex(int index){  
         this.index=index;  
        }  
        public int getIndex(){  
         return index;  
        }
        /**
         * 重载方法
         * @param dialog
         * @param which
         */
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            // TODO Auto-generated method stub
            index = which;
//            Toast.makeText(mContext, "您已经选择了： " + index + ":" + nameStrs[index], Toast.LENGTH_LONG).show();  
            dialog.dismiss();  
            tvName.setText(nameStrs[index]);
            getFormMsg(index);
            getDayDetails(index);
            
        }  
    }
    private void getDayDetails(int position){
        ReportStaffTask task = new ReportStaffTask(mContext,staffList.get(position).getAccount());
        task.setProgressVisiable(true);
        task.setShopCode(ShopManager.getInstance(mContext).getCurrentShopCode());
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                ReportDetailsResponse response = (ReportDetailsResponse)obj;
                tvMoney.setText(response.getTotalAmount());
                tvCount.setText(response.getTotalItems());
                List<ReportExpandableListItem> mList = new ArrayList<ReportExpandableListItem>();
                ReportExpandableListItem item = new ReportExpandableListItem(
                    mYear,mMonth,mDay,response.getOrderList());
                mList.add(item);
                leftFragment.setList(mList);
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                ReportDetailsResponse response = (ReportDetailsResponse)obj;
	            ToastUtils.show(mContext, response.getResultDesc());
            }
        });
        task.execute();
    }
    private void getFormMsg(int position){
        rightFragment.setIsLoading();
        ReportStaffFormTask task = new ReportStaffFormTask(mContext, staffList.get(position).getAccount());
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                ReportFormResponse response = (ReportFormResponse)obj;
                ReportFormEntity formEntity = new ReportFormEntity();
                formEntity.setFormList(response.getCollectInfos());
                if(tabHost.getCurrentTab()==1){
                    rightFragment.setFormEntieyUpdate(formEntity);
                }else{
                    rightFragment.setFormEntiey(formEntity);
                }
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                rightFragment.setNoData();
                ReportFormResponse response = (ReportFormResponse)obj;
	            ToastUtils.show(mContext, response.getResultDesc());
            }
        });
        task.execute();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==101&&resultCode==100){
    		
    	}
    }
}
