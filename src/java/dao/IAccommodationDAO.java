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

import java.sql.SQLException;
import java.util.List;
import model.Accommodation;
import model.Room;

/**
 *
 * @author Nguyễn Văn Vang
 */
public interface IAccommodationDAO {

    /**
     * Inserts a new service into the database and returns the generated service ID.<br>
     *
     * @param serviceName The name of the service to insert
     * @return The generated service ID as an integer
     * @throws SQLException If a database error occurs
     */
//    public int insertService(String serviceName) throws SQLException;

    /**
     * Retrieves a list of all accommodations from the database.<br>
     *
     * @return A List of Accommodation objects
     */
    public List<Accommodation> getListAccommodation();

    /**
     * Inserts a new accommodation into the database.<br>
     *
     * @param name The name of the accommodation
     * @param image The image URL or path for the accommodation
     * @param address The address of the accommodation
     * @param phone The contact phone number
     * @param description A description of the accommodation
     * @param rate The rating of the accommodation
     * @param type The type of accommodation (e.g., hotel, hostel)
     * @param status The status of the accommodation (e.g., available, unavailable)
     * @param checkInTime The check-in time for the accommodation
     * @param checkOutTime The check-out time for the accommodation
     */
    public void insertAccommodation(int agentID,String name, String image, String address, String phone, String description,
                                    float rate, String type, int status, String checkInTime, String checkOutTime);

    /**
     * Retrieves an accommodation by its service ID.<br>
     *
     * @param serviceId The unique identifier of the accommodation service
     * @return An Accommodation object if found, null otherwise
     */
    public Accommodation getAccommodationByServiceId(int serviceId);

    /**
     * Updates an existing accommodation in the database.<br>
     *
     * @param serviceId The unique identifier of the accommodation service
     * @param name The updated name of the accommodation
     * @param image The updated image URL or path
     * @param address The updated address
     * @param phone The updated contact phone number
     * @param description The updated description
     * @param rate The updated rating
     * @param type The updated type of accommodation
     * @param status The updated status
     * @param checkInTime The updated check-in time
     * @param checkOutTime The updated check-out time
     */
    public void updateAccommodation(int serviceId, String name, String image, String address, String phone,
                                    String description, float rate, String type, int status, String checkInTime,
                                    String checkOutTime);

    /**
     * Searches for accommodations by name.<br>
     *
     * @param name The name or partial name to search for
     * @return A List of Accommodation objects matching the search criteria
     */
    public List<Accommodation> searchAccommodationByName(String name);

    /**
     * Retrieves a list of rooms associated with a specific accommodation service ID.<br>
     *
     * @param serviceId The unique identifier of the accommodation service
     * @return A List of Room objects associated with the accommodation
     * @throws SQLException If a database access error occurs
     */
    public List<Room> getRoomsByServiceId(int serviceId) throws SQLException;
}