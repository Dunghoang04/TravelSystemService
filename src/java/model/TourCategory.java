/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class TourCategory {
    private int tourCategoryID;
    private String tourCategoryName;

    // Constructor mặc định
    public TourCategory() {
    }

    // Constructor đầy đủ
    public TourCategory(int tourCategoryID, String tourCategoryName) {
        this.tourCategoryID = tourCategoryID;
        this.tourCategoryName = tourCategoryName;
    }

    // Getter và Setter
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

    // toString
    @Override
    public String toString() {
        return "TourCategory{" +
                "tourCategoryID=" + tourCategoryID +
                ", tourCategoryName='" + tourCategoryName + '\'' +
                '}';
    }
}