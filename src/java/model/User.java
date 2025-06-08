/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 /*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên      First implementation
 */
/**
 * Represents a role entity with a unique identifier and name.<br>
 * This class is used to define roles for users in the system.<br>
 * <p>
 * Bugs: None known at this time.</p>
 *
 * @author Hà Thị Duyên
 */
package model;

import java.sql.Date;

/**
 *
 * @author Hà Thị Duyên
 */
public class User {

    private int userID; // Unique identifier for the user
    private String gmail; // User's email address
    private int roleID; // ID of the user's role
    private String password; // User's password
    private String firstName; // User's first name
    private String lastName; // User's last name
    private Date dob; // User's date of birth
    private String gender; // User's gender
    private String address; // User's address
    private String phone; // User's phone number
    private Date createDate; // Date the user account was created
    private Date updateDate; // Date the user account was last updated
    private int status; // User's account status

    /**
     * Default constructor for User.<br>
     * Creates an empty User object with default values.
     */
    // Block comment to describe the method
    /* 
     * Initializes a new User instance with default values.
     * All fields are set to null or 0 depending on their type.
     */
    public User() {
    }

    /**
     * Constructs a User with all specified attributes.<br>
     *
     * @param userID The unique identifier for the user
     * @param gmail The user's email address
     * @param roleID The ID of the user's role
     * @param password The user's password
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param dob The user's date of birth
     * @param gender The user's gender
     * @param address The user's address
     * @param phone The user's phone number
     * @param createDate The date the user account was created
     * @param updateDate The date the user account was last updated
     * @param status The user's account status
     */
    // Block comment to describe the method
    /* 
     * Creates a new User instance with all provided attributes.
     * Assigns values to all fields directly.
     */
    public User(int userID, String gmail, int roleID, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate, Date updateDate, int status) {
        this.userID = userID;
        this.gmail = gmail;
        this.roleID = roleID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    /**
     * Constructs a User with all attributes except roleID in a different
     * order.<br>
     *
     * @param userID The unique identifier for the user
     * @param gmail The user's email address
     * @param password The user's password
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param dob The user's date of birth
     * @param gender The user's gender
     * @param address The user's address
     * @param phone The user's phone number
     * @param createDate The date the user account was created
     * @param updateDate The date the user account was last updated
     * @param status The user's account status
     * @param roleID The ID of the user's role
     */
    // Block comment to describe the method
    /* 
     * Creates a new User instance with a different parameter order.
     * Assigns values to all fields, including roleID at the end.
     */
    public User(int userID, String gmail, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate, Date updateDate, int status, int roleID) {
        this.userID = userID;
        this.gmail = gmail;
        this.roleID = roleID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    /**
     * Constructs a User without userID.<br>
     *
     * @param gmail The user's email address
     * @param password The user's password
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param dob The user's date of birth
     * @param gender The user's gender
     * @param address The user's address
     * @param phone The user's phone number
     * @param createDate The date the user account was created
     * @param updateDate The date the user account was last updated
     * @param status The user's account status
     * @param roleID The ID of the user's role
     */
    // Block comment to describe the method
    /* 
     * Creates a new User instance without userID.
     * Assigns values to all fields except userID.
     */
    public User(String gmail, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate, Date updateDate, int status, int roleID) {
        this.gmail = gmail;
        this.roleID = roleID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    /**
     * Gets the user's unique identifier.<br>
     *
     * @return The user ID as an integer
     */
    // Block comment to describe the method
    /* 
     * Retrieves the unique identifier of the user.
     * Returns the current value of userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user's unique identifier.<br>
     *
     * @param userID The user ID to set
     */
    // Block comment to describe the method
    /* 
     * Updates the unique identifier of the user.
     * Assigns the provided value to userID.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the user's email address.<br>
     *
     * @return The gmail as a String
     */
    // Block comment to describe the method
    /* 
     * Retrieves the email address of the user.
     * Returns the current value of gmail.
     */
    public String getGmail() {
        return gmail;
    }

    /**
     * Sets the user's email address.<br>
     *
     * @param gmail The email address to set
     */
    // Block comment to describe the method
    /* 
     * Updates the email address of the user.
     * Assigns the provided value to gmail.
     */
    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    /**
     * Gets the user's role ID.<br>
     *
     * @return The role ID as an integer
     */
    // Block comment to describe the method
    /* 
     * Retrieves the role ID of the user.
     * Returns the current value of roleID.
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * Sets the user's role ID.<br>
     *
     * @param roleID The role ID to set
     */
    // Block comment to describe the method
    /* 
     * Updates the role ID of the user.
     * Assigns the provided value to roleID.
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    /**
     * Gets the user's password.<br>
     *
     * @return The password as a String
     */
    // Block comment to describe the method
    /* 
     * Retrieves the password of the user.
     * Returns the current value of password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.<br>
     *
     * @param password The password to set
     */
    // Block comment to describe the method
    /* 
     * Updates the password of the user.
     * Assigns the provided value to password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's first name.<br>
     *
     * @return The first name as a String
     */
    // Block comment to describe the method
    /* 
     * Retrieves the first name of the user.
     * Returns the current value of firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.<br>
     *
     * @param firstName The first name to set
     */
    // Block comment to describe the method
    /* 
     * Updates the first name of the user.
     * Assigns the provided value to firstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name.<br>
     *
     * @return The last name as a String
     */
    // Block comment to describe the method
    /* 
     * Retrieves the last name of the user.
     * Returns the current value of lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.<br>
     *
     * @param lastName The last name to set
     */
    // Block comment to describe the method
    /* 
     * Updates the last name of the user.
     * Assigns the provided value to lastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the user's date of birth.<br>
     *
     * @return The date of birth as a Date object
     */
    // Block comment to describe the method
    /* 
     * Retrieves the date of birth of the user.
     * Returns the current value of dob.
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the user's date of birth.<br>
     *
     * @param dob The date of birth to set
     */
    // Block comment to describe the method
    /* 
     * Updates the date of birth of the user.
     * Assigns the provided value to dob.
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Gets the user's gender.<br>
     *
     * @return The gender as a String
     */
    // Block comment to describe the method
    /* 
     * Retrieves the gender of the user.
     * Returns the current value of gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the user's gender.<br>
     *
     * @param gender The gender to set
     */
    // Block comment to describe the method
    /* 
     * Updates the gender of the user.
     * Assigns the provided value to gender.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the user's address.<br>
     *
     * @return The address as a String
     */
    // Block comment to describe the method
    /* 
     * Retrieves the address of the user.
     * Returns the current value of address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the user's address.<br>
     *
     * @param address The address to set
     */
    // Block comment to describe the method
    /* 
     * Updates the address of the user.
     * Assigns the provided value to address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the user's phone number.<br>
     *
     * @return The phone number as a String
     */
    // Block comment to describe the method
    /* 
     * Retrieves the phone number of the user.
     * Returns the current value of phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the user's phone number.<br>
     *
     * @param phone The phone number to set
     */
    // Block comment to describe the method
    /* 
     * Updates the phone number of the user.
     * Assigns the provided value to phone.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the user's account creation date.<br>
     *
     * @return The creation date as a Date object
     */
    // Block comment to describe the method
    /* 
     * Retrieves the account creation date of the user.
     * Returns the current value of createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the user's account creation date.<br>
     *
     * @param createDate The creation date to set
     */
    // Block comment to describe the method
    /* 
     * Updates the account creation date of the user.
     * Assigns the provided value to createDate.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the user's last update date.<br>
     *
     * @return The update date as a Date object
     */
    // Block comment to describe the method
    /* 
     * Retrieves the last update date of the user.
     * Returns the current value of updateDate.
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the user's last update date.<br>
     *
     * @param updateDate The update date to set
     */
    // Block comment to describe the method
    /* 
     * Updates the last update date of the user.
     * Assigns the provided value to updateDate.
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Gets the user's account status.<br>
     *
     * @return The status as an integer
     */
    // Block comment to describe the method
    /* 
     * Retrieves the account status of the user.
     * Returns the current value of status.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the user's account status.<br>
     *
     * @param status The status to set
     */
    // Block comment to describe the method
    /* 
     * Updates the account status of the user.
     * Assigns the provided value to status.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the User object.<br>
     *
     * @return A string containing all user attributes
     */
    // Block comment to describe the method
    /* 
     * Generates a string representation of the User object.
     * Includes all attributes in the format "User{...}".
     */
    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", gmail=" + gmail + ", roleID=" + roleID + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", gender=" + gender + ", address=" + address + ", phone=" + phone + ", createDate=" + createDate + ", updateDate=" + updateDate + ", status=" + status + '}';
    }
}
