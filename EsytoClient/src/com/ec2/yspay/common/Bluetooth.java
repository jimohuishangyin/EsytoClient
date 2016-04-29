/*
 * 类文件名:  BluetoothTool.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月21日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月21日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Bluetooth
{
    public  enum ServerOrCilent{
        NONE,
        SERVICE,
        CILENT
    };
    public static String BlueToothAddress = "null";
    public static ServerOrCilent serviceOrCilent = ServerOrCilent.NONE;
    static boolean isOpen = false;
}
