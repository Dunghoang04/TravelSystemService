/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelSystemService
 * Description: Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24   1.1      H       First implementation
 * [Not specified in original code]
 */

package controller.agent;

import dao.BookTourDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.BookDetail;

/**
 *
 * @author Hung
 */
public class BookDetailManagement extends HttpServlet {

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
        BookTourDAO bookTourDao = new BookTourDAO();
        ArrayList<BookDetail> listBook = new ArrayList<>();
        if(listBook.isEmpty() || listBook == null){
            request.setAttribute("error", "Không có đơn đặt nào");
            request.getRequestDispatcher("view/agent/bookdetail.jsp").forward(request, response);
            return;
        }
        request.setAttribute("listBook", listBook);
        request.getRequestDispatcher("view/agent/bookdetail.jsp").forward(request, response);
    } 

    

}
