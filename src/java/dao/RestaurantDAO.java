/**
 * Manages data access for restaurant records in the agent module.
 * Provides methods to insert, update, delete, and retrieve restaurant data, including agent-specific filtering.
 *
 * Project: TravelAgentService
 * Version: 1.1
 * Date: 2025-07-14
 * Bugs: No known issues.
 *
 * Record of Change:
 * DATE            Version             AUTHOR                      DESCRIPTION
 * 2025-06-13      1.0                 Hoang Tuan Dung             First implementation
 * 2025-07-14      1.1                 Grok                        Added agent ID filtering methods
 *
 * @author Hoang Tuan Dung, Grok
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Restaurant;

/**
 * Implementation of the IRestaurantDAO interface for managing restaurant records.
 * Provides CRUD operations and search functionality for the Restaurant table, including agent-specific filtering.
 */
public class RestaurantDAO extends DBContext implements IRestaurantDAO {

    private static final String INSERT_RESTAURANT_SQL = "INSERT INTO Restaurant(serviceId, name, image, address, phone, description, rate, type, status, timeOpen, timeClose) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_RESTAURANTS_BY_AGENT_SQL = "SELECT r.* FROM Restaurant r JOIN Service s ON r.serviceId = s.serviceId WHERE s.travelAgentID = ? ORDER BY r.serviceId DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String COUNT_ALL_RESTAURANTS_BY_AGENT_SQL = "SELECT COUNT(*) AS Total FROM Restaurant r JOIN Service s ON r.serviceId = s.serviceId WHERE s.travelAgentID = ?";
    private static final String SEARCH_BY_AGENT_TYPE_AND_NAME_SQL = "SELECT r.* FROM Restaurant r JOIN Service s ON r.serviceId = s.serviceId WHERE s.travelAgentID = ? AND LOWER(r.name) LIKE LOWER(?) AND r.status = ? ORDER BY r.serviceId DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String COUNT_BY_AGENT_TYPE_AND_NAME_SQL = "SELECT COUNT(*) AS Total FROM Restaurant r JOIN Service s ON r.serviceId = s.serviceId WHERE s.travelAgentID = ? AND LOWER(r.name) LIKE LOWER(?) AND r.status = ?";
    private static final String UPDATE_RESTAURANT_SQL = "UPDATE [dbo].[Restaurant] SET [name] = ?, [image] = ?, [address] = ?, [phone] = ?, [description] = ?, [rate] = ?, [type] = ?, [status] = ?, [timeOpen] = ?, [timeClose] = ? WHERE serviceId = ?";
    private static final String SELECT_STATUS_BY_SERVICEID_SQL = "SELECT status FROM [dbo].[Restaurant] WHERE serviceId = ?";
    private static final String CHANGE_STATUS_SQL = "UPDATE [dbo].[Restaurant] SET [status] = ? WHERE serviceId = ?";
    private static final String DELETE_RESTAURANT_SQL = "DELETE FROM Restaurant WHERE serviceId = ?";
    private static final String DELETE_SERVICE_SQL = "DELETE FROM Service WHERE serviceId = ?";
    private static final String SEARCH_BY_AGENT_AND_NAME_SQL = "SELECT r.* FROM Restaurant r JOIN Service s ON r.serviceId = s.serviceId WHERE s.travelAgentID = ? AND LOWER(r.name) LIKE LOWER(?) ORDER BY r.serviceId DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String COUNT_BY_AGENT_AND_NAME_SQL = "SELECT COUNT(*) AS Total FROM Restaurant r JOIN Service s ON r.serviceId = s.serviceId WHERE s.travelAgentID = ? AND LOWER(r.name) LIKE LOWER(?)";
    private static final String SELECT_RESTAURANT_BY_SERVICEID_SQL = "SELECT * FROM Restaurant WHERE serviceId = ?";
    private static final String COUNT_BY_AGENT_AND_STATUS_SQL = "SELECT COUNT(*) AS Total FROM Restaurant r JOIN Service s ON r.serviceId = s.serviceId WHERE s.travelAgentID = ? AND r.status = ?";
    private static final String SEARCH_BY_AGENT_AND_STATUS_SQL = "SELECT r.* FROM Restaurant r JOIN Service s ON r.serviceId = s.serviceId WHERE s.travelAgentID = ? AND r.status = ? ORDER BY r.serviceId DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private Restaurant createRestaurantFromResultSet(ResultSet resultSet) throws SQLException {
        Time timeOpen = resultSet.getTime("timeOpen");
        Time timeClose = resultSet.getTime("timeClose");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return new Restaurant(
                resultSet.getInt("serviceId"),
                resultSet.getString("name"),
                resultSet.getString("image"),
                resultSet.getString("address"),
                resultSet.getString("phone"),
                resultSet.getString("description"),
                resultSet.getFloat("rate"),
                resultSet.getString("type"),
                resultSet.getInt("status"),
                timeOpen != null ? simpleDateFormat.format(timeOpen) : "",
                timeClose != null ? simpleDateFormat.format(timeClose) : ""
        );
    }

    @Override
    public void insertRestaurantFull(int agentID, String name, String image, String address, String phone, String description, float rate, String type, int status, String timeOpen, String timeClose) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }
            conn.setAutoCommit(false);

            ServiceDao serviceDao = new ServiceDao();
            int serviceId = serviceDao.addService(1, name, agentID);

            preparedStatement = conn.prepareStatement(INSERT_RESTAURANT_SQL);
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
            preparedStatement.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Rollback failed", ex);
                }
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
    public List<Restaurant> getListRestaurantByAgent(int agentID, int page, int pageSize) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(SELECT_ALL_RESTAURANTS_BY_AGENT_SQL);
            preparedStatement.setInt(1, agentID);
            preparedStatement.setInt(2, (page - 1) * pageSize);
            preparedStatement.setInt(3, pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurantList.add(createRestaurantFromResultSet(resultSet));
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
        return restaurantList;
    }

    @Override
    public List<Restaurant> searchRestaurantByAgentAndName(int agentID, String name, int page, int pageSize) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(SEARCH_BY_AGENT_AND_NAME_SQL);
            preparedStatement.setInt(1, agentID);
            preparedStatement.setString(2, "%" + name + "%");
            preparedStatement.setInt(3, (page - 1) * pageSize);
            preparedStatement.setInt(4, pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurantList.add(createRestaurantFromResultSet(resultSet));
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
        return restaurantList;
    }

    @Override
    public int getStatusByServiceID(int serviceId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(SELECT_STATUS_BY_SERVICEID_SQL);
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

    @Override
    public boolean changeStatus(int serviceId, int status) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            conn.setAutoCommit(false);
            ServiceDao serviceDao = new ServiceDao();
            serviceDao.updateServiceStatus(serviceId, status);

            preparedStatement = conn.prepareStatement(CHANGE_STATUS_SQL);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, serviceId);
            int rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
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
    public List<Restaurant> searchByAgentTypeAndName(int agentID, int status, String name, int page, int pageSize) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(SEARCH_BY_AGENT_TYPE_AND_NAME_SQL);
            preparedStatement.setInt(1, agentID);
            preparedStatement.setString(2, "%" + name + "%");
            preparedStatement.setInt(3, status);
            preparedStatement.setInt(4, (page - 1) * pageSize);
            preparedStatement.setInt(5, pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurantList.add(createRestaurantFromResultSet(resultSet));
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
        return restaurantList;
    }

    @Override
    public Restaurant getRestaurantByServiceId(int id) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(SELECT_RESTAURANT_BY_SERVICEID_SQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createRestaurantFromResultSet(resultSet);
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
    
    public static void main(String[] args) throws SQLException {
        RestaurantDAO dao = new RestaurantDAO();
        System.out.println(dao.getRestaurantByServiceId(1));
    }

    @Override
    public List<Restaurant> getRestaurantByAgentAndStatus(int agentID, int status, int page, int pageSize) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(SEARCH_BY_AGENT_AND_STATUS_SQL);
            preparedStatement.setInt(1, agentID);
            preparedStatement.setInt(2, status);
            preparedStatement.setInt(3, (page - 1) * pageSize);
            preparedStatement.setInt(4, pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurantList.add(createRestaurantFromResultSet(resultSet));
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
        return restaurantList;
    }

    @Override
    public void updateRestaurant(int serviceId, String name, String image, String address, String phone, String description, float rate, String type, int status, String timeOpen, String timeClose) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(UPDATE_RESTAURANT_SQL);
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
            preparedStatement.setInt(11, serviceId);
            preparedStatement.executeUpdate();

            ServiceDao serviceDAO = new ServiceDao();
            serviceDAO.updateServiceName(serviceId, name);

            TourServiceDetailDAO tourServiceDetailDAO = new TourServiceDetailDAO();
            if (tourServiceDetailDAO.getTourServiceDetailsByServiceId(serviceId).size() > 0) {
                tourServiceDetailDAO.updateServiceNameByServiceId(serviceId, name);
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
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
    public void deleteRestaurant(int serviceId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatementRest = null;
        PreparedStatement preparedStatementServ = null;
        try {
            conn.setAutoCommit(false);
            preparedStatementRest = conn.prepareStatement(DELETE_RESTAURANT_SQL);
            preparedStatementRest.setInt(1, serviceId);
            preparedStatementRest.executeUpdate();

            preparedStatementServ = conn.prepareStatement(DELETE_SERVICE_SQL);
            preparedStatementServ.setInt(1, serviceId);
            preparedStatementServ.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (preparedStatementRest != null) {
                try {
                    preparedStatementRest.close();
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

    @Override
    public int countByAgentAndName(int agentID, String name) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(COUNT_BY_AGENT_AND_NAME_SQL);
            preparedStatement.setInt(1, agentID);
            preparedStatement.setString(2, "%" + name + "%");
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

    @Override
    public int countByAgentTypeAndName(int agentID, int status, String name) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(COUNT_BY_AGENT_TYPE_AND_NAME_SQL);
            preparedStatement.setInt(1, agentID);
            preparedStatement.setString(2, "%" + name + "%");
            preparedStatement.setInt(3, status);
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

    @Override
    public int countByAgentAndStatus(int agentID, int status) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(COUNT_BY_AGENT_AND_STATUS_SQL);
            preparedStatement.setInt(1, agentID);
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

    @Override
    public int countByAgent(int agentID) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(COUNT_ALL_RESTAURANTS_BY_AGENT_SQL);
            preparedStatement.setInt(1, agentID);
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