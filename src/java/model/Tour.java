/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Time;

public class Tour {
    private int tourID;
    private int tourCategoryID;
    private int travelAgentID;
    private String startPlace;
    private String endPlace;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private int numberOfDay;
    private float pricePerNight;
    private float adultPrice;
    private float childrenPrice;
    private int quantity;
    private float rate;
    private boolean status;

    // Constructor mặc định
    public Tour() {
    }

    // Constructor đầy đủ
    public Tour(int tourID, int tourCategoryID, int travelAgentID, String startPlace, String endPlace, Date startDate, Date endDate, Time startTime, Time endTime, int numberOfDay, float pricePerNight, float adultPrice, float childrenPrice, int quantity, float rate, boolean status) {
        this.tourID = tourID;
        this.tourCategoryID = tourCategoryID;
        this.travelAgentID = travelAgentID;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfDay = numberOfDay;
        this.pricePerNight = pricePerNight;
        this.adultPrice = adultPrice;
        this.childrenPrice = childrenPrice;
        this.quantity = quantity;
        this.rate = rate;
        this.status = status;
    }

    // Getter và Setter
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public void setNumberOfDay(int numberOfDay) {
        this.numberOfDay = numberOfDay;
    }

    public float getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public float getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(float adultPrice) {
        this.adultPrice = adultPrice;
    }

    public float getChildrenPrice() {
        return childrenPrice;
    }

    public void setChildrenPrice(float childrenPrice) {
        this.childrenPrice = childrenPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // toString
    @Override
    public String toString() {
        return "Tour{" +
                "tourID=" + tourID +
                ", tourCategoryID=" + tourCategoryID +
                ", travelAgentID=" + travelAgentID +
                ", startPlace='" + startPlace + '\'' +
                ", endPlace='" + endPlace + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", numberOfDay=" + numberOfDay +
                ", pricePerNight=" + pricePerNight +
                ", adultPrice=" + adultPrice +
                ", childrenPrice=" + childrenPrice +
                ", quantity=" + quantity +
                ", rate=" + rate +
                ", status=" + status +
                '}';
    }
}
