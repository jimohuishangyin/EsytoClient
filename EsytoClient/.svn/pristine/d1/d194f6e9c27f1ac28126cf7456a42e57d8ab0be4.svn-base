package com.ec2.yspay.zxing.decode;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;



public class RecodeUtils {
	private final static String[] ALLCODES = {"UTF-8","GBK","GB2312","unicode","UTF-16","GB18030","ISO-8859-1"};
	public static String recode(String str) { 
		Log.i("recode", "原："+str);
        String formart = getFirstRecode("ISO-8859-1",str);  
//        if(isMessyCode(formart)||true){//初步解码还是乱码
//        	formart = getSeceondRecode(str);
//        }
//        if (isMessyCode(formart)||true){
//        	formart = getThirdRecode(str);
//        }
        if (isMessyCode(formart)){
        	formart = str;
        }
        Log.i("recode", "结果："+formart);
        return formart;  
    }  
	/**
	 * 判断字符是否是中文
	 *
	 * @param c 字符
	 * @return 是否是中文
	 */
	private static boolean isChinese(char c) {
	    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
	            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
	            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
	            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
	            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
	        return true;
	    }
	    return false;
	}
	 
	/**
	 * 判断字符串是否是乱码
	 *
	 * @param strName 字符串
	 * @return 是否是乱码
	 */
	private static boolean isMessyCode(String strName) {
	    Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
	    Matcher m = p.matcher(strName);
	    String after = m.replaceAll("");
	    String temp = after.replaceAll("\\p{P}", "");
	    char[] ch = temp.trim().toCharArray();
	    float chLength = ch.length;
	    float count = 0;
	    for (int i = 0; i < ch.length; i++) {
	        char c = ch[i];
	        if (!Character.isLetterOrDigit(c)) {
	            if (!isChinese(c)) {
	                count = count + 1;
	            }
	        }
	    }
	    float result = count / chLength;
	    if (result > 0.4) {
	        return true;
	    } else {
	    	if(isMessyCodeSpecial(strName))
	    		return true;
	    	else
	    		return false;
	    }
	 
	} 
	private static String getFirstRecode(String codeType,String str){
		String formart = str;  
		try {  
        	for(String s:ALLCODES){
        		if (isMessyCode(formart)){
        			formart = new String(str.getBytes(codeType), s);
        			Log.w("recode01", codeType+"-->"+s+" : "+formart);
        		}
        		else{
        			break;
//        			Log.i("lhx", codeType+"-->"+s+" : "+formart);
        		}
        	}
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }
		return formart;
	}
	private static String getSeceondRecode(String str){
		String formart = str;  
        try {  
        	for(String s:ALLCODES){
        		for(String s2:ALLCODES){
	        		if (isMessyCode(formart)){
	        			formart = new String(str.getBytes(s), s2);
	        			Log.w("recode02", s+"-->"+s2+" : "+formart);
	        		}
	        		else{
//	        			break;
	        			Log.i("lhx", s+"-->"+s2+" : "+formart);
	        		}
        		}
        	}
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return formart;
	}
	/**
	 * 2次转码
	 * @param str
	 * @return
	 */
	private static String getThirdRecode(String str){
		String formart = str;  
        try {  
        	for(String s:ALLCODES){
        		for(String s2:ALLCODES){
	        		if (isMessyCode(str)){
	        			formart = new String(str.getBytes(s), s2);
	        			formart = getFirstRecode(s2, formart);
//	        			Log.i("recode03", s+"-->"+s2+" : "+formart);
	        		}
	        		else{
//	        			break;
//	        			Log.i("lhx", s+"-->"+s2+" : "+formart);
	        		}
        		}
        	}
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return formart;
	}
	private static boolean isMessyCodeSpecial(String strName){
		boolean flag = false;
		String[] sp = {"�","ļ","Ƭ","ȡ","ͼ","Դ","Ì","À","Ä","¤","Á","Î","²","¿","½","Â","Ç","µ½"};
		for(String st:sp){
			if(strName.contains(st)){
				flag = true;
				break;
			}
		}
		return flag;
	}
}
