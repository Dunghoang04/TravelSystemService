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
public class Entertainment {
    private int serviceID;
    private String description;
    private String type;
    private Time timeOpen;
    private Time timeClose;
    private String dayOfWeekOpen;

    public Entertainment() {
    }

    public Entertainment(int serviceID, String description, String type, Time timeOpen, Time timeClose, String dayOfWeekOpen) {
        this.serviceID = serviceID;
        this.description = description;
        this.type = type;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.dayOfWeekOpen = dayOfWeekOpen;
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

    public String getDayOfWeekOpen() {
        return dayOfWeekOpen;
    }

    public void setDayOfWeekOpen(String dayOfWeekOpen) {
        this.dayOfWeekOpen = dayOfWeekOpen;
    }
    
    
}
