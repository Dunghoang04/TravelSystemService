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
public class VAT {
    private int vatId;
    private double vatRate;
    private String description;
    private Date startDate;
    private Date endDate;
    private int status;
    private Date createDate;
    private Date updateDate;

    public VAT() {
    }

    public VAT(int vatId, double vatRate, String description, Date startDate, Date endDate, int status, Date createDate, Date updateDate) {
        this.vatId = vatId;
        this.vatRate = vatRate;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public int getVatId() {
        return vatId;
    }

    public void setVatId(int vatId) {
        this.vatId = vatId;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "VAT{" + "vatId=" + vatId + ", vatRate=" + vatRate + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", createDate=" + createDate + ", updateDate=" + updateDate + '}';

    }
    
    
}
