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

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.EmailSender;

/**
 * Handles resending OTP for user verification.<br>
 * Generates and sends a new OTP via email if the session contains a valid email.<br>
 * <p>Bugs: No rate limiting for OTP resend requests; potential email sending failures not handled robustly.</p>
 *
 * @author Hà Thị Duyên
 */
@WebServlet(name = "ResendOtp", urlPatterns = {"/ResendOtp"})
public class ResendOtp extends HttpServlet {

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
     * Generates a new OTP and sends it to the user's email.
     * Updates session with new OTP and expiry time.
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        //lấy email từ session (giả sử đa lưu email vào session từ trc dó)
        String gmail = (String) session.getAttribute("gmail");
        if (gmail == null) {
            response.sendRedirect(request.getContextPath() + "/view/user/verify-otp.jsp?error=Không tìm thấy email trong session!");
            return;
        }
        //Tạo otp mới
        int otp = (int) (Math.random() * 900000) + 100000;
        long expiryTime = System.currentTimeMillis() + 2 * 60 * 1000;
        session.setAttribute("otp", otp);
        session.setAttribute("otpExpiry", expiryTime);
        session.setAttribute("otpFails", 0);

        //Gửi gmail lại 
        try {
            EmailSender.send(gmail, "Mã OTP mới của bạn", "Mã OTP mới của bạn là: " + otp);
            request.setAttribute("message", "Mã OTP mới đã được gửi!");
            request.removeAttribute("error"); // Xóa thông báo lỗi cũ
            request.getRequestDispatcher("/view/user/verify-otp.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi gửi email. Vui lòng thử lại!");
            request.getRequestDispatcher("/view/user/verify-otp.jsp").forward(request, response);
        }

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
        processRequest(request, response);
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
