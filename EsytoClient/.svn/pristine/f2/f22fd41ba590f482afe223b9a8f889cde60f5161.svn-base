package com.ec2.yspay.http.response;

import org.json.JSONObject;

import com.ec2.yspay.http.request.ClientResult;

public class SubmitResponse extends Response {

	private String processId;
	@Override
	public boolean parseResult(ClientResult result) {
		
		boolean res = parseCR(result);
		if(isSuccess())
		try {
			String inputJson = getResultJson();
			JSONObject jsonObject = new JSONObject(inputJson);
			processId = jsonObject.getString("processId");
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
            res = false;
            setIsSuccess(false);
            setResultCode("002");
            setResultDesc("解析失败");
            return false;
		}
		return res;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	

}
