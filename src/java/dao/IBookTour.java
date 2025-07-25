/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hung              First implementation
 */

package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.BookDetail;
import model.Tour;

/**
 *
 * @author Hung
 */
public interface IBookTour {

    /**
     * Retrieves a list of book details for a specific agent with default
     * pagination.
     *
     * @param agentId the ID of the travel agent
     * @return a list of BookDetail objects
     * @throws SQLException if a database error occurs
     */
    ArrayList<BookDetail> getBookDetailsByAgent(int agentId) throws SQLException;

    /**
     * Retrieves a paginated list of book details for a specific agent.
     *
     * @param agentId the ID of the travel agent
     * @param page the page number (1-based)
     * @param pageSize the number of items per page
     * @return a list of BookDetail objects
     * @throws SQLException if a database error occurs
     */
    ArrayList<BookDetail> getBookDetailsByAgent(int agentId, int page, int pageSize) throws SQLException;

    /**
     * Retrieves a list of book details by status with default pagination.
     *
     * @param status the status code
     * @return a list of BookDetail objects
     * @throws SQLException if a database error occurs
     */
    List<BookDetail> getBookDetailByStatus(int status) throws SQLException;

    /**
     * Retrieves a paginated list of book details by status.
     *
     * @param status the status code
     * @param page the page number (1-based)
     * @param pageSize the number of items per page
     * @return a list of BookDetail objects
     * @throws SQLException if a database error occurs
     */
    List<BookDetail> getBookDetailByStatus(int status, int page, int pageSize) throws SQLException;

    /**
     * Counts the number of book details by status.
     *
     * @param status the status code
     * @return the count of matching records
     * @throws SQLException if a database error occurs
     */
    int countByStatus(int status) throws SQLException;

    /**
     * Creates a new book detail record.
     *
     * @param userId the ID of the user
     * @param tourID the ID of the tour
     * @param voucherID the ID of the voucher
     * @param bookDate the booking date
     * @param numberAdult the number of adults
     * @param numberChildren the number of children
     * @param payMethod the payment method
     * @param firstName the first name
     * @param lastName the last name
     * @param phone the phone number
     * @param gmail the email address
     * @param note additional notes
     * @param isBookedForOther whether the booking is for someone else
     * @param totalPrice the total price
     * @param status the status code
     * @return a list containing the created BookDetail object
     * @throws SQLException if a database error occurs
     */
    ArrayList<BookDetail> createBookDetail(int userId, int tourID, int voucherID, Date bookDate, int numberAdult, int numberChildren, String payMethod, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, float totalPrice, int status) throws SQLException;

    /**
     * Retrieves a book detail by its ID.
     *
     * @param bookDetailId the ID of the book detail
     * @return the BookDetail object or null if not found
     * @throws SQLException if a database error occurs
     */
    BookDetail getBookDetailById(int bookDetailId) throws SQLException;

    /**
     * Searches book details by name (across firstName or lastName) for a
     * specific agent.
     *
     * @param agentId the ID of the travel agent
     * @param name the name to search
     * @param page the page number (1-based)
     * @param pageSize the number of items per page
     * @return a list of matching BookDetail objects
     * @throws SQLException if a database error occurs
     */
    List<BookDetail> searchByName(int agentId, String name, int page, int pageSize) throws SQLException;

    /**
     * Counts the number of book details matching the name search for a specific
     * agent.
     *
     * @param agentId the ID of the travel agent
     * @param name the name to search
     * @return the count of matching records
     * @throws SQLException if a database error occurs
     */
    int countByName(int agentId, String name) throws SQLException;

    /**
     * Searches book details by name and status for a specific agent.
     *
     * @param agentId the ID of the travel agent
     * @param name the name to search
     * @param status the status code
     * @param page the page number (1-based)
     * @param pageSize the number of items per page
     * @return a list of matching BookDetail objects
     * @throws SQLException if a database error occurs
     */
    List<BookDetail> searchByNameAndStatus(int agentId, String name, int status, int page, int pageSize) throws SQLException;

    /**
     * Counts the number of book details matching the name and status search for
     * a specific agent.
     *
     * @param agentId the ID of the travel agent
     * @param name the name to search
     * @param status the status code
     * @return the count of matching records
     * @throws SQLException if a database error occurs
     */
    int countByNameAndStatus(int agentId, String name, int status) throws SQLException;

    /**
     * Retrieves a list of book details by agent and status.
     *
     * @param agentId the ID of the travel agent
     * @param status the status code
     * @param page the page number (1-based)
     * @param pageSize the number of items per page
     * @return a list of matching BookDetail objects
     * @throws SQLException if a database error occurs
     */
    List<BookDetail> getBookDetailsByAgentAndStatus(int agentId, int status, int page, int pageSize) throws SQLException;

    /**
     * Counts the number of book details for a specific agent.
     *
     * @param agentId the ID of the travel agent
     * @return the count of matching records
     * @throws SQLException if a database error occurs
     */
    int countByAgent(int agentId) throws SQLException;

    boolean insertBookDetail(int userID, int tourID, Integer voucherID, int numberAdult, int numberChildren, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, double totalPrice, int status, int paymentMethodID, long bookCode) throws SQLException;

    boolean inserBookDetailPayOs(int userID, int tourID, Integer voucherID, int numberAdult, int numberChildren, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, double totalPrice, int status, int paymentMethodID, long bookCode) throws SQLException;

    int getStatusByBookId(int bookId) throws SQLException;

    Map<BookDetail, Tour> getBookingDetailById(int id) throws SQLException;

    void updateStatus(int bookId, int status) throws SQLException;

    boolean updateBookStatusByBookCode(long bookCode, int newStatus) throws SQLException;

    List<BookDetail> getLatest5Bookings(int travelAgentId) throws SQLException;

    Map<BookDetail, String> getCurrentBookingByUser(int userId) throws SQLException;

    Map<BookDetail, String> getFinishedBookingByUser(int userId) throws SQLException;

    List<BookDetail> getBookingToCompletes() throws Exception;

    Map<BookDetail, String[]> getBookDetailByStatus(int status, Date fromDate, Date toDate) throws SQLException;

    BookDetail getBookDetailByBookCode(long bookCode) throws SQLException;

    double getTotalRevenueByStatus(int status) throws SQLException;

    int getTotalBookDetailsWithFilters(Integer agentId, Integer status, String name) throws SQLException;

    ArrayList<BookDetail> getBookDetailsWithFilters(Integer agentId, Integer status, String name) throws SQLException;

    /**
 * Retrieves a list of book details with filters including date range.
 *
 * @param agentId the ID of the travel agent (nullable)
 * @param status the status code (nullable)
 * @param name the name to search (nullable)
 * @param startDate the start date of the booking range
 * @param endDate the end date of the booking range
 * @return a list of matching BookDetail objects
 * @throws SQLException if a database error occurs
 */
ArrayList<BookDetail> getBookDetailsWithFiltersAndDate(Integer agentId, Integer status, String name, Date startDate, Date endDate) throws SQLException;

/**
 * Counts the number of book details with filters including date range.
 *
 * @param agentId the ID of the travel agent (nullable)
 * @param status the status code (nullable)
 * @param name the name to search (nullable)
 * @param startDate the start date of the booking range
 * @param endDate the end date of the booking range
 * @return the count of matching records
 * @throws SQLException if a database error occurs
 */
int getTotalBookDetailsWithFiltersAndDate(Integer agentId, Integer status, String name, Date startDate, Date endDate) throws SQLException;
}
