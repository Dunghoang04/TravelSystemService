/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelAgentService 
 * Description: Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-23  1.0        Hà Thị Duyên          First implementation for admin profile
 */
package controller.admin;

import dao.IUserDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import model.User;

/**
 * Manages admin profile viewing and updating functionalities for users with roleID = 1.<br>
 * Handles form validation and database updates for admin profiles.<br>
 * <p>
 * Bugs: No check for duplicate phone numbers; potential SQL injection if session data is tampered.</p>
 *
 * @author Nhat Anh
 */
@WebServlet(name = "ManageAdminProfile", urlPatterns = {"/ManageAdminProfile"})
public class ManageAdminProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     * Handles viewing, editing, and updating admin profile information based on the service parameter.
     * Ensures the user is an admin (roleID = 1) and validates inputs before updating the database.
     *
     * @param request  The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object for sending responses
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set response content type
        HttpSession session = request.getSession(); // Retrieve session
        IUserDAO userDAO = new UserDAO(); // Initialize UserDAO for database operations
        String service = request.getParameter("service"); // Get service parameter

        // Retrieve admin user from session
        User loginAdmin = (User) session.getAttribute("loginUser");

        // Check if user is logged in and has admin role (roleID = 1)
        if (loginAdmin == null || loginAdmin.getRoleID() != 1) {
            request.setAttribute("error", "Vui lòng đăng nhập với tư cách admin để truy cập!"); // Set error message
            request.getRequestDispatcher("/view/user/login.jsp").forward(request, response); // Redirect to login page
            return;
        }

        // Default to view profile if no service is specified
        if (service == null || service.equals("viewAdminProfile")) {
            request.getRequestDispatcher("view/admin/viewAdminProfile.jsp").forward(request, response); // Forward to view profile JSP
            return;
        }

        // Navigate to edit profile page
        if (service.equals("editAdminProfile")) {
            request.getRequestDispatcher("view/admin/editAdminProfile.jsp").forward(request, response); // Forward to edit profile JSP
            return;
        }

        // Handle profile update
        if (service.equals("updateAdmin")) {
            // Retrieve form input parameters
            String firstName = request.getParameter("firstName").trim();
            String lastName = request.getParameter("lastName").trim();
            String password = request.getParameter("password").trim();
            String gender = request.getParameter("gender");
            String dobStr = request.getParameter("dob");
            String address = request.getParameter("address").trim();
            String gmail = request.getParameter("gmail").trim();
            String phone = request.getParameter("phone").trim();

            // Store input values in request attributes to repopulate form
            request.setAttribute("firstName", firstName.trim());
            request.setAttribute("lastName", lastName.trim());
            request.setAttribute("password", password.trim());
            request.setAttribute("phone", phone.trim());
            request.setAttribute("dob", dobStr);
            request.setAttribute("gender", gender);
            request.setAttribute("address", address.trim());
            request.setAttribute("gmail", gmail.trim());

            // Retain existing password if not updated
            if (password == null || password.trim().isEmpty()) {
                password = loginAdmin.getPassword(); // Use current password
            }

            // Validate form inputs
            validateInput(request, lastName, firstName, password, phone, dobStr, gender, address);

            try {
                // Proceed with update if no validation errors
                if (request.getAttribute("lastNameError") == null
                        && request.getAttribute("firstNameError") == null
                        && request.getAttribute("passwordError") == null
                        && request.getAttribute("phoneError") == null
                        && request.getAttribute("dobError") == null
                        && request.getAttribute("genderError") == null
                        && request.getAttribute("addressError") == null) {

                    // Parse date of birth and set update date
                    LocalDate dob = LocalDate.parse(dobStr);
                    LocalDate now = LocalDate.now();
                    Date updateDate = Date.valueOf(now);

                    // Update admin object with new information
                    loginAdmin.setLastName(lastName);
                    loginAdmin.setFirstName(firstName);
                    loginAdmin.setPassword(password);
                    loginAdmin.setGender(gender);
                    loginAdmin.setPhone(phone);
                    loginAdmin.setDob(Date.valueOf(dob));
                    loginAdmin.setAddress(address);
                    loginAdmin.setUpdateDate(updateDate);

                    // Update database with new admin information
                    userDAO.updateUser(loginAdmin);

                    // Update session with modified admin object
                    session.setAttribute("loginUser", loginAdmin);

                    // Set success message
                    request.setAttribute("success", "Cập nhật thông tin admin thành công!");
                    request.getRequestDispatcher("view/admin/editAdminProfile.jsp").forward(request, response); // Forward to edit profile JSP
                } else {
                    // Return to edit page with validation errors
                    request.setAttribute("error", "Vui lòng kiểm tra lại thông tin!");
                    request.getRequestDispatcher("view/admin/editAdminProfile.jsp").forward(request, response); // Forward to edit profile JSP
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log exception
                request.setAttribute("error", "Cập nhật thông tin thất bại. Vui lòng thử lại!"); // Set error message
                request.getRequestDispatcher("view/admin/editAdminProfile.jsp").forward(request, response); // Forward to edit profile JSP
            }
        }
    }

    /**
     * Validates input data for admin profile updates.
     * Sets error attributes in the request if validation fails.
     *
     * @param request   The HttpServletRequest object to store error attributes
     * @param lastName  The last name input
     * @param firstName The first name input
     * @param password  The password input
     * @param phone     The phone number input
     * @param dobStr    The date of birth input as a string
     * @param gender    The gender input
     * @param address   The address input
     */
    private void validateInput(HttpServletRequest request, String lastName, String firstName, String password,
            String phone, String dobStr, String gender, String address) {
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
        }

        // Validate phone number
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("phoneError", "Số điện thoại không được để trống!"); // Empty phone number error
        } else if (!phone.matches("^0[35789]\\d{8}$")) {
            request.setAttribute("phoneError", "Số điện thoại phải bắt đầu bằng 03, 05, 07, 08 hoặc 09 và có đúng 10 chữ số!"); // Invalid phone format
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
                    request.setAttribute("dobError", "Bạn phải đủ 18 tuổi trở lên!"); // Underage error
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
        return "Manages admin profile viewing and updating for roleID = 1.";
    }
}