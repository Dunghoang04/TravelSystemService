/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-03  1.0        Hung              First implementation
 */

package dao;

import dal.DBContext;
import dao.IBookTour;
import dao.TourDAO;
import dao.TransactionHistoryDAO;
import dao.UserDAO;
import dao.VoucherDAO;
import dao.WalletDAO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.BookDetail;
import model.Tour;
import model.User;

/**
 *
 * @author Hung
 */
public class BookTourDAO extends DBContext implements IBookTour {

    // Status codes
    // 1: Đã thanh toán
    // 2: Bị hủy bởi người dùng
    // 3: Bị hủy bởi đại lý
    // 4: Đã hoàn thành chuyến đi
    // 5: Đã yêu cầu hoàn tiền
    // 6: Đã hoàn tiền
    // 7: Chờ thanh toán
    private static final String GET_BY_AGENT_SQL = "SELECT b.bookID, b.userID, b.tourID, b.voucherID, b.bookDate, "
            + "b.numberAdult, b.numberChildren, b.firstName, b.lastName, b.phone, b.gmail, b.note, "
            + "b.isBookedForOther, b.totalPrice, b.status "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE t.travelAgentID = ? "
            + "ORDER BY b.bookID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String COUNT_BY_AGENT_SQL = "SELECT COUNT(*) FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID WHERE t.travelAgentID = ?";

    private static final String GET_BY_AGENT_AND_STATUS_SQL = "SELECT b.bookID, b.userID, b.tourID, b.voucherID, b.bookDate, "
            + "b.numberAdult, b.numberChildren, b.firstName, b.lastName, b.phone, b.gmail, b.note, "
            + "b.isBookedForOther, b.totalPrice, b.status "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE t.travelAgentID = ? AND b.status = ? "
            + "ORDER BY b.bookID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String COUNT_BY_AGENT_AND_STATUS_SQL = "SELECT COUNT(*) FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID WHERE t.travelAgentID = ? AND b.status = ?";

    private static final String SEARCH_BY_NAME_AND_STATUS_SQL = "SELECT b.bookID, b.userID, b.tourID, b.voucherID, b.bookDate, "
            + "b.numberAdult, b.numberChildren, b.firstName, b.lastName, b.phone, b.gmail, b.note, "
            + "b.isBookedForOther, b.totalPrice, b.status "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE t.travelAgentID = ? AND (LOWER(b.firstName) LIKE LOWER(?) OR LOWER(b.lastName) LIKE LOWER(?)) AND b.status = ? "
            + "ORDER BY b.bookID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String COUNT_BY_NAME_AND_STATUS_SQL = "SELECT COUNT(*) FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID WHERE t.travelAgentID = ? AND "
            + "(LOWER(b.firstName) LIKE LOWER(?) OR LOWER(b.lastName) LIKE LOWER(?)) AND b.status = ?";

    private static final String SEARCH_BY_NAME_SQL = "SELECT b.bookID, b.userID, b.tourID, b.voucherID, b.bookDate, "
            + "b.numberAdult, b.numberChildren, b.firstName, b.lastName, b.phone, b.gmail, b.note, "
            + "b.isBookedForOther, b.totalPrice, b.status "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE t.travelAgentID = ? AND (LOWER(b.firstName) LIKE LOWER(?) OR LOWER(b.lastName) LIKE LOWER(?)) "
            + "ORDER BY b.bookID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String COUNT_BY_NAME_SQL = "SELECT COUNT(*) FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID WHERE t.travelAgentID = ? AND "
            + "(LOWER(b.firstName) LIKE LOWER(?) OR LOWER(b.lastName) LIKE LOWER(?))";

    private static final String GET_BY_STATUS_SQL = "SELECT * FROM BookDetail WHERE status = ? ORDER BY bookID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String COUNT_BY_STATUS_SQL = "SELECT COUNT(*) FROM BookDetail WHERE status = ?";

    private static final String GET_BY_ID_SQL = "SELECT * FROM BookDetail WHERE bookID = ?";

    private static final String UPDATE_STATUS_BOOKDETAIL = "UPDATE [BookDetail] SET status = ? WHERE bookID = ?";
    private static final String GET_LATEST_5_BOOKINGS = "select TOP 5 * from BookDetail bd JOIN Tour t ON bd.tourID=t.tourID JOIN TravelAgent ta ON t.travelAgentID=ta.travelAgentID where ta.travelAgentID=? ORDER BY bookDate DESC";

