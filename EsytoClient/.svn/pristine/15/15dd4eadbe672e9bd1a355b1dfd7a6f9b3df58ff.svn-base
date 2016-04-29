package com.ec2.yspay.http.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ec2.yspay.entity.ChannelTypes;
import com.ec2.yspay.http.cash.ObjList;
import com.ec2.yspay.http.request.ClientResult;

public class GetPayChanneResponse extends Response {
	
	private List<ChannelTypes>channelTypes= new ArrayList<ChannelTypes>();

	@Override
	public boolean parseResult(ClientResult result) {
		boolean res = parseCR(result);
        if(isSuccess())
        try {
        	String inputJson = getResultJson();
            JSONObject jsonObject = new JSONObject(inputJson);
            JSONArray jsonArray = jsonObject.getJSONArray("channelTypes");
			if(jsonArray!=null){
				for(int i = 0; i < jsonArray.length() ; i++){ 
                    JSONObject jsonObj = ((JSONObject)jsonArray.opt(i));
                    String channelType1 = jsonObj.getString("channelType");
                    String isOpen = jsonObj.getString("isOpen");
                    String name = jsonObj.getString("name");
                    ChannelTypes chanelTypes = new ChannelTypes(channelType1, isOpen, name);
                    channelTypes.add(chanelTypes);
				}
			}
			res = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            res = false;
            setIsSuccess(false);
            setResultCode("002");
            setResultDesc("解析失败");
            return false;
        }
        return res;
	}

	public List<ChannelTypes> getChannelTypes() {
		return channelTypes;
	}

	public void setChannelTypes(List<ChannelTypes> channelTypes) {
		this.channelTypes = channelTypes;
	}
	
	

}
