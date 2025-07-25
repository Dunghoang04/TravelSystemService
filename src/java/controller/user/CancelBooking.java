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
import dao.RequestCancelDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.BookDetail;
import model.User;

/**
 *
 * @author Hung
 */
public class CancelBooking extends HttpServlet {
   

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
        
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("LoginLogout");
            return;
        }

        User user = (User) session.getAttribute("loginUser");

        RequestCancelDAO requestCancelDao = new RequestCancelDAO();
        BookTourDAO bookTourDao = new BookTourDAO();
        
        String bookId_raw = request.getParameter("bookID").trim();
        String reason = request.getParameter("reason").trim();
        
        try {
            int bookId = Integer.parseInt(bookId_raw);
            BookDetail bookDetail = bookTourDao.getBookDetailById(bookId);
            if(requestCancelDao.createReasonCancel(bookId,user.getUserID(), reason)){
                if(bookTourDao.updateBookStatusByBookCode(bookDetail.getBookCode(), 5)){
                    response.sendRedirect("currentbooking?cancelSuccess=true");
                }else{
                    response.sendRedirect("currentbooking?cancelSuccess=false");
                }
            }else{
                 response.sendRedirect("currentbooking?cancelSuccess=false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
