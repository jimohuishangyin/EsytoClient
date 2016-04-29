package com.ec2.yspay.activity;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.PrintDeviceTools;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PrintOne;
import com.ec2.yspay.entity.PrintOrderEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.PrintByOrderidResponse;
import com.ec2.yspay.http.response.PrintLastOneResponse;
import com.ec2.yspay.http.task.PrintByOrderidTask;
import com.ec2.yspay.print.PrintManager;
import com.ec2.yspay.zxing.activity.CaptureActivity;

public class PrintTradingNumberActivity extends BaseActivity
{
    private EditText etOrderId;
    private ImageButton ibSaoma;
    private Button btnCancel,btnDone;
    private PrintManager printManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_trading_number);
        printManager = PrintManager.getInstance(mContext);
        etOrderId = (EditText)findViewById(R.id.et_orderid);
        ibSaoma = (ImageButton)findViewById(R.id.ib_saoma);
        ibSaoma.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent saomaIntent = new Intent(mContext, CaptureActivity.class);
                saomaIntent.putExtra("isShowBtn", false);
                startActivityForResult(saomaIntent, 101);
            }
        });
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnDone = (Button)findViewById(R.id.btn_done);
        btnCancel.setOnClickListener(onclick);
        btnDone.setOnClickListener(onclick);
        etOrderId.requestFocus();
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
        if(requestCode==101&&resultCode==100){
            String codeMsg = data.getStringExtra("qrCodeFromScan");
            etOrderId.setText(codeMsg);
        }
    }
   
    private OnClickListener onclick = new OnClickListener()
    {
        
        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            switch(v.getId()){
                case R.id.btn_cancel:
                    finish();
                    break;
                case R.id.btn_done:
                  String orderId = etOrderId.getText().toString().trim();
                    if(Toolkits.isStrEmpty(orderId)){
                        showToast("请输入订单号");
                    }else{
                        getOrderMsg(orderId);
                    }
                    break;
            }
        }
    };
    private void getOrderMsg(String orderId){
        PrintByOrderidTask task = new PrintByOrderidTask(mContext, orderId);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                PrintByOrderidResponse response = (PrintByOrderidResponse)obj;
                PrintOrderEntity mPrintOrderEntity = response.getPrintMsg();
                if (MyApplication.mDataCache.shopCode.equals(mPrintOrderEntity.getShopCode())) {
                    PrintOrderEntity entity = new PrintOrderEntity(mPrintOrderEntity.getTransactionId(), mPrintOrderEntity.getOrderId(),
                        mPrintOrderEntity.getOrderTime(), mPrintOrderEntity.getAccount(), mPrintOrderEntity.getAmount(),
                        mPrintOrderEntity.getRemark(), mPrintOrderEntity.getShopName(), mPrintOrderEntity.getChannelType());
                    
                    Intent printerIntent = new Intent(mContext, PrintPreviewActivity.class);
                    printerIntent.putExtra("orderItem", (Serializable)entity);
                    startActivity(printerIntent);
                    finish();
                } else {
                    Toast.makeText(mContext, "本门店不存在该交易", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                PrintByOrderidResponse response = (PrintByOrderidResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
  
}
