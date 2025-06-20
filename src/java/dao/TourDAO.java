/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Nhat Anh          Initial implementation
 * 2025-06-14  1.1        Quynh Mai         Refactored with ITourDAO, improved resource management and comments
 * 2025-06-19  1.2        [Your Name]       Added filter for status=1, future startDate, and 10-day range around departureDate
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;
import model.Tour;
import java.sql.Date;

/**
 * Data Access Object for managing Tour entities. Extends DBContext for database
 * connectivity and implements ITourDAO. Provides CRUD operations, filtering,
 * and status management for the Tour table. Ensures proper resource management
 * and detailed error handling.
 *
 * @author Quynh Mai
 */
public class TourDAO extends DBContext implements ITourDAO {

    /**
     * Retrieves distinct start places from the Tour table.
     *
     * @return A Vector of unique start place strings
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<String> getUniqueStartPlaces() throws SQLException {
        Vector<String> startPlaces = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT startPlace FROM Tour";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                startPlaces.add(rs.getString("startPlace")); // Add start place
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve start places: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) {
                rs.close(); // Close ResultSet
            }
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
        return startPlaces;
    }

    /**
     * Retrieves distinct end places from the Tour table.
     *
     * @return A Vector of unique end place strings
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<String> getUniqueEndPlaces() throws SQLException {
        Vector<String> endPlaces = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT endPlace FROM Tour";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                endPlaces.add(rs.getString("endPlace")); // Add end place
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve end places: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) {
                rs.close(); // Close ResultSet
            }
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
        return endPlaces;
    }
    
    /**
     * Retrieves all tours from the database.
     *
     * @return A Vector containing all Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> getAllTours() throws SQLException {
        Vector<Tour> listTours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tour";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                Tour tour = createTourFromResultSet(rs); // Create tour object
                listTours.add(tour); // Add tour to result list
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve all tours: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ps != null) ps.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return listTours;
    }
    
    
    /**
     * Retrieves all tours from the database.
     *
     * @return A Vector containing all Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> searchTourByStatus(int status) throws SQLException {
        Vector<Tour> listTours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tour where status = ?";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setInt(1, status);
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                Tour tour = createTourFromResultSet(rs); // Create tour object
                listTours.add(tour); // Add tour to result list
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve all tours: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ps != null) ps.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return listTours;
    }




    /**
     * Retrieves tours based on multiple filter criteria.
     *
     * @param budget The budget range (e.g., "under5", "5-10")
     * @param departure The start place
     * @param destination The end place
     * @param departureDate The departure date
     * @param tourCategory The tour category ID
     * @return A Vector of filtered Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> getToursWithFilters(String budget, String departure, String destination,
            String departureDate, String tourCategory, int page, int pageSize) throws SQLException {
        Vector<Tour> tours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = departureDate != null && !departureDate.isEmpty() ? LocalDate.parse(departureDate) : currentDate;
        LocalDate startRange = selectedDate.minusDays(10); // 5 days before
        LocalDate endRange = selectedDate.plusDays(10);   // 5 days after

        StringBuilder sql = new StringBuilder("SELECT * FROM Tour WHERE status = 1 AND startDay > ? AND startDay BETWEEN ? AND ?");
        if (budget != null && !budget.isEmpty()) {
            sql.append(" AND adultPrice ").append(getBudgetCondition(budget)); // Add budget filter
        }
        if (departure != null && !departure.isEmpty()) {
            sql.append(" AND startPlace = ?"); // Add departure filter
        }
        if (destination != null && !destination.isEmpty()) {
            sql.append(" AND endPlace = ?"); // Add destination filter
        }
        if (tourCategory != null && !tourCategory.isEmpty()) {
            sql.append(" AND tourCategoryID = ?"); // Add category filter
        }
        sql.append(" ORDER BY ABS(DATEDIFF(day, startDay, ?))");
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql.toString()); // Prepare SQL query
            int paramIndex = 1;
            ps.setDate(paramIndex++, Date.valueOf(currentDate)); // Current date for future check
            ps.setDate(paramIndex++, Date.valueOf(startRange));  // Start of 10-day range
            ps.setDate(paramIndex++, Date.valueOf(endRange));    // End of 10-day range
            if (departure != null && !departure.isEmpty()) {
                ps.setString(paramIndex++, departure); // Set departure parameter
            }
            if (destination != null && !destination.isEmpty()) {
                ps.setString(paramIndex++, destination); // Set destination parameter
            }
            if (tourCategory != null && !tourCategory.isEmpty()) {
                ps.setString(paramIndex++, tourCategory); // Set category parameter
            }
            ps.setDate(paramIndex++, Date.valueOf(selectedDate)); // Date for sorting by closeness
            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex++, pageSize);
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                Tour tour = createTourFromResultSet(rs); // Create tour object
                tours.add(tour); // Add tour to result list
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to filter tours: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) {
                rs.close(); // Close ResultSet
            }
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
        return tours;
    }

    /**
     * Maps a ResultSet row to a Tour object.
     *
     * @param rs The ResultSet containing tour data
     * @return A Tour object
     * @throws SQLException If a database error occurs
     */
    private Tour createTourFromResultSet(ResultSet rs) throws SQLException {
        return new Tour(
                rs.getInt("tourID"),
                rs.getInt("tourCategoryID"),
                rs.getInt("travelAgentID"),
                rs.getString("tourName"),
                rs.getInt("numberOfDay"),
                rs.getString("startPlace"),
                rs.getString("endPlace"),
                rs.getInt("quantity"),
                rs.getString("image"),
                rs.getString("tourIntroduce"),
                rs.getString("tourSchedule"),
                rs.getString("tourInclude"),
                rs.getString("tourNonInclude"),
                rs.getFloat("rate"),
                rs.getInt("status"),
                rs.getDate("startDay"),
                rs.getDate("endDay"),
                rs.getDouble("adultPrice"),
                rs.getDouble("childrenPrice"),
                rs.getString("reason")
        );
    }

