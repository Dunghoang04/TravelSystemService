/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24  1.0        Nguyễn Văn Vang   First implementation
 */
package controller.agent.accommodation;

import dao.AccommodationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import model.Accommodation;
import model.Room;
import jakarta.servlet.annotation.MultipartConfig;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "UpdateAccommodation", urlPatterns = {"/UpdateAccommodation"})
public class UpdateAccommodation extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("");
            out.println("Servlet UpdateAccommodation at " + request.getContextPath());
            out.println("");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        AccommodationDAO accDAO = new AccommodationDAO();
        Accommodation accUpdate = accDAO.getAccommodationByServiceId(id);
        List rooms;
        try {
            rooms = accDAO.getRoomsByServiceId(id);
            request.setAttribute("accommodationUpdate", accUpdate);
            request.setAttribute("rooms", rooms);
            request.getRequestDispatcher("view/agent/accommodation/updateAccommodation.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateAccommodation.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp?message=" + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String serviceIDStr = request.getParameter("serviceID");
            if (serviceIDStr == null || serviceIDStr.trim().isEmpty()) {
                sendError(request, response, "serviceID", "Mã dịch vụ (serviceID) không hợp lệ.", request.getParameterMap());
                return;
            }
            int id = Integer.parseInt(serviceIDStr);

            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String rateStr = request.getParameter("rate");
            String checkInTimeStr = request.getParameter("checkInTime");
            String checkOutTimeStr = request.getParameter("checkOutTime");
            String description = request.getParameter("description");
            String statusStr = request.getParameter("status");
            Part filePart = request.getPart("image");

            HttpSession session = request.getSession();
            String imageFileName = request.getParameter("existingImage"); // Lấy ảnh hiện tại từ form

            if (filePart != null && filePart.getSize() > 0) {
                // Nếu có ảnh mới, sử dụng ảnh mới
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadsDirPath = getServletContext().getRealPath("/") + "Uploads";
                java.io.File uploadsDir = new java.io.File(uploadsDirPath);
                if (!uploadsDir.exists()) {
                    uploadsDir.mkdirs();
                }
                filePart.write(uploadsDirPath + java.io.File.separator + imageFileName);
                session.setAttribute("imageFileName", imageFileName);
                imageFileName = "Uploads/" + imageFileName;
            } else {
                // Nếu không có ảnh mới, giữ nguyên ảnh hiện tại
                if (imageFileName != null && !imageFileName.isEmpty()) {
                    imageFileName = imageFileName; // Giữ nguyên đường dẫn ảnh cũ
                } else {
                    sendError(request, response, "image", "Vui lòng chọn ảnh hoặc giữ ảnh hiện tại.", request.getParameterMap());
                    return;
                }
            }

            AccommodationDAO accdao = new AccommodationDAO();
            Accommodation accommdationUpdate = accdao.getAccommodationByServiceId(id);
            List rooms = accdao.getRoomsByServiceId(id);
            request.setAttribute("accommodationUpdate", accommdationUpdate);
            request.setAttribute("rooms", rooms);

            // Validate name
            if (name == null || name.trim().isEmpty()) {
                sendError(request, response, "name", "Tên nơi ở không được để trống.", request.getParameterMap());
                return;
            }

            // Validate phone
            if (phone == null || phone.trim().isEmpty()) {
                sendError(request, response, "phone", "Số điện thoại không được để trống.", request.getParameterMap());
                return;
            }
            if (phone.length() != 10) {
                sendError(request, response, "phone", "Số điện thoại phải có đúng 10 số.", request.getParameterMap());
                return;
            }
            if (!phone.startsWith("0")) {
                sendError(request, response, "phone", "Số điện thoại phải bắt đầu bằng số 0.", request.getParameterMap());
                return;
            }
            if (!phone.matches("^(03|05|07|09)[0-9]{8}$")) {
                sendError(request, response, "phone", "Số điện thoại phải bắt đầu bằng 03, 05, 07 hoặc 09.", request.getParameterMap());
                return;
            }

            // Validate type
            if (type == null || type.trim().isEmpty()) {
                sendError(request, response, "type", "Loại nhà hàng không được bỏ trống.", request.getParameterMap());
                return;
            }

            // Validate rate
            float rate;
            try {
                rate = Float.parseFloat(rateStr);
                if (rate < 0 || rate > 10) {
                    sendError(request, response, "rate", "Điểm đánh giá phải nằm trong khoảng 1-10.", request.getParameterMap());
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "rate", "Điểm đánh giá không hợp lệ.", request.getParameterMap());
                return;
            }

            // Validate time
            String checkInTimeStrFinal = checkInTimeStr != null && !checkInTimeStr.isEmpty() ? checkInTimeStr + ":00" : "00:00:00";
            String checkOutTimeStrFinal = checkOutTimeStr != null && !checkOutTimeStr.isEmpty() ? checkOutTimeStr + ":00" : "00:00:00";

            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm:ss");
            try {
                if (!checkInTimeStrFinal.equals("00:00:00")) {
                    sdf24.parse(checkInTimeStrFinal);
                }
                if (!checkOutTimeStrFinal.equals("00:00:00")) {
                    sdf24.parse(checkOutTimeStrFinal);
                }
            } catch (Exception e) {
                sendError(request, response, "checkInTime", "Thời gian không hợp lệ. Vui lòng dùng định dạng HH:mm.", request.getParameterMap());
                return;
            }

            LocalTime timeOpen = LocalTime.parse(checkInTimeStrFinal.substring(0, 8));
            LocalTime timeClose = LocalTime.parse(checkOutTimeStrFinal.substring(0, 8));
            if (timeOpen.isAfter(timeClose)) {
                sendError(request, response, "checkInTime", "Thời gian nhận phòng phải trước thời gian trả phòng.", request.getParameterMap());
                return;
            }

            // Validate description
            if (description == null || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "description", "Vui lòng điền mô tả nơi ở từ 10 từ trở lên.", request.getParameterMap());
                return;
            }

            int status = Integer.parseInt(statusStr);
            String imagePath = imageFileName;

            accdao.updateAccommodation(id, name, imagePath, address, phone, description, rate, type, status, checkInTimeStrFinal, checkOutTimeStrFinal);
            request.setAttribute("success", "Cập nhật nơi ở thành công!");
            request.getRequestDispatcher("view/agent/accommodation/updateAccommodation.jsp").forward(request, response);

        } catch (Exception e) {
            sendError(request, response, "general", "Lỗi khi cập nhật nơi ở: " + e.getMessage(), request.getParameterMap());
        }
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response, String field, String errorMessage, java.util.Map<String, String[]> parameterMap)
            throws ServletException, IOException {
        request.setAttribute("error_" + field, errorMessage);
        // Lưu trữ các giá trị đã nhập để hiển thị lại
        parameterMap.forEach((key, value) -> request.setAttribute("input_" + key, value[0]));
        request.getRequestDispatcher("view/agent/accommodation/updateAccommodation.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}