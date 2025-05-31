/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Nhat Anh
 */
public class TourTime {
    private int tourId;
    private Date startDay;
    private Date endDay;
    private Time startTime;
    private Time endTime;
    private double adultPrice;
    private double childrenPrice;

    public TourTime() {
    }

    public TourTime(int tourId, Date startDay, Date endDay, Time startTime, Time endTime, double adultPrice, double childrenPrice) {
        this.tourId = tourId;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.adultPrice = adultPrice;
        this.childrenPrice = childrenPrice;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getChildrenPrice() {
        return childrenPrice;
    }

    public void setChildrenPrice(double childrenPrice) {
        this.childrenPrice = childrenPrice;
    }

    @Override
    public String toString() {
        return "TourTime{" + "tourId=" + tourId + ", startDay=" + startDay + ", endDay=" + endDay + ", startTime=" + startTime + ", endTime=" + endTime + ", adultPrice=" + adultPrice + ", childrenPrice=" + childrenPrice + '}';
    }
    
    
}
