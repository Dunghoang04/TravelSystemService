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
    private int voucherId;
    private String voucherCode;
    private String voucherName;
    private String description;
    private float percentDiscount;
    private float maxDiscountAmount;
    private float minAmountApply;
    private Date startDate;
    private Date endDate;
    private int quantity;
    private int status;

    public Voucher() {
    }

    public Voucher(int voucherId, String voucherCode, String voucherName, String description, float percentDiscount, float maxDiscountAmount, float minAmountApply, Date startDate, Date endDate, int quantity, int status) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.voucherName = voucherName;
        this.description = description;
        this.percentDiscount = percentDiscount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.minAmountApply = minAmountApply;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.status = status;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(float percentDiscount) {
        this.percentDiscount = percentDiscount;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Voucher{" + "voucherId=" + voucherId + ", voucherCode=" + voucherCode + ", voucherName=" + voucherName + ", description=" + description + ", percentDiscount=" + percentDiscount + ", maxDiscountAmount=" + maxDiscountAmount + ", minAmountApply=" + minAmountApply + ", startDate=" + startDate + ", endDate=" + endDate + ", quantity=" + quantity + ", status=" + status + '}';
    }
    
    
    
    
}
