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
 * 2025-06-07  1.0        Quynh Mai          First implementation
 */

package model;

import java.sql.Date;

/**
 * TravelAgent represents a travel agent entity in the system.
 * This class extends the User class,mapping to the TravelAgent and User tables in the database. 
 * It contains specific details of the travel agency and its representative.
 * The class contains methods to manage travel agent information from TravelAgent table in database.
 * In the update or insert method, all data will be normalized (trim space) before update/insert into database.
 * The method will throw an object of <code>java.lang.Exception</code> class if there is any error occurring when finding, inserting, or updating data.
 * @author Quynh Mai
 */
public class TravelAgent extends User {

    // Fields representing TravelAgent table
    private int travelAgentID; // Unique identifier for the travel agent
    private String travelAgentName; // Name of the travel agency
    private String travelAgentAddress; // Address of the travel agency
    private String travelAgentGmail; // Email of the travel agency
    private String hotLine; // Hotline number of the travel agency
    private String taxCode; // Tax code of the travel agency
    private Date establishmentDate; // Date when the agency was established
    private String representativeIDCard; // ID card number of the representative
    private Date dateOfIssue; // Date of issue for the ID card
    private String businessLicense; // Path to the business license image
    private String frontIDCard; // Path to the front side of ID card image
    private String backIDCard; // Path to the back side of ID card image

 /**
     * Default constructor for TravelAgent.
     * Initializes a new instance with default values.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     */
    public TravelAgent() {
        super(); // Call default constructor of User
        // Initialize TravelAgent-specific fields with default values
    }

    /**
     * Parameterized constructor for TravelAgent with travelAgentID.
     * Initializes a TravelAgent instance with all attributes, including inherited fields from User.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param travelAgentID The unique identifier for the travel agent
     * @param travelAgentName The name of the travel agency
     * @param travelAgentAddress The address of the travel agency
     * @param travelAgentGmail The email of the travel agency
     * @param hotLine The hotline number of the travel agency
     * @param taxCode The tax code of the travel agency
     * @param establishmentDate The date when the agency was established
     * @param representativeIDCard The ID card number of the representative
     * @param dateOfIssue The date of issue for the ID card
     * @param frontIDCard The path to the front side of ID card image
     * @param backIDCard The path to the back side of ID card image
     * @param businessLicense The path to the business license image
     * @param userID The unique identifier for the user account
     * @param gmail The personal email of the representative
     * @param roleID The role ID of the user (e.g., 4 for travel agent)
     * @param password The password for the user account
     * @param firstName The first name of the representative
     * @param lastName The last name of the representative
     * @param dob The date of birth of the representative
     * @param gender The gender of the representative
     * @param address The personal address of the representative
     * @param phone The personal phone number of the representative
     * @param createDate The date when the user account was created
     * @param updateDate The date when the user account was last updated
     * @param status The status of the user account (e.g., 2 for pending approval)
     */
    public TravelAgent(int travelAgentID, String travelAgentName, String travelAgentAddress, String travelAgentGmail,
            String hotLine, String taxCode, Date establishmentDate, String representativeIDCard, Date dateOfIssue,
            String frontIDCard, String backIDCard, String businessLicense, int userID, String gmail, int roleID,
            String password, String firstName, String lastName, Date dob, String gender, String address, String phone,
            Date createDate, Date updateDate, int status) {
        super(userID, gmail, roleID, password, firstName, lastName, dob, gender, address, phone, createDate, updateDate, status);
        // Assign TravelAgent-specific fields
        this.travelAgentID = travelAgentID;
        this.travelAgentName = travelAgentName;
        this.travelAgentAddress = travelAgentAddress;
        this.travelAgentGmail = travelAgentGmail;
        this.hotLine = hotLine;
        this.taxCode = taxCode;
        this.establishmentDate = establishmentDate;
        this.representativeIDCard = representativeIDCard;
        this.dateOfIssue = dateOfIssue;
        this.frontIDCard = frontIDCard;
        this.backIDCard = backIDCard;
        this.businessLicense = businessLicense;
    }

