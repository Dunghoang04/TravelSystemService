package dao;

import com.sun.jdi.connect.spi.Connection;
import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Restaurant;

/**
 *
 * @author ad
 */
public class RestaurantDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public int insertService(String serviceName) throws SQLException {

        String sql = "INSERT INTO Service (serviceCategoryID) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, 1); // có thể nhận từ người dùng
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Không thể lấy serviceID sau khi insert Service.");
        }
    }

    public void insertRestaurantFull(String name, String image, String address, String phone, String description, float rate, String type, int status, String timeOpen, String timeClose) {
        try {
            connection.setAutoCommit(false);
            int serviceID = insertService("Dịch vụ nhà hàng"); // hoặc name, nếu muốn

            String sql = "INSERT INTO Restaurant(serviceID, name,image,address,phone, description,rate,type,status, timeOpen, timeClose) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceID);
            ps.setString(2, name);
            ps.setString(3, image);
            ps.setString(4, address);
            ps.setString(5, phone);
            ps.setString(6, description);
            ps.setFloat(7, rate);
            ps.setString(8, type);
            ps.setInt(9, 1);
            ps.setTime(10, Time.valueOf(timeOpen));
            ps.setTime(11, Time.valueOf(timeClose));

            ps.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Restaurant> getListRestaurant() {
        String sql = "select * from Restaurant";

        List<Restaurant> listOfRestaurant = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Time timeOpen = rs.getTime(10);
                Time timeClose = rs.getTime(11);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String timeOpenFormat = sdf.format(timeOpen);
                String timeCloseFormat = sdf.format(timeClose);

                Restaurant res = new Restaurant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getString(8), rs.getInt(9), timeOpenFormat, timeCloseFormat);
                listOfRestaurant.add(res);
                System.out.println(res);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listOfRestaurant;
    }

    public List<Restaurant> searchRestaurantByName(String name) {
        String sql = "select * from Restaurant where name like '%" + name + "%'";
        List<Restaurant> listOfRestaurant = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Time timeOpen = rs.getTime(10);
                Time timeClose = rs.getTime(11);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String timeOpenFormat = sdf.format(timeOpen);
                String timeCloseFormat = sdf.format(timeClose);
                Restaurant res = new Restaurant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getString(8), rs.getInt(9), timeOpenFormat, timeCloseFormat);
                listOfRestaurant.add(res);
                System.out.println(res);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listOfRestaurant;
    }

    public Restaurant getRestaurantByServiceId(int id) {
        String sql = "select * from Restaurant where serviceID=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Time timeOpen = rs.getTime(10);
                Time timeClose = rs.getTime(11);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String timeOpenFormat = sdf.format(timeOpen);
                String timeCloseFormat = sdf.format(timeClose);

                Restaurant res = new Restaurant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getString(8), rs.getInt(9), timeOpenFormat, timeCloseFormat);
                return res;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateRestaurant(int serviceId, String name, String image, String address, String phone, String description, String type, int status, String timeOpen, String timeClose) {
        String sql = "UPDATE [dbo].[Restaurant]\n"
                + "   SET [name] =?\n"
                + "      ,[image] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[phone] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[type] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[timeOpen] = ?\n"
                + "      ,[timeClose] = ?\n"
                + " WHERE [serviceID]=?";
        try {
            ps=connection.prepareCall(sql);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setString(5, description);
            ps.setString(6, type);
            ps.setInt(7, status);
            ps.setTime(8, Time.valueOf(timeOpen));
            ps.setTime(9, Time.valueOf(timeClose));
            ps.setInt(10, serviceId);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
 

    public List<Restaurant> getRestaurantByType(String type) {
        String sql = "select * from Restaurant where type like '%" + type + "%'";
        List<Restaurant> listOfRestaurant = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Time timeOpen = rs.getTime(10);
                Time timeClose = rs.getTime(11);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String timeOpenFormat = sdf.format(timeOpen);
                String timeCloseFormat = sdf.format(timeClose);
                Restaurant res = new Restaurant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getString(8), rs.getInt(9), timeOpenFormat, timeCloseFormat);
                listOfRestaurant.add(res);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listOfRestaurant;
    }

    public void deleteRestaurant(int serviceID) {
        String sqlRestaurant = "delete from Restaurant where serviceID=?";
        String sqlService = "delete from Service where serviceID=?";
        try {
            // Delete from Restaurant first
            PreparedStatement psRestaurant = connection.prepareStatement(sqlRestaurant); // Assume conn is initialized
            psRestaurant.setInt(1, serviceID);
            psRestaurant.executeUpdate();

            // Then delete from Service
            PreparedStatement psService = connection.prepareStatement(sqlService);
            psService.setInt(1, serviceID);
            psService.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting restaurant: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        RestaurantDAO rdao = new RestaurantDAO();
        Restaurant list = rdao.getRestaurantByServiceId(1);
        System.out.println(list);
    }
}
