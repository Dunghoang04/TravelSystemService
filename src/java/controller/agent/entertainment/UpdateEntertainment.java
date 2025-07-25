/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelSystemService
 * Description: Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-14   1.1      Hoang Tuan Dung       First implementation
 * [Not specified in original code]
 */

package controller.agent.entertainment;

import dao.EntertainmentDAO;
import dao.IEntertainmentDAO;
import dao.IService;
import dao.ServiceDao;
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
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalTime;
import model.Entertainment;

/**
 * Handles the update operation for entertainment records in the agent module.
 * Processes GET requests to display the update form and POST requests to update
 * data.
 *
 * Project: TravelAgentService Version: 1.0 Date: 2025-06-07 Bugs: No known
 * issues.
 *
 * Record of Change: DATE Version AUTHOR DESCRIPTION 2025-06-07 1.0 Hoang Tuan
 * Dung First implementation 2025-06-08 1.1 Hoang Tuan Dung Enhanced validation
 * and error handling
 *
 * @author Hoang Tuan Dung
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class UpdateEntertainment extends HttpServlet {

//    private static final Logger LOG=Logger.getLogger(UpdateEntertainmentServlet.getName());
    private static final String UPLOAD_DIR = "assets/img-entertainment";

    /**
     * Handles the HTTP GET method to display the entertainment update form.<br>
     * Retrieves entertainment data by ID and forwards to the update JSP.<br>
     *
     * @param request servlet request containing the service ID parameter<br>
     * @param response servlet response<br>
     * @throws ServletException if a servlet-specific error occurs<br>
     * @throws IOException if an I/O error occurs<br>
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IEntertainmentDAO entertainmentDAO = new EntertainmentDAO();
        IService serviceDao = new ServiceDao();
        String currentPageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "1"; // Default to page 1
        try {
            String serviceIdParam = request.getParameter("id");
            if (serviceIdParam == null || serviceIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã dịch vụ không hợp lệ");
            }
            int id = Integer.parseInt(serviceIdParam);
            Entertainment entertainmentUpdate = entertainmentDAO.getEntertainmentByServiceId(id);
            if (entertainmentUpdate == null) {
                throw new IllegalArgumentException("Không tìm thấy dịch vụ giải trí với id = " + id);
            }
            int serviceUsed = serviceDao.countServiceUsed(entertainmentDAO.getEntertainmentByServiceId(id).getServiceId());
            int currentPage;
            try {
                currentPage = Integer.parseInt(currentPageParam);
                if (currentPage <= 0) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng số trang không hợp lệ: " + e.getMessage());
                return;
            }
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setGroupingUsed(true);
            String formattedTicketPrice = numberFormat.format(entertainmentUpdate.getTicketPrice());

            // Đặt các thuộc tính riêng lẻ vào request thay vì đối tượng Entertainment
            request.setAttribute("serviceId", entertainmentUpdate.getServiceId());
            request.setAttribute("name", entertainmentUpdate.getName());
            request.setAttribute("image", entertainmentUpdate.getImage());
            request.setAttribute("address", entertainmentUpdate.getAddress());
            request.setAttribute("phone", entertainmentUpdate.getPhone());
            request.setAttribute("description", entertainmentUpdate.getDescription());
            request.setAttribute("rate", entertainmentUpdate.getRate());
            request.setAttribute("type", entertainmentUpdate.getType());
            request.setAttribute("status", entertainmentUpdate.getStatus());
            request.setAttribute("timeOpen", entertainmentUpdate.getTimeOpen() != null ? entertainmentUpdate.getTimeOpen().toString().substring(0, 5) : "");
            request.setAttribute("timeClose", entertainmentUpdate.getTimeClose() != null ? entertainmentUpdate.getTimeClose().toString().substring(0, 5) : "");
            request.setAttribute("dayOfWeekOpen", entertainmentUpdate.getDayOfWeekOpen());
            request.setAttribute("ticketPrice", formattedTicketPrice);
            request.setAttribute("page", currentPage);
            if (serviceUsed > 0) {
                request.setAttribute("serviceUsed", "Dịch vụ đã được sử dụng");
            }

            request.getRequestDispatcher("view/agent/entertainment/updateEntertainment.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            sendError(request, response, "errorSystem", "ID dịch vụ không hợp lệ.");
        } catch (SQLException e) {
            sendError(request, response, "errorSystem", "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            sendError(request, response, "errorSystem", e.getMessage());
        }

    }

    /**
     * Handles the HTTP POST method to update an entertainment record.<br>
     * Validates input data, handles image upload, and updates the database.<br>
     *
     * @param request servlet request containing updated entertainment data<br>
     * @param response servlet response<br>
     * @throws ServletException if a servlet-specific error occurs<br>
     * @throws IOException if an I/O error occurs<br>
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IEntertainmentDAO entertainmentDAO = new EntertainmentDAO();
        String currentPageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "1"; // Default to page 1

        try {
            String serviceIdParam = request.getParameter("serviceId");
            String statusType = request.getParameter("statusType"); // Preserve dropdown filter
            if (serviceIdParam == null || serviceIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("ID dịch vụ không hợp lệ");
            }
            int id = Integer.parseInt(serviceIdParam);
            String name = request.getParameter("name") != null ? request.getParameter("name").trim() : "";
            String type = request.getParameter("type") != null ? request.getParameter("type").trim() : "";
            String address = request.getParameter("address") != null ? request.getParameter("address").trim() : "";
            String phone = request.getParameter("phone") != null ? request.getParameter("phone").trim() : "";
            String rateStr = request.getParameter("rate") != null ? request.getParameter("rate").trim() : "";
            String timeOpenStr = request.getParameter("timeopen") != null ? request.getParameter("timeopen").trim() : "";
            String timeCloseStr = request.getParameter("timeclose") != null ? request.getParameter("timeclose").trim() : "";
            String[] dayOfWeekOpen = request.getParameterValues("dayOfWeekOpen");
            String ticketPriceStr = request.getParameter("ticketPrice") != null ? request.getParameter("ticketPrice").trim() : "";
            String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
            String statusStr = request.getParameter("status"); // Get status from form
            Part filePart = request.getPart("image");
            String existingImage = request.getParameter("existingImage") != null ? request.getParameter("existingImage").trim() : "";

            // Lưu dữ liệu đã nhập vào request để hiển thị lại khi validate thất bại
            request.setAttribute("serviceId", serviceIdParam);
            request.setAttribute("name", name);
            request.setAttribute("type", type);
            request.setAttribute("address", address);
            request.setAttribute("phone", phone);
            request.setAttribute("rate", rateStr);
            request.setAttribute("timeOpen", timeOpenStr);
            request.setAttribute("timeClose", timeCloseStr);
            request.setAttribute("dayOfWeekOpen", dayOfWeekOpen != null ? String.join(" , ", dayOfWeekOpen) : "");
            request.setAttribute("ticketPrice", ticketPriceStr);
            request.setAttribute("description", description);
            request.setAttribute("status", statusStr);
            request.setAttribute("image", existingImage); // Giữ lại đường dẫn ảnh hiện tại
            request.setAttribute("page", currentPageParam);

            // Validate status
            int status;
            try {
                status = (statusStr != null && !statusStr.isEmpty()) ? Integer.parseInt(statusStr) : 1; // Mặc định status nếu không có
                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorStatus", "Trạng thái không hợp lệ.");
                return;
            }

            int currentPage;
            try {
                currentPage = Integer.parseInt(currentPageParam);
                if (currentPage <= 0) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng số trang không hợp lệ: " + e.getMessage());
                return;
            }

            // Define relative path for image storage
            String imageFileName = existingImage.isEmpty() ? null : Paths.get(existingImage).getFileName().toString();
            String relativeImagePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                relativeImagePath = UPLOAD_DIR + "/" + imageFileName;
                String uploadsDirPath = getServletContext().getRealPath("/") + UPLOAD_DIR;
                File uploadsDir = new File(uploadsDirPath);
                if (!uploadsDir.exists()) {
                    if (!uploadsDir.mkdirs()) {
                        throw new IOException("Không thể tạo thư mục lưu trữ ảnh.");
                    }
                }
                filePart.write(uploadsDirPath + File.separator + imageFileName);
                request.setAttribute("image", relativeImagePath); // Cập nhật đường dẫn ảnh mới
            } else if (imageFileName == null || imageFileName.trim().isEmpty()) {
                sendError(request, response, "errorImage", "Vui lòng chọn ảnh cho dịch vụ.");
                return;
            } else {
                relativeImagePath = UPLOAD_DIR + "/" + imageFileName; // Use existing image
            }

            // Validation: Check max lengths
            if (name.length() > 255) {
                sendError(request, response, "errorName", "Tên không được vượt quá 255 ký tự.");
                return;
            }
            if (address.length() > 255) {
                sendError(request, response, "errorAddress", "Địa chỉ không được vượt quá 255 ký tự.");
                return;
            }

            // Validation: name
            if (name.isEmpty()) {
                sendError(request, response, "errorName", "Tên dịch vụ không được để trống.");
                return;
            }

            // Validation: phone
            if (phone.isEmpty()) {
                sendError(request, response, "errorPhone", "Số điện thoại không được để trống.");
                return;
            }
            if (phone.length() != 10 || !phone.startsWith("0") || !phone.matches("^(0)[0-9]{9}$")) {
                sendError(request, response, "errorPhone", "Số điện thoại phải có 10 số, bắt đầu bằng 0.");
                return;
            }

            // Validation: type
            if (type.isEmpty()) {
                sendError(request, response, "errorType", "Loại dịch vụ giải trí không được bỏ trống.");
                return;
            }

            // Validation: rate
            float rate;
            if (rateStr.isEmpty()) {
                sendError(request, response, "errorRate", "Điểm đánh giá không được để trống.");
                return;
            }
            try {
                rate = Float.parseFloat(rateStr);
                if (rate < 0 || rate > 10) {
                    sendError(request, response, "errorRate", "Điểm đánh giá phải nằm trong khoảng 0-10.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorRate", "Điểm đánh giá không hợp lệ.");
                return;
            }

            // Validation: time
            if (timeOpenStr.length() == 5) {
                timeOpenStr += ":00";
            }
            if (timeCloseStr.length() == 5) {
                timeCloseStr += ":00";
            }
            try {
                LocalTime timeOpen = LocalTime.parse(timeOpenStr);
                LocalTime timeClose = LocalTime.parse(timeCloseStr);
                if (Duration.between(timeOpen, timeClose).toHours() < 1) {
                    sendError(request, response, "errorTime", "Thời gian mở cửa phải ít nhất 1 tiếng.");
                    return;
                }
            } catch (Exception e) {
                sendError(request, response, "errorTime", "Thời gian mở/đóng cửa không hợp lệ.");
                return;
            }

            // Validation: dayOfWeek
            String dayOfWeekAll = "";
            if (dayOfWeekOpen != null && dayOfWeekOpen.length > 0) {
                dayOfWeekAll = String.join(" , ", dayOfWeekOpen);
            } else {
                sendError(request, response, "errorDayOfWeek", "Vui lòng chọn ít nhất 1 ngày mở cửa.");
                return;
            }

            // Validation: ticketPrice
            if (ticketPriceStr.isEmpty()) {
                sendError(request, response, "errorTicketPrice", "Giá vé không được để trống.");
                return;
            }
            // Không cho phép chứa dấu chấm
            if (ticketPriceStr.contains(".")) {
                sendError(request, response, "errorTicketPrice", "Giá vé không được chứa dấu chấm (chỉ dùng dấu phẩy để phân tách hàng nghìn).");
                return;
            }
            String cleanedTicketPriceStr = ticketPriceStr.replaceAll(",", "");
            float ticketPrice;
            try {
                ticketPrice = Float.parseFloat(cleanedTicketPriceStr);
                if (ticketPrice < 0) {
                    sendError(request, response, "errorTicketPrice", "Giá vé không được là số âm.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorTicketPrice", "Giá vé phải là số hợp lệ (ví dụ: 500 hoặc 500,000).");
                return;
            }

            // Validation: description
            if (description.isEmpty() || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "errorDescription", "Mô tả dịch vụ phải có ít nhất 10 từ.");
                return;
            }

            // Update database
            entertainmentDAO.updateEntertainment(id, name, relativeImagePath, address, phone, description, rate, type, status, timeOpenStr, timeCloseStr, dayOfWeekAll, ticketPrice);
            request.setAttribute("success", "Cập nhập giải trí thành công");
            request.getRequestDispatcher("view/agent/entertainment/updateEntertainment.jsp").forward(request, response);
        } catch (SQLException e) {
            sendError(request, response, "errorSystem", "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (IOException e) {
            sendError(request, response, "errorSystem", "Lỗi khi tải lên ảnh: " + e.getMessage());
        } catch (Exception e) {
            sendError(request, response, "errorSystem", "Đã xảy ra lỗi bất ngờ: " + e.getMessage());
        }
    }

    /**
     * Sends an error message to the update JSP page.<br>
     *
     * @param request servlet request to set the error attribute<br>
     * @param response servlet response<br>
     * @param errorMessage the error message to display<br>
     * @throws ServletException if a servlet-specific error occurs<br>
     * @throws IOException if an I/O error occurs<br>
     */
    private void sendError(HttpServletRequest request, HttpServletResponse response, String typeError, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute(typeError, errorMessage);
        request.getRequestDispatcher("view/agent/entertainment/updateEntertainment.jsp").forward(request, response);
    }

}
