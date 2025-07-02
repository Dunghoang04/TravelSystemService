/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-21  1.0        Quynh Mai              First implementation

 */
package controller.agent;

import dao.ITourCategoryDAO;
import dao.ITourDAO;
import dao.TourDAO;
import dao.TourCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import model.Tour;
import model.TourCategory;

/**
 * ManageTourServlet handles both registration and update processes for tours.
 * This servlet processes tour operations including file uploads, data
 * validation, and database operations. It supports 'add' and 'edit' services
 * based on the 'service' parameter.
 * <p>
 * Bugs: Potential issues with file upload failures not fully handled
 *
 * @author Grok
 */
@WebServlet(name = "ManageTourServlet", urlPatterns = {"/ManageTourServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold
        maxFileSize = 1024 * 1024 * 10, // 10MB max file size
        maxRequestSize = 1024 * 1024 * 50 // 50MB max request size
)
public class ManageTourServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "assets/img/tour"; // Directory for uploaded files
    private ITourDAO tourDAO = new TourDAO(); // DAO instance with interface
    private ITourCategoryDAO tourCategoryDAO = new TourCategoryDAO(); // DAO for tour categories
    private HttpSession session;

    /**
     * Handles the HTTP GET method. Initializes session, fetches tour
     * categories, and forwards to the appropriate page based on the 'service'
     * parameter.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession(); // Initialize session
        String service = request.getParameter("service");
        try {
            Vector<TourCategory> tourCategories = tourCategoryDAO.getAllTourCategory();
            request.setAttribute("tourCategories", tourCategories);
            if ("add".equals(service)) {
                request.getRequestDispatcher("/view/agent/tour/addTour.jsp").forward(request, response);
            } else if ("edit".equals(service)) {
                String tourIdStr = request.getParameter("tourId");
                if (tourIdStr != null) {
                    int tourId = Integer.parseInt(tourIdStr);
                    Tour tour = tourDAO.searchTourByID(tourId);
                    if (tour != null) {
                        request.setAttribute("tour", tour);
                        request.getRequestDispatcher("/view/agent/tour/updateTour.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Tour không tồn tại!");
                        request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "Thiếu tourId!");
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                }
            } else if ("list".equals(service)) {
                String status = request.getParameter("status");
                Vector<Tour> tours;
                if (status != null && !status.equals("all")) {
                    tours = tourDAO.searchTourByStatus(Integer.parseInt(status));
                } else {
                    tours = tourDAO.getAllTours();
                }
                request.setAttribute("tours", tours);
                request.getRequestDispatcher("/view/agent/tour/agentTour.jsp").forward(request, response);
            
        }else {
                request.setAttribute("errorMessage", "Dịch vụ không hợp lệ!");
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            }
    }
    catch (SQLException e

    
        ) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
    }
    catch (Exception e

    
        ) {
            request.setAttribute("errorMessage", "Error loading page: " + e.getMessage());
        request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
    }
}

/**
 * Handles the HTTP POST method. Processes tour operations (add or edit)
 * including file uploads and delegates to processValidation for further
 * handling.
 *
 * @param request servlet request containing service parameters and uploaded
 * files
 * @param response servlet response to send back to the client
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession(); // Initialize session
        String service = request.getParameter("service");
        if (service == null || (!"add".equals(service) && !"edit".equals(service))) {
            request.setAttribute("errorMessage", "Dịch vụ không hợp lệ!");
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            return;
        }

        try {
            // Process file uploads
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + UPLOAD_DIR;
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            String contentType = request.getContentType();
            if (contentType == null || !contentType.toLowerCase().startsWith("multipart/")) {
                request.setAttribute("errorMessage", "Yêu cầu không chứa dữ liệu multipart. Vui lòng chọn file và gửi lại!");
                request.getRequestDispatcher(service.equals("add") ? "/view/agent/tour/addTour.jsp" : "/view/agent/tour/updateTour.jsp").forward(request, response);
                return;
            }

            @SuppressWarnings("unchecked")
            Map<String, String> uploadedFiles = (Map<String, String>) session.getAttribute("uploadedFiles");
            if (uploadedFiles == null) {
                uploadedFiles = new HashMap<>();
            }

            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && !fileName.isEmpty()) {
                    String filePath = savePath + File.separator + fileName;
                    part.write(filePath);
                    if (part.getName().equals("image")) {
                        uploadedFiles.put("imagePath", UPLOAD_DIR + "/" + fileName);
                    }
                }
            }
            session.setAttribute("uploadedFiles", uploadedFiles);

            // Extract form data
            String tourIdStr = request.getParameter("tourId");
            String tourName = request.getParameter("tourName") != null ? request.getParameter("tourName").trim() : "";
            String startPlace = request.getParameter("startPlace") != null ? request.getParameter("startPlace").trim() : "";
            String endPlace = request.getParameter("endPlace") != null ? request.getParameter("endPlace").trim() : "";
            String startDayStr = request.getParameter("startDay") != null ? request.getParameter("startDay").trim() : "";
            String endDayStr = request.getParameter("endDay") != null ? request.getParameter("endDay").trim() : "";
            String numberOfDayStr = request.getParameter("numberOfDay") != null ? request.getParameter("numberOfDay").trim() : "";
            String quantityStr = request.getParameter("quantity") != null ? request.getParameter("quantity").trim() : "";
            String adultPriceStr = request.getParameter("adultPrice") != null ? request.getParameter("adultPrice").trim() : "";
            String childrenPriceStr = request.getParameter("childrenPrice") != null ? request.getParameter("childrenPrice").trim() : "";
            String tourIntroduce = request.getParameter("tourIntroduce") != null ? request.getParameter("tourIntroduce").trim() : "";
            String tourSchedule = request.getParameter("tourSchedule") != null ? request.getParameter("tourSchedule").trim() : "";
            String tourInclude = request.getParameter("tourInclude") != null ? request.getParameter("tourInclude").trim() : "";
            String tourNonInclude = request.getParameter("tourNonInclude") != null ? request.getParameter("tourNonInclude").trim() : "";
            String rateStr = request.getParameter("rate") != null ? request.getParameter("rate").trim() : "";
            String statusStr = request.getParameter("status") != null ? request.getParameter("status").trim() : "";
            String tourCategoryIDStr = request.getParameter("tourCategoryID") != null ? request.getParameter("tourCategoryID").trim() : "";

            // Process validation and operation
            processValidation(
                    tourName, startPlace, endPlace, startDayStr, endDayStr, numberOfDayStr,
                    quantityStr, adultPriceStr, childrenPriceStr, tourIntroduce, tourSchedule,
                    tourInclude, tourNonInclude, rateStr, statusStr, uploadedFiles.get("imagePath"),
                    tourCategoryIDStr, service, tourIdStr, request, response
            );
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        }
    }

    /**
     * Validates all tour operation data and processes add/edit operations.
     *
     * @param tourName Tour name
     * @param startPlace Starting location
     * @param endPlace Destination
     * @param startDayStr Start date
     * @param endDayStr End date
     * @param numberOfDayStr Number of days
     * @param quantityStr Quantity
     * @param adultPriceStr Adult price
     * @param childrenPriceStr Children price
     * @param tourIntroduce Tour introduction
     * @param tourSchedule Tour schedule
     * @param tourInclude Tour inclusions
     * @param tourNonInclude Tour exclusions
     * @param rateStr Rate
     * @param statusStr Status
     * @param imagePath Tour image path
     * @param tourCategoryIDStr Tour category ID
     * @param service The operation type ('add' or 'edit')
     * @param tourIdStr Tour ID for edit operation
     * @param request Servlet request
     * @param response Servlet response
     * @throws SQLException if database operation fails
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    private void processValidation(String tourName, String startPlace, String endPlace,
            String startDayStr, String endDayStr, String numberOfDayStr, String quantityStr,
            String adultPriceStr, String childrenPriceStr, String tourIntroduce, String tourSchedule,
            String tourInclude, String tourNonInclude, String rateStr, String statusStr,
            String imagePath, String tourCategoryIDStr, String service, String tourIdStr,
            HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        HttpSession session = request.getSession();
        Vector<TourCategory> tourCategories = tourCategoryDAO.getAllTourCategory();
        request.setAttribute("tourCategories", tourCategories);

        if (tourName.isEmpty()) {
            errors.put("tourName", "Tên tour không được để trống!");
        }
        if (startPlace.isEmpty()) {
            errors.put("startPlace", "Nơi khởi hành không được để trống!");
        }
        if (endPlace.isEmpty()) {
            errors.put("endPlace", "Điểm đến không được để trống!");
        }
        if (startDayStr.isEmpty()) {
            errors.put("startDay", "Ngày bắt đầu không được để trống!");
        } else if (!startDayStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errors.put("startDay", "Ngày bắt đầu không hợp lệ!");
        } else {
            Date startDay = Date.valueOf(startDayStr);
            if (startDay.before(Date.valueOf(currentDate))) {
                errors.put("startDay", "Ngày bắt đầu phải sau hoặc bằng ngày hiện tại!");
            }
        }
        if (endDayStr.isEmpty()) {
            errors.put("endDay", "Ngày kết thúc không được để trống!");
        } else if (!endDayStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errors.put("endDay", "Ngày kết thúc không hợp lệ!");
        } else {
            Date endDay = Date.valueOf(endDayStr);
            Date startDay = Date.valueOf(startDayStr);
            if (endDay.before(startDay)) {
                errors.put("endDay", "Ngày kết thúc phải sau ngày bắt đầu!");
            }
        }
        if (numberOfDayStr.isEmpty()) {
            errors.put("numberOfDay", "Số ngày không được để trống!");
        } else {
            try {
                int numberOfDay = Integer.parseInt(numberOfDayStr);
                if (numberOfDay <= 0) {
                    errors.put("numberOfDay", "Số ngày phải lớn hơn 0!");
                }
            } catch (NumberFormatException e) {
                errors.put("numberOfDay", "Số ngày phải là số hợp lệ!");
            }
        }
        if (quantityStr.isEmpty()) {
            errors.put("quantity", "Số lượng không được để trống!");
        } else {
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    errors.put("quantity", "Số lượng phải lớn hơn 0!");
                }
            } catch (NumberFormatException e) {
                errors.put("quantity", "Số lượng phải là số hợp lệ!");
            }
        }
        if (adultPriceStr.isEmpty()) {
            errors.put("adultPrice", "Giá người lớn không được để trống!");
        } else {
            try {
                double adultPrice = Double.parseDouble(adultPriceStr);
                if (adultPrice < 0) {
                    errors.put("adultPrice", "Giá người lớn không được âm!");
                }
            } catch (NumberFormatException e) {
                errors.put("adultPrice", "Giá người lớn phải là số hợp lệ!");
            }
        }
        if (childrenPriceStr.isEmpty()) {
            errors.put("childrenPrice", "Giá trẻ em không được để trống!");
        } else {
            try {
                double childrenPrice = Double.parseDouble(childrenPriceStr);
                if (childrenPrice < 0) {
                    errors.put("childrenPrice", "Giá trẻ em không được âm!");
                }
            } catch (NumberFormatException e) {
                errors.put("childrenPrice", "Giá trẻ em phải là số hợp lệ!");
            }
        }
        if (tourIntroduce.isEmpty()) {
            errors.put("tourIntroduce", "Giới thiệu tour không được để trống!");
        } else if (tourIntroduce.length() < 10 || tourIntroduce.length() > 500) {
            errors.put("tourIntroduce", "Giới thiệu tour phải từ 10 đến 500 ký tự!");
        }
        if (tourSchedule.isEmpty()) {
            errors.put("tourSchedule", "Lịch trình tour không được để trống!");
        } else if (tourSchedule.length() < 10 || tourSchedule.length() > 1000) {
            errors.put("tourSchedule", "Lịch trình tour phải từ 10 đến 1000 ký tự!");
        }
        if (tourInclude.isEmpty()) {
            errors.put("tourInclude", "Bao gồm không được để trống!");
        } else if (tourInclude.length() < 10 || tourInclude.length() > 500) {
            errors.put("tourInclude", "Bao gồm phải từ 10 đến 500 ký tự!");
        }
        if (tourNonInclude.isEmpty()) {
            errors.put("tourNonInclude", "Không bao gồm không được để trống!");
        } else if (tourNonInclude.length() < 10 || tourNonInclude.length() > 500) {
            errors.put("tourNonInclude", "Không bao gồm phải từ 10 đến 500 ký tự!");
        }
        if (rateStr.isEmpty()) {
            errors.put("rate", "Đánh giá không được để trống!");
        } else {
            try {
                float rate = Float.parseFloat(rateStr);
                if (rate < 0.0 || rate > 5.0) {
                    errors.put("rate", "Đánh giá phải từ 0.0 đến 5.0!");
                }
            } catch (NumberFormatException e) {
                errors.put("rate", "Đánh giá phải là số hợp lệ!");
            }
        }
        if (statusStr.isEmpty()) {
            errors.put("status", "Trạng thái không được để trống!");
        } else {
            try {
                int status = Integer.parseInt(statusStr);
                if (status < 0 || status > 2) {
                    errors.put("status", "Trạng thái phải là 0, 1 hoặc 2!");
                }
            } catch (NumberFormatException e) {
                errors.put("status", "Trạng thái phải là số hợp lệ!");
            }
        }
        if ("add".equals(service) && (imagePath == null || imagePath.trim().isEmpty())) {
            errors.put("image", "Hình ảnh tour không được để trống!");
        }
        if (tourCategoryIDStr.isEmpty()) {
            errors.put("tourCategoryID", "Loại tour không được để trống!");
        } else {
            try {
                int tourCategoryID = Integer.parseInt(tourCategoryIDStr);
                if (tourCategoryID <= 0) {
                    errors.put("tourCategoryID", "Loại tour phải là số hợp lệ và lớn hơn 0!");
                }
            } catch (NumberFormatException e) {
                errors.put("tourCategoryID", "Loại tour phải là số hợp lệ!");
            }
        }

        if (errors.isEmpty()) {
            int travelAgentID = ((model.TravelAgent) session.getAttribute("agent")).getTravelAgentID();
            int tourCategoryID = Integer.parseInt(tourCategoryIDStr);
            int numberOfDay = Integer.parseInt(numberOfDayStr);
            int quantity = Integer.parseInt(quantityStr);
            float rate = Float.parseFloat(rateStr);
            int status = Integer.parseInt(statusStr);
            double adultPrice = Double.parseDouble(adultPriceStr);
            double childrenPrice = Double.parseDouble(childrenPriceStr);
            Date startDay = Date.valueOf(startDayStr);
            Date endDay = Date.valueOf(endDayStr);

            Tour tour = new Tour(
                    tourCategoryID,
                    travelAgentID,
                    tourName,
                    numberOfDay,
                    startPlace,
                    endPlace,
                    quantity,
                    imagePath != null ? imagePath : request.getParameter("existingImage"),
                    tourIntroduce,
                    tourSchedule,
                    tourInclude,
                    tourNonInclude,
                    rate,
                    status,
                    startDay,
                    endDay,
                    adultPrice,
                    childrenPrice,
                    "" // reason (can be added if needed)
            );

            if ("add".equals(service)) {
                tourDAO.insertTour(tour);
                session.setAttribute("successMessage", "Thêm tour thành công!");
            } else if ("edit".equals(service) && tourIdStr != null && !tourIdStr.isEmpty()) {
                tourDAO.updateTour(tour);
                session.setAttribute("successMessage", "Cập nhật tour thành công!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageTourServlet?service=list");
        } else {
            session.setAttribute("validationErrors", errors);
            session.setAttribute("tourName", tourName);
            session.setAttribute("startPlace", startPlace);
            session.setAttribute("endPlace", endPlace);
            session.setAttribute("startDay", startDayStr);
            session.setAttribute("endDay", endDayStr);
            session.setAttribute("numberOfDay", numberOfDayStr);
            session.setAttribute("quantity", quantityStr);
            session.setAttribute("adultPrice", adultPriceStr);
            session.setAttribute("childrenPrice", childrenPriceStr);
            session.setAttribute("tourIntroduce", tourIntroduce);
            session.setAttribute("tourSchedule", tourSchedule);
            session.setAttribute("tourInclude", tourInclude);
            session.setAttribute("tourNonInclude", tourNonInclude);
            session.setAttribute("rate", rateStr);
            session.setAttribute("status", statusStr);
            session.setAttribute("tourCategoryID", tourCategoryIDStr);
            if ("add".equals(service)) {
                request.getRequestDispatcher("/view/agent/tour/addTour.jsp").forward(request, response);
            } else if ("edit".equals(service) && tourIdStr != null && !tourIdStr.isEmpty()) {
                int tourId = Integer.parseInt(tourIdStr);
                Tour existingTour = tourDAO.searchTourByID(tourId);
                if (existingTour != null) {
                    request.setAttribute("tour", existingTour);
                    request.getRequestDispatcher("/view/agent/tour/updateTour.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Tour không tồn tại!");
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                }
            }
        }
    }

    /**
     * Extracts file name from Part object. Parses the content-disposition
     * header to retrieve the file name.
     *
     * @param part The Part object from multipart request
     * @return File name or empty string if not found
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition"); // Get content-disposition header
        if (contentDisp != null) {
            String[] items = contentDisp.split(";"); // Split into items
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    String fileName = s.substring(s.indexOf("=") + 2, s.length() - 1); // Extract file name
                    return fileName.trim(); // Trim to remove extra whitespace
                }
            }
        }
        return ""; // Return empty string if no file name found
    }
}
