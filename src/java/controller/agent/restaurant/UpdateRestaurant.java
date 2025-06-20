package controller.agent.restaurant;

import dao.IRestaurantDAO;
import dao.RestaurantDAO;
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
     * Retrieves restaurant data by ID and forwards to the update JSP page.
     *
     * @param request Servlet request containing the restaurant ID
     * @param response Servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IRestaurantDAO restaurantDAO = new RestaurantDAO();
        String currentPageParam=request.getParameter("page")!=null ?request.getParameter("page") :"1";
        try {
            String serviceIdParam = request.getParameter("id");
            if (serviceIdParam == null || serviceIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã nhà hang không hợp lệ");
            }
            int id = Integer.parseInt(serviceIdParam);
            Restaurant updateRestaurant = restaurantDAO.getRestaurantByServiceId(id);
            if (updateRestaurant == null) {
                throw new IllegalArgumentException("Không tìm thấy nhà hàng có mã = " + serviceIdParam);
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
            request.setAttribute("updateRestaurant", updateRestaurant);
            request.setAttribute("page", currentPageParam);
            request.getRequestDispatcher("view/agent/restaurant/updateRestaurant.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            sendError(request, response, "errorSystem", "Mã nhà hàng không hợp lệ");
        } catch (SQLException e) {
            sendError(request, response, "errorSystem", "Lỗi cơ sở dữ liệu" + e.getMessage());
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
        IRestaurantDAO restaurantDAO = new RestaurantDAO(); // Initialize DAO for restaurant updates
        String currentPageParam=request.getParameter("page")!=null ?request.getParameter("page") :"1";
        try {
            // Retrieve form parameters, trimming null or empty inputs
            String serviceIdParam = request.getParameter("serviceId");
            if (serviceIdParam == null || serviceIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã nhà hàng không hợp lệ"); // Validate service ID
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
            int id = Integer.parseInt(serviceIdParam); // Parse ID to integer
            String name = request.getParameter("name") != null ? request.getParameter("name").trim() : "";
            String address = request.getParameter("address") != null ? request.getParameter("address").trim() : "";
            String type = request.getParameter("type") != null ? request.getParameter("type").trim() : "";
            String phone = request.getParameter("phone") != null ? request.getParameter("phone").trim() : "";
            String timeOpenStr = request.getParameter("timeopen") != null ? request.getParameter("timeopen").trim() : "";
            String timeCloseStr = request.getParameter("timeclose") != null ? request.getParameter("timeclose").trim() : "";
            String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
            String rateStr = request.getParameter("rate") != null ? request.getParameter("rate").trim() : "";
            String statusParam = request.getParameter("status");
            Part filePart = request.getPart("image"); // Get uploaded image
            String existingImage = request.getParameter("existingImage") != null ? request.getParameter("existingImage").trim() : "";

            // Fetch existing restaurant data to preserve in case of errors
            Restaurant updateRestaurant = restaurantDAO.getRestaurantByServiceId(id);
            if (updateRestaurant == null) {
                throw new IllegalArgumentException("Không tìm thấy nhà hàng để cập nhật với ID: " + id);
            }
            request.setAttribute("updateRestaurant", updateRestaurant); // Set data for JSP error handling

            // Validate status (0 or 1)
            int status;
            try {
                status = (statusParam != null && !statusParam.isEmpty()) ? Integer.parseInt(statusParam) : updateRestaurant.getStatus();
                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái không hợp lệ (chỉ chấp nhận 0 hoặc 1).");
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorStatus", "Trạng thái không hợp lệ: " + e.getMessage());
                return;
            }

            // Handle image upload
            String imageFileName = existingImage.isEmpty() ? null : Paths.get(existingImage).getFileName().toString();
            String relativeImagePath = null;
            if (filePart != null && filePart.getSize() > 0) { // Check if new image is uploaded
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                relativeImagePath = UPLOAD_DIR + "/" + imageFileName; // Construct relative path
                String uploadsDirPath = getServletContext().getRealPath("/") + UPLOAD_DIR; // Get server path
                File uploadsDir = new File(uploadsDirPath);
                if (!uploadsDir.exists()) {
                    if (!uploadsDir.mkdirs()) { // Create upload directory if it doesn't exist
                        throw new IOException("Không thể tạo thư mục lưu trữ ảnh: " + uploadsDirPath);
                    }
                }
                filePart.write(uploadsDirPath + File.separator + imageFileName); // Save uploaded file
            } else if (imageFileName == null || imageFileName.trim().isEmpty()) {
                sendError(request, response, "errorImage", "Vui lòng chọn ảnh cho nhà hàng.");
                return;
            } else {
                relativeImagePath = UPLOAD_DIR + "/" + imageFileName; // Use existing image
            }

            // Validate input lengths
            if (name.length() > 255) {
                sendError(request, response, "errorName", "Tên không được vượt quá 255 ký tự.");
                return;
            }
            if (address.length() > 255) {
                sendError(request, response, "errorAddress", "Địa chỉ không được vượt quá 255 ký tự.");
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
                sendError(request, response, "errorRate", "Điểm đánh giá không hợp lệ: " + e.getMessage());
                return;
            }

            // Validate opening and closing times
            if (timeOpenStr.length() == 5) {
                timeOpenStr += ":00"; // Append seconds if time is in HH:mm format
            }
            if (timeCloseStr.length() == 5) {
                timeCloseStr += ":00";
            }
            LocalTime timeOpen = LocalTime.parse(timeOpenStr); // Parse opening time
            LocalTime timeClose = LocalTime.parse(timeCloseStr); // Parse closing time
            if (Duration.between(timeOpen, timeClose).toHours() < 1) {
                sendError(request, response, "errorTime", "Thời gian mở cửa phải ít nhất 1 tiếng.");
                return;
            }

            // Validate description
            if (description.isEmpty() || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "errorDescription", "Mô tả nhà hàng phải có ít nhất 10 từ.");
                return;
            }

            // Update restaurant in the database
            restaurantDAO.updateRestaurant(id, name, relativeImagePath, address, phone, description, rate, type, status, timeOpenStr, timeCloseStr);
            response.sendRedirect("managerestaurant?page="+currentPage); // Redirect to restaurant management page
        } catch (SQLException e) {
            // Handle SQL errors, including foreign key constraints
            if (e.getMessage().contains("FOREIGN KEY")) {
                sendError(request, response, "errorSystem", "Lỗi: Mã nhà hàng không hợp lệ hoặc không tồn tại trong danh sách dịch vụ.");
            } else {
                sendError(request, response, "errorSystem", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            }
        } catch (IOException e) {
            sendError(request, response, "errorSystem", "Lỗi khi tải lên ảnh: " + e.getMessage()); // Handle image upload errors
        } catch (IllegalArgumentException e) {
            sendError(request, response, "errorSystem", e.getMessage()); // Handle validation errors
        } catch (Exception e) {
            sendError(request, response, "errorSystem", "Đã xảy ra lỗi bất ngờ: " + e.getMessage()); // Handle unexpected errors
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
