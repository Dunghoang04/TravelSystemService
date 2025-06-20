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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BookDetail;

/**
 *
 * @author Hung
 */
//
//0	Đang chờ xác nhận
//1	Đã xác nhận
//2	Đã thanh toán
//3	Bị hủy bởi người dùng
//4	Bị hủy bởi đại lý
//5	Đã hoàn thành chuyến đi
//6	Đã yêu cầu hoàn tiền
//7	Đã hoàn tiền
public class BookTourDAO extends DBContext implements IBookTour {

    @Override
    public ArrayList<BookDetail> getBookDetailsByAgent(int travelAgentID) {
        ArrayList<BookDetail> list = new ArrayList<>();
        String sql = "SELECT b.bookID, u.firstName + ' ' + u.lastName AS userName, "
                + "t.tourName, b.bookDate, b.numberAdult, b.numberChildren, "
                + "b.payMethod, b.firstName + ' ' + b.lastName AS fullName, "
                + "b.phone, b.gmail, b.note, b.isBookedForOther, "
                + "b.totalPrice, b.status "
                + "FROM BookDetail b "
                + "JOIN [User] u ON b.userID = u.userID "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "WHERE t.travelAgentID = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, travelAgentID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                BookDetail bd = new BookDetail();
                bd.setBookID(rs.getInt("bookID"));
                bd.setUserName(rs.getString("userName"));
                bd.setTourName(rs.getString("tourName"));
                bd.setBookDate(rs.getDate("bookDate"));
                bd.setNumberAdult(rs.getInt("numberAdult"));
                bd.setNumberChildren(rs.getInt("numberChildren"));
                bd.setPayMethod(rs.getString("payMethod"));
                bd.setFullName(rs.getString("fullName"));
                bd.setPhone(rs.getString("phone"));
                bd.setGmail(rs.getString("gmail"));
                bd.setNote(rs.getString("note"));
                bd.setIsBookedForOther(rs.getInt("isBookedForOther"));
                bd.setTotalPrice(rs.getFloat("totalPrice"));
                bd.setStatus(rs.getInt("status"));

                list.add(bd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList<BookDetail> getBookDetailByStatus(int status) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<BookDetail> createBookDetail(int userId, int tourID, int voucherID, Date bookDate, int numberAdult, int numberChildren, String payMethod, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, float totalPrice, int status) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        BookTourDAO dao = new BookTourDAO();
        ArrayList<BookDetail> list = dao.getBookDetailsByAgent(1);
        for (BookDetail b : list) {
            System.out.println(b);
        }
    }
}
