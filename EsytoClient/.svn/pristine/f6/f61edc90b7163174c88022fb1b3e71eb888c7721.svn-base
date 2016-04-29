/*
 * 类文件名:  GetQrCodeResponse.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月11日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.http.response;

import org.json.JSONObject;

import com.ec2.yspay.http.request.ClientResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月11日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GetAppPayConfResponse extends Response
{
    private String mer_id;
    private String term_id;
    /**
     * 重载方法
     * @param result
     * @return
     */
    @Override
    public boolean parseResult(ClientResult result)
    {
        // TODO Auto-generated method stub
        boolean res = parseCR(result);
        if(isSuccess())
        try {
            String inputJson = getResultJson();
            JSONObject jsonObject = new JSONObject(inputJson);
            mer_id = jsonObject.getString("mer_id");
            term_id = jsonObject.getString("term_id");
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
    /**
     * 获取 mer_id
     * @return 返回 mer_id
     */
    public String getMer_id()
    {
        return mer_id;
    }
    /**
     * 设置 mer_id
     * @param 对mer_id进行赋值
     */
    public void setMer_id(String mer_id)
    {
        this.mer_id = mer_id;
    }
    /**
     * 获取 term_id
     * @return 返回 term_id
     */
    public String getTerm_id()
    {
        return term_id;
    }
    /**
     * 设置 term_id
     * @param 对term_id进行赋值
     */
    public void setTerm_id(String term_id)
    {
        this.term_id = term_id;
    }
    
}
