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

import dao.IService;
import dao.ITourCategoryDAO;
import dao.ITourDAO;
import dao.ITourServiceDetailDAO;
import dao.TourDAO;
import dao.TourCategoryDAO;
import dao.ServiceDao;
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

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "AddTour", urlPatterns = {"/AddTour"})
@MultipartConfig
public class AddTour extends HttpServlet {

    private static final String UPLOAD_DIR = "assets/img/tour";
    private ITourDAO tourDAO = new TourDAO();
    private ITourCategoryDAO tourCategoryDAO = new TourCategoryDAO();
    private IService serviceDao = new ServiceDao();
    private ITourServiceDetailDAO tourServiceDetailDAO = new TourServiceDetailDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        try {
            Vector<TourCategory> tourCategories = tourCategoryDAO.getAllTourCategory();
            request.setAttribute("tourCategories", tourCategories);
            String gmail = (String) session.getAttribute("gmail");
            if (gmail == null) {
                response.sendRedirect("LoginLogout?service=loginUser");
                return;
            }
            int agentId = ((TravelAgent) session.getAttribute("agent")).getTravelAgentID();
            session.removeAttribute("validationErrors");
            if ("addStep1".equals(service)) {
                Vector<String> addresses = serviceDao.getDistinctAddresses(agentId);
                request.setAttribute("addresses", addresses);
                request.getRequestDispatcher("/view/agent/tour/addTourStep1.jsp").forward(request, response);
            } else if ("addStep2".equals(service)) {
                String endPlace = (String) session.getAttribute("endPlace");
                Vector<Service> restaurants = serviceDao.getRestaurantsByProvince(endPlace, agentId);
                Vector<Service> accommodations = serviceDao.getAccommodationsByProvince(endPlace, agentId);
                Vector<Service> entertainments = serviceDao.getEntertainmentsByProvince(endPlace, agentId);
                request.setAttribute("restaurants", restaurants);
                request.setAttribute("accommodations", accommodations);
                request.setAttribute("entertainments", entertainments);
                request.getRequestDispatcher("/view/agent/tour/addTourStep2.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý yêu cầu: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        try {
            request.setAttribute("tourCategories", tourCategoryDAO.getAllTourCategory());
            String gmail = (String) session.getAttribute("gmail");
            if (gmail == null) {
                response.sendRedirect("LoginLogout?service=loginUser");
                return;
            }
            request.setAttribute("addresses", serviceDao.getDistinctAddresses(((TravelAgent) session.getAttribute("agent")).getTravelAgentID()));
            if ("addStep1".equals(service)) {
                String tourCategoryID = request.getParameter("tourCategoryID");
                String startPlace = request.getParameter("startPlace");
                String endPlace = request.getParameter("endPlace");
                String startDay = request.getParameter("startDay");
                String endDay = request.getParameter("endDay");

                Map<String, String> errors = validateStep1(tourCategoryID, startPlace, endPlace, startDay, endDay);

                if (!errors.isEmpty()) {
                    session.setAttribute("validationErrors", errors);
                    session.setAttribute("tourCategoryID", tourCategoryID);
                    session.setAttribute("startPlace", startPlace);
                    session.setAttribute("endPlace", endPlace);
                    session.setAttribute("startDay", startDay);
                    session.setAttribute("endDay", endDay);
                    request.getRequestDispatcher("/view/agent/tour/addTourStep1.jsp").forward(request, response);
                } else {
                    session.setAttribute("tourCategoryID", tourCategoryID);
                    session.setAttribute("startPlace", startPlace);
                    session.setAttribute("endPlace", endPlace);
                    session.setAttribute("startDay", startDay);
                    session.setAttribute("endDay", endDay);
                    response.sendRedirect(request.getContextPath() + "/AddTour?service=addStep2");
                }
            } else if ("addStep2".equals(service)) {
                String endPlace = (String) session.getAttribute("endPlace");
                Vector<Service> restaurants = serviceDao.getRestaurantsByProvince(endPlace, ((TravelAgent) session.getAttribute("agent")).getTravelAgentID());
                Vector<Service> accommodations = serviceDao.getAccommodationsByProvince(endPlace, ((TravelAgent) session.getAttribute("agent")).getTravelAgentID());
                Vector<Service> entertainments = serviceDao.getEntertainmentsByProvince(endPlace, ((TravelAgent) session.getAttribute("agent")).getTravelAgentID());
                request.setAttribute("restaurants", restaurants);
                request.setAttribute("accommodations", accommodations);
                request.setAttribute("entertainments", entertainments);

                String appPath = request.getServletContext().getRealPath("");
                String savePath = appPath + File.separator + UPLOAD_DIR;
                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }

                String contentType = request.getContentType();
                if (contentType == null || !contentType.toLowerCase().startsWith("multipart/")) {
                    request.setAttribute("errorMessage", "Yêu cầu không chứa dữ liệu multipart. Vui lòng chọn file và gửi lại!");
                    request.getRequestDispatcher("/view/agent/tour/addTourStep2.jsp").forward(request, response);
                    return;
                }

                Map<String, String> uploadedFiles = new HashMap<>();
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

                String tourName = request.getParameter("tourName") != null ? request.getParameter("tourName").trim() : "";
                String quantityStr = request.getParameter("quantity") != null ? request.getParameter("quantity").trim() : "";
                String adultPriceStr = request.getParameter("adultPrice") != null ? request.getParameter("adultPrice").trim() : "";
                String childrenPriceStr = request.getParameter("childrenPrice") != null ? request.getParameter("childrenPrice").trim() : "";
                String tourIntroduce = request.getParameter("tourIntroduce") != null ? request.getParameter("tourIntroduce").trim() : "";
                String tourSchedule = request.getParameter("tourSchedule") != null ? request.getParameter("tourSchedule").trim() : "";
                String tourInclude = request.getParameter("tourInclude") != null ? request.getParameter("tourInclude").trim() : "";
                String tourNonInclude = request.getParameter("tourNonInclude") != null ? request.getParameter("tourNonInclude").trim() : "";
                String[] restaurantIds = request.getParameterValues("restaurantIds");
                String[] accommodationIds = request.getParameterValues("accommodationIds");
                String[] entertainmentIds = request.getParameterValues("entertainmentIds");

                // Sử dụng imagePath từ session nếu không có file mới
                String imagePath = uploadedFiles.get("imagePath");
                if (imagePath == null && session.getAttribute("imagePath") != null) {
                    imagePath = (String) session.getAttribute("imagePath");
                }

                Map<String, String> errors = validateStep2(tourName, quantityStr, adultPriceStr, childrenPriceStr,
                        tourIntroduce, tourSchedule, tourInclude, tourNonInclude, imagePath,
                        restaurantIds, accommodationIds, entertainmentIds);

                if (!errors.isEmpty()) {
                    session.setAttribute("tourName", tourName);
                    session.setAttribute("quantity", quantityStr);
                    session.setAttribute("adultPrice", adultPriceStr);
                    session.setAttribute("childrenPrice", childrenPriceStr);
                    session.setAttribute("tourIntroduce", tourIntroduce);
                    session.setAttribute("tourSchedule", tourSchedule);
                    session.setAttribute("tourInclude", tourInclude);
                    session.setAttribute("tourNonInclude", tourNonInclude);
                    session.setAttribute("restaurantIds", restaurantIds);
                    session.setAttribute("accommodationIds", accommodationIds);
                    session.setAttribute("entertainmentIds", entertainmentIds);
                    session.setAttribute("imagePath", imagePath); // Lưu imagePath hiện tại
                    for (Map.Entry<String, String> entry : errors.entrySet()) {
                        request.setAttribute("validationErrors_" + entry.getKey(), entry.getValue());
                    }
                    request.getRequestDispatcher("/view/agent/tour/addTourStep2.jsp").forward(request, response);
                } else {
                    int travelAgentID = ((TravelAgent) session.getAttribute("agent")).getTravelAgentID();
                    String tourCategoryIDStr = (String) session.getAttribute("tourCategoryID");
                    String startPlace = (String) session.getAttribute("startPlace");
                    String startDayStr = (String) session.getAttribute("startDay");
                    String endDayStr = (String) session.getAttribute("endDay");

                    int tourCategoryID = Integer.parseInt(tourCategoryIDStr);
                    Date startDay = Date.valueOf(startDayStr);
                    Date endDay = Date.valueOf(endDayStr);
                    int numberOfDay = (int) ChronoUnit.DAYS.between(startDay.toLocalDate(), endDay.toLocalDate()) + 1;
                    int quantity = Integer.parseInt(quantityStr);
                    double adultPrice = Double.parseDouble(adultPriceStr);
                    double childrenPrice = Double.parseDouble(childrenPriceStr);

                    Tour tour = new Tour(
                            tourCategoryID,
                            travelAgentID,
                            tourName,
                            numberOfDay,
                            startPlace,
                            endPlace,
                            quantity,
                            imagePath,
                            tourIntroduce,
                            tourSchedule,
                            tourInclude,
                            tourNonInclude,
                            0,
                            2,
                            startDay,
                            endDay,
                            adultPrice,
                            childrenPrice,
                            ""
                    );

                    int tourId = tourDAO.insertTour(tour);
                    saveTourServices(tourId, restaurantIds, accommodationIds, entertainmentIds);
                    // Clear session attributes after successful submission
                    session.removeAttribute("tourName");
                    session.removeAttribute("quantity");
                    session.removeAttribute("adultPrice");
                    session.removeAttribute("childrenPrice");
                    session.removeAttribute("tourIntroduce");
                    session.removeAttribute("tourSchedule");
                    session.removeAttribute("tourInclude");
                    session.removeAttribute("tourNonInclude");
                    session.removeAttribute("restaurantIds");
                    session.removeAttribute("accommodationIds");
                    session.removeAttribute("entertainmentIds");
                    session.removeAttribute("imagePath");
                    session.setAttribute("successMessage", "Thêm tour thành công!");
                    session.setAttribute("showSuccessPopup", "add"); // Trigger pop-up
                    response.sendRedirect(request.getContextPath() + "/ListTour?service=list&status=2&lastPage=true");
                }
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý yêu cầu: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        }
    }

    private Map<String, String> validateStep1(String tourCategoryID, String startPlace, String endPlace,
            String startDay, String endDay) {
        Map<String, String> errors = new HashMap<>();
        LocalDate currentDate = LocalDate.now();

        if (tourCategoryID == null || tourCategoryID.isEmpty()) {
            errors.put("tourCategoryID", "Loại tour không được để trống!");
        }
        if (startPlace == null || startPlace.isEmpty()) {
            errors.put("startPlace", "Điểm đi không được để trống!");
        }
        if (endPlace == null || endPlace.isEmpty()) {
            errors.put("endPlace", "Điểm đến không được để trống!");
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

        return errors;
    }

    private Map<String, String> validateStep2(String tourName, String quantityStr, String adultPriceStr,
            String childrenPriceStr, String tourIntroduce, String tourSchedule, String tourInclude,
            String tourNonInclude, String imagePath, String[] restaurantIds, String[] accommodationIds,
            String[] entertainmentIds) {
        Map<String, String> errors = new HashMap<>();

        if (tourName.isEmpty()) {
            errors.put("tourName", "Tên tour không được để trống!");
        } else if (tourName.length() < 2 || tourName.length() > 255) {
            errors.put("tourName", "Tên tour phải từ 2 đến 255 ký tự!");
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
        if (imagePath == null || imagePath.isEmpty()) {
            errors.put("image", "Vui lòng chọn ảnh cho tour!");
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
                TourServiceDetail detail = new TourServiceDetail(tourId, serviceId, serviceName, 1);
                tourServiceDetailDAO.insertTourServiceDetail(detail);
            }
        }
        if (accommodationIds != null) {
            for (String serviceIdStr : accommodationIds) {
                int serviceId = Integer.parseInt(serviceIdStr);
                String serviceName = serviceDao.getServiceName(serviceId);
                TourServiceDetail detail = new TourServiceDetail(tourId, serviceId, serviceName, 1);
                tourServiceDetailDAO.insertTourServiceDetail(detail);
            }
        }
        if (entertainmentIds != null) {
            for (String serviceIdStr : entertainmentIds) {
                int serviceId = Integer.parseInt(serviceIdStr);
                String serviceName = serviceDao.getServiceName(serviceId);
                TourServiceDetail detail = new TourServiceDetail(tourId, serviceId, serviceName, 1);
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
