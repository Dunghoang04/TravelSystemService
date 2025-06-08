package controller.agent.entertainment;

import dao.EntertainmentDAO;
import dao.IEntertainmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import model.Entertainment;

/**
 * Manages entertainment records in the agent module. Handles GET requests to
 * list and filter entertainment data with pagination, and delegates POST
 * requests.
 *
 * Project: TravelAgentService Version: 1.0 Date: 2025-06-07 Bugs: No known
 * issues.
 *
 * Record of Change: DATE Version AUTHOR DESCRIPTION 2025-06-07 1.0 Hoang Tuan
 * Dung First implementation 2025-06-08 1.1 Hoang Tuan Dung Enhanced pagination
 * and error handling
 *
 * @author Hoang Tuan Dung
 */
public class ManagementEntertainment extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 5; // Number of items per page

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
            out.println("<title>Servlet ManagementEntertainment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagementEntertainment at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP GET method to display and filter entertainment records
     * with pagination. Retrieves data based on searchName and status filters,
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
        List<Entertainment> entertainmentList;
        IEntertainmentDAO entertainmentDataAccess = new EntertainmentDAO();
        String searchName = request.getParameter("searchName") != null ? request.getParameter("searchName").trim() : "";
        String statusType = request.getParameter("statusType") != null ? request.getParameter("statusType").trim() : "";
        request.setAttribute("searchName", searchName);
        request.setAttribute("statusType", statusType);
        int amountPerPage = ITEMS_PER_PAGE;
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        try {
            if (!searchName.isEmpty() && !statusType.isEmpty()) {
                int status = Integer.parseInt(statusType); // Convert statusType to status (int)
                entertainmentList = entertainmentDataAccess.searchByTypeAndName(status, searchName, currentPage, amountPerPage);
                int numberPage = (int) Math.ceil(entertainmentDataAccess.countByTypeAndName(status, searchName) / (double) amountPerPage); // Calculate total pages
                int startIndex = (currentPage - 1) * amountPerPage + 1; // Calculate starting index
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            } else if (!statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                entertainmentList = entertainmentDataAccess.getEntertainmentByStatus(status, currentPage, amountPerPage);
                int numberPage = (int) Math.ceil(entertainmentDataAccess.countByStatus(status) / (double) amountPerPage); // Calculate total pages
                int startIndex = (currentPage - 1) * amountPerPage + 1; // Calculate starting index
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            } else if (!searchName.isEmpty()) {
                entertainmentList = entertainmentDataAccess.searchEntertainmentByName(searchName, currentPage, amountPerPage);
                int numberPage = (int) Math.ceil(entertainmentDataAccess.countByName(searchName) / (double) amountPerPage); // Calculate total pages
                int startIndex = (currentPage - 1) * amountPerPage + 1; // Calculate starting index
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            } else {
                int numberPage = (int) Math.ceil(entertainmentDataAccess.countByName(searchName) / (double) amountPerPage); // Calculate total pages
                int startIndex = (currentPage - 1) * amountPerPage + 1; // Calculate starting index
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("numberPage", numberPage);
                entertainmentList = entertainmentDataAccess.getListEntertainment(currentPage, amountPerPage);
            }
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("entertainmentList", entertainmentList);
        } catch (NumberFormatException numberFormatException) {
            request.setAttribute("error", "Invalid status value");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
            return;
        } catch (SQLException sqlException) {
            request.setAttribute("error", "Database error: " + sqlException.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("view/agent/entertainment/agentEntertainment.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method by delegating to the processRequest method.
     * Intended for future extension to handle form submissions if needed.
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
        return "Short description";
    }// </editor-fold>

}
