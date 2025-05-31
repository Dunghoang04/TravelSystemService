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
import model.Entertainment;

public class EntertainmentDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public int insertService(String serviceName) throws SQLException {
        String sql = "INSERT INTO [dbo].[Service]\n"
                + "           ([serviceCategoryID]\n"
                + "           ,[serviceName])\n"
                + "     VALUES\n"
                + "           (?,?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, 2); // Ví dụ: 2 là mã dịch vụ giải trí
        ps.setString(2, serviceName);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Không thể lấy serviceID sau khi insert Service.");
        }
    }

    public void insertEntertainmentFull(String name, String image, String address, String phone, String description, float rate, String type, int status, String timeOpen, String timeClose, String dayOfWeekOpen, float ticketPrice) {
        try {
            connection.setAutoCommit(false);
            int serviceID = insertService("Dịch vụ giải trí");

            String sql = "INSERT INTO [dbo].[Entertainment]\n"
                    + "           ([serviceID]\n"
                    + "           ,[name]\n"
                    + "           ,[image]\n"
                    + "           ,[address]\n"
                    + "           ,[phone]\n"
                    + "           ,[description]\n"
                    + "           ,[rate]\n"
                    + "           ,[type]\n"
                    + "           ,[status]\n"
                    + "           ,[timeOpen]\n"
                    + "           ,[timeClose]\n"
                    + "           ,[dayOfWeekOpen]\n"
                    + "           ,[ticketPrice])\n"
                    + "     VALUES\n"
                    + "           (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceID);
            ps.setString(2, name);
            ps.setString(3, image);
            ps.setString(4, address);
            ps.setString(5, phone);
            ps.setString(6, description);
            ps.setFloat(7, rate);
            ps.setString(8, type);
            ps.setInt(9, status);
            ps.setTime(10, Time.valueOf(timeOpen));
            ps.setTime(11, Time.valueOf(timeClose));
            ps.setString(12, dayOfWeekOpen);
            ps.setFloat(13, ticketPrice);
            ps.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Entertainment> getListEntertainment() {
        String sql = "SELECT * FROM Entertainment";
        List<Entertainment> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Time timeOpen = rs.getTime("timeOpen");
                Time timeClose = rs.getTime("timeClose");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Entertainment ent = new Entertainment(
                        rs.getInt("serviceID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        sdf.format(timeOpen),
                        sdf.format(timeClose),
                        rs.getString("dayOfWeekOpen"),
                        rs.getFloat("ticketPrice")
                );
                list.add(ent);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Entertainment getEntertainmentByServiceId(int serviceId) {
        String sql = "SELECT * FROM Entertainment WHERE serviceID=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Time timeOpen = rs.getTime("timeOpen");
                Time timeClose = rs.getTime("timeClose");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                return new Entertainment(
                        rs.getInt("serviceID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        sdf.format(timeOpen),
                        sdf.format(timeClose),
                        rs.getString("dayOfWeekOpen"),
                        rs.getFloat("ticketPrice")
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateEntertainment(int serviceId, String name, String image, String address, String phone,
            String description, float rate, String type, int status, String timeOpen,
            String timeClose, String dayOfWeekOpen, double ticketPrice) {
        String sql = "UPDATE [dbo].[Entertainment]\n"
                + "   SET [name] = ?\n"
                + "      ,[image] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[phone] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[rate] = ?\n"
                + "      ,[type] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[timeOpen] = ?\n"
                + "      ,[timeClose] = ?\n"
                + "      ,[dayOfWeekOpen] = ?\n"
                + "      ,[ticketPrice] = ?\n"
                + " WHERE serviceID=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setString(5, description);
            ps.setFloat(6, rate);
            ps.setString(7, type);
            ps.setInt(8, status);
            ps.setTime(9, Time.valueOf(timeOpen));
            ps.setTime(10, Time.valueOf(timeClose));
            ps.setString(11, dayOfWeekOpen);
            ps.setDouble(12, ticketPrice);
            ps.setInt(13, serviceId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteEntertainment(int serviceID) {
        String sqlEntertainment = "DELETE FROM Entertainment WHERE serviceID=?";
        String sqlService = "DELETE FROM Service WHERE serviceID=?";
        try {
            PreparedStatement psEnt = connection.prepareStatement(sqlEntertainment);
            psEnt.setInt(1, serviceID);
            psEnt.executeUpdate();

            PreparedStatement psServ = connection.prepareStatement(sqlService);
            psServ.setInt(1, serviceID);
            psServ.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting entertainment: " + e.getMessage());
        }
    }

    public List<Entertainment> searchEntertainmentByName(String name) {
        String sql = "select * from Entertainment where type like N'%" + name + "%'";
        List<Entertainment> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Time timeOpen = rs.getTime("timeOpen");
                Time timeClose = rs.getTime("timeClose");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Entertainment ent = new Entertainment(
                        rs.getInt("serviceID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        sdf.format(timeOpen),
                        sdf.format(timeClose),
                        rs.getString("dayOfWeekOpen"),
                        rs.getFloat("ticketPrice")
                );
                list.add(ent);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Entertainment> getEntertainmentByType(String type) {
        String sql = "SELECT * FROM Entertainment WHERE type LIKE ?";
        List<Entertainment> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + type + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Time timeOpen = rs.getTime("timeOpen");
                Time timeClose = rs.getTime("timeClose");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Entertainment ent = new Entertainment(
                        rs.getInt("serviceID"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description"),
                        rs.getFloat("rate"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        sdf.format(timeOpen),
                        sdf.format(timeClose),
                        rs.getString("dayOfWeekOpen"),
                        rs.getFloat("ticketPrice")
                );
                list.add(ent);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
