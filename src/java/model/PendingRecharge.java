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
public class PendingRecharge {
    private int rechargeID;
    private int userID;
    private double amount;
    private String referenceCode;
    private String status;
    private Date requestDate;
    private Date approvedDate;

    public PendingRecharge() {
    }

    public PendingRecharge(int rechargeID, int userID, double amount, String referenceCode, String status, Date requestDate, Date approvedDate) {
        this.rechargeID = rechargeID;
        this.userID = userID;
        this.amount = amount;
        this.referenceCode = referenceCode;
        this.status = status;
        this.requestDate = requestDate;
        this.approvedDate = approvedDate;
    }

    public int getRechargeID() {
        return rechargeID;
    }

    public void setRechargeID(int rechargeID) {
        this.rechargeID = rechargeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Override
    public String toString() {
        return "PendingRecharge{" + "rechargeID=" + rechargeID + ", userID=" + userID + ", amount=" + amount + ", referenceCode=" + referenceCode + ", status=" + status + ", requestDate=" + requestDate + ", approvedDate=" + approvedDate + '}';
    }
    
    
}
