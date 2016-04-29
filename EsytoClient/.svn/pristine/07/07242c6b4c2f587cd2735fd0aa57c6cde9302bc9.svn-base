package com.ec2.yspay.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chinaums.mpos.callback.MposCallback;
import com.chinaums.mpos.service.IUmsMposResultListener;
import com.ec2.yspay.R;
import com.ec2.yspay.common.Common;
import com.ec2.yspay.common.Constants;
import com.ec2.yspay.common.FileTools;
import com.ec2.yspay.common.ToastUtils;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.entity.RefundEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.CheckManagerPwdResponse;
import com.ec2.yspay.http.response.QrOrderMsgResponse;
import com.ec2.yspay.http.response.RefundResponse;
import com.ec2.yspay.http.response.TradeRefundResponse;
import com.ec2.yspay.http.task.CheckManagerPwdTask;
import com.ec2.yspay.http.task.QrOrderMsgTask;
import com.ec2.yspay.http.task.RefundTask;
import com.ec2.yspay.http.task.TradeRefundTask;
import com.ec2.yspay.pay.card.MPosPay;
import com.ec2.yspay.widget.MyTitle;
import com.ec2.yspay.widget.PopupDialog;

public class RefundDetailActivity extends BaseActivity
{
    private EditText etBeizhu;
    private String orderId;
    private String orderTime;
    private String channelType;
    private String voucherId;
    private String amount;
    private String transaction_id;
    private TextView tvOrderId,tvOrderTime,tvOrderChannelType,tvAmount,tv_refund_ramark;
    private MyTitle myTitle;
    private String mTitle;
    private boolean isCeXiao = false;
    private Button mRefundButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_detail);
        orderId = getIntent().getStringExtra("orderId");
        isCeXiao = getIntent().getBooleanExtra("isCeXiao", false);
        etBeizhu = (EditText)findViewById(R.id.et_refund_beizhu);
        etBeizhu.clearFocus();
        tvOrderId = (TextView)findViewById(R.id.tv_orderid);
        tvOrderTime = (TextView)findViewById(R.id.tv_orderTime);
        tvOrderChannelType = (TextView)findViewById(R.id.tv_channelType);
        tvAmount = (TextView)findViewById(R.id.tv_amount);
        tv_refund_ramark = (TextView)findViewById(R.id.tv_refund_ramark);
        mRefundButton =(Button) findViewById(R.id.btn_refund);
        if(isCeXiao){
        	mRefundButton.setText("确定撤销");
        	mTitle = "撤销";
        }else{
        	mTitle = "退款";
        	mRefundButton.setText("确定退款");
        }
        getOrderMsg();
