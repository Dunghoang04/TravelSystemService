/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dao.TravelAgentDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.TravelAgent;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "Login", urlPatterns = {"/LoginLogout"})
public class Login extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        UserDAO uDAO = new UserDAO();
        TravelAgentDAO aDAO = new TravelAgentDAO();
        String service = request.getParameter("service");
        if (service == null) {
            // Hiển thị trang login nếu không có service
            request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
            return;
        }

        if (service.equals("loginUser")) {
            String gmail = request.getParameter("gmail");
            String password = request.getParameter("password");

            String error = validate(gmail, password);
            if (error != null) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
                return;
            }

            User u = uDAO.checkLogin(gmail, password);

            if (u != null) {
                session.setAttribute("loginUser", u);
                int role = u.getRoleID(); // Lấy vai trò người dùng
                session.setAttribute("gmail", gmail);


                // Điều hướng theo vai trò
                switch (role) {
                    case 1:
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                        break;
                    case 2:
                        response.sendRedirect(request.getContextPath() + "/staff/dashboard.jsp");
                        break;
                    case 3:
                        response.sendRedirect(request.getContextPath() + "/home");
                        break;
                    case 4:
                        TravelAgent a = aDAO.searchByTravelAgentGmail(gmail);
                        session.setAttribute("agent", a);
                        response.sendRedirect(request.getContextPath() + "/ManageTravelAgentProfile");

                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/home");
                }
                return;
            } else {
                request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
                return;
            }
        }

        if (service.equals("logoutUser")) {
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/home");

            return;
        }
    }

    public String validate(String gmail, String password) {
        if (gmail == null || gmail.trim().isEmpty()) {
            return "Vui lòng điền gmail!";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Vui lòng điền password!";
        }

        return null;
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
        processRequest(request, response);
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
        processRequest(request, response);
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
