/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-21  1.0        Quynh Mai          First implementation
 */
package model;

/**
 *
 * @author Nhat Anh
 */
public class TourCategory {
    private int tourCategoryID;
    private String tourCategoryName;

    public TourCategory() {
    }

    public TourCategory(int tourCategoryID, String tourCategoryName) {
        this.tourCategoryID = tourCategoryID;
        this.tourCategoryName = tourCategoryName;
    }
    
    public TourCategory(String tourCategoryName) {
        this.tourCategoryName = tourCategoryName;
    }

    public int getTourCategoryID() {
        return tourCategoryID;
    }

    public void setTourCategoryID(int tourCategoryID) {
        this.tourCategoryID = tourCategoryID;
    }

    public String getTourCategoryName() {
        return tourCategoryName;
    }

    public void setTourCategoryName(String tourCategoryName) {
        this.tourCategoryName = tourCategoryName;
    }

    @Override
    public String toString() {
        return "TourCategory{" + "tourCategoryID=" + tourCategoryID + ", tourCategoryName=" + tourCategoryName + '}';
    }
    
    
    
}
