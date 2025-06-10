/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hung          First implementation
 */
package controller.staff;

import dao.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import model.Voucher;

/**
 * This servlet is responsible for retrieving the list of vouchers and handling pagination.
 * It fetches data from the database using VoucherDAO and forwards it to the JSP for rendering.
 * 
 * Functionalities:
 * - Load all vouchers with pagination (GET method)
 * - Default number of vouchers per page: 10
 * - Handle SQL exceptions and invalid page input gracefully
 * 
 * JSP View: view/staff/voucher.jsp
 * 
 * @author Hung
 */
public class ListVoucher extends HttpServlet {

    /**
     * This method generates a sample HTML page response.
     * It is not used in actual voucher listing, but provided as a fallback.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Sample HTML for testing servlet is accessible
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListVoucher</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListVoucher at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles HTTP GET requests to list vouchers with pagination.
     * Default values: page=1, itemsPerPage=10.
     * Retrieves voucher data from the database and forwards to JSP for display.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Kiểm tra session
        HttpSession session = request.getSession(false); // false: không tạo session mới nếu chưa có
            if (session == null || session.getAttribute("gmail") == null) {
            // Chưa đăng nhập
            response.sendRedirect("LoginLogout");
            return;
        }
        VoucherDAO voucherDao = new VoucherDAO(); // DAO instance for voucher access
        try {
            int page = 1; // Default to first page
            int itemsPerPage = 10; // Default number of items per page

            // Get "page" parameter if available
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    page = 1; // Fallback if parsing fails
                }
            }

            // Retrieve all vouchers from DAO
            List<Voucher> allVouchers = voucherDao.getAllVoucher();

            int totalItems = allVouchers.size(); // Total number of vouchers
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage); // Compute total pages

            // Compute start and end indexes for pagination
            int start = (page - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, totalItems);

            // Extract sublist for current page
            List<Voucher> listVoucher = allVouchers.subList(start, end);

            // Set attributes to forward to JSP
            request.setAttribute("listVoucher", listVoucher);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            // Forward to the voucher list JSP
            request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
        } catch (SQLException e) {
            // If any SQL error occurs, set error attribute and forward to JSP
            request.setAttribute("error", "SQL Error occurred while retrieving vouchers.");
            request.getRequestDispatcher("view/staff/voucher.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     * This method simply delegates to processRequest (fallback/test only).
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a brief description of this servlet.
     *
     * @return servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for listing all vouchers with pagination support";
    }// </editor-fold>

}
