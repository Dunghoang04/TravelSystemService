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
    private Date bookDate;
    private int numberAdult;
    private int numberChildren;
    private String payMethod;
    private String phone;
    private String gmail;
    private String note;
    private float totalPrice;
    private int status;

    public BookDetail() {
    }

    public BookDetail(int bookID, int userID, int tourID, Date bookDate, int numberAdult, int numberChildren, String payMethod, String phone, String gmail, String note, float totalPrice, int status) {
        this.bookID = bookID;
        this.userID = userID;
        this.tourID = tourID;
        this.bookDate = bookDate;
        this.numberAdult = numberAdult;
        this.numberChildren = numberChildren;
        this.payMethod = payMethod;
        this.phone = phone;
        this.gmail = gmail;
        this.note = note;
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

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
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
        return "BookDetail{" + "bookID=" + bookID + ", userID=" + userID + ", tourID=" + tourID + ", bookDate=" + bookDate + ", numberAdult=" + numberAdult + ", numberChildren=" + numberChildren + ", payMethod=" + payMethod + ", phone=" + phone + ", gmail=" + gmail + ", note=" + note + ", totalPrice=" + totalPrice + ", status=" + status + '}';
    }  
    
}
