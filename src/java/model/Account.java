/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class Account {
    private int accountID;
    private int roleID;
    private String gmail;
    private String password;
    private boolean status;


    public Account() {
    }


    public Account(int accountID, int roleID, String gmail, String password, boolean status) {
        this.accountID = accountID;
        this.roleID = roleID;
        this.gmail = gmail;
        this.password = password;
        this.status = status;
    }

    // Getter và Setter
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // toString
    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", roleID=" + roleID +
                ", gmail='" + gmail + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}
