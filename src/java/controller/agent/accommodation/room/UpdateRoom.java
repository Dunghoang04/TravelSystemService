/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-08  1.0        Nguyễn Văn Vang   First implementation
 */
package controller.agent.accommodation.room;

import dao.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;

/**
 * Servlet to handle the updating of an existing room for an accommodation.
 * @author Nhat Anh
 */
@WebServlet(name="UpdateRoom", urlPatterns={"/UpdateRoom"})
public class UpdateRoom extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateRoom</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRoom at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    /** 
     * Handles HTTP GET request to display the room update form.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Retrieve roomID from request parameters
        int roomID = Integer.parseInt(request.getParameter("id"));
        // Initialize RoomDAO to interact with the database
        RoomDAO dao = new RoomDAO();
        Room room;
        try {
            // Fetch room details by roomID
            room = dao.getRoomByRoomID(roomID);
            if (room == null) {
                // Set error message if room does not exist and redirect
                request.setAttribute("error", "Room does not exist.");
                response.sendRedirect("ManagementRoom");
                return;
            }
            // Set room object for use in JSP
            request.setAttribute("room", room);
            // Forward to the JSP page to display the update form
            request.getRequestDispatcher("/view/agent/accommodation/room/updateRoom.jsp").forward(request, response);
        } catch (SQLException ex) {
            // Log any SQL exceptions that occur during room retrieval
            Logger.getLogger(UpdateRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    /** 
     * Handles HTTP POST request to update room details in the database.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Retrieve form parameters: roomID, accommodationID, roomTypes, numberOfRooms, priceOfRoom
            int roomID = Integer.parseInt(request.getParameter("roomID"));
            int accommodationID = Integer.parseInt(request.getParameter("accommodationID"));
            String roomTypes = request.getParameter("roomTypes");
            int numberOfRooms = Integer.parseInt(request.getParameter("numberOfRooms"));
            float priceOfRoom = Float.parseFloat(request.getParameter("priceOfRoom"));
            int status = Integer.parseInt(request.getParameter("status"));

            // Initialize RoomDAO to interact with the database
            RoomDAO dao = new RoomDAO();
            // Update room details in the database using roomID
            dao.updateRoom(roomID, roomTypes, numberOfRooms, priceOfRoom, status);

            // Redirect to the room management page
            response.sendRedirect("ManagementRoom");
        } catch (Exception e) {
            // Store error message in request if an exception occurs
            request.setAttribute("error", "Error updating room: " + e.getMessage());
            // Forward back to the JSP page to display the error
            request.getRequestDispatcher("/view/agent/accommodation/room/updateRoom.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a brief description of the servlet.
     * @return A String containing the servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for updating room details";
    }
}