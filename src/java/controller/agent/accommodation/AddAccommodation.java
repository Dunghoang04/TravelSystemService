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
import dao.IAccommodationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalTime;
import model.TravelAgent;

@WebServlet(name = "AddAccommodation", urlPatterns = {"/AddAccommodation"})
@jakarta.servlet.annotation.MultipartConfig
public class AddAccommodation extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("Servlet AddAccommodation at " + request.getContextPath() + "");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] arr = {"Khach sạn", "Nhà nghỉ", "Villa", "Homestay"};
        request.setAttribute("type", arr);

        request.getRequestDispatcher("view/agent/accommodation/addAccommodation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            IAccommodationDAO accDAO = new AccommodationDAO();
            HttpSession session = request.getSession();
            Part imagePart = request.getPart("image");
            String imageFileName = null;

            // Lưu tên file ảnh đã chọn vào session nếu có
            if (imagePart != null && imagePart.getSize() > 0) {
                imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                session.setAttribute("imageFileName", imageFileName);
            } else {
                imageFileName = (String) session.getAttribute("imageFileName");
            }

            TravelAgent travelAgent = (TravelAgent) session.getAttribute("agent");
            // Lưu giá trị đã nhập để giữ lại khi có lỗi
            request.setAttribute("name", request.getParameter("name"));
            request.setAttribute("type", request.getParameter("type"));
            request.setAttribute("address", request.getParameter("address"));
            request.setAttribute("phone", request.getParameter("phone"));
            request.setAttribute("rate", request.getParameter("rate"));
            request.setAttribute("checkInTime", request.getParameter("checkInTime"));
            request.setAttribute("checkOutTime", request.getParameter("checkOutTime"));
            request.setAttribute("description", request.getParameter("description"));
            // Lưu thông tin ảnh đã chọn vào request
            request.setAttribute("imageFileName", imageFileName);

            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String rateStr = request.getParameter("rate");
            String checkInTime = request.getParameter("checkInTime");
            String checkOutTime = request.getParameter("checkOutTime");
            String description = request.getParameter("description");

            validateName(name, request, response);

            validatePhoneNumber(phone, request, response);

            float rate;
            try {
                rate = Float.parseFloat(rateStr);
                if (rate < 0 || rate > 10) {
                    sendError(request, response, "rate", "Điểm đánh giá phải nằm trong khoảng 1-10.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "rate", "Điểm đánh giá không hợp lệ.");
                return;
            }

            // Kiểm tra và định dạng thời gian
            if (checkInTime == null || checkInTime.trim().isEmpty()) {
                sendError(request, response, "checkInTime", "Thời gian nhận phòng không được để trống.");
                return;
            }
            if (checkOutTime == null || checkOutTime.trim().isEmpty()) {
                sendError(request, response, "checkOutTime", "Thời gian trả phòng không được để trống.");
                return;
            }
            if (checkInTime.length() == 5) {
                checkInTime += ":00";
            } else if (!checkInTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
                sendError(request, response, "checkInTime", "Thời gian nhận phòng phải đúng định dạng HH:mm:ss.");
                return;
            }
            if (checkOutTime.length() == 5) {
                checkOutTime += ":00";
            } else if (!checkOutTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
                sendError(request, response, "checkOutTime", "Thời gian trả phòng phải đúng định dạng HH:mm:ss.");
                return;
            }

            // Thêm validate thời gian nhận phòng phải trước thời gian trả phòng
            LocalTime checkIn = LocalTime.parse(checkInTime);
            LocalTime checkOut = LocalTime.parse(checkOutTime);
            if (!checkIn.isBefore(checkOut)) {
                sendError(request, response, "checkInTime", "Thời gian nhận phòng phải trước thời gian trả phòng.");
                return;
            }

            // 7. Validate mô tả
            if (description.isEmpty() || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "description", "Vui lòng điền mô tả dịch vụ từ 10 từ trở lên.");
                return;
            }
            if (description.length() > 500) {
                sendError(request, response, "description", "Mô tả không được vượt quá 500 ký tự.");
                return;
            }

            // Chỉ kiểm tra imagePart nếu chưa có imageFileName trong session
            if (imageFileName == null && (imagePart == null || imagePart.getSize() == 0)) {
                sendError(request, response, "image", "Vui lòng chọn ảnh.");
                return;
            }

            String uploadPath = getServletContext().getRealPath("/") + "Uploads" + File.separator + imageFileName;
            File uploadDir = new File(uploadPath).getParentFile();
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Chỉ upload ảnh nếu có imagePart mới
            if (imagePart != null && imagePart.getSize() > 0) {
                imagePart.write(uploadPath);
            }
            String imagePath = "Uploads/" + imageFileName;

            accDAO.insertAccommodation(travelAgent.getTravelAgentID(), name, imagePath, address, phone, description, rate, type, 1, checkInTime, checkOutTime);
            request.setAttribute("success", "Thêm nơi ở thành công!");
            request.getRequestDispatcher("view/agent/accommodation/addAccommodation.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error_image", "Vui lòng chọn ảnh.");
            request.getRequestDispatcher("view/agent/accommodation/addAccommodation.jsp").forward(request, response);
        }
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response, String field, String message)
            throws ServletException, IOException {
        request.setAttribute("error_" + field, message);
        request.getRequestDispatcher("view/agent/accommodation/addAccommodation.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to add a new accommodation";
    }

    private void validateName(String name, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (name == null || name.trim().isEmpty()) {
            sendError(request, response, "name", "Tên nơi ở không được để trống.");
        } else if (name.length() > 100) {
            sendError(request, response, "name", "Tên nơi ở không được vượt quá 100 ký tự.");
        } else if (!name.matches("[a-zA-Z0-9\\s.,-]*")) {
            sendError(request, response, "name", "Tên nơi ở chỉ được chứa chữ cái, số, khoảng trắng và ký tự (.,-).");
        }
    }

    private void validatePhoneNumber(String phone, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (phone == null || phone.trim().isEmpty()) {
            sendError(request, response, "phone", "Số điện thoại không được để trống.");
        } else {
            if (phone.length() != 10) {
                sendError(request, response, "phone", "Số điện thoại phải có đúng 10 số.");
            }
            if (!phone.startsWith("0")) {
                sendError(request, response, "phone", "Số điện thoại phải bắt đầu bằng số 0.");
            }
            if (!phone.matches("^(03|05|07|09|08)[0-9]{8}$")) {
                sendError(request, response, "phone", "Số điện thoại phải bắt đầu bằng 03, 05, 07, 09 hoặc 08.");
            }
        }
    }
}