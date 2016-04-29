package com.ec2.yspay.entity;

public class ChannelTypes {
	
	private String channelType;
	private String isOpen;
	private String name;
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ChannelTypes(String channelType, String isOpen, String name) {
		super();
		this.channelType = channelType;
		this.isOpen = isOpen;
		this.name = name;
	}
	
	

}
