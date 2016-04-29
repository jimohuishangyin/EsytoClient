package com.ec2.yspay.common;

import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.provider.SyncStateContract.Helpers;
import android.util.Base64;
/**
 * Java 加解密工具类
 * 
 * @author 
 *
 */
public class CryptToolkit {

    private static final String UTF8 = "utf-8";
    //定义 加密算法,可用 DES,DESede,Blowfish
    private static final String ALGORITHM_DESEDE = "DESede/CBC/PKCS7Padding";

    /**
     * BASE64 加密
     * 
     * @param src
     * @return
     * @throws Exception
     */
    public String base64Encoder(String src) throws Exception {
        return new String(Base64.encode(src.getBytes(UTF8), Base64.DEFAULT),UTF8);
    }
    
    /**
     * BASE64解密
     * 
     * @param dest
     * @return
     * @throws Exception
     */
    public String base64Decoder(String dest) throws Exception {
        return new String(Base64.decode(dest, Base64.DEFAULT),UTF8);
    }
    
    /**
     * 3DES加密
     * 
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    public String desedeEncoder(String src, String key) throws Exception {
        IvParameterSpec ips = new IvParameterSpec(HexUtils.strToByte("12345678"));
        SecretKey secretKey = new SecretKeySpec(build3DesKey(key), ALGORITHM_DESEDE);
        Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ips);
        byte[] b = cipher.doFinal(src.getBytes(UTF8));
        
        return byte2HexStr(b);
    }
    
    /**
     * 3DES解密
     * 
     * @param dest
     * @param key
     * @return
     * @throws Exception
     */
    public String desedeDecoder(String dest, String key) throws Exception {
        IvParameterSpec ips = new IvParameterSpec(HexUtils.strToByte("12345678"));
        SecretKey secretKey = new SecretKeySpec(build3DesKey(key), ALGORITHM_DESEDE);
        Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
        cipher.init(Cipher.DECRYPT_MODE, secretKey,ips);
        byte[] b = cipher.doFinal(str2ByteArray(dest));
        
        return new String(b, UTF8);
    
    }
    
    /**
     * 字节数组转化为大写16进制字符串
     * 
     * @param b
     * @return
     */
    private String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }
            
            sb.append(s.toUpperCase());
        }
        
        return sb.toString();
    }
    
    /**
     * 字符串转字节数组
     * 
     * @param s
     * @return
     */
    private byte[] str2ByteArray(String s) {
        int byteArrayLength = s.length()/2;
        byte[] b = new byte[byteArrayLength];
        for (int i = 0; i < byteArrayLength; i++) {
            byte b0 = (byte) Integer.valueOf(s.substring(i*2, i*2+2), 16).intValue();
            b[i] = b0;
        }
        
        return b;
    }
    
    /**
     * 构造3DES加解密方法key
     * 
     * @param keyStr
     * @return
     * @throws Exception
     */
    private byte[] build3DesKey(String keyStr) throws Exception {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes(UTF8);
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        
        return key;
    }
    
    public static void main(String[] args) {
        try {
            CryptToolkit des = new CryptToolkit();  // 实例化一个对像
            System.out.println("DES:" + des.desedeEncoder("FFCS0123456", "012345678912345678904321"));
            
            String val = des.base64Encoder(des.desedeEncoder("FFCS0123456", "012345678912345678904321"));
            System.out.println(val);
            String src = des.desedeDecoder(des.base64Decoder(val), "012345678912345678904321");// 把String 类型的密文解密
            System.out.println(src);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static class HexUtils {

        private static char[] hexChar = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F' };

        public static byte[] toHex(byte[] digestByte) {
            byte[] rtChar = new byte[digestByte.length * 2];
            for (int i = 0; i < digestByte.length; i++) {
                byte b1 = (byte) (digestByte[i] >> 4 & 0x0f);
                byte b2 = (byte) (digestByte[i] & 0x0f);
                rtChar[i * 2] = (byte) (b1 < 10 ? b1 + 48 : b1 + 55);
                rtChar[i * 2 + 1] = (byte) (b2 < 10 ? b2 + 48 : b2 + 55);
            }
            return rtChar;
        }

        public static String toHexString(byte[] digestByte) {
            return new String(toHex(digestByte));
        }

        public static byte[] fromHex(byte[] sc) {
            byte[] res = new byte[sc.length / 2];
            for (int i = 0; i < sc.length; i++) {
                byte c1 = (byte) (sc[i] - 48 < 17 ? sc[i] - 48 : sc[i] - 55);
                i++;
                byte c2 = (byte) (sc[i] - 48 < 17 ? sc[i] - 48 : sc[i] - 55);
                res[i / 2] = (byte) (c1 * 16 + c2);
            }
            return res;
        }

        public static byte[] fromHexString(String hex) {
            return fromHex(hex.getBytes());
        }

        public static String encode(String in) {
            return new String(toHex(in.getBytes()));
        }

        public static byte[] strToByte(String inStr) {
//          if (!String.) {
//              return null;
//          }
            ArrayList byteList = new ArrayList();
            for (int i = 0; i < inStr.length(); i++) {

                byteList.add(new Byte(Byte.parseByte(String.valueOf(inStr.charAt(i)))));
                // byteArray.add(new Byte(inStr.charAt(i)-47));
            }
            byte[] byteArray = new byte[byteList.size()];
            for (int j = 0; j < byteArray.length; j++) {
                byteArray[j] = ((Byte) byteList.get(j)).byteValue();

            }
            return byteArray;
        }
        public static String decode(String in) {
            return new String(fromHex(in.getBytes()));
        }

    }
}
