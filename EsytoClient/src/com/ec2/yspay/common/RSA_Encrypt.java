/*
 * 类文件名:  RSA_Encrypt.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: yunlong_zeng@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  jting
 * 创建时间:  2015-12-9
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;

import javax.crypto.Cipher;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   zhenggh
 * @version  V001Z0001
 * @date     2015-12-9
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RSA_Encrypt
{
    /** 指定加密算法为DESede *//*
    private static String ALGORITHM = "RSA";
    *//** 指定key的大小 *//*
    private static int KEYSIZE = 1024;
    *//** 指定公钥存放文件 *//*
    private static String PUBLIC_KEY_FILE = "D:/PublicKey.DAT";
    *//** 指定私钥存放文件 *//*
    private static String PRIVATE_KEY_FILE = "D:/PrivateKey.DAT";


    *//**
    * 加密方法
    * source： 源数据
    *//*
    public static String encrypt(String source) throws Exception{
       generateKeyPair();
       *//** 将文件中的公钥对象读出 *//*
       ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
       Key key = (Key) ois.readObject();
       ois.close();
       *//** 得到Cipher对象来实现对源数据的RSA加密 *//*
       Cipher cipher = Cipher.getInstance(ALGORITHM);
       cipher.init(Cipher.ENCRYPT_MODE, key);
       byte[] b = source.getBytes();
       *//** 执行加密操作 *//*
       byte[] b1 = cipher.doFinal(b);
       BASE64Encoder encoder = new BASE64Encoder();
       return encoder.encode(b1);
    }

    *//**
    * 解密算法
    * cryptograph:密文
    *//*
    public static String decrypt(String cryptograph) throws Exception{
       *//** 将文件中的私钥对象读出 *//*
       ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
       Key key = (Key) ois.readObject();
       *//** 得到Cipher对象对已用公钥加密的数据进行RSA解密 *//*
       Cipher cipher = Cipher.getInstance(ALGORITHM);
       cipher.init(Cipher.DECRYPT_MODE, key);
       BASE64Decoder decoder = new BASE64Decoder();
       byte[] b1 = decoder.decodeBuffer(cryptograph);
       *//** 执行解密操作 *//*
       byte[] b = cipher.doFinal(b1);
       return new String(b);
    }*/
    
}
