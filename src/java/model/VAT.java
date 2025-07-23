/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 17-07-2025  1.0        Hà Thị Duyên     First implementation
 */
package model;

import java.sql.Date;

/**
 * The VAT class represents a Value Added Tax (VAT) record in the system.
 * It encapsulates attributes such as VAT ID, rate, description, start and end dates, status, and creation/update dates.
 * This class provides getter and setter methods for accessing and modifying these attributes.
 * <p>Bugs: None identified at this time.
 *
 * @author Hà Thị Duyên
 */
public class VAT {

    private int vatID;
    private double vatRate;
    private String description;
    private Date startDate;
    private Date endDate;
    private int status;
    private Date createDate;
    private Date updateDate;

    /**
     * Default constructor for the VAT class.
     * Initializes a new VAT object with default values.
     */
    public VAT() {
    }

    /**
     * Parameterized constructor for the VAT class.
     * Initializes a VAT object with the specified attributes.
     *
     * @param vatID the unique identifier for the VAT record
     * @param vatRate the VAT rate as a percentage
     * @param description a brief description of the VAT record
     * @param startDate the start date of the VAT's validity period
     * @param endDate the end date of the VAT's validity period
     * @param status the status of the VAT (0 for inactive, 1 for active)
     * @param createDate the date when the VAT record was created
     * @param updateDate the date when the VAT record was last updated
     */
    public VAT(int vatID, double vatRate, String description, Date startDate, Date endDate, int status, Date createDate, Date updateDate) {
        this.vatID = vatID;
        this.vatRate = vatRate;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    /**
     * Gets the unique identifier of the VAT record.
     *
     * @return the VAT ID
     */
    public int getVatID() {
        return vatID;
    }

    /**
     * Sets the unique identifier of the VAT record.
     *
     * @param vatID the VAT ID to set
     */
    public void setVatID(int vatID) {
        this.vatID = vatID;
    }

    /**
     * Gets the VAT rate as a percentage.
     *
     * @return the VAT rate
     */
    public double getVatRate() {
        return vatRate;
    }

    /**
     * Sets the VAT rate as a percentage.
     *
     * @param vatRate the VAT rate to set
     */
    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    /**
     * Gets the description of the VAT record.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the VAT record.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the start date of the VAT's validity period.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the VAT's validity period.
     *
     * @param startDate the start date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the VAT's validity period.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the VAT's validity period.
     *
     * @param endDate the end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the status of the VAT record.
     *
     * @return the status (0 for inactive, 1 for active)
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the VAT record.
     *
     * @param status the status to set (0 for inactive, 1 for active)
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the creation date of the VAT record.
     *
     * @return the creation date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the VAT record.
     *
     * @param createDate the creation date to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the last update date of the VAT record.
     *
     * @return the last update date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the last update date of the VAT record.
     *
     * @param updateDate the update date to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Returns a string representation of the VAT object.
     *
     * @return a string containing the VAT's attributes
     */
    @Override
    public String toString() {
        return "VAT{" + "vatID=" + vatID + ", vatRate=" + vatRate + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", createDate=" + createDate + ", updateDate=" + updateDate + '}';
    }
}