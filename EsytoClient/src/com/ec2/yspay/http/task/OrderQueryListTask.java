package com.ec2.yspay.http.task;

import com.ec2.yspay.http.request.Client;
import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.OrderQueryListResponse;

import android.content.Context;

public class OrderQueryListTask extends BaseTask {
	
	private static final String url_left = "/app/pos/queryAllPos";
	private OrderQueryListResponse response;
	
	protected Context mContext;
	private String mPage;
	public OrderQueryListTask(Context context,String page) {
		super(context);
		this.mContext = context;
		this.mPage = page;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		response = new OrderQueryListResponse();
		
		ClientResult cResult=client.build(getRequest(mPage)).post();
        response.parseResult(cResult);
        
        return response.isSuccess();
		
		
	}
	
	@Override
	protected void onPostExecute(Boolean rslt) {
		super.onPostExecute(rslt);
		
		if (mOnTaskFinished != null) {
            if (rslt) {
                mOnTaskFinished.onSucc(response);
            } else {
                mOnTaskFinished.onFail(response);
            }
        }
		
	}
	
	
	private ClientRequest getRequest(String page){
        ClientRequest request = new ClientRequest(url_left);
        request.addParam("page", page);
        return request;
    }
	
	

}
