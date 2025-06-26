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
 * 2025-06-14  1.0        Quynh Mai         Second implement
 */
package dao;

import java.sql.SQLException;
import java.util.Vector;
import model.Tour;

/**
 * Interface for data access operations on Tour entities.
 * Defines methods for CRUD operations, filtering, and status management of tours.
 * All methods throw SQLException to handle database errors.
 *
 * @author Quynh Mai
 */
public interface ITourDAO {

    /**
     * Retrieves distinct start places from the Tour table.
     *
     * @return A Vector of unique start place strings
     * @throws SQLException If a database error occurs
     */
    Vector<String> getUniqueStartPlaces() throws SQLException;

    /**
     * Retrieves distinct end places from the Tour table.
     *
     * @return A Vector of unique end place strings
     * @throws SQLException If a database error occurs
     */
    Vector<String> getUniqueEndPlaces() throws SQLException;

    /**
     * Retrieves the total number of tours in the database.
     *
     * @return The total count of tours
     * @throws SQLException If a database error occurs
     */
    int getTotalTours() throws SQLException;

    /**
     * Retrieves all tours from the database.
     *
     * @return A Vector containing all Tour objects
     * @throws SQLException If a database error occurs
     */
    Vector<Tour> getAllTours(int page, int pageSize) throws SQLException;

    /**
     * Searches for tours by destination (end place).
     *
     * @param destination The destination to search for (partial match)
     * @return A Vector of matching Tour objects
     * @throws SQLException If a database error occurs
     */
    Vector<Tour> searchToursByDestination(String destination, int page, int pageSize) throws SQLException;

    /**
     * Retrieves the total number of tours matching the destination.
     *
     * @param destination The destination to search for (partial match)
     * @return The total count of matching tours
     * @throws SQLException If a database error occurs
     */
    int getTotalToursByDestination(String destination) throws SQLException;
    
    /**
     * Retrieves tours based on multiple filter criteria.
     *
     * @param budget The budget range (e.g., "under5", "5-10")
     * @param departure The start place
     * @param destination The end place
     * @param departureDate The departure date
     * @param tourCategory The tour category ID
     * @return A Vector of filtered Tour objects
     * @throws SQLException If a database error occurs
     */
    Vector<Tour> getToursWithFilters(String budget, String departure, String destination, 
                                     String departureDate, String tourCategory, int page, int pageSize) throws SQLException;

    /**
     * Retrieves the total number of tours matching the filter criteria.
     *
     * @param budget The budget range (e.g., "under5", "5-10")
     * @param departure The start place
     * @param destination The end place
     * @param departureDate The departure date
     * @param tourCategory The tour category ID
     * @return The total count of matching tours
     * @throws SQLException If a database error occurs
     */
    int getTotalToursWithFilters(String budget, String departure, String destination, 
                                 String departureDate, String tourCategory) throws SQLException; 
    /**
     * Updates the quantity of a tour.
     *
     * @param tourID The ID of the tour
     * @param quantity The quantity to subtract
     * @throws SQLException If a database error occurs
     */
    void updateQuantity(int tourID, int quantity) throws SQLException;

    /**
     * Searches for tours by tour name.
     *
     * @param searchQuery The query to search for (partial match)
     * @return A Vector of matching Tour objects
     * @throws SQLException If a database error occurs
     */
    Vector<Tour> searchToursByName(String searchQuery, int page, int pageSize) throws SQLException;

    /**
     * Retrieves the top 3 newest tours.
     *
     * @return A Vector of the top 3 newest Tour objects
     * @throws SQLException If a database error occurs
     */
    Vector<Tour> getTopNewTour() throws SQLException;

    /**
     * Inserts a new tour into the database.
     *
     * @param tour The Tour object to insert
     * @throws SQLException If a database error occurs
     */
    
    
    public int getTotalTourForSearch() throws SQLException;
    void insertTour(Tour tour) throws SQLException;


    /**
     * Searches for a tour by its ID.
     *
     * @param tourId The ID of the tour to search for
     * @return The Tour object if found, null otherwise
     * @throws SQLException If a database error occurs
     */
    Tour searchTourByID(int tourId) throws SQLException;

    /**
     * Updates an existing tour in the database.
     *
     * @param tour The Tour object with updated details
     * @throws SQLException If a database error occurs
     */
    void updateTour(Tour tour) throws SQLException;

    /**
     * Changes the status of a tour in the database.
     *
     * @param tourId The ID of the tour to update
     * @param newStatus The new status value (e.g., 0 for inactive, 1 for active)
     * @throws SQLException If a database error occurs
     * @author Quynh Mai
     */
    void changeStatusTour(int tourId, int newStatus) throws SQLException;

    /**
     * Retrieves tours from the database based on their status.
     *
     * @param status The status of the tours to retrieve (e.g., 0 for inactive, 1 for active)
     * @return A Vector containing matching Tour objects
     * @throws SQLException If a database error occurs
     * @author Quynh Mai
     */
    int deleteTour(int tourId) throws SQLException;
    
    /**
     * Searches for a tour by its ID.
     *
     * @param tourId The ID of the tour to search for
     * @return The Tour object if found, null otherwise
     * @throws SQLException If a database error occurs
     */
    Tour searchTour(int tourId) throws SQLException;
    
    Vector<Tour> searchTourByStatus(int status) throws SQLException;
    Vector<Tour> getAllTours() throws SQLException ;
    
    boolean updateQuantityAfterBooking(int tourID, int numberHuman);

}

