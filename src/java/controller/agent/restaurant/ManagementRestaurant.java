/**
 * Servlet for managing restaurant records in the agent module.
 * Handles HTTP GET requests to display restaurant records for the logged-in travel agent with pagination.
 *
 * Project: TravelAgentService
 * Version: 1.3
 * Date: 2025-07-14
 * Bugs: No known issues.
 *
 * Record of Change:
 * DATE            Version             AUTHOR                      DESCRIPTION
 * 2025-06-13      1.0                 Hoang Tuan Dung             First implementation
 * 2025-06-21      1.1                 Hoang Tuan Dung             Added pagination and filtering
 * 2025-07-14      1.2                 Hoang Tuan Dung             Filter restaurants by agent ID
 * 2025-07-14      1.3                 Hoang Tuan Dung             Use TravelAgent object and fix JSP path
 *
 * @author Hoang Tuan Dung, Grok
 */
package controller.agent.restaurant;

import dao.IRestaurantDAO;
import dao.RestaurantDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import model.Restaurant;
import model.TravelAgent;

/**
 * Manages restaurant records for a specific travel agent.
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
        try (var out = response.getWriter()) {
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
     * Handles the HTTP GET method to display restaurant records for the logged-in agent with pagination.
     * Retrieves agent ID from the TravelAgent object in session, filters restaurants by agent ID,
     * and supports search and status filters.
     *
     * @param request servlet request containing filter parameters (searchName, statusType, page)
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check session and agent
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("gmail") == null) {
            response.sendRedirect("LoginLogout?service=loginUser");
            return;
        }
        TravelAgent travelAgent = (TravelAgent) session.getAttribute("agent");
        if (travelAgent == null) {
            request.setAttribute("error", "No travel agent session found");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
            return;
        }
        int travelAgentId = travelAgent.getTravelAgentID();

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
            int numberPage;
            int startIndex = (currentPage - 1) * amountPerPage + 1;

            // Handle filtering and pagination by agent ID
            if (!searchName.isEmpty() && !statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái phải là 0 hoặc 1.");
                }
                numberPage = (int) Math.ceil(restaurantDao.countByAgentTypeAndName(travelAgentId, status, searchName) / (double) amountPerPage);
                restaurantList = restaurantDao.searchByAgentTypeAndName(travelAgentId, status, searchName, currentPage, amountPerPage);
            } else if (!statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái phải là 0 hoặc 1.");
                }
                numberPage = (int) Math.ceil(restaurantDao.countByAgentAndStatus(travelAgentId, status) / (double) amountPerPage);
                restaurantList = restaurantDao.getRestaurantByAgentAndStatus(travelAgentId, status, currentPage, amountPerPage);
            } else if (!searchName.isEmpty()) {
                numberPage = (int) Math.ceil(restaurantDao.countByAgentAndName(travelAgentId, searchName) / (double) amountPerPage);
                restaurantList = restaurantDao.searchRestaurantByAgentAndName(travelAgentId, searchName, currentPage, amountPerPage);
            } else {
                numberPage = (int) Math.ceil(restaurantDao.countByAgent(travelAgentId) / (double) amountPerPage);
                restaurantList = restaurantDao.getListRestaurantByAgent(travelAgentId, currentPage, amountPerPage);
            }

            // Validate pagination
            if (currentPage > numberPage && numberPage > 0) {
                currentPage = numberPage;
                startIndex = (currentPage - 1) * amountPerPage + 1;
            }
            if (startIndex < 1) {
                startIndex = 1;
            }
            if (restaurantList == null || restaurantList.isEmpty()) {
                request.setAttribute("error", "Không có nhà hàng nào thuộc quyền quản lý của bạn.");
                request.getRequestDispatcher("view/agent/restaurant/agentRestaurant.jsp").forward(request, response);
                return;
            }
            // Set attributes for JSP
            request.setAttribute("startIndex", startIndex);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("restaurantList", restaurantList);
            request.getRequestDispatcher("view/agent/restaurant/agentRestaurant.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Giá trị trạng thái hoặc trang không hợp lệ.");
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
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
        return "Servlet for managing restaurant records for a specific travel agent.";
    }
}
