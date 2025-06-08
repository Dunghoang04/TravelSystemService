/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * Implements data access operations for Role entities.<br>
 * Handles retrieval of role data from the database using SQL queries.<br>
 * <p>
 * Bugs: Potential SQL injection risk if user input is used directly in SQL
 * queries.</p>
 *
 * @author Hà Thị Duyên
 */
package dao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.Role;

/**
 *
 * @author Nhat Anh
 */
public class RoleDAO extends DBContext implements IRoleDAO {

    /**
     * Main method for testing the getAllRole method.<br>
     * Executes a sample query to retrieve and print all roles.
     *
     * @param args Command-line arguments (not used)
     */
    // Block comment to describe the method
    /* 
     * Tests the getAllRole method with a default SQL query.
     * Prints each Role object to the console or handles SQLException.
     */
    @Override
    public Vector<Role> getAllRole(String sql) throws SQLException {
        Vector<Role> listRole = new Vector<>();
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            rs = ptm.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getInt(1), rs.getString(2));
                listRole.add(r);
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
        return listRole;
    }

    /**
     * Main method for testing the getAllRole method.<br>
     * Executes a sample query to retrieve and print all roles.
     *
     * @param args Command-line arguments (not used)
     */
    // Block comment to describe the method
    /* 
     * Tests the getAllRole method with a default SQL query.
     * Prints each Role object to the console or handles SQLException.
     */
    public static void main(String[] args) {
        String sql = "SELECT * FROM Role";
        RoleDAO rdao = new RoleDAO();
        // Lấy danh sách roles

        try {
            Vector<Role> list = rdao.getAllRole(sql);
            for (Role role : list) {
                System.out.println(role);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

    }

}
