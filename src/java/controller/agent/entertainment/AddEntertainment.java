/**
 * Adds a new entertainment record in the agent module.<br>
 * Handles GET requests to display the add form and POST requests to process new
 * entertainment data with image upload.<br>
 *
 * Project: TravelAgentService Version: 1.0 Date: 2025-06-07 Bugs: No known
 * issues.
 *
 * Record of Change: DATE Version AUTHOR DESCRIPTION 2025-06-07 1.0 Hoang Tuan
 * Dung First implementation 2025-06-08 1.1 [Your Name] Updated Javadoc for
 * clarity and consistency
 *
 * @author Hoang Tuan Dung
 */
package controller.agent.entertainment;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dao.EntertainmentDAO;
import dao.IEntertainmentDAO;
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
import java.util.Arrays;
import model.TravelAgent;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AddEntertainment extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "assets/img-entertainment";

    /**
     * Sends an error message back to the add form JSP.<br>
     * Sets an error attribute and forwards to the AddEntertainment JSP
     * page.<br>
     *
     * @param request servlet request to set the error attribute<br>
     * @param response servlet response<br>
     * @param message the error message to display<br>
     * @throws ServletException if a servlet-specific error occurs<br>
     * @throws IOException if an I/O error occurs<br>
     */
    private void sendError(HttpServletRequest request, HttpServletResponse response,String errorType, String message)
            throws ServletException, IOException {
        request.setAttribute(errorType, message);
        request.getRequestDispatcher("view/agent/entertainment/addEntertainment.jsp").forward(request, response); // Assume error page
    }

    /**
     * Handles the HTTP GET method to display the add entertainment form.<br>
     * Clears the session image attribute and forwards to the add form JSP.<br>
     *
     * @param request servlet request<br>
     * @param response servlet response<br>
     * @throws ServletException if a servlet-specific error occurs<br>
     * @throws IOException if an I/O error occurs<br>
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("imageFileName");
        request.getRequestDispatcher("view/agent/entertainment/addEntertainment.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method to add a new entertainment record.<br>
     * Validates input data, processes image upload, and inserts the record into
     * the database.<br>
     *
     * @param request servlet request containing entertainment data and
     * image<br>
     * @param response servlet response<br>
     * @throws ServletException if a servlet-specific error occurs<br>
     * @throws IOException if an I/O error occurs<br>
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IEntertainmentDAO enDao = new EntertainmentDAO();
        HttpSession session = request.getSession();
        String imageFileName = (String) session.getAttribute("imageFileName");
        TravelAgent travelAgent = (TravelAgent) session.getAttribute("agent");
        // Get and validate form parameters
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
        request.setAttribute("dayOfWeekAll", dayOfWeekOpen != null ? Arrays.asList(dayOfWeekOpen) : null);
        request.setAttribute("ticketPriceStr", ticketPriceStr);

        try {
            // Handle image upload
            String relativeImagePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                relativeImagePath = UPLOAD_DIRECTORY + "/" + imageFileName;
                String uploadsDirPath = getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
                File uploadsDir = new File(uploadsDirPath);
                if (!uploadsDir.exists()) {
                    if (!uploadsDir.mkdirs()) {
                        throw new IOException("Failed to create upload directory");
                    }
                }
                filePart.write(uploadsDirPath + File.separator + imageFileName);
                session.setAttribute("imageFileName", imageFileName);
                request.setAttribute("imageFileName", imageFileName);
            } else if (imageFileName == null || imageFileName.trim().isEmpty()) {
                request.setAttribute("imageFileName", "");
                sendError(request, response, "errorImage", "Vui lòng chọn ảnh cho nhà hàng.");
                return;
            } else {
                relativeImagePath = UPLOAD_DIRECTORY + "/" + imageFileName; // Use existing image
                request.setAttribute("imageFileName", imageFileName); // Pass to request for JSP
            }

            // Validation: name
            if (name.isEmpty()) {
                sendError(request, response,"errorName",  "Tên dịch vụ không được để trống");
                return;
            }

            // Validation: phone
            if (phone.isEmpty()) {
                sendError(request, response,"errorPhone",  "Số điện thoại không được để trống.");
                return;
            }
            if (phone.length() != 10 || !phone.startsWith("0") || !phone.matches("^(0)[0-9]{9}$")) {
                sendError(request, response,"errorPhone", "Số điện thoại phải có 10 số, bắt đầu bằng 0.");
                return;
            }

            // Validation: type
            if (type.isEmpty()) {
                sendError(request, response,"errorType", "Loại dịch vụ giải trí không được bỏ trống.");
                return;
            }

            // Validation: rate
            float rate;
            if (rateStr.isEmpty()) {
                sendError(request, response,"errorRate", "Điểm đánh giá không được để trống.");
                return;
            }
            try {
                rate = Float.parseFloat(rateStr);
                if (rate < 0 || rate > 10) {
                    sendError(request, response,"errorRate", "Điểm đánh giá phải nằm trong khoảng 0-10");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorRate","Điểm đánh giá không hợp lệ , điểm đánh giá phải là số");
                return;
            }

            // Validation: time
            if (timeOpenStr.length() == 5) {
                timeOpenStr += ":00";
            }
            if (timeCloseStr.length() == 5) {
                timeCloseStr += ":00";
            }
            LocalTime timeOpen = LocalTime.parse(timeOpenStr);
            LocalTime timeClose = LocalTime.parse(timeCloseStr);
            if (Duration.between(timeOpen, timeClose).toHours() < 1) {
                sendError(request, response, "errorTime","Thời gian mở cửa phải ít nhất 1 tiếng.");
                return;
            }

            // Validation: dayOfWeek
            String dayOfWeekAll = "";
            if (dayOfWeekOpen != null && dayOfWeekOpen.length > 0) {
                dayOfWeekAll = String.join(" , ", dayOfWeekOpen);
            } else {
                sendError(request, response, "errorDayOfWeek","Vui lòng chọn ít nhất 1 ngày mở cửa");
                return;
            }

            // Validation: ticketPrice
            if (ticketPriceStr == null || ticketPriceStr.trim().isEmpty()) {
                sendError(request, response,"errorTicketPrice", "Giá vé không được để trống.");
                return;
            }
            ticketPriceStr = ticketPriceStr.trim();

            if (!ticketPriceStr.matches("^[0-9]{1,3}([.,]?[0-9]{3})*$")) {
                sendError(request, response, "errorTicketPrice","Giá vé không hợp lệ. Vui lòng chỉ nhập số, có thể dùng '.' hoặc ',' ngăn cách <br>hàng chục, hàng trăm, hàng nghìn.<br>Không được thừa ký tự ở đầu và cuối giá tiền");
                return;
            }

            String cleanedTicketPriceStr = ticketPriceStr.replaceAll("[.,]", "");
            float ticketPrice;
            try {
                ticketPrice = Float.parseFloat(cleanedTicketPriceStr);
                if (ticketPrice < 0) {
                    sendError(request, response,"errorTicketPrice", "Giá vé không được là số âm.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorTicketPrice","Giá vé phải là số hợp lệ.");
                return;
            }
            // Validation: description
            if (description.isEmpty() || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "errorDescription","Vui lòng điền mô tả dịch vụ từ 10 từ trở lên.");
                return;
            }

            // Insert into database
            enDao.insertEntertainmentFull(travelAgent.getTravelAgentID(),name, relativeImagePath, address, phone, description, rate, type, 1, timeOpenStr, timeCloseStr, dayOfWeekAll, ticketPrice);
            request.setAttribute("success", "Thêm dịch vụ giải trí thành công!");
            request.getRequestDispatcher("view/agent/entertainment/addEntertainment.jsp").forward(request, response);
        } catch (SQLServerException e) {
            sendError(request, response, "errorSystem","Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (IOException e) {
            sendError(request, response, "errorSystem","Lỗi tải ảnh lên: " + e.getMessage());
        } catch (Exception e) {
            sendError(request, response,"errorSystem", "Lỗi không mong muốn: " + e.getMessage());
        }
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
