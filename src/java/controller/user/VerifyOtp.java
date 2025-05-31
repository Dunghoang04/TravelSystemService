/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
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

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "VerifyOtp", urlPatterns = {"/VerifyOtp"})
public class VerifyOtp extends HttpServlet {

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
        HttpSession session = request.getSession();
        String inputOtp = request.getParameter("otp");

        Integer fail = (Integer) session.getAttribute("otpFails");
        if (fail == null) {
            fail = 0;
        }

        String validate = validateOtp(inputOtp, session);

        if (validate != null) {
            // Nếu lỗi là "Mã OTP không đúng!", thì tăng số lần sai
            if (validate.equals("Mã OTP không đúng!")) {
                fail++;
                session.setAttribute("otpFails", fail);

                if (fail >= 3) {
                    session.removeAttribute("otp");
                    session.removeAttribute("otpExpiry");
                    session.removeAttribute("otpFails");
                    request.setAttribute("error", "Bạn đã nhập sai quá 3 lần. Vui lòng yêu cầu gửi lại mã!");
                    request.getRequestDispatcher("/view/user/verify-otp.jsp").forward(request, response);
                    return;
                } else {
                     request.setAttribute("error", validate + " Còn " + (3 - fail) + " lần thử.");
                    request.getRequestDispatcher( "/view/user/verify-otp.jsp").forward(request, response);
                    return;
                }
            } else {
                 request.setAttribute("error", validate );
                request.getRequestDispatcher( "/view/user/verify-otp.jsp").forward(request, response);
                return;
            }
        }

        // Xác thực thành công
        session.removeAttribute("otp");
        session.removeAttribute("otpExpiry");
        session.removeAttribute("otpFails");
        response.sendRedirect(request.getContextPath() + "/view/user/registerUser.jsp");
    }

    private String validateOtp(String inputOtp, HttpSession session) {
        Object otpObj = session.getAttribute("otp");
        Object expiryObj = session.getAttribute("otpExpiry");

        if (otpObj == null || expiryObj == null) {
            return "Mã OTP không tồn tại!";
        }

        long expiryTime = (long) expiryObj;
        if (System.currentTimeMillis() > expiryTime) {
            return "Mã OTP đã hết hạn!";
        }
        if (inputOtp == null || inputOtp.isEmpty()) {
            return "Vui lòng nhập mã OTP.";
        }
        int otp = (int) otpObj;
        if (!inputOtp.equals(String.valueOf(otp))) {
            return "Mã OTP không đúng!";
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
