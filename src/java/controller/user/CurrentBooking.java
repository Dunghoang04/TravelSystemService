package controller.user;

import dao.BookTourDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.BookDetail;
import model.User;

/**
 *
 * @author Hung
 */
public class CurrentBooking extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("LoginLogout");
            return;
        }

        User user = (User) session.getAttribute("loginUser");

        try {
            BookTourDAO dao = new BookTourDAO();
            Map<BookDetail, String> bookingMap = dao.getCurrentBookingByUser(user.getUserID());
            List<Map.Entry<BookDetail, String>> allBookings = new ArrayList<>(bookingMap.entrySet());

            // ===== Phân trang =====
            int pageSize = 5;
            int currentPage = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }

            int totalBookings = allBookings.size();
            int totalPages = (int) Math.ceil((double) totalBookings / pageSize);

            int fromIndex = (currentPage - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, totalBookings);
            List<Map.Entry<BookDetail, String>> bookingsPage = allBookings.subList(fromIndex, toIndex);

            request.setAttribute("bookingList", bookingsPage);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("view/user/currentBooking.jsp").forward(request, response);
    }

}
