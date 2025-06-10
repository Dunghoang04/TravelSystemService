/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Represents an entertainment record entity in the agent module.<br>
 * Contains attributes and methods to manage entertainment data.<br>
 * <p>Bugs: No known issues.<br>
 * @author Hoang Tuan Dung
 * 
 * Project: TravelAgentService
 * Version: 1.0
 * Date: 2025-06-07
 * Record of Change:
 * DATE        Version    AUTHOR                 DESCRIPTION
 * 2025-06-07  1.0        Hoang Tuan Dung        First implementation
 */
package model;

/**
 * Represents an Entertainment entity with details such as service ID, name,
 * image, and more. Used to store and manage entertainment data in the
 * application.
 * <p>
 * Bugs: No known issues.
 *
 * @author Hoang Tuan Dung
 */
public class Entertainment {

    private int serviceId;
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
    private String dayOfWeekOpen;
    private double ticketPrice;

    /**
     * Constructs a default Entertainment object with no initial values.<br>
     */
    public Entertainment() {
    }

    /**
     * Constructs a new Entertainment object with the specified details.
     *
     * @param serviceId the service ID
     * @param name the name of the entertainment
     * @param image the image URL or path
     * @param address the address
     * @param phone the phone number
     * @param description the description
     * @param rate the rating (0–10)
     * @param type the type of entertainment
     * @param status the status
     * @param timeOpen the opening time
     * @param timeClose the closing time
     * @param dayOfWeekOpen days open
     * @param ticketPrice the ticket price
     */
    public Entertainment(int serviceId, String name, String image, String address, String phone, String description, float rate, String type, int status, String timeOpen, String timeClose, String dayOfWeekOpen, double ticketPrice) {
        this.serviceId = serviceId;
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
        this.dayOfWeekOpen = dayOfWeekOpen;
        this.ticketPrice = ticketPrice;
    }

    /**
     * Returns the service ID of the entertainment.<br>
     *
     * @return the service ID<br>
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * Sets the service ID of the entertainment.<br>
     *
     * @param serviceId the service ID to set<br>
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Returns the name of the entertainment.<br>
     *
     * @return the name<br>
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the entertainment.<br>
     *
     * @param name the name to set<br>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the image URL or path of the entertainment.<br>
     *
     * @return the image<br>
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image URL or path of the entertainment.<br>
     *
     * @param image the image to set<br>
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns the address of the entertainment.<br>
     *
     * @return the address<br>
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the entertainment.<br>
     *
     * @param address the address to set<br>
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the phone number of the entertainment.<br>
     *
     * @return the phone number<br>
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the entertainment.<br>
     *
     * @param phone the phone number to set<br>
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the description of the entertainment.<br>
     *
     * @return the description<br>
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the entertainment.<br>
     *
     * @param description the description to set<br>
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the rating of the entertainment.<br>
     *
     * @return the rating (0–10)<br>
     */
    public float getRate() {
        return rate;
    }

    /**
     * Sets the rating of the entertainment.<br>
     *
     * @param rate the rating to set (0–10)<br>
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     * Returns the type of the entertainment.<br>
     *
     * @return the type<br>
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the entertainment.<br>
     *
     * @param type the type to set<br>
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the status of the entertainment.<br>
     *
     * @return the status<br>
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the entertainment.<br>
     *
     * @param status the status to set<br>
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns the opening time of the entertainment.<br>
     *
     * @return the opening time<br>
     */
    public String getTimeOpen() {
        return timeOpen;
    }

    /**
     * Sets the opening time of the entertainment.<br>
     *
     * @param timeOpen the opening time to set<br>
     */
    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    /**
     * Returns the closing time of the entertainment.<br>
     *
     * @return the closing time<br>
     */
    public String getTimeClose() {
        return timeClose;
    }

    /**
     * Sets the closing time of the entertainment.<br>
     *
     * @param timeClose the closing time to set<br>
     */
    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    /**
     * Returns the days of the week the entertainment is open.<br>
     *
     * @return the days open<br>
     */
    public String getDayOfWeekOpen() {
        return dayOfWeekOpen;
    }

    /**
     * Sets the days of the week the entertainment is open.<br>
     *
     * @param dayOfWeekOpen the days open to set<br>
     */
    public void setDayOfWeekOpen(String dayOfWeekOpen) {
        this.dayOfWeekOpen = dayOfWeekOpen;
    }

    /**
     * Returns the ticket price of the entertainment.<br>
     *
     * @return the ticket price<br>
     */
    public double getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Sets the ticket price of the entertainment.<br>
     *
     * @param ticketPrice the ticket price to set<br>
     */
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Returns a string representation of the Entertainment object.<br>
     *
     * @return a string containing all attributes<br>
     */
    @Override
    public String toString() {
        return "Entertainment{" + "serviceId=" + serviceId + ", name=" + name + ", image=" + image + ", address=" + address + ", phone=" + phone + ", description=" + description + ", rate=" + rate + ", type=" + type + ", status=" + status + ", timeOpen=" + timeOpen + ", timeClose=" + timeClose + ", dayOfWeekOpen=" + dayOfWeekOpen + ", ticketPrice=" + ticketPrice + '}';
    }

}
