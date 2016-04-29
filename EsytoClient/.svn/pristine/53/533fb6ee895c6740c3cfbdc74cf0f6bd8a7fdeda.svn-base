package com.ec2.yspay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.widget.MyTitle;

public class ExplainActivity extends BaseActivity {
	private String expliant = "1.撤销只支持银行卡当日（结算）前交易，且需匹配原付款银行卡（刷卡），否则无效；\n2.易Pos只支持当日交易撤销，不支持退货；\n3.持银联电子签购单撤销，请输入“销售单据号”；\n4.使用微信支付凭证，请输入“商户单号”；\n5.使用支付宝账单记录，请输入“商户订单号”；\n6.使用翼支付账单记录，请输入“流水单号”。";
	private TextView mExpliantText;
	private MyTitle mTitle;
	private String myTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explain);
		mExpliantText = (TextView) findViewById(R.id.explain);
		stringColorChange();
		mTitle = (MyTitle) findViewById(R.id.rl_top);
		myTitle = getIntent().getStringExtra("title");
		mTitle.setTitleText(myTitle);
		
	}

	private void stringColorChange(){
        int yinlian=expliant.indexOf("“销售单据号”");
        int yinlianFend=yinlian+"“销售单据号”".length();
        int weixin=expliant.indexOf("“商户单号”");
        int weixinFend=weixin+"“商户单号”".length();
        int zhifubao=expliant.indexOf("“商户订单号”");
        int zhifubaoFend=zhifubao+"“商户订单号”".length();
        int yizhifu =expliant.indexOf("“流水单号”");
        int yizhifuFend=yizhifu+"“流水单号”".length();
        SpannableStringBuilder style=new SpannableStringBuilder(expliant);
        style.setSpan(new ForegroundColorSpan(Color.RED),yinlian,yinlianFend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.RED),weixin,weixinFend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.RED),zhifubao,zhifubaoFend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.RED),yizhifu,yizhifuFend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mExpliantText.setText(style);
    }
}
