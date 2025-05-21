/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class TourSchedule {
    private int tourID;
    private String detail;

    // Constructor mặc định
    public TourSchedule() {
    }

    // Constructor đầy đủ
    public TourSchedule(int tourID, String detail) {
        this.tourID = tourID;
        this.detail = detail;
    }

    // Getter và Setter
    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    // toString
    @Override
    public String toString() {
        return "TourSchedule{" +
                "tourID=" + tourID +
                ", detail='" + detail + '\'' +
                '}';
    }
}
