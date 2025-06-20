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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.TravelAgent;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * ManageTravelAgentProfile Servlet handles profile management for travel
 * agents. Supports three separate updates: company info, representative info,
 * and password. Handles multipart form data for image uploads and forwards for
 * password changes.
 */
@WebServlet(name = "ManageTravelAgentProfile", urlPatterns = {"/ManageTravelAgentProfile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class ManageTravelAgentProfile extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String gmail = (String) session.getAttribute("gmail");
        if (gmail == null) {
            response.sendRedirect("LoginLogout?service=loginUser");
            return;
        }

        ITravelAgentDAO agentDAO = new TravelAgentDAO();
        String service = request.getParameter("service");

        try {
            if (service == null || service.equals("viewProfile")) {
                TravelAgent travelAgent = agentDAO.searchTravelAgentByGmail(gmail);
                if (travelAgent == null) {
                    throw new SQLException("Không tìm thấy thông tin đại lý.");
                }
                session.setAttribute("agent", travelAgent);
                request.getRequestDispatcher("view/agent/agentProfile.jsp").forward(request, response);
            } else if (service.equals("editAgent")) {
                TravelAgent travelAgent = agentDAO.searchTravelAgentByGmail(gmail);
                if (travelAgent == null) {
                    throw new SQLException("Không tìm thấy thông tin đại lý để cập nhật.");
                }
                session.setAttribute("agent", travelAgent);
                request.getRequestDispatcher("view/agent/editAgentProfile.jsp").forward(request, response);
            } else if (service.equals("editRepresentative")) {
                TravelAgent travelAgent = agentDAO.searchTravelAgentByGmail(gmail);
                if (travelAgent == null) {
                    throw new SQLException("Không tìm thấy thông tin đại lý để cập nhật.");
                }
                session.setAttribute("agent", travelAgent);
                request.getRequestDispatcher("view/agent/editRepresentative.jsp").forward(request, response);
            } else if (service.equals("saveAgent")) {
                // Extract and trim parameters for company update
                String travelAgentName = request.getParameter("travelAgentName").trim();
                String travelAgentAddress = request.getParameter("travelAgentAddress").trim();
                String travelAgentEmail = request.getParameter("travelAgentEmail").trim();
                String hotLine = request.getParameter("hotLine").trim();
                String taxCode = request.getParameter("taxCode").trim();
                String establishmentDateStr = request.getParameter("establishmentDate").trim();
                // Handle file upload
                String businessLicensePath = handleFileUpload(request.getPart("businessLicense"), request);

                // Retrieve existing travel agent
                TravelAgent travelAgent = agentDAO.searchTravelAgentByGmail(gmail);
                if (travelAgent == null) {
                    throw new SQLException("Không tìm thấy đại lý để lưu thông tin.");
                }

                // Update travel agent object with trimmed data
                travelAgent.setTravelAgentName(travelAgentName);
                travelAgent.setTravelAgentAddress(travelAgentAddress);
                travelAgent.setTravelAgentGmail(travelAgentEmail);
                travelAgent.setHotLine(hotLine);
                travelAgent.setTaxCode(taxCode);
                travelAgent.setEstablishmentDate(Date.valueOf(establishmentDateStr));
                if (businessLicensePath != null) {
                    travelAgent.setBusinessLicense(businessLicensePath);
                }
                request.setAttribute("agent", travelAgent);
                // Validate company input
                Map<String, String> fieldErrors = validateCompanyInput(travelAgentName, travelAgentAddress, travelAgentEmail, hotLine, taxCode, establishmentDateStr);
                if (!fieldErrors.isEmpty()) {
                    request.setAttribute("fieldErrors", fieldErrors);
                    request.getRequestDispatcher("view/agent/editAgentProfile.jsp").forward(request, response);
                    return;
                }

                // Save updated company profile
                agentDAO.updateTravelAgent(travelAgent, "company");
                session.setAttribute("agent", travelAgent);
                request.setAttribute("successAgent", "Cập nhật thông tin công ty thành công!");
                request.getRequestDispatcher("view/agent/editAgentProfile.jsp").forward(request, response);
            } else if (service.equals("saveRepresentative")) {
                // Extract and trim parameters for representative update
                String firstName = request.getParameter("firstName").trim();
                String lastName = request.getParameter("lastName").trim();
                String phone = request.getParameter("phone").trim();
                String address = request.getParameter("address").trim();
                String dobStr = request.getParameter("dob").trim();
                String gender = request.getParameter("gender").trim();
                String representativeIDCard = request.getParameter("representativeIDCard").trim();
                String dateOfIssueStr = request.getParameter("dateOfIssue").trim();
                // Handle file uploads
                String frontIDCardPath = handleFileUpload(request.getPart("frontIDCard"), request);
                String backIDCardPath = handleFileUpload(request.getPart("backIDCard"), request);

                // Retrieve existing travel agent
                TravelAgent travelAgent = agentDAO.searchTravelAgentByGmail(gmail);
                if (travelAgent == null) {
                    throw new SQLException("Không tìm thấy đại lý để lưu thông tin.");
                }

                // Update travel agent object with trimmed data
                travelAgent.setFirstName(firstName);
                travelAgent.setLastName(lastName);
                travelAgent.setPhone(phone);
                travelAgent.setAddress(address);
                travelAgent.setDob(Date.valueOf(dobStr));
                travelAgent.setGender(gender);
                travelAgent.setRepresentativeIDCard(representativeIDCard);
                travelAgent.setDateOfIssue(Date.valueOf(dateOfIssueStr));
                if (frontIDCardPath != null) {
                    travelAgent.setFrontIDCard(frontIDCardPath);
                }
                if (backIDCardPath != null) {
                    travelAgent.setBackIDCard(backIDCardPath);
                }
                request.setAttribute("agent", travelAgent);
                // Validate representative input
                Map<String, String> fieldErrors = validateRepresentativeInput(firstName, lastName, phone, address, dobStr, gender, representativeIDCard, dateOfIssueStr);
                if (!fieldErrors.isEmpty()) {
                    request.setAttribute("fieldErrors", fieldErrors);
                    request.getRequestDispatcher("view/agent/editRepresentative.jsp").forward(request, response);
                    return;
                }

                // Save updated representative profile
                agentDAO.updateTravelAgent(travelAgent, "representative");
                session.setAttribute("agent", travelAgent);
                request.setAttribute("successRe", "Cập nhật thông tin người đại diện thành công!");
                request.getRequestDispatcher("view/agent/editRepresentative.jsp").forward(request, response);
            } else if (service.equals("savePassword")) {
                String password = request.getParameter("newPassword") != null ? request.getParameter("newPassword").trim() : "";
                String confirmPassword = request.getParameter("confirmPassword") != null ? request.getParameter("confirmPassword").trim() : "";

                String errorMessage = validatePasswordInput(password, confirmPassword);
                if (errorMessage != null) {
                    request.setAttribute("errorPassword", errorMessage);
                    request.setAttribute("showPasswordModal", "true");
                    request.getRequestDispatcher("view/agent/agentProfile.jsp").forward(request, response);
                    return;
                }

                TravelAgent travelAgent = agentDAO.searchTravelAgentByGmail(gmail);

                agentDAO.updatePassword(travelAgent.getUserID(), password);

                travelAgent.setPassword(password);
                session.setAttribute("agent", travelAgent);
                request.setAttribute("agent", travelAgent);
                session.setAttribute("successChangePass", "Đổi mật khẩu thành công!");

                response.sendRedirect(request.getContextPath() + "/ManageTravelAgentProfile?service=viewProfile&showPasswordModal=true");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        }
    }

    private Map<String, String> validateCompanyInput(String travelAgentName, String travelAgentAddress, String travelAgentEmail, String hotLine, String taxCode, String establishmentDateStr) {
        Map<String, String> errors = new HashMap<>();

        if (travelAgentName == null || travelAgentName.trim().isEmpty()) {
            errors.put("travelAgentName", "Tên công ty không được để trống!");
        }

        if (travelAgentAddress == null || travelAgentAddress.trim().isEmpty()) {
            errors.put("travelAgentAddress", "Địa chỉ không được để trống!");
        } else if (travelAgentAddress.length() < 5 || travelAgentAddress.length() > 200) {
            errors.put("travelAgentAddress", "Địa chỉ phải từ 5 đến 200 ký tự!");
        }

        if (travelAgentEmail == null || travelAgentEmail.trim().isEmpty()) {
            errors.put("travelAgentEmail", "Email không được để trống!");
        } else if (!travelAgentEmail.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
            errors.put("travelAgentEmail", "Chỉ chấp nhận email hợp lệ như @gmail.com, @abc.edu, @company.com...");
        }

        if (hotLine == null || hotLine.trim().isEmpty()) {
            errors.put("hotLine", "Số điện thoại không được để trống!");
        } else if (!hotLine.matches("^0\\d{9}$")) {
            errors.put("hotLine", "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số.");
        }

        if (taxCode == null || taxCode.trim().isEmpty()) {
            errors.put("taxCode", "Mã số thuế không được để trống!");
        } else if (!taxCode.matches("\\d{10}|\\d{13}")) {
            errors.put("taxCode", "Mã số thuế phải có 10 hoặc 13 chữ số!");
        }

        if (establishmentDateStr == null || establishmentDateStr.trim().isEmpty()) {
            errors.put("establishmentDate", "Ngày thành lập không được để trống!");
        } else {
            try {
                Date establishmentDate = Date.valueOf(establishmentDateStr);
                Date currentDate = Date.valueOf(LocalDate.now());
                if (establishmentDate.after(currentDate)) {
                    errors.put("establishmentDate", "Ngày thành lập phải trước ngày hiện tại!");
                }
            } catch (Exception e) {
                errors.put("establishmentDate", "Ngày thành lập không hợp lệ!");
            }
        }

        return errors;
    }

    private Map<String, String> validateRepresentativeInput(String firstName, String lastName, String phone, String address, String dobStr, String gender, String representativeIDCard, String dateOfIssueStr) {
        Map<String, String> errors = new HashMap<>();

        if (firstName == null || firstName.trim().isEmpty()) {
            errors.put("firstName", "Họ không được để trống!");
        } else if (!firstName.matches("^[\\p{L} ]{2,50}$")) {
            errors.put("firstName", "Họ chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            errors.put("lastName", "Tên không được để trống!");
        } else if (!lastName.matches("^[\\p{L} ]{2,50}$")) {
            errors.put("lastName", "Tên chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!");
        }

        if (phone == null || phone.trim().isEmpty()) {
            errors.put("phone", "Số điện thoại không được để trống!");
        } else if (!phone.matches("^0\\d{9}$")) {
            errors.put("phone", "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số.");
        }

        if (address == null || address.trim().isEmpty()) {
            errors.put("address", "Địa chỉ không được để trống!");
        } else if (address.length() < 5 || address.length() > 200) {
            errors.put("address", "Địa chỉ phải từ 5 đến 200 ký tự!");
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate dobLocal = null;
        
      
        
        if (dobStr == null || dobStr.trim().isEmpty()) {
            errors.put("dob", "Ngày sinh không được để trống!");
        } else if (!dobStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errors.put("dob", "Ngày sinh không hợp lệ!");
        } else {
            Date dob = Date.valueOf(dobStr);
            dobLocal = LocalDate.parse(dob.toString());
            if (dob.after(Date.valueOf(currentDate))) {
                errors.put("dob", "Ngày sinh không được lớn hơn ngày hiện tại!");
            } else if (dobLocal.plusYears(18).isAfter(currentDate)) {
                errors.put("dob", "Bạn phải đủ 18 tuổi trở lên!");
            } else if (dobLocal.isBefore(currentDate.minusYears(100))) {
                errors.put("dob", "Tuổi không được lớn hơn 100!");
            }
        }

        if (gender == null || gender.trim().isEmpty()) {
            errors.put("gender", "Giới tính không được để trống!");
        }

        if (representativeIDCard == null || representativeIDCard.trim().isEmpty()) {
            errors.put("representativeIDCard", "CCCD không được để trống!");
        } else if (!representativeIDCard.matches("^\\d{12}$")) {
            errors.put("representativeIDCard", "CCCD phải có đúng 12 chữ số!");
        }

        if (dateOfIssueStr == null || dateOfIssueStr.trim().isEmpty()) {
            errors.put("dateOfIssue", "Ngày cấp không được để trống!");
        } else if (!dateOfIssueStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errors.put("dateOfIssue", "Ngày cấp không hợp lệ!");
        } else if (dobLocal == null) {
            errors.put("dateOfIssue", "Không thể kiểm tra ngày cấp vì ngày sinh không hợp lệ!");
        } else {
            Date dateOfIssue = Date.valueOf(dateOfIssueStr);
            LocalDate dateOfIssueLocal = LocalDate.parse(dateOfIssue.toString());
            if (dateOfIssue.after(Date.valueOf(currentDate))) {
                errors.put("dateOfIssue", "Ngày cấp căn cước phải trước ngày hiện tại!");
            } else if (dateOfIssueLocal.isBefore(dobLocal.plusYears(14))) {
                errors.put("dateOfIssue", "Ngày cấp căn cước phải sau ngày sinh ít nhất 14 năm!");
            }
        }

        return errors;
    }

    private String validatePasswordInput(String password, String confirmPassword) {
        if (password == null || password.trim().isEmpty() || confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return "Vui lòng điền đầy đủ mật khẩu!";
        }

        String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
        if (!password.matches(passwordRegex)) {
            return "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!";
        }

        if (!password.equals(confirmPassword)) {
            return "Mật khẩu xác nhận không khớp!";
        }

        return null;
    }

    private String handleFileUpload(Part filePart, HttpServletRequest request) throws IOException, ServletException {
        if (filePart == null || filePart.getSize() == 0) {
            return null; // No new file uploaded
        }

        String fileName = getFileName(filePart);
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        // Validate file type
        String contentType = filePart.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new ServletException("Chỉ chấp nhận file hình ảnh!");
        }

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        return UPLOAD_DIR + "/" + fileName;
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
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
        return "Servlet to manage travel agent profiles";
    }
}
