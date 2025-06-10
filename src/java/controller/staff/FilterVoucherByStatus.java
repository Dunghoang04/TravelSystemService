/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
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
 * This servlet handles the filtering of vouchers based on their status.
 * It supports filtering by:
 *  - Active vouchers (status = 1)
 *  - Inactive vouchers (status = 0)
 *  - All vouchers (status = 2, which redirects to the main voucher listing)
 * 
 * The servlet also implements pagination (10 items per page by default).
 * 
 * Servlet URL: /filtervoucherbystatus
 * 
 * @author Hung
 */
public class FilterVoucherByStatus extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Simply forwards the user to the voucher.jsp page.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the voucher view page (initial display or fallback)
        request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * This method processes the filtering of vouchers based on status and implements pagination.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the status parameter from the request
        String status_raw = request.getParameter("status");

        // Instantiate DAO for voucher operations
        VoucherDAO voucherDao = new VoucherDAO();

        // Default pagination settings
        int page = 1;
        int itemsPerPage = 10;

        // Check for the 'page' parameter and parse it if available
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1; // fallback to page 1 on invalid input
            }
        }

        try {
            // Convert the status to an integer
            int status = Integer.parseInt(status_raw);

            // Get the total number of vouchers matching the given status
            int totalItems = voucherDao.getVoucherByStatus(status).size();

            // Calculate total pages based on item count
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            // Determine the start and end index for the current page
            int start = (page - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, totalItems);

            // Keep the current status selected for user interface
            request.setAttribute("status", status);

            // Filter for Active vouchers
            if (status == 1) {
                List<Voucher> listVoucher = voucherDao.getVoucherByStatus(status).subList(start, end);
                request.setAttribute("listVoucher", listVoucher);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
                return;
            }

            // Filter for Inactive vouchers
            if (status == 0) {
                List<Voucher> listVoucher = voucherDao.getVoucherByStatus(status).subList(start, end);
                request.setAttribute("listVoucher", listVoucher);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
                return;
            }

            // Status = 2 → means show all vouchers
            if (status == 2) {
                response.sendRedirect("listvoucher");
                return;
            }

            // If status is invalid, forward to the view without loading data
            request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Handle invalid number input for status
            e.printStackTrace();
        } catch (SQLException ex) {
            // Log any SQL exceptions that occur
            Logger.getLogger(FilterVoucherByStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Provides a brief description of the servlet.
     * 
     * @return a String describing this servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet for filtering vouchers based on status with pagination support.";
    }
}
