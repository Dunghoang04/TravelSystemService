/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai         Refactored with ITourDAO, improved resource management and comments
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TourServiceDetail;

/**
 *
 * @author Nhat Anh
 */
public class TourServiceDetailDAO extends DBContext implements ITourServiceDetailDAO {

    private static final String UPDATE_SERVICE_NAME_SQL = "UPDATE [dbo].[Tour_Service_Detail] SET serviceName = ? WHERE serviceID = ?";
    private static final String SELECT_BY_SERVICE_ID_SQL = "SELECT * FROM [dbo].[Tour_Service_Detail] WHERE serviceID = ?";
    private static final String GET_SERVICEID_FROM_TOURID = "select serviceid from Tour_Service_Detail where tourID= ?";

    @Override
    public Vector<TourServiceDetail> getAllTourServiceDetails() throws SQLException {
        Vector<TourServiceDetail> listDetails = new Vector<>();
        try {
            String sql = "SELECT * FROM Tour_Service_Detail";
            PreparedStatement ptm = getConnection().prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                TourServiceDetail detail = new TourServiceDetail(
                        rs.getInt("detailID"),
                        rs.getInt("tourID"),
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getInt("status")
                );
                listDetails.add(detail);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            getConnection().close();
        }
        return listDetails;
    }

    @Override
    public Vector<TourServiceDetail> getTourServiceDetails(int id) throws SQLException {
        Vector<TourServiceDetail> listDetails = new Vector<>();
        try {
            String sql = "SELECT * FROM Tour_Service_Detail where tourID = ?";
            PreparedStatement ptm = getConnection().prepareStatement(sql);
            ptm.setInt(1, id);
            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                TourServiceDetail detail = new TourServiceDetail(
                        rs.getInt("detailID"),
                        rs.getInt("tourID"),
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getInt("status")
                );
                listDetails.add(detail);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            getConnection().close();
        }
        return listDetails;
    }

    @Override
    public void insertTourServiceDetail(TourServiceDetail detail) {
        String sql = "INSERT INTO [dbo].[Tour_Service_Detail]\n"
                + "           ([tourID], [serviceID], [serviceName], [status])\n"
                + "     VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ptm = getConnection().prepareStatement(sql);
            ptm.setInt(1, detail.getTourID());
            ptm.setInt(2, detail.getServiceID());
            ptm.setString(3, detail.getServiceName());
            ptm.setInt(4, detail.getStatus());
            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public TourServiceDetail searchTourServiceDetail(int detailId) throws SQLException {
        String sql = "SELECT * FROM Tour_Service_Detail WHERE detailID = ?";
        try {
            PreparedStatement ptm = getConnection().prepareStatement(sql);
            ptm.setInt(1, detailId);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                return new TourServiceDetail(
                        rs.getInt("detailID"),
                        rs.getInt("tourID"),
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getInt("status")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            getConnection().close();
        }
        return null;
    }

    @Override
    public void updateTourServiceDetail(TourServiceDetail detail) throws SQLException {
        String sql = "UPDATE [dbo].[Tour_Service_Detail]\n"
                + "   SET [tourID] = ?, [serviceID] = ?, [serviceName] = ?\n"
                + " WHERE detailID = ?";
        try {
            PreparedStatement ptm = getConnection().prepareStatement(sql);
            ptm.setInt(1, detail.getTourID());
            ptm.setInt(2, detail.getServiceID());
            ptm.setString(3, detail.getServiceName());
            ptm.setInt(4, detail.getDetailID());
            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            getConnection().close();
        }
    }

    @Override
    public int updateStatusTourServiceDetail(int detailId, int status) throws SQLException {
        int n = 0;
        String sql = "Update [dbo].[Tour_Service_Detail]\n"
                + "SET status = ?      WHERE detailID = ?";
        try {
            PreparedStatement ptm = getConnection().prepareStatement(sql);
            ptm.setInt(1, status);
            ptm.setInt(2, status);
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            getConnection().close();
        }
        return n;
    }

    @Override
    public void updateServiceNameByServiceId(int serviceId, String newServiceName) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }

            conn.setAutoCommit(false);
            ps = conn.prepareStatement(UPDATE_SERVICE_NAME_SQL);
            ps.setString(1, newServiceName);
            ps.setInt(2, serviceId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No Tour_Service_Detail found with serviceID: " + serviceId);
            }
            conn.commit();
        } catch (SQLException e) {
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
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    @Override
    public void deleteTourServiceDetails(int tourId) throws SQLException {
       Connection conn = null;
       PreparedStatement ps = null;
       try {
           conn = getConnection();
           String sql = "Update [dbo].[Tour_Service_Detail] set status = 0 WHERE tourID = ?";
           ps = conn.prepareStatement(sql);
           ps.setInt(1, tourId);
           ps.executeUpdate();
       } finally {
           if (ps != null) ps.close();
           if (conn != null) conn.close();
       }
   }

    @Override
    public List<TourServiceDetail> getTourServiceDetailsByServiceId(int serviceId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TourServiceDetail> details = new ArrayList<>();
        try {
            conn = getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }

            ps = conn.prepareStatement(SELECT_BY_SERVICE_ID_SQL);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();
            while (rs.next()) {
                TourServiceDetail detail = new TourServiceDetail();
                detail.setDetailID(rs.getInt("detailID"));
                detail.setTourID(rs.getInt("tourID"));
                detail.setServiceID(rs.getInt("serviceID"));
                detail.setServiceName(rs.getString("serviceName"));
                detail.setStatus(rs.getInt("status"));
                details.add(detail);
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
        return details;
    }

    @Override
    public int getServiceIdByTourId(int tourId) throws SQLException {
        int serviceId = -1; // Giá trị mặc định nếu không tìm thấy
        try {
            String sql = "SELECT serviceId FROM Tour_Service_Detail WHERE tourID = ?";
            PreparedStatement ptm = getConnection().prepareStatement(sql);
            ptm.setInt(1, tourId);
            ResultSet rs = ptm.executeQuery();

            if (rs.next()) {
                serviceId = rs.getInt("serviceId");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (getConnection() != null) {
                    getConnection().close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return serviceId;
    }
}
