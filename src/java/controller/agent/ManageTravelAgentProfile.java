/*
 * Click https://netbeans.org/project/licenses/ to change this license
 * Click https://netbeans.org/features/ to edit this template
 */
/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai          First implementation
 */
package controller.agent;

import dao.ITravelAgentDAO;
import dao.TravelAgentDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.TravelAgent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * ManageTravelAgentProfile Servlet handles profile management for travel agents.
 * This servlet processes requests for viewing, updating, and saving travel agent profiles.
 * It handles exceptions thrown from the DAO layer and forwards errors to view/common/error.jsp.
 * Authentication is checked via session, and input validation is performed before saving changes.
 * @author Quynh Mai
 */
@WebServlet(name = "ManageTravelAgentProfile", urlPatterns = {"/ManageTravelAgentProfile"})
public class ManageTravelAgentProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     * Handles services like viewing profile, updating profile, and saving profile changes.
     * Catches and handles exceptions thrown from the DAO layer, redirecting to login if not authenticated.
     * @param request servlet request containing service parameters
     * @param response servlet response to send back to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set response content type

        // Get session to check user authentication
        HttpSession session = request.getSession();
        String gmail = (String) session.getAttribute("gmail");
        if (gmail == null) {
            response.sendRedirect("LoginLogout?service=loginUser"); // Redirect to login if not authenticated
            return;
        }

        ITravelAgentDAO agentDAO = new TravelAgentDAO(); // Initialize DAO with interface
        String service = request.getParameter("service");

        try {
            if (service == null || service.equals("viewProfile")) {
                // Retrieve travel agent profile by Gmail from DAO
                TravelAgent travelAgent = agentDAO.searchByTravelAgentGmail(gmail);
                if (travelAgent == null) {
                    throw new SQLException("Không tìm thấy thông tin đại lý."); // Throw SQLException if not found
                }
                session.setAttribute("agent", travelAgent); // Store agent in session
                request.getRequestDispatcher("view/agent/agentProfile.jsp").forward(request, response); // Forward to profile view
            } else if (service.equals("updateProfile")) {
                // Prepare profile for update by retrieving from DAO
                TravelAgent travelAgent = agentDAO.searchByTravelAgentGmail(gmail);
                if (travelAgent == null) {
                    throw new SQLException("Không tìm thấy thông tin đại lý để cập nhật."); // Throw SQLException if not found
                }
                session.setAttribute("agent", travelAgent); // Store agent in session
                request.getRequestDispatcher("view/agent/editAgentProfile.jsp").forward(request, response); // Forward to edit page
            } else if (service.equals("save")) {
                // Extract parameters from request
                String travelAgentEmail = request.getParameter("travelAgentEmail");
                String hotLine = request.getParameter("hotLine");
                String address = request.getParameter("address");
                String representativeFirstName = request.getParameter("representativeFirstName");
                String representativeLastName = request.getParameter("representativeLastName");
                String representativePhone = request.getParameter("representativePhone");
                String representativeAddress = request.getParameter("representativeAddress");
                String dobStr = request.getParameter("dob");
                String gender = request.getParameter("gender");
                String dateOfIssueStr = request.getParameter("dateOfIssue");
                String password = request.getParameter("password");

                // Validate input data
                String errorMessage = validateInput(travelAgentEmail, hotLine, address, representativeFirstName,
                        representativeLastName, representativePhone, representativeAddress, dobStr, gender, password, dateOfIssueStr);
                if (errorMessage != null) {
                    request.setAttribute("error", errorMessage); // Set error message if validation fails
                    request.getRequestDispatcher("view/agent/editAgentProfile.jsp").forward(request, response);
                    return;
                }

                // Convert string dates to SQL Date
                Date dateOfIssue = Date.valueOf(dateOfIssueStr); // Convert date of issue
                Date dob = Date.valueOf(dobStr); // Convert date of birth

                // Retrieve existing travel agent from DAO
                TravelAgent travelAgent = agentDAO.searchByTravelAgentGmail(gmail);
                if (travelAgent == null) {
                    throw new SQLException("Không tìm thấy đại lý để lưu thông tin."); // Throw SQLException if not found
                }

                // Update travel agent object
                travelAgent.setTravelAgentGmail(travelAgentEmail);
                travelAgent.setHotLine(hotLine);
                travelAgent.setTravelAgentAddress(address);
                travelAgent.setFirstName(representativeFirstName);
                travelAgent.setLastName(representativeLastName);
                travelAgent.setPhone(representativePhone);
                travelAgent.setAddress(representativeAddress);
                travelAgent.setDob(dob);
                travelAgent.setGender(gender);
                travelAgent.setPassword(password);

                // Save updated profile using DAO
                agentDAO.updateTravelAgent(travelAgent);
                session.setAttribute("agent", travelAgent); // Update session with new data
                request.setAttribute("success", "Cập nhật thông tin thành công!"); // Set success message
                request.getRequestDispatcher("view/agent/editAgentProfile.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            // Handle SQL exceptions thrown from DAO and forward to error page
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle other exceptions and forward to error page
            request.setAttribute("error", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        }
    }

    /**
     * Validates input data for profile update.
     * Checks for empty fields, format validation (names, phone, email, password), and date constraints.
     * @param travelAgentEmail Email of the travel agent
     * @param hotLine Hotline number
     * @param address Travel agent address
     * @param representativeFirstName First name of representative
     * @param representativeLastName Last name of representative
     * @param representativePhone Phone number of representative
     * @param representativeAddress Address of representative
     * @param dobStr Date of birth as string
     * @param gender Gender of representative
     * @param password Password for the account
     * @param dateOfIssueStr Date of issue as string
     * @return Error message if validation fails, null otherwise
     */
    private String validateInput(String travelAgentEmail, String hotLine, String address, String representativeFirstName,
            String representativeLastName, String representativePhone, String representativeAddress, String dobStr,
            String gender, String password, String dateOfIssueStr) {
        // Check if any required field is empty
        if (travelAgentEmail == null || travelAgentEmail.trim().isEmpty()
                || hotLine == null || hotLine.trim().isEmpty()
                || address == null || address.trim().isEmpty()
                || representativeFirstName == null || representativeFirstName.trim().isEmpty()
                || representativeLastName == null || representativeLastName.trim().isEmpty()
                || representativePhone == null || representativePhone.trim().isEmpty()
                || representativeAddress == null || representativeAddress.trim().isEmpty()
                || dobStr == null || dobStr.trim().isEmpty()
                || gender == null || gender.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || dateOfIssueStr == null || dateOfIssueStr.trim().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin!"; // Return error if any field is empty
        }

        // Validate name format (letters and spaces, 2-50 characters)
        if (!representativeLastName.matches("^[\\p{L} ]{2,50}$") || !representativeFirstName.matches("^[\\p{L} ]{2,50}$")) {
            return "Họ và tên chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!"; // Validate name format
        }

        // Validate address length (5-200 characters)
        if (representativeAddress.length() < 5 || representativeAddress.length() > 200) {
            return "Địa chỉ phải từ 5 đến 200 ký tự!"; // Validate address length
        }

        // Validate phone number (10 digits starting with 0)
        if (!hotLine.matches("^0\\d{9}$") || !representativePhone.matches("^0\\d{9}$")) {
            return "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số."; // Validate phone format
        }

        // Validate password (starts with uppercase, 1 lowercase, 1 digit, 1 special char, min 8 chars)
        String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
        if (!password.matches(passwordRegex)) {
            return "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!"; // Validate password format
        }

        // Validate email format
        if (travelAgentEmail == null || !travelAgentEmail.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
            return "Chỉ chấp nhận email hợp lệ như @gmail.com, @abc.edu, @company.com..."; // Validate email format
        }

        // Validate date of birth
        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr); // Parse date of birth
            LocalDate today = LocalDate.now();
            if (dob.isAfter(today)) {
                return "Ngày sinh không được lớn hơn ngày hiện tại!"; // Check future date
            }
            if (dob.plusYears(18).isAfter(today)) {
                return "Bạn phải đủ 18 tuổi trở lên để đăng ký!"; // Check age requirement
            }
            if (dob.isBefore(today.minusYears(100))) {
                return "Tuổi không được lớn hơn 100!"; // Check maximum age
            }
        } catch (Exception e) {
            return "Ngày sinh không hợp lệ"; // Handle invalid date format
        }

        // Validate date of issue
        try {
            Date dateOfIssue = Date.valueOf(dateOfIssueStr); // Parse date of issue
            Date currentDate = Date.valueOf(LocalDate.now());
            if (dateOfIssue.after(currentDate)) {
                return "Ngày cấp phải trước ngày hiện tại!"; // Check future date
            }
        } catch (Exception e) {
            return "Ngày cấp không hợp lệ"; // Handle invalid date format
        }

        return null; // Validation passed
    }

    /**
     * Handles the HTTP GET method.
     * Delegates to processRequest to handle profile-related services.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Delegate GET requests to processRequest
    }

    /**
     * Handles the HTTP POST method.
     * Delegates to processRequest to handle profile-related services.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Delegate POST requests to processRequest
    }

    /**
     * Returns a short description of the servlet.
     * Provides a brief overview of the servlet's purpose.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to manage travel agent profiles"; // Return servlet description
    }
}