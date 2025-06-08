/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-08  1.0        Nguyễn Văn Vang   First implementation
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Accommodation;
import model.Room;

/**
 * Implements data access operations for Accommodation entities.<br>
 * Handles insertion, retrieval, updating, and querying of accommodation data from the database using SQL queries.<br>
 * <p>
 * Bugs: Potential SQL injection risk if user input is used directly in SQL queries.</p>
 *
 * @author Nguyễn Văn Vang
 */
public class AccommodationDAO extends DBContext implements IAccommodationDAO {

    /**
     * Inserts a new service into the database and returns the generated service ID.<br>
     *
     * @param serviceName The name of the service to insert
     * @return The generated service ID as an integer
     * @throws SQLException If a database error occurs
     */
    /*
     * Inserts a new service record with a fixed serviceCategoryID and provided serviceName.
     * Returns the auto-generated serviceID using Statement.RETURN_GENERATED_KEYS.
     */
    @Override
    public int insertService(String serviceName) throws SQLException {
        String sql = "INSERT INTO [dbo].[Service] ([serviceCategoryID], [serviceName]) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 3); // Assumes serviceCategoryID = 3 for "Hotel"
            ps.setString(2, serviceName);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Không thể lấy serviceID sau khi insert Service.");
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves a list of all accommodations from the database.<br>
     *
     * @return A List of Accommodation objects
     */
    /*
     * Queries the Accommodation table to retrieve all records.
     * Formats check-in and check-out times using SimpleDateFormat and constructs Accommodation objects.
     */
    @Override
    public List<Accommodation> getListAccommodation() {
        String sql = "SELECT * FROM Accommodation";
        List<Accommodation> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            while (rs.next()) {
                Time checkInTime = rs.getTime("checkInTime");
                Time checkOutTime = rs.getTime("checkOutTime");
                String formattedCheckIn = (checkInTime != null) ? sdf.format(checkInTime) : "00:00:00";
                String formattedCheckOut = (checkOutTime != null) ? sdf.format(checkOutTime) : "00:00:00";
                Accommodation acc = new Accommodation(
                        rs.getInt("serviceID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        formattedCheckIn,
                        formattedCheckOut);
                list.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * Inserts a new accommodation into the database.<br>
     *
     * @param name The name of the accommodation
     * @param image The image URL or path for the accommodation
     * @param address The address of the accommodation
     * @param phone The contact phone number
     * @param description A description of the accommodation
     * @param rate The rating of the accommodation
     * @param type The type of accommodation (e.g., hotel, hostel)
     * @param status The status of the accommodation (e.g., available, unavailable)
     * @param checkInTime The check-in time for the accommodation
     * @param checkOutTime The check-out time for the accommodation
     */
    /*
     * Inserts a new service and accommodation record in a single transaction.
     * Ensures check-in and check-out times are in HH:mm:ss format before insertion.
     */
    @Override
    public void insertAccommodation(String name, String image, String address, String phone, String description,
            float rate, String type, int status, String checkInTime, String checkOutTime) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            int serviceID = insertService("Dịch vụ nơi ở");

            // Ensure HH:mm:ss format
            checkInTime = (checkInTime + ":00").trim();
            checkOutTime = (checkOutTime + ":00").trim();

            String sql = "INSERT INTO [dbo].[Accommodation] "
                    + "([serviceID], [name], [image], [address], [phone], [description], [rate], [type], [status], [checkInTime], [checkOutTime]) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, serviceID);
            ps.setString(2, name);
            ps.setString(3, image);
            ps.setString(4, address);
            ps.setString(5, phone);
            ps.setString(6, description);
            ps.setFloat(7, rate);
            ps.setString(8, type);
            ps.setInt(9, status);
            ps.setTime(10, Time.valueOf(checkInTime));
            ps.setTime(11, Time.valueOf(checkOutTime));
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves an accommodation by its service ID.<br>
     *
     * @param serviceId The unique identifier of the accommodation service
     * @return An Accommodation object if found, null otherwise
     */
    /*
     * Queries the Accommodation table by serviceID.
     * Formats check-in and check-out times and constructs an Accommodation object.
     */
    @Override
    public Accommodation getAccommodationByServiceId(int serviceId) {
        String sql = "SELECT * FROM Accommodation WHERE serviceID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            if (rs.next()) {
                Time checkInTime = rs.getTime("checkInTime");
                Time checkOutTime = rs.getTime("checkOutTime");
                String formattedCheckIn = (checkInTime != null) ? sdf.format(checkInTime) : "00:00:00";
                String formattedCheckOut = (checkOutTime != null) ? sdf.format(checkOutTime) : "00:00:00";
                return new Accommodation(
                        rs.getInt("serviceID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        formattedCheckIn,
                        formattedCheckOut);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing accommodation in the database.<br>
     *
     * @param serviceId The unique identifier of the accommodation service
     * @param name The updated name of the accommodation
     * @param image The updated image URL or path
     * @param address The updated address
     * @param phone The updated contact phone number
     * @param description The updated description
     * @param rate The updated rating
     * @param type The updated type of accommodation
     * @param status The updated status
     * @param checkInTime The updated check-in time
     * @param checkOutTime The updated check-out time
     */
    /*
     * Updates the Accommodation table for the specified serviceID.
     * Ensures check-in and check-out times are in HH:mm:ss format before updating.
     */
    @Override
    public void updateAccommodation(int serviceId, String name, String image, String address, String phone,
            String description, float rate, String type, int status, String checkInTime,
            String checkOutTime) {
        String sql = "UPDATE [dbo].[Accommodation] "
                + "SET [name] = ?, [image] = ?, [address] = ?, [phone] = ?, [description] = ?, [rate] = ?, [type] = ?, [status] = ?, [checkInTime] = ?, [checkOutTime] = ? "
                + "WHERE serviceID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            checkInTime = (checkInTime + ":00").trim();
            checkOutTime = (checkOutTime + ":00").trim();
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setString(5, description);
            ps.setFloat(6, rate);
            ps.setString(7, type);
            ps.setInt(8, status);
            ps.setTime(9, Time.valueOf(checkInTime));
            ps.setTime(10, Time.valueOf(checkOutTime));
            ps.setInt(11, serviceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Searches for accommodations by name.<br>
     *
     * @param name The name or partial name to search for
     * @return A List of Accommodation objects matching the search criteria
     */
    /*
     * Queries the Accommodation table using a LIKE clause for the name field.
     * Formats check-in and check-out times and constructs Accommodation objects.
     */
    @Override
    public List<Accommodation> searchAccommodationByName(String name) {
        String sql = "SELECT * FROM Accommodation WHERE name LIKE ?";
        List<Accommodation> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            while (rs.next()) {
                Time checkInTime = rs.getTime("checkInTime");
                Time checkOutTime = rs.getTime("checkOutTime");
                String formattedCheckIn = (checkInTime != null) ? sdf.format(checkInTime) : "00:00:00";
                String formattedCheckOut = (checkOutTime != null) ? sdf.format(checkOutTime) : "00:00:00";
                Accommodation acc = new Accommodation(
                        rs.getInt("serviceID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        formattedCheckIn,
                        formattedCheckOut);
                list.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * Retrieves a list of rooms associated with a specific accommodation service ID.<br>
     *
     * @param serviceId The unique identifier of the accommodation service
     * @return A List of Room objects associated with the accommodation
     */
    /*
     * Delegates to RoomDAO to retrieve rooms by accommodationID.
     * Assumes serviceId corresponds to accommodationID.
     */
    @Override
    public List<Room> getRoomsByServiceId(int serviceId) throws SQLException {
        RoomDAO roomDAO = new RoomDAO();
        return roomDAO.getRoomsByAccommodationID(serviceId);
    }

    /**
     * Main method for testing AccommodationDAO functionality.<br>
     *
     * @param args Command-line arguments
     */
    /*
     * Tests retrieval of an accommodation and its associated rooms by serviceId.
     * Prints the results to the console.
     */
    public static void main(String[] args) {
        AccommodationDAO dao = new AccommodationDAO();
        int serviceId = 1; // Replace with an actual serviceId
        try {
            Accommodation acc = dao.getAccommodationByServiceId(serviceId);
            if (acc != null) {
                System.out.println("Retrieved accommodation: " + acc);
                List<Room> rooms = dao.getRoomsByServiceId(serviceId);
                for (Room room : rooms) {
                    System.out.println(room);
                }
            } else {
                System.out.println("Accommodation not found for serviceId: " + serviceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}