/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai         Second implement
 */
package controller.user;

import dao.ITourCategoryDAO;
import dao.ITourDAO;
import dao.TourCategoryDAO;
import dao.TourDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tour;
import model.TourCategory;

@WebServlet(name = "TourServlet", urlPatterns = {"/TourServlet"})
public class TourServlet extends HttpServlet {

    private static final int PAGE_SIZE = 5; // Number of tours per page

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    /**
     * Retrieves the current page number from the request, defaulting to 1 if
     * invalid.
     *
     * @param request The HTTP request
     * @return The page number
     */
    private int getPageParameter(HttpServletRequest request) {
        try {
            String pageParam = request.getParameter("page");
            int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
            return Math.max(page, 1); // Ensure page is at least 1
        } catch (NumberFormatException e) {
            return 1; // Default to page 1 on invalid input
        }
    }

    /**
     * Processes tour search with filters and pagination.
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @throws ServletException If a servlet error occurs
     * @throws IOException If an I/O error occurs
     */
    private void processTourSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ITourDAO tdao = new TourDAO();
        ITourCategoryDAO tcdao = new TourCategoryDAO();
        LocalDate currentDate = LocalDate.now();
        int page = getPageParameter(request);

        // Get filter parameters
        String budget = request.getParameter("budget");
        String departure = request.getParameter("departure");
        String destination = request.getParameter("destination");
        String departureDate = request.getParameter("departureDate");
        String tourCategory = request.getParameter("tourCategory");

        // Set default departureDate to current date if not provided
        if (departureDate == null || departureDate.trim().isEmpty()) {
            departureDate = currentDate.toString(); // Format: yyyy-MM-dd
        }

        // Set attributes for JSP
        request.setAttribute("defaultDepartureDate", departureDate);
        request.setAttribute("budget", budget);
        request.setAttribute("departure", departure);
        request.setAttribute("destination", destination);
        request.setAttribute("departureDate", departureDate);
        request.setAttribute("tourCategory", tourCategory);

        try {
            // Fetch dynamic options
            request.setAttribute("startPlaces", tdao.getUniqueStartPlaces());
            request.setAttribute("endPlaces", tdao.getUniqueEndPlaces());
            request.setAttribute("categories", tcdao.getAllTourCategory());

            Vector<Tour> tours;
            int totalTours;
            int totalPages;

            try {
                Date sqlDepartureDate = Date.valueOf(departureDate);
                Date sqlCurrentDate = Date.valueOf(currentDate);

                if (sqlDepartureDate.after(sqlCurrentDate) || sqlDepartureDate.equals(sqlCurrentDate)) {
                    // Fetch filtered tours with pagination using all parameters
                    tours = tdao.getToursWithFilters(budget, departure, destination, departureDate, tourCategory, page, PAGE_SIZE);
                    totalTours = tdao.getTotalToursWithFilters(budget, departure, destination, departureDate, tourCategory);
                    totalPages = (int) Math.ceil((double) totalTours / PAGE_SIZE);
                    // Set attributes for JSP
                    request.setAttribute("tours", tours);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalTours", totalTours);
                    request.setAttribute("totalPages", totalPages);
                } else {

                    request.setAttribute("error", "Ngày đi không được là ngày trong quá khứ.");
                }
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Định dạng ngày không hợp lệ. Vui lòng chọn ngày theo định dạng yyyy-MM-dd.");
            }

            // Forward to tour.jsp
            request.getRequestDispatcher("view/user/tour.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(TourServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("tours", new Vector<>());
            request.setAttribute("error", "Database error occurred while fetching tours.");
            request.getRequestDispatcher("view/user/tour.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processTourSearch(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processTourSearch(request, response);
    }

    @Override
    public String getServletInfo() {
        return "TourServlet handles tour search and filtering";
    }
}
