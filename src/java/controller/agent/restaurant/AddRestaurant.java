package controller.agent.restaurant;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dao.IRestaurantDAO;
import dao.RestaurantDAO;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author ad
 */
@MultipartConfig
public class AddRestaurant extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "assets/img-restaurant";

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
            out.println("<title>Servlet AddRestaurant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRestaurant at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/agent/restaurant/addRestaurant.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IRestaurantDAO restaurantDAO = new RestaurantDAO();
        HttpSession session = request.getSession();
        String imageFileName = (String) session.getAttribute("imageFileName");

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

        TravelAgent travelAgent = (TravelAgent) session.getAttribute("agent");

        try {

            // Define relative path for image storage
            String relativeImagePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                relativeImagePath = UPLOAD_DIRECTORY + "/" + imageFileName;
                String uploadsDirPath = getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadsDirPath);
                if (!uploadDir.exists()) {
                    if (!uploadDir.mkdirs()) {
                        throw new IOException("Faild to create upload directory");
                    }
                }
                filePart.write(uploadsDirPath + File.separator + imageFileName);
                session.setAttribute("imageFileName", imageFileName);
                request.setAttribute("imageFileName", imageFileName);
            } else if (imageFileName == null || imageFileName.trim().isEmpty()) {
                request.setAttribute("imageFileName", imageFileName);
                sendError(request, response, "errorImage", "Vui lòng chọn ảnh cho nhà hàng.");
                return;
            } else {
                relativeImagePath = UPLOAD_DIRECTORY + "/" + imageFileName;
                request.setAttribute("imageFileName", imageFileName);
            }

            // name
            if (name == null || name.trim().isEmpty()) {
                sendError(request, response, "errorName", "Tên nhà hàng không được để trống");
                return;
            }

            // phone
            if (phone == null || phone.trim().isEmpty()) {
                sendError(request, response, "errorPhone", "Số điện thoại không được để trống.");
                return;
            }
            if (phone.length() != 10) {
                sendError(request, response, "errorPhone", "Số điện thoại phải có đúng 10 số.");
                return;
            }
            if (!phone.startsWith("0")) {
                sendError(request, response, "errorPhone", "Số điện thoại phải bắt đầu bằng số 0.");
                return;
            }
            if (!phone.matches("^(0)[0-9]{9}$")) {
                sendError(request, response, "errorPhone", "Số điện thoại phải bắt đầu bằng 0.");
                return;
            }

            // type
            if (type == null || type.trim().isEmpty()) {
                sendError(request, response, "errorType", "Loại nhà hàng không được bỏ trống.");
                return;
            }
            //rate
            float rate;

            try {
                rate = Float.parseFloat(rateStr);
                if (rate < 0 || rate > 10) {
                    sendError(request, response, "errorRate", "Điểm đánh giá phải nằm trong khoảng 1-10");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "errorRate", "Điểm đánh giá không hợp lệ , điểm đánh giá phải là số");
                return;
            }
            //time
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

//        if (!timeOpen.isBefore(timeClose)) {
//            sendError(request, response, "Thời gian mở cửa phải trước thời gian đóng cửa.");
//            return;
//        }
            if (Duration.between(timeOpen, timeClose).toHours() < 1) {
                sendError(request, response, "errorTime", "Thời gian mở cửa phải ít nhất 1 tiếng.");
                return;
            }
            // description
            if (description == null || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "errorDescription", "Vui lòng điền mô tả nhà hàng từ 10 từ trở lên.");
                return;
            }
            // Thêm vào DB
            restaurantDAO.insertRestaurantFull(travelAgent.getUserID(), name, relativeImagePath, address, phone, description, rate, type, 1, timeOpenStr, timeCloseStr);
            // Điều hướng sau khi thêm thành công
            request.setAttribute("success", "Thêm dịch vụ thành công");
            request.getRequestDispatcher("view/agent/restaurant/addRestaurant.jsp").forward(request, response);
        } catch (SQLServerException e) {
            sendError(request, response, "errorSystem", "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (IOException e) {
            sendError(request, response, "errorSystem", "Lỗi tải ảnh lên: " + e.getMessage());
        } catch (Exception e) {
            sendError(request, response, "errorSystem", "Lỗi không mong muốn: " + e.getMessage());
        }
    }

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
        return "Short description";
    }// </editor-fold>

}
