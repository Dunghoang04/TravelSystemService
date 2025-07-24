/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR     DESCRIPTION
 * 2025-06-24  1.0        Hưng       First implementation
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Hung
 */
public class HistoryUpdateVoucher {
    private int historyID;
    private int voucherID;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private Date updateDate;
    private int updateBy;
    private String updatedByName; 

    public HistoryUpdateVoucher() {
    }

    public HistoryUpdateVoucher(int historyID, int voucherID, String fieldName, String oldValue, String newValue, Date updateDate, int updateBy, String updatedByName) {
        this.historyID = historyID;
        this.voucherID = voucherID;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.updatedByName = updatedByName;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
    }

    

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(int voucherID) {
        this.voucherID = voucherID;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "HistoryUpdateVoucher{" + "historyID=" + historyID + ", voucherID=" + voucherID + ", fieldName=" + fieldName + ", oldValue=" + oldValue + ", newValue=" + newValue + ", updateDate=" + updateDate + ", updateBy=" + updateBy + ", updatedByName=" + updatedByName + '}';
    }

    
    
    
}
