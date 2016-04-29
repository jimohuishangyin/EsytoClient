package com.ec2.yspay.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;

import com.ec2.yspay.entity.ProvinceItem;

public class CitysManager {
	private static Context mContext;
	private static String fileName = "CityList.txt";
	public static List<CityInfo> citylist = new ArrayList<CityInfo>();
	public static List<ProvinceItem> proList = new ArrayList<ProvinceItem>();
	private static CitysManager citysManager = null;
	public synchronized static CitysManager getInstance(Context context){
	    mContext = context;
	    if(citysManager==null)
	        citysManager = new CitysManager();
	    return citysManager;
	}
	public void initProvinceList(){
	    new Thread(new Runnable()
        {
            
            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                parser();
                getProvinceCityList();
            }
        }).start();
	}
	private CitysManager(){
	    initProvinceList();
	}
	/**
	 * 获取城市列表
	 * @return
	 */
	public List<CityInfo> getCitylist() {
		return citylist;
	}
	/**
	 * 获取城市列表，城市按省份分类
	 * 
	 * @author   罗洪祥
	 * @version  V001Z0001
	 * @date     2015年11月13日
	 * @see  [相关类/方法]
	 * @since  [产品/模块版本]
	 */
	public List<ProvinceItem> getProvinceCityList(){
	    if(proList.size()>0)return proList;
	    MyCompartor mc = new MyCompartor();
	    Collections.sort(citylist,mc);     //按照provName升序
	    String provName= citylist.get(0).getProvName();
	    String provCode= citylist.get(0).getProvCode();
	    List<String> citys = new ArrayList<String>();
	    citys.add(citylist.get(0).getCityName());
	    for(int i = 1;i<citylist.size();i++){
	        CityInfo cityItem = citylist.get(i);
	        if(!provCode.equals(cityItem.getProvCode())){
	            proList.add(new ProvinceItem(provName, citys));
	            provName = cityItem.getProvName().trim();
	            provCode = cityItem.getProvCode().trim();
	            citys = new ArrayList<String>();
	            citys.add(cityItem.getCityName());
	        }else{
	            citys.add(cityItem.getCityName());
	        }
	    }
	    proList.add(new ProvinceItem(provName, citys));
        return proList;
	}
	class MyCompartor implements Comparator
	{
	     @Override
	     public int compare(Object o1, Object o2)
	     {
	         CityInfo sdto1= (CityInfo )o1;
	         CityInfo sdto2= (CityInfo )o2;
	         String proPinyin1=HanyuToPinyin(sdto1.getProvName());
	         String proPinyin2=HanyuToPinyin(sdto2.getProvName());
	         if(proPinyin1.equals(proPinyin2)){
	             return sdto1.getProvCode().compareTo(sdto2.getProvCode());
	         }else{
	             return proPinyin1.compareTo(proPinyin2);
	         }
	         
	     }
	}
	/**
	 * 通过市编号获取城市信息
	 * @param cityCode
	 * @return
	 */
	public CityInfo getCityByCityCode(String cityCode){
		CityInfo cityInfo = null;
		for(CityInfo ci:citylist){
			if(cityCode.equals(ci.getCityCode())){
				cityInfo = ci;
				break;
			}
		}
		return cityInfo;
	}
	/**
	 * 通过市名称获取城市信息
	 * @param cityName
	 * @return
	 */
	public CityInfo getCityByCityName(String cityName){
		CityInfo cityInfo = null;
		for(CityInfo ci:citylist){
			if(cityName.equals(ci.getCityName())){
				cityInfo = ci;
				break;
			}
		}
		return cityInfo;
	}
	/**
	 * 通过号百编号获取城市信息
	 * @param haobaiCode
	 * @return
	 */
	public CityInfo getCityByHaobaiCode(String haobaiCode){
		CityInfo cityInfo = null;
		for(CityInfo ci:citylist){
			if(haobaiCode.equals(ci.getHaobaiCode())){
				cityInfo = ci;
				break;
			}
		}
		return cityInfo;
	}
	/**
	 * 通过SID编号获取城市信息
	 * @param sidCode
	 * @return
	 */
	public CityInfo getCityBySidCodeCode(String sidCode){
		CityInfo cityInfo = null;
		
		if (sidCode==null)
			return null;
		
		for(CityInfo ci:citylist){
			if(sidCode.equals(ci.getSIDCode())){
				cityInfo = ci;
				break;
			}
		}
		return cityInfo;
	}
	private void parser(){
		String strFile = readFromFile();
		parserFromJson(strFile);
	}
	private String readFromFile(){
		//从JSon文件读取数据
		AssetManager assetManager = mContext.getAssets();
		InputStream is;
		String strText = null;
		try {
			is = assetManager.open(fileName);
			strText = readFromInputStream(is);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return strText;
	}
	private String readFromInputStream(InputStream inputStream){
		StringBuffer sb = new StringBuffer();
		String b = null;  
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf8"));
			while ((b = br.readLine()) != null) {  
                sb.append(b);
//                writeToSD(b+"\r\n");
            }  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
		  
	}

	private void parserFromJson(String strFile){
		try {
			//将Json文件数据形成JSONObject对象
			JSONObject jsonObject = new JSONObject(strFile);
			//获取JSONObject对象数据并打印
			JSONArray citys = jsonObject.getJSONArray("CityList") ;
			StringBuffer jsonFileInfo = new StringBuffer();
			for (int i = 0; i < citys.length(); i++) {
				JSONObject city = citys.getJSONObject(i);
				String pinyinCode = city.getString("PinYinCode");
				String pinyin = city.getString("PinYin");
				String pinyinShort = city.getString("PinYinShort");
				String firstChar = city.getString("FirstChar");
				String cityName = city.getString("cityName");
				String provName = city.getString("provName");
				String cityCode = city.getString("cityCode");
				String provCode = city.getString("provCode");
				String haobaiCode = city.getString("haobaiCode");
				String SIDCode = city.getString("SIDCode");
				CityInfo cityInfo = new CityInfo(pinyinCode,pinyin,pinyinShort,firstChar,cityCode, provCode, cityName, provName, haobaiCode, SIDCode);
				citylist.add(cityInfo);
//				Log.d("ffcs", "city:" + cityName + " prov:"+provName+ " SIDCode:"+SIDCode +" pinyin:"+pinyin);
			}
//			Log.d("ffcs", "city Num:"+citylist.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	//修改文件，追加。拼音全称和拼音缩写
//		private void writeToSD(String str){
//			if(str.startsWith("{\"FirstChar\":")){
//				String s = str.substring(27, str.indexOf("\"", 27));
//				str = str.substring(0, 1)+ "\"PinYin\":\""+HanyuToPinyin(s)+"\","+str.substring(1);
//				str = str.substring(0, 1)+ "\"PinYinShort\":\""+pinyinShort+"\","+str.substring(1);
//				
//				str = str.substring(0, 1)+ "\"PinYinCode\":\""+pinyinCode+"\","+str.substring(1);
//			}
//			//写str到sd卡里
//			String sdPath=Environment.getExternalStorageDirectory().toString(); //获得SD卡路径
//			String path = sdPath+"/city6.txt";
//			try {
//				File filename = new File(path); 
//				if (!filename.exists()) { 
//					filename.createNewFile(); 
//				} 
//				FileWriter fileWriter =new FileWriter(path,true);
//				fileWriter.write(str);
//				fileWriter.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	//修改文件，追加。之后删了以前的就行了
//	private void writeToSD(String str){
//		if(str.startsWith("{\"cityName\":")){
//			char c = str.charAt(13);
//			str = str.substring(0, 1)+ "\"FirstChar\":"+getFirstPinyin(c)+","+str.substring(1);
//		}
//		//写str到sd卡里
//		String sdPath=Environment.getExternalStorageDirectory().toString(); //获得SD卡路径
//		String path = sdPath+"/city6.txt";
//		try {
//			File filename = new File(path); 
//			if (!filename.exists()) { 
//				filename.createNewFile(); 
//			} 
//			FileWriter fileWriter =new FileWriter(path,true);
//			fileWriter.write(str);
//			fileWriter.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	private String getFirstPinyin(char c){
//		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
//		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
//		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//		String[] pinyin={};
//		try {
//			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
//		} catch (BadHanyuPinyinOutputFormatCombination e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(pinyin.length>1){
////			return "$";
//			return pinyin[0].toString().substring(0, 1);
//		}else{
//			return pinyin[0].toString().substring(0, 1);
//		}
//	}
//	public static void main(String[] args) {
//		CityJsonParser parser = new CityJsonParser(null);
//		parser.getFirstPinyin('场');
//	}
//	private String pinyinShort = "";
//	private String pinyinCode="";
//	/**
//    * 汉字转拼音的方法
//    * @param name 汉字
//    * @return 拼音
//    */
	private String HanyuToPinyin(String name){
		//多音字特殊处理
		String pinyinName = "";
		String test = "";
		String pinyinShort="";
		String pinyinCode = "";
		
	    char[] nameChar = name.toCharArray();
	    HanyuPinyinOutputFormat defaultFormat = 
	                                       new HanyuPinyinOutputFormat();
	    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	    for (int i = 0; i < nameChar.length; i++) {
	        if (nameChar[i] > 128) {
	            try {
	            	String temp = getSpecialPinyin(nameChar[i]);
	            	String[] result;
	            	if(!"".equals(temp))
	            		result= new String[]{temp};
	            	else
	            		result = PinyinHelper.toHanyuPinyinStringArray
	                        (nameChar[i], defaultFormat);
	                pinyinName += result[0];
	                pinyinCode+="1";
	                for(int j=1;j<result[0].length();j++)
	                	pinyinCode+="0";
	                pinyinShort+=getFirstCharLowerCase(result[0]);
	                
	                test+=result[0];
	                if(result.length>1){
	                	test+="["+result[1];
	                    for(int t = 2;t<result.length;t++){
	                    	test+=","+result[t];
	                    }
	                    test+="]";
	                }
	                test+=" ";
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } 
	    }
//	    System.out.println( name+" "+pinyinShort+" "+test);
	    return pinyinName;
	}
    private String getSpecialPinyin(char name){
    	String pinyin="";
    	if('什'==name){
    		pinyin="shi";
    	}else if('重'==name){
    		pinyin="chong";
    	}else if('都'==name){
    		pinyin="du";
    	}else if('长'==name){
    		pinyin="chang";
    	}else if('那'==name){
    		pinyin="na";
    	}else if('侗'==name){
    		pinyin="tong";
    	}else if('厦'==name){
    		pinyin="xia";
    	}
    	return pinyin;
    }
//    /**
//     * 获取首字母（小写）
//     * @param pinyin
//     * @return
//     */
    private String getFirstCharLowerCase(String pinyin){
    	return pinyin.substring(0, 1).toLowerCase();
    }
}
