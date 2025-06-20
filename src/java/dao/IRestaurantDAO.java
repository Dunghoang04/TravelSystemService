/**
 * Interface for data access operations related to Restaurant entities.
 * Provides methods to insert, update, delete, and retrieve restaurant records.
 *
 * Project: TravelAgentService
 * Version: 1.0
 * Date: 2025-06-13
 * Bugs: No known issues.
 *
 * Record of Change:
 * DATE            Version             AUTHOR                      DESCRIPTION
 * 2025-06-13      1.0                 Hoang Tuan Dung             First implementation
 *
 * @author Hoang Tuan Dung
 */
package dao;

import java.util.List;
import model.Restaurant;
import java.sql.SQLException;

/**
 * Inserts a new restaurant record into the database.
 *
 * @param name the name of the restaurant
 * @param image the image URL or path
 * @param address the address
 * @param phone the phone number
 * @param description the description
 * @param rate the rating (0–10)
 * @param type the type of restaurant
 * @param status the status
 * @param timeOpen the opening time (HH:mm:ss)
 * @param timeClose the closing time (HH:mm:ss)
 * @throws SQLException if a database error occurs
 */
public interface IRestaurantDAO {

    /**
     * Inserts a new restaurant record into the database.
     *
     * @param name the name of the restaurant
     * @param image the image URL or path
     * @param address the address
     * @param phone the phone number
     * @param description the description
     * @param rate the rating (0–10)
     * @param type the type of restaurant
     * @param status the status
     * @param timeOpen the opening time (HH:mm:ss)
     * @param timeClose the closing time (HH:mm:ss)
     * @throws SQLException if a database error occurs
     */
    public void insertRestaurantFull(int agentID,String name, String image, String address, String phone, String description,
            float rate, String type, int status, String timeOpen, String timeClose) throws SQLException;

    /**
     * Retrieves a paginated list of all restaurant records.
     *
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of Restaurant objects
     * @throws SQLException if a database error occurs
     */
    public List<Restaurant> getListRestaurant(int page, int pageSize) throws SQLException;
    
    /**
     * Retrieves a paginated list of all restaurant records.
     *
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of Restaurant objects
     * @throws SQLException if a database error occurs
     */
    public List<Restaurant> getListRestaurantNoPage(int serviceId) throws SQLException;

    /**
     * Searches restaurant records by name with pagination.
     *
     * @param name the name to search (partial match)
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Restaurant objects
     * @throws SQLException if a database error occurs
     */
    public List<Restaurant> searchRestaurantByName(String name, int page, int pageSize) throws SQLException;

    /**
     * Retrieves the status of a restaurant by service ID.
     *
     * @param serviceId the service ID
     * @return the status, or -1 if not found
     * @throws SQLException if a database error occurs
     */
    public int getStatusByServiceID(int serviceID) throws SQLException;

    /**
     * Changes the status of a restaurant record.
     *
     * @param serviceId the service ID
     * @param status the new status
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean changeStatus(int serviceID, int status) throws SQLException;

    /**
     * Searches restaurant records by status and name with pagination.
     *
     * @param status the status
     * @param name the name to search (partial match)
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Restaurant objects
     * @throws SQLException if a database error occurs
     */
    public List<Restaurant> searchByTypeAndName(int status, String name, int page, int pageSize) throws SQLException;

    /**
     * Retrieves a restaurant record by service ID.
     *
     * @param serviceId the service ID
     * @return the Restaurant object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Restaurant getRestaurantByServiceId(int id) throws SQLException;

    /**
     * Retrieves restaurant records by status with pagination.
     *
     * @param status the status
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Restaurant objects
     * @throws SQLException if a database error occurs
     */
    public List<Restaurant> getRestaurantByStatus(int status, int page, int pageSize) throws SQLException;

    /**
     * Updates a restaurant record.
     *
     * @param serviceId the service ID
     * @param name the name
     * @param image the image URL or path
     * @param address the address
     * @param phone the phone number
     * @param description the description
     * @param rate the rating
     * @param type the type
     * @param status the status
     * @param timeOpen the opening time
     * @param timeClose the closing time
     * @throws SQLException if a database error occurs
     */
    public void updateRestaurant(int serviceId, String name, String image, String address, String phone, String description,
            float rate, String type, int status, String timeOpen, String timeClose) throws SQLException;

    /**
     * Deletes a restaurant record and its related service.
     *
     * @param serviceId the service ID
     * @throws SQLException if a database error occurs
     */
    public void deleteRestaurant(int serviceID) throws SQLException;

    /**
     * Counts restaurant records by name.
     *
     * @param name the name to search
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    public int countByName(String name) throws SQLException;

    /**
     * Counts restaurant records by status and name.
     *
     * @param status the status
     * @param name the name to search
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    public int countByTypeAndName(int status, String name) throws SQLException;

    /**
     * Counts restaurant records by status.
     *
     * @param status the status
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    public int countByStatus(int status) throws SQLException;

    /**
     * Counts all restaurant records.
     *
     * @return the total number of records
     * @throws SQLException if a database error occurs
     */
    public int countAllRestaurant() throws SQLException;

}
