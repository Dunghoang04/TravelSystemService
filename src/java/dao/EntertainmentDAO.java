/**
 * Manages data access for entertainment records in the agent module.<br>
 * Provides methods to insert, update, delete, and retrieve entertainment
 * data.<br>
 * <p>
 * Bugs: No known issues.<br>
 *
 * @author Hoang Tuan Dung
 *
 * Project: TravelAgentService Version: 1.0 Date: 2025-06-07 Record of Change:
 * DATE Version AUTHOR DESCRIPTION 2025-06-07 1.0 Hoang Tuan Dung First
 * implementation
 */
package dao;

import dal.DBContext;
import model.Entertainment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

/**
 * Implementation of the EntertainmentDAO interface for managing entertainment
 * records. Provides CRUD operations and search functionality for the
 * Entertainment table.
 *
 * Project: TravelAgentService Version: 1.0 Date: 2025-06-07 Bugs: No known
 * issues.
 *
 * Record of Change: DATE Version AUTHOR DESCRIPTION 2025-06-07 1.0 Hoang Tuan
 * Dung First implementation 2025-06-08 1.1 Hoang Tuan Dung Enhanced Javadoc and
 * error handling
 *
 * @author Hoang Tuan Dung
 */
public class EntertainmentDAO extends DBContext implements IEntertainmentDAO {

    private static final String INSERT_ENTERTAINMENT_SQL = "INSERT INTO [dbo].[Entertainment] ([serviceID], [name], [image], [address], [phone], [description], [rate], [type], [status], [timeOpen], [timeClose], [dayOfWeekOpen], [ticketPrice]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_ENTERTAINMENTS_SQL = "SELECT * FROM Entertainment ORDER BY serviceID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String COUNT_ALL_SQL = "SELECT COUNT(*) FROM Entertainment";
    private static final String SELECT_BY_SERVICE_ID_SQL = "SELECT * FROM Entertainment WHERE serviceID = ?";
    private static final String SEARCH_BY_TYPE_AND_NAME_SQL = "SELECT * FROM Entertainment WHERE LOWER(name) LIKE LOWER(?) AND status = ? ORDER BY serviceID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String COUNT_BY_TYPE_AND_NAME_SQL = "SELECT COUNT(*) FROM Entertainment WHERE LOWER(name) LIKE LOWER(?) AND status = ?";
    private static final String UPDATE_ENTERTAINMENT_SQL = "UPDATE [dbo].[Entertainment] SET [name] = ?, [image] = ?, [address] = ?, [phone] = ?, [description] = ?, [rate] = ?, [type] = ?, [status] = ?, [timeOpen] = ?, [timeClose] = ?, [dayOfWeekOpen] = ?, [ticketPrice] = ? WHERE serviceID = ?";
    private static final String SELECT_STATUS_SQL = "SELECT status FROM [dbo].[Entertainment] WHERE serviceID = ?";
    private static final String CHANGE_STATUS_SQL = "UPDATE [dbo].[Entertainment] SET [status] = ? WHERE serviceID = ?";
    private static final String DELETE_ENTERTAINMENT_SQL = "DELETE FROM Entertainment WHERE serviceID = ?";
    private static final String DELETE_SERVICE_SQL = "DELETE FROM Service WHERE serviceID = ?";
    private static final String SEARCH_BY_NAME_SQL = "SELECT * FROM Entertainment WHERE LOWER(name) LIKE LOWER(?) ORDER BY serviceID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String COUNT_BY_NAME_SQL = "SELECT COUNT(*) FROM Entertainment WHERE LOWER(name) LIKE LOWER(?)";
    private static final String SELECT_BY_STATUS_SQL = "SELECT * FROM Entertainment WHERE status = ? ORDER BY serviceID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String COUNT_BY_STATUS_SQL = "SELECT COUNT(*) FROM Entertainment WHERE status = ?";

    private Entertainment createEntertainmentFromResultSet(ResultSet resultSet) throws SQLException {
        Time timeOpen = resultSet.getTime("timeOpen");
        Time timeClose = resultSet.getTime("timeClose");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return new Entertainment(
                resultSet.getInt("serviceID"),
                resultSet.getString("name"),
                resultSet.getString("image"),
                resultSet.getString("address"),
                resultSet.getString("phone"),
                resultSet.getString("description"),
                resultSet.getFloat("rate"),
                resultSet.getString("type"),
                resultSet.getInt("status"),
                timeOpen != null ? simpleDateFormat.format(timeOpen) : "",
                timeClose != null ? simpleDateFormat.format(timeClose) : "",
                resultSet.getString("dayOfWeekOpen"),
                resultSet.getFloat("ticketPrice")
        );
    }

