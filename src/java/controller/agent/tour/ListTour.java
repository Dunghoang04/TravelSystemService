/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai         Refactored with ITourDAO, improved resource management and comments
 */
package controller.agent.tour;

import dao.ITourCategoryDAO;
import dao.ITourDAO;
import dao.ITourServiceDetailDAO;
import dao.ITravelAgentDAO;
import dao.TourCategoryDAO;
import dao.TourDAO;
import dao.TourServiceDetailDAO;
import dao.TravelAgentDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.Tour;
import model.TourServiceDetail;
import java.sql.SQLException;
import model.TourCategory;
import model.TravelAgent;

/**
 * Servlet for listing and managing tours specific to a travel agent.
 * Retrieves the travel agent's ID from the session's gmail and filters tours accordingly.
 *
 * @author Nhat Anh
 */
@WebServlet(name = "ListTour", urlPatterns = {"/ListTour"})
public class ListTour extends HttpServlet {

    private ITourDAO tourDAO = new TourDAO();
    private ITourServiceDetailDAO tourServiceDetailDAO = new TourServiceDetailDAO();
    private ITravelAgentDAO travelAgentDAO = new TravelAgentDAO();
    private ITourCategoryDAO tourCategoryDAO = new TourCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        String gmail = (String) session.getAttribute("gmail");

        if (gmail == null) {
            response.sendRedirect("LoginLogout?service=loginUser");
            return;
        }

        try {
            // Retrieve TravelAgent using gmail
            TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByGmail(gmail);
            if (travelAgent == null) {
                request.setAttribute("errorMessage", "Không tìm thấy TravelAgent với gmail: " + gmail);
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                return;
            }
            int travelAgentID = travelAgent.getTravelAgentID();

            if (service == null || (!"list".equals(service) && !"delete".equals(service) && !"viewTourDetail".equals(service))) {
                request.setAttribute("errorMessage", "Dịch vụ không hợp lệ!");
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            } else if ("list".equals(service)) {
                String status = request.getParameter("status");
                Vector<Tour> tours;
                if (status != null && !status.equals("all")) {
                    tours = tourDAO.searchTourByStatusAndAgent(travelAgentID, Integer.parseInt(status));
                } else {
                    tours = tourDAO.getAllToursByAgent(travelAgentID);
                }
                request.setAttribute("tours", tours);
                request.getRequestDispatcher("/view/agent/tour/agentTour.jsp").forward(request, response);
            } else if ("delete".equals(service)) {
                String tourIdStr = request.getParameter("tourId");
                if (tourIdStr == null || tourIdStr.trim().isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID tour không hợp lệ!");
                    return;
                }
                int tourId;
                try {
                    tourId = Integer.parseInt(tourIdStr);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID tour không phải là số hợp lệ!");
                    return;
                }
                // Verify that the tour belongs to the travel agent
                Tour tour = tourDAO.searchTourByID(tourId);
                if (tour == null || tour.getTravelAgentID() != travelAgentID) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền xóa tour này!");
                    return;
                }
                tourDAO.changeStatusTour(tourId, 0);
                response.setStatus(HttpServletResponse.SC_OK);
            } else if ("viewTourDetail".equals(service)) {
                int tourID = Integer.parseInt(request.getParameter("tourId"));
                Tour tour = tourDAO.searchTourByID(tourID);
                if (tour == null || tour.getTravelAgentID() != travelAgentID) {
                    request.setAttribute("errorMessage", "Không tìm thấy tour hoặc bạn không có quyền truy cập tour với ID: " + tourID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                // Lấy TourCategory
                int categoryID = tour.getTourCategoryID();
                TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                if (tourCategory == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy TourCategory với ID: " + categoryID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                // Đặt các thuộc tính vào request
                request.setAttribute("tour", tour);
                request.setAttribute("travelAgentName", travelAgent.getTravelAgentName());
                request.setAttribute("categoryName", tourCategory.getTourCategoryName());
                request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", "Phương thức không được hỗ trợ!");
        request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
    }
}