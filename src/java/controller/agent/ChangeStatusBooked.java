
package controller.agent;

import dao.BookTourDAO;
import dao.EntertainmentDAO;
import dao.IBookTour;
import dao.IEntertainmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * @author ad
 */
@WebServlet(name="ChangeStatusBooked", urlPatterns={"/ChangeStatusBooked"})
public class ChangeStatusBooked extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ChangeStatusBooked</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeStatusBooked at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

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
        IBookTour bookTour = new BookTourDAO();
        String bookIdParam = request.getParameter("id") != null ? request.getParameter("id").trim() : "";
        String currentPageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "1"; // Default to page 1

        // Validate service ID
        if (bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã dịch vụ không thể để trống");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdParam);
            if (bookId <= 0) {
                throw new NumberFormatException("Mã dịch vụ phải là số nguyên dương");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng mã dịch vụ không hợp lệt: " + e.getMessage());
            return;
        }

        int currentPage;
        try {
            currentPage = Integer.parseInt(currentPageParam);
            if (currentPage <= 0) {
                currentPage = 1;
            }
        } catch (NumberFormatException e) {
            currentPage = 1; 
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng số trang không hợp lệ: " + e.getMessage());
            return;
        }

        try {
            int currentStatus = bookTour.getStatusByBookId(bookId);
            int newStatus = (currentStatus == 1) ? 0 : 1;
            boolean isSuccess = bookTour.changeStatus(bookId, newStatus);
            if (!isSuccess) {
                throw new SQLException("Không cập nhật được trạng thái cho mã dịch vụ: " + bookId);
            }
            response.sendRedirect("ManageTourBooked?page=" + currentPage);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
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
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
