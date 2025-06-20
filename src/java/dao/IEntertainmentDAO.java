/**
 * Interface for data access operations related to Entertainment entities.
 * Provides methods to insert, update, delete, and retrieve entertainment records.
 * 
 * Project: TravelAgentService
 * Version: 1.0
 * Date: 2025-06-07
 * Bugs: No known issues.
 * 
 * Record of Change:
 * DATE            Version             AUTHOR                      DESCRIPTION
 * 2025-06-07      1.0                 Hoang Tuan Dung             First implementation
 * 2025-06-08      1.1                 Hoang Tuan Dung             Enhanced Javadoc
 * 
 * @author Hoang Tuan Dung
 */

package dao;

import model.Entertainment;
import java.sql.SQLException;
import java.util.List;

public interface IEntertainmentDAO {

    /**
     * Inserts a new entertainment record into the database.
     *
     * @param name         the name of the entertainment
     * @param image        the image URL or path
     * @param address      the address
     * @param phone        the phone number
     * @param description  the description
     * @param rate         the rating (0–10)
     * @param type         the type of entertainment
     * @param status       the status
     * @param timeOpen     the opening time (HH:mm:ss)
     * @param timeClose    the closing time (HH:mm:ss)
     * @param dayOfWeekOpen days open
     * @param ticketPrice  the ticket price
     * @throws SQLException if a database error occurs
     */
    void insertEntertainmentFull(int agentID,String name, String image, String address, String phone, String description,
            float rate, String type, int status, String timeOpen, String timeClose, String dayOfWeekOpen, float ticketPrice)
            throws SQLException;

    /**
     * Retrieves a paginated list of all entertainment records.
     *
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of Entertainment objects
     * @throws SQLException if a database error occurs
     */
    List<Entertainment> getListEntertainment(int page, int pageSize) throws SQLException;
    
    List<Entertainment> getListEntertainmentNoPage(int serviceId) throws SQLException;

    /**
     * Counts all entertainment records.
     *
     * @return the total number of records
     * @throws SQLException if a database error occurs
     */
    int countAll() throws SQLException;

    /**
     * Retrieves an entertainment record by service ID.
     *
     * @param serviceId the service ID
     * @return the Entertainment object, or null if not found
     * @throws SQLException if a database error occurs
     */
    Entertainment getEntertainmentByServiceId(int serviceId) throws SQLException;

    /**
     * Searches entertainment records by status and name with pagination.
     *
     * @param status the status
     * @param name the name to search (partial match)
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Entertainment objects
     * @throws SQLException if a database error occurs
     */
    List<Entertainment> searchByTypeAndName(int status, String name, int page, int pageSize) throws SQLException;

    /**
     * Counts entertainment records by status and name.
     *
     * @param status the status
     * @param name the name to search
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    int countByTypeAndName(int status, String name) throws SQLException;

    /**
     * Updates an entertainment record.
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
     * @param dayOfWeekOpen days open
     * @param ticketPrice the ticket price
     * @throws SQLException if a database error occurs
     */
    void updateEntertainment(int serviceId, String name, String image, String address, String phone,
            String description, float rate, String type, int status, String timeOpen,
            String timeClose, String dayOfWeekOpen, double ticketPrice) throws SQLException;

    /**
     * Retrieves the status of an entertainment by service ID.
     *
     * @param serviceId the service ID
     * @return the status, or -1 if not found
     * @throws SQLException if a database error occurs
     */
    int getStatusByServiceId(int serviceId) throws SQLException;

    /**
     * Changes the status of an entertainment record.
     *
     * @param serviceId the service ID
     * @param status the new status
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean changeStatus(int serviceId, int status) throws SQLException;

    /**
     * Deletes an entertainment record and its related service.
     *
     * @param serviceId the service ID
     * @throws SQLException if a database error occurs
     */
    void deleteEntertainment(int serviceId) throws SQLException;

    /**
     * Searches entertainment records by name with pagination.
     *
     * @param name the name to search (partial match)
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Entertainment objects
     * @throws SQLException if a database error occurs
     */
    List<Entertainment> searchEntertainmentByName(String name, int page, int pageSize) throws SQLException;

    /**
     * Counts entertainment records by name.
     *
     * @param name the name to search
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    int countByName(String name) throws SQLException;

    /**
     * Retrieves entertainment records by status with pagination.
     *
     * @param status the status
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Entertainment objects
     * @throws SQLException if a database error occurs
     */
    List<Entertainment> getEntertainmentByStatus(int status, int page, int pageSize) throws SQLException;

    /**
     * Counts entertainment records by status.
     *
     * @param status the status
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    int countByStatus(int status) throws SQLException;
}
