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
public class BookDetail {
    private int bookID;
    private int userID;
    private int tourID;
    private int voucherID;
    private Date bookDate;
    private int numberAdult;
    private int numberChildren;
    private String firstName;
    private String lastName;
    private String phone;
    private String gmail;
    private String note;
    private int isBookedForOther;
    private float totalPrice;
    private int status;

    public BookDetail() {
    }

    public BookDetail(int bookID, int userID, int tourID, int voucherID, Date bookDate, int numberAdult, int numberChildren, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, float totalPrice, int status) {
        this.bookID = bookID;
        this.userID = userID;
        this.tourID = tourID;
        this.voucherID = voucherID;
        this.bookDate = bookDate;
        this.numberAdult = numberAdult;
        this.numberChildren = numberChildren;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gmail = gmail;
        this.note = note;
        this.isBookedForOther = isBookedForOther;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(int voucherID) {
        this.voucherID = voucherID;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public int getNumberAdult() {
        return numberAdult;
    }

    public void setNumberAdult(int numberAdult) {
        this.numberAdult = numberAdult;
    }

    public int getNumberChildren() {
        return numberChildren;
    }

    public void setNumberChildren(int numberChildren) {
        this.numberChildren = numberChildren;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIsBookedForOther() {
        return isBookedForOther;
    }

    public void setIsBookedForOther(int isBookedForOther) {
        this.isBookedForOther = isBookedForOther;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookDetail{" + "bookID=" + bookID + ", userID=" + userID + ", tourID=" + tourID + ", voucherID=" + voucherID + ", bookDate=" + bookDate + ", numberAdult=" + numberAdult + ", numberChildren=" + numberChildren +  ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", gmail=" + gmail + ", note=" + note + ", isBookedForOther=" + isBookedForOther + ", totalPrice=" + totalPrice + ", status=" + status + '}';
    }

    

    

}
