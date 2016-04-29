/*
 * 类文件名:  OrderInfo.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年9月21日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.entity;

/**
 * 订单详情
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月21日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OrderInfo
{
    private String amount;
    private String subject;
    private String body;
    private String goods_detail;
    private String operator_id;
    private String store_id;
    /**
     * 获取 amount
     * @return 返回 amount
     */
    public String getAmount()
    {
        return amount;
    }
    /**
     * 设置 amount
     * @param 对amount进行赋值
     */
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    /**
     * 获取 订单标题
     * @return 返回 subject
     */
    public String getSubject()
    {
        return subject;
    }
    /**
     * 设置 订单标题
     * @param 对subject进行赋值
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    /**
     * 获取 订单描述
     * @return 返回 body
     */
    public String getBody()
    {
        return body;
    }
    /**
     * 设置 订单描述
     * @param 对body进行赋值
     */
    public void setBody(String body)
    {
        this.body = body;
    }
    /**
     * 获取 商品明细列表
     * @return 返回 goods_detail
     */
    public String getGoods_detail()
    {
        return goods_detail;
    }
    /**
     * 设置 商品明细列表
     * @param 对goods_detail进行赋值
     */
    public void setGoods_detail(String goods_detail)
    {
        this.goods_detail = goods_detail;
    }
    /**
     * 获取 商户操作员编号
     * @return 返回 operator_id
     */
    public String getOperator_id()
    {
        return operator_id;
    }
    /**
     * 设置商户操作员编号
     * @param 对operator_id进行赋值
     */
    public void setOperator_id(String operator_id)
    {
        this.operator_id = operator_id;
    }
    /**
     * 获取 商户门店编号
     * @return 返回 store_id
     */
    public String getStore_id()
    {
        return store_id;
    }
    /**
     * 设置 商户门店编号
     * @param 对store_id进行赋值
     */
    public void setStore_id(String store_id)
    {
        this.store_id = store_id;
    }
    /** 
     * amount 商品金额
     */
    public OrderInfo(String amount)
    {
        super();
        this.amount = amount;
    }
    /** 
     * <默认构造函数>
     */
    public OrderInfo(String amount, String subject, String body, String goods_detail, String operator_id,
        String store_id)
    {
        super();
        this.amount = amount;
        this.subject = subject;
        this.body = body;
        this.goods_detail = goods_detail;
        this.operator_id = operator_id;
        this.store_id = store_id;
    }
    

}
