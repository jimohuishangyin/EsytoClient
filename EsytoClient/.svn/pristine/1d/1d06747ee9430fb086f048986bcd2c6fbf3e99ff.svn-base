package com.ec2.yspay.activity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.TradeQueryResponse;
import com.ec2.yspay.http.task.TradeQueryTask;
import com.ec2.yspay.widget.MyTitle;
/**
 * 等待支付页面
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月14日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PayPaikaActivity extends BaseActivity
{
    private double money = 1;
    private TextView tvPayTitle;
    private TextView tvMoney;
    private PayTypeEntity payType;
    private ImageView ivPayIcon;
    private String orderId;
    private TextView tvOrderId,tvUserName,tvShopName;
    private MyTitle mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_paika);
        money = getIntent().getDoubleExtra("money", 0);
        orderId = getIntent().getStringExtra("orderId");
        payType = (PayTypeEntity)getIntent().getSerializableExtra("payType");
        if(payType==null)payType = new PayTypeEntity(PayTypeEntity.PAY_BEST, "翼支付", R.drawable.yzf);
        tvPayTitle = (TextView)findViewById(R.id.tv_paytitle);
        tvMoney = (TextView)findViewById(R.id.tv_money);
        tvPayTitle.setText(payType.getPayName());
        tvMoney.setText("￥"+Toolkits.doubleFormat(money));
        ivPayIcon = (ImageView)findViewById(R.id.iv_payicon);
        ivPayIcon.setImageResource(payType.getSmallImgId());
        tvOrderId = (TextView)findViewById(R.id.tv_orderId);
        tvOrderId.setText(orderId);
        tvUserName = (TextView)findViewById(R.id.tv_username);
        tvShopName = (TextView)findViewById(R.id.tv_shopname);
        tvUserName.setText(MyApplication.mDataCache.userName);
        tvShopName.setText(MyApplication.mDataCache.shopName);
//        mTitle = (MyTitle)findViewById(R.id.rl_top);
//        mTitle.setLeftOnclickListener(new View.OnClickListener()
//        {
//            
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                showWarnDialog();
//            }
//        });
//        mHandler.sendEmptyMessageDelayed(1, 2000);
        getNfcMsg();
    }
    private Handler mHandler = new Handler(){

        /**
         * 重载方法
         * @param msg
         */
        @Override
        public void handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            getNfcMsg();
//            qrState();
        }
    };
    private void getNfcMsg()
    {
        // TODO Auto-generated method stub
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);  
        if (nfcAdapter == null) {  
            showToast("设备不支持NFC！");  
        } else{
            if (!nfcAdapter.isEnabled()) {  
                showToast("请在系统设置中先启用NFC功能！");  
            } else{
//                if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {  
//                    showToast("nfc功能正常使用");
//                }  
                showToast("nfc功能正常使用");
            }
        }
         
    }
    private boolean readFromTag(Intent intent){  
        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);  
        NdefMessage mNdefMsg = (NdefMessage)rawArray[0];  
        NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];  
        try {  
            if(mNdefRecord != null){  
                String readResult = new String(mNdefRecord.getPayload(),"UTF-8");  
                Log.e("lhx", "readResult:"+readResult);
                showToast(readResult);
                return true;  
             }  
        }  
        catch (Exception e) {  
             e.printStackTrace();  
        };  
        return false;  
     }  
    /**
     * 重载方法
     */
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
      //得到是否检测到ACTION_TECH_DISCOVERED触发  
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {  
            //处理该intent  
//            readFromTag(getIntent());
            processIntent(getIntent());  
        }  
    }
  //字符序列转换为16进制字符串  
    private String bytesToHexString(byte[] src) {  
        StringBuilder stringBuilder = new StringBuilder("0x");  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        char[] buffer = new char[2];  
        for (int i = 0; i < src.length; i++) {  
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);  
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);  
//            System.out.println(buffer);  
            stringBuilder.append(buffer);  
        }  
        return stringBuilder.toString();  
    }  
  
    /** 
     * Parses the NDEF Message from the intent and prints to the TextView 
     */  
    private void processIntent(Intent intent) {  
        //取出封装在intent中的TAG  
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);  
        for (String tech : tagFromIntent.getTechList()) {  
            Log.e("lhx",tech);  
        }  
        boolean auth = false;  
        //读取TAG  
        MifareClassic mfc = MifareClassic.get(tagFromIntent);  
        try {  
            String metaInfo = "";  
            //Enable I/O operations to the tag from this TagTechnology object.  
            mfc.connect();  
            int type = mfc.getType();//获取TAG的类型  
            int sectorCount = mfc.getSectorCount();//获取TAG中包含的扇区数  
            String typeS = "";  
            switch (type) {  
            case MifareClassic.TYPE_CLASSIC:  
                typeS = "TYPE_CLASSIC";  
                break;  
            case MifareClassic.TYPE_PLUS:  
                typeS = "TYPE_PLUS";  
                break;  
            case MifareClassic.TYPE_PRO:  
                typeS = "TYPE_PRO";  
                break;  
            case MifareClassic.TYPE_UNKNOWN:  
                typeS = "TYPE_UNKNOWN";  
                break;  
            }  
            metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"  
                    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";  
            for (int j = 0; j < sectorCount; j++) {  
                //Authenticate a sector with key A.  
                auth = mfc.authenticateSectorWithKeyA(j,  
                        MifareClassic.KEY_DEFAULT);  
                int bCount;  
                int bIndex;  
                if (auth) {  
                    metaInfo += "Sector " + j + ":验证成功\n";  
                    // 读取扇区中的块  
                    bCount = mfc.getBlockCountInSector(j);  
                    bIndex = mfc.sectorToBlock(j);  
                    for (int i = 0; i < bCount; i++) {  
                        byte[] data = mfc.readBlock(bIndex);  
                        metaInfo += "Block " + bIndex + " : "  
                                + bytesToHexString(data) + "\n";  
                        bIndex++;  
                    }  
                } else {  
                    metaInfo += "Sector " + j + ":验证失败\n";  
                }  
            }  
            showToast(metaInfo); 
            Log.e("lhx", metaInfo);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    private void qrState(){
        TradeQueryTask task = new TradeQueryTask(mContext, "00000000", orderId);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                TradeQueryResponse response = (TradeQueryResponse)obj;
                String result = response.getTranResult();
                Log.i(TAG, "订单状态："+result);
                if("TRADE_SUCCESS".equals(result)){
                    showToast("交易成功");
                    Intent intent = new Intent(mContext, PaySuccessActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("payType", payType);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                }else {
                    //2秒一次，查询交易状态
                    if(mHandler!=null)
                        mHandler.sendEmptyMessageDelayed(1, 2000);
                }
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                TradeQueryResponse response = (TradeQueryResponse)obj;
                showToast("查询失败"+response.getResultDesc());
                if(mHandler!=null)
                    mHandler.sendEmptyMessageDelayed(1, 2000);
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
        mHandler.removeMessages(1);
        mHandler = null;
    }
    
    
    
}
