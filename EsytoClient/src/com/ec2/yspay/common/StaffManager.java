package com.ec2.yspay.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ec2.yspay.http.cash.ShopItem;
import com.ec2.yspay.http.cash.StaffItem;

public class StaffManager {
	private static Context mContext;
	private List<StaffItem> stafflist = new ArrayList<StaffItem>();
	private static StaffManager staffManager = null;
	private StaffManager(){
		
	}
	public synchronized static StaffManager getInstance(Context context){
	    mContext = context;
	    if(staffManager==null)
	    	staffManager = new StaffManager();
	    return staffManager;
	}
	
	public boolean isShopListEmpty(){
		if(this.stafflist==null||this.stafflist.size()<1)
			return true;
		else
			return false;
	}
	public void clearData(){
		this.stafflist.clear();
	}
	public List<StaffItem> getStafflist() {
		return stafflist;
	}
	public void setStafflist(List<StaffItem> stafflist) {
		this.stafflist = stafflist;
	}
	public void addStaff(StaffItem item){
		this.stafflist.add(item);
	}
	public void delStaff(int location){
		stafflist.remove(location);
	}
	
}
