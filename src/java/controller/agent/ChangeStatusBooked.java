/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelSystemService
 * Description: Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-24   1.1      Hoang Tuan Dung       First implementation
 * [Not specified in original code]
 */

package controller.agent;

import dao.BookTourDAO;
import dao.IBookTour;
import dao.IRequestCancel;
import dao.RequestCancelDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import model.User;

@WebServlet(name = "ChangeStatusBooked", urlPatterns = {"/ChangeStatusBooked"})
public class ChangeStatusBooked extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET không được hỗ trợ");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IBookTour bookTour = new BookTourDAO();
        IRequestCancel cancel = new RequestCancelDAO();
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("LoginLogout");
            return;
        }
        String bookIdParam = request.getParameter("bookID");
        String reason = request.getParameter("reason");
        String pageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "1";

        try {
            // Kiểm tra dữ liệu
            if (bookIdParam == null ||  reason == null
                    || bookIdParam.trim().isEmpty() ||reason.trim().isEmpty()) {
                response.sendRedirect("ManageTourBooked?page=" + pageParam + "&cancel=false");
                return;
            }

            int bookId = Integer.parseInt(bookIdParam.trim());
            int page = Integer.parseInt(pageParam.trim());

            if (bookId <= 0 ||  page <= 0 || reason.trim().length() < 10) {
                response.sendRedirect("ManageTourBooked?page=" + page + "&cancel=false");
                return;
            }

            // Cập nhật trạng thái đặt tour sang đã huỷ (status = 3)
            bookTour.updateStatus(bookId, 2);
            cancel.saveCancelRequest(bookId, loginUser.getUserID(), reason);
            response.sendRedirect("ManageTourBooked?page=" + page + "&cancel=true");
        } catch (NumberFormatException e) {
            response.sendRedirect("ManageTourBooked?page=" + pageParam + "&cancel=false");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("ManageTourBooked?page=" + pageParam + "&cancel=false");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý hủy chuyến đi từ agent và lưu lý do hủy";
    }
}
