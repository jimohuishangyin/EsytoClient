package com.ec2.yspay.activity;

import java.io.Serializable;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinaums.mpos.app.UmsMposContext;
import com.ec2.yspay.AboutUsActivity;
import com.ec2.yspay.CompanyMainActivity;
import com.ec2.yspay.PersonalMsgActivity;
import com.ec2.yspay.R;
import com.ec2.yspay.ReportMainActivity;
import com.ec2.yspay.SettleAccountsActivity;
import com.ec2.yspay.StoreManagementActivity;
import com.ec2.yspay.common.AsyncImageLoader;
import com.ec2.yspay.common.DoubleClickExitUtils;
import com.ec2.yspay.common.Log;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.NetworkUtil;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.fragment.ReceiptFragment;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.response.GetMainPayStateResponse;
import com.ec2.yspay.http.response.LoginResponse;
import com.ec2.yspay.http.task.GetMainPayStateTask;
import com.ec2.yspay.http.task.LoginOutTask;
import com.ec2.yspay.pay.card.MPosPay;
import com.ec2.yspay.test.TestActivity;
import com.ec2.yspay.widget.DragLayout;
import com.ec2.yspay.widget.DragLayout.DragListener;
import com.ec2.yspay.widget.DragLayout.Status;
import com.ec2.yspay.widget.PopupDialog;
import com.ec2.yspay.widget.RoundImageView;

public class MainActivity extends FragmentActivity implements OnClickListener{
    private static final String TAG = "MainActivity";
    private Context mContext = this;
    public DrawerLayout drawerLayout;
    public ReceiptFragment receiptFragment;
    private RelativeLayout rlLeftBaobiao,rlLeftEquipmentManagement,rlLeftShanghu,rlLeftStaff,rlLeftStore,rlLeftShouru,rlLeftServer;
    private LinearLayout llPersonMsg;
    private TextView tvUserName,tvAccount,tvTotalMoney,tvTotalItems;
    private int paddingTop = 0;
    private DragLayout mSlidingMenu;
    private DoubleClickExitUtils mBackExitHelper;
    private RoundImageView rivTouxiang;
    private AsyncImageLoader asyImg = new AsyncImageLoader(this);
    private View viewLine;
    private TextView tvShanghu;
    private RelativeLayout rlTest;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "MainActivity onCreate!");
        paddingTop = Toolkits.dip2px(this, 45)+Toolkits.getStatusHeight(this);
        UmsMposContext.getInstance().init(this, UmsMposContext.TYPE_PHONE);
        initView();
        rlLeftBaobiao = (RelativeLayout)findViewById(R.id.rl_left_baobiao);
        rlLeftShanghu = (RelativeLayout)findViewById(R.id.rl_left_shanghu);
        rlLeftStaff = (RelativeLayout)findViewById(R.id.rl_left_staff);
        rlLeftStore = (RelativeLayout)findViewById(R.id.rl_left_store);
        rlLeftShouru = (RelativeLayout)findViewById(R.id.rl_left_shouru);
        rlLeftServer = (RelativeLayout)findViewById(R.id.rl_left_server);
        rlTest = (RelativeLayout)findViewById(R.id.rl_test);
        viewLine = findViewById(R.id.view_line_shanghu);
        tvShanghu = (TextView)findViewById(R.id.tv_shanghu_name);
//        rlLeftEquipmentManagement = (RelativeLayout)findViewById(R.id.equipment_management);
//        rlLeftEquipmentManagement.setOnClickListener(this);
        rlLeftBaobiao.setOnClickListener(this);
        rlLeftShanghu.setOnClickListener(this);
        rlLeftStaff.setOnClickListener(this);
        rlLeftShouru.setOnClickListener(this);
        rlLeftStore.setOnClickListener(this);
        rlLeftServer.setOnClickListener(this);
        llPersonMsg = (LinearLayout)findViewById(R.id.ll_personal_msg);
        llPersonMsg.setOnClickListener(this);
        rlTest.setOnClickListener(this);
