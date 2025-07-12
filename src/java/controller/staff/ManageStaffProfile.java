/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelAgentService 
 * Description: Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-02  1.0        Hà Thị Duyên      First implementation for staff profile
 * 2025-07-02  1.1        Hà Thị Duyên      Merged edit profile functionality
 */
package controller.staff;

import dao.IStaffDAO;
import dao.IUserDAO;
import dao.StaffDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import model.Staff;
import model.User;
import java.sql.SQLException;

/**
 * Manages staff profile viewing and editing functionalities for users with roleID != 1 (non-admin).<br>
 * Handles viewing, editing, and updating staff profiles, restricting changes to employeeCode, hireDate, and workStatus.<br>
 * Displays success messages using SweetAlert after successful updates.<br>
 * <p>
 * Bugs: No check for duplicate phone numbers; potential session tampering risks.</p>
 *
 * @author Hà Thị Duyên
 */
@WebServlet(name = "ManageStaffProfile", urlPatterns = {"/ManageStaffProfile"})
public class ManageStaffProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     * Handles viewing and editing of staff profile information.
     * Ensures the user is a staff member (roleID != 1) and validates inputs before updating.
     *
     * @param request  The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object for sending responses
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        IStaffDAO staffDAO = new StaffDAO();
        IUserDAO userDAO = new UserDAO();
        String service = request.getParameter("service");

        // Retrieve logged-in user from session
        User loginUser = (User) session.getAttribute("loginUser");

        // Check if user is logged in and is not an admin
        if (loginUser == null || loginUser.getRoleID() == 1) {
            request.setAttribute("error", "Vui lòng đăng nhập với tư cách nhân viên để truy cập!");
            request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
            return;
        }

        // Retrieve staff information
        Staff staff;
        try {
            staff = staffDAO.getStaffByUserID(loginUser.getUserID());
            if (staff == null) {
                request.setAttribute("error", "Không tìm thấy thông tin nhân viên!");
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải thông tin nhân viên!");
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            return;
        }

        // Store staff information in request scope
        request.setAttribute("staff", staff);

        // Handle services
        if (service == null || service.equals("viewStaffProfile")) {
            // Transfer successMessage from session to request scope for display
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) {
                request.setAttribute("successMessage", successMessage);
                session.removeAttribute("successMessage"); // Remove after displaying
            }
            request.getRequestDispatcher("/view/staff/viewStaffProfile.jsp").forward(request, response);
            return;
        }

        if (service.equals("editStaffProfile")) {
            request.getRequestDispatcher("/view/staff/editStaffProfile.jsp").forward(request, response);
            return;
        }

        if (service.equals("updateStaff")) {
            // Retrieve form input parameters
            String firstName = request.getParameter("firstName").trim();
            String lastName = request.getParameter("lastName").trim();
            String password = request.getParameter("password").trim();
            String gender = request.getParameter("gender");
            String dobStr = request.getParameter("dob");
            String address = request.getParameter("address").trim();
            String gmail = request.getParameter("gmail").trim();
            String phone = request.getParameter("phone").trim();

            // Store input values to repopulate form
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("password", password);
            request.setAttribute("phone", phone);
            request.setAttribute("dob", dobStr);
            request.setAttribute("gender", gender);
            request.setAttribute("address", address);
            request.setAttribute("gmail", gmail);
            request.setAttribute("employeeCode", staff.getEmployeeCode());
            request.setAttribute("hireDate", staff.getHireDate());
            request.setAttribute("workStatus", staff.getWorkStatus());

            // Retain existing password if not updated
            if (password == null || password.trim().isEmpty()) {
                password = loginUser.getPassword();
            }

            // Validate inputs
            validateInput(request, lastName, firstName, password, phone, dobStr, gender, address, userDAO, gmail, loginUser.getGmail(), staffDAO);

            try {
                // Proceed with update if no validation errors
                if (request.getAttribute("lastNameError") == null
                        && request.getAttribute("firstNameError") == null
                        && request.getAttribute("passwordError") == null
                        && request.getAttribute("phoneError") == null
                        && request.getAttribute("dobError") == null
                        && request.getAttribute("genderError") == null
                        && request.getAttribute("addressError") == null
                        && request.getAttribute("gmailError") == null) {

                    // Parse date of birth and set update date
                    LocalDate dob = LocalDate.parse(dobStr);
                    LocalDate now = LocalDate.now();
                    Date updateDate = Date.valueOf(now);

                    // Update User object
                    loginUser.setGmail(gmail);
                    loginUser.setPassword(password);
                    loginUser.setFirstName(firstName);
                    loginUser.setLastName(lastName);
                    loginUser.setDob(Date.valueOf(dob));
                    loginUser.setGender(gender);
                    loginUser.setAddress(address);
                    loginUser.setPhone(phone);
                    loginUser.setUpdateDate(updateDate);

                    // Update Staff object (only User-related fields)
                    staff.setGmail(gmail);
                    staff.setPassword(password);
                    staff.setFirstName(firstName);
                    staff.setLastName(lastName);
                    staff.setDob(Date.valueOf(dob));
                    staff.setGender(gender);
                    staff.setAddress(address);
                    staff.setPhone(phone);
                    staff.setUpdateDate(updateDate);

                    // Update database
                    userDAO.updateUser(loginUser);
                    staffDAO.updateStaff(staff);

                    // Update session
                    session.setAttribute("loginUser", loginUser);

                    // Set success message in session scope
                    session.setAttribute("successMessage", "Cập nhật thông tin nhân viên thành công!");
                    response.sendRedirect(request.getContextPath() + "/ManageStaffProfile?service=viewStaffProfile");
                    return;
                } else {
                    // Return to edit page with errors
                    request.setAttribute("error", "Vui lòng kiểm tra lại thông tin!");
                    request.getRequestDispatcher("/view/staff/editStaffProfile.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Cập nhật thông tin thất bại. Vui lòng thử lại!");
                request.getRequestDispatcher("/view/staff/editStaffProfile.jsp").forward(request, response);
            }
            return;
        }
    }

    /**
     * Validates input data for staff profile updates.
     * Sets error attributes in the request if validation fails.
     *
     * @param request      The HttpServletRequest object to store error attributes
     * @param lastName     The last name input
     * @param firstName    The first name input
     * @param password     The password input
     * @param phone        The phone number input
     * @param dobStr       The date of birth input as a string
     * @param gender       The gender input
     * @param address      The address input
     * @param userDAO      The UserDAO instance for checking Gmail
     * @param gmail        The Gmail input
     * @param currentGmail The current Gmail of the user
     * @param staffDAO     The StaffDAO instance for checking phone number
     */
    private void validateInput(HttpServletRequest request, String lastName, String firstName, String password,
            String phone, String dobStr, String gender, String address, IUserDAO userDAO, String gmail,
            String currentGmail, IStaffDAO staffDAO) {
        // Validate last name
        if (lastName == null || lastName.trim().isEmpty()) {
            request.setAttribute("lastNameError", "Họ không được để trống!");
        } else if (!lastName.matches("^[\\p{L} ]{2,50}$")) {
            request.setAttribute("lastNameError", "Họ chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!");
        }

        // Validate first name
        if (firstName == null || firstName.trim().isEmpty()) {
            request.setAttribute("firstNameError", "Tên không được để trống!");
        } else if (!firstName.matches("^[\\p{L} ]{2,50}$")) {
            request.setAttribute("firstNameError", "Tên chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!");
        }

        // Validate address
        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("addressError", "Địa chỉ không được để trống!");
        } else if (address.length() < 5 || address.length() > 200) {
            request.setAttribute("addressError", "Địa chỉ phải từ 5 đến 200 ký tự!");
        }

        // Validate phone number
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("phoneError", "Số điện thoại không được để trống!");
        } else if(!phone.matches("^0\\d{9}$")) {
            request.setAttribute("phoneError", "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số!");
        }

        // Validate date of birth
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

        // Validate gender
        if (gender == null || gender.trim().isEmpty()) {
            request.setAttribute("genderError", "Vui lòng chọn giới tính!");
        }

        // Validate Gmail
        if (gmail == null || gmail.trim().isEmpty()) {
            request.setAttribute("gmailError", "Gmail không được để trống!");
        } else if (!gmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("gmailError", "Gmail không hợp lệ!");
        } else {
            try {
                if (!gmail.equalsIgnoreCase(currentGmail) && userDAO.isGmailRegister(gmail)) {
                    request.setAttribute("gmailError", "Gmail đã được sử dụng!");
                }
            } catch (SQLException e) {
                request.setAttribute("gmailError", "Lỗi kiểm tra Gmail!");
            }
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
        return "Manages staff profile viewing and editing for non-admin users.";
    }
}