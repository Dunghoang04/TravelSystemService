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
     * service name and derives travelAgentID from the userID via TravelAgent
     * table.
     *
     * @param serviceName The name of the service to be inserted. It is a
     * <code>java.lang.String</code> object.
     * @param userID The ID of the user from the session, used to derive
     * travelAgentID. It is an <code>int</code> value.
     * @return The generated service ID after successful insertion. It is an
     * <code>int</code> value.
     * @throws SQLException If a database error occurs during the insertion or
     * key retrieval process.
     */
    @Override
    public int addService(int serviceCategoryID, String serviceName, int agentID) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }

            // Insert into Service table with provided agentID directly
            String sql = "INSERT INTO [dbo].[Service] ([serviceCategoryID], [serviceName], [travelAgentID], [status]) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, serviceCategoryID); // Hardcoded serviceCategoryID, can be parameterized
            ps.setString(2, serviceName);
            ps.setInt(3, agentID); // Use agentID directly as travelAgentID
            ps.setInt(4, 1); // Default status = 1
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

    @Override
    public void updateServiceStatus(int serviceId, int newStatus) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }

            // Start transaction
            conn.setAutoCommit(false);

            // Update status in Service table
            String sql = "UPDATE [dbo].[Service] SET status = ? WHERE serviceID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, newStatus);
            ps.setInt(2, serviceId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No service found with serviceID: " + serviceId);
            }

            // Commit transaction
            conn.commit();
        } catch (SQLException e) {
            // Rollback transaction on error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Rollback failed", ex);
                }
            }
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // Log lỗi nếu cần
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Log lỗi nếu cần
                }
            }
        }
    }

    @Override
    public int countServiceUsed(int serviceId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;

        try {
            conn = getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }
            String sql = "SELECT COUNT(*) as UsedCount "
                    + "FROM [TravelAgency2021].[dbo].[BookDetail] bd "
                    + "JOIN [TravelAgency2021].[dbo].[Tour_Service_Detail] tsd ON bd.tourID = tsd.tourID "
                    + "WHERE tsd.serviceID = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery(); // Sử dụng executeQuery cho SELECT

            if (rs.next()) {
                rowsAffected = rs.getInt("UsedCount"); // Lấy giá trị COUNT(*)
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            // Đóng ResultSet trước khi đóng PreparedStatement và Connection
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
        return rowsAffected;
    }

    public static void main(String[] args) {
        ServiceDao sDao = new ServiceDao();
        try {
            System.out.println(sDao.countServiceUsed(10));
        } catch (Exception e) {
        }
    }
}
