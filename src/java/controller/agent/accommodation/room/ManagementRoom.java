/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0       NguyenVanVang     First implementation
 *
 * Servlet for managing and displaying rooms in the TravelAgentService system.
 */
package controller.agent.accommodation.room;

import dao.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Room;

/**
 * Handles HTTP requests to display a list of rooms, optionally filtered by accommodation ID.
 * Processes GET requests to fetch room data and forward to the management JSP.
 */
public class ManagementRoom extends HttpServlet {

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
            out.println("<title>Servlet ManagementRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagementRoom at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles GET requests to fetch and display a list of rooms.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize DAO for room data access
        RoomDAO dao = new RoomDAO();
        List<Room> rooms;

        try {
            // Retrieve search parameters
            String searchTerm = request.getParameter("searchTerm");
            String serviceIDStr = request.getParameter("accommodationID");

            // Check for accommodation ID parameter
            if (serviceIDStr != null && !serviceIDStr.trim().isEmpty()) {
                try {
                    // Parse accommodation ID
                    int serviceID = Integer.parseInt(serviceIDStr);
                    
                    // Fetch room by accommodation ID
                    Room room = dao.getRoomByAccommodationID(serviceID);
                    if (room != null) {
                        // Convert single room to list for JSP compatibility
                        rooms = List.of(room);
                    } else {
                        // Set empty list and error message if no room found
                        rooms = List.of();
                        request.setAttribute("error", "Không tìm thấy phòng với accommodationID: " + serviceID);
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid accommodation ID format
                    request.setAttribute("error", "Mã phòng không hợp lệ.");
                    rooms = dao.getAllRooms();
                }
            } else {
                // Fetch all rooms if no accommodation ID provided
                rooms = dao.getAllRooms();
            }

            // Set room list for JSP
            request.setAttribute("rooms", rooms);

        } catch (Exception e) {
            // Handle any errors during data retrieval
            request.setAttribute("error", "Lỗi khi lấy danh sách phòng: " + e.getMessage());
            request.setAttribute("rooms", List.of());
        }

        // Forward to room management JSP
        request.getRequestDispatcher("/view/agent/accommodation/room/AgentRoom.jsp").forward(request, response);
    }

    /**
     * Handles POST requests by delegating to processRequest (not used).
     *
     * @param request  servlet request
     * @param response servlet response
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
        return "Short description";
    }
}