/*
 * Copyright(C) 2025, GROUP 6.
 * Restaurant Management System:
 *  A web application for managing restaurant information.
 *
 * Record of change:
 * DATE            Version    AUTHOR            DESCRIPTION
 * 2025-06-21      1.0        Hoang Tuan Dung   Initial implementation
 */

/**
 * The ChangeStatusRestaurant servlet handles HTTP GET requests to change the status
 * of a restaurant (e.g., active/inactive) in the system. It validates the service ID
 * and current page parameters, retrieves the current status from RestaurantDAO,
 * toggles the status, and redirects to the restaurant management page. All input
 * data is trimmed to remove leading/trailing spaces before processing. The servlet
 * throws exceptions for database or invalid input errors to be handled by the error page.
 *
 * <p>Bugs: None known at this time. Potential issue with page parameter validation.
 *
 * @author Hoang Tuan Dung
 */
package controller.agent.restaurant;

import dao.EntertainmentDAO;
import dao.IRestaurantDAO;
import dao.RestaurantDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * @author ad
 */
public class ChangeStatusRestaurant extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangeStatus</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeStatus at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP GET method to change the status of a restaurant.
     * Validates the service ID and page parameters, toggles the status
     * using RestaurantDAO, and redirects to the restaurant management page.
     *
     * @param request  the HttpServletRequest object containing query parameters
     * @param response the HttpServletResponse object for sending response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize DAO
        IRestaurantDAO restaurantDao = new RestaurantDAO();

        // Retrieve and trim parameters
        String serviceIdParam = request.getParameter("id") != null ? request.getParameter("id").trim() : "";
        String currentPageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "";

        // Validate service ID
        if (serviceIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã dịch vụ không thể để trống.");
            return;
        }
        int serviceId;
        try {
            serviceId = Integer.parseInt(serviceIdParam);
            if (serviceId <= 0) {
                throw new NumberFormatException("Mã dịch vụ phải là số nguyên dương.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng mã dịch vụ không hợp lệ: " + e.getMessage());
            return;
        }

        // Validate and set current page
        int currentPage;
        try {
            currentPage = Integer.parseInt(currentPageParam);
            if (currentPage <= 0) {
                currentPage = 1;
            }
        } catch (NumberFormatException e) {
            currentPage = 1; // Default to page 1 if invalid
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng số trang không hợp lệ: " + e.getMessage());
            return;
        }

        try {
            // Check if service ID exists
            if (restaurantDao.getStatusByServiceID(serviceId) == -1) { // Assuming -1 indicates non-existent ID
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Mã dịch vụ không tồn tại.");
                return;
            }

            // Toggle status (1 -> 0 or 0 -> 1)
            int currentStatus = restaurantDao.getStatusByServiceID(serviceId);
            int newStatus = (currentStatus == 1 ? 0 : 1);
            boolean success = restaurantDao.changeStatus(serviceId, newStatus);
            if (!success) {
                throw new SQLException("Không thể cập nhật trạng thái cho mã nhà hàng: " + serviceId);
            }

            // Set success message and redirect
            request.setAttribute("success", "Cập nhật trạng thái thành công.");
            response.sendRedirect("managerestaurant?page=" + currentPage);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP POST method by delegating to the processRequest method.
     * Note: This servlet primarily uses GET for status changes; POST is not implemented.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
