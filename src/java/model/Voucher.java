/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Nhat Anh
 */
public class Voucher {
    private int voucherID;
    private String voucherCode;
    private String voucherName;
    private float percentDiscount;
    private Date startDate;
    private Date endDate;
    private float maxDiscountAmount;
    private float minAmountApply;
    private boolean status;

    public Voucher() {
    }

    public Voucher(int voucherID, String voucherCode, String voucherName, float percentDiscount, Date startDate, Date endDate, float maxDiscountAmount, float minAmountApply, boolean status) {
        this.voucherID = voucherID;
        this.voucherCode = voucherCode;
        this.voucherName = voucherName;
        this.percentDiscount = percentDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDiscountAmount = maxDiscountAmount;
        this.minAmountApply = minAmountApply;
        this.status = status;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(int voucherID) {
        this.voucherID = voucherID;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public float getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(float percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(float maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public float getMinAmountApply() {
        return minAmountApply;
    }

    public void setMinAmountApply(float minAmountApply) {
        this.minAmountApply = minAmountApply;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
