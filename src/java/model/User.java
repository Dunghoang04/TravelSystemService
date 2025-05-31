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
public class User {

    private int userID;
    private String gmail;
    private int roleID;
    private String password;
    private String firstName;
    private String lastName;
    private Date dob;
    private String gender;
    private String address;
    private String phone;
    private Date createDate;
    private Date updateDate;
    private int status;

    public User() {
    }

    public User(int userID, String gmail, int roleID, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate, Date updateDate, int status) {
        this.userID = userID;
        this.gmail = gmail;
        this.roleID = roleID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    public User(int userID, String gmail, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate, Date updateDate, int status, int roleID) {
        this.userID = userID;
        this.gmail = gmail;
        this.roleID = roleID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    public User(String gmail, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate, Date updateDate, int status, int roleID) {

        this.gmail = gmail;
        this.roleID = roleID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", gmail=" + gmail + ", roleID=" + roleID + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", gender=" + gender + ", address=" + address + ", phone=" + phone + ", createDate=" + createDate + ", updateDate=" + updateDate + ", status=" + status + '}';
    }

}
