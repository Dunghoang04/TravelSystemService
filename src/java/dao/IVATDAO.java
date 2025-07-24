/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 17-07-2025  1.0        Hà Thị Duyên     First implementation
 */
package dao;

import java.sql.Date;
import java.util.Vector;
import model.VAT;
import java.sql.SQLException;

/**
 * The IVATDAO interface defines methods for managing VAT (Value Added Tax) records in the database.
 * It provides operations for retrieving, inserting, updating, and checking VAT records.
 * Implementing classes must handle database connectivity and ensure proper exception handling.
 * <p>Bugs: None identified at this time.
 *
 * @author Hà Thị Duyên
 */
public interface IVATDAO {

    /**
     * Retrieves a list of VAT records based on the provided SQL query.
     *
     * @param sql the SQL query to execute
     * @return a <code>Vector<VAT></code> containing the retrieved VAT records
     * @throws SQLException if a database error occurs
     */
    Vector<VAT> getAllVAT(String sql) throws SQLException;

    /**
     * Retrieves the currently active VAT record based on status and date range.
     *
     * @return a <code>VAT</code> object representing the active VAT, or <code>null</code> if no active VAT is found
     */
    VAT getVATActive();

    /**
     * Inserts a new VAT record into the database.
     *
     * @param vat the <code>VAT</code> object to insert
     * @throws SQLException if a database error occurs
     */
    void insertVAT(VAT vat) throws SQLException;

    /**
     * Changes the status of a VAT record identified by its ID.
     *
     * @param vatID the ID of the VAT record to update
     * @param status the new status value (0 for inactive, 1 for active)
     * @throws SQLException if a database error occurs
     */
    void changeVATStatus(int vatID, int status) throws SQLException;

    /**
     * Retrieves a VAT record by its ID from the database.
     *
     * @param vatID the ID of the VAT record to retrieve
     * @return a <code>VAT</code> object representing the VAT record, or <code>null</code> if not found
     * @throws SQLException if a database error occurs
     */
    VAT getVATByID(int vatID) throws SQLException;

    /**
     * Checks if a VAT record with the given rate and date range overlaps with any active VAT records.
     *
     * @param vatRate the VAT rate to check
     * @param startDate the start date of the VAT period
     * @param endDate the end date of the VAT period
     * @param excludeVatID the ID of the VAT record to exclude from the overlap check
     * @return <code>true</code> if an overlap is found, <code>false</code> otherwise
     * @throws SQLException if a database error occurs
     */
    boolean checkOverlappingVAT(double vatRate, Date startDate, Date endDate, int excludeVatID) throws SQLException;

    /**
     * Deactivates all active VAT records that are still valid as of the current date.
     *
     * @throws SQLException if a database error occurs
     */
    void deactivateAllActiveVATs() throws SQLException;
    Vector<VAT> getOverlappingVATs(Date startDate, Date endDate, int excludeVatID) throws SQLException;
     void updateExpiredVATs() throws SQLException;
}