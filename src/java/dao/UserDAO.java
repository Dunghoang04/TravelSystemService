package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Vector;
import model.User;

/**
 *
 * @author ad
 */
public class UserDAO extends DBContext {

    public Vector<User> getAllUsers(String sql) {
        Vector<User> listUser = new Vector<>();
        try (PreparedStatement ptm = connection.prepareStatement(sql); ResultSet rs = ptm.executeQuery()) {
            while (rs.next()) {
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getDate(10), rs.getDate(11), rs.getInt(12), rs.getInt(13));
                listUser.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listUser;
    }

    public void insertUser(User u) {
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
        try (PreparedStatement ptm = connection.prepareStatement(sql)) {
            ptm.setString(1, u.getGmail());
            ptm.setString(2, u.getPassword());
            ptm.setString(3, u.getFirstName());
            ptm.setString(4, u.getLastName());
            ptm.setDate(5, u.getDob());
            ptm.setString(6, u.getGender());
            ptm.setString(7, u.getAddress());
            ptm.setString(8, u.getPhone());
            ptm.setInt(9, u.getStatus());
            ptm.setInt(10, u.getRoleID());
            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User u) {
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
        try (PreparedStatement ptm = connection.prepareStatement(sql)) {
            ptm.setString(1, u.getGmail());
            ptm.setString(2, u.getPassword());
            ptm.setString(3, u.getFirstName());
            ptm.setString(4, u.getLastName());
            ptm.setDate(5, u.getDob());
            ptm.setString(6, u.getGender());
            ptm.setString(7, u.getAddress());
            ptm.setString(8, u.getPhone());
            ptm.setInt(9, u.getStatus());
            ptm.setInt(10, u.getRoleID());
            ptm.setInt(11, u.getUserID());
            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isGmailRegister(String gmail) {
        String sql = "SELECT 1 FROM [User] WHERE gmail = ?";
        try (PreparedStatement ptm = connection.prepareStatement(sql)) {
            ptm.setString(1, gmail);
            ResultSet rs = ptm.executeQuery();
            return rs.next(); // nếu dòng nào khớp => gmail đã tồn tại 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User checkLogin(String gmail, String password) {
        String sql = "SELECT * FROM [User] WHERE gmail = ? AND password=? AND status = 1";
        try (PreparedStatement ptm = connection.prepareStatement(sql)) {
            ptm.setString(1, gmail);
            ptm.setString(2, password);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("userID"), rs.getString("gmail"), rs.getString("password"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getDate("dob"),
                        rs.getString("gender"), rs.getString("address"), rs.getString("phone"),
                        rs.getDate("create_at"), rs.getDate("update_at"), rs.getInt("status"),
                        rs.getInt("roleID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String sql = "SELECT * FROM [User]";
        UserDAO udao = new UserDAO();

        Vector<User> list = udao.getAllUsers(sql);

        list = udao.getAllUsers(sql);
        for (User user : list) {
            System.out.println(user);
        }

    }

}
