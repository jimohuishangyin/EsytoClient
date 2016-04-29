package com.ec2.yspay.http.task;

import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.SubmitResponse;

import android.content.Context;

public class SubmitTask extends BaseTask {

	private static final String url_left = "/app/company/payApply";
	private SubmitResponse response;
	private String mName;
	private String mNum;
	private String mQQ;
	private String mEmail;
	private Context mContext;

	public SubmitTask(Context context, String name, String num, String qq,
			String email) {
		super(context);
		this.mContext = context;
		this.mName = name;
		this.mNum = num;
		this.mQQ = qq;
		this.mEmail = email;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		response = new SubmitResponse();

		ClientResult cResult = client.build(getRequest()).post();
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

	private ClientRequest getRequest() {
		ClientRequest request = new ClientRequest(url_left);
		request.addParam("name", mName);
		request.addParam("phone", mNum);
		request.addParam("qq", mQQ);
		request.addParam("email", mEmail);
		return request;
	}

}