    /**
     * Inserts a new entertainment record into the database with transaction
     * support.
     *
     * @param name the name of the entertainment
     * @param image the image URL or path
     * @param address the address
     * @param phone the phone number
     * @param description the description
     * @param rate the rating (0–5)
     * @param type the type of entertainment
     * @param status the status
     * @param timeOpen the opening time (HH:mm:ss)
     * @param timeClose the closing time (HH:mm:ss)
     * @param dayOfWeekOpen days open
     * @param ticketPrice the ticket price
     * @throws SQLException if a database error occurs
     */
    @Override
    public void insertEntertainmentFull(String name, String image, String address, String phone, String description, float rate, String type, int status, String timeOpen, String timeClose, String dayOfWeekOpen, float ticketPrice) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            conn.setAutoCommit(false); // Start transaction
            IService serviceDao = new ServiceDAO();
            // Insert into Service using the above ID and get service ID
            int serviceId = serviceDao.addService(name);

            // Insert into Entertainment
            preparedStatement = conn.prepareStatement(INSERT_ENTERTAINMENT_SQL);
            preparedStatement.setInt(1, serviceId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, image);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, description);
            preparedStatement.setFloat(7, rate);
            preparedStatement.setString(8, type);
            preparedStatement.setInt(9, status);
            preparedStatement.setTime(10, Time.valueOf(timeOpen));
            preparedStatement.setTime(11, Time.valueOf(timeClose));
            preparedStatement.setString(12, dayOfWeekOpen);
            preparedStatement.setFloat(13, ticketPrice);
            preparedStatement.executeUpdate();
            conn.commit(); // Commit transaction
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

    /**
     * Retrieves a paginated list of all entertainment records.
     *
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of Entertainment objects
     * @throws SQLException if a database error occurs
     */
    @Override
    public List<Entertainment> getListEntertainment(int page, int pageSize) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Entertainment> entertainmentList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(SELECT_ALL_ENTERTAINMENTS_SQL);
            preparedStatement.setInt(1, (page - 1) * pageSize);
            preparedStatement.setInt(2, pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entertainmentList.add(createEntertainmentFromResultSet(resultSet));
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
        return entertainmentList;
    }

    /**
     * Counts all entertainment records.
     *
     * @return the total number of records
     * @throws SQLException if a database error occurs
     */
    @Override
    public int countAll() throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(COUNT_ALL_SQL);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
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
        return 0;
    }

    /**
     * Retrieves an entertainment record by service ID.
     *
     * @param serviceId the service ID
     * @return the Entertainment object, or null if not found
     * @throws SQLException if a database error occurs
     */
    @Override
    public Entertainment getEntertainmentByServiceId(int serviceId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(SELECT_BY_SERVICE_ID_SQL);
            preparedStatement.setInt(1, serviceId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createEntertainmentFromResultSet(resultSet);
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
        return null;
    }

    /**
     * Searches entertainment records by status and name with pagination.
     *
     * @param status the status
     * @param name the name to search (partial match)
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Entertainment objects
     * @throws SQLException if a database error occurs
     */
    @Override
    public List<Entertainment> searchByTypeAndName(int status, String name, int page, int pageSize) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Entertainment> entertainmentList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(SEARCH_BY_TYPE_AND_NAME_SQL);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setInt(2, status);
            preparedStatement.setInt(3, (page - 1) * pageSize);
            preparedStatement.setInt(4, pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entertainmentList.add(createEntertainmentFromResultSet(resultSet));
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
        return entertainmentList;
    }

    /**
     * Counts entertainment records by status and name.
     *
     * @param status the status
     * @param name the name to search
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    @Override
    public int countByTypeAndName(int status, String name) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(COUNT_BY_TYPE_AND_NAME_SQL);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setInt(2, status);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
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
        return 0;
    }

    /**
     * Updates an entertainment record with transaction support.
     *
     * @param serviceId the service ID
     * @param name the name
     * @param image the image URL or path
     * @param address the address
     * @param phone the phone number
     * @param description the description
     * @param rate the rating
     * @param type the type
     * @param status the status
     * @param timeOpen the opening time
     * @param timeClose the closing time
     * @param dayOfWeekOpen days open
     * @param ticketPrice the ticket price
     * @throws SQLException if a database error occurs
     */
    @Override
    public void updateEntertainment(int serviceId, String name, String image, String address, String phone, String description, float rate, String type, int status, String timeOpen, String timeClose, String dayOfWeekOpen, double ticketPrice) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            conn.setAutoCommit(false); // Start transaction
            preparedStatement = conn.prepareStatement(UPDATE_ENTERTAINMENT_SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, image);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, description);
            preparedStatement.setFloat(6, rate);
            preparedStatement.setString(7, type);
            preparedStatement.setInt(8, status);
            preparedStatement.setTime(9, Time.valueOf(timeOpen));
            preparedStatement.setTime(10, Time.valueOf(timeClose));
            preparedStatement.setString(11, dayOfWeekOpen);
            preparedStatement.setDouble(12, ticketPrice);
            preparedStatement.setInt(13, serviceId);
            preparedStatement.executeUpdate();

            ServiceDAO serviceDAO = new ServiceDAO();
            serviceDAO.updateServiceName(serviceId, name);
            conn.commit(); // Commit transaction
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

    /**
     * Retrieves the status of an entertainment by service ID.
     *
     * @param serviceId the service ID
     * @return the status, or -1 if not found
     * @throws SQLException if a database error occurs
     */
    @Override
    public int getStatusByServiceId(int serviceId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(SELECT_STATUS_SQL);
            preparedStatement.setInt(1, serviceId);
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

    /**
     * Changes the status of an entertainment record with transaction support.
     *
     * @param serviceId the service ID
     * @param status the new status
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    @Override
    public boolean changeStatus(int serviceId, int status) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            conn.setAutoCommit(false); // Start transaction
            preparedStatement = conn.prepareStatement(CHANGE_STATUS_SQL);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, serviceId);
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

    /**
     * Deletes an entertainment record and its related service with transaction
     * support.
     *
     * @param serviceId the service ID
     * @throws SQLException if a database error occurs
     */
    @Override
    public void deleteEntertainment(int serviceId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatementEnt = null;
        PreparedStatement preparedStatementServ = null;
        try {
            conn.setAutoCommit(false); // Start transaction
            preparedStatementEnt = conn.prepareStatement(DELETE_ENTERTAINMENT_SQL);
            preparedStatementEnt.setInt(1, serviceId);
            preparedStatementEnt.executeUpdate();

            preparedStatementServ = conn.prepareStatement(DELETE_SERVICE_SQL);
            preparedStatementServ.setInt(1, serviceId);
            preparedStatementServ.executeUpdate();
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback on error
            }
            throw e;
        } finally {
            if (preparedStatementEnt != null) {
                try {
                    preparedStatementEnt.close();
                } catch (SQLException e) {
                }
            }
            if (preparedStatementServ != null) {
                try {
                    preparedStatementServ.close();
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

    /**
     * Searches entertainment records by name with pagination.
     *
     * @param name the name to search (partial match)
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Entertainment objects
     * @throws SQLException if a database error occurs
     */
    @Override
    public List<Entertainment> searchEntertainmentByName(String name, int page, int pageSize) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Entertainment> entertainmentList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(SEARCH_BY_NAME_SQL);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setInt(2, (page - 1) * pageSize);
            preparedStatement.setInt(3, pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entertainmentList.add(createEntertainmentFromResultSet(resultSet));
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
        return entertainmentList;
    }

    /**
     * Counts entertainment records by name.
     *
     * @param name the name to search
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    @Override
    public int countByName(String name) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(COUNT_BY_NAME_SQL);
            preparedStatement.setString(1, "%" + name + "%");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
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
        return 0;
    }

    /**
     * Retrieves entertainment records by status with pagination.
     *
     * @param status the status
     * @param page the page number (1-based)
     * @param pageSize the number of records per page
     * @return a list of matching Entertainment objects
     * @throws SQLException if a database error occurs
     */
    @Override
    public List<Entertainment> getEntertainmentByStatus(int status, int page, int pageSize) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Entertainment> entertainmentList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(SELECT_BY_STATUS_SQL);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, (page - 1) * pageSize);
            preparedStatement.setInt(3, pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entertainmentList.add(createEntertainmentFromResultSet(resultSet));
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
        return entertainmentList;
    }

    /**
     * Counts entertainment records by status.
     *
     * @param status the status
     * @return the number of matching records
     * @throws SQLException if a database error occurs
     */
    @Override
    public int countByStatus(int status) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(COUNT_BY_STATUS_SQL);
            preparedStatement.setInt(1, status);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
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
        return 0;
    }
}
