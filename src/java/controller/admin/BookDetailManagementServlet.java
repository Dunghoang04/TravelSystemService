/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-03  1.0        Hà Thị Duyên      First implementation for book detail management
 */
package controller.admin;

import dao.BookTourDAO;
import dao.TourDAO;
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

@WebServlet(name = "BookDetailManagementServlet", urlPatterns = {"/BookDetailManagement"})
public class BookDetailManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Lấy tham số bookId và tourId
        String bookIdParam = request.getParameter("bookId");
        String tourIdParam = request.getParameter("tourId");

        try {
            int bookId = Integer.parseInt(bookIdParam);
            int tourId = Integer.parseInt(tourIdParam);

            // Lấy thông tin đơn đặt
            BookTourDAO bookTourDAO = new BookTourDAO();
            BookDetail bookDetail = bookTourDAO.getBookDetailById(bookId);

            // Lấy thông tin tour
            TourDAO tourDAO = new TourDAO();
            Tour tour = tourDAO.searchTourByID(tourId);

            if (bookDetail == null || tour == null) {
                request.setAttribute("error", "Không tìm thấy thông tin đơn đặt hoặc tour.");
                request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
                return;
            }

            // Gửi dữ liệu về JSP
            request.setAttribute("bookDetail", bookDetail);
            request.setAttribute("tour", tour);
            request.getRequestDispatcher("view/admin/bookDetailManagement.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID đơn đặt hoặc tour không hợp lệ.");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
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
        return "BookDetailManagementServlet handles detailed view of booking for admin";
    }
}