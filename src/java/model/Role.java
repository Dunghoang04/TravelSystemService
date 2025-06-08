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
 * Represents a role in the system, containing a unique identifier and name.<br>
 * This class is used to define user roles for access control.<br>
 * <p>
 * Bugs: None known at this time.</p>
 *
 * @author Hà Thị Duyên
 */
package model;

public class Role {

    private int roleID;
    private String roleName;

    /**
     * Default constructor for Role.<br>
     * Creates an empty Role object with default values.
     */
    // Block comment to describe the method
    /* 
     * Initializes a new Role instance with default values.
     * roleID is set to 0 and roleName is set to null.
     */
    public Role() {
    }

    /**
     * Constructs a Role with specified ID and name.<br>
     *
     * @param roleID The unique identifier for the role
     * @param roleName The name of the role, must not be null or empty
     * @throws IllegalArgumentException If roleName is invalid
     */
    // Block comment to describe the method
    /* 
     * Creates a new Role instance with the given ID and name.
     * Assigns values to roleID and roleName directly.
     */
    public Role(int roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = (roleName == null || roleName.trim().isEmpty()) ? "" : roleName;
    }

    /**
     * Gets the role's unique identifier.<br>
     *
     * @return The role ID as an integer
     */
    // Block comment to describe the method
    /* 
     * Retrieves the unique identifier of the role.
     * Returns the current value of roleID.
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * Sets the role's unique identifier.<br>
     *
     * @param roleID The role ID to set
     */
    // Block comment to describe the method
    /* 
     * Updates the unique identifier of the role.
     * Assigns the provided value to roleID.
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    /**
     * Gets the role's name.<br>
     *
     * @return The role name as a String
     */
    // Block comment to describe the method
    /* 
     * Retrieves the name of the role.
     * Returns the current value of roleName.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the role's name.<br>
     *
     * @param roleName The role name to set, must not be null or empty
     */
    // Block comment to describe the method
    /* 
     * Updates the name of the role.
     * Assigns the provided value to roleName.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Returns a string representation of the Role object.<br>
     *
     * @return A string containing roleID and roleName
     */
    // Block comment to describe the method
    /* 
     * Generates a string representation of the Role object.
     * Includes roleID and roleName in the format "Role{roleID=x, roleName=y}".
     */
    @Override
    public String toString() {
        return "Role{" + "roleID=" + roleID + ", roleName=" + roleName + '}';
    }
}
