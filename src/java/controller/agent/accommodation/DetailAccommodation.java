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
package controller.agent.accommodation;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Accommodation;
import model.Room;
import dao.AccommodationDAO;
import dao.IRoomDAO;
import dao.RoomDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet to handle the display of accommodation details and associated rooms.
 * @author Nhat Anh
 */
@MultipartConfig
@WebServlet(name="DetailAccommodation", urlPatterns={"/DetailAccommodation"})
public class DetailAccommodation extends HttpServlet {
   
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
            out.println("<title>Servlet DetailAccommodation</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailAccommodation at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles HTTP GET request to retrieve and display accommodation and room details.
     * @param request The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object to send the response to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Retrieve accommodation ID from request parameters
        int id = Integer.parseInt(request.getParameter("id"));
        // Initialize AccommodationDAO to interact with the database
        AccommodationDAO accDAO = new AccommodationDAO();
        // Initialize IRoomDAO with RoomDAO implementation for room data access
        IRoomDAO rdao = new RoomDAO();
        // Fetch accommodation details by service ID
        Accommodation acc = accDAO.getAccommodationByServiceId(id);
        List<Room> rooms;
        try {
            // Fetch list of rooms associated with the accommodation ID
            rooms = rdao.getRoomsByAccommodationID(id);
            // Set accommodation and rooms attributes for use in JSP
            request.setAttribute("acc", acc);
            request.setAttribute("rooms", rooms);
            // Forward to the JSP page to display accommodation and room details
            request.getRequestDispatcher("view/agent/accommodation/detailAccommodation.jsp").forward(request, response);
        } catch (SQLException ex) {
            // Log any SQL exceptions that occur during data retrieval
            Logger.getLogger(DetailAccommodation.class.getName()).log(Level.SEVERE, null, ex);
        }
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