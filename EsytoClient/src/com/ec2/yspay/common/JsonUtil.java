package com.ec2.yspay.common;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	public static String getJsonVal(JSONObject jsonObject, String key) throws JSONException {
		String value = "";
		if(jsonObject.has(key))
		{
			value = jsonObject.getString(key);
		}
		
		return value;
	}
}
