/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-20  1.0        Nhat Anh          Initial implementation
 * 2025-06-20  1.1        Grok              Fixed session issues and redirect to forward
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
import java.io.PrintWriter;

@WebServlet(name = "ManageTravelAgentRegister", urlPatterns = {"/ManageTravelAgentRegister"})
public class ManageTravelAgentRegister extends HttpServlet {

    private ITravelAgentDAO travelAgentDAO = new TravelAgentDAO();

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
                    request.setAttribute("travelAgents", travelAgentDAO.searchByTravelAgentByStatus(status));
                } else {
                    request.setAttribute("travelAgents", travelAgentDAO.getAllTravelAgent());
                }
                request.getRequestDispatcher("/view/staff/manageTravelAgentRegister.jsp").forward(request, response);
            } else if ("profile".equals(service)) {
                int travelAgentID = Integer.parseInt(request.getParameter("travelAgentID"));
                TravelAgent agent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                request.setAttribute("agent", agent);
                request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
            } else if ("confirm".equals(service)) {
                if (session.getAttribute("actionStatus") == null || session.getAttribute("travelAgent") == null) {
                    request.setAttribute("errorMessage", "Dữ liệu session không hợp lệ.");
                    response.sendRedirect("view/common/error.jsp");
                    return;
                }
                // Chuyển hướng đến confirm.jsp
                request.getRequestDispatcher("/view/staff/confirm.jsp").forward(request, response);
            } else if ("clearSession".equals(service)) {
                session.removeAttribute("actionStatus");
                session.removeAttribute("travelAgentName");
                session.removeAttribute("reason");
                session.removeAttribute("travelAgent");
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ: " + e.getMessage());
            System.out.println("NumberFormatException: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý: " + e.getMessage());
            System.out.println("Exception occurred: " + e.getMessage());
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
                int travelAgentID = Integer.parseInt(request.getParameter("travelAgentID"));
                TravelAgent agent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                int userID = agent.getUserID();
                String travelAgentName = request.getParameter("travelAgentName");
                travelAgentDAO.changeStatusTravelAgent(userID, 1); // Approve (status = 1)
                if (agent != null) {
                    session.setAttribute("actionStatus", "approve");
                    session.setAttribute("travelAgent", agent);
                    session.setAttribute("travelAgentName", travelAgentName);
                    System.out.println("Forwarding to confirm.jsp with travelAgent: " + agent);
                    request.getRequestDispatcher("/view/staff/confirm.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Không tìm thấy đại lý với ID: " + travelAgentID);
                    System.out.println("Failed to find TravelAgent with ID: " + travelAgentID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                }
            } else if ("reject".equals(service)) {
                int travelAgentID = Integer.parseInt(request.getParameter("travelAgentID"));
                int userID = Integer.parseInt(request.getParameter("userID"));
                String reason = request.getParameter("reason");
                String travelAgentName = request.getParameter("travelAgentName");
                if (reason == null || reason.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Lý do từ chối không được để trống.");
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                System.out.println("Processing reject for travelAgentID: " + travelAgentID + ", userID: " + userID);
                travelAgentDAO.changeStatusTravelAgent(userID, 3); // Reject (status = 3)
                travelAgentDAO.updateRejectionReason(travelAgentID, reason);
                TravelAgent agent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                System.out.println("TravelAgent retrieved for ID " + travelAgentID + ": " + (agent != null ? agent.toString() : "null"));
                if (agent != null) {
                    session.setAttribute("actionStatus", "reject");
                    session.setAttribute("travelAgent", agent);
                    session.setAttribute("travelAgentName", travelAgentName);
                    session.setAttribute("reason", reason);
                    System.out.println("Forwarding to confirm.jsp with travelAgent: " + agent);
                    request.getRequestDispatcher("/view/staff/confirm.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Không tìm thấy đại lý với ID: " + travelAgentID);
                    System.out.println("Failed to find TravelAgent with ID: " + travelAgentID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                }
            } else {
                doGet(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ: " + e.getMessage());
            System.out.println("NumberFormatException: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý: " + e.getMessage());
            System.out.println("Exception occurred: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
        }
    }

    private void processSendConfirmationEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            TravelAgent travelAgent = (TravelAgent) session.getAttribute("travelAgent");
            String actionStatus = (String) session.getAttribute("actionStatus");
            String travelAgentName = (String) session.getAttribute("travelAgentName");
            String reason = (String) session.getAttribute("reason");

            if (travelAgent == null || actionStatus == null || travelAgentName == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"status\":\"error\",\"message\":\"Missing required session data.\"}");
                return;
            }

            System.out.println("TravelAgent from session: " + travelAgent.toString());
            System.out.println("ActionStatus: " + actionStatus);
            System.out.println("TravelAgentName: " + travelAgentName);
            if ("reject".equals(actionStatus) && reason == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"status\":\"error\",\"message\":\"Missing rejection reason.\"}");
                return;
            }

            StringBuilder body = new StringBuilder();
            body.append("<h2>Xác nhận hành động đại lý du lịch</h2>");
            body.append("<p>Ngày xử lý: ").append(java.time.LocalDate.now()).append("</p>");
            body.append("<h3>Thông tin hành động:</h3>");
            body.append("<p>Tên công ty: ").append(travelAgent.getTravelAgentName()).append("</p>");
            String email = travelAgent.getGmail();
            System.out.println("Email recipient: " + email);
            body.append("<p>Email: ").append(email).append("</p>");
            if ("approve".equals(actionStatus)) {
                body.append("<p>Trạng thái: Đã duyệt thành công.</p>");
            } else if ("reject".equals(actionStatus)) {
                body.append("<p>Trạng thái: Đã từ chối.</p>");
                body.append("<p>Lý do: ").append(reason).append("</p>");
            }
            body.append("<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>");

            try {
                EmailSender.send(email, "Xác nhận " + actionStatus + " đại lý - " + java.time.LocalDate.now(), body.toString());
                System.out.println("Email gửi thành công cho hành động " + actionStatus + " tới: " + email);
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
