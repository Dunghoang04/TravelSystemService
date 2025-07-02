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
import java.util.List;
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

    public boolean insertBookDetail(int userID, int tourID, Integer voucherID, int numberAdult, int numberChildren, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, double totalPrice, int status) throws SQLException {
        String sql = "INSERT INTO BookDetail(userID, tourID, voucherID, bookDate, numberAdult, numberChildren, firstName, lastName, phone, gmail, note, isBookedForOther, totalPrice, status) "
                + "VALUES (?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "OUTPUT INSERTED.*"; // SQL Server syntax for returning inserted row
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
                if (!tourDao.updateQuantityAfterBooking(tourID, totalPeople)) {
                    conn.rollback();
                    return false;
                }

                conn.commit();
                return true;
            } catch (Exception e) {
                conn.rollback();
                System.err.println("Transaction Error in insertBookDetail: " + e.getMessage());
                throw e;
            }
        }
    }
    
    @Override
    public boolean changeStatus(int bookId, int status) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            conn.setAutoCommit(false); // Start transaction
          
            preparedStatement = conn.prepareStatement("UPDATE [dbo].[BookDetail] SET [status] = ? WHERE bookID = ?");
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            conn.commit(); // Commit transaction
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback on error
            }
            throw e;
        } finally {
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
}
