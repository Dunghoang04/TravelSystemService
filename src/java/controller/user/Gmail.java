/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import service.EmailSender;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "Gmail", urlPatterns = {"/GmailUser"})
public class Gmail extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String gmail = request.getParameter("gmail");
        String error = validateEmail(gmail);

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/view/user/gmail.jsp").forward(request, response);
            return;

        }

        // Tạo mã OTP
        int otp = (int) (Math.random() * 900000) + 100000;

        // Thời gian hết hạn OTP (2 phút tính bằng milliseconds)
        long expiryTime = System.currentTimeMillis() + 2 * 60 * 1000;

        HttpSession session = request.getSession();
        session.setAttribute("gmail", gmail);
        session.setAttribute("otp", otp);
        session.setAttribute("otpExpiry", expiryTime);

        EmailSender.send(gmail, "Mã xác nhận đăng ký", "Mã OTP của bạn là: " + otp);
        // Chuyển sang trang nhập OTP
        response.sendRedirect(request.getContextPath() +"/view/user/verify-otp.jsp");
    }

    // Hàm validate email trả về chuỗi lỗi hoặc null nếu hợp lệ
    private String validateEmail(String gmail) {
        // Chấp nhận gmail, giáo dục và doanh nghiệp
        if (gmail == null || !gmail.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
            return "Chỉ chấp nhận email hợp lệ như @gmail.com, @abc.edu, @company.com...";
        }
        // check trùng
        UserDAO udao = new UserDAO();
        if(udao.isGmailRegister(gmail)){
            return "Gmail đã được sử dụng. Vui lòng dùng gmail khác.";
        }
        
        return null; // hợp lệ
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
