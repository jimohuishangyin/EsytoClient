
package com.ec2.yspay.http.task;

import android.content.Context;

import com.ec2.yspay.http.request.ClientRequest;
import com.ec2.yspay.http.request.ClientResult;
import com.ec2.yspay.http.response.GetCompanyMsgResponse;
import com.ec2.yspay.http.response.GetMainPayStateResponse;
import com.ec2.yspay.http.response.GetNewVersionResponse;
import com.ec2.yspay.http.response.GetPersonalMsgResponse;


public class GetNewVersionTask extends BaseTask
{
    private static final String url_left = "/app/checkVersion";
    private GetNewVersionResponse response;
    private String packageName,version,versionCode;
    /** 
     * <默认构造函数>
     */
    public GetNewVersionTask(Context context,String packageName,String version,String versionCode)
    {
        super(context);
        // TODO Auto-generated constructor stub
        this.packageName = packageName;
        this.version = version;
        this.mContext = context;
        this.versionCode = versionCode;
    }
    /**
     * 重载方法
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(String... params)
    {
        // TODO Auto-generated method stub
        response = new GetNewVersionResponse();
        
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
        request.addParam("packageName",packageName);
        request.addParam("version",version);
        request.addParam("versionCode",versionCode);
        return request;
    }
}
