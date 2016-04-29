package com.ec2.yspay.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.entity.PayTypeEntity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.cash.PaytypeCountItem;
import com.ec2.yspay.http.response.PrintDayAllResponse;
import com.ec2.yspay.http.task.PrintDayAllTask;
import com.ec2.yspay.print.PrintManager;
import com.ec2.yspay.print.PrintTotalEntity;

public class PrintAllTogetherActivity extends BaseActivity {
	private Button btnPrint;
	private String userName;
	private String totalAmount;
	private String totalItems;
	private List<PaytypeCountItem> mList = new ArrayList<PaytypeCountItem>();
	private String cashMoney = "0.00", cashCount = "0";
	private String cardMoney = "0.00", cardCount = "0";
	private String bestMoney = "0.00", bestCount = "0";
	private String aliMoney = "0.00", aliCount = "0";
	private String wxMoney = "0.00", wxCount = "0";
	private String bdMoney = "0.00", bdCount = "0";
	private String beginTime, endTime;
	private TextView tvUser, tvBeginTime, tvCash, tvCard, tvBest, tvWx, tvAli,
			tvBdu;
	private TextView tvCashNum, tvCardNum, tvBestNum, tvWxNum, tvAliNum,
			tvBduNum;
	private TextView tvTotal, tvTotalNum;
	Date tDate = new Date();
	private PrintManager printManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print_all_together);
		printManager = PrintManager.getInstance(mContext);
		btnPrint = (Button) findViewById(R.id.btn_sure);
		getOrderCollect();
		btnPrint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				print();
			}
		});
	}

	private void initView() {
		tvUser = (TextView) findViewById(R.id.tv_all_user);
		tvBeginTime = (TextView) findViewById(R.id.tv_all_begintime);
		tvCash = (TextView) findViewById(R.id.tv_all_cash);
		tvCashNum = (TextView) findViewById(R.id.tv_all_cash_num);
		tvCard = (TextView) findViewById(R.id.tv_all_card);
		tvCardNum = (TextView) findViewById(R.id.tv_all_card_num);
		tvBest = (TextView) findViewById(R.id.tv_all_best);
		tvBestNum = (TextView) findViewById(R.id.tv_all_best_num);
		tvWx = (TextView) findViewById(R.id.tv_all_wx);
		tvWxNum = (TextView) findViewById(R.id.tv_all_wx_num);
		tvBdu = (TextView) findViewById(R.id.tv_all_baidu);
		tvBduNum = (TextView) findViewById(R.id.tv_all_baidu_num);
		tvTotal = (TextView) findViewById(R.id.tv_totalMoney);
		tvTotalNum = (TextView) findViewById(R.id.tv_totalMoneyNum);
		tvAli = (TextView) findViewById(R.id.tv_all_ali);
		tvAliNum = (TextView) findViewById(R.id.tv_all_ali_num);
		tvUser.setText(userName);
		tvBeginTime.setText(beginTime.substring(0, 10));
		tvBest.setText("￥" + bestMoney);
		tvBestNum.setText(bestCount + "笔");
		tvWx.setText("￥" + wxMoney);
		tvWxNum.setText(wxCount + "笔");
		tvAli.setText("￥" + aliMoney);
		tvAliNum.setText(aliCount + "笔");
		tvCash.setText("￥" + cashMoney);
		tvCashNum.setText(cashCount + "笔");
		tvCard.setText("￥" + cardMoney);
		tvCardNum.setText(cardCount + "笔");
		tvTotal.setText(totalAmount + "");
		tvBdu.setText("￥" + bdMoney);
		tvBduNum.setText(bdCount + "笔");
		tvTotalNum.setText(totalItems + "");
	}

	private void getOrderCollect() {
		PrintDayAllTask task = new PrintDayAllTask(mContext);
		task.setProgressVisiable(true);
		task.setOnTaskFinished(new OnTaskFinished() {

			@Override
			public void onSucc(Object obj) {
				// TODO Auto-generated method stub
				PrintDayAllResponse response = (PrintDayAllResponse) obj;
				totalAmount = response.getTotalAmount();
				totalItems = response.getTotalItems();
				mList = response.getCollectInfos();
				userName = response.getUserName();
				beginTime = response.getBeginTime();
				anaMsg();
				initView();
			}

			@Override
			public void onFail(Object obj) {
				// TODO Auto-generated method stub
				PrintDayAllResponse response = (PrintDayAllResponse) obj;
				showToast(response.getResultDesc());
				finish();
			}
		});
		task.execute();
	}

	private void anaMsg() {
		for (PaytypeCountItem item : mList) {
			int payId = Integer.valueOf(item.getChannelType());
			switch (payId) {
			case PayTypeEntity.PAY_CASH:
				cashMoney = item.getTotalAmount();
				cashCount = item.getTotalItems();
				break;
			case PayTypeEntity.PAY_UNION:
				cardMoney = item.getTotalAmount();
				cardCount = item.getTotalItems();
				break;
			case PayTypeEntity.PAY_ALI:
				aliMoney = item.getTotalAmount();
				aliCount = item.getTotalItems();
				break;
			case PayTypeEntity.PAY_BEST:
				bestMoney = item.getTotalAmount();
				bestCount = item.getTotalItems();
				break;
			case PayTypeEntity.PAY_WX:
				wxMoney = item.getTotalAmount();
				wxCount = item.getTotalItems();
				break;
			case PayTypeEntity.PAY_BAIDU:
				bdMoney = item.getTotalAmount();
				bdCount = item.getTotalItems();
			default:
				break;
			}
		}
	}

	public void print() {
		PrintTotalEntity entity = new PrintTotalEntity(userName, tDate,
				cashMoney, cashCount, cardMoney, cardCount, bestMoney,
				bestCount, aliMoney, aliCount, wxMoney, wxCount, totalAmount,
				totalItems);
		printManager.setmPrintTotalEntity(entity);
		printManager.printTotal();
	}
}
