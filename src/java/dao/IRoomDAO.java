/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang          First implementation
 */
package dao;

import java.util.List;
import model.Room;

/**
 * Interface for Room Data Access Object (DAO) operations.
 * Defines methods for managing rooms in the database.
 *
 * @author TuanAnhJr
 */
public interface IRoomDAO {

    /**
     * Adds a new room to the database.
     *
     * @param room the Room object to be added
     */
    void addRoom(Room room);

    /**
     * Updates an existing room in the database.
     *
     * @param accommodationID the ID of the accommodation
     * @param roomTypes the types of the room
     * @param numberOfRooms the number of rooms
     * @param priceOfRoom the price of the room
     */
    void updateRoom(int accommodationID, String roomTypes, int numberOfRooms, double priceOfRoom);

    /**
     * Deletes a room from the database by its accommodation ID.
     *
     * @param accommodationID the ID of the accommodation
     */
    void deleteRoom(int accommodationID);

    /**
     * Retrieves a list of all rooms from the database.
     *
     * @return a list of Room objects
     */
    List<Room> getAllRooms();

    /**
     * Retrieves a room by its accommodation ID.
     *
     * @param id the ID of the accommodation
     * @return the Room object, or null if not found
     */
    Room getRoomByAccommodationID(int id);
}