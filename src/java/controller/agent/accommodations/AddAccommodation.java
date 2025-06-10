/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 *
 * Servlet for adding a new accommodation to the TravelAgentService system.
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
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;

/**
 * Handles HTTP requests to add a new accommodation with image upload.
 * Processes GET requests to display the add form and POST requests to save data.
 */
@MultipartConfig
public class AddAccommodation extends HttpServlet {

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
            out.println("<title>Servlet AddAccommodation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddAccommodation at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles GET requests to display the add accommodation form.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to add form
        request.getRequestDispatcher("view/agent/accommodation/AddAccommodation.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to add a new accommodation with image upload.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set character encoding for request and response
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Retrieve form parameters
            String name = request.getParameter("name");
            String roomIDStr = request.getParameter("roomID");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String rateStr = request.getParameter("rate");
            String checkInTime = request.getParameter("checkInTime");
            String checkOutTime = request.getParameter("checkOutTime");
            String description = request.getParameter("description");

            // Parse room ID
            int roomID = 0;
            if (roomIDStr != null && !roomIDStr.isEmpty()) {
                roomID = Integer.parseInt(roomIDStr);
            }

            // Validate mandatory fields
            if (name == null || name.trim().isEmpty()) {
                sendError(request, response, "Tên nơi ở không được để trống.");
                return;
            }

            if (phone == null || phone.trim().isEmpty()) {
                sendError(request, response, "Số điện thoại không được để trống.");
                return;
            }
            if (phone.length() != 10) {
                sendError(request, response, "Số điện thoại phải có đúng 10 số và phải bắt đầu bằng số 0.");
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

            // Validate rating
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

            // Validate description
            if (description == null || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "Vui lòng điền mô tả nhà hàng từ 10 từ trở lên.");
                return;
            }

            // Format time inputs
            if (checkInTime.length() == 5) {
                checkInTime += ":00";
            }
            if (checkOutTime.length() == 5) {
                checkOutTime += ":00";
            }

            // Handle image upload
            Part imagePart = request.getPart("image");
            String relativeFolder = "assets/img/accommodation";
            String realPath = getServletContext().getRealPath("/") + relativeFolder;

            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
                if (!uploadDir.exists()) {
                    throw new IOException("Không thể tạo thư mục: " + realPath);
                }
            }

            String fileName = System.currentTimeMillis() + "_" + Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String filePath = realPath + File.separator + fileName;
            System.out.println("Đường dẫn lưu ảnh: " + filePath);
            imagePart.write(filePath);

            // Verify file was saved
            File uploadedFile = new File(filePath);
            if (!uploadedFile.exists()) {
                throw new IOException("Lỗi: File không được lưu tại: " + filePath);
            }

            String imagePath = relativeFolder + "/" + fileName;

            // Insert accommodation into database
            AccommodationDAO dao = new AccommodationDAO();
            dao.insertAccommodation(roomID, name, imagePath, address, phone, description, rate, type, 1, checkInTime, checkOutTime);

            // Redirect to management page
            response.sendRedirect("managementaccommodation");

        } catch (Exception e) {
            // Log error and forward to error page
            e.printStackTrace();
            sendError(request, response, "Lỗi khi lưu ảnh hoặc thêm dữ liệu: " + e.getMessage());
        }
    }

    /**
     * Forwards to the add form with an error message.
     *
     * @param request      servlet request
     * @param response     servlet response
     * @param errorMessage the error message to display
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void sendError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("view/agent/accommodation/AddAccommodation.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}