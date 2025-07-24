/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-08  1.0        Nguyễn Văn Vang   First implementation
 */
/*
 * Click nb://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb://SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.agent.accommodation.room;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.RoomDAO;
import java.util.List;
import model.Room;

/**
 * Servlet to handle the management and display of rooms for an accommodation.
 * @author Nhat Anh
 */
@WebServlet(name="ManagementRoom", urlPatterns={"/ManagementRoom"})
public class ManagementRoom extends HttpServlet {
   
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
            out.println("<title>Servlet ManagementRoom</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagementRoom at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles HTTP GET request to retrieve and display the list of rooms.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Initialize RoomDAO to interact with the database
        RoomDAO dao = new RoomDAO();
        List<Room> rooms;

        try {
            // Retrieve accommodationID from request parameters
            String accommodationIDStr = request.getParameter("id");

            if (accommodationIDStr != null && !accommodationIDStr.trim().isEmpty()) {
                try {
                    // Parse accommodationID and fetch rooms by accommodationID
                    int accommodationID = Integer.parseInt(accommodationIDStr);
                    rooms = dao.getRoomsByAccommodationID(accommodationID);
                    if (rooms.isEmpty()) {
                        // Set error message if no rooms are found for the accommodationID
                        request.setAttribute("error", "No rooms found for accommodationID: " + accommodationID);
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid accommodationID format and fetch all rooms
                    request.setAttribute("error", "Invalid accommodationID format.");
                    rooms = dao.getAllRooms();
                }
            } else {
                // Fetch all rooms if no accommodationID is provided
                rooms = dao.getAllRooms();
            }

            // Set rooms and accommodationID attributes for use in JSP
            request.setAttribute("rooms", rooms);
            request.setAttribute("accommodationID", accommodationIDStr);

        } catch (Exception e) {
            // Handle any exceptions and set an empty room list with an error message
            request.setAttribute("error", "Error retrieving room list: " + e.getMessage());
            request.setAttribute("rooms", List.of());
        }

        // Forward to the JSP page to display the room list
        request.getRequestDispatcher("/view/agent/accommodation/room/agentRoom.jsp").forward(request, response);
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