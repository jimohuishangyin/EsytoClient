package com.ec2.yspay.http.task;

import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.OrderQueryListResponse;

import android.content.Context;

public class OrderQueryByKeyListTask extends BaseTask {

	private static final String url_left = "/app/pos/queryPosByKey";
	private OrderQueryListResponse response;
	private Context mContext;
	private String mPage;
	private String mKey;
	
	public OrderQueryByKeyListTask(Context context,String page,String key) {
		super(context);
		this.mContext = context;
		this.mPage = page;
		this.mKey = key;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		response = new OrderQueryListResponse();
		
		ClientResult cResult=client.build(getRequest(mPage,mKey)).post();
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
	
	private ClientRequest getRequest(String page,String key){
        ClientRequest request = new ClientRequest(url_left);
        request.addParam("page", page);
        request.addParam("month", "");
        request.addParam("key_word", key);
        return request;
    }

}
