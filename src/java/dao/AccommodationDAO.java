/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 * 2025-06-08  1.1       TuanAnhJr         Updated serviceID to serviceId for naming consistency with database
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Accommodation;
import model.Room;

/**
 * Data Access Object (DAO) for managing Accommodation data in the database.
 * Implements IAccommodationDAO interface to provide methods for CRUD operations
 * and additional functionality for accommodations in the TravelAgentService system.
 *
 * @author NguyenVanVang
 */
public class AccommodationDAO extends DBContext implements IAccommodationDAO {

    /**
     * Inserts a new service into the database and returns its generated ID.
     *
     * @param serviceName the name of the service to be inserted
     * @return the generated service ID
     * @throws SQLException if a database error occurs during insertion
     */
    @Override
    public int insertService(String serviceName) throws SQLException {
        String sql = "INSERT INTO [dbo].[Service] ([serviceCategoryID], [serviceName]) VALUES (?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            // Prepare statement with RETURN_GENERATED_KEYS to retrieve the inserted service ID
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 3); // Hardcoded service category ID for accommodation
            ps.setString(2, serviceName);
            ps.executeUpdate();

            // Retrieve the generated service ID
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve serviceId after inserting Service.");
            }
        } finally {
            // Close ResultSet and PreparedStatement to prevent resource leaks
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Retrieves a list of all accommodations from the database.
     *
     * @return a list of Accommodation objects
     */
    @Override
    public List<Accommodation> getListAccommodation() {
        String sql = "SELECT * FROM Accommodation";
        List<Accommodation> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            // Execute query to fetch all accommodations
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // Format check-in and check-out times to HH:mm
                Time timeOpen = rs.getTime("checkInTime");
                Time timeClose = rs.getTime("checkOutTime");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                // Create Accommodation object with retrieved data
                Accommodation acc = new Accommodation(
                        rs.getInt("serviceId"),
                        rs.getInt("roomID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        sdf.format(timeOpen),
                        sdf.format(timeClose));
                list.add(acc);
            }
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close ResultSet and PreparedStatement to prevent resource leaks
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }

    /**
     * Inserts a new accommodation into the database.
     *
     * @param roomID the ID of the associated room
     * @param name the name of the accommodation
     * @param image the image URL or path for the accommodation
     * @param address the address of the accommodation
     * @param phone the contact phone number
     * @param description the description of the accommodation
     * @param rate the rating of the accommodation
     * @param type the type of accommodation
     * @param status the status of the accommodation (e.g., available or unavailable)
     * @param checkInTime the check-in time in HH:mm format
     * @param checkOutTime the check-out time in HH:mm format
     */
    @Override
    public void insertAccommodation(int roomID, String name, String image, String address, String phone,
            String description, float rate, String type, int status, String checkInTime, String checkOutTime) {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            // Disable auto-commit to ensure transaction integrity
            connection.setAutoCommit(false);
            
            // Insert service and get generated service ID
            int serviceId = insertService("Accommodation Service");

            // Prepare SQL for inserting accommodation
            String sql = "INSERT INTO [dbo].[Accommodation] ([serviceId], [roomID], [name], [image], [address], " +
                         "[phone], [description], [rate], [type], [status], [checkInTime], [checkOutTime]) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceId);
            ps.setInt(2, roomID);
            ps.setString(3, name);
            ps.setString(4, image);
            ps.setString(5, address);
            ps.setString(6, phone);
            ps.setString(7, description);
            ps.setFloat(8, rate);
            ps.setString(9, type);
            ps.setInt(10, status);
            ps.setTime(11, Time.valueOf(checkInTime));
            ps.setTime(12, Time.valueOf(checkOutTime));
            ps.executeUpdate();
            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaction on error
            try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            // Close PreparedStatement and restore auto-commit
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Deletes an accommodation and its associated service from the database.
     *
     * @param serviceId the ID of the service to delete
     */
    @Override
    public void deleteAccommodation(int serviceId) {
        String sqlAcc = "DELETE FROM [dbo].[Accommodation] WHERE serviceId = ?";
        String sqlService = "DELETE FROM [dbo].[Service] WHERE serviceId = ?";
        PreparedStatement psAcc = null;
        PreparedStatement psService = null;
        Connection connection = null;
        try {
            // Disable auto-commit for transaction
            connection.setAutoCommit(false);
            // Delete from Accommodation table
            psAcc = connection.prepareStatement(sqlAcc);
            psAcc.setInt(1, serviceId);
            psAcc.executeUpdate();

            // Delete from Service table
            psService = connection.prepareStatement(sqlService);
            psService.setInt(1, serviceId);
            psService.executeUpdate();

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaction on error
            try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            // Close PreparedStatements and restore auto-commit
            if (psAcc != null) try { psAcc.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (psService != null) try { psService.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Retrieves an accommodation by its service ID.
     *
     * @param serviceId the ID of the service
     * @return the Accommodation object, or null if not found
     */
    @Override
    public Accommodation getAccommodationByServiceId(int serviceId) {
        String sql = "SELECT * FROM Accommodation WHERE serviceId = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            // Execute query to fetch accommodation by service ID
            ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Format check-in and check-out times to HH:mm
                Time timeOpen = rs.getTime("checkInTime");
                Time timeClose = rs.getTime("checkOutTime");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                // Create Accommodation object with retrieved data
                Accommodation acc = new Accommodation(
                        rs.getInt("serviceId"),
                        rs.getInt("roomID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        sdf.format(timeOpen),
                        sdf.format(timeClose));
                return acc;
            }
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close ResultSet and PreparedStatement
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    /**
     * Updates an existing accommodation in the database.
     *
     * @param serviceId the ID of the service
     * @param roomID the ID of the associated room
     * @param name the name of the accommodation
     * @param image the image URL or path
     * @param address the address of the accommodation
     * @param phone the contact phone number
     * @param description the description of the accommodation
     * @param rate the rating of the accommodation
     * @param type the type of accommodation
     * @param status the status of the accommodation
     * @param checkInTime the check-in time in HH:mm format
     * @param checkOutTime the check-out time in HH:mm format
     */
    @Override
    public void updateAccommodation(int serviceId, int roomID, String name, String image, String address, String phone,
            String description, float rate, String type, int status, String checkInTime, String checkOutTime) {
        String sql = "UPDATE [dbo].[Accommodation] SET [serviceId] = ?, [roomID] = ?, [name] = ?, [image] = ?, " +
                     "[address] = ?, [phone] = ?, [description] = ?, [rate] = ?, [type] = ?, [status] = ?, " +
                     "[checkInTime] = ?, [checkOutTime] = ? WHERE serviceId = ?";
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            // Prepare and execute update statement
            ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceId);
            ps.setInt(2, roomID);
            ps.setString(3, name);
            ps.setString(4, image);
            ps.setString(5, address);
            ps.setString(6, phone);
            ps.setString(7, description);
            ps.setFloat(8, rate);
            ps.setString(9, type);
            ps.setInt(10, status);
            ps.setTime(11, Time.valueOf(checkInTime));
            ps.setTime(12, Time.valueOf(checkOutTime));
            ps.setInt(13, serviceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close PreparedStatement
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Searches for accommodations by name (partial match).
     *
     * @param name the name or partial name to search for
     * @return a list of matching Accommodation objects
     */
    @Override
    public List<Accommodation> searchAccommodationByName(String name) {
        String sql = "SELECT * FROM Accommodation WHERE name LIKE ?";
        List<Accommodation> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            // Prepare query with wildcard for partial name search
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                // Format check-in and check-out times to HH:mm
                Time checkInTime = rs.getTime("checkInTime");
                Time checkOutTime = rs.getTime("checkOutTime");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                // Create Accommodation object with retrieved data
                Accommodation acc = new Accommodation(
                        rs.getInt("serviceId"),
                        rs.getInt("roomID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        sdf.format(checkInTime),
                        sdf.format(checkOutTime));
                list.add(acc);
            }
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close ResultSet and PreparedStatement
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }

    /**
     * Retrieves a room by its accommodation ID.
     *
     * @param accommodationID the ID of the accommodation
     * @return the Room object, or null if not found
     */
    @Override
    public Room getRoomById(int accommodationID) {
        String sql = "SELECT * FROM Room WHERE accommodationID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            // Execute query to fetch room by accommodation ID
            ps = connection.prepareStatement(sql);
            ps.setInt(1, accommodationID);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Create Room object with retrieved data
                Room room = new Room(
                        rs.getInt("accommodationID"),
                        rs.getString("roomTypes"),
                        rs.getInt("numberOfRooms"),
                        rs.getFloat("priceOfRoom"));
                return room;
            }
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close ResultSet and PreparedStatement
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    /**
     * Retrieves the status of an accommodation by its service ID.
     *
     * @param serviceId the ID of the service
     * @return the status value, or -1 if not found
     */
    @Override
    public int getStatusByServiceId(int serviceId) {
        String sql = "SELECT status FROM [dbo].[Accommodation] WHERE serviceId = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int status = -1;
        Connection connection = null;
        try {
            // Execute query to fetch status by service ID
            ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getInt("status");
            }
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close ResultSet and PreparedStatement
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return status;
    }

    /**
     * Changes the status of an accommodation.
     *
     * @param serviceId the ID of the service
     * @param status the new status value
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean changeStatus(int serviceId, int status) {
        String sql = "UPDATE [dbo].[Accommodation] SET [status] = ? WHERE serviceId = ?";
        PreparedStatement ps = null;
        boolean success = false;
        Connection connection = null;
        try {
            // Execute update to change status
            ps = connection.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, serviceId);
            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close PreparedStatement
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return success;
    }
}