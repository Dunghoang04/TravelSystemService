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
 * Defines the interface for User data access operations.<br>
 * Provides methods for CRUD operations and user authentication.<br>
 * <p>
 * Bugs: None known at this time.</p>
 *
 * @author Hà Thị Duyên
 */
package dao;

import java.util.Vector;
import model.User;
import java.sql.SQLException;

public interface IUserDAO {

    /**
     * Retrieves all users from the database based on the provided SQL
     * query.<br>
     *
     * @param sql The SQL query to execute
     * @return A Vector containing all User objects retrieved
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Declares the method to fetch all users using a custom SQL query.
     * Must be implemented by the concrete class.
     */
    Vector<User> getAllUsers(String sql) throws SQLException;

    /**
     * Inserts a new user into the database.<br>
     *
     * @param u The User object to insert
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Declares the method to insert a new user record.
     * Must be implemented by the concrete class.
     */
    void insertUser(User u) throws SQLException;

    /**
     * Updates an existing user in the database.<br>
     *
     * @param u The User object to update
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Declares the method to update an existing user record.
     * Must be implemented by the concrete class.
     */
    void updateUser(User u) throws SQLException;

    /**
     * Checks if a gmail address is already registered.<br>
     *
     * @param gmail The email address to check
     * @return true if the gmail is registered, false otherwise
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Declares the method to check if a gmail is already registered.
     * Must be implemented by the concrete class.
     */
    boolean isGmailRegister(String gmail) throws SQLException;

    /**
     * Validates user login credentials.<br>
     *
     * @param gmail The user's email address
     * @param password The user's password
     * @return The User object if login is successful, null otherwise
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Declares the method to validate user login credentials.
     * Must be implemented by the concrete class.
     */
    User checkLogin(String gmail, String password) throws SQLException;

    /**
     * Updates the password for a user.<br>
     *
     * @param gmail The user's email address
     * @param newPassword The new password to set
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Declares the method to update a user's password.
     * Must be implemented by the concrete class.
     */
    void updatePassword(String gmail, String newPassword) throws SQLException;

    /**
     * Counts the total number of users in the database.<br>
     *
     * @return The number of users
     * @throws SQLException If a database access error occurs
     */
    int countUser();

    /**
     * Counts the number of users by role ID.<br>
     *
     * @param roleId The role ID to filter by
     * @return The number of users with the specified role ID
     * @throws SQLException If a database access error occurs
     */
    int countUserByRoleID(int roleId);

    /**
     * Changes the status of a user in the database.<br>
     *
     * @param userID The ID of the user to update
     * @param newStatus The new status to set
     * @throws SQLException If a database access error occurs
     */
    void changeStatus(int userID, int newStatus) throws SQLException;

    /**
     * Retrieves a user record from the database by their user ID.
     *
     * @param userID The ID of the user to retrieve
     * @return The User object, or null if no user is found
     * @throws SQLException If a database access error occurs
     */
    User getUserByID(int userID) throws SQLException;
}
