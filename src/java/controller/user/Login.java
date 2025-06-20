/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
 /*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên          First implementation
 */
package controller.user;

import dao.ITravelAgentDAO;
import dao.TravelAgentDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TravelAgent;
import model.User;
import service.EmailSender;
import dao.IUserDAO;

/**
 * Manages user login, logout, and password reset functionalities.<br>
 * Handles role-based redirection and email-based password recovery.<br>
 * <p>
 * Bugs: Password stored in plain text; no rate limiting for password reset
 * requests.</p>
 *
 * @author Hà Thị Duyên
 */
@WebServlet(name = "Login", urlPatterns = {"/LoginLogout"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Block comment to describe the method
    /* 
     * Handles login, logout, and password reset based on service parameter.
     * Redirects based on user role or handles password recovery via email.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        IUserDAO uDAO = new UserDAO();
        ITravelAgentDAO aDAO = new TravelAgentDAO();
        String service = request.getParameter("service");
        if (service == null) {
            // Hiển thị trang login nếu không có service
            request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
            return;
        }

        if (service.equals("loginUser")) {
            String gmail = request.getParameter("gmail");
            String password = request.getParameter("password");

            String error = validate(gmail, password);
            if (error != null) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
                return;
            }

            User u = uDAO.checkLogin(gmail, password);

            if (u != null) {
                session.setAttribute("loginUser", u);
                int role = u.getRoleID();
                session.setAttribute("gmail", gmail);
// Điều hướng theo vai trò
                switch (role) {
                    case 1:
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                        break;
                    case 2:
                        response.sendRedirect(request.getContextPath() + "/ManageTravelAgentRegister");
                        break;
                    case 3:
                        response.sendRedirect(request.getContextPath() + "/home");
                        break;
                    case 4:
                        TravelAgent a = aDAO.searchTravelAgentByGmail(gmail);
                        session.setAttribute("agent", a);
                        response.sendRedirect(request.getContextPath() + "/ManageTravelAgentProfile");
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/home");
                }
                return;
            } else {
                request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
                return;
            }
        }

        if (service.equals("logoutUser")) {
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        if (service.equals("forgetPassword")) {
            String gmail = request.getParameter("gmail");

            // Xác thực email cơ bản
            if (gmail == null || gmail.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập email!");
                request.getRequestDispatcher("/view/user/rePass.jsp").forward(request, response);
                return;
            }

            // Kiểm tra email tồn tại
            if (!uDAO.isGmailRegister(gmail)) {
                request.setAttribute("error", "Email không tồn tại!");
                request.getRequestDispatcher("/view/user/rePass.jsp").forward(request, response);
                return;
            }

            // Tạo liên kết đơn giản với email
            String resetLink = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + request.getContextPath() + "/view/user/resetPassword.jsp?gmail=" + gmail;

            // Gửi email với liên kết
            try {
                EmailSender.send(gmail, "Đặt lại mật khẩu",
                        "Nhấn vào liên kết để đặt lại mật khẩu: <a href='" + resetLink + "'>Đặt lại mật khẩu</a>");
                request.setAttribute("message", "Liên kết đặt lại đã được gửi đến email của bạn!");
            } catch (Exception e) {
                request.setAttribute("error", "Có lỗi khi gửi email. Vui lòng thử lại!");
                e.printStackTrace();
            }

            request.getRequestDispatcher("/view/user/rePass.jsp").forward(request, response);
            return;
        }

        if (service.equals("resetPassword")) {
            String gmail = request.getParameter("gmail");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Mật khẩu không khớp!");
                request.getRequestDispatcher("/view/user/resetPassword.jsp").forward(request, response);
                return;
            }
            // Kiểm tra email và mật khẩu mới
            if (gmail == null || newPassword == null || newPassword.trim().isEmpty()) {
                request.setAttribute("error", "Dữ liệu không hợp lệ!");
                request.getRequestDispatcher("/view/user/resetPassword.jsp").forward(request, response);
                return;
            }

            // Kiểm tra email tồn tại
            if (!uDAO.isGmailRegister(gmail)) {
                request.setAttribute("error", "Email không tồn tại!");
                request.getRequestDispatcher("/view/user/resetPassword.jsp").forward(request, response);
                return;
            }

            // Cập nhật mật khẩu mới
            uDAO.updatePassword(gmail, newPassword);
            request.setAttribute("message", "Mật khẩu đã được đặt lại thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
            return;
        }
    }

    /**
     * Validates login credentials.<br>
     *
     * @param gmail The user's email address
     * @param password The user's password
     * @return Error message if invalid, null if valid
     */

    // Block comment to describe the method
    /* 
     * Checks if email and password fields are filled.
     * Returns error message if any field is empty.
     */
    public String validate(String gmail, String password) {
        if (gmail == null || gmail.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin!";
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
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
