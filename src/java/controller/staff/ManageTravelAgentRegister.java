/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai         First implementation
 */
package controller.staff;

import dao.ITravelAgentDAO;
import dao.TravelAgentDAO;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.TravelAgent;
import service.EmailSender;
import java.io.IOException;

@WebServlet(name = "ManageTravelAgentRegister", urlPatterns = {"/ManageTravelAgentRegister"})
public class ManageTravelAgentRegister extends HttpServlet {

    private ITravelAgentDAO travelAgentDAO = new TravelAgentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        try {
            if ("list".equals(service) || service == null) {
                String statusParam = request.getParameter("status");
                String page = request.getParameter("page");
                System.out.println("doGet: service=list, page=" + page + ", status=" + statusParam);
                if (statusParam != null && !statusParam.equals("all")) {
                    int status = Integer.parseInt(statusParam);
                    request.setAttribute("travelAgents", travelAgentDAO.searchByTravelAgentByStatus(status));
                } else {
                    request.setAttribute("travelAgents", travelAgentDAO.getAllTravelAgent());
                }
                session.setAttribute("currentPage", page != null && page.matches("\\d+") ? page : "1");
                session.setAttribute("currentStatus", statusParam != null ? statusParam : "all");
                System.out.println("doGet: Stored currentPage=" + session.getAttribute("currentPage") + ", currentStatus=" + session.getAttribute("currentStatus"));
                request.getRequestDispatcher("/view/staff/manageTravelAgentRegister.jsp").forward(request, response);
            } else if ("profile".equals(service)) {
                int travelAgentID = Integer.parseInt(request.getParameter("travelAgentID"));
                String page = request.getParameter("page");
                String status = request.getParameter("status");
                System.out.println("doGet: service=profile, travelAgentID=" + travelAgentID + ", page=" + page + ", status=" + status);
                session.setAttribute("currentPage", page != null && page.matches("\\d+") ? page : "1");
                session.setAttribute("currentStatus", status != null ? status : "all");
                System.out.println("doGet: Stored currentPage=" + session.getAttribute("currentPage") + ", currentStatus=" + session.getAttribute("currentStatus"));
                TravelAgent agent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                if (agent == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy đại lý với ID: " + travelAgentID);
                    System.out.println("doGet: Agent not found for travelAgentID=" + travelAgentID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("agent", agent);
                request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
            } else if ("clearSession".equals(service)) {
                session.removeAttribute("actionStatus");
                session.removeAttribute("travelAgentName");
                session.removeAttribute("reason");
                session.removeAttribute("travelAgent");
                System.out.println("doGet: Cleared session attributes");
                response.sendRedirect(request.getContextPath() + "/ManageTravelAgentRegister?service=list");
            } else {
                request.setAttribute("errorMessage", "Dịch vụ không hợp lệ: " + service);
                System.out.println("doGet: Invalid service=" + service);
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
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
                int travelAgentID = Integer.parseInt(request.getParameter("travelAgentID"));
                int userID = Integer.parseInt(request.getParameter("userID"));
                String travelAgentName = request.getParameter("travelAgentName");
                String page = request.getParameter("page");
                String status = request.getParameter("status");
                System.out.println("doPost: service=" + service + ", travelAgentID=" + travelAgentID + ", userID=" + userID + ", travelAgentName=" + travelAgentName + ", page=" + page + ", status=" + status);
                TravelAgent agent = travelAgentDAO.searchTravelAgentByID(travelAgentID);

                if (agent == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy đại lý với ID: " + travelAgentID);
                    System.out.println("doPost: Agent not found for travelAgentID=" + travelAgentID);
                    request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
                    return;
                }

                int newStatus = "approve".equals(service) ? 1 : 3;
                String reason = null;
                if ("reject".equals(service)) {
                    reason = request.getParameter("reason");
                    System.out.println("Reject: reason=" + (reason != null ? reason : "null"));
                    if (reason == null || reason.trim().isEmpty()) {
                        System.out.println("Reject: Reason is empty, forwarding back to profileTravelAgent.jsp");
                        request.setAttribute("agent", agent);
                        request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
                        return;
                    }
                    if (reason.trim().length() > 255) {
                        System.out.println("Reject: Reason exceeds 255 characters, forwarding back to profileTravelAgent.jsp");
                        request.setAttribute("agent", agent);
                        request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
                        return;
                    }
                    reason = reason.trim();
                    System.out.println("Reject: Updating agent with reason: " + reason);
                    travelAgentDAO.updateRejectionReason(travelAgentID, reason);
                }

                System.out.println("Changing agent status to: " + newStatus);
                travelAgentDAO.changeStatusTravelAgent(userID, newStatus);

                String email = agent.getGmail();
                if (email == null || email.trim().isEmpty()) {
                    System.out.println("Invalid or missing email for travelAgentID=" + travelAgentID);
                    request.setAttribute("errorMessage", "Email không hợp lệ hoặc không tìm thấy đại lý.");
                    request.setAttribute("agent", agent);
                    request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
                    return;
                }

                StringBuilder body = new StringBuilder();
                body.append("<h2>Xác nhận hành động đại lý du lịch</h2>");
                body.append("<p>Ngày xử lý: ").append(java.time.LocalDate.now()).append("</p>");
                body.append("<h3>Thông tin hành động:</h3>");
                body.append("<p>Tên công ty: ").append(agent.getTravelAgentName()).append("</p>");
                body.append("<p>Email: ").append(email).append("</p>");
                body.append("<p>Trạng thái: ").append("approve".equals(service) ? "Đã duyệt thành công." : "Đã từ chối.").append("</p>");
                if ("reject".equals(service)) {
                    body.append("<p>Lý do: ").append(reason).append("</p>");
                }
                body.append("<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>");

                try {
                    System.out.println("Sending email to: " + email);
                    EmailSender.send(email, "Xác nhận " + ("approve".equals(service) ? "duyệt" : "từ chối") + " đại lý - " + java.time.LocalDate.now(), body.toString());
                    session.setAttribute("currentPage", page != null && page.matches("\\d+") ? page : "1");
                    session.setAttribute("currentStatus", status != null ? status : "all");
                    System.out.println("doPost: Stored currentPage=" + session.getAttribute("currentPage") + ", currentStatus=" + session.getAttribute("currentStatus"));
                    request.setAttribute("successMessage", "approve".equals(service) ?
                        "Đại lý " + travelAgentName + " đã được duyệt thành công. Email xác nhận đã được gửi." :
                        "Đại lý " + travelAgentName + " đã bị từ chối. Email xác nhận đã được gửi.");
                    session.removeAttribute("actionStatus");
                    session.removeAttribute("travelAgentName");
                    session.removeAttribute("reason");
                    session.removeAttribute("travelAgent");
                    System.out.println("Success: Forwarding to profileTravelAgent.jsp with successMessage");
                    request.setAttribute("agent", agent);
                    request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
                } catch (MessagingException | IOException e) {
                    System.out.println("Failed to send email for action " + service + ": " + e.getMessage());
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "Lỗi khi gửi email: " + e.getMessage());
                    request.setAttribute("agent", agent);
                    request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Dịch vụ không hợp lệ: " + service);
                System.out.println("Invalid service: " + service);
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ: " + e.getMessage());
            System.out.println("NumberFormatException in doPost: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý: " + e.getMessage());
            System.out.println("Exception in doPost: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
        }
    }
}