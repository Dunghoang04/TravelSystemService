/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai         Refactored with ITourDAO, improved resource management and comments
 */
package controller.agent.tour;

import dao.ServiceDao;
import dao.TourCategoryDAO;
import dao.TourDAO;
import dao.TourServiceDetailDAO;
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
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import model.Service;
import model.Tour;
import model.TourCategory;
import model.TourServiceDetail;
import model.TravelAgent;

@WebServlet(name = "EditTour", urlPatterns = {"/EditTour"})
@MultipartConfig
public class EditTour extends HttpServlet {

    private static final String UPLOAD_DIR = "assets/img/tour";
    private TourDAO tourDAO = new TourDAO();
    private TourCategoryDAO tourCategoryDAO = new TourCategoryDAO();
    private ServiceDao serviceDao = new ServiceDao();
    private TourServiceDetailDAO tourServiceDetailDAO = new TourServiceDetailDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            String tourIdStr = request.getParameter("tourId");
            if (tourIdStr == null || tourIdStr.trim().isEmpty()) {
                session.setAttribute("errorMessage", "Không tìm thấy tourId trong yêu cầu!");
                session.setAttribute("showErrorPopup", "edit");
                String page = request.getParameter("page");
                String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
                session.setAttribute("currentStatus", status);
                String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
                if (page != null && !page.isEmpty()) {
                    try {
                        Integer.parseInt(page); // Validate page parameter
                        redirectUrl += "&page=" + page;
                    } catch (NumberFormatException e) {
                        // If page is invalid, do not append it
                    }
                }
                response.sendRedirect(redirectUrl);
                return;
            }

