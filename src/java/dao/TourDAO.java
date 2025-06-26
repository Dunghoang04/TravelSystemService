/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai         Refactored with ITourDAO, improved resource management and comments
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.Tour;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.Statement;

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
     * Retrieves the total number of tours in the database.
     *
     * @return The total count of tours
     * @throws SQLException If a database error occurs
     */
    @Override
    public int getTotalTours() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM Tour";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            rs = ps.executeQuery(); // Execute query
            if (rs.next()) { // Process result
                return rs.getInt(1); // Return count
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve total tours: " + ex.getMessage(), ex);
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
        return 0;
    }

    /**
     * Retrieves all tours from the database.
     *
     * @return A Vector containing all Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> getAllTours(int page, int pageSize) throws SQLException {
        Vector<Tour> listTours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate startRange = currentDate.minusDays(10); // 10 days before
        LocalDate endRange = currentDate.plusDays(10);   // 10 days after
        String sql = "SELECT * FROM Tour WHERE startDay > ? AND status = 1 AND startDay BETWEEN ? AND ? ORDER BY tourID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setDate(1, Date.valueOf(currentDate)); // Current date for future check
            ps.setDate(2, Date.valueOf(startRange));  // Start of 10-day range
            ps.setDate(3, Date.valueOf(endRange));    // End of 10-day range
            ps.setInt(4, (page - 1) * pageSize);
            ps.setInt(5, pageSize);
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                Tour tour = createTourFromResultSet(rs); // Create tour object
                listTours.add(tour); // Add tour to result list
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve all tours: " + ex.getMessage(), ex);
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
     * Searches for tours by destination (end place).
     *
     * @param destination The destination to search for (partial match)
     * @return A Vector of matching Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> searchToursByDestination(String destination, int page, int pageSize) throws SQLException {
        Vector<Tour> tours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate startRange = currentDate.minusDays(10); // 10 days before
        LocalDate endRange = currentDate.plusDays(10);   // 10 days after
        String sql = "SELECT * FROM Tour WHERE endPlace LIKE ? AND status = 1 AND startDay > ? AND startDay BETWEEN ? AND ? "
                + "ORDER BY ABS(DATEDIFF(day, startDay, ?)) OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setString(1, "%" + destination + "%"); // Set destination parameter
            ps.setDate(2, Date.valueOf(currentDate)); // Current date for future check
            ps.setDate(3, Date.valueOf(startRange));  // Start of 10-day range
            ps.setDate(4, Date.valueOf(endRange));    // End of 10-day range
            ps.setDate(5, Date.valueOf(currentDate));
            ps.setInt(6, (page - 1) * pageSize);
            ps.setInt(7, pageSize);
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                Tour tour = createTourFromResultSet(rs); // Create tour object
                tours.add(tour); // Add tour to result list
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to search tours by destination: " + ex.getMessage(), ex);
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
     * Searches for tours by tour name.
     *
     * @param searchQuery The query to search for (partial match)
     * @return A Vector of matching Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> searchToursByName(String searchQuery, int page, int pageSize) throws SQLException {
        Vector<Tour> tours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tour WHERE tourName LIKE ? AND status = 1 ORDER BY tourID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setString(1, "%" + searchQuery + "%"); // Set search query parameter
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            rs = ps.executeQuery(); // Execute query
            while (rs.next()) { // Process result set
                Tour tour = createTourFromResultSet(rs); // Create tour object
                tours.add(tour); // Add tour to result list
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to search tours by name: " + ex.getMessage(), ex);
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
public int insertTour(Tour tour) throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "INSERT INTO [dbo].[Tour] ([tourCategoryID], [travelAgentID], [tourName], [numberOfDay], "
            + "[startPlace], [endPlace], [quantity], [image], [tourIntroduce], [tourSchedule], "
            + "[tourInclude], [tourNonInclude], [rate], [status], [startDay], [endDay], "
            + "[adultPrice], [childrenPrice]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
        conn = getConnection(); // Establish database connection
        ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Prepare SQL query with generated keys
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

        // Retrieve the generated tourID
        rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // Return the generated tourID
        } else {
            throw new SQLException("Failed to retrieve generated tour ID.");
        }
    } catch (SQLException ex) {
        throw new SQLException("Failed to insert tour: " + ex.getMessage(), ex);
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
                + "[childrenPrice] = ?, [reason] = ? WHERE tourID = ?";
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
            ps.setString(19, tour.getReason()); // Set children price
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

    

    @Override
    public int getTotalToursByDestination(String destination) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate startRange = currentDate.minusDays(10); // 10 days before
        LocalDate endRange = currentDate.plusDays(10);   // 10 days after
        String sql = "SELECT COUNT(*) FROM Tour WHERE endPlace LIKE ? AND status = 1 AND startDay > ? AND startDay BETWEEN ? AND ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + destination + "%");
            ps.setDate(2, Date.valueOf(currentDate)); // Current date for future check
            ps.setDate(3, Date.valueOf(startRange));  // Start of 10-day range
            ps.setDate(4, Date.valueOf(endRange));    // End of 10-day range
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve total tours by destination: " + ex.getMessage(), ex);
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

    @Override
    public int getTotalTourForSearch() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate startRange = currentDate.minusDays(10); // 10 days before
        LocalDate endRange = currentDate.plusDays(10);   // 10 days after
        String sql = "SELECT COUNT(*) FROM Tour WHERE status = 1 AND startDay > ? AND startDay BETWEEN ? AND ?";
        try {
            conn = getConnection(); // Establish database connection
            ps = conn.prepareStatement(sql); // Prepare SQL query
            ps.setDate(1, Date.valueOf(currentDate)); // Current date for future check
            ps.setDate(2, Date.valueOf(startRange));  // Start of 10-day range
            ps.setDate(3, Date.valueOf(endRange));    // End of 10-day range
            rs = ps.executeQuery(); // Execute query
            if (rs.next()) { // Process result
                return rs.getInt(1); // Return count
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve total tours: " + ex.getMessage(), ex);
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
        return 0;
    }

    @Override
    public int getTotalToursWithFilters(String budget, String departure, String destination,
            String departureDate, String tourCategory) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = departureDate != null && !departureDate.isEmpty() ? LocalDate.parse(departureDate) : currentDate;
        LocalDate startRange = selectedDate.minusDays(10); // 10 days before
        LocalDate endRange = selectedDate.plusDays(10);   // 10 days after

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
     * Retrieves all tours for a specific travel agent by status.
     *
     * @param travelAgentID The ID of the travel agent
     * @param status The status of the tours to retrieve
     * @return A Vector containing all Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> searchTourByStatusAndAgent(int travelAgentID, int status) throws SQLException {
        Vector<Tour> listTours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tour WHERE travelAgentID = ? AND status = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, travelAgentID);
            ps.setInt(2, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                Tour tour = createTourFromResultSet(rs);
                listTours.add(tour);
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve tours by status and agent: " + ex.getMessage(), ex);
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
        return listTours;
    }

    /**
     * Retrieves all tours for a specific travel agent.
     *
     * @param travelAgentID The ID of the travel agent
     * @return A Vector containing all Tour objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Tour> getAllToursByAgent(int travelAgentID) throws SQLException {
        Vector<Tour> listTours = new Vector<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tour WHERE travelAgentID = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, travelAgentID);
            rs = ps.executeQuery();
            while (rs.next()) {
                Tour tour = createTourFromResultSet(rs);
                listTours.add(tour);
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve all tours by agent: " + ex.getMessage(), ex);
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
        return listTours;
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
            Vector<Tour> tours = dao.searchToursByDestination("", 1, 10);
            Vector<Tour> tours2 = dao.getToursWithFilters("", "", "", "2025-07-03", "", 2, 5);
            Tour t = dao.searchTourByID(5);
            System.out.println(t);
        } catch (SQLException ex) {
            ex.printStackTrace(); // Print stack trace for debugging
        }
    }

}

