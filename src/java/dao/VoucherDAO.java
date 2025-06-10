/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR     DESCRIPTION
 * 2025-06-07  1.0        Hưng       First implementation
 */
package dao;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.Voucher;
import java.util.Date;

/**
 * VoucherDAO provides access methods for retrieving and manipulating voucher data
 * from the database. It connects to the Voucher table and allows standard CRUD operations.
 * 
 * This class extends DBContext to use established database connection utilities.
 * Implements IVoucherDAO interface to ensure all business-required methods are fulfilled.
 * 
 * @author Hưng
 */
public class VoucherDAO extends DBContext implements IVoucherDAO {

    /**
     * Retrieve all vouchers from the database.
     * 
     * @return list of all Voucher objects
     * @throws SQLException if database access error occurs
     */
    public ArrayList<Voucher> getAllVoucher() throws SQLException {
        ArrayList<Voucher> list = new ArrayList<>();
        String sql = "SELECT * FROM Voucher";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                // Map result set to Voucher object
                voucher.setVoucherId(rs.getInt(1));
                voucher.setVoucherCode(rs.getString(2));
                voucher.setVoucherName(rs.getString(3));
                voucher.setDescription(rs.getString(4));
                voucher.setPercentDiscount(rs.getFloat(5));
                voucher.setMaxDiscountAmount(rs.getFloat(6));
                voucher.setMinAmountApply(rs.getFloat(7));
                voucher.setStartDate(rs.getDate(8));
                voucher.setEndDate(rs.getDate(9));
                voucher.setQuantity(rs.getInt(10));
                voucher.setStatus(rs.getInt(11));
                list.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Always close resources
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
            getConnection().close();
        }
        return list;
    }

    /**
     * Retrieve a single voucher based on its ID.
     * 
     * @param id ID of the voucher to retrieve
     * @return the corresponding Voucher object, or null if not found
     * @throws SQLException if database access error occurs
     */
    public Voucher getVoucherById(int id) throws SQLException {
        String sql = "SELECT * FROM Voucher WHERE voucherId = ?";
        Voucher voucher = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                voucher = new Voucher();
                voucher.setVoucherId(rs.getInt(1));
                voucher.setVoucherCode(rs.getString(2));
                voucher.setVoucherName(rs.getString(3));
                voucher.setDescription(rs.getString(4));
                voucher.setPercentDiscount(rs.getFloat(5));
                voucher.setMaxDiscountAmount(rs.getFloat(6));
                voucher.setMinAmountApply(rs.getFloat(7));
                voucher.setStartDate(rs.getDate(8));
                voucher.setEndDate(rs.getDate(9));
                voucher.setQuantity(rs.getInt(10));
                voucher.setStatus(rs.getInt(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
            getConnection().close();
        }
        return voucher;
    }

    /**
     * Retrieve a list of vouchers based on their status.
     * 
     * @param status the status to filter by (1 = active, 0 = inactive)
     * @return list of matching Voucher objects
     * @throws SQLException if database access error occurs
     */
    public ArrayList<Voucher> getVoucherByStatus(int status) throws SQLException {
        ArrayList<Voucher> listVoucher = new ArrayList<>();
        String sql = "SELECT * FROM Voucher WHERE status = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, status);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherId(rs.getInt(1));
                voucher.setVoucherCode(rs.getString(2));
                voucher.setVoucherName(rs.getString(3));
                voucher.setDescription(rs.getString(4));
                voucher.setPercentDiscount(rs.getFloat(5));
                voucher.setMaxDiscountAmount(rs.getFloat(6));
                voucher.setMinAmountApply(rs.getFloat(7));
                voucher.setStartDate(rs.getDate(8));
                voucher.setEndDate(rs.getDate(9));
                voucher.setQuantity(rs.getInt(10));
                voucher.setStatus(rs.getInt(11));
                listVoucher.add(voucher);
            }
            return listVoucher;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
            getConnection().close();
        }
    }

    /**
     * Update a voucher's information based on its ID.
     * 
     * @param voucherId the ID of the voucher to update
     * @param voucherName updated name
     * @param description updated description
     * @param percentDiscount updated discount percent
     * @param maxDiscountAmount updated max discount value
     * @param minAmountApply updated min applicable amount
     * @param startDate updated start date
     * @param endDate updated end date
     * @param quantity updated available quantity
     * @param status updated status
     * @return true if update was successful, false otherwise
     * @throws SQLException if database access error occurs
     */
    public boolean updateVoucher(int voucherId, String voucherName, String description, float percentDiscount,
            float maxDiscountAmount, float minAmountApply, Date startDate, Date endDate,
            int quantity, int status) throws SQLException {
        String sql = "UPDATE Voucher SET voucherName = ?, description = ?, percentDiscount = ?, "
                + "maxDiscountAmount = ?, minAmountApply = ?, startDate = ?, endDate = ?, "
                + "quantity = ?, status = ? WHERE voucherId = ?";
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, voucherName);
            stmt.setString(2, description);
            stmt.setFloat(3, percentDiscount);
            stmt.setFloat(4, maxDiscountAmount);
            stmt.setFloat(5, minAmountApply);
            stmt.setDate(6, new java.sql.Date(startDate.getTime()));
            stmt.setDate(7, new java.sql.Date(endDate.getTime()));
            stmt.setInt(8, quantity);
            stmt.setInt(9, status);
            stmt.setInt(10, voucherId);
            int check = stmt.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) stmt.close();
            getConnection().close();
        }
    }

    /**
     * Change the status of a voucher (e.g., active/inactive).
     * 
     * @param voucherId ID of the voucher to update
     * @param status new status value
     * @return true if update was successful, false otherwise
     * @throws SQLException if database access error occurs
     */
    public boolean changeStatusById(int voucherId, int status) throws SQLException {
        String sql = "UPDATE Voucher SET status = ? WHERE voucherId = ?";
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, status);
            stmt.setInt(2, voucherId);
            int check = stmt.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) stmt.close();
            getConnection().close();
        }
    }

    /**
     * Search for vouchers using a partial or full voucher code.
     * 
     * @param voucherCode search keyword
     * @return list of matching vouchers
     * @throws SQLException if database access error occurs
     */
    public ArrayList<Voucher> getVoucherByVoucherCode(String voucherCode) throws SQLException {
        String sql = "SELECT * FROM Voucher WHERE voucherCode LIKE ?";
        ArrayList<Voucher> listVoucher = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, "%" + voucherCode + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherId(rs.getInt(1));
                voucher.setVoucherCode(rs.getString(2));
                voucher.setVoucherName(rs.getString(3));
                voucher.setDescription(rs.getString(4));
                voucher.setPercentDiscount(rs.getFloat(5));
                voucher.setMaxDiscountAmount(rs.getFloat(6));
                voucher.setMinAmountApply(rs.getFloat(7));
                voucher.setStartDate(rs.getDate(8));
                voucher.setEndDate(rs.getDate(9));
                voucher.setQuantity(rs.getInt(10));
                voucher.setStatus(rs.getInt(11));
                listVoucher.add(voucher);
            }
            return listVoucher;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
            getConnection().close();
        }
    }

    /**
     * Insert a new voucher into the database.
     * 
     * @param voucherCode code of the new voucher
     * @param voucherName name of the voucher
     * @param description brief description
     * @param percentDiscount discount percentage
     * @param maxDiscountAmount maximum discount limit
     * @param minAmountApply minimum purchase to apply
     * @param startDate effective start date
     * @param endDate expiry date
     * @param quantity total available
     * @param status active/inactive
     * @return true if insertion successful, false otherwise
     * @throws SQLException if database access error occurs
     */
    public boolean addVoucher(String voucherCode, String voucherName, String description, float percentDiscount,
            float maxDiscountAmount, float minAmountApply, Date startDate, Date endDate,
            int quantity, int status) throws SQLException {
        String sql = "INSERT INTO Voucher "
                + "(voucherCode, voucherName, description, percentDiscount, maxDiscountAmount, minAmountApply, "
                + "startDate, endDate, quantity, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, voucherCode);
            stmt.setString(2, voucherName);
            stmt.setString(3, description);
            stmt.setFloat(4, percentDiscount);
            stmt.setFloat(5, maxDiscountAmount);
            stmt.setFloat(6, minAmountApply);
            stmt.setDate(7, new java.sql.Date(startDate.getTime()));
            stmt.setDate(8, new java.sql.Date(endDate.getTime()));
            stmt.setInt(9, quantity);
            stmt.setInt(10, status);
            int check = stmt.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) stmt.close();
            getConnection().close();
        }
    }

    /**
     * For testing DAO functionality: retrieve and print all vouchers.
     */
    public static void main(String[] args) throws SQLException {
        VoucherDAO dao = new VoucherDAO();
        ArrayList<Voucher> list = dao.getAllVoucher();
        for (Voucher v : list) {
            System.out.println(v);
        }
    }
}
