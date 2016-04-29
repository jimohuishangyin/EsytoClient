package com.ec2.yspay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import bluetooth.sdk.BluetoothManager;

import com.ec2.yspay.DeviceConnectActivity;
import com.ec2.yspay.R;
import com.ec2.yspay.R.id;
import com.ec2.yspay.R.layout;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.PrintDeviceTools;
import com.ec2.yspay.entity.PrintOne;
import com.ec2.yspay.entity.PrintOrderEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.PrintLastOneResponse;
import com.ec2.yspay.http.task.PrintLastOneTask;
import com.ec2.yspay.print.PrintManager;
import com.ec2.yspay.widget.PrintLinearLayoutButton;

public class PrintHomeActivity extends BaseActivity implements OnClickListener
{
    private PrintLinearLayoutButton btnPrintLast,btnPrintOne,btnPrintList,btnPrintAll,btnPrintDetail;
    private PrintManager printManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_home);
        
        btnPrintLast = (PrintLinearLayoutButton)findViewById(R.id.llbtn_print_last);
        btnPrintOne = (PrintLinearLayoutButton)findViewById(R.id.llbtn_print_one);
        btnPrintList = (PrintLinearLayoutButton)findViewById(R.id.llbtn_print_list);
        btnPrintAll = (PrintLinearLayoutButton)findViewById(R.id.llbtn_print_all);
        btnPrintDetail = (PrintLinearLayoutButton)findViewById(R.id.llbtn_print_detail);
        btnPrintLast.setBtnOnclickListener(this);
        btnPrintOne.setBtnOnclickListener(this);
        btnPrintList.setBtnOnclickListener(this);
        btnPrintAll.setBtnOnclickListener(this);
        btnPrintDetail.setBtnOnclickListener(this);
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	printManager = PrintManager.getInstance(mContext);
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
            case R.id.llbtn_print_last:
                getLastOneMsg();
                break;
            case R.id.llbtn_print_one:
                Intent oneIntent = new Intent(mContext, PrintTradingNumberActivity.class);
                startActivity(oneIntent);
                break;
            case R.id.llbtn_print_list:
                Intent listIntent = new Intent(mContext, PrintListActivity.class);
                startActivity(listIntent);
                break;
            case R.id.llbtn_print_all:
                Intent pwdIntent = new Intent(mContext, PrintAllTogetherActivity.class);
                startActivity(pwdIntent);
                break;
            case R.id.llbtn_print_detail:
                Intent detIntent = new Intent(mContext, PrintDetailActivity.class);
                startActivity(detIntent);
                break;
            default:
                break;
        }
    }
    private void getLastOneMsg(){
        PrintLastOneTask task = new PrintLastOneTask(mContext);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
            	PrintLastOneResponse response = (PrintLastOneResponse)obj;
                PrintOrderEntity mPrintOrderEntity = response.getPrintMsg();
                PrintOrderEntity entity = new PrintOrderEntity(mPrintOrderEntity.getTransactionId(), mPrintOrderEntity.getOrderId(),
                        mPrintOrderEntity.getOrderTime(), mPrintOrderEntity.getAccount(), mPrintOrderEntity.getAmount(),
                        mPrintOrderEntity.getRemark(), mPrintOrderEntity.getShopName(), mPrintOrderEntity.getChannelType());
                    entity.setAccount(MyApplication.mDataCache.UserPhoneNbr);
                    entity.setShopName(MyApplication.mDataCache.shopName);
                    printManager.setmPrintOrderEntity(entity);
                    printManager.printOneDetailNew();
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                PrintLastOneResponse response = (PrintLastOneResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.setProgressVisiable(true);
        task.execute();
    }
    private void printDetail() {
        
    }
    
    private void printLast(){
        PrintDeviceTools tools = PrintDeviceTools.getInstance(mContext);
        if (!tools.isConnected()) 
        {
            showToast("请先连接打印机！");
            Intent intent = new Intent(mContext, DeviceConnectActivity.class);
            startActivity(intent);
        }else{
            tools.printBig("   收银凭据");
            tools.printNormal("\n商户名：家乐福超市");
            tools.printNormal("商户号：007");
            tools.printNormal("分  店：明发商业广场");
            tools.printNormal("操作员：罗洪祥");
            tools.printNormal("...............................");
            tools.printNormal("订单编号：20151022000000008");
            tools.printNormal("付款类型：微信支付");
            tools.printNormal("交易单号：20151022000000008123");
            tools.printNormal("订单金额：650元");
            tools.printNormal("实付金额：560元");
            tools.printNormal("找零：");
            tools.printNormal("...............................");
            String tempStr = "13 51 00 18 15 02 FE A3 F8 82 3A 08 BA D2 E8 BA 02 E8 BA 1A E8 82 FA 08 FE AB F8 00 48 00 A3 21 28 5C 56 D8 0A DC D0 CD F5 60 B3 D9 38 00 E4 80 FE A6 A0 82 28 F0 BA 67 F0 BA 17 80 BA 9C F8 82 16 40 FE FF A8 00 0A";
            tools.printCode(tempStr);
            tools.printNormal("\n\n\n");
        }
    }
}