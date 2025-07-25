/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Nguyen Van Vang              First implementation
 */
package model;

import java.sql.Date;

/**
 *
 * @author Nhat Anh
 */
public class Feedback {
    private int feedbackID;
    private float rate;
    private String image;
    private String content;
    private Date createDate;
    private int status;
    private int bookID;
    private int userID;
    private String userName;

    public Feedback() {
    }

    public Feedback(int feedbackID, float rate, String image, String content, Date createDate, int status, int bookID, int userID, String userName) {
        this.feedbackID = feedbackID;
        this.rate = rate;
        this.image = image;
        this.content = content;
        this.createDate = createDate;
        this.status = status;
        this.bookID = bookID;
        this.userID = userID;
        this.userName = userName;
    }

    
    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "Feedback{" + "feedbackID=" + feedbackID + ", rate=" + rate + ", image=" + image + ", content=" + content + ", createDate=" + createDate + ", status=" + status + ", bookID=" + bookID + ", userID=" + userID + ", userName=" + userName + '}';
    }

    
}