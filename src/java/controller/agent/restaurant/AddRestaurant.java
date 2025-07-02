/*
 * Copyright(C) 2025, GROUP 6.
 * Restaurant Management System:
 *  A web application for managing restaurant information.
 *
 * Record of change:
 * DATE            Version    AUTHOR            DESCRIPTION
 * 2025-06-21      1.0        Hoang Tuan Dung   Initial implementation
 * 2025-06-22      1.1        Hoang Tuan Dung   Fixed image preview persistence after validation errors
 */

/**
 * The AddRestaurant servlet handles HTTP requests to add a new restaurant to the system.
 * It processes form data from addRestaurant.jsp, validates inputs against database schema,
 * uploads restaurant images, and interacts with RestaurantDAO to persist data. All input
 * data is trimmed to remove leading/trailing spaces before processing. The servlet throws
 * exceptions for database or I/O errors to be handled by the error page.
 *
 * <p>
 * Bugs: Fixed issue where image preview disappeared after validation errors.
 *
 * @author Hoang Tuan Dung
 */
package controller.agent.restaurant;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dao.IRestaurantDAO;
import dao.RestaurantDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import model.TravelAgent;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AddRestaurant extends HttpServlet {

    // Constant for image upload directory
    private static final String UPLOAD_DIRECTORY = "assets/img-restaurant";

    /**
     * Handles the HTTP GET method by forwarding to the add restaurant form page.
     * Clears the session's imageFileNameRestaurant attribute to reset the form state.
     *
     * @param request the HttpServletRequest object containing client request data
     * @param response the HttpServletResponse object for sending response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("imageFileNameRestaurant"); // Clear session image data
        request.getRequestDispatcher("view/agent/restaurant/addRestaurant.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method to process form data for adding a new restaurant.
     * Validates input fields against database schema, uploads the restaurant image,
     * and inserts data into the database via RestaurantDAO. Preserves form data and
     * image preview in case of errors and forwards back to the form with appropriate
     * error messages.
     *
     * @param request the HttpServletRequest object containing form data and file part
     * @param response the HttpServletResponse object for sending response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize DAO and session
        IRestaurantDAO restaurantDAO = new RestaurantDAO();
        HttpSession session = request.getSession();
        TravelAgent travelAgent = (TravelAgent) session.getAttribute("agent");
        String imageFileName = (String) session.getAttribute("imageFileNameRestaurant");

        // Retrieve and trim form inputs
        String name = request.getParameter("name") != null ? request.getParameter("name").trim() : "";
        String address = request.getParameter("address") != null ? request.getParameter("address").trim() : "";
        String type = request.getParameter("type") != null ? request.getParameter("type").trim() : "";
        String timeOpenStr = request.getParameter("timeopen") != null ? request.getParameter("timeopen").trim() : "";
        String timeCloseStr = request.getParameter("timeclose") != null ? request.getParameter("timeclose").trim() : "";
        String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
        String phone = request.getParameter("phone") != null ? request.getParameter("phone").trim() : "";
        String rateStr = request.getParameter("rate") != null ? request.getParameter("rate").trim() : "";
        Part filePart = request.getPart("image");

        // Preserve form data for error case
        request.setAttribute("name", name);
        request.setAttribute("address", address);
        request.setAttribute("type", type);
        request.setAttribute("timeOpenStr", timeOpenStr);
        request.setAttribute("timeCloseStr", timeCloseStr);
        request.setAttribute("description", description);
        request.setAttribute("phone", phone);
        request.setAttribute("rateStr", rateStr);

        try {
            // Validate and process image upload
            String relativeImagePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                relativeImagePath = UPLOAD_DIRECTORY + "/" + imageFileName;
                String uploadDirPath = getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadDirPath);
                if (!uploadDir.exists()) {
                    if (!uploadDir.mkdirs()) {
                        throw new IOException("Failed to create upload directory.");
                    }
                }
                filePart.write(uploadDirPath + File.separator + imageFileName);
                session.setAttribute("imageFileNameRestaurant", imageFileName);
                request.setAttribute("imageFileNameRestaurant", imageFileName); // Set for JSP
            } else if (imageFileName == null || imageFileName.trim().isEmpty()) {
                request.setAttribute("imageFileNameRestaurant", "");
                sendError(request, response, "errorImage", "Vui lòng chọn ảnh cho nhà hàng.");
                return;
            } else {
                relativeImagePath = UPLOAD_DIRECTORY + "/" + imageFileName; // Use existing image
                request.setAttribute("imageFileNameRestaurant", imageFileName); // Set for JSP
            }

            // Validate name (max 255 characters)
            if (name.isEmpty()) {
                sendError(request, response, "errorName", "Tên nhà hàng không được để trống.");
                return;
            }
            if (name.length() > 255) {
                sendError(request, response, "errorName", "Tên nhà hàng không được vượt quá 255 ký tự.");
                return;
            }

            // Validate phone 
            if (phone.isEmpty()) {
                sendError(request, response, "errorPhone", "Số điện thoại không được để trống.");
                return;
            }
            if (!phone.matches("^0[0-9]{9}$")) {
                sendError(request, response, "errorPhone", "Số điện thoại phải có 10 số và bắt đầu bằng 0.");
                return;
            }

            // Validate rate
            float rate;
            try {
                rate = Float.parseFloat(rateStr);
                if (rate < 0 || rate > 10) {
                    sendError(request, response, "errorRate", "Điểm đánh giá phải từ 0 đến 10.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorRate", "Điểm đánh giá phải là một số hợp lệ.");
                return;
            }

            // Validate time
            if (timeOpenStr.length() == 5) {
                timeOpenStr += ":00";
            }
            if (timeCloseStr.length() == 5) {
                timeCloseStr += ":00";
            }
            LocalTime timeOpen, timeClose;
            try {
                timeOpen = LocalTime.parse(timeOpenStr);
                timeClose = LocalTime.parse(timeCloseStr);
            } catch (Exception e) {
                sendError(request, response, "errorTime", "Thời gian không hợp lệ.");
                return;
            }
            // Handle case where timeClose is earlier than timeOpen
            Duration duration = Duration.between(timeOpen, timeClose);
            if (duration.toHours() < 1) {
                sendError(request, response, "errorTime", "Thời gian mở cửa phải cách thời gian đóng cửa ít nhất 1 giờ.");
                return;
            }

            // Validate description
            if (description.isEmpty()) {
                sendError(request, response, "errorDescription", "Mô tả nhà hàng không được để trống.");
                return;
            }
            if (description.length() > 4000) {
                sendError(request, response, "errorDescription", "Mô tả nhà hàng không được vượt quá 4000 ký tự.");
                return;
            }
            String[] words = description.trim().split("\\s+");
            if (words.length < 10) {
                sendError(request, response, "errorDescription", "Mô tả nhà hàng phải có ít nhất 10 từ.");
                return;
            }

            // Insert into database
            restaurantDAO.insertRestaurantFull(travelAgent.getUserID(), name, relativeImagePath, address, phone,
                    description, rate, type, 1, timeOpenStr, timeCloseStr);
            session.removeAttribute("imageFileNameRestaurant"); // Clear session after success
            request.setAttribute("success", "Thêm nhà hàng thành công.");
            request.getRequestDispatcher("view/agent/restaurant/addRestaurant.jsp").forward(request, response);
        } catch (SQLServerException e) {
            sendError(request, response, "errorSystem", "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (IOException e) {
            sendError(request, response, "errorSystem", "Lỗi tải ảnh lên: " + e.getMessage());
        } catch (Exception e) {
            sendError(request, response, "errorSystem", "Lỗi không mong muốn: " + e.getMessage());
        }
    }

    /**
     * Sends an error message back to the form page with the specified error type and message.
     *
     * @param request      the HttpServletRequest object to set error attributes
     * @param response     the HttpServletResponse object for forwarding
     * @param errorType    the key for the error message (e.g., "errorName")
     * @param errorMessage the error message to display
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void sendError(HttpServletRequest request, HttpServletResponse response, String typeError, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute(typeError, errorMessage);
        request.getRequestDispatcher("view/agent/restaurant/addRestaurant.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles addition of new restaurants with form validation and image upload.";
    }
}
