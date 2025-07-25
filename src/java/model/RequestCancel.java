/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-28  1.0        Hung              First implementation
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
    private int userID;
    private Date requestDate;
    private String reason;
    private int status;

    public RequestCancel() {
    }

    public RequestCancel(int requestCancelID, int bookID, int userID, Date requestDate, String reason, int status) {
        this.requestCancelID = requestCancelID;
        this.bookID = bookID;
        this.userID = userID;
        this.requestDate = requestDate;
        this.reason = reason;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestCancel{" + "requestCancelID=" + requestCancelID + ", bookID=" + bookID + ", userID=" + userID + ", requestDate=" + requestDate + ", reason=" + reason + ", status=" + status + '}';
    }

    

    

    
}
