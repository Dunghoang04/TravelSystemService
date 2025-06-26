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
import java.time.LocalDate;
import java.sql.Date;
import model.User;

/**
 * Manages user profile viewing and updating functionalities.<br>
 * Handles form validation and database updates for user profiles.<br>
 * <p>
 * Bugs: No check for duplicate phone numbers; potential SQL injection if
 * session data is tampered.</p>
 *
 * @author Hà Thị Duyên
 */
@WebServlet(name = "ProfileUser", urlPatterns = {"/ProfileUser"})
public class ProfileUser extends HttpServlet {

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
     * Manages profile viewing, editing, and updating based on service parameter.
     * Validates and updates user data in the database.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        IUserDAO udao = new UserDAO();
        String service = request.getParameter("service");

        //Nếu không có service, chuyển đến trang profile
        if (service == null || service.equals("viewProfileUser")) {
            request.getRequestDispatcher("view/user/viewProfileUser.jsp").forward(request, response);
            return;
        }

        // Chuyển đến trang chỉnh sửa profile
        if (service.equals("editProfileUser")) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser == null) {
                request.setAttribute("error", "Vui lòng đăng nhập để chỉnh sửa thông tin!");
                request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("view/user/editProfileUser.jsp").forward(request, response);
            return;
        }

        //Xử lí thông tin người dùng 
        if (service.equals("updateUser")) {
            String firstName = request.getParameter("firstName").trim();
            String lastName = request.getParameter("lastName").trim();
            String password = request.getParameter("password").trim();
            String gender = request.getParameter("gender");
            String dobStr = request.getParameter("dob");
            String address = request.getParameter("address").trim();
            String gmail = request.getParameter("gmail");
            String phone = request.getParameter("phone").trim();
            //Lấy thông tin từ người dùng hiện tại từ session
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser == null) {
                request.setAttribute("error", "Vui lòng đăng nhập để cập nhật thông tin!");
                request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
                return;
            }

            // Lưu giá trị đã nhập để hiển thị lại trong form
            request.setAttribute("lastName", lastName.trim());
            request.setAttribute("firstName", firstName.trim());
            request.setAttribute("password", password.trim());
            request.setAttribute("phone", phone.trim());
            request.setAttribute("dob", dobStr);
            request.setAttribute("gender", gender);
            request.setAttribute("address", address.trim());
            request.setAttribute("gmail", gmail);

            // Nếu không thay đổi mật khẩu, giữ nguyên mật khẩu cũ
            if (password == null || password.trim().isEmpty()) {
                password = loginUser.getPassword();
            }

            validateInput(request, lastName, firstName, password, phone, dobStr, gender, address);
            try {
                if (request.getAttribute("lastNameError") == null
                        && request.getAttribute("firstNameError") == null
                        && request.getAttribute("passwordError") == null
                        && request.getAttribute("repasswordError") == null
                        && request.getAttribute("phoneError") == null
                        && request.getAttribute("dobError") == null
                        && request.getAttribute("genderError") == null
                        && request.getAttribute("addressError") == null) {

                    LocalDate dob = LocalDate.parse(dobStr);
                    LocalDate now = LocalDate.now();
                    Date updateDate = Date.valueOf(now);

                    //Cập nhật thông tin người dùng
                    loginUser.setLastName(lastName.trim());
                    loginUser.setFirstName(firstName.trim());
                    loginUser.setPassword(password.trim());
                    loginUser.setGender(gender);
                    loginUser.setPhone(phone.trim());
                    loginUser.setDob(Date.valueOf(dob));
                    loginUser.setAddress(address.trim());
                    loginUser.setUpdateDate(updateDate);

                    //Gọi DAO để cập nhật vào cơ sở dữ liệu 
                    udao.updateUser(loginUser);

                    //Cập nhat lại session
                    session.setAttribute("loginUser", loginUser);

                    //Hiển thị cập nhật thành công
                    request.setAttribute("success", "Cập nhật thông tin thành công!");
                    request.getRequestDispatcher("/view/user/editProfileUser.jsp").forward(request, response);
                } else {
                    // Nếu có lỗi, quay lại trang chỉnh sửa với thông báo lỗi
                    request.setAttribute("error", "Vui lòng kiểm tra lại thông tin!");
                    request.getRequestDispatcher("/view/user/editProfileUser.jsp").forward(request, response);
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Cập nhật thông tin thát bại.Vui lòng thử lại!");
                request.getRequestDispatcher("/view/user/editProfileUser.jsp").forward(request, response);
                return;
            }

        }
    }

    /**
     * Validates user input for profile update.<br>
     *
     * @param lastName User's last name
     * @param firstName User's first name
     * @param password User's password
     * @param phone User's phone number
     * @param dobStr User's date of birth as string
     * @param gender User's gender
     * @param address User's address
     * @return Error message if invalid, null if valid
     */
    // Block comment to describe the method
    /* 
     * Validates all input fields for profile update.
     * Checks for empty fields, format, and age constraints.
     */
    private void validateInput(HttpServletRequest request, String lastName, String firstName, String password,
            String phone, String dobStr, String gender, String address) {
// Kiểm tra họ
        if (lastName == null || lastName.trim().isEmpty()) {
            request.setAttribute("lastNameError", "Họ không được để trống!");
        } else if (!lastName.matches("^[\\p{L} ]{2,50}$")) {
            request.setAttribute("lastNameError", "Họ chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!");
        }

        // Kiểm tra tên
        if (firstName == null || firstName.trim().isEmpty()) {
            request.setAttribute("firstNameError", "Tên không được để trống!");
        } else if (!firstName.matches("^[\\p{L} ]{2,50}$")) {
            request.setAttribute("firstNameError", "Tên chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!");
        }

        // Kiểm tra địa chỉ
        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("addressError", "Địa chỉ không được để trống!");
        } else if (address.length() < 5 || address.length() > 200) {
            request.setAttribute("addressError", "Địa chỉ phải từ 5 đến 200 ký tự!");
        }

        // Kiểm tra số điện thoại
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("phoneError", "Số điện thoại không được để trống!");
        } else if (!phone.matches("^0\\d{9}$")) {
            request.setAttribute("phoneError", "Số điện thoại phải bắt đầu bằng 0 và có đúng 10 chữ số!");
        }

        // Kiểm tra ngày sinh
        if (dobStr == null || dobStr.trim().isEmpty()) {
            request.setAttribute("dobError", "Ngày sinh không được để trống!");
        } else {
            try {
                LocalDate dob = LocalDate.parse(dobStr);
                LocalDate today = LocalDate.now();
                if (dob.isAfter(today)) {
                    request.setAttribute("dobError", "Ngày sinh không được lớn hơn ngày hiện tại!");
                } else if (dob.plusYears(18).isAfter(today)) {
                    request.setAttribute("dobError", "Bạn phải đủ 18 tuổi trở lên!");
                } else if (dob.isBefore(today.minusYears(100))) {
                    request.setAttribute("dobError", "Tuổi không được lớn hơn 100!");
                }
            } catch (Exception e) {
                request.setAttribute("dobError", "Ngày sinh không hợp lệ!");
            }
        }

        // Kiểm tra giới tính
        if (gender == null || gender.trim().isEmpty()) {
            request.setAttribute("genderError", "Vui lòng chọn giới tính!");
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
