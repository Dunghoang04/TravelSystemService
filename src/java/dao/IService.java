/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE            Version             AUTHOR                   DESCRIPTION
 * 2025-06-08      1.0                 Hoang Tuan Dung          First implementation
 * 2025-06-08      1.1                 Hoang Tuan Dung          Moved to service package, enhanced Javadoc, and refined validation
 */
package dao;
import java.sql.SQLException;

/**
 * Interface defining the contract for business logic operations related to service entities
 * in the TravelAgentService system. This interface provides methods to manage services,
 * such as adding and updating service details, with business rule enforcement.
 *
 * @author Hoang Tuan Dung
 */
public interface IService {

    /**
     * Adds a new service to the system with the specified service name and user ID from session.
     *
     * @param serviceName The name of the service to be added.
     *                    It is a <code>java.lang.String</code> object.
     * @param userID The ID of the user from the session, used to derive travelAgentID.
     *               It is an <code>int</code> value.
     * @return The generated service ID after successful insertion.
     *         It is an <code>int</code> value.
     * @throws SQLException If a database error occurs during the insertion process.
     */
    int addService(int serviceCategoryID, String serviceName, int agentID) throws SQLException;

    /**
     * Updates the name of an existing service identified by its service ID.
     *
     * @param serviceId The ID of the service to be updated.
     *                  It is an <code>int</code> value.
     * @param newServiceName The new name for the service.
     *                       It is a <code>java.lang.String</code> object.
     * @throws SQLException If a database error occurs during the update process.
     */
    void updateServiceName(int serviceId, String newServiceName) throws SQLException;
    
    /**
     * Updates the status of an existing service identified by its service ID.
     * Updates the Service table first, then the corresponding Restaurant or Entertainment table if applicable.
     *
     * @param serviceId The ID of the service to be updated.
     *                  It is an <code>int</code> value.
     * @param newStatus The new status for the service.
     *                  It is an <code>int</code> value.
     * @throws SQLException If a database error occurs during the update process.
     */
    void updateServiceStatus(int serviceId, int newStatus) throws SQLException;
    
    public int countServiceUsed(int serviceId) throws SQLException;
}

