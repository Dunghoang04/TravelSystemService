/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-08  1.0        Nguyễn Văn Vang   First implementation
 */
package controller.agent.accommodation;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Accommodation;
import dao.AccommodationDAO;
import model.TravelAgent;

/**
 * Servlet to handle the management and display of accommodations.
 * @author Nhat Anh
 */
@MultipartConfig
@WebServlet(name="ManagementAccommodation", urlPatterns={"/ManagementAccommodation"})
public class ManagementAccommodation extends HttpServlet {
   
    /** 
     * Processes HTTP GET and POST requests.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Set the response content type to HTML with UTF-8 encoding
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Output basic HTML content for the response page
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManagementAccommodation</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagementAccommodation at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles HTTP GET request to retrieve and display a list of accommodations.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
    // Get the current HTTP session
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("gmail") == null) {
        response.sendRedirect("LoginLogout?service=loginUser");
        return;
    }
    TravelAgent travelAgent = (TravelAgent) session.getAttribute("agent");
    if (travelAgent == null) {
        request.setAttribute("error", "No travel agent session found");
        request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        return;
    }
    int travelAgentId = travelAgent.getTravelAgentID();

    // Initialize AccommodationDAO to interact with the database
    AccommodationDAO accDao = new AccommodationDAO();

    // Retrieve search parameter for accommodation name
    String name = request.getParameter("name");
    // Trim the name parameter, default to empty string if null
    name = (name != null) ? name.trim() : "";
    List<Accommodation> list;
    if (!name.isEmpty()) {
        // Search accommodations by name if provided
        list = accDao.searchAccommodationByName(name);
        // Set the search name for display in the search field
        request.setAttribute("name", name);
    } else {
        // Fetch accommodations by agent ID if no search name is provided
        list = accDao.getListAccommodation(travelAgentId);
    }
    // Store the accommodation list in the session
    session.setAttribute("listAcc", list);
    // Forward to the JSP page to display the accommodation list
    request.getRequestDispatcher("view/agent/accommodation/agentAccommodation.jsp").forward(request, response);
}

    /** 
     * Handles HTTP POST request by delegating to processRequest.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Delegate to processRequest for handling POST requests
        processRequest(request, response);
    }

    /** 
     * Returns a brief description of the servlet.
     * @return A String containing the servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}