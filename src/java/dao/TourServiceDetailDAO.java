/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TourServiceDetail;

/**
 *
 * @author Nhat Anh
 */
public class TourServiceDetailDAO extends DBContext implements ITourServiceDetailDAO{

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

    public static void main(String[] args) {
        TourServiceDetailDAO dao = new TourServiceDetailDAO();
        Vector<TourServiceDetail> list;
        Vector<TourServiceDetail> list2;
        dao.insertTourServiceDetail(new TourServiceDetail(4, 1, "afsjkawf", 1));
        try {
            list = dao.getAllTourServiceDetails();
            list2 = dao.getTourServiceDetails(4);
            for (TourServiceDetail detail : list2) {
                System.out.println(detail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TourServiceDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
