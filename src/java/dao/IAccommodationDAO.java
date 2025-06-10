/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First definition
 * 2025-06-08  1.1       TuanAnhJr         Updated serviceID to serviceId for naming consistency
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Accommodation;
import model.Room;

/**
 * Interface for Data Access Object (DAO) to manage Accommodation data in the database.
 * Defines methods for CRUD operations and additional functionality for accommodations.
 *
 * @author NguyenVanVang
 */
public interface IAccommodationDAO {

    /**
     * Inserts a new service into the database and returns its generated ID.
     *
     * @param serviceName the name of the service
     * @return the generated service ID
     * @throws SQLException if a database error occurs
     */
    int insertService(String serviceName) throws SQLException;

    /**
     * Retrieves a list of all accommodations from the database.
     *
     * @return a list of Accommodation objects
     */
    List<Accommodation> getListAccommodation();

    /**
     * Inserts a new accommodation into the database.
     *
     * @param roomID the ID of the room
     * @param name the name of the accommodation
     * @param image the image URL or path
     * @param address the address of the accommodation
     * @param phone the contact phone number
     * @param description the description of the accommodation
     * @param rate the rating of the accommodation
     * @param type the type of accommodation
     * @param status the status of the accommodation
     * @param checkInTime the check-in time
     * @param checkOutTime the check-out time
     */
    void insertAccommodation(int roomID, String name, String image, String address, String phone,
            String description, float rate, String type, int status, String checkInTime, String checkOutTime);

    /**
     * Deletes an accommodation and its associated service from the database.
     *
     * @param serviceId the ID of the service to delete
     */
    void deleteAccommodation(int serviceId);

    /**
     * Retrieves an accommodation by its service ID.
     *
     * @param serviceId the ID of the service
     * @return the Accommodation object, or null if not found
     */
    Accommodation getAccommodationByServiceId(int serviceId);

    /**
     * Updates an existing accommodation in the database.
     *
     * @param serviceId the ID of the service
     * @param roomID the ID of the room
     * @param name the name of the accommodation
     * @param image the image URL or path
     * @param address the address of the accommodation
     * @param phone the contact phone number
     * @param description the description of the accommodation
     * @param rate the rating of the accommodation
     * @param type the type of accommodation
     * @param status the status of the accommodation
     * @param checkInTime the check-in time
     * @param checkOutTime the check-out time
     */
    void updateAccommodation(int serviceId, int roomID, String name, String image, String address, String phone,
            String description, float rate, String type, int status, String checkInTime, String checkOutTime);

    /**
     * Searches for accommodations by name (partial match).
     *
     * @param name the name or partial name to search for
     * @return a list of matching Accommodation objects
     */
    List<Accommodation> searchAccommodationByName(String name);

    /**
     * Retrieves a room by its accommodation ID.
     *
     * @param accommodationID the ID of the accommodation
     * @return the Room object, or null if not found
     */
    Room getRoomById(int accommodationID);

    /**
     * Retrieves the status of an accommodation by its service ID.
     *
     * @param serviceId the ID of the service
     * @return the status value, or -1 if not found
     */
    int getStatusByServiceId(int serviceId);

    /**
     * Changes the status of an accommodation.
     *
     * @param serviceId the ID of the service
     * @param status the new status value
     * @return true if the update was successful, false otherwise
     */
    boolean changeStatus(int serviceId, int status);
}