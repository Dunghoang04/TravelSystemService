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
public class Feedback {
    private int feedbackID;
    private float rate;
    private String content;
    private Date createDate;
    private int status;
    private int tourID;
    private int userID;

    public Feedback() {
    }

    public Feedback(int feedbackID, float rate, String content, Date createDate, int status, int tourID, int userID) {
        this.feedbackID = feedbackID;
        this.rate = rate;
        this.content = content;
        this.createDate = createDate;
        this.status = status;
        this.tourID = tourID;
        this.userID = userID;
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

    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Feedback{" + "feedbackID=" + feedbackID + ", rate=" + rate + ", content=" + content + ", createDate=" + createDate + ", status=" + status + ", tourID=" + tourID + ", userID=" + userID + '}';
    }

    
}
