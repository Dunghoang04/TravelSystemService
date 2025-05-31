/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.Role;

/**
 *
 * @author Nhat Anh
 */
public class RoleDAO extends DBContext {

    public Vector<Role> getAllRole(String sql) {
        Vector<Role> listRole = new Vector<>();
        try (PreparedStatement ptm = connection.prepareStatement(sql); ResultSet rs = ptm.executeQuery()) {
            while (rs.next()) {
                Role r = new Role(rs.getInt(1), rs.getString(2));
                listRole.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listRole;
    }

    public static void main(String[] args) {
        String sql = "SELECT * FROM Role";
        RoleDAO rdao = new RoleDAO();
        // Lấy danh sách roles
        Vector<Role> list = rdao.getAllRole(sql);

        list = rdao.getAllRole(sql);

        for (Role role : list) {
            System.out.println(role);
        }
    }

    
}

