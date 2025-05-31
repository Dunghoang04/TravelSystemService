/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Voucher;
import java.util.Date;

/**
 *
 * @author Hung
 */
public class VoucherDAO {

    Connection conn;

    public VoucherDAO() {
        DBContext db = new DBContext();
        this.conn = db.getConnection();
    }

    public ArrayList<Voucher> getAllVoucher() {
        ArrayList<Voucher> list = new ArrayList<>();
        String sql = "Select * from Voucher";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
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

                list.add(voucher);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Voucher getVoucherById(int id) {
        String sql = "SELECT * FROM Voucher WHERE voucherId = ?";
        Voucher voucher = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voucher;
    }

    public ArrayList<Voucher> getVoucherByStatus(int status) {
        ArrayList<Voucher> listVoucher = new ArrayList<>();
        String sql = "Select * from Voucher where status = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, status);
            ResultSet rs = stmt.executeQuery();

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
        }
    }

    public boolean updateVoucher(int voucherId, String voucherName, String description, float percentDiscount, float maxDiscountAmount, float minAmountApply, Date startDate, Date endDate, int quantity, int status) {
        String sql = "UPDATE Voucher SET voucherName = ?, description = ?, percentDiscount = ?, maxDiscountAmount = ?, "
                + "minAmountApply = ?, startDate = ?, endDate = ?, quantity = ?, status = ? WHERE voucherId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
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

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeStatusById(int voucherId, int status) {
        String sql = "UPDATE Voucher\n"
                + "SET status = ?\n"
                + "WHERE voucherId = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, status);
            stmt.setInt(2, voucherId);
            int check = stmt.executeUpdate();
            return check > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Voucher> getVoucherByVoucherCode(String voucherCode) {
        String sql = "Select * from Voucher where voucherCode like ?";
        ArrayList<Voucher> listVoucher = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + voucherCode + "%");
            ResultSet rs = stmt.executeQuery();
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
        }
    }

    public boolean addVoucher(String voucherCode, String voucherName, String description, float percentDiscount, float maxDiscountAmount, float minAmountApply, Date startDate, Date endDate, int quantity, int status) {
        String sql = "INSERT INTO Voucher "
           + "(voucherCode, voucherName, description, percentDiscount, maxDiscountAmount, minAmountApply, startDate, endDate, quantity, status) "
           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
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
            return check>0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        VoucherDAO dao = new VoucherDAO();
        ArrayList<Voucher> list = dao.getAllVoucher();
        for(Voucher v:list){
            System.out.println(v);
        }
    }
}
