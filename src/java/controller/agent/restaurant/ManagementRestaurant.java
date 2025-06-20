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
     * pagination. Retrieves data based on searchName and status filters,
     * calculates pagination, and forwards to JSP.
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("gmail") == null) {
            response.sendRedirect("LoginLogout?service=loginUser");
            return;
        }
        List<Restaurant> restaurantList;
        IRestaurantDAO restaurantDAO = new RestaurantDAO();
        String searchName = request.getParameter("searchName") != null ? request.getParameter("searchName").trim() : "";
        String statusType = request.getParameter("statusType") != null ? request.getParameter("statusType").trim() : "";
        request.setAttribute("searchName", searchName);
        request.setAttribute("statusType", statusType);
        int amountPerPage = ITEMS_PER_PAGE;
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        try {
            if (!searchName.isEmpty() && !statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                int startIndex=(currentPage-1)*amountPerPage+1;
                int numberPage=(int)Math.ceil(restaurantDAO.countByTypeAndName(status, searchName)/(double)amountPerPage);
                restaurantList = restaurantDAO.searchByTypeAndName(status, searchName, currentPage, amountPerPage);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("numberPage", numberPage);
            } else if (!statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                int startIndex=(currentPage-1)*amountPerPage+1;
                int numberPage=(int)Math.ceil(restaurantDAO.countByStatus(status)/(double)amountPerPage);
                restaurantList = restaurantDAO.getRestaurantByStatus(status, currentPage, amountPerPage);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("numberPage", numberPage);
            } else if (!searchName.isEmpty()) {
                restaurantList = restaurantDAO.searchRestaurantByName(searchName, currentPage, amountPerPage);
                int numberPage = (int) Math.ceil(restaurantDAO.countByName(searchName) / (double) amountPerPage);
                int startIndex = (currentPage - 1) * amountPerPage + 1;
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            } else {
                restaurantList = restaurantDAO.getListRestaurant(currentPage, amountPerPage);
                int numberPage = (int) Math.ceil(restaurantDAO.countAllRestaurant()/ (double) amountPerPage);
                int startIndex = (currentPage - 1) * amountPerPage + 1;
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            }
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("restaurantList", restaurantList);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid status value");
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
            return;
        }catch (SQLException sqlException) {
            request.setAttribute("error", "Database error: " + sqlException.getMessage());
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("view/agent/restaurant/agentRestaurant.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
