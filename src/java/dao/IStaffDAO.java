/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelSystemService 
 * Description: Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24  1.0        Hà Thị Duyên      First implementation
 */

/**
 * Interface for Staff data access operations in the TravelSystemService.
 * Defines methods to manage staff data in the database.
 * <p>
 * Bugs: None known at this time.
 * </p>
 *
 * @author Hà Thị Duyên
 */
package dao;

import java.sql.SQLException;
import java.util.Vector;
import model.Staff;

public interface IStaffDAO {

    /**
     * Retrieves all staff records from the database.
     *
     * @return A Vector of Staff objects
     * @throws SQLException If a database access error occurs
     */
    Vector<Staff> getAllStaff() throws SQLException;

    /**
     * Retrieves a staff record by user ID.
     *
     * @param userID The ID of the user associated with the staff
     * @return The Staff object, or null if not found
     * @throws SQLException If a database access error occurs
     */
    Staff getStaffByUserID(int userID) throws SQLException;

    /**
     * Inserts a new staff record into the database.
     *
     * @param staff The Staff object to insert
     * @throws SQLException If a database access error occurs
     */
    void insertStaff(Staff staff) throws SQLException;

    /**
     * Updates an existing staff record in the database.
     *
     * @param staff The Staff object with updated information
     * @throws SQLException If a database access error occurs
     */
    void updateStaff(Staff staff) throws SQLException;

    /**
     * Soft deletes a staff record by setting their status to 0.
     *
     * @param staffID The ID of the staff to delete
     * @throws SQLException If a database access error occurs
     */
    void deleteStaff(int staffID) throws SQLException;

    /**
     * Changes the status of a staff member.
     *
     * @param staffID The ID of the staff member
     * @param newStatus The new status (1 for active, 0 for inactive)
     * @throws SQLException If a database access error occurs
     */
    void changeStatus(int staffID, int newStatus) throws SQLException;

    /**
     * Checks if an employee code is already registered.
     *
     * @param employeeCode The employee code to check
     * @return true if the employee code is registered, false otherwise
     */
    boolean isEmployeeCodeRegister(String employeeCode);

    /**
     * Checks if a Gmail address is already registered.
     *
     * @param gmail The Gmail address to check
     * @return true if the Gmail is registered, false otherwise
     * @throws SQLException If a database access error occurs
     */
    boolean isGmailRegister(String gmail) throws SQLException;
}