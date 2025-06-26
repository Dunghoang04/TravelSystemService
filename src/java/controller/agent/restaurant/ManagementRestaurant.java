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
 * Servlet for managing restaurant records in the agent module.
 * Handles HTTP GET requests to display and filter restaurant records with pagination.
 *
 * Project: TravelAgentService
 * Version: 1.0
 * Date: 2025-06-13
 * Bugs: No known issues.
 *
 * Record of Change:
 * DATE            Version             AUTHOR                      DESCRIPTION
 * 2025-06-13      1.0                 Hoang Tuan Dung             First implementation
 *
 * @author Hoang Tuan Dung
 */
/**
 * Servlet for managing restaurant records in the agent module.
 * Handles HTTP GET requests to display and filter restaurant records with pagination.
 *
 * Project: TravelAgentService
 * Version: 1.1
 * Date: 2025-06-21
 * Bugs: No known issues.
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
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import model.Restaurant;

/**
 *
 * @author ad
 */
public class ManagementRestaurant extends HttpServlet {

    // Number of items displayed per page
    private static final int ITEMS_PER_PAGE = 5;

    /**
     * Processes requests for both HTTP GET and POST methods with a sample HTML
     * response. Intended for testing purposes only and should be overridden by
     * specific handlers.
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
            out.println("<title>Servlet ManagementRestaurant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagementRestaurant at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP GET method to display and filter restaurant records with
     * pagination. Validates session, retrieves data based on searchName and
     * status filters, calculates pagination, and forwards to JSP.
     *
     * @param request servlet request containing filter parameters (searchName,
     * statusType, page)
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("gmail") == null) {
            response.sendRedirect("LoginLogout?service=loginUser");
            return;
        }

        // Initialize DAO and variables
        IRestaurantDAO restaurantDao = new RestaurantDAO();
        List<Restaurant> restaurantList;
        String searchName = request.getParameter("searchName") != null ? request.getParameter("searchName").trim() : "";
        String statusType = request.getParameter("statusType") != null ? request.getParameter("statusType").trim() : "";
        request.setAttribute("searchName", searchName);
        request.setAttribute("statusType", statusType);
        int amountPerPage = ITEMS_PER_PAGE;
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        try {
            int numberPage = 0;
            int startIndex = (currentPage - 1) * amountPerPage + 1;

            // Handle filtering and pagination
            if (!searchName.isEmpty() && !statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái phải là 0 hoặc 1.");
                }
                numberPage = (int) Math.ceil(restaurantDao.countByTypeAndName(status, searchName) / (double) amountPerPage);
                restaurantList = restaurantDao.searchByTypeAndName(status, searchName, currentPage, amountPerPage);
            } else if (!statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái phải là 0 hoặc 1.");
                }
                numberPage = (int) Math.ceil(restaurantDao.countByStatus(status) / (double) amountPerPage);
                restaurantList = restaurantDao.getRestaurantByStatus(status, currentPage, amountPerPage);
            } else if (!searchName.isEmpty()) {
                numberPage = (int) Math.ceil(restaurantDao.countByName(searchName) / (double) amountPerPage);
                restaurantList = restaurantDao.searchRestaurantByName(searchName, currentPage, amountPerPage);
            } else {
                numberPage = (int) Math.ceil(restaurantDao.countAllRestaurant() / (double) amountPerPage);
                restaurantList = restaurantDao.getListRestaurant(currentPage, amountPerPage);
            }

            // Validate pagination
            if (currentPage > numberPage && numberPage > 0) {
                currentPage = numberPage;
                startIndex = (currentPage - 1) * amountPerPage + 1;
            }
            if (startIndex < 1) {
                startIndex = 1;
            }

            // Set attributes for JSP
            request.setAttribute("startIndex", startIndex);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("restaurantList", restaurantList);
            request.getRequestDispatcher("view/agent/restaurant/agentRestaurant.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Giá trị trạng thái hoặc trang không hợp lệ.");
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP POST method. Currently not implemented.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP POST method. Currently not implemented.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
