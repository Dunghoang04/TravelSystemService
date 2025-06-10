/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 *
 * Servlet for displaying accommodation and room details in the TravelAgentService system.
 */
package controller.agent.accommodation;

import dao.AccommodationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Accommodation;
import model.Room;

/**
 * Handles HTTP requests to display details of an accommodation and its associated room.
 * Processes GET requests to fetch and display data via a JSP.
 */
public class DetailAccommodation extends HttpServlet {

    /**
     * Default method for processing HTTP requests (not used).
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DetailAccommodation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailAccommodation at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles GET requests to display accommodation and room details.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Parse service ID from request
        int id = Integer.parseInt(request.getParameter("id"));
        
        // Fetch accommodation and room details
        AccommodationDAO accDAO = new AccommodationDAO();
        Accommodation acc = accDAO.getAccommodationByServiceId(id);
        Room room = accDAO.getRoomById(id);
        
        // Set attributes for JSP
        request.setAttribute("acc", acc);
        request.setAttribute("room", room);
        
        // Forward to detail page
        request.getRequestDispatcher("view/agent/accommodation/DetailAccommodation.jsp").forward(request, response);
    }

    /**
     * Handles POST requests (currently not implemented).
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}