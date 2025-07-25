/**
 * Interface for data access operations related to Restaurant entities.
 * Provides methods to insert, update, delete, and retrieve restaurant records, including agent-specific filtering.
 *
 * Project: TravelAgentService
 * Version: 1.1
 * Date: 2025-07-14
 * Bugs: No known issues.
 *
 * Record of Change:
 * DATE            Version             AUTHOR                      DESCRIPTION
 * 2025-06-13      1.0                 Hoang Tuan Dung             First implementation
 * 2025-07-14      1.1                 Grok                        Added agent ID filtering methods
 *
 * @author Hoang Tuan Dung, Grok
 */
package dao;

import java.util.List;
import model.Restaurant;
import java.sql.SQLException;

/**
 * Interface for Restaurant data access operations.
 */
public interface IRestaurantDAO {

    /**
     * Inserts a new restaurant record into the database.
     *
     * @param agentID the ID of the travel agent
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
    void insertRestaurantFull(int agentID, String name, String image, String address, String phone, String description,
            float rate, String type, int status, String timeOpen, String timeClose) throws SQLException;

    /**
     * Retrieves a paginated list of restaurant records for a specific agent.
     *
     * @param agentID the ID of the travel agent
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of Restaurant objects
     * @throws SQLException if a database error occurs
     */
    List<Restaurant> getListRestaurantByAgent(int agentID, int page, int pageSize) throws SQLException;

    /**
     * Searches restaurant records by name for a specific agent with pagination.
     *
     * @param agentID the ID of the travel agent
     * @param name the name to search (partial match)
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Restaurant objects
     * @throws SQLException if a database error occurs
     */
    List<Restaurant> searchRestaurantByAgentAndName(int agentID, String name, int page, int pageSize) throws SQLException;

    /**
     * Retrieves the status of a restaurant by service ID.
     *
     * @param serviceId the service ID
     * @return the status, or -1 if not found
     * @throws SQLException if a database error occurs
     */
    int getStatusByServiceID(int serviceID) throws SQLException;

    /**
     * Changes the status of a restaurant record.
     *
     * @param serviceId the service ID
     * @param status the new status
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean changeStatus(int serviceID, int status) throws SQLException;

    /**
     * Searches restaurant records by status and name for a specific agent with pagination.
     *
     * @param agentID the ID of the travel agent
     * @param status the status
     * @param name the name to search (partial match)
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Restaurant objects
     * @throws SQLException if a database error occurs
     */
    List<Restaurant> searchByAgentTypeAndName(int agentID, int status, String name, int page, int pageSize) throws SQLException;

    /**
     * Retrieves a restaurant record by service ID.
     *
     * @param serviceId the service ID
     * @return the Restaurant object, or null if not found
     * @throws SQLException if a database error occurs
     */
    Restaurant getRestaurantByServiceId(int id) throws SQLException;

    /**
     * Retrieves restaurant records by status for a specific agent with pagination.
     *
     * @param agentID the ID of the travel agent
     * @param status the status
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Restaurant objects
     * @throws SQLException if a database error occurs
     */
    List<Restaurant> getRestaurantByAgentAndStatus(int agentID, int status, int page, int pageSize) throws SQLException;

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
    void updateRestaurant(int serviceId, String name, String image, String address, String phone, String description,
            float rate, String type, int status, String timeOpen, String timeClose) throws SQLException;

    /**
     * Deletes a restaurant record and its related service.
     *
     * @param serviceId the service ID
     * @throws SQLException if a database error occurs
     */
    void deleteRestaurant(int serviceID) throws SQLException;

    /**
     * Counts restaurant records by name for a specific agent.
     *
     * @param agentID the ID of the travel agent
     * @param name the name to search
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    int countByAgentAndName(int agentID, String name) throws SQLException;

    /**
     * Counts restaurant records by status and name for a specific agent.
     *
     * @param agentID the ID of the travel agent
     * @param status the status
     * @param name the name to search
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    int countByAgentTypeAndName(int agentID, int status, String name) throws SQLException;

    /**
     * Counts restaurant records by status for a specific agent.
     *
     * @param agentID the ID of the travel agent
     * @param status the status
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    int countByAgentAndStatus(int agentID, int status) throws SQLException;

    /**
     * Counts all restaurant records for a specific agent.
     *
     * @param agentID the ID of the travel agent
     * @return the total number of records
     * @throws SQLException if a database error occurs
     */
    int countByAgent(int agentID) throws SQLException;
}