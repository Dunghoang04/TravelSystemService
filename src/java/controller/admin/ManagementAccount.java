/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelAgentService
 * Description: Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-23  1.0        Hà Thị Duyên      First implementation for admin profile
 */
package controller.admin;

import dao.IStaffDAO;
import dao.IUserDAO;
import dao.StaffDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Staff;
import model.User;

/**
 * Servlet for managing user accounts in the TravelAgentService system.
 * Handles listing of all user accounts for admin users, with session-based authentication.
 *
 * @author Hà Thị Duyên
 */
@WebServlet(name = "ManagementAccount", urlPatterns = {"/ManagementAccount"})
public class ManagementAccount extends HttpServlet {

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
     * Handles HTTP GET requests to list user accounts or redirect to login if not authenticated.
     * Checks for a valid session with a Gmail attribute and retrieves all users for display.
     *
     * @param request  The HttpServletRequest object containing session and service parameters
     * @param response The HttpServletResponse object for redirection or forwarding
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(); // Retrieve session
        String gmail = (String) session.getAttribute("gmail"); // Get Gmail from session for authentication
        if (gmail == null) {
            response.sendRedirect("LoginLogout?service=loginUser"); // Redirect to login if not authenticated
            return;
        }

        String service = request.getParameter("service"); // Get service parameter
        IUserDAO udao = new UserDAO(); // Initialize UserDAO for database operations

        // Handle listing of user accounts (default or explicit service)
        if (service == null || service.equals("listAccount")) {
            String sql = "SELECT * FROM [User]"; // SQL query to retrieve all users
            try {
                Vector<User> userList = udao.getAllUsers(sql); // Fetch user list from database
                request.setAttribute("userList", userList); // Store user list in request for JSP
                request.getRequestDispatcher("/view/admin/listAccount.jsp").forward(request, response); // Forward to list accounts JSP
            } catch (SQLException ex) {
                Logger.getLogger(ManagementAccount.class.getName()).log(Level.SEVERE, null, ex); // Log database error
                request.setAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách người dùng."); // Set error message
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response); // Forward to error page
            }
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