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
public class TourSession {
    private int tourId;
    private Date startDay;
    private Date endDay;
    private double adultPrice;
    private double childrenPrice;

    public TourSession() {
    }

    public TourSession(int tourId, Date startDay, Date endDay,double adultPrice, double childrenPrice) {
        this.tourId = tourId;
        this.startDay = startDay;
        this.endDay = endDay;
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
        return "TourTime{" + "tourId=" + tourId + ", startDay=" + startDay + ", endDay=" + endDay + ", adultPrice=" + adultPrice + ", childrenPrice=" + childrenPrice + '}';
    }
    
    
}
