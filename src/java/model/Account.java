/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nhat Anh
 */
public class Account {

    private int accountID;
    private String email;
    private int roleID;
    private String password;
    private String status;

    public Account() {
    }

    public Account(int accountID, String email, int roleID, String password, String status) {
        this.accountID = accountID;
        this.email = email;
        this.roleID = roleID;
        this.password = password;
        this.status = status;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

    @Override
    public String toString() {
        return accountID + ", " + email + ", " + roleID + ", " + password + ", " + status ;
    }
    
    
}
