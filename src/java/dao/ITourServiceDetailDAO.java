package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import model.TourServiceDetail;

/**
 * This interface defines the operations for accessing and managing
 * TourServiceDetail records in the database.
 */
public interface ITourServiceDetailDAO {

    /**
     * Retrieves all tour service details from the database.
     *
     * @return A Vector of TourServiceDetail objects
     * @throws SQLException If a database access error occurs
     */
    Vector<TourServiceDetail> getAllTourServiceDetails() throws SQLException;

    /**
     * Retrieves all service details for a specific tour based on tour ID.
     *
     * @param tourId The ID of the tour
     * @return A Vector of TourServiceDetail objects for the given tour
     * @throws SQLException If a database access error occurs
     */
    Vector<TourServiceDetail> getTourServiceDetails(int tourId) throws SQLException;

    /**
     * Inserts a new TourServiceDetail record into the database.
     *
     * @param detail The TourServiceDetail object to insert
     * @throws SQLException If a database access error occurs
     */
    void insertTourServiceDetail(TourServiceDetail detail) throws SQLException;

    /**
     * Searches for a TourServiceDetail record by its detail ID.
     *
     * @param detailId The ID of the detail to search for
     * @return The TourServiceDetail object if found, or null if not found
     * @throws SQLException If a database access error occurs
     */
    TourServiceDetail searchTourServiceDetail(int detailId) throws SQLException;

    /**
     * Updates an existing TourServiceDetail record in the database.
     *
     * @param detail The TourServiceDetail object containing updated data
     * @throws SQLException If a database access error occurs
     */
    void updateTourServiceDetail(TourServiceDetail detail) throws SQLException;

    /**
     * Update status a TourServiceDetail record  by detail ID.
     *
     * @param detailId The ID of the detail to delete
     * @return The number of records affected (0 if not found)
     * @throws SQLException If a database access error occurs
     */
    int updateStatusTourServiceDetail(int detailId, int status) throws SQLException;
    
    /**
     * Updates the service name in Tour_Service_Detail based on service ID.
     *
     * @param serviceId The ID of the service to update.
     * @param newServiceName The new service name.
     * @throws SQLException If a database error occurs.
     */
    void updateServiceNameByServiceId(int serviceId, String newServiceName) throws SQLException;

    /**
     * Retrieves all Tour_Service_Detail records for a given service ID.
     *
     * @param serviceId The ID of the service.
     * @return A list of TourServiceDetail objects.
     * @throws SQLException If a database error occurs.
     */
    List<TourServiceDetail> getTourServiceDetailsByServiceId(int serviceId) throws SQLException;
    
    int getServiceIdByTourId(int tour) throws SQLException;
}