    /**
     * Generates SQL condition for budget filter.
     *
     * @param budget The budget range (e.g., "under5", "5-10")
     * @return The SQL condition string
     */
    private String getBudgetCondition(String budget) {
        switch (budget) {
            case "under5":
                return "< 5000000";
            case "5-10":
                return "BETWEEN 5000000 AND 10000000";
            case "10-20":
                return "BETWEEN 10000000 AND 20000000";
            case "over20":
                return "> 20000000";
            default:
                return "";
        }
    }

    /**
     * Updates the quantity of a tour.
     *
     * @param tourID The ID of the tour
     * @param quantity The quantity to subtract
     * @throws SQLException If a database error occurs
     */
    @Override
    public void updateQuantity(int tourID, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE Tour SET quantity = quantity - ? WHERE tourID = ?";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setInt(1, quantity); // Set quantity parameter
            ps.setInt(2, tourID); // Set tour ID parameter
            ps.executeUpdate(); // Execute update
        } catch (SQLException ex) {
            throw new SQLException("Failed to update tour quantity: " + ex.getMessage(), ex);
        } finally {
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
    }

    

    /**
     * Retrieves the top 3 newest tours.
     *
     * @return A Vector of the top 3 newest Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> getTopNewTour() throws SQLException {
        Vector<Tour> listTours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT TOP 3 * FROM Tour WHERE status = 1 AND startDay > ? ORDER BY tourID DESC";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                Tour tour = createTourFromResultSet(rs); // Create tour object
                listTours.add(tour); // Add tour to result list
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve top new tours: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) {
                rs.close(); // Close ResultSet
            }
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
        return listTours;
    }

    /**
     * Inserts a new tour into the database.
     *
     * @param tour The Tour object to insert
     * @throws SQLException If a database error occurs
     */
    @Override
    public void insertTour(Tour tour) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO [dbo].[Tour] ([tourCategoryID], [travelAgentID], [tourName], [numberOfDay], "
                + "[startPlace], [endPlace], [quantity], [image], [tourIntroduce], [tourSchedule], "
                + "[tourInclude], [tourNonInclude], [rate], [status], [startDay], [endDay], "
                + "[adultPrice], [childrenPrice]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setInt(1, tour.getTourCategoryID()); // Set category ID
            ps.setInt(2, tour.getTravelAgentID()); // Set travel agent ID
            ps.setString(3, tour.getTourName()); // Set tour name
            ps.setInt(4, tour.getNumberOfDay()); // Set number of days
            ps.setString(5, tour.getStartPlace()); // Set start place
            ps.setString(6, tour.getEndPlace()); // Set end place
            ps.setInt(7, tour.getQuantity()); // Set quantity
            ps.setString(8, tour.getImage()); // Set image
            ps.setString(9, tour.getTourIntroduce()); // Set tour introduction
            ps.setString(10, tour.getTourSchedule()); // Set tour schedule
            ps.setString(11, tour.getTourInclude()); // Set included items
            ps.setString(12, tour.getTourNonInclude()); // Set non-included items
            ps.setFloat(13, tour.getRate()); // Set rate
            ps.setInt(14, tour.getStatus()); // Set status
            ps.setDate(15, tour.getStartDay()); // Set start day
            ps.setDate(16, tour.getEndDay()); // Set end day
            ps.setDouble(17, tour.getAdultPrice()); // Set adult price
            ps.setDouble(18, tour.getChildrenPrice()); // Set children price
            ps.executeUpdate(); // Execute insert
        } catch (SQLException ex) {
            throw new SQLException("Failed to insert tour: " + ex.getMessage(), ex);
        } finally {
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
    }

