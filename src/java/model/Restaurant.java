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
    private String name;
    private String image;
    private String address;
    private String phone;
    private String description;
    private float rate;
    private String type;
    private int status;
    private String timeOpen;
    private String timeClose;

    public Restaurant() {
    }

    public Restaurant(int serviceID, String name, String image, String address, String phone, String description, float rate, String type, int status, String timeOpen, String timeClose) {
        this.serviceID = serviceID;
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.rate = rate;
        this.type = type;
        this.status = status;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    public String getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    @Override
    public String toString() {
        return "Restaurant{" + "serviceID=" + serviceID + ", name=" + name + ", image=" + image + ", address=" + address + ", phone=" + phone + ", description=" + description + ", rate=" + rate + ", type=" + type + ", status=" + status + ", timeOpen=" + timeOpen + ", timeClose=" + timeClose + '}';
    }

    
}
