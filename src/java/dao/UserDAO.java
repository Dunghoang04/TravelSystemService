/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.User;

/**
 *
 * @author Hung
 */
public class UserDAO {

    private Connection conn;

    public UserDAO() {
        DBContext con = new DBContext();
        this.conn = con.getConnection();
    }

    public ArrayList<User> getStaffAccount() {
        String sql = "Select * from [User] u where u.roleID in(1,2); ";
        ArrayList<User> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt(1));
                user.setGmail((rs.getString(2)));
                user.setPassword(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setDob(rs.getDate(6));
                user.setGender(rs.getString(7));
                user.setAddress(rs.getString(8));
                user.setPhone(rs.getString(9));
                user.setCreateDate(rs.getDate(10));
                user.setUpdateDate((rs.getDate(11)));
                user.setStatus(rs.getInt(12));
                user.setRoleID(rs.getInt(13));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addStaff(String email, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, int status, int roleId) {
        String sql = "INSERT INTO [User] (gmail, password, firstName, lastName, dob, gender, address, phone, status, roleID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.setDate(5, new java.sql.Date(dob.getTime())); 
            stmt.setString(6, gender);
            stmt.setString(7, address);
            stmt.setString(8, phone);
            stmt.setInt(9, status);
            stmt.setInt(10, roleId);
            
            int check = stmt.executeUpdate();
            return check>0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        ArrayList<User> listUser = userDAO.getStaffAccount();
        for (User user : listUser) {
            System.out.println(user);
        }
    }
}
