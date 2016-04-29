package com.ec2.yspay.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import com.ec2.yspay.http.cash.ShopItem;

public class ShopManager {
	private static Context mContext;
	private ShopItem currentShop;
	private List<ShopItem> shoplist = new ArrayList<ShopItem>();
	private static ShopManager shopManager = null;
	private ShopManager(){
		
	}
	public synchronized static ShopManager getInstance(Context context){
	    mContext = context;
	    if(shopManager==null)
	    	shopManager = new ShopManager();
	    return shopManager;
	}
	
	public boolean isShopListEmpty(){
		if(this.shoplist==null||this.shoplist.size()<1)
			return true;
		else
			return false;
	}
	public String getCurrentShopCode(){
		if(currentShop==null)
			return "";
		else
			return currentShop.getShopCode();
					
	}
	public String getCurrentShopName(){
		if(currentShop==null)
			return "全部";
		else
			return currentShop.getShopName();
					
	}
	public ShopItem getCurrentShop() {
		return currentShop;
	}
	public void setCurrentShop(ShopItem currentShop) {
		this.currentShop = currentShop;
	}
	public List<ShopItem> getShoplist() {
		return shoplist;
	}
	public void setShoplist(List<ShopItem> shoplist) {
		this.shoplist = shoplist;
	}
	public void clearData(){
		this.shoplist = null;
		this.currentShop = null;
	}
	
}
