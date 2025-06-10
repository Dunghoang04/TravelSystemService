/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 */
package controller.agent.accommodations;

import dao.IAccommodationDAO;
import dao.AccommodationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Accommodation;

/**
 * Servlet for managing accommodations in the TravelAgentService system.
 * Handles requests to display a paginated list of accommodations, with optional
 * search by name functionality for agents.
 *
 * @author TuanAnhJr
 */
public class ManagementAccommodation extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     * Generates a basic HTML response (currently unused as doGet handles logic).
     *
     * @param request  the HttpServletRequest object containing client request
     * @param response the HttpServletResponse object for sending response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Generate basic HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManagementAccommodation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagementAccommodation at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles HTTP GET requests to display a paginated list of accommodations.
     * Supports searching accommodations by name and stores the result in session.
     * Forwards to AgentAccommodation.jsp for rendering.
     *
     * @param request  the HttpServletRequest object containing client request
     * @param response the HttpServletResponse object for sending response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Initialize session and DAO
            HttpSession session = request.getSession();
            IAccommodationDAO accDao = new AccommodationDAO();

            // Get search parameter 'name' and trim it
            String name = request.getParameter("name");
            name = (name != null) ? name.trim() : "";
            List<Accommodation> list;

            // Search by name if provided, otherwise get all accommodations
            if (!name.isEmpty()) {
                list = accDao.searchAccommodationByName(name);
                request.setAttribute("name", name); // Retain search term for JSP
            } else {
                list = accDao.getListAccommodation();
            }

            // Configure pagination (5 items per page)
            int amountPerPage = 5;
            int totalItems = list.size();
            int numberPage = (int) Math.ceil((double) totalItems / amountPerPage);
            int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            int begin = (currentPage - 1) * amountPerPage;
            int startIndex = (currentPage - 1) * amountPerPage + 1;
            int end = Math.min(begin + amountPerPage, totalItems);

            // Extract paginated list
            List<Accommodation> listAccommodationPaged = list.subList(begin, end);

            // Set attributes for JSP
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("startIndex", startIndex);
            session.setAttribute("listAcc", listAccommodationPaged);

            // Forward to JSP for rendering
            request.getRequestDispatcher("view/agent/accommodation/agentAccommodation.jsp").forward(request, response);
        } catch (Exception e) {
            // Log error and redirect to error page
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests by delegating to processRequest.
     *
     * @param request  the HttpServletRequest object containing client request
     * @param response the HttpServletResponse object for sending response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
        return "Servlet for managing accommodations with search and pagination.";
    }
}