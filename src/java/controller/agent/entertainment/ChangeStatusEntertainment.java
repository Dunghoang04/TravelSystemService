/**
 * Manages the status change for a specific entertainment record in the agent module.<br>
 * Handles GET requests to toggle the status (active/inactive) of an entertainment record by service ID and redirects.<br>
 * 
 * Project: TravelAgentService
 * Version: 1.0
 * Date: 2025-06-07
 * Bugs: No known issues.
 * 
 * Record of Change:
 * DATE            Version             AUTHOR                               DESCRIPTION
 * 2025-06-07      1.0                 Hoang Tuan Dung                      First implementation
 * 2025-06-08      1.1                 Hoang Tuan Dung                      Initial Javadoc update and code review
 * 
 * @author Hoang Tuan Dung
 */
package controller.agent.entertainment;

import dao.EntertainmentDAO;
import dao.IEntertainmentDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ChangeStatusEntertainment extends HttpServlet {

    /**
     * Handles the HTTP GET method to toggle the status of a specific
     * entertainment record.<br>
     * Validates the service ID, retrieves the current status, updates it, and
     * redirects to the management page with the current page.<br>
     *
     * @param request servlet request containing the service ID and page
     * parameters<br>
     * @param response servlet response<br>
     * @throws ServletException if a servlet-specific error occurs<br>
     * @throws IOException if an I/O error occurs<br>
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IEntertainmentDAO entertainmentDataAccess = new EntertainmentDAO();
        String serviceIdParam = request.getParameter("id") != null ? request.getParameter("id").trim() : "";
        String currentPageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "1"; // Default to page 1

        // Validate service ID
        if (serviceIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã dịch vụ không thể để trống");
            return;
        }

        int serviceId;
        try {
            serviceId = Integer.parseInt(serviceIdParam);
            if (serviceId <= 0) {
                throw new NumberFormatException("Mã dịch vụ phải là số nguyên dương");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng mã dịch vụ không hợp lệt: " + e.getMessage());
            return;
        }

        int currentPage;
        try {
            currentPage = Integer.parseInt(currentPageParam);
            if (currentPage <= 0) {
                currentPage = 1;
            }
        } catch (NumberFormatException e) {
            currentPage = 1; 
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng số trang không hợp lệ: " + e.getMessage());
            return;
        }

        try {
            int currentStatus = entertainmentDataAccess.getStatusByServiceId(serviceId);
            int newStatus = (currentStatus == 1) ? 0 : 1;
            boolean isSuccess = entertainmentDataAccess.changeStatus(serviceId, newStatus);
            if (!isSuccess) {
                throw new SQLException("Không cập nhật được trạng thái cho mã dịch vụ: " + serviceId);
            }
            response.sendRedirect("managementertainment?page=" + currentPage);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP POST method with an error response.<br>
     * Indicates that the POST method is not supported for this servlet.<br>
     *
     * @param request servlet request<br>
     * @param response servlet response<br>
     * @throws ServletException if a servlet-specific error occurs<br>
     * @throws IOException if an I/O error occurs<br>
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Phương thức POST không được hỗ trợ cho servlet này");
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
