/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên          First implementation*/
package controller.user;

import dao.IUserDAO;
import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import model.User;

@WebServlet(name = "Register", urlPatterns = {"/RegisterUser"})
public class RegisterUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        IUserDAO udao = new UserDAO();
        String service = request.getParameter("service");

        if (service == null || service.isEmpty()) {
            String gmail = (String) session.getAttribute("gmail");
            if (gmail == null) {
                response.sendRedirect(request.getContextPath() + "/gmailUser");
                return;
            }
            request.getRequestDispatcher("/view/user/registerUser.jsp").forward(request, response);
            return;
        }

        if (service.equals("addUser")) {
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String dobStr = request.getParameter("dob");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String gmail = (String) session.getAttribute("gmail");

            if (gmail == null) {
                request.setAttribute("generalError", "Phiên đăng ký hết hạn. Vui lòng nhập lại email.");
                request.getRequestDispatcher("/view/user/registerUser.jsp").forward(request, response);
                return;
            }

            // Kiểm tra dữ liệu đầu vào và lưu lỗi vào request
            validateInput(request, lastName, firstName, password, repassword, phone, dobStr, gender, address);

            // Lưu lại giá trị đã nhập để hiển thị trong form
            request.setAttribute("lastName", lastName);
            request.setAttribute("firstName", firstName);
            request.setAttribute("password", password);
            request.setAttribute("repassword", repassword);
            request.setAttribute("phone", phone);
            request.setAttribute("dob", dobStr);
            request.setAttribute("gender", gender);
            request.setAttribute("address", address);

            // Nếu không có lỗi, thực hiện đăng ký
            try {
                // Kiểm tra xem có lỗi nào được đặt hay không
                if (request.getAttribute("lastNameError") == null &&
                    request.getAttribute("firstNameError") == null &&
                    request.getAttribute("passwordError") == null &&
                    request.getAttribute("repasswordError") == null &&
                    request.getAttribute("phoneError") == null &&
                    request.getAttribute("dobError") == null &&
                    request.getAttribute("genderError") == null &&
                    request.getAttribute("addressError") == null) {

                    LocalDate dob = LocalDate.parse(dobStr);
                    LocalDate now = LocalDate.now();
                    Date createDate = Date.valueOf(now);
                    Date updateDate = Date.valueOf(now);

                    User newUser = new User(gmail, password, firstName, lastName, Date.valueOf(dob), gender, address, phone, createDate, updateDate, 1, 3);
                    udao.insertUser(newUser);
                    session.removeAttribute("gmail");
                    session.removeAttribute("otp");
                    session.removeAttribute("otpExpiry");
                    request.setAttribute("successMessage", "Đăng ký thành công! Hãy đăng nhập.");
                }
                request.getRequestDispatcher("/view/user/registerUser.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("generalError", "Đăng ký thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("/view/user/registerUser.jsp").forward(request, response);
            }
        }
    }

    private void validateInput(HttpServletRequest request, String lastName, String firstName, String password, 
            String repassword, String phone, String dobStr, String gender, String address) {
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

        // Kiểm tra mật khẩu
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Mật khẩu không được để trống!");
        } else {
            String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
            if (!password.matches(passwordRegex)) {
                request.setAttribute("passwordError", "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!");
            }
        }

        // Kiểm tra nhập lại mật khẩu
        if (repassword == null || repassword.trim().isEmpty()) {
            request.setAttribute("repasswordError", "Vui lòng nhập lại mật khẩu!");
        } else if (!password.equals(repassword)) {
            request.setAttribute("repasswordError", "Mật khẩu không khớp!");
        }

        // Kiểm tra số điện thoại
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("phoneError", "Số điện thoại không được để trống!");
        } else if (!phone.matches("^0\\d{9}$")) {
            request.setAttribute("phoneError", "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số!");
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
                    request.setAttribute("dobError", "Bạn phải đủ 18 tuổi trở lên để đăng ký!");
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles user registration with individual error reporting using setAttribute.";
    }
}