//        rlTest.setVisibility(Log.dbgFlg?View.VISIBLE:View.GONE);
        findViewById(R.id.rl_about_us).setOnClickListener(this);
        findViewById(R.id.rl_exit).setOnClickListener(this);
        mBackExitHelper = new DoubleClickExitUtils(this);
        rivTouxiang = (RoundImageView)findViewById(R.id.riv_touxiang);
        MyApplication.addActivity(this);
        //商户管理，权限管理
        if(MyApplication.mDataCache.duty==1){//管理员
            
        }else if(MyApplication.mDataCache.duty==2){//店长
//            tvShanghu.setText("门店管理");
        	rlLeftServer.setVisibility(View.GONE);
        }else{//收银员
            viewLine.setVisibility(View.GONE);
            rlLeftStaff.setVisibility(View.GONE);
            rlLeftStore.setVisibility(View.GONE);
            rlLeftServer.setVisibility(View.GONE);
//            rlLeftStaff.setVisibility(View.GONE);
        }
    }
    
    /**
     * 重载方法
     */
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        Log.e(TAG, "MainActivity onResume!");
        tvUserName.setText(MyApplication.mDataCache.userName);
        asyImg.LoadImage(MyApplication.mDataCache.userImageUrl,MyApplication.mDataCache.userImage
            , rivTouxiang,getResources().getDrawable(R.drawable.default_portrait));
        if (!checkLoginState()){
            finish();
            return;
        }
    }
    private boolean checkLoginState(){
        if(!MyApplication.mDataCache.isLoginYet() ){
            ToastUtils.showLong(this,"用户信息已失效，请重新登录！");
            MyApplication.toLoginActivity(mContext);
            return false;
        }
        return true;
    }
