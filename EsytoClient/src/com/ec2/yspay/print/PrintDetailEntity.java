package com.ec2.yspay.print;


import java.util.Date;
import java.util.List;

import com.ec2.yspay.http.cash.PrintDetailItem;

/**
 * Created by luohx on 2016/1/5.
 */
public class PrintDetailEntity {
    private String cashierName;
    private Date tDate;
    private String totalAmount;
    private List<PrintDetailItem> mList;
    private String totalItems;

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public Date gettDate() {
        return tDate;
    }

    public void settDate(Date tDate) {
        this.tDate = tDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PrintDetailItem> getmList() {
        return mList;
    }

    public void setmList(List<PrintDetailItem> mList) {
        this.mList = mList;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public PrintDetailEntity(String cashierName, Date tDate, String totalAmount,String totalItems, List<PrintDetailItem> mList) {
        this.cashierName = cashierName;
        this.tDate = tDate;
        this.totalAmount = totalAmount;
        this.mList = mList;
        this.totalItems = totalItems;
    }

    public PrintDetailEntity() {
    }
}
