/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên      First implementation
 */
/**
 * Implements data access operations for User entities.<br>
 * Handles CRUD operations (create, read, update) and login validation for
 * users.<br>
 * <p>
 * Bugs: Potential SQL injection risk if user input is used directly in SQL
 * queries; no validation for password strength.</p>
 *
 * @author Hà Thị Duyên
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;
import model.User;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ad
 */
public class UserDAO extends DBContext implements IUserDAO {

    /**
     * Retrieves all users from the database based on the provided SQL
     * query.<br>
     *
     * @param sql The SQL query to execute
     * @return A Vector containing all User objects retrieved
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Executes the given SQL query to fetch all users.
     * Creates a Vector to store User objects and populates it from the ResultSet.
     */
    @Override
    public Vector<User> getAllUsers(String sql) throws SQLException {
        Vector<User> listUser = new Vector<>();
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            rs = ptm.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getDate(10), rs.getDate(11), rs.getInt(12), rs.getInt(13));
                listUser.add(u);
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listUser;
    }

    /**
     * Inserts a new user into the database.<br>
     *
     * @param u The User object to insert
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Inserts a new user record into the User table.
     * Uses current date for create_at and update_at fields.
     */
    @Override
    public void insertUser(User u) throws SQLException {

        String sql = "INSERT INTO [dbo].[User]\n"
                + "           ([gmail]\n"
                + "           ,[password]\n"
                + "           ,[firstName]\n"
                + "           ,[lastName]\n"
                + "           ,[dob]\n"
                + "           ,[gender]\n"
                + "           ,[address]\n"
                + "           ,[phone]\n"
                + "           ,[create_at]\n"
                + "           ,[update_at]\n"
                + "           ,[status]\n"
                + "           ,[roleID])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,GETDATE(), GETDATE(),?,?)";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);

            ptm.setString(1, u.getGmail().toLowerCase());
            ptm.setString(2, u.getPassword());
            ptm.setString(3, u.getFirstName().trim());
            ptm.setString(4, u.getLastName().trim());
            ptm.setDate(5, u.getDob());
            ptm.setString(6, u.getGender());
            ptm.setString(7, u.getAddress().trim());
            ptm.setString(8, u.getPhone().trim());
            ptm.setInt(9, u.getStatus());
            ptm.setInt(10, u.getRoleID());
            ptm.executeUpdate();
        } finally {
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates an existing user in the database.<br>
     *
     * @param u The User object to update
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Updates an existing user record in the User table.
     * Sets update_at to the current date and matches by userID.
     */
    @Override
    public void updateUser(User u) throws SQLException {
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [gmail] = ?\n"
                + "      ,[password] = ?\n"
                + "      ,[firstName] = ?\n"
                + "      ,[lastName] = ?\n"
                + "      ,[dob] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[phone] = ?\n"
                + "      ,[update_at] = GETDATE()\n"
                + "      ,[status] = ?\n"
                + "      ,[roleID] = ?\n"
                + " WHERE userID=?";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);

            ptm.setString(1, u.getGmail().toLowerCase());
            ptm.setString(2, u.getPassword());
            ptm.setString(3, u.getFirstName().trim());
            ptm.setString(4, u.getLastName().trim());
            ptm.setDate(5, u.getDob());
            ptm.setString(6, u.getGender());
            ptm.setString(7, u.getAddress().trim());
            ptm.setString(8, u.getPhone().trim());
            ptm.setInt(9, u.getStatus());
            ptm.setInt(10, u.getRoleID());
            ptm.setInt(11, u.getUserID());
            ptm.executeUpdate();
        } finally {
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks if a gmail address is already registered.<br>
     *
     * @param gmail The email address to check
     * @return true if the gmail is registered, false otherwise
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Queries the database to check if the given gmail exists.
     * Returns true if a matching record is found.
     */
    @Override
    public boolean isGmailRegister(String gmail) throws SQLException {
        String sql = "SELECT 1 FROM [User] WHERE gmail = ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setString(1, gmail.toLowerCase());
            rs = ptm.executeQuery();
            return rs.next(); // nếu dòng nào khớp => gmail đã tồn tại 
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Validates user login credentials.<br>
     *
     * @param gmail The user's email address
     * @param password The user's password
     * @return The User object if login is successful, null otherwise
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Checks login credentials against the database.
     * Returns a User object if gmail, password, and status match.
     */
    @Override
    public User checkLogin(String gmail, String password) throws SQLException {
        String sql = "SELECT * FROM [User] WHERE gmail = ? AND password=? AND status = 1";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setString(1, gmail.toLowerCase());
            ptm.setString(2, password);
            rs = ptm.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("userID"), rs.getString("gmail"), rs.getString("password"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getDate("dob"),
                        rs.getString("gender"), rs.getString("address"), rs.getString("phone"),
                        rs.getDate("create_at"), rs.getDate("update_at"), rs.getInt("status"),
                        rs.getInt("roleID"));
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * Updates the password for a user.<br>
     *
     * @param gmail The user's email address
     * @param newPassword The new password to set
     * @throws SQLException If a database access error occurs
     */
    // Block comment to describe the method
    /* 
     * Updates the password for the user with the given gmail.
     * Sets update_at to the current date.
     */
    @Override
    public void updatePassword(String gmail, String newPassword) throws SQLException {
        String sql = "UPDATE [User] SET password = ?, update_at = GETDATE() WHERE gmail = ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setString(1, newPassword);
            ptm.setString(2, gmail.toLowerCase());
            ptm.executeUpdate();
        } finally {
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Counts the total number of users in the database.<br>
     *
     * @return The number of users
     * @throws SQLException If a database access error occurs
     */
    @Override
    public int countUser() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM [User]";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            rs = ptm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            ex.getSQLState();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
        return count;
    }

    /**
     * Counts the number of users by role ID.<br>
     *
     * @param roleId The role ID to filter by
     * @return The number of users with the specified role ID
     * @throws SQLException If a database access error occurs
     */
    @Override
    public int countUserByRoleID(int roleId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM [User] WHERE roleID = ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setInt(1, roleId);
            rs = ptm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            ex.getSQLState();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
        return count;
    }

    /**
     * Changes the status of a user in the database.<br>
     *
     * @param userID The ID of the user to update
     * @param newStatus The new status to set
     * @throws SQLException If a database access error occurs
     */
    @Override
    public void changeStatus(int userID, int newStatus) throws SQLException {
        String sql = "UPDATE [User] SET status = ?, update_at = GETDATE() WHERE userID = ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setInt(1, newStatus);
            ptm.setInt(2, userID);
            int affectedRows = ptm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No user found with userID: " + userID);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error changing user status: " + ex.getMessage(), ex);
        } finally {
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Retrieves a user by their userID from the database.
     *
     * @param userID The ID of the user to retrieve
     * @return The User object if found, null otherwise
     * @throws SQLException If a database access error occurs
     */
    @Override
    public User getUserByID(int userID) throws SQLException {
        String sql = "SELECT * FROM [User] WHERE userID = ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setInt(1, userID);
            rs = ptm.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status"),
                        rs.getInt("roleID")
                );
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ptm != null) {
                try {
                    ptm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
        return null;
    }
    
    public User getUserById(int userId) {
        String sql = "	Select * from [User] where userID = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            User u = new User();
            while (rs.next()) {

                u.setUserID(rs.getInt("userID"));
                u.setGmail(rs.getString("gmail"));
                u.setPassword(rs.getString("password"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setDob(rs.getDate("dob"));
                u.setGender(rs.getString("gender"));
                u.setAddress(rs.getString("address"));
                u.setPhone(rs.getString("phone"));
                u.setCreateDate(rs.getDate("create_at"));
                u.setUpdateDate(rs.getDate("update_at"));
                u.setStatus(rs.getInt("status"));
                u.setRoleID(rs.getInt("roleID"));
                return u;
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (stmt != null)try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        return null;
    }

    /**
     * Main method for testing the getAllUsers method.<br>
     * Executes a sample query to retrieve and print all users.
     *
     * @param args Command-line arguments (not used)
     */
    // Block comment to describe the method
    /* 
     * Tests the getAllUsers method with a default SQL query.
     * Prints each User object to the console or handles SQLException.
     */
//    public static void main(String[] args) {
//        String sql = "SELECT COUNT(*) FROM [User] WHERE roleID = 4";
//        UserDAO udao = new UserDAO();
//        System.out.println(udao.countUserByRoleID(4));
//    }
//    public static void main(String[] args) {
//        int userID = 7;         // ID của user bạn muốn thay đổi trạng thái
//        int newStatus = 1;      // Trạng thái mới: 0 (vô hiệu), 1 (kích hoạt), v.v.
//
//        UserDAO udao = new UserDAO();
//        try {
//            udao.changeStatus(userID, newStatus);
//            System.out.println("User status updated successfully.");
//        } catch (SQLException e) {
//            System.err.println("Failed to update user status: " + e.getMessage());
//        }
//    }
    /**
     * Main method for testing the insertUser method.<br>
     * Creates a sample User object and attempts to insert it into the database.
     *
     * @param args Command-line arguments (not used)
     */
    /* 
 * Tests the insertUser method by creating a new User object.
 * Prints success message or handles SQLException.
     */
     public static void main(String[] args) {
        UserDAO udao = new UserDAO();
        try {
            // Create a sample admin User object
            User adminUser = new User(
                    0, // userID (auto-generated by database, set to 0)
                    "admin2025@example.com", // gmail
                    "AdminPass2025!", // password
                    "Admin", // firstName
                    "Manager", // lastName
                    Date.valueOf("1985-05-15"), // dob
                    "Male", // gender
                    "789 Admin Road", // address
                    "0912345678", // phone
                    null, // create_at (set by database)
                    null, // update_at (set by database)
                    1, // status (1 for active)
                    1 // roleID (assuming 1 is for admin role)
            );

            // Insert the admin user
            udao.insertUser(adminUser);
            System.out.println("Admin user inserted successfully at " + new java.util.Date());

        } catch (SQLException e) {
            System.err.println("Failed to insert admin user: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

