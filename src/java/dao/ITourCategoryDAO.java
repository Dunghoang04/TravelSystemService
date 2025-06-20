/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai         First implementation
 */
package dao;

import java.sql.SQLException;
import java.util.Vector;
import model.TourCategory;

/**
 * Interface for data access operations on TourCategory entities.
 * Defines methods for CRUD operations and status management of tour categories.
 * All methods throw SQLException to handle database errors.
 */
public interface ITourCategoryDAO {

    /**
     * Retrieves all TourCategory records from the database.
     * @return Vector of TourCategory objects
     * @throws SQLException if database access fails
     */
    Vector<TourCategory> getAllTourCategory() throws SQLException;

    /**
     * Inserts a new TourCategory into the database.
     * @param category The TourCategory object to insert
     * @throws SQLException if database insertion fails
     */
    void insertTourCategory(TourCategory category) throws SQLException;

    /**
     * Searches for a TourCategory by its ID.
     * @param id The ID of the TourCategory to search
     * @return TourCategory object if found, null otherwise
     * @throws SQLException if database access fails
     */
    TourCategory searchTourCategory(int id) throws SQLException;

    /**
     * Updates an existing TourCategory in the database.
     * @param category The TourCategory object to update
     * @throws SQLException if database update fails
     */
    void updateTourCategory(TourCategory category) throws SQLException;

    /**
     * Changes the status of a TourCategory in the database.
     * @param tourCategoryID The ID of the TourCategory
     * @param newStatus The new status to set
     * @throws SQLException if database update fails
     */
    void changeStatusTourCategory(int tourCategoryID, int newStatus) throws SQLException;

    /**
     * Deletes a TourCategory from the database if no tours are linked.
     * @param tourCategoryID The ID of the TourCategory to delete
     * @return Number of affected rows (0 if disabled due to linked tours)
     * @throws SQLException if database deletion fails
     */
    int deleteTourCategory(int tourCategoryID) throws SQLException;
}