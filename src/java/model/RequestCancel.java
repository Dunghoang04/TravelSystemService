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
    private int bookID;
    private int cancelReasonID;
    private Date requestDate;
    private String reason;
    private String not;
    private int status;

    public RequestCancel() {
    }

    public RequestCancel(int requestCancelID, int bookID, int cancelReasonID, Date requestDate, String reason, String not, int status) {
        this.requestCancelID = requestCancelID;
        this.bookID = bookID;
        this.cancelReasonID = cancelReasonID;
        this.requestDate = requestDate;
        this.reason = reason;
        this.not = not;
        this.status = status;
    }

    public int getRequestCancelID() {
        return requestCancelID;
    }

    public void setRequestCancelID(int requestCancelID) {
        this.requestCancelID = requestCancelID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getCancelReasonID() {
        return cancelReasonID;
    }

    public void setCancelReasonID(int cancelReasonID) {
        this.cancelReasonID = cancelReasonID;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestCancel{" + "requestCancelID=" + requestCancelID + ", bookID=" + bookID + ", cancelReasonID=" + cancelReasonID + ", requestDate=" + requestDate + ", reason=" + reason + ", not=" + not + ", status=" + status + '}';
    }

    
}
