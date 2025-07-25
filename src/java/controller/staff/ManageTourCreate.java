/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai          First implementation
 */
package controller.staff;

import dao.ITourCategoryDAO;
import dao.ITourDAO;
import dao.ITravelAgentDAO;
import dao.TourCategoryDAO;
import dao.TourDAO;
import dao.TravelAgentDAO;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Tour;
import model.TravelAgent;
import model.TourCategory;
import service.EmailSender;
import java.io.IOException;

@WebServlet(name = "ManageTourCreate", urlPatterns = {"/ManageTourCreate"})
public class ManageTourCreate extends HttpServlet {

    private ITourDAO tourDAO = new TourDAO();
    private ITravelAgentDAO travelAgentDAO = new TravelAgentDAO(); 
    private ITourCategoryDAO tourCategoryDAO = new TourCategoryDAO(); 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        try {
            if ("list".equals(service) || service == null) {
                String statusParam = request.getParameter("status");
                String page = request.getParameter("page");
                if (statusParam != null && !statusParam.equals("all")) {
                    int status = Integer.parseInt(statusParam);
                    request.setAttribute("tours", tourDAO.searchTourByStatus(status));
                } else {
                    request.setAttribute("tours", tourDAO.getAllTours());
                }
                session.setAttribute("currentPage", page != null && page.matches("\\d+") ? page : "1");
                session.setAttribute("currentStatus", statusParam != null ? statusParam : "all");
                request.getRequestDispatcher("/view/staff/manageTourCreate.jsp").forward(request, response);
            } else if ("profile".equals(service)) {
                int tourID = Integer.parseInt(request.getParameter("tourID"));
                String page = request.getParameter("page");
                String status = request.getParameter("status");
                session.setAttribute("currentPage", page != null && page.matches("\\d+") ? page : "1");
                session.setAttribute("currentStatus", status != null ? status : "all");

                Tour tour = tourDAO.searchTourByID(tourID);
                if (tour == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                int travelAgentID = tour.getTravelAgentID();
                TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                int categoryID = tour.getTourCategoryID();
                TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                request.setAttribute("tour", tour);
                request.setAttribute("travelAgentName", travelAgent != null ? travelAgent.getTravelAgentName() : "Không xác định");
                request.setAttribute("categoryName", tourCategory != null ? tourCategory.getTourCategoryName() : "Không xác định");

                request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
            } else if ("clearSession".equals(service)) {
                session.removeAttribute("actionStatus");
                session.removeAttribute("tourName");
                session.removeAttribute("reason");
                session.removeAttribute("tour");
                response.sendRedirect(request.getContextPath() + "/ManageTourCreate?service=list");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ: " + e.getMessage());
            System.out.println("NumberFormatException in doGet: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý: " + e.getMessage());
            System.out.println("Exception in doGet: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        try {
            if ("approve".equals(service) || "reject".equals(service)) {
                int tourID = Integer.parseInt(request.getParameter("tourID"));
                String tourName = request.getParameter("tourName");
                String page = request.getParameter("page");
                String status = request.getParameter("status");
                Tour tour = tourDAO.searchTourByID(tourID);
                
                System.out.println("doPost: service=" + service + ", tourID=" + tourID + ", tourName=" + tourName + ", page=" + page + ", status=" + status);
                
                if (tour == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourID);
                    request.setAttribute("tour", null);
                    request.setAttribute("travelAgentName", "Không xác định");
                    request.setAttribute("categoryName", "Không xác định");
                    request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
                    return;
                }

                int newStatus = "approve".equals(service) ? 1 : 3;
                String reason = null;
                if ("reject".equals(service)) {
                    reason = request.getParameter("reason");
                    System.out.println("Reject: reason=" + (reason != null ? reason : "null"));
                    if (reason == null || reason.trim().isEmpty()) {
                        System.out.println("Reject: Reason is empty, forwarding back to tourDetail.jsp");
                        int travelAgentID = tour.getTravelAgentID();
                        TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                        int categoryID = tour.getTourCategoryID();
                        TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                        request.setAttribute("tour", tour);
                        request.setAttribute("travelAgentName", travelAgent != null ? travelAgent.getTravelAgentName() : "Không xác định");
                        request.setAttribute("categoryName", tourCategory != null ? tourCategory.getTourCategoryName() : "Không xác định");
                        request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
                        return;
                    }
                    if (reason.trim().length() > 255) {
                        System.out.println("Reject: Reason exceeds 255 characters, forwarding back to tourDetail.jsp");
                        int travelAgentID = tour.getTravelAgentID();
                        TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                        int categoryID = tour.getTourCategoryID();
                        TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                        request.setAttribute("tour", tour);
                        request.setAttribute("travelAgentName", travelAgent != null ? travelAgent.getTravelAgentName() : "Không xác định");
                        request.setAttribute("categoryName", tourCategory != null ? tourCategory.getTourCategoryName() : "Không xác định");
                        request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
                        return;
                    }
                    reason = reason.trim();
                    tour.setReason(reason);
                    System.out.println("Reject: Updating tour with reason: " + reason);
                    tourDAO.updateTour(tour);
                }

                System.out.println("Changing tour status to: " + newStatus);
                tourDAO.changeStatusTour(tourID, newStatus);

                int travelAgentID = tour.getTravelAgentID();
                TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                String email = (travelAgent != null) ? travelAgent.getGmail() : null;

                if (email == null || email.trim().isEmpty()) {
                    System.out.println("Reject: Invalid or missing email for travelAgentID=" + travelAgentID);
                    request.setAttribute("errorMessage", "Email không hợp lệ hoặc không tìm thấy TravelAgent.");
                    int categoryID = tour.getTourCategoryID();
                    TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                    request.setAttribute("tour", tour);
                    request.setAttribute("travelAgentName", travelAgent != null ? travelAgent.getTravelAgentName() : "Không xác định");
                    request.setAttribute("categoryName", tourCategory != null ? tourCategory.getTourCategoryName() : "Không xác định");
                    request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
                    return;
                }

                StringBuilder body = new StringBuilder();
                body.append("<h2>Xác nhận hành động tour</h2>");
                body.append("<p>Ngày xử lý: ").append(java.time.LocalDate.now()).append("</p>");
                body.append("<h3>Thông tin hành động:</h3>");
                body.append("<p>Tên tour: ").append(tour.getTourName()).append("</p>");
                body.append("<p>Email: ").append(email).append("</p>");
                body.append("<p>Trạng thái: ").append("approve".equals(service) ? "Đã duyệt thành công." : "Đã từ chối.").append("</p>");
                if ("reject".equals(service)) {
                    body.append("<p>Lý do: ").append(reason).append("</p>");
                }
                body.append("<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>");

                try {
                    System.out.println("Sending email to: " + email);
                    EmailSender.send(email, "Xác nhận " + ("approve".equals(service) ? "duyệt" : "từ chối") + " tour - " + java.time.LocalDate.now(), body.toString());
                    session.setAttribute("currentPage", page != null && page.matches("\\d+") ? page : "1");
                    session.setAttribute("currentStatus", status != null ? status : "all");
                    request.setAttribute("successMessage", "approve".equals(service) ? 
                        "Tour " + tourName + " đã được duyệt thành công. Email xác nhận đã được gửi." : 
                        "Tour " + tourName + " đã bị từ chối. Email xác nhận đã được gửi.");
                    session.removeAttribute("actionStatus");
                    session.removeAttribute("tourName");
                    session.removeAttribute("reason");
                    session.removeAttribute("tour");
                    int categoryID = tour.getTourCategoryID();
                    TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                    request.setAttribute("tour", tour);
                    request.setAttribute("travelAgentName", travelAgent != null ? travelAgent.getTravelAgentName() : "Không xác định");
                    request.setAttribute("categoryName", tourCategory != null ? tourCategory.getTourCategoryName() : "Không xác định");
                    System.out.println("Reject: Success, forwarding to tourDetail.jsp with successMessage");
                    request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
                } catch (MessagingException | IOException e) {
                    System.out.println("Failed to send email for action " + service + ": " + e.getMessage());
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "Lỗi khi gửi email: " + e.getMessage());
                    int categoryID = tour.getTourCategoryID();
                    TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                    request.setAttribute("tour", tour);
                    request.setAttribute("travelAgentName", travelAgent != null ? travelAgent.getTravelAgentName() : "Không xác định");
                    request.setAttribute("categoryName", tourCategory != null ? tourCategory.getTourCategoryName() : "Không xác định");
                    request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Dịch vụ không hợp lệ.");
                System.out.println("Invalid service: " + service);
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ: " + e.getMessage());
            System.out.println("NumberFormatException in doPost: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý: " + e.getMessage());
            System.out.println("Exception in doPost: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
        }
    }
}