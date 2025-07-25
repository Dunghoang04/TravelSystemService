/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Hung              First implementation
 */
package dao;

import dal.DBContext;
import java.sql.Date; // Để dùng cho cột DATE
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.BookDetail;
import model.RequestCancel;
import model.RequestCancelFullDTO;
import model.Tour;
import model.TravelAgent;

/**
 *
 * @author Dell
 */
public class RequestCancelDAO extends DBContext implements IRequestCancel {

    @Override
    public void saveCancelRequest(int bookID, int userID, String reason) throws SQLException {
        String sql = "INSERT INTO [dbo].[Request_Cancel]\n"
                + "           ([bookID]\n"
                + "           ,[userID]\n"
                + "           ,[requestDate]\n"
                + "           ,[reason]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookID);
            stmt.setInt(2, userID);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setString(4, reason);
            stmt.setString(5, "PENDING"); // Assuming 1 means active request
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();  // 👈 thêm dòng này
        }
    }
    
    @Override
    public boolean createReasonCancel(int bookId, int userId, String reason) throws Exception {
        String sql = "INSERT INTO Request_Cancel (bookID, reason,userID)\n"
                + "VALUES (?, ?, ?);";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            stmt.setString(2, reason);
            stmt.setInt(3, userId);
            int check = stmt.executeUpdate();
            return check > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return false;
    }
    
    @Override
    public List<RequestCancelFullDTO> getAllTouristCancelRequests() throws Exception {
        List<RequestCancelFullDTO> list = new ArrayList<>();
        String sql = "SELECT rc.requestCancelID, rc.bookID, rc.userID, rc.requestDate, rc.reason, rc.status, "
                + "b.bookCode, b.bookDate, b.totalPrice, b.numberAdult, b.numberChildren,b.gmail, "
                + "t.tourID, t.tourName, t.startPlace, t.endPlace, t.startDay "
                + "FROM Request_Cancel rc "
                + "JOIN BookDetail b ON rc.bookID = b.bookID "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "JOIN [User] u ON rc.userID = u.userID "
                + "WHERE u.roleID = 3";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // RequestCancel
                RequestCancel rc = new RequestCancel();
                rc.setRequestCancelID(rs.getInt("requestCancelID"));
                rc.setBookID(rs.getInt("bookID"));
                rc.setUserID(rs.getInt("userID"));
                rc.setRequestDate(rs.getDate("requestDate"));
                rc.setReason(rs.getString("reason"));
                int status = 0;
                if (rs.getString("status").equalsIgnoreCase("PENDING")) {
                    status = 1;
                } else if (rs.getString("status").equalsIgnoreCase("FINISHED")) {
                    status = 2;
                } else {
                    status = 3;
                }
                rc.setStatus(status);

                // BookDetail
                BookDetail book = new BookDetail();
                book.setBookID(rs.getInt("bookID"));
                book.setBookCode(rs.getLong("bookCode"));
                book.setBookDate(rs.getDate("bookDate"));
                book.setTotalPrice(rs.getFloat("totalPrice"));
                book.setNumberAdult(rs.getInt("numberAdult"));
                book.setNumberChildren(rs.getInt("numberChildren"));
                book.setGmail(rs.getString("gmail"));

                // Tour
                Tour tour = new Tour();
                tour.setTourID(rs.getInt("tourID")); // Sửa lại tên đúng
                tour.setTourName(rs.getString("tourName"));
                tour.setStartPlace(rs.getString("startPlace"));
                tour.setEndPlace(rs.getString("endPlace"));
                tour.setStartDay(rs.getDate("startDay")); // Gán ngày khởi hành

                // Gộp DTO
                RequestCancelFullDTO dto = new RequestCancelFullDTO();
                dto.setRequestCancel(rc);
                dto.setBookDetail(book);
                dto.setTour(tour);

                list.add(dto);
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
                rs.close();
            }
        }
        return list;
    }

    @Override
    public List<RequestCancelFullDTO> searchTouristCancelRequestByEmail(String email) throws Exception {
        List<RequestCancelFullDTO> list = new ArrayList<>();
        String sql = "SELECT rc.requestCancelID, rc.bookID, rc.userID, rc.requestDate, rc.reason, rc.status, "
                + "b.bookCode, b.bookDate, b.totalPrice, b.numberAdult, b.numberChildren, b.gmail, "
                + "t.tourID, t.tourName, t.startPlace, t.endPlace, t.startDay "
                + "FROM Request_Cancel rc "
                + "JOIN BookDetail b ON rc.bookID = b.bookID "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "JOIN [User] u ON rc.userID = u.userID "
                + "WHERE b.gmail LIKE ? and u.roleID = 3";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + email + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RequestCancel rc = new RequestCancel();
                rc.setRequestCancelID(rs.getInt("requestCancelID"));
                rc.setBookID(rs.getInt("bookID"));
                rc.setUserID(rs.getInt("userID"));
                rc.setRequestDate(rs.getDate("requestDate"));
                rc.setReason(rs.getString("reason"));
                int status = 0;
                if (rs.getString("status").equalsIgnoreCase("PENDING")) {
                    status = 1;
                } else if (rs.getString("status").equalsIgnoreCase("FINISHED")) {
                    status = 2;
                } else {
                    status = 3;
                }
                rc.setStatus(status);

                BookDetail book = new BookDetail();
                book.setBookID(rs.getInt("bookID"));
                book.setBookCode(rs.getLong("bookCode"));
                book.setBookDate(rs.getDate("bookDate"));
                book.setTotalPrice(rs.getFloat("totalPrice"));
                book.setNumberAdult(rs.getInt("numberAdult"));
                book.setNumberChildren(rs.getInt("numberChildren"));
                book.setGmail(rs.getString("gmail"));

                Tour tour = new Tour();
                tour.setTourID(rs.getInt("tourID"));
                tour.setTourName(rs.getString("tourName"));
                tour.setStartPlace(rs.getString("startPlace"));
                tour.setEndPlace(rs.getString("endPlace"));
                tour.setStartDay(rs.getDate("startDay"));

                RequestCancelFullDTO dto = new RequestCancelFullDTO();
                dto.setRequestCancel(rc);
                dto.setBookDetail(book);
                dto.setTour(tour);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<RequestCancelFullDTO> filterTouristCancelRequestByStatus(int status) throws Exception {
        List<RequestCancelFullDTO> list = new ArrayList<>();

        String statusString = null;
        if (status == 1) {
            statusString = "PENDING";
        } else if (status == 2) {
            statusString = "FINISHED";
        } else if (status == 3) {
            statusString = "REJECTED";
        }
        if (statusString == null) {
            return list; // Không hợp lệ
        }
        String sql = "SELECT rc.requestCancelID, rc.bookID, rc.userID, rc.requestDate, rc.reason, rc.status, "
                + "b.bookCode, b.bookDate, b.totalPrice, b.numberAdult, b.numberChildren, b.gmail, "
                + "t.tourID, t.tourName, t.startPlace, t.endPlace, t.startDay "
                + "FROM Request_Cancel rc "
                + "JOIN BookDetail b ON rc.bookID = b.bookID "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "JOIN [User] u ON rc.userID = u.userID "
                + "WHERE rc.status = ? and u.roleID = 3";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statusString);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RequestCancel rc = new RequestCancel();
                rc.setRequestCancelID(rs.getInt("requestCancelID"));
                rc.setBookID(rs.getInt("bookID"));
                rc.setUserID(rs.getInt("userID"));
                rc.setRequestDate(rs.getDate("requestDate"));
                rc.setReason(rs.getString("reason"));
                int statusField = 0;
                if (rs.getString("status").equalsIgnoreCase("PENDING")) {
                    statusField = 1;
                } else if (rs.getString("status").equalsIgnoreCase("FINISHED")) {
                    statusField = 2;
                } else {
                    statusField = 3;
                }
                rc.setStatus(status);

                BookDetail book = new BookDetail();
                book.setBookID(rs.getInt("bookID"));
                book.setBookCode(rs.getLong("bookCode"));
                book.setBookDate(rs.getDate("bookDate"));
                book.setTotalPrice(rs.getFloat("totalPrice"));
                book.setNumberAdult(rs.getInt("numberAdult"));
                book.setNumberChildren(rs.getInt("numberChildren"));
                book.setGmail(rs.getString("gmail"));

                Tour tour = new Tour();
                tour.setTourID(rs.getInt("tourID"));
                tour.setTourName(rs.getString("tourName"));
                tour.setStartPlace(rs.getString("startPlace"));
                tour.setEndPlace(rs.getString("endPlace"));
                tour.setStartDay(rs.getDate("startDay"));

                RequestCancelFullDTO dto = new RequestCancelFullDTO();
                dto.setRequestCancel(rc);
                dto.setBookDetail(book);
                dto.setTour(tour);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public RequestCancelFullDTO getRequestCancelFullById(int id) throws Exception {
        String sql = "  SELECT \n"
                + "            rc.requestCancelID, rc.bookID, rc.userID, rc.requestDate, rc.reason, rc.status AS cancelStatus,\n"
                + "\n"
                + "            b.bookID,b.userID as touristId, b.bookCode, b.bookDate, b.totalPrice, b.numberAdult, b.numberChildren, \n"
                + "            b.firstName, b.lastName, b.phone, b.gmail, b.note AS bookingNote,\n"
                + "            b.isBookedForOther, b.status AS bookStatus, b.paymentMethodID, b.voucherID,\n"
                + "\n"
                + "            t.tourID, t.tourName, t.tourIntroduce, t.tourSchedule, t.tourInclude, t.tourNonInclude,\n"
                + "            t.numberOfDay, t.startPlace, t.endPlace, t.startDay, t.endDay, \n"
                + "            t.quantity, t.adultPrice, t.childrenPrice, t.image, t.status AS tourStatus\n"
                + "        FROM \n"
                + "            Request_Cancel rc\n"
                + "        JOIN \n"
                + "            BookDetail b ON rc.bookID = b.bookID\n"
                + "        JOIN \n"
                + "            Tour t ON b.tourID = t.tourID\n"
                + "        WHERE \n"
                + "            rc.requestCancelID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                RequestCancel rc = new RequestCancel();
                rc.setRequestCancelID(rs.getInt("requestCancelID"));
                rc.setBookID(rs.getInt("bookID"));
                rc.setUserID(rs.getInt("userID"));
                rc.setRequestDate(rs.getDate("requestDate"));
                rc.setReason(rs.getString("reason"));

                String statusText = rs.getString("cancelStatus");
                int status = switch (statusText) {
                    case "FINISHED" ->
                        2;
                    case "REJECTED" ->
                        3;
                    default ->
                        1;
                };
                rc.setStatus(status);

                // BookDetail
                BookDetail book = new BookDetail();
                book.setBookID(rs.getInt("bookID"));
                book.setBookCode(rs.getLong("bookCode"));
                book.setUserID(rs.getInt("touristId"));
                book.setBookDate(rs.getDate("bookDate"));
                book.setTotalPrice(rs.getLong("totalPrice"));
                book.setNumberAdult(rs.getInt("numberAdult"));
                book.setNumberChildren(rs.getInt("numberChildren"));
                book.setFirstName(rs.getString("firstName"));
                book.setLastName(rs.getString("lastName"));
                book.setPhone(rs.getString("phone"));
                book.setGmail(rs.getString("gmail"));
                book.setNote(rs.getString("bookingNote"));
                book.setIsBookedForOther(rs.getInt("isBookedForOther"));
                book.setStatus(rs.getInt("bookStatus"));
                book.setPaymentMethodId(rs.getInt("paymentMethodID"));
                int voucherId = rs.getInt("voucherID");
                if (!rs.wasNull()) {
                    book.setVoucherID(voucherId);
                }

                // Tour
                Tour tour = new Tour();
                tour.setTourID(rs.getInt("tourID"));
                tour.setTourName(rs.getString("tourName"));
                tour.setTourIntroduce(rs.getString("tourIntroduce"));
                tour.setTourSchedule(rs.getString("tourSchedule"));
                tour.setTourInclude(rs.getString("tourInclude"));
                tour.setTourNonInclude(rs.getString("tourNonInclude"));
                tour.setNumberOfDay(rs.getInt("numberOfDay"));
                tour.setStartPlace(rs.getString("startPlace"));
                tour.setEndPlace(rs.getString("endPlace"));
                tour.setStartDay(rs.getDate("startDay"));
                tour.setEndDay(rs.getDate("endDay"));
                tour.setQuantity(rs.getInt("quantity"));
                tour.setAdultPrice(rs.getDouble("adultPrice"));
                tour.setChildrenPrice(rs.getDouble("childrenPrice"));
                tour.setImage(rs.getString("image"));
                tour.setStatus(rs.getInt("tourStatus"));

                // Build DTO
                RequestCancelFullDTO dto = new RequestCancelFullDTO();
                dto.setRequestCancel(rc);
                dto.setBookDetail(book);
                dto.setTour(tour);
                return dto;
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
                rs.close();
            }
        }
        return null;
    }

    @Override
    public boolean changeStatusRequestCancel(int requestCancelId, String status) throws Exception {
        String sql = "  UPDATE Request_Cancel SET status = ? WHERE requestCancelID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, requestCancelId);
            int check = stmt.executeUpdate();
            return check > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return false;
    }

    @Override
    public List<RequestCancelFullDTO> getAllTravelAgentCancelRequests() throws Exception {
        List<RequestCancelFullDTO> list = new ArrayList<>();
        String sql = "SELECT rc.requestCancelID, rc.bookID, rc.userID, rc.requestDate, rc.reason, rc.status, "
                + "b.bookCode, b.bookDate, b.totalPrice, b.numberAdult, b.numberChildren,b.gmail, "
                + "t.tourID, t.tourName, t.startPlace, t.endPlace, t.startDay "
                + "FROM Request_Cancel rc "
                + "JOIN BookDetail b ON rc.bookID = b.bookID "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "JOIN [User] u ON rc.userID = u.userID "
                + "WHERE u.roleID = 4";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // RequestCancel
                RequestCancel rc = new RequestCancel();
                rc.setRequestCancelID(rs.getInt("requestCancelID"));
                rc.setBookID(rs.getInt("bookID"));
                rc.setUserID(rs.getInt("userID"));
                rc.setRequestDate(rs.getDate("requestDate"));
                rc.setReason(rs.getString("reason"));
                int status = 0;
                if (rs.getString("status").equalsIgnoreCase("PENDING")) {
                    status = 1;
                } else if (rs.getString("status").equalsIgnoreCase("FINISHED")) {
                    status = 2;
                } else {
                    status = 3;
                }
                rc.setStatus(status);

                // BookDetail
                BookDetail book = new BookDetail();
                book.setBookID(rs.getInt("bookID"));
                book.setBookCode(rs.getLong("bookCode"));
                book.setBookDate(rs.getDate("bookDate"));
                book.setTotalPrice(rs.getFloat("totalPrice"));
                book.setNumberAdult(rs.getInt("numberAdult"));
                book.setNumberChildren(rs.getInt("numberChildren"));
                book.setGmail(rs.getString("gmail"));

                // Tour
                Tour tour = new Tour();
                tour.setTourID(rs.getInt("tourID")); // Sửa lại tên đúng
                tour.setTourName(rs.getString("tourName"));
                tour.setStartPlace(rs.getString("startPlace"));
                tour.setEndPlace(rs.getString("endPlace"));
                tour.setStartDay(rs.getDate("startDay")); // Gán ngày khởi hành

                // Gộp DTO
                RequestCancelFullDTO dto = new RequestCancelFullDTO();
                dto.setRequestCancel(rc);
                dto.setBookDetail(book);
                dto.setTour(tour);

                list.add(dto);
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
                rs.close();
            }
        }
        return list;
    }

    @Override
    public List<RequestCancelFullDTO> searchTravelAgentCancelRequestByEmail(String email) throws Exception {
        List<RequestCancelFullDTO> list = new ArrayList<>();
        String sql = "SELECT rc.requestCancelID, rc.bookID, rc.userID, rc.requestDate, rc.reason, rc.status, "
                + "b.bookCode, b.bookDate, b.totalPrice, b.numberAdult, b.numberChildren, b.gmail, "
                + "t.tourID, t.tourName, t.startPlace, t.endPlace, t.startDay "
                + "FROM Request_Cancel rc "
                + "JOIN BookDetail b ON rc.bookID = b.bookID "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "JOIN [User] u ON rc.userID = u.userID "
                + "WHERE b.gmail LIKE ? and u.roleID = 4";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + email + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RequestCancel rc = new RequestCancel();
                rc.setRequestCancelID(rs.getInt("requestCancelID"));
                rc.setBookID(rs.getInt("bookID"));
                rc.setUserID(rs.getInt("userID"));
                rc.setRequestDate(rs.getDate("requestDate"));
                rc.setReason(rs.getString("reason"));
                int status = 0;
                if (rs.getString("status").equalsIgnoreCase("PENDING")) {
                    status = 1;
                } else if (rs.getString("status").equalsIgnoreCase("FINISHED")) {
                    status = 2;
                } else {
                    status = 3;
                }
                rc.setStatus(status);

                BookDetail book = new BookDetail();
                book.setBookID(rs.getInt("bookID"));
                book.setBookCode(rs.getLong("bookCode"));
                book.setBookDate(rs.getDate("bookDate"));
                book.setTotalPrice(rs.getFloat("totalPrice"));
                book.setNumberAdult(rs.getInt("numberAdult"));
                book.setNumberChildren(rs.getInt("numberChildren"));
                book.setGmail(rs.getString("gmail"));

                Tour tour = new Tour();
                tour.setTourID(rs.getInt("tourID"));
                tour.setTourName(rs.getString("tourName"));
                tour.setStartPlace(rs.getString("startPlace"));
                tour.setEndPlace(rs.getString("endPlace"));
                tour.setStartDay(rs.getDate("startDay"));

                RequestCancelFullDTO dto = new RequestCancelFullDTO();
                dto.setRequestCancel(rc);
                dto.setBookDetail(book);
                dto.setTour(tour);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<RequestCancelFullDTO> filterTravelAgentCancelRequestByStatus(int status) throws Exception {
        List<RequestCancelFullDTO> list = new ArrayList<>();

        String statusString = null;
        if (status == 1) {
            statusString = "PENDING";
        } else if (status == 2) {
            statusString = "FINISHED";
        } else if (status == 3) {
            statusString = "REJECTED";
        }
        if (statusString == null) {
            return list; // Không hợp lệ
        }
        String sql = "SELECT rc.requestCancelID, rc.bookID, rc.userID, rc.requestDate, rc.reason, rc.status, "
                + "b.bookCode, b.bookDate, b.totalPrice, b.numberAdult, b.numberChildren, b.gmail, "
                + "t.tourID, t.tourName, t.startPlace, t.endPlace, t.startDay "
                + "FROM Request_Cancel rc "
                + "JOIN BookDetail b ON rc.bookID = b.bookID "
                + "JOIN Tour t ON b.tourID = t.tourID "
                + "JOIN [User] u ON rc.userID = u.userID "
                + "WHERE rc.status = ? and u.roleID = 4";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statusString);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RequestCancel rc = new RequestCancel();
                rc.setRequestCancelID(rs.getInt("requestCancelID"));
                rc.setBookID(rs.getInt("bookID"));
                rc.setUserID(rs.getInt("userID"));
                rc.setRequestDate(rs.getDate("requestDate"));
                rc.setReason(rs.getString("reason"));
                int statusField = 0;
                if (rs.getString("status").equalsIgnoreCase("PENDING")) {
                    statusField = 1;
                } else if (rs.getString("status").equalsIgnoreCase("FINISHED")) {
                    statusField = 2;
                } else {
                    statusField = 3;
                }
                rc.setStatus(status);

                BookDetail book = new BookDetail();
                book.setBookID(rs.getInt("bookID"));
                book.setBookCode(rs.getLong("bookCode"));
                book.setBookDate(rs.getDate("bookDate"));
                book.setTotalPrice(rs.getFloat("totalPrice"));
                book.setNumberAdult(rs.getInt("numberAdult"));
                book.setNumberChildren(rs.getInt("numberChildren"));
                book.setGmail(rs.getString("gmail"));

                Tour tour = new Tour();
                tour.setTourID(rs.getInt("tourID"));
                tour.setTourName(rs.getString("tourName"));
                tour.setStartPlace(rs.getString("startPlace"));
                tour.setEndPlace(rs.getString("endPlace"));
                tour.setStartDay(rs.getDate("startDay"));

                RequestCancelFullDTO dto = new RequestCancelFullDTO();
                dto.setRequestCancel(rc);
                dto.setBookDetail(book);
                dto.setTour(tour);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<RequestCancelFullDTO, TravelAgent> getTravelAgentRequestCancelFullById(int id) throws Exception {
        Map<RequestCancelFullDTO, TravelAgent> result = new HashMap<>();

        String sql = "SELECT \n"
                + "  rc.requestCancelID, rc.bookID, rc.userID, rc.requestDate, rc.reason, rc.status AS cancelStatus,\n"
                + "  b.bookID, b.bookCode, b.bookDate, b.totalPrice, b.numberAdult, b.numberChildren, \n"
                + "  b.firstName, b.lastName, b.phone, b.gmail, b.note AS bookingNote,\n"
                + "  b.isBookedForOther, b.status AS bookStatus, b.paymentMethodID, b.voucherID,\n"
                + "  t.tourID, t.tourName, t.tourIntroduce, t.tourSchedule, t.tourInclude, t.tourNonInclude,\n"
                + "  t.numberOfDay, t.startPlace, t.endPlace, t.startDay, t.endDay, \n"
                + "  t.quantity, t.adultPrice, t.childrenPrice, t.image, t.status AS tourStatus, t.travelAgentID,\n"
                + "  ta.travelAgentID AS taID, ta.travelAgentName, ta.travelAgentGmail AS agentEmail, ta.hotLine AS agentPhone\n"
                + "FROM Request_Cancel rc\n"
                + "JOIN BookDetail b ON rc.bookID = b.bookID\n"
                + "JOIN Tour t ON b.tourID = t.tourID\n"
                + "JOIN TravelAgent ta ON t.travelAgentID = ta.travelAgentID\n"
                + "WHERE rc.requestCancelID = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // RequestCancel
                RequestCancel rc = new RequestCancel();
                rc.setRequestCancelID(rs.getInt("requestCancelID"));
                rc.setBookID(rs.getInt("bookID"));
                rc.setUserID(rs.getInt("userID"));
                rc.setRequestDate(rs.getDate("requestDate"));
                rc.setReason(rs.getString("reason"));
                int status = switch (rs.getString("cancelStatus")) {
                    case "FINISHED" ->
                        2;
                    case "REJECTED" ->
                        3;
                    default ->
                        1;
                };
                rc.setStatus(status);

                // BookDetail
                BookDetail book = new BookDetail();
                book.setBookID(rs.getInt("bookID"));
                book.setBookCode(rs.getLong("bookCode"));
                book.setBookDate(rs.getDate("bookDate"));
                book.setTotalPrice(rs.getLong("totalPrice"));
                book.setNumberAdult(rs.getInt("numberAdult"));
                book.setNumberChildren(rs.getInt("numberChildren"));
                book.setFirstName(rs.getString("firstName"));
                book.setLastName(rs.getString("lastName"));
                book.setPhone(rs.getString("phone"));
                book.setGmail(rs.getString("gmail"));
                book.setNote(rs.getString("bookingNote"));
                book.setIsBookedForOther(rs.getInt("isBookedForOther"));
                book.setStatus(rs.getInt("bookStatus"));
                book.setPaymentMethodId(rs.getInt("paymentMethodID"));
                int voucherId = rs.getInt("voucherID");
                if (!rs.wasNull()) {
                    book.setVoucherID(voucherId);
                }

                // Tour
                Tour tour = new Tour();
                tour.setTourID(rs.getInt("tourID"));
                tour.setTourName(rs.getString("tourName"));
                tour.setTourIntroduce(rs.getString("tourIntroduce"));
                tour.setTourSchedule(rs.getString("tourSchedule"));
                tour.setTourInclude(rs.getString("tourInclude"));
                tour.setTourNonInclude(rs.getString("tourNonInclude"));
                tour.setNumberOfDay(rs.getInt("numberOfDay"));
                tour.setStartPlace(rs.getString("startPlace"));
                tour.setEndPlace(rs.getString("endPlace"));
                tour.setStartDay(rs.getDate("startDay"));
                tour.setEndDay(rs.getDate("endDay"));
                tour.setQuantity(rs.getInt("quantity"));
                tour.setAdultPrice(rs.getDouble("adultPrice"));
                tour.setChildrenPrice(rs.getDouble("childrenPrice"));
                tour.setImage(rs.getString("image"));
                tour.setStatus(rs.getInt("tourStatus"));

                // TravelAgent
                TravelAgent agent = new TravelAgent();
                agent.setTravelAgentID(rs.getInt("taID"));
                agent.setTravelAgentName(rs.getString("travelAgentName"));
                agent.setTravelAgentGmail(rs.getString("agentEmail"));
                agent.setHotLine(rs.getString("agentPhone"));

                // Gán vào DTO
                RequestCancelFullDTO dto = new RequestCancelFullDTO();
                dto.setRequestCancel(rc);
                dto.setBookDetail(book);
                dto.setTour(tour);

                result.put(dto, agent);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }
    public static void main(String[] args) throws Exception {
        RequestCancelDAO r=new RequestCancelDAO();
        System.out.println(r.getAllTravelAgentCancelRequests().size());
    }
}
