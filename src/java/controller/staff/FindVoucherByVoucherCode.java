/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hung              First implementation
 */
package controller.staff;

import dao.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Voucher;

/**
 * This servlet handles the search for vouchers using their voucher code.
 * It processes HTTP POST requests and supports pagination for displaying results.
 * 
 * Functionalities:
 * - Search vouchers by exact or partial voucher code.
 * - Display results with pagination (10 items per page).
 * - Maintain the search keyword and page number during navigation.
 * 
 * Servlet URL: /findvoucherbyvouchercode
 * 
 * View: view/staff/voucher.jsp
 * 
 * @author Hung
 */
public class FindVoucherByVoucherCode extends HttpServlet {

    /**
     * Default generated placeholder for GET and POST requests, not used in production.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Sample HTML response for testing only
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FindVoucherByVoucherCode</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FindVoucherByVoucherCode at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * Simply forwards to the voucher listing page without processing search.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Performs voucher search by code and displays paginated results.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the search keyword from request parameters
        String voucherCode = request.getParameter("voucherCode").trim();

        // Validate user input
        if(voucherCode.length()>25){
            request.setAttribute("error", "Please enter input less than or equal 25 character");
            request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
            return;
        }
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            request.setAttribute("error", "Please enter a voucher code to search.");
            request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
            return;
        }

        VoucherDAO voucherDao = new VoucherDAO();

        // Default pagination setup
        int page = 1;
        int itemsPerPage = 10;

        // Try to get the requested page number from parameters
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                // Fallback to page 1 if input is not a valid number
                page = 1;
            }
        }

        List<Voucher> fullResult = null;

        // Execute search query using DAO
        try {
            fullResult = voucherDao.getVoucherByVoucherCode(voucherCode);
        } catch (SQLException ex) {
            // Log SQL error and continue with error response
            Logger.getLogger(FindVoucherByVoucherCode.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Check for null result (e.g., DB failure or unhandled exception)
        if (fullResult == null) {
            request.setAttribute("error", "An error occurred while searching for vouchers.");
            request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
            return;
        }

        // Calculate pagination values
        int totalItems = fullResult.size(); // total number of matching vouchers
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, totalItems); // Ensure no IndexOutOfBounds

        // Extract the sublist for current page
        List<Voucher> listVoucher = fullResult.subList(start, end);

        // Set attributes for JSP rendering
        request.setAttribute("listVoucher", listVoucher);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchVoucherCode", voucherCode); // Preserve keyword for UI display

        // Forward request and data to the JSP page for display
        request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
    }

    /**
     * Returns a brief description of this servlet.
     *
     * @return a String describing this servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet to search vouchers by code with pagination support.";
    }

}
