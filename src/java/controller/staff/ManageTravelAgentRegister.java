/*
 * Click nfs://netbeans/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nfs://netbeans/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staff;

import dao.ITravelAgentDAO;
import dao.TravelAgentDAO;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TravelAgent;
import service.EmailSender;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "ManageTravelAgentRegister", urlPatterns = {"/ManageTravelAgentRegister"})
public class ManageTravelAgentRegister extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String service = request.getParameter("service");
        ITravelAgentDAO travelAgentDAO = new TravelAgentDAO();

        if ("list".equals(service) || service == null) {
            try {
                String statusParam = request.getParameter("status");
                if (statusParam != null && !statusParam.equals("all")) {
                    int status = Integer.parseInt(statusParam);
                    request.setAttribute("travelAgents", travelAgentDAO.searchByTravelAgentByStatus(status));
                } else {
                    request.setAttribute("travelAgents", travelAgentDAO.getAllTravelAgent());
                }
                request.getRequestDispatcher("/view/staff/manageTravelAgentRegister.jsp").forward(request, response);
            } catch (Exception e) {
                response.sendRedirect("error.jsp");
            }
        } else if ("profile".equals(service)) {
            try {
                int travelAgentID = Integer.parseInt(request.getParameter("travelAgentID"));
                TravelAgent agent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                request.setAttribute("agent", agent);
                request.getRequestDispatcher("/view/staff/profileTravelAgent.jsp").forward(request, response);
            } catch (Exception e) {
                response.sendRedirect("error.jsp");
            }
        } else if ("approve".equals(service)) {
            int travelAgentID = Integer.parseInt(request.getParameter("travelAgentID"));
            int userID = Integer.parseInt(request.getParameter("userID"));
            try {
                travelAgentDAO.changeStatusTravelAgent(userID, 1); // Duyệt (status = 1)
                TravelAgent agent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                if (agent != null) {
                    String subject = "Đăng ký Travel Agent - Thành công";
                    String content = "<h3>Chúc mừng!</h3>" +
                            "<p>Đăng ký Travel Agent của bạn với ID " + travelAgentID + " đã được duyệt thành công.</p>" +
                            "<p>Thông tin liên hệ: " + agent.getTravelAgentGmail() + "</p>";
                    EmailSender.send(agent.getTravelAgentGmail(), subject, content);
                    response.sendRedirect(request.getContextPath() + "/ManageTravelAgentRegister?service=list&status=1"); // Chuyển hướng về trang Hoạt động
                } else {
                    response.sendRedirect("error.jsp");
                }
            } catch (MessagingException | IOException e) {
                response.sendRedirect("error.jsp");
            } catch (Exception e) {
                response.sendRedirect("error.jsp");
            }
        } else if ("reject".equals(service)) {
            int travelAgentID = Integer.parseInt(request.getParameter("travelAgentID"));
            int userID = Integer.parseInt(request.getParameter("userID"));
            String reason = request.getParameter("reason");
            try {
                travelAgentDAO.changeStatusTravelAgent(userID, 3); // Từ chối (status = 3)
                travelAgentDAO.updateRejectionReason(travelAgentID, reason);
                TravelAgent agent = travelAgentDAO.searchTravelAgentByID(travelAgentID);
                if (agent != null) {
                    String subject = "Đăng ký Travel Agent - Bị từ chối";
                    String content = "<h3>Thông báo từ chối!</h3>" +
                            "<p>Đăng ký Travel Agent của bạn với ID " + travelAgentID + " đã bị từ chối.</p>" +
                            "<p>Lý do: " + reason + "</p>" +
                            "<p>Thông tin liên hệ: " + agent.getTravelAgentGmail() + "</p>";
                    EmailSender.send(agent.getTravelAgentGmail(), subject, content);
                    response.sendRedirect(request.getContextPath() + "/ManageTravelAgentRegister?service=list&status=3"); // Chuyển hướng về trang Bị từ chối
                } else {
                    response.sendRedirect("error.jsp");
                }
            } catch (MessagingException | IOException e) {
                response.sendRedirect("error.jsp");
            } catch (Exception e) {
                response.sendRedirect("error.jsp");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}