/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelAgentService
 * Description: Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24   1.1      Hà Thị Duyên      First implementation
 */
package controller.admin;

import dao.StaffDAO;
import dao.TravelAgentDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import java.sql.SQLException;
import model.Staff;
import model.TravelAgent;

/**
 * Servlet for viewing detailed account information for a specific user in the TravelAgentService system.
 * Retrieves user details by userID and additional role-specific data (Staff or Travel Agent) for display.
 *
 * @author Nhat Anh
 */
@WebServlet(name="ViewDetailAccount", urlPatterns={"/ViewDetailAccount"})
public class ViewDetailAccount extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP GET and POST methods.
     * Currently sets the response content type but does not handle specific logic.
     *
     * @param request  The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object for sending responses
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set response content type
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles HTTP GET requests to retrieve and display detailed account information.
     * Fetches user details by userID and role-specific data (Staff or Travel Agent) if applicable.
     *
     * @param request  The HttpServletRequest object containing userID parameter
     * @param response The HttpServletResponse object for forwarding or error responses
     * @throws ServletException If a servlet-specific error occurs, including database errors
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Get userID from request parameter
            String userIDStr = request.getParameter("userID");
            if (userIDStr == null || userIDStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing userID parameter"); // Validate userID presence
                return;
            }

            int userID;
            try {
                userID = Integer.parseInt(userIDStr); // Parse userID to integer
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid userID format"); // Handle invalid userID format
                return;
            }

            // Initialize DAOs for database operations
            UserDAO userDAO = new UserDAO();
            StaffDAO staffDAO = new StaffDAO();
            TravelAgentDAO travelAgentDAO = new TravelAgentDAO();

            // Fetch user details using a prepared statement
            String sql = "SELECT * FROM [User] WHERE userID = ?";
            User user = null;
            try (java.sql.Connection conn = userDAO.getConnection();
                 java.sql.PreparedStatement ptm = conn.prepareStatement(sql)) {
                ptm.setInt(1, userID); // Set userID parameter to prevent SQL injection
                try (java.sql.ResultSet rs = ptm.executeQuery()) {
                    if (rs.next()) {
                        // Create User object from result set
                        user = new User(
                                rs.getInt("userID"),
                                rs.getString("gmail"),
                                rs.getString("password"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getDate("dob"),
                                rs.getString("gender"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getDate("create_at"),
                                rs.getDate("update_at"),
                                rs.getInt("status"),
                                rs.getInt("roleID")
                        );
                    }
                }
            } catch (SQLException e) {
                throw new ServletException("Error retrieving user details: " + e.getMessage(), e); // Wrap SQL error in ServletException
            }

            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found"); // Handle case where user is not found
                return;
            }

            // Store user object in request scope for JSP
            request.setAttribute("user", user);

            // Fetch additional details based on user role
            if (user.getRoleID() == 2) { // Staff role
                try {
                    Staff staff = staffDAO.getStaffByUserID(userID); // Retrieve staff details
                    request.setAttribute("staff", staff); // Store staff object in request scope
                } catch (SQLException e) {
                    throw new ServletException("Error retrieving staff details: " + e.getMessage(), e); // Wrap SQL error in ServletException
                }
            } else if (user.getRoleID() == 4) { // Travel Agent role
                try {
                    TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByGmail(user.getGmail()); // Retrieve travel agent details
                    request.setAttribute("travelAgent", travelAgent); // Store travel agent object in request scope
                } catch (SQLException e) {
                    throw new ServletException("Error retrieving travel agent details: " + e.getMessage(), e); // Wrap SQL error in ServletException
                }
            }

            // Forward to JSP for rendering account details
            request.getRequestDispatcher("/view/admin/viewDetail.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Unexpected error occurred: " + e.getMessage(), e); // Handle unexpected errors
        }
    } 

    /** 
     * Handles HTTP POST requests by delegating to processRequest.
     * Currently, no specific POST logic is implemented.
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
        return "Short description"; // Brief servlet description
    }// </editor-fold>
}