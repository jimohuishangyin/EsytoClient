package com.ec2.yspay.http.response;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.ec2.yspay.common.Constants;
import com.ec2.yspay.common.MyApplication;
import com.ec2.yspay.common.Toolkits;
import com.ec2.yspay.http.ErrorCode;
import com.ec2.yspay.http.request.ClientResult;

import android.util.Log;

public abstract class Response implements Serializable{
	private static final String TAG = "Response";
	private String resultCode;
	private String resultDesc;
	private String code;
	private boolean isSuccess;
	private String resultJson;
	private boolean isTokenFail = false;
	/**
	 * 
	 * 根据每个接口不同的参数进行解析，数据提取封装
	 * @author   罗洪祥
	 * @version  V001Z0001
	 * @date     2015年9月15日
	 * @see  [相关类/方法]
	 * @since  [产品/模块版本]
	 */
	public abstract boolean parseResult(ClientResult result);
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getResultJson() {
		return resultJson;
	}

	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}
	
	public boolean isTokenFail() {
		return isTokenFail;
	}

	public void setTokenFail(boolean isTokenFail) {
		this.isTokenFail = isTokenFail;
	}
	public boolean isConnectedSuccess(){
	    return "200".equals(resultCode);
	        
	}
	/**
	 * 结果初步解析，总体分析成功与否
	 * @param result
	 */
	protected boolean parseCR(ClientResult result){
		String sResult = result.getResult();
		int statusCode = result.getResultCode();
		resultDesc = result.getResultDesc();
		this.resultCode = statusCode+"";
		this.resultJson = sResult;
		if(statusCode==200){
		    try
            {
                JSONObject json = new JSONObject(sResult);
                code = json.getString("code");
                resultDesc = json.getString("message");
                if(Constants.OK.equals(code)){
                    isSuccess = true;
                }else if("8810021".equals(code)){//token失效
                    isSuccess = false;
                    Log.i(TAG, "TOKEN 失效！");
                    MyApplication.mDataCache.UserPhoneNbr = null;
                    MyApplication.mDataCache.token = null;
                }else{
                    isSuccess = false;
                }
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                isSuccess = false;
                resultCode = ErrorCode.JSON_ERROR+"";
                resultDesc = ErrorCode.get(ErrorCode.JSON_ERROR);
            }
			
		}
		else{
			isSuccess = false;
		}
		if(Toolkits.isStrEmpty(code))
			code = resultCode;
		return isSuccess;
	}
}