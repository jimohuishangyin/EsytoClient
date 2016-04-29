/*
 * 类文件名:  PayTypeEntity.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月9日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.entity;

import java.io.Serializable;

import com.ec2.yspay.R;

/**
 * 支付类型
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月9日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PayTypeEntity implements Serializable
{
    private int payId;
    public static final int PAY_CASH = 1004;
    public static final int PAY_UNION = 1005;
    public static final int PAY_BEST = 1003;
    public static final int PAY_ALI = 1001;
    public static final int PAY_WX = 1002;
    public static final int PAY_BAIDU = 1006;
    public static final String PAYNAME_CASH = "现金";
    public static final String PAYNAME_UNION = "银行卡";
    public static final String PAYNAME_BEST = "翼支付";
    public static final String PAYNAME_ALI = "支付宝";
    public static final String PAYNAME_WX = "微信支付";
    public static final String PAYNAME_BAIDU = "百度钱包";
    
    public static String getPayName(String channelType){
        String name = "";
        int payId = Integer.valueOf(channelType);
        switch (payId)
        {
            case PAY_CASH:
                name = PAYNAME_CASH;
                break;
            case PAY_UNION:
                name = PAYNAME_UNION;
                break;
            case PAY_BEST:
                name = PAYNAME_BEST;
                break;
            case PAY_ALI:
                name = PAYNAME_ALI;
                break;
            case PAY_WX:
                name = PAYNAME_WX;
                break;
            case PAY_BAIDU:
                name = PAYNAME_BAIDU;
                break;
            default:
                break;
        }
        return name;
    }
    public static String getPayName(int channelType){
        String name = "";
        switch (channelType)
        {
        case PAY_CASH:
            name = PAYNAME_CASH;
            break;
        case PAY_UNION:
            name = PAYNAME_UNION;
            break;
        case PAY_BEST:
            name = PAYNAME_BEST;
            break;
        case PAY_ALI:
            name = PAYNAME_ALI;
            break;
        case PAY_WX:
            name = PAYNAME_WX;
            break;
        case PAY_BAIDU:
            name = PAYNAME_BAIDU;
            break;
            default:
                break;
        }
        return name;
    }
    private String payName;
    private int imgId;
    private String isOpen;
    /**
     * 获取 payId
     * @return 返回 payId
     */
    public int getPayId()
    {
        return payId;
    }
    /**
     * 设置 payId
     * @param 对payId进行赋值
     */
    public void setPayId(int payId)
    {
        this.payId = payId;
    }
    /**
     * 获取 payName
     * @return 返回 payName
     */
    public String getPayName()
    {
        return payName;
    }
    /**
     * 设置 payName
     * @param 对payName进行赋值
     */
    public void setPayName(String payName)
    {
        this.payName = payName;
    }
    /**
     * 获取 imgId
     * @return 返回 imgId
     */
    public int getImgId()
    {
        return imgId;
    }
    /**
     * 设置 imgId
     * @param 对imgId进行赋值
     */
    public void setImgId(int imgId)
    {
        this.imgId = imgId;
    }
    
    
    public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	/** 
     * <默认构造函数>
     */
    public PayTypeEntity(int payId, String payName, int imgId)
    {
        super();
        this.payId = payId;
        this.payName = payName;
        this.imgId = imgId;
    }
	/** 
     * <默认构造函数>
     */
    public PayTypeEntity(int payId, String payName, int imgId,String isOpen)
    {
        super();
        this.payId = payId;
        this.payName = payName;
        this.imgId = imgId;
        this.isOpen = isOpen;
    }
    /** 
     * <默认构造函数>
     */
    public PayTypeEntity()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    public  int getSmallImgId(){
        int smallImgId = 0;
        switch(payId){
            case PAY_CASH:
                smallImgId = R.drawable.xianjin_96_96;
                break;
            case PAY_UNION:
                smallImgId = R.drawable.yinhangka_96_96;
                break;
            case PAY_ALI:
                smallImgId = R.drawable.zhifubao_96_96;
                break;
            case PAY_WX:
                smallImgId = R.drawable.weixin_96_96;
                break;
            case PAY_BEST:
                smallImgId = R.drawable.yizhifu_96_96;
                break;
            case PAY_BAIDU:
            	smallImgId = R.drawable.baiduqianbao_96_96;
                break;
        }
        return smallImgId;
    }
    
    public static int get72ImgId(String channelType){
        int payId = Integer.valueOf(channelType);
        int smallImgId = 0;
        switch(payId){
            case PAY_CASH:
                smallImgId = R.drawable.xianjin_72_72;
                break;
            case PAY_UNION:
                smallImgId = R.drawable.yinhangka_72_72;
                break;
            case PAY_ALI:
                smallImgId = R.drawable.zhifubao_72_72;
                break;
            case PAY_WX:
                smallImgId = R.drawable.weixin_72_72;
                break;
            case PAY_BEST:
                smallImgId = R.drawable.yizhifu_72_72;
                break;
            case PAY_BAIDU:
            	smallImgId = R.drawable.baiduqianbao_72_72;
                break;
        }
        return smallImgId;
    }
    /**
     * 支付图标，96px id
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年12月11日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static int get96ImgId(String channelType){
        int payId = Integer.valueOf(channelType);
        int smallImgId = 0;
        switch(payId){
            case PAY_CASH:
                smallImgId = R.drawable.xianjin_96_96;
                break;
            case PAY_UNION:
                smallImgId = R.drawable.yinhangka_96_96;
                break;
            case PAY_ALI:
                smallImgId = R.drawable.zhifubao_96_96;
                break;
            case PAY_WX:
                smallImgId = R.drawable.weixin_96_96;
                break;
            case PAY_BEST:
                smallImgId = R.drawable.yizhifu_96_96;
                break;
            case PAY_BAIDU:
            	smallImgId = R.drawable.baiduqianbao_96_96;
                break;
        }
        return smallImgId;
    }
    /**
     * 支付图标，144px id
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年12月11日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static int get144ImgId(String channelType){
        int payId = Integer.valueOf(channelType);
        int smallImgId = 0;
        switch(payId){
            case PAY_CASH:
                smallImgId = R.drawable.xianjin_color_144_144;
                break;
            case PAY_UNION:
                smallImgId = R.drawable.yinhangka_color_144_144;
                break;
            case PAY_ALI:
                smallImgId = R.drawable.zhifubao_color_144_144;
                break;
            case PAY_WX:
                smallImgId = R.drawable.weixin_color_144_144;
                break;
            case PAY_BEST:
                smallImgId = R.drawable.yizhifu_color_144_144;
                break;
            case PAY_BAIDU:
            	smallImgId = R.drawable.baiduqianbao_color_144_144;
                break;
        }
        return smallImgId;
    }
    /**
     * 支付图标，144px id
     * 
     * @author   罗洪祥
     * @version  V001Z0001
     * @date     2015年12月11日
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static int get144ImgDeafaultId(String channelType){
        int payId = Integer.valueOf(channelType);
        int smallImgId = 0;
        switch(payId){
            case PAY_CASH:
                smallImgId = R.drawable.xianjin_color_144_144;
                break;
            case PAY_UNION:
                smallImgId = R.drawable.yinhangka_gray_144_144;
                break;
            case PAY_ALI:
                smallImgId = R.drawable.zhifubao_gray_144_144;
                break;
            case PAY_WX:
                smallImgId = R.drawable.weixin_gray_144_144;
                break;
            case PAY_BEST:
                smallImgId = R.drawable.yizhifu_gray_144_144;
                break;
            case PAY_BAIDU:
            	smallImgId = R.drawable.baiduqianbao_gray_144_144;
                break;
        }
        return smallImgId;
    }
    
    public static int get144ImgId(int payId){
        int smallImgId = 0;
        switch(payId){
            case PAY_CASH:
                smallImgId = R.drawable.xianjin_color_144_144;
                break;
            case PAY_UNION:
                smallImgId = R.drawable.yinhangka_color_144_144;
                break;
            case PAY_ALI:
                smallImgId = R.drawable.zhifubao_color_144_144;
                break;
            case PAY_WX:
                smallImgId = R.drawable.weixin_color_144_144;
                break;
            case PAY_BEST:
                smallImgId = R.drawable.yizhifu_color_144_144;
                break;
            case PAY_BAIDU:
            	smallImgId = R.drawable.baiduqianbao_color_144_144;
                break;
        }
        return smallImgId;
    }
    
    public static int get144ImgDeafaultId(int payId){
        int smallImgId = 0;
        switch(payId){
            case PAY_CASH:
                smallImgId = R.drawable.xianjin_color_144_144;
                break;
            case PAY_UNION:
                smallImgId = R.drawable.yinhangka_gray_144_144;
                break;
            case PAY_ALI:
                smallImgId = R.drawable.zhifubao_gray_144_144;
                break;
            case PAY_WX:
                smallImgId = R.drawable.weixin_gray_144_144;
                break;
            case PAY_BEST:
                smallImgId = R.drawable.yizhifu_gray_144_144;
                break;
            case PAY_BAIDU:
            	smallImgId = R.drawable.baiduqianbao_gray_144_144;
                break;
        }
        return smallImgId;
    }
    /**
     * 二维码中心图标
     * 
     * @return
     */
    public int getErWeiMaIcon(){
        int smallImgId = 0;
        switch(payId){
            case PAY_ALI:
                smallImgId = R.drawable.zhifubao_120_120;
                break;
            case PAY_WX:
                smallImgId = R.drawable.weixinzhifu_120_120;
                break;
            case PAY_BEST:
                smallImgId = R.drawable.yizhifu_120_120;
                break;
            case PAY_BAIDU:
                smallImgId = R.drawable.baoduqianbao_120_120;
                break;
        }
        return smallImgId;
    }
    
}
