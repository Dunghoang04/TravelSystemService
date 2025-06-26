/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-21  1.0        Quynh Mai          First implementation
 */
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
public class Tour {
    private int tourID;
    private int tourCategoryID;
    private int travelAgentID;
    private String tourName;
    private int numberOfDay;
    private String startPlace;
    private String endPlace;
    private int quantity;
    private String image;
    private String tourIntroduce;
    private String tourSchedule;
    private String tourInclude;
    private String tourNonInclude;
    private float rate;
    private int status;
    private Date startDay;
    private Date endDay;
    private double adultPrice;
    private double childrenPrice;
    private String reason;

    public Tour() {
    }

    public Tour(int tourID, int tourCategoryID, int travelAgentID, String tourName, int numberOfDay, String startPlace, String endPlace, int quantity, String image, String tourIntroduce, String tourSchedule, String tourInclude, String tourNonInclude, float rate, int status, Date startDay, Date endDay, double adultPrice, double childrenPrice, String reason) {
        this.tourID = tourID;
        this.tourCategoryID = tourCategoryID;
        this.travelAgentID = travelAgentID;
        this.tourName = tourName;
        this.numberOfDay = numberOfDay;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.quantity = quantity;
        this.image = image;
        this.tourIntroduce = tourIntroduce;
        this.tourSchedule = tourSchedule;
        this.tourInclude = tourInclude;
        this.tourNonInclude = tourNonInclude;
        this.rate = rate;
        this.status = status;
        this.startDay = startDay;
        this.endDay = endDay;
        this.adultPrice = adultPrice;
        this.childrenPrice = childrenPrice;
        this.reason = reason;
    }
    
    public Tour(int tourCategoryID, int travelAgentID, String tourName, int numberOfDay, String startPlace, String endPlace, int quantity, String image, String tourIntroduce, String tourSchedule, String tourInclude, String tourNonInclude, float rate, int status, Date startDay, Date endDay, double adultPrice, double childrenPrice, String reason) {
        this.tourCategoryID = tourCategoryID;
        this.travelAgentID = travelAgentID;
        this.tourName = tourName;
        this.numberOfDay = numberOfDay;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.quantity = quantity;
        this.image = image;
        this.tourIntroduce = tourIntroduce;
        this.tourSchedule = tourSchedule;
        this.tourInclude = tourInclude;
        this.tourNonInclude = tourNonInclude;
        this.rate = rate;
        this.status = status;
        this.startDay = startDay;
        this.endDay = endDay;
        this.adultPrice = adultPrice;
        this.childrenPrice = childrenPrice;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    

    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    public int getTourCategoryID() {
        return tourCategoryID;
    }

    public void setTourCategoryID(int tourCategoryID) {
        this.tourCategoryID = tourCategoryID;
    }

    public int getTravelAgentID() {
        return travelAgentID;
    }

    public void setTravelAgentID(int travelAgentID) {
        this.travelAgentID = travelAgentID;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public void setNumberOfDay(int numberOfDay) {
        this.numberOfDay = numberOfDay;
    }


    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTourIntroduce() {
        return tourIntroduce;
    }

    public void setTourIntroduce(String tourIntroduce) {
        this.tourIntroduce = tourIntroduce;
    }

    public String getTourSchedule() {
        return tourSchedule;
    }

    public void setTourSchedule(String tourSchedule) {
        this.tourSchedule = tourSchedule;
    }

    public String getTourInclude() {
        return tourInclude;
    }

    public void setTourInclude(String tourInclude) {
        this.tourInclude = tourInclude;
    }

    public String getTourNonInclude() {
        return tourNonInclude;
    }

    public void setTourNonInclude(String tourNonInclude) {
        this.tourNonInclude = tourNonInclude;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tour{" + "tourID=" + tourID + ", tourCategoryID=" + tourCategoryID + ", travelAgentID=" + travelAgentID + ", tourName=" + tourName + ", numberOfDay=" + numberOfDay + ", startPlace=" + startPlace + ", endPlace=" + endPlace + ", quantity=" + quantity + ", image=" + image + ", tourIntroduce=" + tourIntroduce + ", tourSchedule=" + tourSchedule + ", tourInclude=" + tourInclude + ", tourNonInclude=" + tourNonInclude + ", rate=" + rate + ", status=" + status + ", startDay=" + startDay + ", endDay=" + endDay + ", adultPrice=" + adultPrice + ", childrenPrice=" + childrenPrice + ", reason=" + reason + '}';
    }

    
    

}