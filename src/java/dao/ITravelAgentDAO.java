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
 * 2025-06-07  1.0        Quynh Mai         First implementation
 */
package dao;

import java.util.Vector;
import model.TravelAgent;
import java.sql.SQLException;

/**
 * ITravelAgentDAO defines the contract for data access operations related to
 * TravelAgent entities. This interface specifies methods for CRUD operations
 * (Create, Read, Update, Delete) and validation checks, throwing SQLException
 * to be handled by the upper layer (Controller). All methods interact with the
 * TravelAgent and User tables in the database to manage travel agent data.
 *
 * @author Quynh Mai
 */
public interface ITravelAgentDAO {

    /**
     * Retrieves all TravelAgent records from the database. This method queries
     * the TravelAgent table and returns all records as a Vector of TravelAgent
     * objects.
     *
     * @return Vector of TravelAgent objects containing all records
     * @throws SQLException if a database access error occurs
     */
    Vector<TravelAgent> getAllTravelAgent() throws SQLException;

    /**
     * Checks if the given email exists in the User table. This method performs
     * a lookup to verify the existence of the specified email.
     *
     * @param email The email to check
     * @return true if email exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean isEmailExists(String email) throws SQLException;

    /**
     * Checks if the given travel agent email exists in the TravelAgent table.
     * This method verifies the uniqueness of the travel agent's email address.
     *
     * @param email The travel agent email to check
     * @return true if email exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean isTravelAgentEmailExists(String email) throws SQLException;

    /**
     * Checks if the given tax code exists in the TravelAgent table. This method
     * ensures the uniqueness of the tax code for travel agents.
     *
     * @param taxCode The tax code to check
     * @return true if tax code exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean isTaxCodeExists(String taxCode) throws SQLException;

    /**
     * Checks if the given ID card exists in the TravelAgent table. This method
     * verifies the uniqueness of the representative's ID card number.
     *
     * @param idCard The ID card number to check
     * @return true if ID card exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean isIDCardExists(String idCard) throws SQLException;

    /**
     * Inserts a new TravelAgent record into the database. This method adds a
     * new travel agent entry to the TravelAgent and User tables.
     *
     * @param agent The TravelAgent object to insert
     * @throws SQLException if a database access error occurs
     */
    void insertTravelAgent(TravelAgent agent) throws SQLException;


    /**
     * Searches for a TravelAgent by their Gmail when status = 1. This method returns the first
     * matching TravelAgent record based on the Gmail.
     *
     * @param gmail The Gmail to search for
     * @return TravelAgent object if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
    TravelAgent searchTravelAgentByGmail(String gmail) throws SQLException;
    /**
     * Searches for a TravelAgent by status. This method returns the first
     * matching TravelAgent record based on the Gmail.
     *
     * @param status The Gmail to search for
     * @return TravelAgent object if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
    public Vector<TravelAgent> searchByTravelAgentByStatus(int status) throws SQLException;

    /**
     * Searches for TravelAgent records by name. This method performs a partial
     * match search on the travel agent name.
     *
     * @param name The name to search for (partial match)
     * @return Vector of matching TravelAgent objects
     * @throws SQLException if a database access error occurs
     */
    public Vector<TravelAgent> searchTravelAgentByGmailForAdmin(String gmail) throws SQLException;

    /**
     * Updates an existing TravelAgent record in the database. This method
     * modifies an existing travel agent entry in the TravelAgent and User
     * tables.
     *
     * @param agent The TravelAgent object to update
     * @throws SQLException if a database access error occurs
     */ 
    public void updateTravelAgent(TravelAgent agent, String updateType) throws SQLException;
    
    public TravelAgent searchTravelAgentByID(int id) throws SQLException;
    
    public void updateRejectionReason(int travelAgentID, String reason) throws SQLException;
    
    public void updatePassword(int userID, String password) throws SQLException;

    /**
     * Deletes a TravelAgent record from the database. This method removes a
     * travel agent entry from the TravelAgent and User tables based on ID.
     *
     * @param travelAgentID The ID of the TravelAgent to delete
     * @throws SQLException if a database access error occurs
     */
    void deleteTravelAgent(int travelAgentID) throws SQLException;

    /**
     * Changes the status of a TravelAgent in the User table. This method
     * updates the status field for the corresponding user account.
     *
     * @param userID The ID of the user to update
     * @param newStatus The new status value
     * @throws SQLException if a database access error occurs
     */
    void changeStatusTravelAgent(int userID, int newStatus) throws SQLException;

}
