package com.ec2.yspay.entity;

public class ReportEntity {
	private String payName;
	private String payMoney;
	private String payCount;
	private float percent;
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public String getPayCount() {
		return payCount;
	}
	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}
	
	public float getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public ReportEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReportEntity(String payName, String payMoney, String payCount,
			float percent) {
		super();
		this.payName = payName;
		this.payMoney = payMoney;
		this.payCount = payCount;
		this.percent = percent;
	}
	
}
