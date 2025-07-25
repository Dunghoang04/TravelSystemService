/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-14  1.0        Hung              First implementation
 */

package controller.user;

import dao.BookTourDAO;
import dao.TravelAgentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import model.BookDetail;
import model.Tour;
import model.TravelAgent;

/**
 *
 * @author Hung
 */
public class BookingDetail extends HttpServlet {
   
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("loginUser") == null) {
        response.sendRedirect("LoginLogout");
        return;
    }

    String bookId_raw = request.getParameter("bookID");
    try {
        int bookId = Integer.parseInt(bookId_raw);
        BookTourDAO bookTourDao = new BookTourDAO();
        Map<BookDetail, Tour> bookingDetail = bookTourDao.getBookingDetailById(bookId);

        if (!bookingDetail.isEmpty()) {
            Map.Entry<BookDetail, Tour> entry = bookingDetail.entrySet().iterator().next();
            BookDetail booking = entry.getKey();
            Tour tour = entry.getValue();

            // Lấy agency từ Tour
            TravelAgentDAO agencyDAO = new TravelAgentDAO();
            TravelAgent travelAgent = agencyDAO.searchTravelAgentByID(tour.getTravelAgentID());

            request.setAttribute("booking", booking);
            request.setAttribute("tour", tour);
            request.setAttribute("travelAgent", travelAgent);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    request.getRequestDispatcher("view/user/bookingDetail.jsp").forward(request, response);
    } 
    

}
