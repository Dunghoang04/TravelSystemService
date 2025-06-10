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
 * 2025-06-07  1.0        Hà Thị Duyên          First implementation
 */
/**
 * Defines the interface for Role data access operations.<br>
 * Provides methods to retrieve role data from the database.<br>
 * <p>
 * Bugs: None known at this time.</p>
 *
 * @author Hà Thị Duyên
 */
package dao;

import java.util.Vector;
import model.Role;
import java.sql.SQLException;

public interface IRoleDAO {

    /**
     * Retrieves all roles from the database based on the provided SQL
     * query.<br>
     *
     * @param sql The SQL query to execute
     * @return A Vector containing all Role objects retrieved
     * @throws SQLException If a database access error occurs
     */

    // Block comment to describe the method
    /* 
     * Declares the method to fetch all roles using a custom SQL query.
     * Must be implemented by the concrete class.
     */
    Vector<Role> getAllRole(String sql) throws SQLException;

}
