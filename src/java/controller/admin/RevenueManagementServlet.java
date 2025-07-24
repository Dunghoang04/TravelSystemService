/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-03  1.0        Hà Thị Duyên      First implementation for revenue management
 * 2025-07-22  1.1        [Your Name]       Added total VAT calculation for tax reporting
 */
package controller.admin;

import dao.BookTourDAO;
import dao.TourDAO;
import dao.TravelAgentDAO;
import dao.VATDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.BookDetail;
import model.Tour;
import model.VAT;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RevenueManagementServlet", urlPatterns = {"/RevenueManagementServlet"})
public class RevenueManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("LoginLogout");
            return;
        }

        BookTourDAO bookTourDAO = new BookTourDAO();
        TourDAO tourDAO = new TourDAO();
        VATDAO vatDAO = new VATDAO();

        try {
            // Lấy tham số startDate và endDate từ request
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            java.sql.Date startDate = null;
            java.sql.Date endDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = new java.sql.Date(sdf.parse(startDateStr).getTime());
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = new java.sql.Date(sdf.parse(endDateStr).getTime());
            }

            // Lấy danh sách đơn đặt với trạng thái "Đã hoàn thành" (status = 4) và khoảng thời gian
            ArrayList<BookDetail> bookList = bookTourDAO.getBookDetailsWithFiltersAndDate(null, 4, null, startDate, endDate);
            int totalBooks = bookTourDAO.getTotalBookDetailsWithFiltersAndDate(null, 4, null, startDate, endDate);

            // Tính tổng doanh thu (không bao gồm VAT) cho trạng thái "Đã hoàn thành"
            double totalRevenue = bookTourDAO.getTotalRevenueByStatus(4); // Cần cập nhật để hỗ trợ lọc theo ngày nếu muốn

            // Lấy VAT hiện hành
            VAT vat = vatDAO.getVATActive();
            double vatRate = (vat != null) ? vat.getVatRate() / 100.0 : 0.0;

            // Tính tổng VAT cần nộp
            double totalVAT = totalRevenue * vatRate / (1 + vatRate);

            // Lấy danh sách tên tour
            Map<Integer, String> tourNames = new HashMap<>();
            for (BookDetail book : bookList) {
                Tour tour = tourDAO.searchTourByID(book.getTourID());
                if (tour != null) {
                    tourNames.put(book.getTourID(), tour.getTourName());
                }
            }

            // Gửi dữ liệu về JSP
            request.setAttribute("bookList", bookList);
            request.setAttribute("tourNames", tourNames);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalVAT", totalVAT);
            request.setAttribute("vatRate", vatRate * 100);
            request.setAttribute("totalBooks", totalBooks);
            request.setAttribute("startDate", startDateStr);
            request.setAttribute("endDate", endDateStr);

            request.getRequestDispatcher("view/admin/revenueManagement.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + ex.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Lỗi: " + ex.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "RevenueManagementServlet handles revenue and VAT reporting for admin with DataTable, for completed bookings";
    }
}
