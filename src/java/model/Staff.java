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
 * 2025-06-015  1.0        Hà Thị Duyên      First implementation
 */
/**
 * Staff represents a staff member in the system, extending the User class.
 * This class maps to the Staff and User tables in the database, containing
 * staff-specific details such as employee code and hire date.
 *
 * @author Hà Thị Duyên
 */
package model;

import java.sql.Date;

public class Staff extends User {

    private int staffID; // Unique identifier for the staff member
    private int userID;// Identifier linking to the User table
    private String employeeCode;    // Unique code assigned to the employee
    private Date hireDate;    // Date when the staff member was hired
    private String workStatus;    // Current employment status (e.g., active, inactive)

    /**
     * Default constructor for Staff. Initializes a new Staff instance with
     * default values.
     */
    public Staff() {
    }

    /**
     * Parameterized constructor for Staff. Initializes a Staff instance with
     * all attributes, including inherited fields from User.
     *
     * @param staffID Unique identifier for the staff
     * @param userID Identifier linking to the User table
     * @param employeeCode Unique employee code
     * @param hireDate Date of hiring
     * @param workStatus Current work status
     * @param gmail User's email address
     * @param roleID User's role identifier
     * @param password User's password
     * @param firstName User's first name
     * @param lastName User's last name
     * @param dob User's date of birth
     * @param gender User's gender
     * @param address User's address
     * @param phone User's phone number
     * @param createDate Date when the user account was created
     * @param updateDate Date when the user account was last updated
     * @param status User's account status
     */
    public Staff(int staffID, int userID, String employeeCode, Date hireDate, String workStatus,
            String gmail, int roleID, String password, String firstName, String lastName,
            Date dob, String gender, String address, String phone, Date createDate,
            Date updateDate, int status) {
        super(gmail, password, firstName, lastName, dob, gender, address, phone,
                createDate, updateDate, status, roleID);
        this.staffID = staffID;
        this.userID = userID;
        this.employeeCode = employeeCode;
        this.hireDate = hireDate;
        this.workStatus = workStatus;
    }

    // Getter and setter methods for staffID
    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    // Getter and setter methods for userID
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    // Getter and setter methods for employeeCode
    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    // Getter and setter methods for hireDate
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    // Getter and setter methods for workStatus
    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    /**
     * Returns a string representation of the Staff object. Includes only
     * Staff-specific fields.
     *
     * @return A string containing staff details
     */
    @Override
    public String toString() {
        return "Staff{"
                + "staffID=" + staffID
                + ", userID=" + userID
                + ", employeeCode='" + employeeCode + '\''
                + ", hireDate=" + hireDate
                + ", workStatus='" + workStatus + '\''
                + ", user=" + super.toString()
                + // gọi toString() từ lớp User
                '}';
    }

}
