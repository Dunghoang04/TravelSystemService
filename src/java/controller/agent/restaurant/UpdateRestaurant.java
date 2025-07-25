/*
 * Copyright(C) 2025, GROUP 6.
 * Restaurant Management System:
 *  A web application for managing restaurant information.
 *
 * Record of change:
 * DATE            Version    AUTHOR            DESCRIPTION
 * 2025-06-21      1.0        Hoang Tuan Dung   Initial implementation
 */
/**
 * Servlet for updating restaurant information in the agent module. Handles GET
 * requests to display the update form and POST requests to process updates.
 * Supports image uploads and input validation based on database schema.
 *
 * Project: TravelAgentService
 * Version: 1.1
 * Date: 2025-06-22
 * Bugs: No known issues. Potential issue with time range validation across days.
 *
 * @author Hoang Tuan Dung
 */
package controller.agent.restaurant;

import dao.IRestaurantDAO;
import dao.IService;
import dao.RestaurantDAO;
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
import model.Restaurant;

/**
 * Servlet for updating restaurant information in the agent module. Handles GET
 * requests to display the update form and POST requests to process updates.
 * Supports image uploads and input validation.
 *
 * @author Hoang Tuan Dung
 */
@MultipartConfig
public class UpdateRestaurant extends HttpServlet {

    // Directory for storing uploaded restaurant images
    private static final String UPLOAD_DIR = "assets/img-restaurant";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateRestaurant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRestaurant at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles HTTP GET requests to display the restaurant update form.
     * Validates the service ID, retrieves restaurant data, and forwards to the
     * update JSP page.
     *
     * @param request Servlet request containing the restaurant ID
     * @param response Servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IRestaurantDAO restaurantDao = new RestaurantDAO();
        IService serviceDao = new ServiceDao();
        String currentPageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "1";

