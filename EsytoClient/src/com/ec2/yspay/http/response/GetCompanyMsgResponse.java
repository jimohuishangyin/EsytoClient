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
public class GetCompanyMsgResponse extends Response
{
    private String companyName;
    private String companyCode;
    private String userName;
    private String compPhone;
    private String compAddress;
    private String province;
    private String city;
    private String companyLogoImage;
    private String companyLogoImageUrl;
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
            companyName = jsonObject.getString("companyName");
            companyCode = jsonObject.getString("companyCode");
            userName = jsonObject.getString("linkman");
            compPhone = jsonObject.getString("compPhone");
            compAddress = jsonObject.getString("compAddress");
            province = jsonObject.getString("province");
            city = jsonObject.getString("city");
            companyLogoImage = jsonObject.getString("companyLogoImage");
            companyLogoImageUrl = jsonObject.getString("companyLogoImageUrl");
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
     * 获取 companyName
     * @return 返回 companyName
     */
    public String getCompanyName()
    {
        return companyName;
    }
    /**
     * 设置 companyName
     * @param 对companyName进行赋值
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    /**
     * 获取 companyCode
     * @return 返回 companyCode
     */
    public String getCompanyCode()
    {
        return companyCode;
    }
    /**
     * 设置 companyCode
     * @param 对companyCode进行赋值
     */
    public void setCompanyCode(String companyCode)
    {
        this.companyCode = companyCode;
    }
    /**
     * 获取 compPhone
     * @return 返回 compPhone
     */
    public String getCompPhone()
    {
        return compPhone;
    }
    /**
     * 设置 compPhone
     * @param 对compPhone进行赋值
     */
    public void setCompPhone(String compPhone)
    {
        this.compPhone = compPhone;
    }
    /**
     * 获取 compAddress
     * @return 返回 compAddress
     */
    public String getCompAddress()
    {
        return compAddress;
    }
    /**
     * 设置 compAddress
     * @param 对compAddress进行赋值
     */
    public void setCompAddress(String compAddress)
    {
        this.compAddress = compAddress;
    }
    /**
     * 获取 province
     * @return 返回 province
     */
    public String getProvince()
    {
        return province;
    }
    /**
     * 设置 province
     * @param 对province进行赋值
     */
    public void setProvince(String province)
    {
        this.province = province;
    }
    /**
     * 获取 city
     * @return 返回 city
     */
    public String getCity()
    {
        return city;
    }
    /**
     * 设置 city
     * @param 对city进行赋值
     */
    public void setCity(String city)
    {
        this.city = city;
    }
    /**
     * 获取 userName
     * @return 返回 userName
     */
    public String getUserName()
    {
        return userName;
    }
    /**
     * 设置 userName
     * @param 对userName进行赋值
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    /**
     * 获取 companyLogoImage
     * @return 返回 companyLogoImage
     */
    public String getCompanyLogoImage()
    {
        return companyLogoImage;
    }
    /**
     * 设置 companyLogoImage
     * @param 对companyLogoImage进行赋值
     */
    public void setCompanyLogoImage(String companyLogoImage)
    {
        this.companyLogoImage = companyLogoImage;
    }
    /**
     * 获取 companyLogoImageUrl
     * @return 返回 companyLogoImageUrl
     */
    public String getCompanyLogoImageUrl()
    {
        return companyLogoImageUrl;
    }
    /**
     * 设置 companyLogoImageUrl
     * @param 对companyLogoImageUrl进行赋值
     */
    public void setCompanyLogoImageUrl(String companyLogoImageUrl)
    {
        this.companyLogoImageUrl = companyLogoImageUrl;
    }
    
}
