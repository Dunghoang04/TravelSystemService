package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Vector;
import model.Staff;
import model.User;

/**
 * Data Access Object (DAO) for Staff operations, implementing {@link IStaffDAO}.
 * Manages CRUD operations for staff data, interacting with Staff and User tables.
 * Extends {@link DBContext} for database connection management.
 *
 * @author Nhat Anh
 */
public class StaffDAO extends DBContext implements IStaffDAO {

    /**
     * Retrieves all staff records, joining Staff, User, and Role tables.
     *
     * @return A Vector of Staff objects
     * @throws SQLException If a database error occurs
     */
    @Override
    public Vector<Staff> getAllStaff() throws SQLException {
        Vector<Staff> listStaff = new Vector<>();
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT \n"
                + "    s.staffID,\n"
                + "    s.userID,\n"
                + "    s.employeeCode,\n"
                + "    s.hireDate,\n"
                + "    s.workStatus,\n"
                + "    u.gmail,\n"
                + "    u.roleID,\n"
                + "    u.password,\n"
                + "    u.firstName,\n"
                + "    u.lastName,\n"
                + "    u.dob,\n"
                + "    u.gender,\n"
                + "    u.address,\n"
                + "    u.phone,\n"
                + "    u.create_at,\n"
                + "    u.update_at,\n"
                + "    u.status\n"
                + "FROM Staff s\n"
                + "INNER JOIN [User] u ON s.userID = u.userID\n"
                + "INNER JOIN Role r ON u.roleID = r.roleID;";
        try {
            connection = getConnection(); // Obtain database connection
            ptm = connection.prepareStatement(sql); // Prepare SQL statement
            rs = ptm.executeQuery(); // Execute query
            while (rs.next()) {
                // Create Staff object from result set and add to list
                Staff s = new Staff(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getDate(4), rs.getString(5), rs.getString(6),
                        rs.getInt(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getDate(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getDate(15), rs.getDate(16), rs.getInt(17));
                listStaff.add(s);
            }
        } finally {
            // Close resources in reverse order of creation
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
        return listStaff;
    }

    /**
     * Retrieves a staff record by user ID, joining Staff and User tables.
     *
     * @param userID The ID of the user associated with the staff
     * @return The Staff object, or null if not found
     * @throws SQLException If a database error occurs
     */
    @Override
    public Staff getStaffByUserID(int userID) throws SQLException {
        Connection conn = getConnection(); // Obtain database connection
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String sql = "SELECT s.*, u.* FROM Staff s INNER JOIN [User] u ON s.userID = u.userID WHERE s.userID = ?";
        try {
            ptm = conn.prepareStatement(sql); // Prepare SQL statement
            ptm.setInt(1, userID); // Set userID parameter
            rs = ptm.executeQuery(); // Execute query
            if (rs.next()) {
                // Create and return Staff object from result set
                return new Staff(
                        rs.getInt("staffID"), rs.getInt("userID"), rs.getString("employeeCode"),
                        rs.getDate("hireDate"), rs.getString("workStatus"), rs.getString("gmail"),
                        rs.getInt("roleID"), rs.getString("password"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getDate("dob"), rs.getString("gender"),
                        rs.getString("address"), rs.getString("phone"), rs.getDate("create_at"),
                        rs.getDate("update_at"), rs.getInt("status")
                );
            }
        } finally {
            // Close resources
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    /**
     * Inserts a new staff record, creating entries in User and Staff tables.
     * Uses a transaction to ensure data consistency.
     *
     * @param s The Staff object to insert
     * @throws SQLException If a database error occurs or userID generation fails
     */
    @Override
    public void insertStaff(Staff s) throws SQLException {
        Connection connection = null;
        PreparedStatement ptmUser = null;
        PreparedStatement ptmStaff = null;
        ResultSet rs = null;
        try {
            connection = getConnection(); // Obtain database connection
            connection.setAutoCommit(false); // Start transaction

            // Validate workStatus to ensure valid input
            String validWorkStatus = s.getWorkStatus() != null && s.getWorkStatus().trim().equals("Đang làm việc") ? "Đang làm việc" : "Đang làm việc";
            // SQL to insert into User table
            String sqlUser = "INSERT INTO [User] (gmail, password, firstName, lastName, dob, gender, address, phone, create_at, update_at, status, roleID) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ptmUser = connection.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);
            // Set User parameters
            ptmUser.setString(1, s.getGmail());
            ptmUser.setString(2, s.getPassword());
            ptmUser.setString(3, s.getFirstName());
            ptmUser.setString(4, s.getLastName());
            ptmUser.setDate(5, s.getDob());
            ptmUser.setString(6, s.getGender());
            ptmUser.setString(7, s.getAddress());
            ptmUser.setString(8, s.getPhone());
            ptmUser.setDate(9, s.getCreateDate());
            ptmUser.setDate(10, s.getUpdateDate());
            ptmUser.setInt(11, s.getStatus());
            ptmUser.setInt(12, s.getRoleID());
            ptmUser.executeUpdate(); // Execute User insert

            // Retrieve generated userID
            rs = ptmUser.getGeneratedKeys();
            int userID = -1;
            if (rs.next()) {
                userID = rs.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated userID.");
            }

            // SQL to insert into Staff table
            String sqlStaff = "INSERT INTO [dbo].[Staff] ([userID], [employeeCode], [hireDate], [workStatus]) "
                    + "VALUES (?, ?, ?, ?)";
            ptmStaff = connection.prepareStatement(sqlStaff);
            // Set Staff parameters
            ptmStaff.setInt(1, userID);
            ptmStaff.setString(2, s.getEmployeeCode());
            ptmStaff.setDate(3, s.getHireDate());
            ptmStaff.setString(4, validWorkStatus);
            ptmStaff.executeUpdate(); // Execute Staff insert

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            // Close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ptmUser != null) {
                try {
                    ptmUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ptmStaff != null) {
                try {
                    ptmStaff.close();
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
     * Checks if a Gmail address is already registered in the User table.
     *
     * @param gmail The Gmail address to check
     * @return true if the Gmail is registered, false otherwise
     * @throws SQLException If a database error occurs
     */
    @Override
    public boolean isGmailRegister(String gmail) throws SQLException {
        String sql = "SELECT 1 FROM [User] WHERE gmail = ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection(); // Obtain database connection
            ptm = connection.prepareStatement(sql); // Prepare SQL statement
            ptm.setString(1, gmail.toLowerCase()); // Set Gmail parameter (case-insensitive)
            rs = ptm.executeQuery(); // Execute query
            return rs.next(); // Return true if Gmail exists
        } finally {
            // Close resources
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
     * Updates an existing staff record in both User and Staff tables.
     * Uses a transaction to ensure data consistency.
     *
     * @param staff The Staff object with updated information
     * @throws SQLException If a database error occurs
     */
    @Override
    public void updateStaff(Staff staff) throws SQLException {
        Connection connection = null;
        PreparedStatement ptmUser = null, ptmStaff = null;
        try {
            connection = getConnection(); // Obtain database connection
            connection.setAutoCommit(false); // Start transaction

            // SQL to update User table
            String sqlUser = "UPDATE [User] SET gmail=?, roleID=?, password=?, firstName=?, lastName=?, dob=?, gender=?, address=?, phone=?, update_at=?, status=? WHERE userID=?";
            ptmUser = connection.prepareStatement(sqlUser);
            // Set User parameters
            ptmUser.setString(1, staff.getGmail());
            ptmUser.setInt(2, staff.getRoleID());
            ptmUser.setString(3, staff.getPassword());
            ptmUser.setString(4, staff.getFirstName());
            ptmUser.setString(5, staff.getLastName());
            ptmUser.setDate(6, staff.getDob());
            ptmUser.setString(7, staff.getGender());
            ptmUser.setString(8, staff.getAddress());
            ptmUser.setString(9, staff.getPhone());
            ptmUser.setDate(10, staff.getUpdateDate());
            ptmUser.setInt(11, staff.getStatus());
            ptmUser.setInt(12, staff.getUserID());
            ptmUser.executeUpdate(); // Execute User update

            // SQL to update Staff table
            String sqlStaff = "UPDATE Staff SET employeeCode=?, hireDate=?, workStatus=? WHERE staffID=?";
            ptmStaff = connection.prepareStatement(sqlStaff);
            // Set Staff parameters
            ptmStaff.setString(1, staff.getEmployeeCode());
            ptmStaff.setDate(2, staff.getHireDate());
            ptmStaff.setString(3, staff.getWorkStatus());
            ptmStaff.setInt(4, staff.getStaffID());
            ptmStaff.executeUpdate(); // Execute Staff update

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // Rollback on error
            }
            throw e;
        } finally {
            // Close resources
            if (ptmUser != null) {
                ptmUser.close();
            }
            if (ptmStaff != null) {
                ptmStaff.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Soft deletes a staff record by setting User status to 0 and Staff workStatus to "Nghỉ việc".
     * Uses a transaction to ensure data consistency.
     *
     * @param staffID The ID of the staff to delete
     * @throws SQLException If a database error occurs or staffID is not found
     */
    @Override
    public void deleteStaff(int staffID) throws SQLException {
        Connection connection = null;
        PreparedStatement ptmGet = null, ptmUpdateUser = null, ptmUpdateStaff = null;
        try {
            connection = getConnection(); // Obtain database connection
            connection.setAutoCommit(false); // Start transaction

            // SQL to retrieve userID from Staff
            String getUserIDSQL = "SELECT userID FROM Staff WHERE staffID=?";
            ptmGet = connection.prepareStatement(getUserIDSQL);
            ptmGet.setInt(1, staffID); // Set staffID parameter
            ResultSet rs = ptmGet.executeQuery();
            int userID = -1;
            if (rs.next()) {
                userID = rs.getInt("userID");
            } else {
                throw new SQLException("Không tìm thấy nhân viên với staffID = " + staffID);
            }
            rs.close();
            ptmGet.close();

            // SQL to update User status
            String updateUserSQL = "UPDATE [User] SET status=?, update_at=? WHERE userID=?";
            ptmUpdateUser = connection.prepareStatement(updateUserSQL);
            ptmUpdateUser.setInt(1, 0); // Set status to inactive
            ptmUpdateUser.setDate(2, new Date(System.currentTimeMillis())); // Set current date
            ptmUpdateUser.setInt(3, userID); // Set userID
            ptmUpdateUser.executeUpdate(); // Execute User update

            // SQL to update Staff workStatus
            String updateStaffSQL = "UPDATE Staff SET workStatus=? WHERE staffID=?";
            ptmUpdateStaff = connection.prepareStatement(updateStaffSQL);
            ptmUpdateStaff.setString(1, "Nghỉ việc"); // Set workStatus to resigned
            ptmUpdateStaff.setInt(2, staffID); // Set staffID
            ptmUpdateStaff.executeUpdate(); // Execute Staff update

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            // Close resources
            if (ptmGet != null) {
                try {
                    ptmGet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ptmUpdateUser != null) {
                try {
                    ptmUpdateUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ptmUpdateStaff != null) {
                try {
                    ptmUpdateStaff.close();
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
     * Changes the status of a staff member in both User and Staff tables.
     * Updates User status and Staff workStatus ("Đang làm việc" for active, "Nghỉ việc" for inactive).
     *
     * @param staffID The ID of the staff member
     * @param newStatus The new status (1 for active, 0 for inactive)
     * @throws SQLException If a database error occurs or staffID/userID is not found
     */
    @Override
    public void changeStatus(int staffID, int newStatus) throws SQLException {
        Connection connection = null;
        PreparedStatement ptm = null, ptmUser = null, ptmStaff = null;
        ResultSet rs = null;

        try {
            connection = getConnection(); // Obtain database connection
            connection.setAutoCommit(false); // Start transaction

            // SQL to retrieve userID from Staff
            String sql = "SELECT userID FROM Staff WHERE staffID = ?";
            ptm = connection.prepareStatement(sql);
            ptm.setInt(1, staffID); // Set staffID parameter
            rs = ptm.executeQuery();
            int userID;
            if (rs.next()) {
                userID = rs.getInt("userID");
            } else {
                throw new SQLException("Không tìm thấy nhân viên với staffID = " + staffID);
            }

            // SQL to update User status
            String updateUserSQL = "UPDATE [User] SET status = ?, update_at = GETDATE() WHERE userID = ?";
            ptmUser = connection.prepareStatement(updateUserSQL);
            ptmUser.setInt(1, newStatus); // Set new status
            ptmUser.setInt(2, userID); // Set userID
            int userRows = ptmUser.executeUpdate(); // Execute User update
            if (userRows == 0) {
                throw new SQLException("Không tìm thấy người dùng với userID = " + userID);
            }

            // SQL to update Staff workStatus
            String updateStaffSQL = "UPDATE Staff SET workStatus = ? WHERE staffID = ?";
            ptmStaff = connection.prepareStatement(updateStaffSQL);
            ptmStaff.setString(1, newStatus == 1 ? "Đang làm việc" : "Nghỉ việc"); // Set workStatus based on newStatus
            ptmStaff.setInt(2, staffID); // Set staffID
            int staffRows = ptmStaff.executeUpdate(); // Execute Staff update
            if (staffRows == 0) {
                throw new SQLException("Không tìm thấy nhân viên với staffID = " + staffID);
            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new SQLException("Lỗi khi thay đổi trạng thái nhân viên: " + e.getMessage(), e); // Wrap exception with context
        } finally {
            // Close resources
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
            if (ptmUser != null) {
                try {
                    ptmUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ptmStaff != null) {
                try {
                    ptmStaff.close();
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
     * Checks if an employee code is already registered in the Staff table.
     *
     * @param employeeCode The employee code to check
     * @return true if the employee code is registered, false otherwise
     */
    @Override
    public boolean isEmployeeCodeRegister(String employeeCode) {
        String sql = "SELECT COUNT(*) FROM staff WHERE employeeCode = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeCode); // Set employeeCode parameter
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if code exists
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking employee code: " + e.getMessage());
        }
        return false;
    }

    /**
     * Main method for testing the insertStaff method.
     * Creates a sample Staff object and attempts to insert it into the database.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        try {
            // Create a sample Staff object
            Staff newStaff = new Staff(
                0, // staffID (auto-generated by database, set to 0)
                0, // userID (auto-generated by database, set to 0)
                "EMP" + System.currentTimeMillis(), // employeeCode (unique code, using timestamp for uniqueness)
                Date.valueOf("2025-06-24"), // hireDate
                "Đang làm việc", // workStatus (corrected to match constraint)
                "newstaff2@example.com", // gmail
                2, // roleID (example role ID)
                "StaffPass1234.", // password
                "Jim", // firstName
                "Load", // lastName
                Date.valueOf("1995-05-15"), // dob
                "Nam", // gender
                "456 Oak Avenue", // address
                "0987654322", // phone
                new Date(System.currentTimeMillis()), // create_at
                new Date(System.currentTimeMillis()), // update_at
                1 // status (1 for active)
            );

            // Insert the staff
            dao.insertStaff(newStaff);
            System.out.println("Staff inserted successfully.");

        } catch (SQLException e) {
            System.err.println("Failed to insert staff: " + e.getMessage());
            e.printStackTrace();
        }
    }
}