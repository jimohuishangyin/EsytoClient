package com.ec2.yspay.http.task;

import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.GetPayChanneResponse;

import android.content.Context;

public class GetPayChannelTask extends BaseTask {
	
	private static final String url_left = "/app/company/getPayChannel";
	private GetPayChanneResponse response;

	public GetPayChannelTask(Context context) {
		super(context);
		this.mContext = context;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
        response = new GetPayChanneResponse();
        
        ClientResult cResult=client.build(getRequest()).post();
        response.parseResult(cResult);
        
        return response.isSuccess();
	}
	
	/**
     * 重载方法
     * @param rslt
     */
    @Override
    protected void onPostExecute(Boolean rslt) {
        // TODO Auto-generated method stub
        super.onPostExecute(rslt);
        //执行完做封装处理，取出自己要的字段
        
        if (mOnTaskFinished != null) {
            if (rslt) {
                mOnTaskFinished.onSucc(response);
            } else {
                mOnTaskFinished.onFail(response);
            }
        }
    }
    private ClientRequest getRequest(){
        ClientRequest request = new ClientRequest(url_left);
        return request;
    }

}
