/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelAgentService 
 * Description: Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-22  1.0        Hà Thị Duyên      First implementation
 */
package controller.admin;

import dao.StaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import model.Staff;

/**
 * Servlet for adding a new staff member to the TravelAgentService.
 * Handles form submission, input validation, and staff insertion into the database.
 * Forwards to the addStaff.jsp page for rendering the form or displaying results.
 *
 * @author Hà Thị Duyên
 */
@WebServlet(name = "AddStaff", urlPatterns = {"/AddStaff"})
public class AddStaff extends HttpServlet {

    /**
     * Processes both GET and POST requests for adding a staff member.
     * Displays the add staff form or processes form submission with validation and database insertion.
     *
     * @param request  The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object for sending responses
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set response content type
        StaffDAO dao = new StaffDAO(); // Initialize StaffDAO for database operations
        String service = request.getParameter("service"); // Get service parameter

        // If no service specified, show the add staff form
        if (service == null || service.isEmpty()) {
            request.getRequestDispatcher("/view/admin/addStaff.jsp").forward(request, response);
            return;
        }

        // Handle add staff request
        if (service.equals("addStaff")) {
            // Retrieve form input parameters
            String employeeCode = request.getParameter("employeeCode").trim();
            String hireDateStr = request.getParameter("hireDate");
            String password = request.getParameter("password").trim();
            String repassword = request.getParameter("repassword").trim();
            String firstName = request.getParameter("firstName").trim();
            String lastName = request.getParameter("lastName").trim();
            String dobStr = request.getParameter("dob");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address").trim();
            String phone = request.getParameter("phone").trim();
            String gmail = request.getParameter("gmail").trim();

            // Store input values in request attributes to repopulate form
            request.setAttribute("employeeCode", employeeCode.trim());
            request.setAttribute("hireDate", hireDateStr);
            request.setAttribute("lastName", lastName.trim());
            request.setAttribute("firstName", firstName.trim());
            request.setAttribute("password", password.trim());
            request.setAttribute("repassword", repassword.trim());
            request.setAttribute("phone", phone.trim());
            request.setAttribute("dob", dobStr);
            request.setAttribute("gender", gender);
            request.setAttribute("address", address.trim());
            request.setAttribute("gmail", gmail.trim());

            // Validate form inputs
            validateInput(request, dao, employeeCode, hireDateStr, lastName, firstName, password, repassword, phone, dobStr, gender, address, gmail);

            // Proceed with staff creation if no validation errors
            try {
                if (request.getAttribute("employeeCodeError") == null &&
                    request.getAttribute("hireDateError") == null &&
                    request.getAttribute("lastNameError") == null &&
                    request.getAttribute("firstNameError") == null &&
                    request.getAttribute("passwordError") == null &&
                    request.getAttribute("repasswordError") == null &&
                    request.getAttribute("phoneError") == null &&
                    request.getAttribute("dobError") == null &&
                    request.getAttribute("genderError") == null &&
                    request.getAttribute("addressError") == null &&
                    request.getAttribute("gmailError") == null) {

                    // Parse date of birth and hire date, set create/update dates
                    LocalDate dob = LocalDate.parse(dobStr);
                    LocalDate hireDate = LocalDate.parse(hireDateStr);
                    LocalDate now = LocalDate.now();
                    Date createDate = Date.valueOf(now);
                    Date updateDate = Date.valueOf(now);

                    // Create new staff object
                    Staff newStaff = new Staff(
                        0, // staffID (auto-generated)
                        0, // userID (auto-generated)
                        employeeCode,
                        Date.valueOf(hireDate),
                        "Đang làm việc", // Default workStatus for new staff
                        gmail,
                        2, // roleID (2 for staff, as per StaffDAO)
                        password,
                        firstName,
                        lastName,
                        Date.valueOf(dob),
                        gender,
                        address,
                        phone,
                        createDate,
                        updateDate,
                        1 // status (1 for active)
                    );

                    dao.insertStaff(newStaff); // Insert staff into database
                    request.setAttribute("successMessage", "Thêm nhân viên thành công!"); // Set success message
                }
                request.getRequestDispatcher("/view/admin/addStaff.jsp").forward(request, response); // Forward to JSP
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("generalError", "Thêm nhân viên thất bại. Vui lòng thử lại."); // Set SQL error message
                request.getRequestDispatcher("/view/admin/addStaff.jsp").forward(request, response); // Forward to JSP
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("generalError", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại."); // Set general error message
                request.getRequestDispatcher("/view/admin/addStaff.jsp").forward(request, response); // Forward to JSP
            }
        }
    }

    /**
     * Validates form input data for adding a staff member.
     * Sets error attributes in the request if validation fails.
     *
     * @param request      The HttpServletRequest object to store error attributes
     * @param dao          The StaffDAO instance for checking employee code and Gmail existence
     * @param employeeCode The employee code input
     * @param hireDateStr  The hire date input as a string
     * @param lastName     The last name input
     * @param firstName    The first name input
     * @param password     The password input
     * @param repassword   The re-entered password input
     * @param phone        The phone number input
     * @param dobStr       The date of birth input as a string
     * @param gender       The gender input
     * @param address      The address input
     * @param gmail        The Gmail input
     */
    private void validateInput(HttpServletRequest request, StaffDAO dao, String employeeCode, String hireDateStr,
            String lastName, String firstName, String password, String repassword, String phone,
            String dobStr, String gender, String address, String gmail) {
        // Validate employee code
        if (employeeCode == null || employeeCode.trim().isEmpty()) {
            request.setAttribute("employeeCodeError", "Mã nhân viên không được để trống!"); // Empty employee code error
        } else if (!employeeCode.matches("^[A-Z0-9]{5,10}$")) {
            request.setAttribute("employeeCodeError", "Mã nhân viên phải chứa chữ cái in hoa hoặc số, độ dài từ 5 đến 10 ký tự!"); // Invalid employee code format
        } else {
            if (dao.isEmployeeCodeRegister(employeeCode)) {
                request.setAttribute("employeeCodeError", "Mã nhân viên đã tồn tại!"); // Employee code already registered
            }
        }

        // Validate hire date
        if (hireDateStr == null || hireDateStr.trim().isEmpty()) {
            request.setAttribute("hireDateError", "Ngày tuyển dụng không được để trống!"); // Empty hire date error
        } else {
            try {
                LocalDate hireDate = LocalDate.parse(hireDateStr); // Parse hire date
                LocalDate today = LocalDate.now();
                if (hireDate.isAfter(today)) {
                    request.setAttribute("hireDateError", "Ngày tuyển dụng không được lớn hơn ngày hiện tại!"); // Future hire date error
                }
            } catch (Exception e) {
                request.setAttribute("hireDateError", "Ngày tuyển dụng không hợp lệ!"); // Invalid hire date format
            }
        }

        // Validate last name
        if (lastName == null || lastName.trim().isEmpty()) {
            request.setAttribute("lastNameError", "Họ không được để trống!"); // Empty last name error
        } else if (!lastName.matches("^[\\p{L} ]{2,50}$")) {
            request.setAttribute("lastNameError", "Họ chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!"); // Invalid last name format
        }

        // Validate first name
        if (firstName == null || firstName.trim().isEmpty()) {
            request.setAttribute("firstNameError", "Tên không được để trống!"); // Empty first name error
        } else if (!firstName.matches("^[\\p{L} ]{2,50}$")) {
            request.setAttribute("firstNameError", "Tên chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!"); // Invalid first name format
        }

        // Validate address
        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("addressError", "Địa chỉ không được để trống!"); // Empty address error
        } else if (address.length() < 5 || address.length() > 200) {
            request.setAttribute("addressError", "Địa chỉ phải từ 5 đến 200 ký tự!"); // Invalid address length
        } else if (!address.matches("^[\\p{L}\\d\\s,.]+$")) {
            request.setAttribute("addressError", "Địa chỉ chỉ được chứa chữ cái, số, khoảng trắng, dấu phẩy và dấu chấm!"); // Invalid address format
        }

