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
 * The DetailRestaurant servlet handles HTTP GET requests to display detailed information
 * of a specific restaurant based on its service ID. It validates the ID parameter,
 * retrieves the restaurant details from RestaurantDAO, and forwards to the detail page.
 * All input data is trimmed to remove leading/trailing spaces before processing.
 * The servlet throws exceptions for invalid input or database errors to be handled
 * by the error page.
 *
 * <p>Bugs: None known at this time. Potential issue with large ID values.
 *
 * @author Hoang Tuan Dung
 */
package controller.agent.restaurant;

import dao.IRestaurantDAO;
import dao.RestaurantDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.Restaurant;

/**
 *
 * @author ad
 */
public class DetailRestaurant extends HttpServlet {

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
            out.println("<title>Servlet DetailRestaurant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailRestaurant at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP GET method to retrieve and display restaurant details.
     * Validates the service ID parameter, fetches the restaurant data using RestaurantDAO,
     * and forwards to the detail page. Handles exceptions by redirecting to an error page.
     *
     * @param request  the HttpServletRequest object containing the service ID parameter
     * @param response the HttpServletResponse object for sending response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize DAO
        IRestaurantDAO restaurantDao = new RestaurantDAO();

        try {
            // Retrieve and trim ID parameter
            String idParam = request.getParameter("id") != null ? request.getParameter("id").trim() : "";
            if (idParam.isEmpty()) {
                throw new IllegalArgumentException("Tham số mã dịch vụ thiếu hoặc trống.");
            }

            // Validate and parse ID
            int id = Integer.parseInt(idParam);
            if (id <= 0) {
                throw new IllegalArgumentException("Mã dịch vụ phải là số nguyên dương.");
            }

            // Retrieve restaurant details
            Restaurant restaurantDetail = restaurantDao.getRestaurantByServiceId(id);
            if (restaurantDetail == null) {
                throw new IllegalArgumentException("Không có nhà hàng với mã = " + id + ".");
            }

            // Set attribute and forward to detail page
            request.setAttribute("restaurantDetail", restaurantDetail);
            request.getRequestDispatcher("view/agent/restaurant/detailRestaurant.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Định dạng ID không hợp lệ.");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP POST method by delegating to the processRequest method.
     * Note: This servlet primarily uses GET for retrieving details; POST is not implemented.
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
