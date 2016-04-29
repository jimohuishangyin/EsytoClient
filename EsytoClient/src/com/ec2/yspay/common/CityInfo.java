package com.ec2.yspay.common;

public class CityInfo {
	private String pinyinCode;
	private String pinyin;
	private String pinyinShort;
	private String firstCode;
	private String cityCode;
	private String provCode;
	private String cityName;
	private String provName;
	private String haobaiCode;
	private String SIDCode;
	
	public String getPinyinCode() {
		return pinyinCode;
	}
	public void setPinyinCode(String pinyinCode) {
		this.pinyinCode = pinyinCode;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getPinyinShort() {
		return pinyinShort;
	}
	public void setPinyinShort(String pinyinShort) {
		this.pinyinShort = pinyinShort;
	}
	public String getFirstCode() {
		return firstCode;
	}
	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvName() {
		return provName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public String getHaobaiCode() {
		return haobaiCode;
	}
	public void setHaobaiCode(String haobaiCode) {
		this.haobaiCode = haobaiCode;
	}
	public String getSIDCode() {
		return SIDCode;
	}
	public void setSIDCode(String sIDCode) {
		SIDCode = sIDCode;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[firstCode:"+firstCode+";cityCode:"+cityCode+";cityName:"+cityName+";provCode:"+provCode+
				";provName:"+provName+";haobaiCode:"+haobaiCode+";SIDCode:"+SIDCode+"]";
	}
	public CityInfo(String pinyinCode,String pinyin,String pinyinShort,String firstCode, String cityCode, String provCode,
			String cityName, String provName, String haobaiCode, String sIDCode) {
		super();
		this.pinyinCode = pinyinCode;
		this.pinyin = pinyin;
		this.pinyinShort = pinyinShort;
		this.firstCode = firstCode;
		this.cityCode = cityCode;
		this.provCode = provCode;
		this.cityName = cityName;
		this.provName = provName;
		this.haobaiCode = haobaiCode;
		SIDCode = sIDCode;
	}
	public CityInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
