/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelAgentService
 * Description: Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24   1.1      Hà Thị Duyên      First implementation for admin profile
 */
package controller.admin;

import dao.IStaffDAO;
import dao.ITourDAO;
import dao.IUserDAO;
import dao.StaffDAO;
import dao.TourDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet for displaying statistical data for admin users in the TravelAgentService system.
 * Provides counts of users, travel agents, staff, tourists, and tours on the admin dashboard.
 * Requires session-based authentication to access.
 *
 * @author Nhat Anh
 */
@WebServlet(name = "StatisticalAdmin", urlPatterns = {"/StatisticalAdmin"})
public class StatisticalAdmin extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     * Authenticates the user via session and displays statistical data (user, travel agent, staff, tourist, and tour counts).
     * on the admin dashboard if authenticated.
     *
     * @param request  The HttpServletRequest object containing session and service parameters
     * @param response The HttpServletResponse object for redirection or forwarding
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set response content type
        HttpSession session = request.getSession(); // Retrieve session
        String gmail = (String) session.getAttribute("gmail"); // Get Gmail from session for authentication
        if (gmail == null) {
            response.sendRedirect("LoginLogout?service=loginUser"); // Redirect to login if not authenticated
            return;
        }

        String service = request.getParameter("service"); // Get service parameter
        IUserDAO udao = new UserDAO(); // Initialize UserDAO for user-related operations
        ITourDAO tourDAO = new TourDAO(); // Initialize TourDAO for tour-related operations
        try {
            // Handle dashboard statistics (default or explicit service)
            if (service == null || service.equals("dashboard")) {
                int totalUser = udao.countUser(); // Count total users
                int totalTravelAgents = udao.countUserByRoleID(4); // Count users with roleID = 4 (Travel Agents)
                int totalStaff = udao.countUserByRoleID(2); // Count users with roleID = 2 (Staff)
                int totalTourists = udao.countUserByRoleID(3); // Count users with roleID = 3 (Tourists)
                int totalTours = tourDAO.getTotalTours(); // Count total tours
                
                // Store statistics in request attributes for JSP
                request.setAttribute("totalUser", totalUser);
                request.setAttribute("totalTravelAgents", totalTravelAgents);
                request.setAttribute("totalStaff", totalStaff);
                request.setAttribute("totalTourists", totalTourists);
                request.setAttribute("totalTours", totalTours);
                request.getRequestDispatcher("/view/admin/admin.jsp").forward(request, response); // Forward to admin dashboard JSP
                return;
            }
            
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Unable to fetch statistics due to a database error."); // Set error message
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response); // Forward to error page
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        return "Short description"; // Brief servlet description
    }// </editor-fold>
}