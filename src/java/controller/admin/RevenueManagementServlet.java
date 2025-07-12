/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-03  1.0        Hà Thị Duyên      First implementation for revenue management
 * 2025-07-03  1.1        Grok              Removed pagination for DataTable integration
 */
package controller.admin;

import dao.BookTourDAO;
import dao.TourDAO;
import dao.TravelAgentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.BookDetail;
import model.Tour;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RevenueManagementServlet", urlPatterns = {"/RevenueManagementServlet"})
public class RevenueManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
      
        BookTourDAO bookTourDAO = new BookTourDAO();
        TourDAO tourDAO = new TourDAO();
        TravelAgentDAO trdao = new TravelAgentDAO();
        try {
            // Lấy toàn bộ danh sách đơn đặt (không lọc, không phân trang)
            ArrayList<BookDetail> bookList = bookTourDAO.getBookDetailsWithFilters(null, null, null);
            int totalBooks = bookTourDAO.getTotalBookDetailsWithFilters(null, null, null);

            // Tính tổng doanh thu (không lọc)
            double totalRevenue = bookTourDAO.getTotalRevenue(null, null, null);

            // Lấy danh sách tour để hiển thị thông tin
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
            request.setAttribute("totalBooks", totalBooks);

            request.getRequestDispatcher("view/admin/revenueManagement.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + ex.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Xử lý POST giống GET
    }

    @Override
    public String getServletInfo() {
        return "RevenueManagementServlet handles revenue reporting for admin with DataTable, no filters";
    }
}