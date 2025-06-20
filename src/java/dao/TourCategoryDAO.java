/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai          Initial implementation
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.TourCategory;

/**
 * Data Access Object for managing TourCategory entities.
 * Extends DBContext for database connectivity and implements ITourCategoryDAO.
 * Provides CRUD operations and status management for the Tour_Category table.
 * Ensures proper resource management and detailed error handling.
 *
 * @author Nhat Anh
 */
public class TourCategoryDAO extends DBContext implements ITourCategoryDAO {

    /**
     * Retrieves all tour categories from the database.
     *
     * @return A Vector containing all TourCategory objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<TourCategory> getAllTourCategory() throws SQLException {
        Vector<TourCategory> list = new Vector<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tour_Category";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare SQL query
            rs = ptm.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                TourCategory category = new TourCategory(
                        rs.getInt("tourCategoryID"),
                        rs.getString("tourCategoryName")
                );
                list.add(category); // Add category to result list
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve tour categories: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return list;
    }

    /**
     * Inserts a new tour category into the database.
     *
     * @param category The TourCategory object to insert
     * @throws SQLException If a database error occurs
     */
    @Override
    public void insertTourCategory(TourCategory category) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        String sql = "INSERT INTO [dbo].[Tour_Category] ([tourCategoryName]) VALUES (?)";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare SQL query
            ptm.setString(1, category.getTourCategoryName()); // Set category name
            ptm.executeUpdate(); // Execute insert
        } catch (SQLException ex) {
            throw new SQLException("Failed to insert tour category: " + ex.getMessage(), ex);
        } finally {
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
    }

    /**
     * Searches for a tour category by its ID.
     *
     * @param id The ID of the tour category to search for
     * @return The TourCategory object if found, null otherwise
     * @throws SQLException If a database error occurs
     */
    @Override
    public TourCategory searchTourCategory(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tour_Category WHERE tourCategoryID = ?";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare SQL query
            ptm.setInt(1, id); // Set category ID
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) { // Process result
                return new TourCategory(
                        rs.getInt("tourCategoryID"),
                        rs.getString("tourCategoryName")
                );
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to search tour category by ID: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return null;
    }

    /**
     * Updates an existing tour category in the database.
     *
     * @param category The TourCategory object with updated details
     * @throws SQLException If a database error occurs
     */
    @Override
    public void updateTourCategory(TourCategory category) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        String sql = "UPDATE [dbo].[Tour_Category] SET [tourCategoryName] = ? WHERE tourCategoryID = ?";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare SQL query
            ptm.setString(1, category.getTourCategoryName()); // Set category name
            ptm.setInt(2, category.getTourCategoryID()); // Set category ID
            ptm.executeUpdate(); // Execute update
        } catch (SQLException ex) {
            throw new SQLException("Failed to update tour category: " + ex.getMessage(), ex);
        } finally {
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
    }

    /**
     * Changes the status of a tour category.
     *
     * @param tourCategoryID The ID of the tour category
     * @param newStatus The new status value (e.g., 0 for inactive, 1 for active)
     * @throws SQLException If a database error occurs
     */
    @Override
    public void changeStatusTourCategory(int tourCategoryID, int newStatus) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        String sql = "UPDATE [dbo].[Tour_Category] SET [status] = ? WHERE tourCategoryID = ?";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare SQL query
            ptm.setInt(1, newStatus); // Set new status
            ptm.setInt(2, tourCategoryID); // Set category ID
            ptm.executeUpdate(); // Execute update
        } catch (SQLException ex) {
            throw new SQLException("Failed to change tour category status: " + ex.getMessage(), ex);
        } finally {
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
    }

    /**
     * Deletes a tour category if no tours are associated with it.
     * If associated tours exist, the category is deactivated instead.
     *
     * @param tourCategoryID The ID of the tour category to delete
     * @return Number of affected rows (0 if deactivated due to linked tours)
     * @throws SQLException If a database error occurs
     */
    @Override
    public int deleteTourCategory(int tourCategoryID) throws SQLException {
        Connection conn = null;
        PreparedStatement checkPtm = null;
        PreparedStatement deletePtm = null;
        ResultSet rs = null;
        int affectedRows = 0;
        String checkSql = "SELECT * FROM Tour WHERE tourCategoryID = ?";
        String deleteSql = "DELETE FROM [dbo].[Tour_Category] WHERE tourCategoryID = ?";
        try {
            conn = getConnection(); // Establish database connection
            checkPtm = conn.prepareStatement(checkSql); // Prepare check query
            checkPtm.setInt(1, tourCategoryID); // Set category ID
            rs = checkPtm.executeQuery(); // Execute check query
            if (rs.next()) { // If linked tours exist
                changeStatusTourCategory(tourCategoryID, 0); // Deactivate category
                return affectedRows;
            }
            deletePtm = conn.prepareStatement(deleteSql); // Prepare delete query
            deletePtm.setInt(1, tourCategoryID); // Set category ID
            affectedRows = deletePtm.executeUpdate(); // Execute delete
        } catch (SQLException ex) {
            throw new SQLException("Failed to delete tour category: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (checkPtm != null) checkPtm.close(); // Close check PreparedStatement
            if (deletePtm != null) deletePtm.close(); // Close delete PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return affectedRows;
    }

    /**
     * Main method for testing TourCategoryDAO functionality.
     * Retrieves and prints all tour categories.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        TourCategoryDAO dao = new TourCategoryDAO();
        try {
            Vector<TourCategory> list = dao.getAllTourCategory();
            for (TourCategory category : list) {
                System.out.println(category);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Print stack trace for debugging
        }
    }
}
