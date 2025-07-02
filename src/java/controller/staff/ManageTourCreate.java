/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-21  1.0        Quynh Mai          First implementation
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
import java.io.PrintWriter;

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
            if ("sendConfirmationEmail".equals(service)) {
                processSendConfirmationEmail(request, response);
            } else if ("list".equals(service) || service == null) {
                String statusParam = request.getParameter("status");
                if (statusParam != null && !statusParam.equals("all")) {
                    int status = Integer.parseInt(statusParam);
                    request.setAttribute("tours", tourDAO.searchTourByStatus(status));
                } else {
                    request.setAttribute("tours", tourDAO.getAllTours());
                }
                request.getRequestDispatcher("/view/staff/manageTourCreate.jsp").forward(request, response);
            } else if ("profile".equals(service)) {
                int tourID = Integer.parseInt(request.getParameter("tourID"));
                Tour tour = tourDAO.searchTourByID(tourID);
                if (tour == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                // Lấy TravelAgent
                int travelAgentID = tour.getTravelAgentID(); 
                TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                if (travelAgent == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy TravelAgent với ID: " + travelAgentID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                // Lấy TourCategory
                int categoryID = tour.getTourCategoryID(); 
                TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                if (tourCategory == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy TourCategory với ID: " + categoryID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                // Đặt các thuộc tính vào request
                request.setAttribute("tour", tour);
                request.setAttribute("travelAgentName", travelAgent.getTravelAgentName());
                request.setAttribute("categoryName", tourCategory.getTourCategoryName());
                request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
            } else if ("confirm".equals(service)) {
                if (session.getAttribute("actionStatus") == null || session.getAttribute("tour") == null) {
                    request.setAttribute("errorMessage", "Dữ liệu session không hợp lệ.");
                    response.sendRedirect("/view/common/error.jsp");
                    return;
                }
                request.getRequestDispatcher("/view/staff/confirm.jsp").forward(request, response);
            } else if ("clearSession".equals(service)) {
                session.removeAttribute("actionStatus");
                session.removeAttribute("tourName");
                session.removeAttribute("reason");
                session.removeAttribute("tour");
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ: " + e.getMessage());
            System.out.println("NumberFormatException: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý: " + e.getMessage());
            System.out.println("Exception occurred: " + e.getMessage());
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
            if ("approve".equals(service)) {
                int tourID = Integer.parseInt(request.getParameter("tourID"));
                Tour tour = tourDAO.searchTourByID(tourID);
                String tourName = request.getParameter("tourName");
                tourDAO.changeStatusTour(tourID, 1); // Approve (status = 1)
                if (tour != null) {
                    session.setAttribute("actionStatus", "approve");
                    session.setAttribute("tour", tour);
                    session.setAttribute("tourName", tourName);
                    System.out.println("Forwarding to confirm.jsp with tour: " + tour);
                    request.getRequestDispatcher("/view/staff/confirm.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourID);
                    System.out.println("Failed to find Tour with ID: " + tourID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                }
            } else if ("reject".equals(service)) {
                int tourID = Integer.parseInt(request.getParameter("tourID"));
                String reason = request.getParameter("reason");
                String tourName = request.getParameter("tourName");

                // Validate reason
                if (reason == null || reason.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Vui lòng nhập lý do từ chối.");
                    Tour tour = tourDAO.searchTourByID(tourID);
                    if (tour != null) {
                        int travelAgentID = tour.getTravelAgentID();
                        TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                        int categoryID = tour.getTourCategoryID();
                        TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                        request.setAttribute("tour", tour);
                        request.setAttribute("travelAgentName", travelAgent != null ? travelAgent.getTravelAgentName() : "");
                        request.setAttribute("categoryName", tourCategory != null ? tourCategory.getTourCategoryName() : "");
                        request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourID);
                        request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    }
                    return;
                }
                if (reason.trim().length() > 255) {
                    request.setAttribute("errorMessage", "Lý do từ chối không được dài quá 255 ký tự.");
                    Tour tour = tourDAO.searchTourByID(tourID);
                    if (tour != null) {
                        int travelAgentID = tour.getTravelAgentID();
                        TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                        int categoryID = tour.getTourCategoryID();
                        TourCategory tourCategory = tourCategoryDAO.searchTourCategory(categoryID);
                        request.setAttribute("tour", tour);
                        request.setAttribute("travelAgentName", travelAgent != null ? travelAgent.getTravelAgentName() : "");
                        request.setAttribute("categoryName", tourCategory != null ? tourCategory.getTourCategoryName() : "");
                        request.getRequestDispatcher("/view/staff/tourDetail.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourID);
                        request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    }
                    return;
                }

                // Trim reason before saving
                String trimmedReason = reason.trim();
                tourDAO.changeStatusTour(tourID, 3); // Reject (status = 3)
                Tour tour = tourDAO.searchTourByID(tourID);
                if (tour != null) {
                    tour.setReason(trimmedReason);
                    tourDAO.updateTour(tour);
                    session.setAttribute("actionStatus", "reject");
                    session.setAttribute("tour", tour);
                    session.setAttribute("tourName", tourName);
                    session.setAttribute("reason", trimmedReason);
                    request.getRequestDispatcher("/view/staff/confirm.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Không tìm thấy tour với ID: " + tourID);
                    System.out.println("Failed to find Tour with ID: " + tourID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                }
            } else {
                doGet(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ: " + e.getMessage());
            System.out.println("NumberFormatException: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý: " + e.getMessage());
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        }
    }

    private void processSendConfirmationEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            Tour tour = (Tour) session.getAttribute("tour");
            String actionStatus = (String) session.getAttribute("actionStatus");
            String tourName = (String) session.getAttribute("tourName");
            String reason = (String) session.getAttribute("reason");

            if (tour == null || actionStatus == null || tourName == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"status\":\"error\",\"message\":\"Missing required session data.\"}");
                return;
            }

            if ("reject".equals(actionStatus) && reason == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"status\":\"error\",\"message\":\"Missing rejection reason.\"}");
                return;
            }

            // Lấy travelAgentID từ Tour
            int travelAgentID = tour.getTravelAgentID();
            TravelAgent travelAgent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
            String email = (travelAgent != null) ? travelAgent.getGmail() : null;

            if (email == null || email.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"status\":\"error\",\"message\":\"Email không hợp lệ hoặc không tìm thấy TravelAgent.\"}");
                return;
            }

            StringBuilder body = new StringBuilder();
            body.append("<h2>Xác nhận hành động tour</h2>");
            body.append("<p>Ngày xử lý: ").append(java.time.LocalDate.now()).append("</p>");
            body.append("<h3>Thông tin hành động:</h3>");
            body.append("<p>Tên tour: ").append(tour.getTourName()).append("</p>");
            body.append("<p>Email: ").append(email).append("</p>");
            if ("approve".equals(actionStatus)) {
                body.append("<p>Trạng thái: Đã duyệt thành công.</p>");
            } else if ("reject".equals(actionStatus)) {
                body.append("<p>Trạng thái: Đã từ chối.</p>");
                body.append("<p>Lý do: ").append(reason).append("</p>");
            }
            body.append("<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>");

            try {
                EmailSender.send(email, "Xác nhận "  + " tour - " + java.time.LocalDate.now(), body.toString());
                out.println("{\"status\":\"sent\"}");
            } catch (MessagingException | IOException e) {
                System.out.println("Gửi email thất bại cho hành động " + actionStatus + ": " + e.getMessage());
                out.println("{\"status\":\"error\",\"message\":\"Lỗi khi gửi email: " + e.getMessage() + "\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"status\":\"error\",\"message\":\"Lỗi khi gửi email: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}

