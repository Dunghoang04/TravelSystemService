/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 *  2025-06-07  1.0       NguyenVanVang     First implementation
 */
package model;

/**
 * Represents an accommodation in the TravelAgentService system.
 * Contains details such as service ID, room ID, name, image, address, phone, description,
 * rate, type, status, and check-in/check-out times.
 */
public class Accommodation {
    /** The unique identifier for the accommodation service. */
    private int serviceID;

    /** The identifier for the associated room (accommodation ID). */
    private int roomID;

    /** The name of the accommodation. */
    private String name;

    /** The path to the accommodation's image. */
    private String image;

    /** The address of the accommodation. */
    private String address;

    /** The contact phone number of the accommodation. */
    private String phone;

    /** A detailed description of the accommodation. */
    private String description;

    /** The rating of the accommodation (0-10). */
    private float rate;

    /** The type of the accommodation (e.g., Hotel, Homestay). */
    private String type;

    /** The status of the accommodation (e.g., 1 for active, 0 for inactive). */
    private int status;

    /** The check-in time for the accommodation (format: HH:mm:ss). */
    private String checkInTime;

    /** The check-out time for the accommodation (format: HH:mm:ss). */
    private String checkOutTime;

    /**
     * Default constructor for Accommodation.
     * Initializes an empty Accommodation object.
     */
    public Accommodation() {
    }

    /**
     * Parameterized constructor for Accommodation.
     *
     * @param serviceID     the unique identifier for the accommodation service
     * @param roomID        the identifier for the associated room
     * @param name          the name of the accommodation
     * @param image         the path to the accommodation's image
     * @param address       the address of the accommodation
     * @param phone         the contact phone number
     * @param description   the detailed description
     * @param rate          the rating (0-10)
     * @param type          the type of accommodation
     * @param status        the status (e.g., 1 for active)
     * @param checkInTime   the check-in time (HH:mm:ss)
     * @param checkOutTime  the check-out time (HH:mm:ss)
     */
    public Accommodation(int serviceID, int roomID, String name, String image, String address, String phone,
                        String description, float rate, String type, int status, String checkInTime,
                        String checkOutTime) {
        // Initialize accommodation attributes
        this.serviceID = serviceID;
        this.roomID = roomID;
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.rate = rate;
        this.type = type;
        this.status = status;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    /**
     * Gets the service ID.
     *
     * @return the service ID
     */
    public int getServiceID() {
        return serviceID;
    }

    /**
     * Sets the service ID.
     *
     * @param serviceID the service ID to set
     */
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * Gets the room ID.
     *
     * @return the room ID
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Sets the room ID.
     *
     * @param roomID the room ID to set
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    /**
     * Gets the name of the accommodation.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the accommodation.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the image path.
     *
     * @return the image path
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image path.
     *
     * @param image the image path to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number.
     *
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the rating.
     *
     * @return the rating
     */
    public float getRate() {
        return rate;
    }

    /**
     * Sets the rating.
     *
     * @param rate the rating to set
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     * Gets the accommodation type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the accommodation type.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the check-in time.
     *
     * @return the check-in time
     */
    public String getCheckInTime() {
        return checkInTime;
    }

    /**
     * Sets the check-in time.
     *
     * @param checkInTime the check-in time to set
     */
    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    /**
     * Gets the check-out time.
     *
     * @return the check-out time
     */
    public String getCheckOutTime() {
        return checkOutTime;
    }

    /**
     * Sets the check-out time.
     *
     * @param checkOutTime the check-out time to set
     */
    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    /**
     * Returns a string representation of the Accommodation object.
     *
     * @return a string containing accommodation details
     */
    @Override
    public String toString() {
        return "Accommodation{" + "serviceID=" + serviceID + ", roomID=" + roomID + ", name=" + name +
               ", image=" + image + ", address=" + address + ", phone=" + phone + ", description=" + description +
               ", rate=" + rate + ", type=" + type + ", status=" + status + ", checkInTime=" + checkInTime +
               ", checkOutTime=" + checkOutTime + '}';
    }
}