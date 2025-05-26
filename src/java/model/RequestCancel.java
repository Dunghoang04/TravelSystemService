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
public class RequestCancel {
    private int requestCancelID;
    private String description;
    private Date requestDate;
    private String status;
    private String reasonCancel;
    private int bookID;

    public RequestCancel() {
    }

    public RequestCancel(int requestCancelID, String description, Date requestDate, String status, String reasonCancel, int bookID) {
        this.requestCancelID = requestCancelID;
        this.description = description;
        this.requestDate = requestDate;
        this.status = status;
        this.reasonCancel = reasonCancel;
        this.bookID = bookID;
    }

    public int getRequestCancelID() {
        return requestCancelID;
    }

    public void setRequestCancelID(int requestCancelID) {
        this.requestCancelID = requestCancelID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReasonCancel() {
        return reasonCancel;
    }

    public void setReasonCancel(String reasonCancel) {
        this.reasonCancel = reasonCancel;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    
    
    
}
