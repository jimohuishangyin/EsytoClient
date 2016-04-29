package com.ec2.yspay.http.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ec2.yspay.http.cash.LimitItem;
import com.ec2.yspay.http.cash.ObjList;
import com.ec2.yspay.http.request.ClientResult;

public class OrderQueryListResponse extends Response {
	
	private List<ObjList> objList = new ArrayList<ObjList>();
	
	@Override
	public boolean parseResult(ClientResult result) {
		boolean res = parseCR(result);
		if(isSuccess()){
			try {
				String inputJson = getResultJson();
				JSONObject jsonObject =new JSONObject(inputJson);
				JSONObject pageInfoJson = jsonObject.getJSONObject("pageInfo");
				JSONArray arrayJArray = pageInfoJson.getJSONArray("objList");
				if(arrayJArray!=null){
					for(int i = 0; i < arrayJArray.length() ; i++){ 
	                    JSONObject jsonObj = ((JSONObject)arrayJArray.opt(i));
	                    String amount = jsonObj.getString("amount");
	                    String channelType = jsonObj.getString("channelType");
	                    String orderNo = jsonObj.getString("orderNo");
	                    String status = jsonObj.getString("status");
	                    String orderTime = jsonObj.getString("orderTime");
	                    ObjList obj = new ObjList(amount, channelType, orderNo, status, orderTime);
	                    objList.add(obj);
					}
				}
				res = true;
				
			} catch (Exception e) {
	            e.printStackTrace();
	            res = false;
	            setIsSuccess(false);
	            setResultCode("002");
	            setResultDesc("解析失败");
	            return false;
			}
			
			
			
		}
		
		return false;
	}

	public List<ObjList> getObjList() {
		return objList;
	}

	public void setObjList(List<ObjList> objList) {
		this.objList = objList;
	}

	
	
	

}
