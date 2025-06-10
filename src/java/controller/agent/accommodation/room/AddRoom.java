/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 *
 * Servlet for adding a new room to an accommodation in the TravelAgentService system.
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
 * Handles HTTP requests to add a new room to an accommodation.
 * Processes GET requests to display the add form and POST requests to save room data.
 */
public class AddRoom extends HttpServlet {

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
            out.println("<title>Servlet AddRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRoom at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles GET requests to display the add room form.
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
        
        // Set accommodation ID for JSP
        request.setAttribute("id", accommodationID);
        
        // Forward to add room form
        request.getRequestDispatcher("/view/agent/accommodation/room/AddRoom.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to add a new room to the database.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve and parse form parameters
            int accommodationID = Integer.parseInt(request.getParameter("id"));
            String roomTypes = request.getParameter("roomTypes");
            int numberOfRooms = Integer.parseInt(request.getParameter("numberOfRooms"));
            float priceOfRoom = Float.parseFloat(request.getParameter("priceOfRoom"));

            // Create new room object
            Room room = new Room(accommodationID, roomTypes, numberOfRooms, priceOfRoom);
            
            // Insert room into database
            RoomDAO dao = new RoomDAO();
            dao.addRoom(room);

            // Redirect to room management page
            response.sendRedirect("managementroom");
        } catch (Exception e) {
            // Forward to add form with error message
            request.setAttribute("error", "Lỗi khi thêm phòng: " + e.getMessage());
            request.getRequestDispatcher("/view/agent/accommodation/room/AddRoom.jsp").forward(request, response);
        }
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