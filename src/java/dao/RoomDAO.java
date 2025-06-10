/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 */
package dao;

import java.sql.Connection;
import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Room;

/**
 * Data Access Object (DAO) for managing Room data in the database.
 * Implements IRoomDAO interface to provide methods for CRUD operations
 * on rooms in the TravelAgentService system.
 *
 * @author NguyenVanVang
 */
public class RoomDAO extends DBContext implements IRoomDAO {

    /**
     * Adds a new room to the database.
     *
     * @param room the Room object containing details to be inserted
     */
    @Override
    public void addRoom(Room room) {
        String sql = "INSERT INTO Room (accommodationID, roomTypes, numberOfRooms, priceOfRoom) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            // Prepare statement for inserting room data
            ps = connection.prepareStatement(sql);
            ps.setInt(1, room.getRoomID());
            ps.setString(2, room.getRoomTypes());
            ps.setInt(3, room.getNumberOfRooms());
            ps.setDouble(4, room.getPriceOfRoom());
            // Execute insert query
            ps.executeUpdate();
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close PreparedStatement to prevent resource leaks
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates an existing room in the database.
     *
     * @param accommodationID the ID of the accommodation associated with the room
     * @param roomTypes the type of the room (e.g., Phòng đơn, Phòng đôi)
     * @param numberOfRooms the number of rooms available
     * @param priceOfRoom the price per room
     */
    @Override
    public void updateRoom(int accommodationID, String roomTypes, int numberOfRooms, double priceOfRoom) {
        String sql = "UPDATE [dbo].[Room] SET [roomTypes] = ?, [numberOfRooms] = ?, [priceOfRoom] = ? WHERE [accommodationID] = ?";
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            // Prepare statement for updating room data
            ps = connection.prepareStatement(sql);
            ps.setString(1, roomTypes);
            ps.setInt(2, numberOfRooms);
            ps.setDouble(3, priceOfRoom);
            ps.setInt(4, accommodationID);
            // Execute update query
            ps.executeUpdate();
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close PreparedStatement to prevent resource leaks
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Deletes a room from the database by its accommodation ID.
     *
     * @param accommodationID the ID of the accommodation
     */
    @Override
    public void deleteRoom(int accommodationID) {
        String sql = "DELETE FROM Room WHERE accommodationID = ?";
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            // Prepare statement for deleting room
            ps = connection.prepareStatement(sql);
            ps.setInt(1, accommodationID);
            // Execute delete query
            ps.executeUpdate();
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close PreparedStatement to prevent resource leaks
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves a list of all rooms from the database.
     *
     * @return a list of Room objects
     */
    @Override
    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Room";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            // Prepare and execute query to fetch all rooms
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // Create Room object with retrieved data
                Room room = new Room(
                        rs.getInt("accommodationID"),
                        rs.getString("roomTypes"),
                        rs.getInt("numberOfRooms"),
                        rs.getFloat("priceOfRoom"));
                list.add(room);
            }
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close ResultSet and PreparedStatement to prevent resource leaks
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * Retrieves a room by its accommodation ID.
     *
     * @param id the ID of the accommodation
     * @return the Room object, or null if not found
     */
    @Override
    public Room getRoomByAccommodationID(int id) {
        String sql = "SELECT * FROM Room WHERE accommodationID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            // Prepare and execute query to fetch room by accommodation ID
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Create Room object with retrieved data
                return new Room(
                        rs.getInt("accommodationID"),
                        rs.getString("roomTypes"),
                        rs.getInt("numberOfRooms"),
                        rs.getFloat("priceOfRoom"));
            }
        } catch (SQLException e) {
            // Log any database errors
            e.printStackTrace();
        } finally {
            // Close ResultSet and PreparedStatement to prevent resource leaks
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}