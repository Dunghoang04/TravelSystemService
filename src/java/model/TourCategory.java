/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
