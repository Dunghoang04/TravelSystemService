package controller.agent.accommodation.room;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.RoomDAO;
import model.Room;

/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-08  1.0        Nguyễn Văn Vang   First implementation
 */
/**
 * Servlet to handle the addition of a new room for an accommodation.
 */
@WebServlet(name="AddRoom", urlPatterns={"/AddRoom"})
public class AddRoom extends HttpServlet {
   
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
            out.println("<title>Servlet AddRoom</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRoom at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles HTTP GET request to display the room addition form.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Retrieve accommodationID from request parameters
        int accommodationID = Integer.parseInt(request.getParameter("id"));
        // Set accommodationID in the request for use in JSP
        request.setAttribute("accommodationID", accommodationID);
        // Forward to the JSP page to display the room addition form
        request.getRequestDispatcher("/view/agent/accommodation/room/addRoom.jsp").forward(request, response);
    } 

    /** 
     * Handles HTTP POST request to add a new room to the database.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         try {
            // Retrieve form parameters: accommodationID, roomTypes, numberOfRooms, priceOfRoom
            int accommodationID = Integer.parseInt(request.getParameter("accommodationID"));
            String roomTypes = request.getParameter("roomTypes");
            int numberOfRooms = Integer.parseInt(request.getParameter("numberOfRooms"));
            float priceOfRoom = Float.parseFloat(request.getParameter("priceOfRoom"));
           
            // Create a Room object with roomID set to 0 (auto-incremented in DB)
            Room room = new Room(0, accommodationID, roomTypes, numberOfRooms, priceOfRoom, 1);
            // Initialize RoomDAO to interact with the database
            RoomDAO dao = new RoomDAO();
            // Call the method to add the room to the database
            dao.addRoom(room);

            // Redirect to the room management page with accommodationID
            response.sendRedirect("ManagementRoom?id=" + accommodationID);
        } catch (Exception e) {
            // Store error message in request if an exception occurs
            request.setAttribute("error", "Error adding room: " + e.getMessage());
            // Forward back to the JSP page to display the error
            request.getRequestDispatcher("/view/agent/accommodation/room/addRoom.jsp").forward(request, response);
        }
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