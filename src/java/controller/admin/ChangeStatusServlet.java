/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelSystemService
 * Description: Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24   1.1      Hà Thị Duyên       First implementation
 * [Not specified in original code]
 */
package controller.admin;

import dao.StaffDAO;
import dao.TravelAgentDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet to handle user status change requests.
 * Updates the status of a user, staff, or travel agent based on userID.
 * 
 * @author Group 6
 */
@WebServlet(name = "ChangeStatusServlet", urlPatterns = {"/ChangeStatusServlet"})
public class ChangeStatusServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     * Retrieves userID from request parameters, toggles the user status, and updates
     * related tables (User, Staff, TravelAgent) accordingly.
     * 
     * @param request  The HttpServletRequest object containing userID parameter
     * @param response The HttpServletResponse object for redirection
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set response content type
        
        try {
            // Retrieve userID from request parameter
            String userIDParam = request.getParameter("userID");
            if (userIDParam == null || userIDParam.isEmpty()) {
                throw new IllegalArgumentException("User ID is required."); // Validate userID presence
            }
            
            int userID = Integer.parseInt(userIDParam); // Parse userID to integer
            UserDAO userDAO = new UserDAO(); // Initialize UserDAO for user operations
            StaffDAO staffDAO = new StaffDAO(); // Initialize StaffDAO for staff operations
            TravelAgentDAO travelAgentDAO = new TravelAgentDAO(); // Initialize TravelAgentDAO for travel agent operations
            
            // Get the current user to determine role and status
            model.User user = userDAO.getUserByID(userID);
            if (user == null) {
                throw new SQLException("User not found with ID: " + userID); // Check if user exists
            }
            
            // Determine new status (toggle between 0 and 1)
            int currentStatus = user.getStatus();
            int newStatus = (currentStatus == 1) ? 0 : 1; // Toggle status: active (1) to inactive (0) or vice versa
            
            // Prevent status change for Admin (roleID = 1) or TravelAgent in pending/rejected state (status 2 or 3)
            if (user.getRoleID() == 1 || (user.getRoleID() == 4 && (currentStatus == 2 || currentStatus == 3))) {
                request.setAttribute("error", "Cannot change status for Admin or pending/rejected Travel Agent."); // Set error message
                request.getRequestDispatcher("/ManagementAccount?service=listAccount").forward(request, response); // Forward to account list
                return;
            }
            
            // Update status based on user role
            if (user.getRoleID() == 2) { // Staff role
                model.Staff staff = staffDAO.getStaffByUserID(userID); // Retrieve staff record
                if (staff != null) {
                    staffDAO.changeStatus(staff.getStaffID(), newStatus); // Update staff status
                }
            } else if (user.getRoleID() == 4) { // Travel Agent role
                model.TravelAgent agent = travelAgentDAO.searchTravelAgentByGmail(user.getGmail()); // Retrieve travel agent record
                if (agent != null) {
                    travelAgentDAO.changeStatusTravelAgent(userID, newStatus); // Update travel agent status
                }
            } else { // Other roles (e.g., Tourist)
                userDAO.changeStatus(userID, newStatus); // Update user status directly
            }
            
            // Set success message and forward to account list
            request.getSession().setAttribute("success", "User status updated successfully.");
            request.getRequestDispatcher("/ManagementAccount?service=listAccount").forward(request, response);
            
        } catch (IllegalArgumentException e) {
            Logger.getLogger(ChangeStatusServlet.class.getName()).log(Level.SEVERE, "Invalid input: " + e.getMessage(), e); // Log invalid input error
            request.setAttribute("error", e.getMessage()); // Set error message
            request.getRequestDispatcher("/ManagementAccount?service=listAccount").forward(request, response); // Forward to account list
        } catch (SQLException e) {
            Logger.getLogger(ChangeStatusServlet.class.getName()).log(Level.SEVERE, "Database error: " + e.getMessage(), e); // Log database error
            request.setAttribute("error", "Failed to update user status due to database error."); // Set error message
            request.getRequestDispatcher("/ManagementAccount?service=listAccount").forward(request, response); // Forward to account list
        } catch (Exception e) {
            Logger.getLogger(ChangeStatusServlet.class.getName()).log(Level.SEVERE, "Unexpected error: " + e.getMessage(), e); // Log unexpected error
            request.setAttribute("error", "An unexpected error occurred."); // Set error message
            request.getRequestDispatcher("/ManagementAccount?service=listAccount").forward(request, response); // Forward to account list
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods">
    /**
     * Handles HTTP GET requests by delegating to processRequest.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Delegate to processRequest
    }

    /**
     * Handles HTTP POST requests by delegating to processRequest.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Delegate to processRequest
    }

    /**
     * Returns a brief description of the servlet.
     *
     * @return A string describing the servlet's functionality
     */
    @Override
    public String getServletInfo() {
        return "ChangeStatusServlet for toggling user status in TravelSystemService";
    }
    // </editor-fold>
}