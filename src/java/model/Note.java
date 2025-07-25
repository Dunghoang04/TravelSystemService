/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Note {
    private int tourID;
    private String tourInclude;
    private String tourNonInclude;

    // Constructor mặc định
    public Note() {
    }

    // Constructor đầy đủ
    public Note(int tourID, String tourInclude, String tourNonInclude) {
        this.tourID = tourID;
        this.tourInclude = tourInclude;
        this.tourNonInclude = tourNonInclude;
    }

    // Getter và Setter
    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
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

    // toString
    @Override
    public String toString() {
        return "Note{" +
                "tourID=" + tourID +
                ", tourInclude='" + tourInclude + '\'' +
                ", tourNonInclude='" + tourNonInclude + '\'' +
                '}';
    }
}