            int tourId = Integer.parseInt(tourIdStr);
            Tour tour = tourDAO.searchTourByID(tourId);
            if (tour == null) {
                session.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourId);
                session.setAttribute("showErrorPopup", "edit");
                String page = request.getParameter("page");
                String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
                session.setAttribute("currentStatus", status);
                String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
                if (page != null && !page.isEmpty()) {
                    try {
                        Integer.parseInt(page); // Validate page parameter
                        redirectUrl += "&page=" + page;
                    } catch (NumberFormatException e) {
                        // If page is invalid, do not append it
                    }
                }
                response.sendRedirect(redirectUrl);
                return;
            }

            // Check if the tour has bookings
            if (tourDAO.hasBookings(tourId)) {
                session.setAttribute("errorMessage", "Tour này đã được đặt và không thể chỉnh sửa!");
                session.setAttribute("showErrorPopup", "edit");
                String page = request.getParameter("page");
                String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
                session.setAttribute("currentStatus", status);
                String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
                if (page != null && !page.isEmpty()) {
                    try {
                        Integer.parseInt(page); // Validate page parameter
                        redirectUrl += "&page=" + page;
                    } catch (NumberFormatException e) {
                        // If page is invalid, do not append it
                    }
                }
                response.sendRedirect(redirectUrl);
                return;
            }

            String gmail = (String) session.getAttribute("gmail");
            if (gmail == null) {
                response.sendRedirect("LoginLogout?service=loginUser");
                return;
            }

            TravelAgent agent = (TravelAgent) session.getAttribute("agent");

            // Load data
            Vector<Service> restaurants = serviceDao.getRestaurantsByProvince(tour.getEndPlace(), agent.getTravelAgentID());
            Vector<Service> accommodations = serviceDao.getAccommodationsByProvince(tour.getEndPlace(), agent.getTravelAgentID());
            Vector<Service> entertainments = serviceDao.getEntertainmentsByProvince(tour.getEndPlace(), agent.getTravelAgentID());

            Vector<TourServiceDetail> tourServiceDetails = tourServiceDetailDAO.getTourServiceDetails(tourId);

            request.setAttribute("tourCategories", tourCategoryDAO.getAllTourCategory());
            request.setAttribute("restaurants", restaurants);
            request.setAttribute("accommodations", accommodations);
            request.setAttribute("entertainments", entertainments);
            request.setAttribute("tourServiceDetails", tourServiceDetails);
            request.setAttribute("tour", tour);
            // Store current page number and status for redirect after edit
            String page = request.getParameter("page");
            String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    session.setAttribute("currentPage", page);
                } catch (NumberFormatException e) {
                    session.removeAttribute("currentPage");
                }
            }
            session.setAttribute("currentStatus", status);
            request.getRequestDispatcher("/view/agent/tour/updateTour.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "tourId không hợp lệ: " + e.getMessage());
            session.setAttribute("showErrorPopup", "edit");
            String page = request.getParameter("page");
            String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
            session.setAttribute("currentStatus", status);
            String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    redirectUrl += "&page=" + page;
                } catch (NumberFormatException ex) {
                    // If page is invalid, do not append it
                }
            }
            response.sendRedirect(redirectUrl);
        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu khi tải tour: " + e.getMessage());
            session.setAttribute("showErrorPopup", "edit");
            String page = request.getParameter("page");
            String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
            session.setAttribute("currentStatus", status);
            String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    redirectUrl += "&page=" + page;
                } catch (NumberFormatException ex) {
                    // If page is invalid, do not append it
                }
            }
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Lỗi không xác định khi tải tour: " + e.getMessage());
            session.setAttribute("showErrorPopup", "edit");
            String page = request.getParameter("page");
            String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
            session.setAttribute("currentStatus", status);
            String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    redirectUrl += "&page=" + page;
                } catch (NumberFormatException ex) {
                    // If page is invalid, do not append it
                }
            }
            response.sendRedirect(redirectUrl);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        if ("clearSession".equals(action)) {
            session.removeAttribute("errorMessage");
            session.removeAttribute("showErrorPopup");
            session.removeAttribute("tourName");
            session.removeAttribute("tourCategoryID");
            session.removeAttribute("quantity");
            session.removeAttribute("adultPrice");
            session.removeAttribute("childrenPrice");
            session.removeAttribute("tourIntroduce");
            session.removeAttribute("tourSchedule");
            session.removeAttribute("tourInclude");
            session.removeAttribute("tourNonInclude");
            session.removeAttribute("startPlace");
            session.removeAttribute("startDay");
            session.removeAttribute("endDay");
            session.removeAttribute("imagePath");
            session.removeAttribute("restaurantIds");
            session.removeAttribute("accommodationIds");
            session.removeAttribute("entertainmentIds");
            session.removeAttribute("validationErrors");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        try {
            String gmail = (String) session.getAttribute("gmail");
            if (gmail == null) {
                response.sendRedirect("LoginLogout?service=loginUser");
                return;
            }

            int tourId = Integer.parseInt(request.getParameter("tourId"));
            Tour existingTour = tourDAO.searchTourByID(tourId);
            if (existingTour == null) {
                session.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourId);
                session.setAttribute("showErrorPopup", "edit");
                String page = (String) session.getAttribute("currentPage");
                String status = (String) session.getAttribute("currentStatus") != null ? (String) session.getAttribute("currentStatus") : "all";
                String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
                if (page != null && !page.isEmpty()) {
                    try {
                        Integer.parseInt(page); // Validate page parameter
                        redirectUrl += "&page=" + page;
                    } catch (NumberFormatException e) {
                        // If page is invalid, do not append it
                    }
                }
                response.sendRedirect(redirectUrl);
                return;
            }

            
            TravelAgent agent = (TravelAgent) session.getAttribute("agent");
            if (agent == null || existingTour.getTravelAgentID() != agent.getTravelAgentID()) {
                session.setAttribute("errorMessage", "Bạn không có quyền chỉnh sửa tour ID: " + tourId);
                session.setAttribute("showErrorPopup", "edit");
                String page = (String) session.getAttribute("currentPage");
                String status = (String) session.getAttribute("currentStatus") != null ? (String) session.getAttribute("currentStatus") : "all";
                String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
                if (page != null && !page.isEmpty()) {
                    try {
                        Integer.parseInt(page); // Validate page parameter
                        redirectUrl += "&page=" + page;
                    } catch (NumberFormatException e) {
                        // If page is invalid, do not append it
                    }
                }
                response.sendRedirect(redirectUrl);
                return;
            }

            // Load data for form in case of validation errors
            Vector<TourCategory> tourCategories = tourCategoryDAO.getAllTourCategory() != null ? tourCategoryDAO.getAllTourCategory() : new Vector<>();
            Vector<Service> restaurants = serviceDao.getRestaurantsByProvince(existingTour.getEndPlace(), agent.getTravelAgentID()) != null ? serviceDao.getRestaurantsByProvince(existingTour.getEndPlace(), agent.getTravelAgentID()) : new Vector<>();
            Vector<Service> accommodations = serviceDao.getAccommodationsByProvince(existingTour.getEndPlace(), agent.getTravelAgentID()) != null ? serviceDao.getAccommodationsByProvince(existingTour.getEndPlace(), agent.getTravelAgentID()) : new Vector<>();
            Vector<Service> entertainments = serviceDao.getEntertainmentsByProvince(existingTour.getEndPlace(), agent.getTravelAgentID()) != null ? serviceDao.getEntertainmentsByProvince(existingTour.getEndPlace(), agent.getTravelAgentID()) : new Vector<>();
            Vector<TourServiceDetail> tourServiceDetails = tourServiceDetailDAO.getTourServiceDetails(tourId) != null ? tourServiceDetailDAO.getTourServiceDetails(tourId) : new Vector<>();

            request.setAttribute("tourCategories", tourCategories);
            request.setAttribute("restaurants", restaurants);
            request.setAttribute("accommodations", accommodations);
            request.setAttribute("entertainments", entertainments);
            request.setAttribute("tourServiceDetails", tourServiceDetails);
            request.setAttribute("tour", existingTour);

            // Handle file upload
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + UPLOAD_DIR;
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            String imagePath = existingTour.getImage();
            String contentType = request.getContentType();
            if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
                for (Part part : request.getParts()) {
                    String fileName = extractFileName(part);
                    if (fileName != null && !fileName.isEmpty() && part.getName().equals("image")) {
                        String filePath = savePath + File.separator + fileName;
                        part.write(filePath);
                        imagePath = UPLOAD_DIR + "/" + fileName;
                    }
                }
            }
            session.setAttribute("imagePath", imagePath);

            // Get form parameters
            String tourName = request.getParameter("tourName") != null ? request.getParameter("tourName").trim() : "";
            String tourCategoryIDStr = request.getParameter("tourCategoryID") != null ? request.getParameter("tourCategoryID").trim() : "";
            String quantityStr = request.getParameter("quantity") != null ? request.getParameter("quantity").trim() : "";
            String adultPriceStr = request.getParameter("adultPrice") != null ? request.getParameter("adultPrice").trim() : "";
            String childrenPriceStr = request.getParameter("childrenPrice") != null ? request.getParameter("childrenPrice").trim() : "";
            String tourIntroduce = request.getParameter("tourIntroduce") != null ? request.getParameter("tourIntroduce").trim() : "";
            String tourSchedule = request.getParameter("tourSchedule") != null ? request.getParameter("tourSchedule").trim() : "";
            String tourInclude = request.getParameter("tourInclude") != null ? request.getParameter("tourInclude").trim() : "";
            String tourNonInclude = request.getParameter("tourNonInclude") != null ? request.getParameter("tourNonInclude").trim() : "";
            String startPlace = request.getParameter("startPlace");
            String startDay = request.getParameter("startDay");
            String endDay = request.getParameter("endDay");
            String[] restaurantIds = request.getParameterValues("restaurantIds");
            String[] accommodationIds = request.getParameterValues("accommodationIds");
            String[] entertainmentIds = request.getParameterValues("entertainmentIds");

            // Validate input
            Map<String, String> errors = validateInput(tourName, tourCategoryIDStr, quantityStr, adultPriceStr, childrenPriceStr,
                    tourIntroduce, tourSchedule, tourInclude, tourNonInclude, startPlace, startDay, endDay,
                    imagePath, restaurantIds, accommodationIds, entertainmentIds);

            if (!errors.isEmpty()) {
                session.setAttribute("validationErrors", errors);
                session.setAttribute("tourName", tourName);
                session.setAttribute("tourCategoryID", tourCategoryIDStr);
                session.setAttribute("quantity", quantityStr);
                session.setAttribute("adultPrice", adultPriceStr);
                session.setAttribute("childrenPrice", childrenPriceStr);
                session.setAttribute("tourIntroduce", tourIntroduce);
                session.setAttribute("tourSchedule", tourSchedule);
                session.setAttribute("tourInclude", tourInclude);
                session.setAttribute("tourNonInclude", tourNonInclude);
                session.setAttribute("startPlace", startPlace);
                session.setAttribute("startDay", startDay);
                session.setAttribute("endDay", endDay);
                session.setAttribute("restaurantIds", restaurantIds != null ? restaurantIds : new String[]{});
                session.setAttribute("accommodationIds", accommodationIds != null ? accommodationIds : new String[]{});
                session.setAttribute("entertainmentIds", entertainmentIds != null ? entertainmentIds : new String[]{});

                request.getRequestDispatcher("/view/agent/tour/updateTour.jsp").forward(request, response);
                return;
            }

            // Create updated tour object
            int tourCategoryID = Integer.parseInt(tourCategoryIDStr);
            int quantity = Integer.parseInt(quantityStr);
            double adultPrice = Double.parseDouble(adultPriceStr);
            double childrenPrice = Double.parseDouble(childrenPriceStr);
            Date startDate = Date.valueOf(startDay);
            Date endDate = Date.valueOf(endDay);
            int numberOfDay = (int) ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate()) + 1;

            Tour updatedTour = new Tour(
                    tourId,
                    tourCategoryID,
                    existingTour.getTravelAgentID(),
                    tourName,
                    numberOfDay,
                    startPlace,
                    existingTour.getEndPlace(),
                    quantity,
                    imagePath,
                    tourIntroduce,
                    tourSchedule,
                    tourInclude,
                    tourNonInclude,
                    existingTour.getRate(),
                    existingTour.getStatus(),
                    startDate,
                    endDate,
                    adultPrice,
                    childrenPrice,
                    existingTour.getReason()
            );

            // Update tour and services
            tourDAO.updateTour(updatedTour);
            tourServiceDetailDAO.deleteTourServiceDetails(tourId);
            saveTourServices(tourId, restaurantIds, accommodationIds, entertainmentIds);

            // Clear session attributes
            session.removeAttribute("tourName");
            session.removeAttribute("tourCategoryID");
            session.removeAttribute("quantity");
            session.removeAttribute("adultPrice");
            session.removeAttribute("childrenPrice");
            session.removeAttribute("tourIntroduce");
            session.removeAttribute("tourSchedule");
            session.removeAttribute("tourInclude");
            session.removeAttribute("tourNonInclude");
            session.removeAttribute("startPlace");
            session.removeAttribute("startDay");
            session.removeAttribute("endDay");
            session.removeAttribute("imagePath");
            session.removeAttribute("restaurantIds");
            session.removeAttribute("accommodationIds");
            session.removeAttribute("entertainmentIds");
            session.removeAttribute("validationErrors");

            session.setAttribute("successMessage", "Sửa tour thành công!");
            session.setAttribute("showSuccessPopup", "edit");
            String page = (String) session.getAttribute("currentPage");
            String status = (String) session.getAttribute("currentStatus") != null ? (String) session.getAttribute("currentStatus") : "all";
            String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    redirectUrl += "&page=" + page;
                } catch (NumberFormatException e) {
                    // If page is invalid, do not append it
                }
            }
            response.sendRedirect(redirectUrl);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Dữ liệu không hợp lệ: " + e.getMessage() + " (tourId: " + request.getParameter("tourId") + ")");
            session.setAttribute("showErrorPopup", "edit");
            String page = (String) session.getAttribute("currentPage");
            String status = (String) session.getAttribute("currentStatus") != null ? (String) session.getAttribute("currentStatus") : "all";
            String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    redirectUrl += "&page=" + page;
                } catch (NumberFormatException ex) {
                    // If page is invalid, do not append it
                }
            }
            response.sendRedirect(redirectUrl);
        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu khi cập nhật tour: " + e.getMessage());
            session.setAttribute("showErrorPopup", "edit");
            String page = (String) session.getAttribute("currentPage");
            String status = (String) session.getAttribute("currentStatus") != null ? (String) session.getAttribute("currentStatus") : "all";
            String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    redirectUrl += "&page=" + page;
                } catch (NumberFormatException ex) {
                    // If page is invalid, do not append it
                }
            }
            response.sendRedirect(redirectUrl);
        } catch (IllegalArgumentException e) {
            session.setAttribute("errorMessage", "Ngày không hợp lệ: " + e.getMessage());
            session.setAttribute("showErrorPopup", "edit");
            String page = (String) session.getAttribute("currentPage");
            String status = (String) session.getAttribute("currentStatus") != null ? (String) session.getAttribute("currentStatus") : "all";
            String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    redirectUrl += "&page=" + page;
                } catch (NumberFormatException ex) {
                    // If page is invalid, do not append it
                }
            }
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Lỗi không xác định khi cập nhật tour: " + e.getMessage());
            session.setAttribute("showErrorPopup", "edit");
            String page = (String) session.getAttribute("currentPage");
            String status = (String) session.getAttribute("currentStatus") != null ? (String) session.getAttribute("currentStatus") : "all";
            String redirectUrl = request.getContextPath() + "/ListTour?service=list&status=" + status;
            if (page != null && !page.isEmpty()) {
                try {
                    Integer.parseInt(page); // Validate page parameter
                    redirectUrl += "&page=" + page;
                } catch (NumberFormatException ex) {
                    // If page is invalid, do not append it
                }
            }
            response.sendRedirect(redirectUrl);
        }
    }

    private Map<String, String> validateInput(String tourName, String tourCategoryIDStr, String quantityStr, String adultPriceStr,
            String childrenPriceStr, String tourIntroduce, String tourSchedule, String tourInclude,
            String tourNonInclude, String startPlace, String startDay, String endDay, String imagePath,
            String[] restaurantIds, String[] accommodationIds, String[] entertainmentIds) {
        Map<String, String> errors = new HashMap<>();
        LocalDate currentDate = LocalDate.now();

        if (tourName.isEmpty()) {
            errors.put("tourName", "Tên tour không được để trống!");
        } else if (tourName.length() < 2 || tourName.length() > 255) {
            errors.put("tourName", "Tên tour phải từ 2 đến 255 ký tự!");
        }
        if (tourCategoryIDStr.isEmpty()) {
            errors.put("tourCategoryID", "Loại tour không được để trống!");
        } else {
            try {
                int tourCategoryID = Integer.parseInt(tourCategoryIDStr);
                if (tourCategoryID <= 0) {
                    errors.put("tourCategoryID", "Loại tour không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                errors.put("tourCategoryID", "Loại tour không hợp lệ!");
            }
        }
        if (quantityStr.isEmpty()) {
            errors.put("quantity", "Số lượng không được để trống!");
        } else {
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    errors.put("quantity", "Số lượng phải lớn hơn 0!");
                } else if (quantity > 1000) {
                    errors.put("quantity", "Số lượng không được vượt quá 1000!");
                }
            } catch (NumberFormatException e) {
                errors.put("quantity", "Số lượng phải là số hợp lệ!");
            }
        }
        if (adultPriceStr.isEmpty()) {
            errors.put("adultPrice", "Giá người lớn không được để trống!");
        } else {
            try {
                double adultPrice = Double.parseDouble(adultPriceStr.replaceAll("[^0-9]", ""));
                if (adultPrice < 0) {
                    errors.put("adultPrice", "Giá người lớn không được âm!");
                } else if (adultPrice > 100000000) {
                    errors.put("adultPrice", "Giá người lớn không được vượt quá 100 triệu VNĐ!");
                }
            } catch (NumberFormatException e) {
                errors.put("adultPrice", "Giá người lớn phải là số hợp lệ!");
            }
        }
        if (childrenPriceStr.isEmpty()) {
            errors.put("childrenPrice", "Giá trẻ em không được để trống!");
        } else {
            try {
                double childrenPrice = Double.parseDouble(childrenPriceStr.replaceAll("[^0-9]", ""));
                if (childrenPrice < 0) {
                    errors.put("childrenPrice", "Giá trẻ em không được âm!");
                } else if (childrenPrice > 100000000) {
                    errors.put("childrenPrice", "Giá trẻ em không được vượt quá 100 triệu VNĐ!");
                }
            } catch (NumberFormatException e) {
                errors.put("childrenPrice", "Giá trẻ em phải là số hợp lệ!");
            }
        }
        if (!adultPriceStr.isEmpty() && !childrenPriceStr.isEmpty()) {
            try {
                double adultPrice = Double.parseDouble(adultPriceStr.replaceAll("[^0-9]", ""));
                double childrenPrice = Double.parseDouble(childrenPriceStr.replaceAll("[^0-9]", ""));
                if (adultPrice <= childrenPrice) {
                    errors.put("adultPrice", "Giá người lớn phải lớn hơn giá trẻ em!");
                }
            } catch (NumberFormatException e) {
                // Already handled above
            }
        }
        if (tourIntroduce.isEmpty()) {
            errors.put("tourIntroduce", "Giới thiệu tour không được để trống!");
        } else if (tourIntroduce.length() < 10 || tourIntroduce.length() > 3000) {
            errors.put("tourIntroduce", "Giới thiệu tour phải từ 10 đến 3000 ký tự!");
        }
        if (tourSchedule.isEmpty()) {
            errors.put("tourSchedule", "Lịch trình tour không được để trống!");
        } else if (tourSchedule.length() < 10 || tourSchedule.length() > 5000) {
            errors.put("tourSchedule", "Lịch trình tour phải từ 10 đến 5000 ký tự!");
        }
        if (tourInclude.isEmpty()) {
            errors.put("tourInclude", "Bao gồm không được để trống!");
        } else if (tourInclude.length() < 10 || tourInclude.length() > 2000) {
            errors.put("tourInclude", "Bao gồm phải từ 10 đến 2000 ký tự!");
        }
        if (tourNonInclude.isEmpty()) {
            errors.put("tourNonInclude", "Không bao gồm không được để trống!");
        } else if (tourNonInclude.length() < 10 || tourNonInclude.length() > 2000) {
            errors.put("tourNonInclude", "Không bao gồm phải từ 10 đến 2000 ký tự!");
        }
        if (startPlace == null || startPlace.isEmpty()) {
            errors.put("startPlace", "Điểm đi không được để trống!");
        }
        if (startDay == null || startDay.isEmpty()) {
            errors.put("startDay", "Ngày bắt đầu không được để trống!");
        } else if (!startDay.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errors.put("startDay", "Ngày bắt đầu không hợp lệ!");
        } else {
            try {
                Date startDate = Date.valueOf(startDay);
                if (!startDate.after(Date.valueOf(currentDate))) {
                    errors.put("startDay", "Ngày bắt đầu phải sau ngày hiện tại!");
                }
            } catch (IllegalArgumentException e) {
                errors.put("startDay", "Ngày bắt đầu không hợp lệ!");
            }
        }
        if (endDay == null || endDay.isEmpty()) {
            errors.put("endDay", "Ngày kết thúc không được để trống!");
        } else if (!endDay.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errors.put("endDay", "Ngày kết thúc không hợp lệ!");
        } else if (startDay != null && !startDay.isEmpty() && endDay != null && !endDay.isEmpty()) {
            try {
                Date startDate = Date.valueOf(startDay);
                Date endDate = Date.valueOf(endDay);
                if (endDate.before(startDate)) {
                    errors.put("endDay", "Ngày kết thúc phải sau ngày bắt đầu!");
                }
            } catch (IllegalArgumentException e) {
                errors.put("endDay", "Ngày kết thúc không hợp lệ!");
            }
        }
        if ((restaurantIds == null || restaurantIds.length == 0)
                && (accommodationIds == null || accommodationIds.length == 0)
                && (entertainmentIds == null || entertainmentIds.length == 0)) {
            errors.put("services", "Vui lòng chọn ít nhất một dịch vụ (nhà hàng, khách sạn, hoặc giải trí)!");
        }

        return errors;
    }

    private void saveTourServices(int tourId, String[] restaurantIds, String[] accommodationIds, String[] entertainmentIds)
            throws SQLException {
        if (restaurantIds != null) {
            for (String serviceIdStr : restaurantIds) {
                int serviceId = Integer.parseInt(serviceIdStr);
                String serviceName = serviceDao.getServiceName(serviceId);
                TourServiceDetail detail = new TourServiceDetail(0, tourId, serviceId, serviceName, 1);
                tourServiceDetailDAO.insertTourServiceDetail(detail);
            }
        }
        if (accommodationIds != null) {
            for (String serviceIdStr : accommodationIds) {
                int serviceId = Integer.parseInt(serviceIdStr);
                String serviceName = serviceDao.getServiceName(serviceId);
                TourServiceDetail detail = new TourServiceDetail(0, tourId, serviceId, serviceName, 1);
                tourServiceDetailDAO.insertTourServiceDetail(detail);
            }
        }
        if (entertainmentIds != null) {
            for (String serviceIdStr : entertainmentIds) {
                int serviceId = Integer.parseInt(serviceIdStr);
                String serviceName = serviceDao.getServiceName(serviceId);
                TourServiceDetail detail = new TourServiceDetail(0, tourId, serviceId, serviceName, 1);
                tourServiceDetailDAO.insertTourServiceDetail(detail);
            }
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp != null) {
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    String fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                    return fileName.trim();
                }
            }
        }
        return "";
    }
}