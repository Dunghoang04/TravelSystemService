/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Group 6          First implementation
 */
package controller.common;

import dao.FeedbackDAO;
import dao.TourDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import model.Feedback;
import model.Tour;

/**
 *
 * @author ad
 */
public class HomeServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        HttpSession session = request.getSession(); // Get or create HTTP session
        TourDAO tdao = new TourDAO(); // Initialize TourDAO instance

        Vector<Tour> topTour = null; // Initialize vector for top tours
         FeedbackDAO fdao = new FeedbackDAO(); // Initialize FeedbackDAO instance
         List<Feedback> recentFeedbacks = null; // Initialize list for recent feedbacks

        try {
            topTour = tdao.getTopNewTour(); // Retrieve top new tours from DAO
            request.setAttribute("topTour", topTour); // Store tours in request scope
            recentFeedbacks = fdao.getRecentFeedbacks(); // Retrieve 5 most recent feedbacks
            request.setAttribute("recentFeedbacks", recentFeedbacks); // Store feedbacks in request scope
            request.getRequestDispatcher("view/common/home.jsp").forward(request, response); // Forward to home page
        } catch (Exception e) { // Handle other exceptions
            request.setAttribute("error", "Lỗi khi tải trang chủ: " + e.getMessage()); // Set error message
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response); // Forward to error page
        }
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
        return "Xử lý yêu cầu cho trang chủ, hiển thị các tour nổi bật và phản hồi gần đây";
    }
}
