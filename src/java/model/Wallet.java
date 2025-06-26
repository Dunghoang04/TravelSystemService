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
public class Wallet {

    private int userId;            // ID người dùng sở hữu ví
    private double balance;        // Số dư hiện tại
    private Date createDate;       // Ngày tạo ví
    
    public Wallet() {
    }

    public Wallet( int userId, double balance, Date createDate) {

        this.userId = userId;
        this.balance = balance;
        this.createDate = createDate;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Wallet{" +  ", userId=" + userId + ", balance=" + balance + ", createDate=" + createDate + '}';
    }

    
    
}