//    private void initStatus(){
//        int sdkNum= android.os.Build.VERSION.SDK_INT;
//        if(sdkNum>=19){//4.4以上版本支持设置状态栏颜色
//            //透明状态栏  
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
//            //透明导航栏  
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//    }
    public void initView(){
        receiptFragment = new ReceiptFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction f_transaction = fragmentManager.beginTransaction();
        f_transaction.replace(R.id.main_content_frame, receiptFragment);
        f_transaction.commitAllowingStateLoss();
        tvUserName = (TextView)findViewById(R.id.tv_username);
        tvAccount = (TextView)findViewById(R.id.tv_account);
        tvTotalMoney = (TextView)findViewById(R.id.tv_total_money);
        tvTotalItems = (TextView)findViewById(R.id.tv_total_count);
        tvUserName.setText(MyApplication.mDataCache.userName);
        tvAccount.setText(MyApplication.mDataCache.UserPhoneNbr);
        mSlidingMenu = (DragLayout)findViewById(R.id.main_drawer_layout);
        mSlidingMenu.setDragListener(new DragListener() {
            @Override
            public void onOpen() {
                getPayState();
                receiptFragment.ShowHeadImage(true);
            }

            @Override
            public void onClose() {
//                shake();
            	receiptFragment.ShowHeadImage(false);
            }

            @Override
            public void onDrag(float percent) {
//                ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }
    private void getPayState(){
        GetMainPayStateTask task = new GetMainPayStateTask(mContext);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                GetMainPayStateResponse response = (GetMainPayStateResponse)obj;
                tvTotalMoney.setText(response.getTotalAmount());
                tvTotalItems.setText(response.getTotalItems());
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                
            }
        });
        task.execute();
    }
    /**
     * 重载方法
     */
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e(TAG, "MainActivity onDestroy!");
        MyApplication.removeActivity(this); 
    }

    /**
     * 重载方法
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.rl_left_baobiao:
                Intent intent2 = new Intent(mContext, ReportMainActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_left_shanghu:
//                if(MyApplication.mDataCache.duty==2){//店长
//                    Intent addIntent = new Intent(mContext, StoreManagementActivity.class);
//                    ShopItem shopItem = new ShopItem(MyApplication.mDataCache.shopName
//                        , MyApplication.mDataCache.shopCode);
//                    addIntent. putExtra("shopItem", (Serializable)shopItem);  
//                    startActivity(addIntent);
//                }else{
////                    Intent shanghuIntent = new Intent(mContext, BusinessManagementActivity.class);
//                	Intent shanghuIntent = new Intent(mContext, CompanyMainActivity.class);
//                    startActivity(shanghuIntent);
//                }
            	Intent shanghuIntent = new Intent(mContext, CompanyMainActivity.class);
                startActivity(shanghuIntent);
                break;
//            case R.id.equipment_management://设备管理
//            	new MPosPay(MainActivity.this).setupDevice();
//                
//                break;
            case R.id.ll_personal_msg:
                Intent personIntent = new Intent(mContext, PersonalMsgActivity.class);
                startActivity(personIntent);
                break;
            case R.id.rl_left_staff:
                Intent staffIntent = new Intent(mContext, PersonalManagerActivity.class);
                startActivity(staffIntent);
                break;
            case R.id.rl_left_store:
            	if(MyApplication.mDataCache.duty==2){//店长
            		ShopItem shopItem = new ShopItem(MyApplication.mDataCache.shopName
                            , MyApplication.mDataCache.shopCode);
    				Intent addIntent = new Intent(mContext, StoreDetailActivity.class);
                    addIntent.putExtra("shopItem", (Serializable)shopItem);  
                    startActivity(addIntent);
            	}else{
            		Intent storeIntent = new Intent(mContext, StoreManagerActivity.class);
                    startActivity(storeIntent);
            	}
            	
            	
            	break;
            case R.id.rl_about_us:
                Intent aboutIntent = new Intent(mContext, AboutUsActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.rl_left_shouru:
                Intent settleintent = new Intent(mContext, SettleAccountsActivity.class);
                startActivity(settleintent);
                break;
            case R.id.rl_left_server:
            	Intent serverIntent = new Intent(mContext,OpenServerActivity.class);
            	startActivity(serverIntent);
            	break;
            case R.id.rl_exit:
            	PopupDialog.Builder builder = new PopupDialog.Builder(this);
		        builder.setTitle("退出登陆");
		        builder.setEditEnabled(false);
		        builder.setMessage("确定退出登陆?");
		        builder.setNegativeButton("取消",
		                new android.content.DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int which) {

		                        dialog.dismiss();
		                    
		                        
		                    }
		                });
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                
		            	
		            	LoginOutRequrst();
		            	   dialog.dismiss();
		                
		            }
		        });

		        builder.create().show();
            	break;
            case R.id.rl_test:
            	Intent testIntent = new Intent(mContext, TestActivity.class);
            	startActivity(testIntent);
            	break;
        }
    }
    
    private void LoginOutRequrst(){

    	LoginOutTask loginOut=new LoginOutTask(mContext);
    	loginOut.setProgressVisiable(false);
    	loginOut.setProgressMsg("正在退出登陆请稍候．．．");
    	loginOut.setOnTaskFinished(new OnTaskFinished() {
			
			@Override
			public void onSucc(Object obj) {
				// TODO Auto-generated method stub
//				MyApplication.toLoginActivity(mContext);

        
			}
			
			@Override
			public void onFail(Object obj) {
//				if(NetworkUtil.isNetworkAvailable(mContext)){
//					LoginResponse response = (LoginResponse)obj;
//	                Toolkits.esytoLongToast(mContext,response.getResultDesc());
//				}else{
//					finish();
//				}
                
//				MyApplication.toLoginActivity(mContext);
				
			}
		});
    	loginOut.execute();
    	MyApplication.toLoginActivity(mContext);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() != KeyEvent.ACTION_UP) {
            // 如果主页侧边栏显示状态则先回复
            if (mSlidingMenu!=null && mSlidingMenu.getStatus()!=Status.Close) {
                mSlidingMenu.close();
                return true;
            }
            // 如果主页弹出框,则要先取消这个状态.
            if (receiptFragment!=null && receiptFragment.isWindowShow()) {
                receiptFragment.closeWindow();
                return true;
            }
            
            // 短时间内连按两次,就退出程序
            return mBackExitHelper.dispatchKeyEvent(event);
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {// 监听menu键
            return false;
        }
        try {
            return super.dispatchKeyEvent(event);
        } catch (Exception e) {
            return true;
        }
    }
}