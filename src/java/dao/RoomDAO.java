/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-08  1.0        Nguyễn Văn Vang   First implementation
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Room;

/**
 * Implements data access operations for Room entities.<br>
 * Handles CRUD operations (create, read, update, delete) for rooms.<br>
 * <p>
 * Bugs: Potential SQL injection risk if user input is used directly in SQL
 * queries.</p>
 *
 * @author Nguyễn Văn Vang
 */
public class RoomDAO extends DBContext implements IRoomDAO {

    /**
     * Retrieves all rooms from the database.<br>
     *
     * @return A List containing all Room objects retrieved
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Executes a SQL query to fetch all rooms.
     * Creates a List to store Room objects and populates it from the ResultSet.
     */
    @Override
    public List<Room> getAllRooms() throws SQLException {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Room";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            rs = ptm.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("roomID"),
                        rs.getInt("accommodationID"),
                        rs.getString("roomTypes"),
                        rs.getInt("numberOfRooms"),
                        rs.getFloat("priceOfRoom"),
                        rs.getInt("status")
                );
                list.add(room);
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
        return list;
    }

    /**
     * Inserts a new room into the database.<br>
     *
     * @param room The Room object to insert
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Inserts a new room record into the Room table.
     * Sets the generated roomID back to the Room object.
     */
    @Override
    public void addRoom(Room room) throws SQLException {
        String sql = "INSERT INTO Room (accommodationID, roomTypes, numberOfRooms, priceOfRoom, status) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptm.setInt(1, room.getAccommodationID());
            ptm.setString(2, room.getRoomTypes());
            ptm.setInt(3, room.getNumberOfRooms());
            ptm.setFloat(4, room.getPriceOfRoom());
            ptm.setInt(5, room.getStatus());
            ptm.executeUpdate();

            rs = ptm.getGeneratedKeys();
            if (rs.next()) {
                room.setRoomID(rs.getInt(1));
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Updates an existing room in the database.<br>
     *
     * @param roomID The ID of the room to update
     * @param roomTypes The type of the room
     * @param numberOfRooms The number of rooms available
     * @param priceOfRoom The price of the room
     * @param status The status of the room
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Updates an existing room record in the Room table.
     * Matches by roomID.
     */
    @Override
    public void updateRoom(int roomID, String roomTypes, int numberOfRooms, float priceOfRoom, int status) throws SQLException {
        String sql = "UPDATE [dbo].[Room] SET [roomTypes] = ?, [numberOfRooms] = ?, [priceOfRoom] = ?, [status] = ? WHERE [roomID] = ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setString(1, roomTypes);
            ptm.setInt(2, numberOfRooms);
            ptm.setFloat(3, priceOfRoom);
            ptm.setInt(4, status);
            ptm.setInt(5, roomID);
            ptm.executeUpdate();
        } finally {
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Retrieves a room by its ID.<br>
     *
     * @param roomID The ID of the room to retrieve
     * @return The Room object if found, null otherwise
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Queries the database to fetch a room by its roomID.
     * Returns a Room object if found, null otherwise.
     */
    @Override
    public Room getRoomByRoomID(int roomID) throws SQLException {
        String sql = "SELECT * FROM Room WHERE roomID=?";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setInt(1, roomID);
            rs = ptm.executeQuery();
            if (rs.next()) {
                return new Room(
                        rs.getInt("roomID"),
                        rs.getInt("accommodationID"),
                        rs.getString("roomTypes"),
                        rs.getInt("numberOfRooms"),
                        rs.getFloat("priceOfRoom"),
                        rs.getInt("status")
                );
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all rooms for a specific accommodation ID.<br>
     *
     * @param accommodationID The ID of the accommodation
     * @return A List containing all Room objects for the accommodation
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Queries the database to fetch all rooms for a given accommodationID.
     * Returns a List of Room objects.
     */
    @Override
    public List<Room> getRoomsByAccommodationID(int accommodationID) throws SQLException {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Room WHERE accommodationID=?";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setInt(1, accommodationID);
            rs = ptm.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("roomID"),
                        rs.getInt("accommodationID"),
                        rs.getString("roomTypes"),
                        rs.getInt("numberOfRooms"),
                        rs.getFloat("priceOfRoom"),
                        rs.getInt("status")
                );
                list.add(room);
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
        return list;
    }

    /**
     * Main method for testing RoomDAO methods.<br>
     * Tests addRoom, updateRoom, getRoomByRoomID, and
     * getRoomsByAccommodationID.
     *
     * @param args Command-line arguments (not used)
     */
    /* 
     * Tests RoomDAO methods with sample data.
     * Prints results to the console or handles SQLException.
     */
    public static void main(String[] args) {
        RoomDAO dao = new RoomDAO();
        try {
            Room room = new Room(0, 1, "Deluxe", 5, 150.0f, 1);
            dao.addRoom(room);
            System.out.println("Added room with roomID: " + room.getRoomID());

            dao.updateRoom(room.getRoomID(), "Suite", 3, 200.0f, 1);
            System.out.println("Updated room with roomID: " + room.getRoomID());

            Room retrievedRoom = dao.getRoomByRoomID(room.getRoomID());
            if (retrievedRoom != null) {
                System.out.println("Retrieved room by roomID: " + retrievedRoom);
            } else {
                System.out.println("Room not found by roomID");
            }

            List<Room> rooms = dao.getRoomsByAccommodationID(1);
            if (!rooms.isEmpty()) {
                System.out.println("Retrieved rooms by accommodationID: " + rooms);
            } else {
                System.out.println("No rooms found for accommodationID 1");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}