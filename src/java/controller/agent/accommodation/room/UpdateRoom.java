/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 *
 * Servlet for updating room details in the TravelAgentService system.
 */
package controller.agent.accommodation.room;

import dao.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Room;

/**
 * Handles HTTP requests to display and update room details.
 * Processes GET requests to show the update form and POST requests to save changes.
 */
public class UpdateRoom extends HttpServlet {

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
            out.println("<title>Servlet UpdateRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRoom at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles GET requests to display the room update form.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Parse accommodation ID from request
        int accommodationID = Integer.parseInt(request.getParameter("id"));
        
        // Fetch room details
        RoomDAO dao = new RoomDAO();
        Room room = dao.getRoomByAccommodationID(accommodationID);
        
        // Set room object for JSP
        request.setAttribute("room", room);
        
        // Forward to update room form
        request.getRequestDispatcher("view/agent/accommodation/room/UpdateRoom.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to update room details in the database.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        int accommodationID = Integer.parseInt(request.getParameter("roomID"));
        String roomTypes = request.getParameter("roomTypes");
        int numberOfRooms = Integer.parseInt(request.getParameter("numberOfRooms"));
        double priceOfRoom = Double.parseDouble(request.getParameter("priceOfRoom"));

        // Update room in database
        RoomDAO dao = new RoomDAO();
        dao.updateRoom(accommodationID, roomTypes, numberOfRooms, priceOfRoom);

        // Redirect to room management page
        response.sendRedirect("managementroom");
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