        // Validate password
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Mật khẩu không được để trống!"); // Empty password error
        } else {
            String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
            if (!password.matches(passwordRegex)) {
                request.setAttribute("passwordError", "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!"); // Invalid password format
            }
        }

        // Validate re-entered password
        if (repassword == null || repassword.trim().isEmpty()) {
            request.setAttribute("repasswordError", "Vui lòng nhập lại mật khẩu!"); // Empty re-entered password error
        } else if (!password.equals(repassword)) {
            request.setAttribute("repasswordError", "Mật khẩu không khớp!"); // Password mismatch error
        }

        // Validate phone number
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("phoneError", "Số điện thoại không được để trống!"); // Empty phone number error
        } else if (!phone.matches("^0\\d{9}$")) {
            request.setAttribute("phoneError", "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số, không được chứa ký tự đặc biệt!"); // Invalid phone format
        }

        // Validate date of birth
        if (dobStr == null || dobStr.trim().isEmpty()) {
            request.setAttribute("dobError", "Ngày sinh không được để trống!"); // Empty date of birth error
        } else {
            try {
                LocalDate dob = LocalDate.parse(dobStr); // Parse date of birth
                LocalDate today = LocalDate.now();
                if (dob.isAfter(today)) {
                    request.setAttribute("dobError", "Ngày sinh không được lớn hơn ngày hiện tại!"); // Future date error
                } else if (dob.plusYears(18).isAfter(today)) {
                    request.setAttribute("dobError", "Nhân viên phải đủ 18 tuổi trở lên!"); // Underage error
                } else if (dob.isBefore(today.minusYears(100))) {
                    request.setAttribute("dobError", "Tuổi không được lớn hơn 100!"); // Overage error
                }
            } catch (Exception e) {
                request.setAttribute("dobError", "Ngày sinh không hợp lệ!"); // Invalid date format
            }
        }

        // Validate gender
        if (gender == null || gender.trim().isEmpty()) {
            request.setAttribute("genderError", "Vui lòng chọn giới tính!"); // Empty gender error
        }

        // Validate Gmail
        if (gmail == null || gmail.trim().isEmpty()) {
            request.setAttribute("gmailError", "Gmail không được để trống!"); // Empty Gmail error
        } else if (!gmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("gmailError", "Gmail không hợp lệ!"); // Invalid Gmail format
        } else {
            try {
                if (dao.isGmailRegister(gmail)) {
                    request.setAttribute("gmailError", "Gmail đã được đăng ký!"); // Gmail already registered
                }
            } catch (SQLException e) {
                request.setAttribute("gmailError", "Lỗi khi kiểm tra Gmail!"); // Database error during Gmail check
            }
        }
    }

    /**
     * Handles HTTP GET requests by delegating to processRequest.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Delegate to processRequest
    }

    /**
     * Handles HTTP POST requests by delegating to processRequest.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Delegate to processRequest
    }

    /**
     * Returns a brief description of the servlet.
     *
     * @return A string describing the servlet's functionality
     */
    @Override
    public String getServletInfo() {
        return "Handles staff addition with individual error reporting using setAttribute.";
    }
}