    /**
     * Parameterized constructor for TravelAgent without travelAgentID.
     * Initializes a TravelAgent instance with all attributes except travelAgentID,
     * which will be auto-generated (e.g., by database).
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param travelAgentName The name of the travel agency
     * @param travelAgentAddress The address of the travel agency
     * @param travelAgentGmail The email of the travel agency
     * @param hotLine The hotline number of the travel agency
     * @param taxCode The tax code of the travel agency
     * @param establishmentDate The date when the agency was established
     * @param representativeIDCard The ID card number of the representative
     * @param dateOfIssue The date of issue for the ID card
     * @param frontIDCard The path to the front side of ID card image
     * @param backIDCard The path to the back side of ID card image
     * @param businessLicense The path to the business license image
     * @param userID The unique identifier for the user account
     * @param gmail The personal email of the representative
     * @param roleID The role ID of the user (e.g., 4 for travel agent)
     * @param password The password for the user account
     * @param firstName The first name of the representative
     * @param lastName The last name of the representative
     * @param dob The date of birth of the representative
     * @param gender The gender of the representative
     * @param address The personal address of the representative
     * @param phone The personal phone number of the representative
     * @param createDate The date when the user account was created
     * @param updateDate The date when the user account was last updated
     * @param status The status of the user account (e.g., 2 for pending approval)
     */
    public TravelAgent(String travelAgentName, String travelAgentAddress, String travelAgentGmail, String hotLine,
            String taxCode, Date establishmentDate, String representativeIDCard, Date dateOfIssue, String frontIDCard,
            String backIDCard, String businessLicense, int userID, String gmail, int roleID, String password,
            String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate,
            Date updateDate, int status) {
        super(userID, gmail, roleID, password, firstName, lastName, dob, gender, address, phone, createDate, updateDate, status);
        // Assign TravelAgent-specific fields
        this.travelAgentName = travelAgentName;
        this.travelAgentAddress = travelAgentAddress;
        this.travelAgentGmail = travelAgentGmail;
        this.hotLine = hotLine;
        this.taxCode = taxCode;
        this.establishmentDate = establishmentDate;
        this.representativeIDCard = representativeIDCard;
        this.dateOfIssue = dateOfIssue;
        this.frontIDCard = frontIDCard;
        this.backIDCard = backIDCard;
        this.businessLicense = businessLicense;
    }

    /**
     * Gets the path to the front side of the ID card image.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The path to the front side of the ID card image
     */
    public String getFrontIDCard() {
        return frontIDCard; // Return the stored front ID card path
    }

    /**
     * Sets the path to the front side of the ID card image.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param frontIDCard The path to the front side of the ID card image to set
     */
    public void setFrontIDCard(String frontIDCard) {
        this.frontIDCard = frontIDCard; // Assign the new front ID card path
    }

    /**
     * Gets the path to the back side of the ID card image.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The path to the back side of the ID card image
     */
    public String getBackIDCard() {
        return backIDCard; // Return the stored back ID card path
    }

    /**
     * Sets the path to the back side of the ID card image.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param backIDCard The path to the back side of the ID card image to set
     */
    public void setBackIDCard(String backIDCard) {
        this.backIDCard = backIDCard; // Assign the new back ID card path
    }

    /**
     * Gets the path to the business license image.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The path to the business license image
     */
    public String getBusinessLicense() {
        return businessLicense; // Return the stored business license path
    }

