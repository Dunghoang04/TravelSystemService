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
public class TransactionHistory {
    private int transactionId;     // Mã giao dịch (PK)
    private int userId;            // Ai thực hiện giao dịch
    private double amount;         // Số tiền (+ hoặc -)
    private String description;    // Mô tả (VD: "Nạp tiền", "Mua tour", "Hoàn tiền")
    private String transactionType; // "RECHARGE", "PURCHASE", "REFUND",...
    private Date transactionDate;  // Ngày giao dịch

    public TransactionHistory() {
    }

    public TransactionHistory(int transactionId, int userId, double amount, String description, String transactionType, Date transactionDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" + "transactionId=" + transactionId + ", userId=" + userId + ", amount=" + amount + ", description=" + description + ", transactionType=" + transactionType + ", transactionDate=" + transactionDate + '}';
    }

    
    
}
