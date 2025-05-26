/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Time;

/**
 *
 * @author Nhat Anh
 */
public class Restaurant {
    private int serviceID;
    private String description;
    private String address;
    private String image;
    private String type;
    private Time timeOpen;
    private Time timeClose;

    public Restaurant() {
    }

    public Restaurant(int serviceID, String description, String address, String image, String type, Time timeOpen, Time timeClose) {
        this.serviceID = serviceID;
        this.description = description;
        this.address = address;
        this.image = image;
        this.type = type;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Time getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(Time timeOpen) {
        this.timeOpen = timeOpen;
    }

    public Time getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(Time timeClose) {
        this.timeClose = timeClose;
    }
    
    
}
