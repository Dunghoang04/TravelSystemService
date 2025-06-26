
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import model.BookDetail;
import model.Tour;
import model.User;

/**
 *
 * @author Hung
 */
//
//1	Đã thanh toán
//2	Bị hủy bởi người dùng
//3	Bị hủy bởi đại lý
//4	Đã hoàn thành chuyến đi
//5	Đã yêu cầu hoàn tiền
//6	Đã hoàn tiền
//7     Chờ thanh toán
public class BookTourDAO extends DBContext implements IBookTour {

    @Override
    public ArrayList<BookDetail> getBookDetailsByAgent(int travelAgentID) {
        ArrayList<BookDetail> list = new ArrayList<>();
        String sql = "SELECT b.bookID, b.userID, b.tourID, b.voucherID, b.bookDate, "
                + "b.numberAdult, b.numberChildren, "
                + "b.firstName, b.lastName, b.phone, b.gmail, b.note, "
                + "b.isBookedForOther, b.totalPrice, b.status, "
                + "pm.method AS paymentMethod "
                + "FROM BookDetail b "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "LEFT JOIN PaymentMethod pm ON b.paymentMethodID = pm.paymentMethodID "
                + "WHERE t.travelAgentID = ?";

        try (
                Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, travelAgentID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookDetail bd = new BookDetail();
                bd.setBookID(rs.getInt("bookID"));
                bd.setUserID(rs.getInt("userID"));
                bd.setTourID(rs.getInt("tourID"));

                bd.setVoucherID(rs.getInt("voucherID"));
                bd.setBookDate(rs.getDate("bookDate"));
                bd.setNumberAdult(rs.getInt("numberAdult"));
                bd.setNumberChildren(rs.getInt("numberChildren"));
                bd.setFirstName(rs.getString("firstName"));
                bd.setLastName(rs.getString("lastName"));
                bd.setPhone(rs.getString("phone"));
                bd.setGmail(rs.getString("gmail"));
                bd.setNote(rs.getString("note"));
                bd.setIsBookedForOther(rs.getInt("isBookedForOther"));
                bd.setTotalPrice(rs.getFloat("totalPrice"));
                bd.setStatus(rs.getInt("status"));

                list.add(bd);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc ghi log
        }

        return list;
    }

    @Override
    public ArrayList<BookDetail> getBookDetailByStatus(int status) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public BookDetail getBookDetailByBookCode(long bookCode) throws SQLException {
        String sql = "SELECT * FROM BookDetail WHERE bookCode = ?";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, bookCode);
            rs = stmt.executeQuery();
            while (rs.next()) {
                BookDetail b = new BookDetail();
                b.setBookID(rs.getInt("bookID"));
                b.setUserID(rs.getInt("userID"));
                b.setTourID(rs.getInt("tourID"));
                b.setVoucherID(rs.getInt("voucherID"));
                b.setBookCode(rs.getLong("bookCode"));
                b.setBookDate(rs.getDate("bookDate"));
                b.setNumberAdult(rs.getInt("numberAdult"));
                b.setNumberChildren(rs.getInt("numberChildren"));
                b.setPaymentMethodId(rs.getInt("paymentMethodID"));
                b.setFirstName(rs.getString("firstName"));
                b.setLastName(rs.getString("lastName"));
                b.setPhone(rs.getString("phone"));
                b.setGmail(rs.getString("gmail"));
                b.setNote(rs.getString("note"));
                b.setIsBookedForOther(rs.getInt("isBookedForOther"));
                b.setTotalPrice(rs.getFloat("totalPrice"));
                b.setStatus(rs.getInt("status"));
                return b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (rs != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return null;
    }

    @Override
    public boolean insertBookDetail(int userID, int tourID, Integer voucherID, int numberAdult, int numberChildren, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, double totalPrice, int status, int paymentMethodID, long bookCode) throws SQLException {
        String sql = "INSERT INTO BookDetail(userID, tourID, voucherID, bookDate, numberAdult, numberChildren, firstName, lastName, phone, gmail, note, isBookedForOther, totalPrice, status,paymentMethodID, bookCode) "
                + "VALUES (?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);
            stmt.setInt(2, tourID);
            if (voucherID != null) {
                stmt.setInt(3, voucherID);
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            stmt.setInt(4, numberAdult);
            stmt.setInt(5, numberChildren);
            stmt.setString(6, firstName);
            stmt.setString(7, lastName);
            stmt.setString(8, phone);
            stmt.setString(9, gmail);
            stmt.setString(10, note);
            stmt.setInt(11, isBookedForOther);
            stmt.setDouble(12, totalPrice);
            stmt.setInt(13, status);
            stmt.setInt(14, paymentMethodID);
            stmt.setLong(15, bookCode);

            int check = stmt.executeUpdate();
            if (check != 1) {
                conn.rollback();
                return false;
            }
            if (voucherID != null) {
                VoucherDAO voucherDao = new VoucherDAO();
                if (!voucherDao.updateQuantityVoucher(voucherID)) {
                    conn.rollback();
                    return false;
                }
            }

            WalletDAO walletDao = new WalletDAO();
            if (!walletDao.paymentForTour(totalPrice, userID)) {
                conn.rollback();
                return false;
            }

            TransactionHistoryDAO thDAO = new TransactionHistoryDAO();
            TourDAO tourDao = new TourDAO();
            Tour tour = tourDao.searchTour(tourID);
            UserDAO userDao = new UserDAO();
            User user = userDao.getUserById(userID);
            if (user == null) {
                conn.rollback();
                return false;
            }
            String transactionDescription = "Thanh toán tour: " + tour.getTourName();
            thDAO.insertTransaction(user.getUserID(), totalPrice, "PURCHASE", transactionDescription);

            int totalPeople = numberAdult + numberChildren;
            // Gọi update số lượng chỗ trống của tour
            if (!tourDao.updateQuantityAfterBooking(tourID, totalPeople)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    @Override
    public boolean updateBookStatusByBookCode(long bookCode, int newStatus) throws SQLException {
        String sql = "UPDATE BookDetail SET status = ? WHERE bookCode = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newStatus);
            stmt.setLong(2, bookCode);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static void main(String[] args) {
        BookTourDAO dao = new BookTourDAO();
        ArrayList<BookDetail> list = dao.getBookDetailsByAgent(1);
        for (BookDetail b : list) {
            System.out.println(b);
        }
    }

    @Override
    public ArrayList<BookDetail> createBookDetail(int userId, int tourID, int voucherID, Date bookDate, int numberAdult, int numberChildren, String payMethod, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, float totalPrice, int status, int paymentMethodID) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
