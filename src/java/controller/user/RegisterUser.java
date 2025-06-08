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

import dao.IUserDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.User;

/**
 * Manages user registration process with OTP verification.<br>
 * Handles form validation and database insertion for new users.<br>
 * <p>
 * Bugs: No rate limiting for registration attempts; session cleanup may fail in
 * error cases.</p>
 *
 * @author Hà Thị Duyên
 */
@WebServlet(name = "Register", urlPatterns = {"/RegisterUser"})
public class RegisterUser extends HttpServlet {

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
     * Handles registration form display and submission.
     * Validates input and inserts new user into database.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        IUserDAO udao = new UserDAO();
        String service = request.getParameter("service");

        // Nếu không có service thì hiển thị trang đăng ký
        if (service == null || service.isEmpty()) {
            String gmail = (String) session.getAttribute("gmail");
            if (gmail == null) {
                response.sendRedirect(request.getContextPath() + "/gmailUser"); // Chuyển về trang nhập gmail nếu chưa có
                return;
            }
            request.getRequestDispatcher("/view/user/registerUser.jsp").forward(request, response);
            return;
        }

        //Xử lí khi người dùng submit form đăng ký
        if (service.equals("addUser")) {
            //Lấy dữ liệu từ form 

            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String dobStr = request.getParameter("dob");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String gmail = (String) session.getAttribute("gmail"); // Lấy gmail từ session

            //Kiểm tra gmail từ session 
            if (gmail == null) {
                request.setAttribute("error", "Phiên đăng ký hết hạn.Vui lòng nhập lại email. ");
                request.getRequestDispatcher("/view/user/registerUser.jsp").forward(request, response);
                return;
            }
            // Kiểm tra dữ liệu đầu vào
            String error = validateInput(lastName, firstName, password, repassword, phone, dobStr, gender, address);
            if (error != null) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("view/user/registerUser.jsp").forward(request, response);
                return;
            }

            try {

                LocalDate dob = LocalDate.parse(dobStr);
                LocalDate now = LocalDate.now();
                Date createDate = Date.valueOf(now);
                Date updateDate = Date.valueOf(now);

                User newUser = new User(gmail, password, firstName, lastName, Date.valueOf(dob), gender, address, phone, createDate, updateDate, 1, 3);
                udao.insertUser(newUser);
                session.removeAttribute("gmail"); // Xóa session sau khi đăng ký thành công
                session.removeAttribute("otp");
                session.removeAttribute("otpExpiry");
                request.setAttribute("successMessage", "Đăng ký thành công! Hãy đăng nhập.");
                request.getRequestDispatcher("/view/user/registerUser.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Đăng ký thất bại.Vui lòng thử lại.");
                request.getRequestDispatcher("/view/user/registerUser.jsp").forward(request, response);
            }
        }
    }

    /**
     * Validates user input for registration.<br>
     *
     * @param lastName User's last name
     * @param firstName User's first name
     * @param password User's password
     * @param repassword User's re-entered password
     * @param phone User's phone number
     * @param dobStr User's date of birth as string
     * @param gender User's gender
     * @param address User's address
     * @return Error message if invalid, null if valid
     */
    // Block comment to describe the method
    /* 
     * Validates all registration input fields.
     * Checks for empty fields, format, password match, and age constraints.
     */
    private String validateInput(String lastName, String firstName, String password, String repassword,
            String phone, String dobStr, String gender, String address) {
// Kiểm tra các trường không được để trống
        if (lastName == null || lastName.trim().isEmpty()
                || firstName == null || firstName.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || repassword == null || repassword.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || dobStr == null || dobStr.trim().isEmpty()
                || gender == null || gender.trim().isEmpty()
                || address == null || address.trim().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin!";
        }
        if (!lastName.matches("^[\\p{L} ]{2,50}$") || !firstName.matches("^[\\p{L} ]{2,50}$")) {
            return "Họ và tên chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!";
        }
        if (address.length() < 5 || address.length() > 200) {
            return "Địa chỉ phải từ 5 đến 200 ký tự!";
        }

        if (!password.equals(repassword)) {
            return "Mật khẩu không khớp";
        }

        if (!phone.matches("^0\\d{9}$")) {
            return "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số. ";
        }
        String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
        if (!password.matches(passwordRegex)) {
            return "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!";
        }

        try {
            LocalDate dob = LocalDate.parse(dobStr);
            LocalDate today = LocalDate.now();
            if (dob.isAfter(today)) {
                return "Ngày sinh không được lớn hơn ngày hiện tại!";
            }

            if (dob.plusYears(18).isAfter(today)) {
                return "Bạn phải đủ 18 tuổi trở lên để đăng ký!";
            }

            if (dob.isBefore(today.minusYears(100))) {
                return "Tuổi không được lớn hơn 100!";
            }

        } catch (Exception e) {
            return "Ngày sinh không hợp lệ";
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
