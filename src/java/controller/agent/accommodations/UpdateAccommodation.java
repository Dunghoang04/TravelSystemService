/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 */
package controller.agent.accommodation;

import dao.AccommodationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.time.LocalTime;
import model.Accommodation;

/**
 * Handles HTTP requests to update accommodation details.
 * Supports GET for displaying the update form and POST for processing form submissions.
 */
@MultipartConfig
public class UpdateAccommodation extends HttpServlet {

    /**
     * Default method for processing HTTP requests (not used).
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateAccommodation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateAccommodation at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles GET requests to display the accommodation update form.
     * Retrieves accommodation details by service ID and forwards to the JSP.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Parse service ID from request
        int id = Integer.parseInt(request.getParameter("id"));
        AccommodationDAO accDAO = new AccommodationDAO();
        // Fetch accommodation details
        Accommodation accUpdate = accDAO.getAccommodationByServiceId(id);
        request.setAttribute("accommodationUpdate", accUpdate);
        // Forward to update form
        request.getRequestDispatcher("view/agent/accommodation/UpdateAccommdation.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to update accommodation details.
     * Validates input, processes file uploads, and updates the database.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve service ID
            String serviceIDStr = request.getParameter("serviceID");
            int id = Integer.parseInt(serviceIDStr);
            int roomID = id; // Assume roomID equals service ID

            // Retrieve form parameters
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String rateStr = request.getParameter("rate");
            String statusStr = request.getParameter("status");
            String checkInTimeStr = request.getParameter("checkInTime");
            String checkOutTimeStr = request.getParameter("checkOutTime");
            String description = request.getParameter("description");
            Part filePart = request.getPart("image");

            // Manage session for image file
            HttpSession session = request.getSession();
            String imageFileName = (String) session.getAttribute("imageFileName");

            // Handle file upload
            String uploadsDirPath = getServletContext().getRealPath("/") + "uploads";
            java.io.File uploadsDir = new java.io.File(uploadsDirPath);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdirs();
            }
            if (filePart != null && filePart.getSize() > 0) {
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String imagePath = "uploads/" + imageFileName;
                filePart.write(uploadsDirPath + java.io.File.separator + imageFileName);
                session.setAttribute("imageFileName", imageFileName);
            }

            // Prepare data for form repopulation on error
            AccommodationDAO accdao = new AccommodationDAO();
            Accommodation accommdationUpdate = accdao.getAccommodationByServiceId(id);
            request.setAttribute("accommodationUpdate", accommdationUpdate);

            // Validate mandatory fields
            // Image
            if (imageFileName == null || imageFileName.trim().isEmpty()) {
                sendError(request, response, "Vui lòng chọn ảnh cho nơi ở.");
                return;
            }

            // Name
            if (name == null || name.trim().isEmpty()) {
                sendError(request, response, "Tên nơi ở không được để trống.");
                return;
            }

            // Phone
            if (phone == null || phone.trim().isEmpty()) {
                sendError(request, response, "Số điện thoại không được để trống.");
                return;
            }
            if (phone.length() != 10) {
                sendError(request, response, "Số điện thoại phải có đúng 10 số.");
                return;
            }
            if (!phone.startsWith("0")) {
                sendError(request, response, "Số điện thoại phải bắt đầu bằng số 0.");
                return;
            }
            if (!phone.matches("^(03|05|07|09)[0-9]{8}$")) {
                sendError(request, response, "Số điện thoại phải bắt đầu bằng 03, 05, 07 hoặc 09.");
                return;
            }

            // Type
            if (type == null || type.trim().isEmpty()) {
                sendError(request, response, "Loại nhà hàng không được bỏ trống.");
                return;
            }

            // Status
            int status = Integer.parseInt(statusStr);

            // Rate
            float rate;
            try {
                rate = Float.parseFloat(rateStr);
                if (rate < 0 || rate > 10) {
                    sendError(request, response, "Điểm đánh giá phải nằm trong khoảng 1-10.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "Điểm đánh giá không hợp lệ.");
                return;
            }

            // Validate and format time
            if (checkInTimeStr.length() == 5) {
                checkInTimeStr += ":00";
            }
            if (checkOutTimeStr.length() == 5) {
                checkOutTimeStr += ":00";
            }
            try {
                LocalTime timeOpen = LocalTime.parse(checkInTimeStr);
                LocalTime timeClose = LocalTime.parse(checkOutTimeStr);
            } catch (Exception e) {
                sendError(request, response, "Thời gian không hợp lệ.");
                return;
            }

            // Description
            if (description == null || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "Vui lòng điền mô tả nơi ở từ 10 từ trở lên.");
                return;
            }

            // Update database
            String imagePath = "uploads/" + imageFileName;
            accdao.updateAccommodation(id, roomID, name, imagePath, address, phone, description, rate, type, status, checkInTimeStr, checkOutTimeStr);

            // Redirect on success
            response.sendRedirect("managementaccommodation");

        } catch (Exception e) {
            sendError(request, response, "Lỗi khi cập nhật nơi ở: " + e.getMessage());
        }
    }

    /**
     * Forwards to the update form with an error message.
     *
     * @param request      servlet request
     * @param response     servlet response
     * @param errorMessage the error message to display
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void sendError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorInput", errorMessage);
        request.getRequestDispatcher("view/agent/accommodation/UpdateAccommdation.jsp").forward(request, response);
    }
}