    /**
     * Searches for a tour by its ID.
     *
     * @param tourId The ID of the tour to search for
     * @return The Tour object if found, null otherwise
     * @throws SQLException If a database error occurs
     */
    @Override
    public Tour searchTourByID(int tourId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tour WHERE tourID = ?";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setInt(1, tourId); // Set tour ID
            rs = ps.executeQuery(); // Execute query
            if (rs.next()) { // Process result
                return createTourFromResultSet(rs); // Create tour object
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to search tour by ID: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) {
                rs.close(); // Close ResultSet
            }
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
        return null;
    }

    /**
     * Updates an existing tour in the database.
     *
     * @param tour The Tour object with updated details
     * @throws SQLException If a database error occurs
     */
    @Override
    public void updateTour(Tour tour) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE [dbo].[Tour] SET [tourCategoryID] = ?, [travelAgentID] = ?, [tourName] = ?, "
                + "[numberOfDay] = ?, [startPlace] = ?, [endPlace] = ?, [quantity] = ?, [image] = ?, "
                + "[tourIntroduce] = ?, [tourSchedule] = ?, [tourInclude] = ?, [tourNonInclude] = ?, "
                + "[rate] = ?, [status] = ?, [startDay] = ?, [endDay] = ?, [adultPrice] = ?, "
                + "[childrenPrice] = ? , [reason] = ? WHERE tourID = ?";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setInt(1, tour.getTourCategoryID()); // Set category ID
            ps.setInt(2, tour.getTravelAgentID()); // Set travel agent ID
            ps.setString(3, tour.getTourName()); // Set tour name
            ps.setInt(4, tour.getNumberOfDay()); // Set number of days
            ps.setString(5, tour.getStartPlace()); // Set start place
            ps.setString(6, tour.getEndPlace()); // Set end place
            ps.setInt(7, tour.getQuantity()); // Set quantity
            ps.setString(8, tour.getImage()); // Set image
            ps.setString(9, tour.getTourIntroduce()); // Set tour introduction
            ps.setString(10, tour.getTourSchedule()); // Set tour schedule
            ps.setString(11, tour.getTourInclude()); // Set included items
            ps.setString(12, tour.getTourNonInclude()); // Set non-included items
            ps.setFloat(13, tour.getRate()); // Set rate
            ps.setInt(14, tour.getStatus()); // Set status
            ps.setDate(15, tour.getStartDay()); // Set start day
            ps.setDate(16, tour.getEndDay()); // Set end day
            ps.setDouble(17, tour.getAdultPrice()); // Set adult price
            ps.setDouble(18, tour.getChildrenPrice()); // Set children price
            ps.setString(19, tour.getReason());
            ps.setInt(20, tour.getTourID()); // Set tour ID
            ps.executeUpdate(); // Execute update
        } catch (SQLException ex) {
            throw new SQLException("Failed to update tour: " + ex.getMessage(), ex);
        } finally {
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
    }

    /**
     * Changes the status of a tour.
     *
     * @param tourId The ID of the tour
     * @param newStatus The new status value (e.g., 0 for inactive, 1 for
     * active)
     * @throws SQLException If a database error occurs
     */
    @Override
    public void changeStatusTour(int tourId, int newStatus) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE [dbo].[Tour] SET [status] = ? WHERE tourID = ?";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setInt(1, newStatus); // Set new status
            ps.setInt(2, tourId); // Set tour ID
            ps.executeUpdate(); // Execute update
        } catch (SQLException ex) {
            throw new SQLException("Failed to change tour status: " + ex.getMessage(), ex);
        } finally {
            if (ps != null) {
                ps.close(); // Close PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
    }

    /**
     * Deletes a tour if no tour sessions are associated with it. If associated
     * sessions exist, the tour is deactivated instead.
     *
     * @param tourId The ID of the tour to delete
     * @return Number of affected rows (0 if deactivated due to linked sessions)
     * @throws SQLException If a database error occurs
     */
    @Override
    public int deleteTour(int tourId) throws SQLException {
        Connection conn = null;
        PreparedStatement checkPs = null;
        PreparedStatement deletePs = null;
        ResultSet rs = null;
        int affectedRows = 0;
        String checkSql = "SELECT * FROM Tour_Session WHERE tourID = ?";
        String deleteSql = "DELETE FROM [dbo].[Tour] WHERE tourID = ?";
        try {
            conn = getConnection(); // Establish database connection
            checkPs = conn.prepareStatement(checkSql); // Prepare check query
            checkPs.setInt(1, tourId); // Set tour ID
            rs = checkPs.executeQuery(); // Execute check query
            if (rs.next()) { // If linked sessions exist
                changeStatusTour(tourId, 0); // Deactivate tour
                return affectedRows;
            }
            deletePs = conn.prepareStatement(deleteSql); // Prepare delete query
            deletePs.setInt(1, tourId); // Set tour ID
            affectedRows = deletePs.executeUpdate(); // Execute delete
        } catch (SQLException ex) {
            throw new SQLException("Failed to delete tour: " + ex.getMessage(), ex);
        } finally {
            if (rs != null) {
                rs.close(); // Close ResultSet
            }
            if (checkPs != null) {
                checkPs.close(); // Close check PreparedStatement
            }
            if (deletePs != null) {
                deletePs.close(); // Close delete PreparedStatement
            }
            if (conn != null) {
                conn.close(); // Close Connection
            }
        }
        return affectedRows;
    }

    
    @Override
    public int getTotalToursWithFilters(String budget, String departure, String destination,
            String departureDate, String tourCategory) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = departureDate != null && !departureDate.isEmpty() ? LocalDate.parse(departureDate) : currentDate;
        LocalDate startRange = selectedDate.minusDays(10); // 5 days before
        LocalDate endRange = selectedDate.plusDays(10);   // 5 days after

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Tour WHERE status = 1 AND startDay > ? AND startDay BETWEEN ? AND ?");
        if (budget != null && !budget.isEmpty()) {
            sql.append(" AND adultPrice ").append(getBudgetCondition(budget));
        }
        if (departure != null && !departure.isEmpty()) {
            sql.append(" AND startPlace = ?");
        }
        if (destination != null && !destination.isEmpty()) {
            sql.append(" AND endPlace = ?");
        }
        if (tourCategory != null && !tourCategory.isEmpty()) {
            sql.append(" AND tourCategoryID = ?");
        }
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql.toString());
            int paramIndex = 1;
            ps.setDate(paramIndex++, Date.valueOf(currentDate)); // Current date for future check
            ps.setDate(paramIndex++, Date.valueOf(startRange));  // Start of 10-day range
            ps.setDate(paramIndex++, Date.valueOf(endRange));    // End of 10-day range
            if (departure != null && !departure.isEmpty()) {
                ps.setString(paramIndex++, departure);
            }
            if (destination != null && !destination.isEmpty()) {
                ps.setString(paramIndex++, destination);
            }
            if (tourCategory != null && !tourCategory.isEmpty()) {
                ps.setString(paramIndex++, tourCategory);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve total filtered tours: " + ex.getMessage(), ex);
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
        return 0;
    }

    /**
     * Main method for testing TourDAO functionality. Retrieves and prints
     * filtered tours.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        TourDAO dao = new TourDAO();
        try {
            Vector<Tour> tours = dao.getToursWithFilters("", "", "", "2025-06-20", "", 1, 5);
            for (Tour tour : tours) {
                System.out.println(tour); // Print tour details
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Print stack trace for debugging
        }
    }

    

}