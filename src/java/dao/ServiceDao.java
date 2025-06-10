/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE            Version             AUTHOR                                DESCRIPTION
 * 2025-06-08      1.0                 Hoang Tuan Dung                       First implementation
 * 2025-06-08      1.1                 Hoang Tuan Dung                       Implemented IService interface, improved resource management
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

/**
 * Data Access Object for managing service-related database operations. This
 * class implements the IService interface to provide CRUD operations for the
 * Service table.
 *
 * @author Hoang Tuan Dung
 */
public class ServiceDao extends DBContext implements IService {

    /**
     * Inserts a new service record into the Service table with the given
     * service name. The serviceCategoryID is hardcoded to 1, which can be
     * parameterized in the future.
     *
     * @param serviceName The name of the service to be inserted. It is a
     * <code>java.lang.String</code> object.
     * @return The generated service ID after successful insertion. It is an
     * <code>int</code> value.
     * @throws SQLException If a database error occurs during the insertion or
     * key retrieval process.
     */
    @Override
    public int addService(String serviceName) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }
            String sql = "INSERT INTO [dbo].[Service] ([serviceCategoryID], [serviceName]) VALUES (?, ?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 2);
            ps.setString(2, serviceName);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve serviceID after inserting Service.");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    /**
     * Updates the name of an existing service in the Service table based on its
     * service ID. If no rows are affected, a SQLException is thrown indicating
     * the service was not found.
     *
     * @param serviceId The ID of the service to be updated. It is an
     * <code>int</code> value.
     * @param newServiceName The new name to assign to the service. It is a
     * <code>java.lang.String</code> object.
     * @throws SQLException If a database error occurs or if no service is found
     * with the given ID.
     */
    @Override
    public void updateServiceName(int serviceId, String newServiceName) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }
            String sql = "UPDATE [dbo].[Service] SET serviceName = ? WHERE serviceID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newServiceName);
            ps.setInt(2, serviceId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No service found with serviceID: " + serviceId);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