        try {
            // Validate and parse service ID
            String serviceIdParam = request.getParameter("id");
            if (serviceIdParam == null || serviceIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã nhà hàng không hợp lệ.");
            }
            int id = Integer.parseInt(serviceIdParam);
            if (id <= 0) {
                throw new IllegalArgumentException("Mã nhà hàng phải là số nguyên dương.");
            }
            int serviceUsed = serviceDao.countServiceUsed(restaurantDao.getRestaurantByServiceId(id).getServiceId());

            // Retrieve restaurant details
            Restaurant updateRestaurant = restaurantDao.getRestaurantByServiceId(id);
            if (updateRestaurant == null) {
                throw new IllegalArgumentException("Không tìm thấy nhà hàng có mã = " + id + ".");
            }

            // Validate and set current page
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

            // Set individual attributes instead of Restaurant object
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setGroupingUsed(true);
            request.setAttribute("serviceId", updateRestaurant.getServiceId());
            request.setAttribute("name", updateRestaurant.getName());
            request.setAttribute("image", updateRestaurant.getImage());
            request.setAttribute("address", updateRestaurant.getAddress());
            request.setAttribute("phone", updateRestaurant.getPhone());
            request.setAttribute("description", updateRestaurant.getDescription());
            request.setAttribute("rate", updateRestaurant.getRate());
            request.setAttribute("type", updateRestaurant.getType());
            request.setAttribute("status", updateRestaurant.getStatus());
            request.setAttribute("timeOpen", updateRestaurant.getTimeOpen() != null ? updateRestaurant.getTimeOpen().substring(0, 5) : "");
            request.setAttribute("timeClose", updateRestaurant.getTimeClose() != null ? updateRestaurant.getTimeClose().substring(0, 5) : "");
            request.setAttribute("page", currentPage);
            if (serviceUsed > 0) {
                request.setAttribute("serviceUsed", "Dịch vụ đã được sử dụng");
            }
            request.getRequestDispatcher("view/agent/restaurant/updateRestaurant.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            sendError(request, response, "errorSystem", "Mã nhà hàng không hợp lệ.");
        } catch (SQLException e) {
            sendError(request, response, "errorSystem", "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            sendError(request, response, "errorSystem", e.getMessage());
        }
    }

    /**
     * Handles HTTP POST requests to update restaurant data. Validates input
     * fields, handles image uploads, and updates the database.
     *
     * @param request Servlet request containing updated restaurant data
     * @param response Servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IRestaurantDAO restaurantDao = new RestaurantDAO();
        String currentPageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "1";
        
        try {
            // Retrieve and validate form parameters
            String serviceIdParam = request.getParameter("serviceId");
            if (serviceIdParam == null || serviceIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã nhà hàng không hợp lệ.");
            }
            int id = Integer.parseInt(serviceIdParam);
            if (id <= 0) {
                throw new IllegalArgumentException("Mã nhà hàng phải là số nguyên dương.");
            }

            String name = request.getParameter("name") != null ? request.getParameter("name").trim() : "";
            String address = request.getParameter("address") != null ? request.getParameter("address").trim() : "";
            String type = request.getParameter("type") != null ? request.getParameter("type").trim() : "";
            String phone = request.getParameter("phone") != null ? request.getParameter("phone").trim() : "";
            String timeOpenStr = request.getParameter("timeopen") != null ? request.getParameter("timeopen").trim() : "";
            String timeCloseStr = request.getParameter("timeclose") != null ? request.getParameter("timeclose").trim() : "";
            String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
            String rateStr = request.getParameter("rate") != null ? request.getParameter("rate").trim() : "";
            String statusParam = request.getParameter("status");
            Part filePart = request.getPart("image");
            String existingImage = request.getParameter("existingImage") != null ? request.getParameter("existingImage").trim() : "";

            // Set attributes to preserve user input on validation failure
            request.setAttribute("serviceId", serviceIdParam);
            request.setAttribute("name", name);
            request.setAttribute("image", existingImage);
            request.setAttribute("address", address);
            request.setAttribute("phone", phone);
            request.setAttribute("description", description);
            request.setAttribute("rate", rateStr);
            request.setAttribute("type", type);
            request.setAttribute("status", statusParam != null ? statusParam : "1");
            request.setAttribute("timeOpen", timeOpenStr);
            request.setAttribute("timeClose", timeCloseStr);
            request.setAttribute("page", currentPageParam);

            // Validate status
            int status;
            try {
                status = (statusParam != null && !statusParam.isEmpty()) ? Integer.parseInt(statusParam) : 1;
                if (status != 0 && status != 1) {
                    sendError(request, response, "errorStatus", "Trạng thái phải là 0 hoặc 1.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorStatus", "Trạng thái không hợp lệ.");
                return;
            }

            // Validate and set current page
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

            // Handle image upload
            String imageFileName = existingImage.isEmpty() ? null : Paths.get(existingImage).getFileName().toString();
            String relativeImagePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                relativeImagePath = UPLOAD_DIR + "/" + imageFileName;
                String uploadsDirPath = getServletContext().getRealPath("/") + UPLOAD_DIR;
                File uploadsDir = new File(uploadsDirPath);
                if (!uploadsDir.exists()) {
                    if (!uploadsDir.mkdirs()) {
                        throw new IOException("Không thể tạo thư mục lưu trữ ảnh: " + uploadsDirPath);
                    }
                }
                filePart.write(uploadsDirPath + File.separator + imageFileName);
                request.setAttribute("image", relativeImagePath); // Update image path for form
            } else if (imageFileName == null || imageFileName.trim().isEmpty()) {
                sendError(request, response, "errorImage", "Vui lòng chọn ảnh cho nhà hàng.");
                return;
            } else {
                relativeImagePath = UPLOAD_DIR + "/" + imageFileName;
            }

            // Validate input lengths
            if (name.length() > 255) {
                sendError(request, response, "errorName", "Tên không được vượt quá 255 ký tự.");
                return;
            }

            if (phone.length() > 20) {
                sendError(request, response, "errorPhone", "Số điện thoại không được vượt quá 20 ký tự.");
                return;
            }
            if (description.length() > 4000) {
                sendError(request, response, "errorDescription", "Mô tả không được vượt quá 4000 ký tự.");
                return;
            }

            // Validate name
            if (name.isEmpty()) {
                sendError(request, response, "errorName", "Tên nhà hàng không được để trống.");
                return;
            }

            // Validate phone number
            if (phone.isEmpty()) {
                sendError(request, response, "errorPhone", "Số điện thoại không được để trống.");
                return;
            }
            if (phone.length() != 10 || !phone.matches("^(0)[0-9]{9}$")) {
                sendError(request, response, "errorPhone", "Số điện thoại phải có 10 số và bắt đầu bằng 0.");
                return;
            }

            // Validate restaurant type
            if (type.isEmpty()) {
                sendError(request, response, "errorType", "Loại nhà hàng không được bỏ trống.");
                return;
            }

            // Validate rating
            float rate;
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

            // Validate opening and closing times
            if (timeOpenStr.length() == 5) {
                timeOpenStr += ":00";
            }
            if (timeCloseStr.length() == 5) {
                timeCloseStr += ":00";
            }
            try {
                LocalTime timeOpen = LocalTime.parse(timeOpenStr);
                LocalTime timeClose = LocalTime.parse(timeCloseStr);
                Duration duration = Duration.between(timeOpen, timeClose);
                if (duration.isNegative()) {
                    duration = duration.plus(Duration.between(timeClose, LocalTime.of(23, 59, 59)));
                }
                if (duration.toHours() < 1) {
                    sendError(request, response, "errorTime", "Thời gian mở cửa phải cách thời gian đóng cửa ít nhất 1 giờ.");
                    return;
                }
            } catch (Exception e) {
                sendError(request, response, "errorTime", "Thời gian mở/đóng cửa không hợp lệ.");
                return;
            }

            // Validate description
            if (description.isEmpty() || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "errorDescription", "Mô tả nhà hàng phải có ít nhất 10 từ.");
                return;
            }

            // Update restaurant in the database
            restaurantDao.updateRestaurant(id, name, relativeImagePath, address, phone, description, rate, type, status, timeOpenStr, timeCloseStr);
            request.setAttribute("success", "Cập nhập nhà hàng thành công");
            request.getRequestDispatcher("view/agent/restaurant/updateRestaurant.jsp").forward(request, response);
        } catch (SQLException e) {
            if (e.getMessage().contains("FOREIGN KEY")) {
                sendError(request, response, "errorSystem", "Lỗi: Mã nhà hàng không hợp lệ hoặc không tồn tại trong danh sách dịch vụ.");
            } else {
                sendError(request, response, "errorSystem", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            }
        } catch (IOException e) {
            sendError(request, response, "errorSystem", "Lỗi khi tải lên ảnh: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            sendError(request, response, "errorSystem", e.getMessage());
        } catch (Exception e) {
            sendError(request, response, "errorSystem", "Đã xảy ra lỗi bất ngờ: " + e.getMessage());
        }
    }

    /**
     * Forwards the request to the update JSP page with an error message.
     *
     * @param request Servlet request to set error attributes
     * @param response Servlet response
     * @param typeError The type of error (e.g., errorName, errorSystem)
     * @param errorMessage The error message to display
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void sendError(HttpServletRequest request, HttpServletResponse response, String typeError, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute(typeError, errorMessage);
        request.getRequestDispatcher("view/agent/restaurant/updateRestaurant.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