//        mTitle = getIntent().getStringExtra("title");
//        if(!mTitle.equals("退款"))
//        	mTitle = "撤销";
        myTitle = (MyTitle)findViewById(R.id.rl_top);
        myTitle.setTitleText(mTitle+"详情");
        tv_refund_ramark.setText(mTitle+"备注");
        etBeizhu.setHint("请输入"+mTitle+"备注内容");
    }
    private void initView(){
        tvOrderId.setText(orderId);
        tvOrderTime.setText(orderTime);
        tvOrderChannelType.setText(PayTypeEntity.getPayName(channelType));
        tvAmount.setText("￥"+amount);
    }
    private boolean isRefundTypeRight(){
        int payId = Integer.valueOf(channelType);
        if(isCeXiao){
            if(payId!=PayTypeEntity.PAY_UNION){
                showToastLong("该交易无法通过银联进行退款！");
                return false;
            }else{
                return true;
            }
        }else {
            if(payId==PayTypeEntity.PAY_UNION){
                showToastLong("请走银联撤销通道！");
                return false;
            }else{
                return true;
            }
        }
    }
    private void getOrderMsg(){
        QrOrderMsgTask task = new QrOrderMsgTask(mContext, orderId);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                QrOrderMsgResponse response = (QrOrderMsgResponse)obj;
                orderTime = response.getOrderTime();
                channelType = response.getChannelType();
                voucherId = response.getVoucherId();
                amount = response.getAmount();
                transaction_id = response.getTransaction_id();
                if(Toolkits.isStrEmpty(voucherId)){
                    voucherId = transaction_id;
                }
                initView();
                if(!isRefundTypeRight()){
                    finish();
                }
            }
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                QrOrderMsgResponse response = (QrOrderMsgResponse)obj;
                showToast(response.getResultDesc());
                finish();
            }
        });
        task.execute();
    }
    private void addRemarkDialog() {

		final PopupDialog.Builder builder = new PopupDialog.Builder(this);
		//builder.setMessage("该凭证号已超过撤销时间,只能通过现金退款,是否继续");
		builder.setTitle("主管密码");
		builder.setEditEnabled(true);
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String mZhuguanPSW = builder.getRemarkText();
				checkPwd(mZhuguanPSW);
				dialog.dismiss();
			}
		});

		PopupDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

	}
    
    private void checkPwd(String psw){
        CheckManagerPwdTask task = new CheckManagerPwdTask(mContext, psw);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
            	if(!isCeXiao){
                    otherRefund();
                }else{
                	//现金退款和银行卡退款，退款走记流水接口，主动告诉平台退款成功和退款金额
                    Log.i("RefundTask", "银行卡退款："+orderId+";"+transaction_id);
                    new MPosPay(mContext).revocatonOrder(transaction_id,orderId,new VoidOrderResultListener());
                    
                }
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                CheckManagerPwdResponse response = (CheckManagerPwdResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
    
    public void onclick_sure(View v){
    	addRemarkDialog();
    }
    private void otherRefund(){
        RefundEntity refundEntity = new RefundEntity(orderId, amount);
        TradeRefundTask task = new TradeRefundTask(mContext, refundEntity, channelType);
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, RefundSuccessActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("orderTime", orderTime);
                intent.putExtra("channelType", channelType);
                intent.putExtra("voucherId", voucherId);
                intent.putExtra("amount", amount);
                intent.putExtra("isCeXiao", isCeXiao);
                intent.putExtra("title", mTitle);
                intent.putExtra("remark", etBeizhu.getText().toString().trim());
                startActivity(intent);
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                TradeRefundResponse response = (TradeRefundResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
    private void cardRefund(){
        RefundTask task = new RefundTask(mContext, orderId, amount, etBeizhu.getText().toString().trim());
        task.setProgressVisiable(true);
        task.setOnTaskFinished(new OnTaskFinished()
        {
            
            @Override
            public void onSucc(Object obj)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, RefundSuccessActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("orderTime", orderTime);
                intent.putExtra("channelType", channelType);
                intent.putExtra("voucherId", voucherId);
                intent.putExtra("amount", amount);
                intent.putExtra("title", mTitle);
                intent.putExtra("remark", etBeizhu.getText().toString().trim());
                startActivity(intent);
            }
            
            @Override
            public void onFail(Object obj)
            {
                // TODO Auto-generated method stub
                RefundResponse response = (RefundResponse)obj;
                showToast(response.getResultDesc());
            }
        });
        task.execute();
    }
    /**
     * 
     * 银行卡刷卡支付撤单返回信息 卓10.29
     */
    class VoidOrderResultListener implements MposCallback {
        @Override
        public void umsTransactionResult(final Bundle result){
            ((Activity) mContext).runOnUiThread(new Runnable() {
                public void run() {
                    String receive = Common.printBundle(result);
                    String cancelStatus = result.getString("cancelStatus");
                    String resultInfo = result.getString("resultInfo");
                    String signatureStatus = result
                            .getString("signatureStatus");
                    Log.i("TAG", "返回信息--->" + receive);
                    if ("success".equals(cancelStatus)) {
                        ToastUtils.showLong(mContext, "POS撤单成功！");
                        cardRefund();
                    } else {
                        ToastUtils.showLong(mContext, resultInfo);

                    }

                }
            });
        }
    }
}
