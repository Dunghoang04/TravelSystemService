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

import model.Room;
import java.sql.SQLException;
import java.util.List;

/**
 * Defines the interface for Room data access operations.<br>
 * Provides methods for CRUD operations on room entities.<br>
 * <p>
 * Bugs: None known at this time.</p>
 *
 * @author Nguyễn Văn Vang
 */
public interface IRoomDAO {

    /**
     * Retrieves all rooms from the database.<br>
     *
     * @return A List containing all Room objects retrieved
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Declares the method to fetch all rooms from the database.
     * Must be implemented by the concrete class.
     */
    List<Room> getAllRooms() throws SQLException;

    /**
     * Inserts a new room into the database.<br>
     *
     * @param room The Room object to insert
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Declares the method to insert a new room record.
     * Must be implemented by the concrete class.
     */
    void addRoom(Room room) throws SQLException;

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
     * Declares the method to update an existing room record.
     * Must be implemented by the concrete class.
     */
    void updateRoom(int roomID, String roomTypes, int numberOfRooms, float priceOfRoom, int status) throws SQLException;

    /**
     * Deletes a room from the database.<br>
     *
     * @param roomID The ID of the room to delete
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Declares the method to delete a room record.
     * Must be implemented by the concrete class.
     */
    // void deleteRoom(int roomID) throws SQLException;

    /**
     * Retrieves a room by its ID.<br>
     *
     * @param roomID The ID of the room to retrieve
     * @return The Room object if found, null otherwise
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Declares the method to fetch a room by its ID.
     * Must be implemented by the concrete class.
     */
    Room getRoomByRoomID(int roomID) throws SQLException;

    /**
     * Retrieves all rooms for a specific accommodation ID.<br>
     *
     * @param accommodationID The ID of the accommodation
     * @return A List containing all Room objects for the accommodation
     * @throws SQLException If a database access error occurs
     */
    /* 
     * Declares the method to fetch rooms by accommodation ID.
     * Must be implemented by the concrete class.
     */
    List<Room> getRoomsByAccommodationID(int accommodationID) throws SQLException;
}