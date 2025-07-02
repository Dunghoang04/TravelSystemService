/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR     DESCRIPTION
 * 2025-06-24  1.0        Hưng       First implementation
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dao.TransactionHistoryDAO;
import dao.WalletDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Random;
import model.TransactionHistory;
import model.User;
import model.Wallet;

/**
 *
 * @author Hung
 */
public class AmountToPay extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AmountToPay</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AmountToPay at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("LoginLogout");
            return;
        }

        int userId = loginUser.getUserID();

// Lấy ví
        WalletDAO walletDao = new WalletDAO();
        Wallet w = walletDao.getWalletByUserId(userId);
        request.setAttribute("wallet", w);

// Lấy tất cả giao dịch
        TransactionHistoryDAO transactionDao = new TransactionHistoryDAO();
        ArrayList<TransactionHistory> allTransactions = transactionDao.getAllTransactionHistoryByUserId(userId);

// Phân trang
        int pageSize = 5;
        int totalTransactions = allTransactions.size();
        int totalPages = (int) Math.ceil((double) totalTransactions / pageSize);
        if (totalPages == 0) {
            totalPages = 1;
        }

        int pageIndex = 1;
        try {
            pageIndex = Integer.parseInt(request.getParameter("page"));
            if (pageIndex < 1) {
                pageIndex = 1;
            }
            if (pageIndex > totalPages) {
                pageIndex = totalPages;
            }
        } catch (NumberFormatException ignored) {
        }

// Cắt trang
        int fromIndex = (pageIndex - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalTransactions);
        ArrayList<TransactionHistory> pagedTransactions = new ArrayList<>();
        if (fromIndex < toIndex) {
            pagedTransactions = new ArrayList<>(allTransactions.subList(fromIndex, toIndex));
        }

// Gửi về JSP
        request.setAttribute("transactionHistoryList", pagedTransactions);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("view/user/amountToPay.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("LoginLogout");
            return;
        }
        String amount_raw = request.getParameter("amount").trim();
        try{
            double amount = Math.ceil(Double.parseDouble(amount_raw));
            String code = "NAP" + loginUser.getUserID() + "_" + (int) amount + "_" + generateRandomAlphaNumeric(4);
            response.sendRedirect("payment?amount=" + (int) amount + "&code=" + code);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public String generateRandomAlphaNumeric(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
        sb.append(chars.charAt(random.nextInt(chars.length())));
    }
    return sb.toString();
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
