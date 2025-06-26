/*
 * Click https://netbeans.org/project/licenses/ to change this license
 * Click https://netbeans.org/features/ to edit this template
 */
/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai          First implementation
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Vector;
import model.TravelAgent;
import java.sql.Date;

/**
 * TravelAgentDAO class manages database operations for TravelAgent entities.
 * This class extends DBContext to handle database connections and implements the ITravelAgentDAO interface.
 * It provides CRUD operations and validation checks for the TravelAgent and User tables.
 * All database interactions are performed with proper resource management and error handling.
 * @author Quynh Mai
 */
public class TravelAgentDAO extends DBContext implements ITravelAgentDAO {

    /**
     * Retrieves all TravelAgent records from the database.
     * This method performs a JOIN query between TravelAgent and User tables to fetch all records.
     * @return Vector of TravelAgent objects containing all records
     * @throws SQLException if database access fails
     */
    @Override
    public Vector<TravelAgent> getAllTravelAgent() throws SQLException {
        Vector<TravelAgent> list = new Vector<>(); // Initialize vector to store results
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT t.travelAgentID, t.travelAgentName, t.travelAgentAddress, t.travelAgentGmail, t.hotLine, t.taxCode, "
                + "t.establishmentDate, t.representativeIDCard, t.dateOfIssue, t.businessLicense, t.frontIDCard, t.backIDCard, t.reason,"
                + "u.userID, u.gmail, u.roleID, u.password, u.firstName, u.lastName, u.dob, u.gender, u.address, u.phone, u.create_at, u.update_at, u.status "
                + "FROM TravelAgent t JOIN [User] u ON t.userID = u.userID";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            rs = ptm.executeQuery(); // Execute query
            while (rs.next()) { // Iterate over results
                TravelAgent agent = new TravelAgent(
                        rs.getInt("travelAgentID"),
                        rs.getString("travelAgentName"),
                        rs.getString("travelAgentAddress"),
                        rs.getString("travelAgentGmail"),
                        rs.getString("hotLine"),
                        rs.getString("taxCode"),
                        rs.getDate("establishmentDate"),
                        rs.getString("representativeIDCard"),
                        rs.getDate("dateOfIssue"),
                        rs.getString("frontIDCard"),
                        rs.getString("backIDCard"),
                        rs.getString("businessLicense"),
                        rs.getString("reason"),
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getInt("roleID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status")
                );
                list.add(agent); // Add each agent to the vector
            }
        } catch (SQLException ex) {
            throw new SQLException("Error retrieving all Travel Agents: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return list; // Return the list of agents
    }

    /**
     * Checks if an email exists in the User table.
     * This method performs a count query to verify email existence.
     * @param email The email to check
     * @return true if email exists, false otherwise
     * @throws SQLException if database access fails
     */
    @Override
    public boolean isEmailExists(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM [User] WHERE gmail = ? AND (status = 1 OR status = 2)";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setString(1, email); // Set email parameter
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if count > 0
            }
        } catch (SQLException ex) {
            throw new SQLException("Error checking email existence: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return false; // Return false if no result or error
    }

    /**
     * Checks if a travel agent email exists in the TravelAgent table.
     * This method performs a count query to verify travel agent email existence.
     * @param email The email to check
     * @return true if email exists, false otherwise
     * @throws SQLException if database access fails
     */
    @Override
    public boolean isTravelAgentEmailExists(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM TravelAgent t join [User] u on t.userID = u.userID WHERE travelAgentGmail = ? AND (status = 1 OR status = 2)";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setString(1, email); // Set email parameter
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if count > 0
            }
        } catch (SQLException ex) {
            throw new SQLException("Error checking travel agent email existence: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return false; // Return false if no result or error
    }

    /**
     * Checks if a tax code exists in the TravelAgent table.
     * This method performs a count query to verify tax code existence.
     * @param taxCode The tax code to check
     * @return true if tax code exists, false otherwise
     * @throws SQLException if database access fails
     */
    @Override
    public boolean isTaxCodeExists(String taxCode) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM TravelAgent t join [User] u on t.userID = u.userID WHERE taxCode = ? AND (status = 1 OR status = 2)";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setString(1, taxCode); // Set tax code parameter
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if count > 0
            }
        } catch (SQLException ex) {
            throw new SQLException("Error checking tax code existence: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return false; // Return false if no result or error
    }

    /**
     * Checks if an ID card exists in the TravelAgent table.
     * This method performs a count query to verify ID card existence.
     * @param idCard The ID card number to check
     * @return true if ID card exists, false otherwise
     * @throws SQLException if database access fails
     */
    @Override
    public boolean isIDCardExists(String idCard) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM TravelAgent t join [User] u on t.userID = u.userID WHERE representativeIDCard = ? AND (status = 1 OR status = 2)";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setString(1, idCard); // Set ID card parameter
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if count > 0
            }
        } catch (SQLException ex) {
            throw new SQLException("Error checking ID card existence: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return false; // Return false if no result or error
    }

    /**
     * Inserts a new TravelAgent into the database.
     * This method inserts records into both User and TravelAgent tables with generated userID.
     * @param agent The TravelAgent object to insert
     * @throws SQLException if database insertion fails
     */
    @Override
    public void insertTravelAgent(TravelAgent agent) throws SQLException {
        Connection conn = null;
        PreparedStatement userPtm = null;
        PreparedStatement travelAgentPtm = null;
        ResultSet rs = null;
        int userID = -1;
        String userSql = "INSERT INTO [User] (gmail, roleID, password, firstName, lastName, dob, gender, address, phone, create_at, update_at, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = getConnection(); // Establish database connection
            userPtm = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS); // Prepare with generated keys
            userPtm.setString(1, agent.getGmail());
            userPtm.setInt(2, agent.getRoleID());
            userPtm.setString(3, agent.getPassword());
            userPtm.setString(4, agent.getFirstName());
            userPtm.setString(5, agent.getLastName());
            userPtm.setDate(6, agent.getDob());
            userPtm.setString(7, agent.getGender());
            userPtm.setString(8, agent.getAddress());
            userPtm.setString(9, agent.getPhone());
            userPtm.setDate(10, agent.getCreateDate());
            userPtm.setDate(11, agent.getUpdateDate());
            userPtm.setInt(12, agent.getStatus());

            int affectedRows = userPtm.executeUpdate(); // Execute User insertion
            if (affectedRows > 0) {
                rs = userPtm.getGeneratedKeys(); // Get generated userID
                if (rs.next()) {
                    userID = rs.getInt(1); // Store generated userID
                }
            }

            if (userID != -1) { // Proceed if userID is generated
                String travelAgentSql = "INSERT INTO TravelAgent (travelAgentName, travelAgentAddress, travelAgentGmail, hotLine, taxCode, establishmentDate, representativeIDCard, dateOfIssue, businessLicense, frontIDCard, backIDCard, userID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                travelAgentPtm = conn.prepareStatement(travelAgentSql); // Prepare TravelAgent insertion
                travelAgentPtm.setString(1, agent.getTravelAgentName());
                travelAgentPtm.setString(2, agent.getTravelAgentAddress());
                travelAgentPtm.setString(3, agent.getTravelAgentGmail());
                travelAgentPtm.setString(4, agent.getHotLine());
                travelAgentPtm.setString(5, agent.getTaxCode());
                travelAgentPtm.setDate(6, agent.getEstablishmentDate());
                travelAgentPtm.setString(7, agent.getRepresentativeIDCard());
                travelAgentPtm.setDate(8, agent.getDateOfIssue());
                travelAgentPtm.setString(9, agent.getBusinessLicense());
                travelAgentPtm.setString(10, agent.getFrontIDCard());
                travelAgentPtm.setString(11, agent.getBackIDCard());
                travelAgentPtm.setInt(12, userID);
                travelAgentPtm.executeUpdate(); // Execute TravelAgent insertion
            }
        } catch (SQLException ex) {
            throw new SQLException("Error inserting Travel Agent: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (userPtm != null) userPtm.close(); // Close User PreparedStatement
            if (travelAgentPtm != null) travelAgentPtm.close(); // Close TravelAgent PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
    }

    
    
    @Override
    public Vector<TravelAgent> searchByTravelAgentByStatus(int status) throws SQLException {
        Vector<TravelAgent> list = new Vector<>(); // Initialize vector to store results
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT t.travelAgentID, t.travelAgentName, t.travelAgentAddress, t.travelAgentGmail, t.hotLine, t.taxCode, "
                + "t.establishmentDate, t.representativeIDCard, t.dateOfIssue, t.businessLicense, t.frontIDCard, t.backIDCard,t.reason, "
                + "u.userID, u.gmail, u.roleID, u.password, u.firstName, u.lastName, u.dob, u.gender, u.address, u.phone, u.create_at, u.update_at, u.status "
                + "FROM TravelAgent t JOIN [User] u ON t.userID = u.userID WHERE u.status = ?";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setInt(1, status); // Set partial match parameter
            rs = ptm.executeQuery(); // Execute query
            while (rs.next()) { // Iterate over results
                TravelAgent agent = new TravelAgent(
                        rs.getInt("travelAgentID"),
                        rs.getString("travelAgentName"),
                        rs.getString("travelAgentAddress"),
                        rs.getString("travelAgentGmail"),
                        rs.getString("hotLine"),
                        rs.getString("taxCode"),
                        rs.getDate("establishmentDate"),
                        rs.getString("representativeIDCard"),
                        rs.getDate("dateOfIssue"),
                        rs.getString("frontIDCard"),
                        rs.getString("backIDCard"),
                        rs.getString("businessLicense"),
                        rs.getString("reason"),
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getInt("roleID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status")
                );
                list.add(agent); // Add each agent to the vector
            }
        } catch (SQLException ex) {
            throw new SQLException("Error searching Travel Agent by name: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return list; // Return the list of agents
    }

    /**
     * Searches for a TravelAgent by Gmail.
     * This method returns the first matching record based on the Gmail.
     * @param gmail The Gmail to search for
     * @return TravelAgent object if found, null otherwise
     * @throws SQLException if database access fails
     */
    @Override
    public TravelAgent searchTravelAgentByGmail(String gmail) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT t.travelAgentID, t.travelAgentName, t.travelAgentAddress, t.travelAgentGmail, t.hotLine, t.taxCode, "
                + "t.establishmentDate, t.representativeIDCard, t.dateOfIssue, t.businessLicense, t.frontIDCard, t.backIDCard, t.reason,"
                + "u.userID, u.gmail, u.roleID, u.password, u.firstName, u.lastName, u.dob, u.gender, u.address, u.phone, u.create_at, u.update_at, u.status "
                + "FROM TravelAgent t JOIN [User] u ON t.userID = u.userID WHERE u.gmail = ? and u.status = 1";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setString(1, gmail); // Set Gmail parameter
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) { // Check if a record exists
                return new TravelAgent(
                        rs.getInt("travelAgentID"),
                        rs.getString("travelAgentName"),
                        rs.getString("travelAgentAddress"),
                        rs.getString("travelAgentGmail"),
                        rs.getString("hotLine"),
                        rs.getString("taxCode"),
                        rs.getDate("establishmentDate"),
                        rs.getString("representativeIDCard"),
                        rs.getDate("dateOfIssue"),
                        rs.getString("frontIDCard"),
                        rs.getString("backIDCard"),
                        rs.getString("businessLicense"),
                        rs.getString("reason"),
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getInt("roleID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status")
                );
            }
        } catch (SQLException ex) {
            throw new SQLException("Error searching Travel Agent by Gmail: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return null; // Return null if no record found
    }
    
    
    /**
     * Searches for a TravelAgent by Gmail.
     * This method returns the first matching record based on the Gmail.
     * @param gmail The Gmail to search for
     * @return TravelAgent object if found, null otherwise
     * @throws SQLException if database access fails
     */
    @Override
    public Vector<TravelAgent> searchTravelAgentByGmailForAdmin(String gmail) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        Vector<TravelAgent> list = new Vector<>();
        String sql = "SELECT t.travelAgentID, t.travelAgentName, t.travelAgentAddress, t.travelAgentGmail, t.hotLine, t.taxCode, "
                + "t.establishmentDate, t.representativeIDCard, t.dateOfIssue, t.businessLicense, t.frontIDCard, t.backIDCard, t.reason,"
                + "u.userID, u.gmail, u.roleID, u.password, u.firstName, u.lastName, u.dob, u.gender, u.address, u.phone, u.create_at, u.update_at, u.status "
                + "FROM TravelAgent t JOIN [User] u ON t.userID = u.userID WHERE u.gmail = ?";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setString(1, gmail); // Set Gmail parameter
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) { // Check if a record exists
                TravelAgent t = new TravelAgent(
                        rs.getInt("travelAgentID"),
                        rs.getString("travelAgentName"),
                        rs.getString("travelAgentAddress"),
                        rs.getString("travelAgentGmail"),
                        rs.getString("hotLine"),
                        rs.getString("taxCode"),
                        rs.getDate("establishmentDate"),
                        rs.getString("representativeIDCard"),
                        rs.getDate("dateOfIssue"),
                        rs.getString("frontIDCard"),
                        rs.getString("backIDCard"),
                        rs.getString("businessLicense"),
                        rs.getString("reason"),
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getInt("roleID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status")
                );
                list.add(t);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error searching Travel Agent by Gmail: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return list; 
    }
    
    @Override
    public TravelAgent searchTravelAgentByID(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT t.travelAgentID, t.travelAgentName, t.travelAgentAddress, t.travelAgentGmail, t.hotLine, t.taxCode, "
                + "t.establishmentDate, t.representativeIDCard, t.dateOfIssue, t.businessLicense, t.frontIDCard, t.backIDCard, t.reason,"
                + "u.userID, u.gmail, u.roleID, u.password, u.firstName, u.lastName, u.dob, u.gender, u.address, u.phone, u.create_at, u.update_at, u.status "
                + "FROM TravelAgent t JOIN [User] u ON t.userID = u.userID WHERE t.travelAgentID = ?";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setInt(1, id); // Set Gmail parameter
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) { // Check if a record exists
                TravelAgent t = new TravelAgent(
                        rs.getInt("travelAgentID"),
                        rs.getString("travelAgentName"),
                        rs.getString("travelAgentAddress"),
                        rs.getString("travelAgentGmail"),
                        rs.getString("hotLine"),
                        rs.getString("taxCode"),
                        rs.getDate("establishmentDate"),
                        rs.getString("representativeIDCard"),
                        rs.getDate("dateOfIssue"),
                        rs.getString("frontIDCard"),
                        rs.getString("backIDCard"),
                        rs.getString("businessLicense"),
                        rs.getString("reason"),
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getInt("roleID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status")
                );
                return t;
            }
        } catch (SQLException ex) {
            throw new SQLException("Error searching Travel Agent by Gmail: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (rs != null) rs.close(); // Close ResultSet
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
        return null; 
    }
    @Override
    public void updateRejectionReason(int travelAgentID, String reason) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        String sql = "UPDATE TravelAgent SET reason = ? WHERE travelAgentID = ?";
        try {
            conn = getConnection();
            ptm = conn.prepareStatement(sql);
            ptm.setString(1, reason);
            ptm.setInt(2, travelAgentID);
            ptm.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error updating rejection reason: " + ex.getMessage(), ex);
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
    }
    

    /**
     * Updates an existing TravelAgent in the database.
     * This method updates records in both User and TravelAgent tables.
     * @param agent The TravelAgent object to update
     * @throws SQLException if database update fails
     */
        /**
     * Updates an existing TravelAgent in the database.
     * @param agent The TravelAgent object to update
     * @param updateType The type of update ("company" or "representative")
     * @throws SQLException if database update fails
     */
    @Override
    public void updateTravelAgent(TravelAgent agent, String updateType) throws SQLException {
        Connection conn = null;
        PreparedStatement userPtm = null;
        PreparedStatement travelAgentPtm = null;
        try {
            conn = getConnection();
            if (updateType.equals("company")) {
                // Update TravelAgent table
                String travelAgentSql = "UPDATE TravelAgent SET travelAgentName=?, travelAgentGmail=?, hotLine=?, travelAgentAddress=?, establishmentDate=?, taxCode=?, businessLicense=? WHERE travelAgentID=?";
                travelAgentPtm = conn.prepareStatement(travelAgentSql);
                travelAgentPtm.setString(1, agent.getTravelAgentName());
                travelAgentPtm.setString(2, agent.getTravelAgentGmail());
                travelAgentPtm.setString(3, agent.getHotLine());
                travelAgentPtm.setString(4, agent.getTravelAgentAddress());
                travelAgentPtm.setDate(5, agent.getEstablishmentDate());
                travelAgentPtm.setString(6, agent.getTaxCode());
                travelAgentPtm.setString(7, agent.getBusinessLicense());
                travelAgentPtm.setInt(8, agent.getTravelAgentID());
                travelAgentPtm.executeUpdate();

                // Update User table's update_at
                String userSql = "UPDATE [User] SET update_at=? WHERE userID=?";
                userPtm = conn.prepareStatement(userSql);
                userPtm.setDate(1, Date.valueOf(LocalDate.now()));
                userPtm.setInt(2, agent.getUserID());
                userPtm.executeUpdate();
            } else if (updateType.equals("representative")) {
                // Update User table
                String userSql = "UPDATE [User] SET firstName=?, lastName=?, dob=?, gender=?, address=?, phone=?, update_at=? WHERE userID=?";
                userPtm = conn.prepareStatement(userSql);
                userPtm.setString(1, agent.getFirstName());
                userPtm.setString(2, agent.getLastName());
                userPtm.setDate(3, agent.getDob());
                userPtm.setString(4, agent.getGender());
                userPtm.setString(5, agent.getAddress());
                userPtm.setString(6, agent.getPhone());
                userPtm.setDate(7, Date.valueOf(LocalDate.now()));
                userPtm.setInt(8, agent.getUserID());
                userPtm.executeUpdate();

                // Update TravelAgent table
                String travelAgentSql = "UPDATE TravelAgent SET representativeIDCard=?, dateOfIssue=?, frontIDCard=?, backIDCard=? WHERE travelAgentID=?";
                travelAgentPtm = conn.prepareStatement(travelAgentSql);
                travelAgentPtm.setString(1, agent.getRepresentativeIDCard());
                travelAgentPtm.setDate(2, agent.getDateOfIssue());
                travelAgentPtm.setString(3, agent.getFrontIDCard());
                travelAgentPtm.setString(4, agent.getBackIDCard());
                travelAgentPtm.setInt(5, agent.getTravelAgentID());
                travelAgentPtm.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new SQLException("Error updating Travel Agent: " + ex.getMessage(), ex);
        } finally {
            if (userPtm != null) userPtm.close();
            if (travelAgentPtm != null) travelAgentPtm.close();
            if (conn != null) conn.close();
        }
    }

    public void updatePassword(int userID, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        String sql = "UPDATE [User] SET password=?, update_at=? WHERE userID=?";
        try {
            conn = getConnection();
            ptm = conn.prepareStatement(sql);
            ptm.setString(1, password);
            ptm.setDate(2, Date.valueOf(LocalDate.now()));
            ptm.setInt(3, userID);
            ptm.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error updating password: " + ex.getMessage(), ex);
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
    }
    /**
     * Deletes a TravelAgent from the database.
     * This method checks for linked tours and either deletes or disables the agent.
     * @param travelAgentID The ID of the TravelAgent to delete
     * @throws SQLException if database deletion fails
     */
    @Override
    public void deleteTravelAgent(int travelAgentID) throws SQLException {
        Connection conn = null;
        PreparedStatement findUserPtm = null;
        PreparedStatement checkTourPtm = null;
        PreparedStatement deleteTravelPtm = null;
        PreparedStatement deleteUserPtm = null;
        ResultSet u = null;
        ResultSet t = null;
        String findUserSql = "SELECT userID FROM TravelAgent WHERE travelAgentID = ?";
        String checkTourSql = "SELECT * FROM Tour WHERE travelAgentID = ?";
        String deleteTravelAgentSql = "DELETE FROM TravelAgent WHERE travelAgentID = ?";
        String deleteUserSql = "DELETE FROM [User] WHERE userID = ?";
        try {
            conn = getConnection(); // Establish database connection
            findUserPtm = conn.prepareStatement(findUserSql); // Prepare user ID query
            findUserPtm.setInt(1, travelAgentID);
            u = findUserPtm.executeQuery(); // Execute user ID query
            int userID = -1;
            if (u.next()) {
                userID = u.getInt("userID"); // Retrieve userID
            }

            if (userID != -1) { // Proceed if userID is found
                checkTourPtm = conn.prepareStatement(checkTourSql); // Prepare tour check query
                checkTourPtm.setInt(1, travelAgentID);
                t = checkTourPtm.executeQuery(); // Execute tour check query
                if (t.next()) {
                    changeStatusTravelAgent(userID, 0); // Disable if tours exist
                } else {
                    deleteTravelPtm = conn.prepareStatement(deleteTravelAgentSql); // Prepare TravelAgent deletion
                    deleteTravelPtm.setInt(1, travelAgentID);
                    deleteTravelPtm.executeUpdate(); // Execute TravelAgent deletion

                    deleteUserPtm = conn.prepareStatement(deleteUserSql); // Prepare User deletion
                    deleteUserPtm.setInt(1, userID);
                    deleteUserPtm.executeUpdate(); // Execute User deletion
                }
            }
        } catch (SQLException ex) {
            throw new SQLException("Error deleting Travel Agent: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (u != null) u.close(); // Close user ResultSet
            if (t != null) t.close(); // Close tour ResultSet
            if (findUserPtm != null) findUserPtm.close(); // Close findUser PreparedStatement
            if (checkTourPtm != null) checkTourPtm.close(); // Close checkTour PreparedStatement
            if (deleteTravelPtm != null) deleteTravelPtm.close(); // Close deleteTravel PreparedStatement
            if (deleteUserPtm != null) deleteUserPtm.close(); // Close deleteUser PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
    }

    /**
     * Changes the status of a TravelAgent in the database.
     * This method updates the status in the User table.
     * @param userID The user ID of the TravelAgent
     * @param newStatus The new status to set
     * @throws SQLException if database update fails
     */
    @Override
    public void changeStatusTravelAgent(int userID, int newStatus) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        String sql = "UPDATE [dbo].[User] SET [status] = ? WHERE [userID] = ?";
        try {
            conn = getConnection(); // Establish database connection
            ptm = conn.prepareStatement(sql); // Prepare the SQL statement
            ptm.setInt(1, newStatus); // Set new status
            ptm.setInt(2, userID); // Set user ID
            ptm.executeUpdate(); // Execute update
        } catch (SQLException ex) {
            throw new SQLException("Error changing Travel Agent status: " + ex.getMessage(), ex); // Throw detailed exception
        } finally {
            if (ptm != null) ptm.close(); // Close PreparedStatement
            if (conn != null) conn.close(); // Close Connection
        }
    }
    
    

    /**
     * Main method for testing TravelAgentDAO functionality.
     * This method searches for a TravelAgent by Gmail and prints the result.
     * @param args Command line arguments (not used)
     */
//    public static void main(String[] args) {
//        TravelAgentDAO dao = new TravelAgentDAO(); // Create DAO instance
//        try {
//            TravelAgent b = dao.searchTravelAgentByGmail("mai24a1k23@cvp.vn"); // Search by Gmail
//            dao.insertTravelAgent(b);
//            b.setHotLine("093029928192");
//            dao.updateTravelAgent(b, "company");
//            System.out.println(b); // Print result
//        } catch (SQLException ex) {
//            ex.printStackTrace(); // Print stack trace for debugging
//        }
//    }
    
    public static void main(String[] args) {
    TravelAgentDAO dao = new TravelAgentDAO();
    try {
        dao.changeStatusTravelAgent(1, 0); // Change status for userID 1 to status 2
        System.out.println("Status changed successfully");
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
}