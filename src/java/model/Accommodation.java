/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb fs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Nguyễn Văn Vang   First implementation
 */
package model;

/**
 * Represents an accommodation entity in the travel service system.<br>
 * This class is used to manage accommodation details such as service ID, name, address, and more.<br>
 * <p>
 * Bugs: None known at this time.</p>
 *
 * @author Nguyễn Văn Vang
 */
public class Accommodation {
    private int serviceID; // Unique identifier for the accommodation service
    private String name; // Name of the accommodation
    private String image; // Image URL or path for the accommodation
    private String address; // Address of the accommodation
    private String phone; // Contact phone number of the accommodation
    private String description; // Description of the accommodation
    private float rate; // Rating of the accommodation
    private String type; // Type of accommodation (e.g., hotel, hostel)
    private int status; // Status of the accommodation (e.g., available, unavailable)
    private String checkInTime; // Check-in time for the accommodation
    private String checkOutTime; // Check-out time for the accommodation

    /**
     * Default constructor for Accommodation.<br>
     * Creates an empty Accommodation object with default values.
     */
    /*
     * Initializes a new Accommodation instance with default values.
     * All fields are set to null or 0 depending on their type.
     */
    public Accommodation() {
    }

    /**
     * Constructs an Accommodation with all specified attributes.<br>
     *
     * @param serviceID The unique identifier for the accommodation service
     * @param name The name of the accommodation
     * @param image The image URL or path for the accommodation
     * @param address The address of the accommodation
     * @param phone The contact phone number
     * @param description A description of the accommodation
     * @param rate The rating of the accommodation
     * @param type The type of accommodation (e.g., hotel, hostel)
     * @param status The status of the accommodation (e.g., available, unavailable)
     * @param checkInTime The check-in time for the accommodation
     * @param checkOutTime The check-out time for the accommodation
     */
    /*
     * Creates a new Accommodation instance with all provided attributes.
     * Assigns values to all fields directly.
     */
    public Accommodation(int serviceID, String name, String image, String address, String phone, String description, float rate, String type, int status, String checkInTime, String checkOutTime) {
        this.serviceID = serviceID;
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
     * Gets the accommodation's unique service identifier.<br>
     *
     * @return The service ID as an integer
     */
    /*
     * Retrieves the unique identifier of the accommodation service.
     * Returns the current value of serviceID.
     */
    public int getServiceID() {
        return serviceID;
    }

    /**
     * Sets the accommodation's unique service identifier.<br>
     *
     * @param serviceID The service ID to set
     */
    /*
     * Updates the unique identifier of the accommodation service.
     * Assigns the provided value to serviceID.
     */
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * Gets the accommodation's name.<br>
     *
     * @return The name as a String
     */
    /*
     * Retrieves the name of the accommodation.
     * Returns the current value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the accommodation's name.<br>
     *
     * @param name The name to set
     */
    /*
     * Updates the name of the accommodation.
     * Assigns the provided value to name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the accommodation's image URL or path.<br>
     *
     * @return The image as a String
     */
    /*
     * Retrieves the image URL or path of the accommodation.
     * Returns the current value of image.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the accommodation's image URL or path.<br>
     *
     * @param image The image to set
     */
    /*
     * Updates the image URL or path of the accommodation.
     * Assigns the provided value to image.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the accommodation's address.<br>
     *
     * @return The address as a String
     */
    /*
     * Retrieves the address of the accommodation.
     * Returns the current value of address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the accommodation's address.<br>
     *
     * @param address The address to set
     */
    /*
     * Updates the address of the accommodation.
     * Assigns the provided value to address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the accommodation's contact phone number.<br>
     *
     * @return The phone number as a String
     */
    /*
     * Retrieves the contact phone number of the accommodation.
     * Returns the current value of phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the accommodation's contact phone number.<br>
     *
     * @param phone The phone number to set
     */
    /*
     * Updates the contact phone number of the accommodation.
     * Assigns the provided value to phone.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the accommodation's description.<br>
     *
     * @return The description as a String
     */
    /*
     * Retrieves the description of the accommodation.
     * Returns the current value of description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the accommodation's description.<br>
     *
     * @param description The description to set
     */
    /*
     * Updates the description of the accommodation.
     * Assigns the provided value to description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the accommodation's rating.<br>
     *
     * @return The rating as a float
     */
    /*
     * Retrieves the rating of the accommodation.
     * Returns the current value of rate.
     */
    public float getRate() {
        return rate;
    }

    /**
     * Sets the accommodation's rating.<br>
     *
     * @param rate The rating to set
     */
    /*
     * Updates the rating of the accommodation.
     * Assigns the provided value to rate.
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     * Gets the accommodation's type.<br>
     *
     * @return The type as a String
     */
    /*
     * Retrieves the type of the accommodation.
     * Returns the current value of type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the accommodation's type.<br>
     *
     * @param type The type to set
     */
    /*
     * Updates the type of the accommodation.
     * Assigns the provided value to type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the accommodation's status.<br>
     *
     * @return The status as an integer
     */
    /*
     * Retrieves the status of the accommodation.
     * Returns the current value of status.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the accommodation's status.<br>
     *
     * @param status The status to set
     */
    /*
     * Updates the status of the accommodation.
     * Assigns the provided value to status.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the accommodation's check-in time.<br>
     *
     * @return The check-in time as a String
     */
    /*
     * Retrieves the check-in time of the accommodation.
     * Returns the current value of checkInTime.
     */
    public String getCheckInTime() {
        return checkInTime;
    }

    /**
     * Sets the accommodation's check-in time.<br>
     *
     * @param checkInTime The check-in time to set
     */
    /*
     * Updates the check-in time of the accommodation.
     * Assigns the provided value to checkInTime.
     */
    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    /**
     * Gets the accommodation's check-out time.<br>
     *
     * @return The check-out time as a String
     */
    /*
     * Retrieves the check-out time of the accommodation.
     * Returns the current value of checkOutTime.
     */
    public String getCheckOutTime() {
        return checkOutTime;
    }

    /**
     * Sets the accommodation's check-out time.<br>
     *
     * @param checkOutTime The check-out time to set
     */
    /*
     * Updates the check-out time of the accommodation.
     * Assigns the provided value to checkOutTime.
     */
    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    /**
     * Returns a string representation of the Accommodation object.<br>
     *
     * @return A string containing all accommodation attributes
     */
    /*
     * Generates a string representation of the Accommodation object.
     * Includes all attributes in the format "Accommodation{...}".
     */
    @Override
    public String toString() {
        return "Accommodation{" + "serviceID=" + serviceID + ", name=" + name + ", image=" + image + ", address=" + address + ", phone=" + phone + ", description=" + description + ", rate=" + rate + ", type=" + type + ", status=" + status + ", checkInTime=" + checkInTime + ", checkOutTime=" + checkOutTime + '}';
    }
}