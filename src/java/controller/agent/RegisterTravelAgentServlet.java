/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai         First implementation
 * 2025-07-13  1.1        Grok              Integrated confirmRegister.jsp as pop-up, removed JSON and AJAX
 */
package controller.agent;

import dao.ITravelAgentDAO;
import dao.TravelAgentDAO;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import model.TravelAgent;
import service.EmailSender;

@WebServlet(name = "RegisterTravelAgentServlet", urlPatterns = {"/RegisterTravelAgentServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold
        maxFileSize = 1024 * 1024 * 10, // 10MB max file size
        maxRequestSize = 1024 * 1024 * 50 // 50MB max request size
)
public class RegisterTravelAgentServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "assets/img/registerAgent"; // Directory for uploaded files
    private ITravelAgentDAO travelAgentDAO = new TravelAgentDAO(); // DAO instance with interface
    private HttpSession session;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession(); // Initialize session
        request.getRequestDispatcher("view/agent/register1.jsp").forward(request, response); // Forward to registration step 1
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession(); // Initialize session
        String service = request.getParameter("service");

        try {
            if ("step1".equals(service)) {
                // Process step 1 of registration (email and password)
                String email = request.getParameter("email").trim();
                String password = request.getParameter("password").trim();
                String confirmPassword = request.getParameter("confirmPassword").trim();
                String result = processStep1(email, password, confirmPassword);

                if (result.equals("success")) {
                    session.setAttribute("email", email); // Store email in session
                    session.setAttribute("password", password); // Store password in session
                    request.getRequestDispatcher("view/agent/register2.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", result);
                    request.getRequestDispatcher("view/agent/register1.jsp").forward(request, response);
                }
            } else if ("addAgent".equals(service)) {
                // Check session validity
                String email = (String) session.getAttribute("email");
                if (email == null) {
                    request.setAttribute("errorMessage", "Phiên đăng ký hết hạn. Vui lòng nhập lại email.");
                    request.getRequestDispatcher("view/agent/register1.jsp").forward(request, response);
                    return;
                }

                // Process file uploads
                String appPath = request.getServletContext().getRealPath("");
                String savePath = appPath + File.separator + UPLOAD_DIR;
                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }

                String contentType = request.getContentType();
                if (contentType == null || !contentType.toLowerCase().startsWith("multipart/")) {
                    request.setAttribute("errorMessage", "Yêu cầu không chứa dữ liệu multipart. Vui lòng chọn file và gửi lại!");
                    request.getRequestDispatcher("view/agent/register2.jsp").forward(request, response);
                    return;
                }

                @SuppressWarnings("unchecked")
                Map<String, String> uploadedFiles = (Map<String, String>) session.getAttribute("uploadedFiles");
                if (uploadedFiles == null) {
                    uploadedFiles = new HashMap<>();
                }

                for (Part part : request.getParts()) {
                    String fileName = extractFileName(part);
                    if (fileName != null && !fileName.isEmpty()) {
                        String filePath = savePath + File.separator + fileName;
                        part.write(filePath);
                        if (part.getName().equals("businessLicense")) {
                            uploadedFiles.put("businessLicensePath", UPLOAD_DIR + "/" + fileName);
                        } else if (part.getName().equals("frontIDCard")) {
                            uploadedFiles.put("frontIDCardPath", UPLOAD_DIR + "/" + fileName);
                        } else if (part.getName().equals("backIDCard")) {
                            uploadedFiles.put("backIDCardPath", UPLOAD_DIR + "/" + fileName);
                        }
                    }
                }
                session.setAttribute("uploadedFiles", uploadedFiles);

                String businessLicensePath = uploadedFiles.get("businessLicensePath");
                String frontIDCardPath = uploadedFiles.get("frontIDCardPath");
                String backIDCardPath = uploadedFiles.get("backIDCardPath");

                // Extract form data
                String travelAgentName = request.getParameter("travelAgentName").trim();
                String travelAgentGmail = request.getParameter("travelAgentGmail").trim();
                String hotline = request.getParameter("hotLine").trim();
                String travelAgentAddress = request.getParameter("address").trim();
                String establishmentDateStr = request.getParameter("establishmentDate");
                String taxCode = request.getParameter("taxCode").trim();
                String firstName = request.getParameter("representativeFirstName").trim();
                String lastName = request.getParameter("representativeLastName").trim();
                String phone = request.getParameter("representativePhone").trim();
                String address = request.getParameter("representativeAddress").trim();
                String dobStr = request.getParameter("dob");
                String representativeIDCard = request.getParameter("representativeIDCard").trim();
                String gender = request.getParameter("gender").trim();
                String dateOfIssueStr = request.getParameter("dateOfIssue");
                email = (String) session.getAttribute("email");
                String password = (String) session.getAttribute("password");

                // Process step 2
                Map<String, String> validationErrors = processStep2(
                        travelAgentName, travelAgentGmail, hotline, travelAgentAddress, establishmentDateStr,
                        taxCode, firstName, lastName, phone, address, dobStr, representativeIDCard,
                        gender, dateOfIssueStr, businessLicensePath, frontIDCardPath, backIDCardPath
                );
                if (validationErrors.isEmpty()) {

                    // Create TravelAgent after validation
                    TravelAgent travelAgent = new TravelAgent(
                            travelAgentName,
                            travelAgentAddress,
                            travelAgentGmail,
                            hotline,
                            taxCode,
                            Date.valueOf(establishmentDateStr),
                            representativeIDCard,
                            Date.valueOf(dateOfIssueStr),
                            frontIDCardPath,
                            backIDCardPath,
                            businessLicensePath,
                            0, // userID
                            email,
                            4, // roleID
                            password,
                            firstName,
                            lastName,
                            Date.valueOf(dobStr),
                            gender,
                            address,
                            phone,
                            Date.valueOf(LocalDate.now()),
                            Date.valueOf(LocalDate.now()),
                            2 // status: pending approval
                    );

                    // Insert travel agent into database
                    travelAgentDAO.insertTravelAgent(travelAgent);

                    // Send confirmation email
                    try {
                        StringBuilder body = new StringBuilder();
                        body.append("<h2>Xác nhận đăng ký đại lý du lịch</h2>");
                        body.append("<p>Cảm ơn bạn đã đăng ký! Dưới đây là chi tiết:</p>");
                        body.append("<p><strong>Ngày đăng ký: </strong> ").append(LocalDate.now()).append("</p>");
                        body.append("<h3>Thông tin đăng ký: </h3>");
                        body.append("<p>Tên công ty: ").append(travelAgent.getTravelAgentName()).append("</p>");
                        body.append("<p>Email công ty: ").append(travelAgent.getTravelAgentGmail()).append("</p>");
                        body.append("<p>Số HotLine: ").append(travelAgent.getHotLine()).append("</p>");
                        body.append("<p>Địa chỉ: ").append(travelAgent.getTravelAgentAddress()).append("</p>");
                        body.append("<p>Ngày thành lập: ").append(travelAgent.getEstablishmentDate()).append("</p>");
                        body.append("<p>Mã số thuế: ").append(travelAgent.getTaxCode()).append("</p>");
                        body.append("<p>Họ: ").append(travelAgent.getFirstName()).append("</p>");
                        body.append("<p>Tên: ").append(travelAgent.getLastName()).append("</p>");
                        body.append("<p>Email: ").append(travelAgent.getGmail()).append("</p>");
                        body.append("<p>Số điện thoại: ").append(travelAgent.getPhone()).append("</p>");
                        body.append("<p>Địa chỉ: ").append(travelAgent.getAddress()).append("</p>");
                        body.append("<p>Ngày sinh: ").append(travelAgent.getDob()).append("</p>");
                        body.append("<p>Giới tính: ").append(travelAgent.getGender()).append("</p>");
                        body.append("<p>Số CCCD: ").append(travelAgent.getRepresentativeIDCard()).append("</p>");
                        body.append("<p>Ngày cấp: ").append(travelAgent.getDateOfIssue()).append("</p>");
                        body.append("<p>Yêu cầu đăng ký của bạn sẽ được duyệt trong vòng 48h. Cảm ơn bạn đã đăng ký làm đại lý du lịch với chúng tôi!</p>");

                        String[] attachments = {
                            savePath + File.separator + new File(travelAgent.getBusinessLicense()).getName(),
                            savePath + File.separator + new File(travelAgent.getFrontIDCard()).getName(),
                            savePath + File.separator + new File(travelAgent.getBackIDCard()).getName()
                        };
                        EmailSender.send(travelAgent.getGmail(), "Xác nhận đăng ký đại lý du lịch - " + LocalDate.now(), body.toString(), attachments);

                        session.invalidate(); // Clear session on success
                        request.setAttribute("confirmationStatus", "success");
                        request.setAttribute("confirmationMessage", "Đăng ký thành công! Vui lòng chờ duyệt trong 48h.");
                    } catch (MessagingException e) {
                        request.setAttribute("confirmationStatus", "error");
                        request.setAttribute("confirmationMessage", "Không thể gửi email xác nhận: " + e.getMessage());
                    }

                    request.getRequestDispatcher("view/agent/register2.jsp").forward(request, response);
                } else {
                    session.setAttribute("validationErrors", validationErrors);
                    session.setAttribute("travelAgentName", travelAgentName);
                    session.setAttribute("travelAgentGmail", travelAgentGmail);
                    session.setAttribute("hotline", hotline);
                    session.setAttribute("travelAgentAddress", travelAgentAddress);
                    session.setAttribute("establishmentDate", establishmentDateStr);
                    session.setAttribute("taxCode", taxCode);
                    session.setAttribute("firstName", firstName);
                    session.setAttribute("lastName", lastName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("address", address);
                    session.setAttribute("dob", dobStr);
                    session.setAttribute("representativeIDCard", representativeIDCard);
                    session.setAttribute("gender", gender);
                    session.setAttribute("dateOfIssue", dateOfIssueStr);
                    request.getRequestDispatcher("view/agent/register2.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Dịch vụ không hợp lệ: " + service);
                request.getRequestDispatcher("view/agent/register1.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error during registration: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing registration: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        }
    }

    private String processStep1(String email, String password, String confirmPassword) throws SQLException {
        // Validate email
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống!";
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
            return "Chỉ chấp nhận email hợp lệ như @gmail.com, @abc.edu, @company.com...";
        }
        if (travelAgentDAO.isEmailExists(email)) {
            return "Email đã được sử dụng!";
        }

        // Validate password
        if (password == null || password.trim().isEmpty() || confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }
        if (!password.equals(confirmPassword)) {
            return "Mật khẩu không khớp!";
        }
        String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
        if (!password.matches(passwordRegex)) {
            return "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!";
        }
        return "success";
    }

    private Map<String, String> processStep2(String travelAgentName, String travelAgentGmail, String hotline,
            String travelAgentAddress, String establishmentDateStr, String taxCode, String firstName,
            String lastName, String phone, String address, String dobStr, String representativeIDCard,
            String gender, String dateOfIssueStr, String businessLicensePath, String frontIDCardPath, String backIDCardPath) throws SQLException {
        Map<String, String> errors = new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate dobLocal = null;

        // Validate non-date fields
        if (travelAgentName == null || travelAgentName.trim().isEmpty()) {
            errors.put("travelAgentName", "Tên công ty không được để trống!");
        }
        if (travelAgentGmail == null || travelAgentGmail.trim().isEmpty()) {
            errors.put("travelAgentGmail", "Email công ty không được để trống!");
        } else if (!travelAgentGmail.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
            errors.put("travelAgentGmail", "Email công ty không hợp lệ!");
        } else if (travelAgentDAO.isTravelAgentEmailExists(travelAgentGmail)) {
            errors.put("travelAgentGmail", "Email công ty đã được sử dụng!");
        }
        if (hotline == null || hotline.trim().isEmpty()) {
            errors.put("hotLine", "Số HotLine không được để trống!");
        } else if (!hotline.matches("^0\\d{9}$")) {
            errors.put("hotLine", "Số HotLine phải bắt đầu bằng số 0 và có đúng 10 chữ số!");
        }
        if (travelAgentAddress == null || travelAgentAddress.trim().isEmpty()) {
            errors.put("travelAgentAddress", "Địa chỉ công ty không được để trống!");
        } else if (travelAgentAddress.length() < 5 || travelAgentAddress.length() > 200) {
            errors.put("travelAgentAddress", "Địa chỉ công ty phải từ 5 đến 200 ký tự!");
        }
        if (taxCode == null || taxCode.trim().isEmpty()) {
            errors.put("taxCode", "Mã số thuế không được để trống!");
        } else if (!taxCode.matches("\\d{10}|\\d{13}")) {
            errors.put("taxCode", "Mã số thuế phải có 10 hoặc 13 chữ số!");
        } else if (travelAgentDAO.isTaxCodeExists(taxCode)) {
            errors.put("taxCode", "Mã số thuế đã được sử dụng!");
        }
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
            errors.put("phone", "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số!");
        }
        if (address == null || address.trim().isEmpty()) {
            errors.put("address", "Địa chỉ không được để trống!");
        } else if (address.length() < 5 || address.length() > 200) {
            errors.put("address", "Địa chỉ phải từ 5 đến 200 ký tự!");
        }
        if (representativeIDCard == null || representativeIDCard.trim().isEmpty()) {
            errors.put("representativeIDCard", "Số căn cước công dân không được để trống!");
        } else if (!representativeIDCard.matches("^0\\d{11}$")) {
            errors.put("representativeIDCard", "Số căn cước công dân phải bắt đầu bằng số 0 và có đúng 12 chữ số!");
        } else if (travelAgentDAO.isIDCardExists(representativeIDCard)) {
            errors.put("representativeIDCard", "Số căn cước công dân đã được sử dụng!");
        }
        if (gender == null || gender.trim().isEmpty()) {
            errors.put("gender", "Giới tính không được để trống!");
        }

        // Validate establishmentDate
        if (establishmentDateStr == null || establishmentDateStr.trim().isEmpty()) {
            errors.put("establishmentDate", "Ngày thành lập không được để trống!");
        } else if (!establishmentDateStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errors.put("establishmentDate", "Ngày thành lập không hợp lệ!");
        } else {
            Date establishmentDate = Date.valueOf(establishmentDateStr);
            if (establishmentDate.after(Date.valueOf(currentDate))) {
                errors.put("establishmentDate", "Ngày thành lập phải trước ngày hiện tại!");
            }
        }

        // Validate dob
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

        // Validate dateOfIssue
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

        if (businessLicensePath == null || frontIDCardPath == null || backIDCardPath == null) {
            if (businessLicensePath == null) {
                errors.put("businessLicense", "Vui lòng tải lên giấy phép kinh doanh!");
            }
            if (frontIDCardPath == null) {
                errors.put("frontIDCard", "Vui lòng tải lên CCCD mặt trước!");
            }
            if (backIDCardPath == null) {
                errors.put("backIDCard", "Vui lòng tải lên CCCD mặt sau!");
            }
        }

        return errors;
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                return fileName.trim();
            }
        }
        return "";
    }
}
