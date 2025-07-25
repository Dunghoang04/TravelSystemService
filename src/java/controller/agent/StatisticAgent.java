/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.agent;

import dao.BookTourDAO;
import dao.IBookTour;
import dao.IService;
import dao.ITourDAO;
import dao.ServiceDao;
import dao.TourDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import model.BookDetail;
import model.TravelAgent;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "StatisticAgent", urlPatterns = {"/StatisticAgent"})
public class StatisticAgent extends HttpServlet {

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
        HttpSession session = request.getSession();
        String gmail = (String) session.getAttribute("gmail");
        if (gmail == null) {
            response.sendRedirect("LoginLogout?service=loginUser"); // Redirect to login if not authenticated
            return;
        }
        TravelAgent agent = (TravelAgent) session.getAttribute("agent");
        IBookTour bookTour = new BookTourDAO();
        IService sDAO = new ServiceDao();
        ITourDAO tourDAO = new TourDAO();
        try {
            int totalTours = tourDAO.getTotalToursByTravelAgent(agent.getTravelAgentID());
            int totalRestaurant = sDAO.getTotalRestaurantByTravelAgent(agent.getTravelAgentID());
            int totalEntertaiment = sDAO.getTotalEntertaimentByTravelAgent(agent.getTravelAgentID());
            int totalAccommodation = sDAO.getTotalAccommodationByTravelAgent(agent.getTravelAgentID());
            request.setAttribute("totalTours", totalTours);
            request.setAttribute("totalRestaurant", totalRestaurant);
            request.setAttribute("totalEntertaiment", totalEntertaiment);
            request.setAttribute("totalAccommodation", totalAccommodation);
            List<BookDetail> latestBookings = bookTour.getLatest5Bookings(agent.getTravelAgentID());
            request.setAttribute("latestBookings", latestBookings);
            request.getRequestDispatcher("/view/agent/statisticAgent.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi xử lý yêu cầu: " + e.getMessage());
            request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
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