    /**
     * Sets the path to the business license image.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param businessLicense The path to the business license image to set
     */
    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense; // Assign the new business license path
    }

    /**
     * Gets the unique identifier for the travel agent.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The unique identifier for the travel agent
     */
    public int getTravelAgentID() {
        return travelAgentID; // Return the stored travel agent ID
    }

    /**
     * Sets the unique identifier for the travel agent.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param travelAgentID The unique identifier for the travel agent to set
     */
    public void setTravelAgentID(int travelAgentID) {
        this.travelAgentID = travelAgentID; // Assign the new travel agent ID
    }

    /**
     * Gets the name of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The name of the travel agency
     */
    public String getTravelAgentName() {
        return travelAgentName; // Return the stored travel agency name
    }

    /**
     * Sets the name of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param travelAgentName The name of the travel agency to set
     */
    public void setTravelAgentName(String travelAgentName) {
        this.travelAgentName = travelAgentName; // Assign the new travel agency name
    }

    /**
     * Gets the address of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The address of the travel agency
     */
    public String getTravelAgentAddress() {
        return travelAgentAddress; // Return the stored travel agency address
    }

    /**
     * Sets the address of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param travelAgentAddress The address of the travel agency to set
     */
    public void setTravelAgentAddress(String travelAgentAddress) {
        this.travelAgentAddress = travelAgentAddress; // Assign the new travel agency address
    }

    /**
     * Gets the email of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The email of the travel agency
     */
    public String getTravelAgentGmail() {
        return travelAgentGmail; // Return the stored travel agency email
    }

    /**
     * Sets the email of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param travelAgentGmail The email of the travel agency to set
     */
    public void setTravelAgentGmail(String travelAgentGmail) {
        this.travelAgentGmail = travelAgentGmail; // Assign the new travel agency email
    }

    /**
     * Gets the hotline number of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The hotline number of the travel agency
     */
    public String getHotLine() {
        return hotLine; // Return the stored hotline number
    }

    /**
     * Sets the hotline number of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param hotLine The hotline number of the travel agency to set
     */
    public void setHotLine(String hotLine) {
        this.hotLine = hotLine; // Assign the new hotline number
    }

    /**
     * Gets the tax code of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The tax code of the travel agency
     */
    public String getTaxCode() {
        return taxCode; // Return the stored tax code
    }

    /**
     * Sets the tax code of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param taxCode The tax code of the travel agency to set
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode; // Assign the new tax code
    }

    /**
     * Gets the establishment date of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The establishment date of the travel agency
     */
    public Date getEstablishmentDate() {
        return establishmentDate; // Return the stored establishment date
    }

    /**
     * Sets the establishment date of the travel agency.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param establishmentDate The establishment date of the travel agency to set
     */
    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate; // Assign the new establishment date
    }

    /**
     * Gets the ID card number of the representative.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The ID card number of the representative
     */
    public String getRepresentativeIDCard() {
        return representativeIDCard; // Return the stored representative ID card number
    }

    /**
     * Sets the ID card number of the representative.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param representativeIDCard The ID card number of the representative to set
     */
    public void setRepresentativeIDCard(String representativeIDCard) {
        this.representativeIDCard = representativeIDCard; // Assign the new representative ID card number
    }

    /**
     * Gets the date of issue for the ID card.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return The date of issue for the ID card
     */
    public Date getDateOfIssue() {
        return dateOfIssue; // Return the stored date of issue
    }

    /**
     * Sets the date of issue for the ID card.
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @param dateOfIssue The date of issue for the ID card to set
     */
    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue; // Assign the new date of issue
    }

    /**
     * Returns a string representation of the TravelAgent object.
     * Includes only TravelAgent-specific fields (excludes inherited fields from User).
     * (Write a succinct description of this method here. If necessary,
     * additional paragraphs should be preceded by <p>, the html tag for
     * a new paragraph.)
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        return "TravelAgent{" +
                "travelAgentID=" + travelAgentID +
                ", travelAgentName=" + travelAgentName +
                ", travelAgentAddress=" + travelAgentAddress +
                ", travelAgentGmail=" + travelAgentGmail +
                ", hotLine=" + hotLine +
                ", taxCode=" + taxCode +
                ", establishmentDate=" + establishmentDate +
                ", representativeIDCard=" + representativeIDCard +
                ", dateOfIssue=" + dateOfIssue +
                ", frontIDCard=" + frontIDCard +
                ", backIDCard=" + backIDCard +
                ", businessLicense=" + businessLicense + '}';
    }
}