    @Override
    public ArrayList<BookDetail> getBookDetailsByAgent(int agentId) throws SQLException {
        return getBookDetailsByAgent(agentId, 1, 10); // Default to page 1, 10 items per page
    }

    @Override
    public ArrayList<BookDetail> getBookDetailsByAgent(int agentId, int page, int pageSize) throws SQLException {
        ArrayList<BookDetail> list = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_BY_AGENT_SQL)) {
            stmt.setInt(1, agentId);
            stmt.setInt(2, (page - 1) * pageSize); // OFFSET
            stmt.setInt(3, pageSize); // FETCH NEXT
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getBookDetailsByAgent: " + e.getMessage());
            throw e;
        }
        return list;
    }

    @Override
    public List<BookDetail> getBookDetailByStatus(int status) throws SQLException {
        return getBookDetailByStatus(status, 1, 10); // Default to page 1, 10 items per page
    }

    @Override
    public List<BookDetail> getBookDetailByStatus(int status, int page, int pageSize) throws SQLException {
        List<BookDetail> bookDetail = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_BY_STATUS_SQL)) {
            stmt.setInt(1, status);
            stmt.setInt(2, (page - 1) * pageSize); // OFFSET
            stmt.setInt(3, pageSize); // FETCH NEXT
            try (ResultSet rs = stmt.executeQuery()) {
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
                    bookDetail.add(bd);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getBookDetailByStatus: " + e.getMessage());
            throw e;
        }
        return bookDetail;
    }

    @Override
    public int countByStatus(int status) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(COUNT_BY_STATUS_SQL)) {
            stmt.setInt(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in countByStatus: " + e.getMessage());
            throw e;
        }
        return 0;
    }

    @Override
    public ArrayList<BookDetail> createBookDetail(int userId, int tourID, int voucherID, Date bookDate, int numberAdult, int numberChildren, String payMethod, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, float totalPrice, int status) throws SQLException {
        ArrayList<BookDetail> list = new ArrayList<>();
        String sql = "INSERT INTO BookDetail (userID, tourID, voucherID, bookDate, numberAdult, numberChildren, firstName, lastName, phone, gmail, note, isBookedForOther, totalPrice, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "OUTPUT INSERTED.*"; // SQL Server syntax for returning inserted row
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, tourID);
            stmt.setInt(3, voucherID);
            stmt.setDate(4, bookDate);
            stmt.setInt(5, numberAdult);
            stmt.setInt(6, numberChildren);
            stmt.setString(7, firstName);
            stmt.setString(8, lastName);
            stmt.setString(9, phone);
            stmt.setString(10, gmail);
            stmt.setString(11, note);
            stmt.setInt(12, isBookedForOther);
            stmt.setFloat(13, totalPrice);
            stmt.setInt(14, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in createBookDetail: " + e.getMessage());
            throw e;
        }
        return list;
    }

    @Override
    public BookDetail getBookDetailById(int bookDetailId) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {
            stmt.setInt(1, bookDetailId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
                    bd.setBookCode(rs.getLong("bookCode"));
                    return bd;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getBookDetailById: " + e.getMessage());
            throw e;
        }
        return null;
    }

    @Override
    public List<BookDetail> searchByName(int agentId, String name, int page, int pageSize) throws SQLException {
        List<BookDetail> bookDetail = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SEARCH_BY_NAME_SQL)) {
            stmt.setInt(1, agentId);
            stmt.setString(2, "%" + name + "%");
            stmt.setString(3, "%" + name + "%");
            stmt.setInt(4, (page - 1) * pageSize); // OFFSET
            stmt.setInt(5, pageSize); // FETCH NEXT
            try (ResultSet rs = stmt.executeQuery()) {
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
                    bookDetail.add(bd);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in searchByName: " + e.getMessage());
            throw e;
        }
        return bookDetail;
    }

    @Override
    public int countByName(int agentId, String name) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(COUNT_BY_NAME_SQL)) {
            stmt.setInt(1, agentId);
            stmt.setString(2, "%" + name + "%");
            stmt.setString(3, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in countByName: " + e.getMessage());
            throw e;
        }
        return 0;
    }

    @Override
    public List<BookDetail> searchByNameAndStatus(int agentId, String name, int status, int page, int pageSize) throws SQLException {
        List<BookDetail> bookDetail = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SEARCH_BY_NAME_AND_STATUS_SQL)) {
            stmt.setInt(1, agentId);
            stmt.setString(2, "%" + name + "%");
            stmt.setString(3, "%" + name + "%");
            stmt.setInt(4, status);
            stmt.setInt(5, (page - 1) * pageSize); // OFFSET
            stmt.setInt(6, pageSize); // FETCH NEXT
            try (ResultSet rs = stmt.executeQuery()) {
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
                    bookDetail.add(bd);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in searchByNameAndStatus: " + e.getMessage());
            throw e;
        }
        return bookDetail;
    }

    @Override
    public int countByNameAndStatus(int agentId, String name, int status) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(COUNT_BY_NAME_AND_STATUS_SQL)) {
            stmt.setInt(1, agentId);
            stmt.setString(2, "%" + name + "%");
            stmt.setString(3, "%" + name + "%");
            stmt.setInt(4, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in countByNameAndStatus: " + e.getMessage());
            throw e;
        }
        return 0;
    }

    @Override
    public List<BookDetail> getBookDetailsByAgentAndStatus(int agentId, int status, int page, int pageSize) throws SQLException {
        List<BookDetail> bookDetail = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_BY_AGENT_AND_STATUS_SQL)) {
            stmt.setInt(1, agentId);
            stmt.setInt(2, status);
            stmt.setInt(3, (page - 1) * pageSize); // OFFSET
            stmt.setInt(4, pageSize); // FETCH NEXT
            try (ResultSet rs = stmt.executeQuery()) {
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
                    bookDetail.add(bd);
                    System.out.println("Found record: " + bd.getBookID() + ", " + bd.getFirstName() + ", Status: " + bd.getStatus()); // Debug output
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getBookDetailsByAgentAndStatus: " + e.getMessage());
            throw e;
        }
        return bookDetail;
    }

    @Override
    public int countByAgent(int agentId) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(COUNT_BY_AGENT_SQL)) {
            stmt.setInt(1, agentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in countByAgent: " + e.getMessage());
            throw e;
        }
        return 0;
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
                if (!voucherDao.updateQuantityVoucherWhenUse(voucherID)) {
                    conn.rollback();
                    return false;
                }
            }

            WalletDAO walletDao = new WalletDAO();
            double amountOfWallet = walletDao.getWalletByUserId(userID).getBalance();
            int totalPrice_i = (int) totalPrice;
            if (!walletDao.minusBalanceByUserId(totalPrice, userID)) {
                conn.rollback();
                return false;
            }

            TransactionHistoryDAO thDAO = new TransactionHistoryDAO();
            TourDAO tourDao = new TourDAO();
            Tour tour = tourDao.searchTourByID(tourID);
            UserDAO userDao = new UserDAO();
            User user = userDao.getUserByID(userID);
            if (user == null) {
                conn.rollback();
                return false;
            }
            
            
            
            String transactionDescription = "Thanh toán tour: " + tour.getTourName();
            thDAO.insertTransaction(user.getUserID(), totalPrice, "PURCHASE", transactionDescription, amountOfWallet-totalPrice);

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
    public boolean inserBookDetailPayOs(int userID, int tourID, Integer voucherID, int numberAdult, int numberChildren, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, double totalPrice, int status, int paymentMethodID, long bookCode) throws SQLException {
        String sql = "INSERT INTO BookDetail(userID, tourID, voucherID, bookDate, numberAdult, numberChildren, "
                + "firstName, lastName, phone, gmail, note, isBookedForOther, totalPrice, status, paymentMethodID, bookCode) "
                + "VALUES (?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
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
    public Map<BookDetail, Tour> getBookingDetailById(int id) throws SQLException {
        String sql = "SELECT b.*FROM BookDetail b "
                + "WHERE b.bookID = ?";

        Map<BookDetail, Tour> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                BookDetail bd = new BookDetail();
                bd.setBookID(rs.getInt("bookID"));
                bd.setUserID(rs.getInt("userID"));
                bd.setTourID(rs.getInt("tourID"));
                bd.setVoucherID(rs.getInt("voucherID"));
                bd.setBookDate(rs.getDate("bookDate"));
                bd.setNumberAdult(rs.getInt("numberAdult"));
                bd.setNumberChildren(rs.getInt("numberChildren"));
                bd.setPaymentMethodId(rs.getInt("paymentMethodID"));
                bd.setFirstName(rs.getString("firstName"));
                bd.setLastName(rs.getString("lastName"));
                bd.setPhone(rs.getString("phone"));
                bd.setGmail(rs.getString("gmail"));
                bd.setNote(rs.getString("note"));
                bd.setIsBookedForOther(rs.getInt("isBookedForOther"));
                bd.setTotalPrice(rs.getFloat("totalPrice"));
                bd.setStatus(rs.getInt("status"));
                bd.setBookCode(rs.getLong("bookCode"));

                // Tour
                TourDAO tourDao = new TourDAO();
                Tour tour = tourDao.searchTourByID(rs.getInt("tourID"));

                result.put(bd, tour);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getStatusByBookId(int bookId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement("SELECT status FROM [dbo].[BookDetail] WHERE bookID = ?");
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("status");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
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
        return -1;
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

    @Override
    public void updateStatus(int bookId, int status) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            conn.setAutoCommit(false); // Start transaction
            preparedStatement = conn.prepareStatement(UPDATE_STATUS_BOOKDETAIL);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback on error
            }
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true); // Reset auto-commit
                conn.close();
            }
        }
    }

    @Override
    public List<BookDetail> getLatest5Bookings(int travelAgentId) throws SQLException {
        List<BookDetail> bookings = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(GET_LATEST_5_BOOKINGS);
            ps.setInt(1, travelAgentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                BookDetail booking = new BookDetail();
                booking.setBookID(rs.getInt("bookID"));
                booking.setUserID(rs.getInt("userID"));
                booking.setTourID(rs.getInt("tourID"));
                booking.setVoucherID(rs.getInt("voucherID")); // Nếu NULL -> 0
                booking.setBookDate(rs.getDate("bookDate"));
                booking.setNumberAdult(rs.getInt("numberAdult"));
                booking.setNumberChildren(rs.getInt("numberChildren"));
                booking.setFirstName(rs.getString("firstName"));
                booking.setLastName(rs.getString("lastName"));
                booking.setPhone(rs.getString("phone"));
                booking.setGmail(rs.getString("gmail"));
                booking.setNote(rs.getString("note"));
                booking.setIsBookedForOther(rs.getInt("isBookedForOther"));
                booking.setTotalPrice(rs.getFloat("totalPrice"));
                booking.setStatus(rs.getInt("status"));
                bookings.add(booking);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return bookings;
    }

    @Override
    public Map<BookDetail, String> getCurrentBookingByUser(int userId) throws SQLException {
        String sql = "SELECT b.*, t.tourName FROM BookDetail b "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "WHERE b.userID = ? AND b.status = 1"
                + "Order by b.bookDate Desc";  // status 1: đã thanh toán, 7: chờ thanh toán

        Map<BookDetail, String> map = new LinkedHashMap<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                BookDetail bd = new BookDetail();
                bd.setBookID(rs.getInt("bookID"));
                bd.setUserID(rs.getInt("userID"));
                bd.setTourID(rs.getInt("tourID"));
                bd.setVoucherID(rs.getInt("voucherID"));
                bd.setBookDate(rs.getDate("bookDate"));
                bd.setNumberAdult(rs.getInt("numberAdult"));
                bd.setNumberChildren(rs.getInt("numberChildren"));
                bd.setPaymentMethodId(rs.getInt("paymentMethodID"));
                bd.setFirstName(rs.getString("firstName"));
                bd.setLastName(rs.getString("lastName"));
                bd.setPhone(rs.getString("phone"));
                bd.setGmail(rs.getString("gmail"));
                bd.setNote(rs.getString("note"));
                bd.setIsBookedForOther(rs.getInt("isBookedForOther"));
                bd.setTotalPrice(rs.getFloat("totalPrice"));
                bd.setStatus(rs.getInt("status"));
                bd.setBookCode(rs.getLong("bookCode"));

                String tourName = rs.getString("tourName");
                map.put(bd, tourName);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return map;
    }

    @Override
    public Map<BookDetail, String> getFinishedBookingByUser(int userId) throws SQLException {
        String sql = "SELECT b.*, t.tourName FROM BookDetail b "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "WHERE b.userID = ? AND b.status IN (3,4,5,6)"
                + "Order by b.bookDate Desc";

        Map<BookDetail, String> map = new LinkedHashMap<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                BookDetail bd = new BookDetail();
                bd.setBookID(rs.getInt("bookID"));
                bd.setUserID(rs.getInt("userID"));
                bd.setTourID(rs.getInt("tourID"));
                bd.setVoucherID(rs.getInt("voucherID"));
                bd.setBookDate(rs.getDate("bookDate"));
                bd.setNumberAdult(rs.getInt("numberAdult"));
                bd.setNumberChildren(rs.getInt("numberChildren"));
                bd.setPaymentMethodId(rs.getInt("paymentMethodID"));
                bd.setFirstName(rs.getString("firstName"));
                bd.setLastName(rs.getString("lastName"));
                bd.setPhone(rs.getString("phone"));
                bd.setGmail(rs.getString("gmail"));
                bd.setNote(rs.getString("note"));
                bd.setIsBookedForOther(rs.getInt("isBookedForOther"));
                bd.setTotalPrice(rs.getFloat("totalPrice"));
                bd.setStatus(rs.getInt("status"));
                bd.setBookCode(rs.getLong("bookCode"));

                String tourName = rs.getString("tourName");
                map.put(bd, tourName);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return map;
    }

    @Override
    public List<BookDetail> getBookingToCompletes() throws Exception {
        List<BookDetail> list = new ArrayList<>();
        String sql = "SELECT b.* FROM BookDetail b "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "WHERE b.status = 1 AND t.endDay < GETDATE()"; // Chỉ lấy booking đã thanh toán và tour đã kết thúc
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
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

                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                stmt.close();
            }
        }
        return list;
    }

    @Override
    public Map<BookDetail, String[]> getBookDetailByStatus(int status, Date fromDate, Date toDate) throws SQLException {
        String sql = "SELECT b.*, t.tourName, t.endDay, ta.travelAgentGmail "
                + "FROM BookDetail b "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "JOIN TravelAgent ta ON t.travelAgentID = ta.travelAgentID "
                + "WHERE b.status = ? AND t.endDay BETWEEN ? AND ? "
                + "ORDER BY t.endDay DESC";

        Map<BookDetail, String[]> map = new LinkedHashMap<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, status);
            stmt.setDate(2, new java.sql.Date(fromDate.getTime()));
            stmt.setDate(3, new java.sql.Date(toDate.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BookDetail bd = new BookDetail();
                    bd.setBookID(rs.getInt("bookID"));
                    bd.setUserID(rs.getInt("userID"));
                    bd.setTourID(rs.getInt("tourID"));
                    bd.setVoucherID(rs.getInt("voucherID"));
                    bd.setBookDate(rs.getDate("bookDate"));
                    bd.setNumberAdult(rs.getInt("numberAdult"));
                    bd.setNumberChildren(rs.getInt("numberChildren"));
                    bd.setPaymentMethodId(rs.getInt("paymentMethodID"));
                    bd.setFirstName(rs.getString("firstName"));
                    bd.setLastName(rs.getString("lastName"));
                    bd.setPhone(rs.getString("phone"));
                    bd.setGmail(rs.getString("gmail"));
                    bd.setNote(rs.getString("note"));
                    bd.setIsBookedForOther(rs.getInt("isBookedForOther"));
                    bd.setTotalPrice(rs.getFloat("totalPrice"));
                    bd.setStatus(rs.getInt("status"));
                    bd.setBookCode(rs.getLong("bookCode"));

                    String tourName = rs.getString("tourName");
                    String agentGmail = rs.getString("travelAgentGmail");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String endDay = sdf.format(rs.getDate("endDay"));

                    map.put(bd, new String[]{tourName, agentGmail, endDay});
                }
            }
        }
        return map;
    }

    @Override
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

    private static final String GET_TOTAL_REVENUE_BY_STATUS_SQL
            = "SELECT SUM(totalPrice) as totalRevenue "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE b.status = ?";

    @Override
    public double getTotalRevenueByStatus(int status) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_TOTAL_REVENUE_BY_STATUS_SQL)) {
            stmt.setInt(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("totalRevenue");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getTotalRevenueByStatus: " + e.getMessage());
            throw e;
        }
        return 0.0;
    }

    private static final String GET_BOOK_DETAILS_WITH_FILTERS_SQL
            = "SELECT b.bookID, b.userID, b.tourID, b.voucherID, b.bookDate, "
            + "b.numberAdult, b.numberChildren, b.firstName, b.lastName, b.phone, b.gmail, b.note, "
            + "b.isBookedForOther, b.totalPrice, b.status "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE (? IS NULL OR t.travelAgentID = ?) AND (? IS NULL OR b.status = ?) "
            + "ORDER BY b.bookID";

    private static final String COUNT_BOOK_DETAILS_WITH_FILTERS_SQL
            = "SELECT COUNT(*) "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE (? IS NULL OR t.travelAgentID = ?) AND (? IS NULL OR b.status = ?)";

    @Override
    public ArrayList<BookDetail> getBookDetailsWithFilters(Integer agentId, Integer status, String name) throws SQLException {
        ArrayList<BookDetail> list = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_BOOK_DETAILS_WITH_FILTERS_SQL)) {
            // Xử lý agentId
            if (agentId == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(1, agentId);
                stmt.setInt(2, agentId);
            }
            // Xử lý status
            if (status == null) {
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(3, status);
                stmt.setInt(4, status);
            }
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getBookDetailsWithFilters: " + e.getMessage());
            throw e;
        }
        return list;
    }

    @Override
    public int getTotalBookDetailsWithFilters(Integer agentId, Integer status, String name) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(COUNT_BOOK_DETAILS_WITH_FILTERS_SQL)) {
            // Xử lý agentId
            if (agentId == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(1, agentId);
                stmt.setInt(2, agentId);
            }
            // Xử lý status
            if (status == null) {
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(3, status);
                stmt.setInt(4, status);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getTotalBookDetailsWithFilters: " + e.getMessage());
            throw e;
        }
        return 0;
    }
    
    private static final String GET_BOOK_DETAILS_WITH_FILTERS_AND_DATE_SQL
            = "SELECT b.bookID, b.userID, b.tourID, b.voucherID, b.bookDate, "
            + "b.numberAdult, b.numberChildren, b.firstName, b.lastName, b.phone, b.gmail, b.note, "
            + "b.isBookedForOther, b.totalPrice, b.status "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE (? IS NULL OR t.travelAgentID = ?) AND (? IS NULL OR b.status = ?) "
            + "AND (? IS NULL OR b.bookDate >= ?) AND (? IS NULL OR b.bookDate <= ?) "
            + "ORDER BY b.bookID";

    private static final String COUNT_BOOK_DETAILS_WITH_FILTERS_AND_DATE_SQL
            = "SELECT COUNT(*) "
            + "FROM BookDetail b JOIN Tour t ON b.tourID = t.tourID "
            + "WHERE (? IS NULL OR t.travelAgentID = ?) AND (? IS NULL OR b.status = ?) "
            + "AND (? IS NULL OR b.bookDate >= ?) AND (? IS NULL OR b.bookDate <= ?)";

    @Override
    public ArrayList<BookDetail> getBookDetailsWithFiltersAndDate(Integer agentId, Integer status, String name, Date startDate, Date endDate) throws SQLException {
        ArrayList<BookDetail> list = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_BOOK_DETAILS_WITH_FILTERS_AND_DATE_SQL)) {
            // Xử lý agentId
            if (agentId == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(1, agentId);
                stmt.setInt(2, agentId);
            }
            // Xử lý status
            if (status == null) {
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(3, status);
                stmt.setInt(4, status);
            }
            // Xử lý startDate
            if (startDate == null) {
                stmt.setNull(5, Types.DATE);
                stmt.setNull(6, Types.DATE);
            } else {
                stmt.setDate(5, new java.sql.Date(startDate.getTime()));
                stmt.setDate(6, new java.sql.Date(startDate.getTime()));
            }
            // Xử lý endDate
            if (endDate == null) {
                stmt.setNull(7, Types.DATE);
                stmt.setNull(8, Types.DATE);
            } else {
                stmt.setDate(7, new java.sql.Date(endDate.getTime()));
                stmt.setDate(8, new java.sql.Date(endDate.getTime()));
            }
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw e;
        }
        return list;
    }

    @Override
    public int getTotalBookDetailsWithFiltersAndDate(Integer agentId, Integer status, String name, Date startDate, Date endDate) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(COUNT_BOOK_DETAILS_WITH_FILTERS_AND_DATE_SQL)) {
            // Xử lý agentId
            if (agentId == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(1, agentId);
                stmt.setInt(2, agentId);
            }
            // Xử lý status
            if (status == null) {
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(3, status);
                stmt.setInt(4, status);
            }
            // Xử lý startDate
            if (startDate == null) {
                stmt.setNull(5, Types.DATE);
                stmt.setNull(6, Types.DATE);
            } else {
                stmt.setDate(5, new java.sql.Date(startDate.getTime()));
                stmt.setDate(6, new java.sql.Date(startDate.getTime()));
            }
            // Xử lý endDate
            if (endDate == null) {
                stmt.setNull(7, Types.DATE);
                stmt.setNull(8, Types.DATE);
            } else {
                stmt.setDate(7, new java.sql.Date(endDate.getTime()));
                stmt.setDate(8, new java.sql.Date(endDate.getTime()));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getTotalBookDetailsWithFiltersAndDate: " + e.getMessage());
            throw e;
        }
        return 0;
    }
}
