package controller.agent;

import dao.BookTourDAO;
import dao.IBookTour;
import dao.ITourDAO;
import dao.IVoucherDAO;
import dao.TourDAO;
import dao.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.BookDetail;
import model.Tour;
import model.TravelAgent;
import model.Voucher;

/**
 *
 * @author ad
 */
@WebServlet(name = "DetailTourBooked", urlPatterns = {"/DetailTourBooked"})
public class DetailTourBooked extends HttpServlet {

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
            out.println("<title>Servlet DetailTourBooked</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailTourBooked at " + request.getContextPath() + "</h1>");
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
        String idParam = request.getParameter("id");
        IBookTour bookTour = new BookTourDAO();
        IVoucherDAO voucherDAO = new VoucherDAO();
        ITourDAO tourDAO = new TourDAO();

        try {
            int id = Integer.parseInt(idParam);
            BookDetail bookDetail = bookTour.getBookDetailById(id); // Trả về một đối tượng BookDetail

            if (bookDetail != null) {
                Map<String, Object> item = new HashMap<>();
                item.put("bookID", bookDetail.getBookID());
                item.put("userID", bookDetail.getUserID());
                item.put("tourID", bookDetail.getTourID());
                item.put("voucherID", bookDetail.getVoucherID());
                item.put("bookDate", bookDetail.getBookDate());
                item.put("numberAdult", bookDetail.getNumberAdult());
                item.put("numberChildren", bookDetail.getNumberChildren());
                item.put("firstName", bookDetail.getFirstName());
                item.put("lastName", bookDetail.getLastName());
                item.put("phone", bookDetail.getPhone());
                item.put("gmail", bookDetail.getGmail());
                item.put("note", bookDetail.getNote());
                item.put("isBookedForOther", bookDetail.getIsBookedForOther());
                item.put("totalPrice", bookDetail.getTotalPrice());
                item.put("status", bookDetail.getStatus());

                // Lấy tên tour từ tourID
                Tour tour = tourDAO.searchTourByID(bookDetail.getTourID()); // Sửa thành getTourById
                item.put("tourName", tour != null ? tour.getTourName() : "Không xác định");
                item.put("tourImage", tour != null ? tour.getImage() : "Không xác định");
                // Lấy mã voucher từ voucherID
                Voucher voucher = voucherDAO.getVoucherById(bookDetail.getVoucherID());
                item.put("code", voucher != null ? voucher.getVoucherCode() : "Không có");

                request.setAttribute("item", item); // Gán một đối tượng duy nhất
                request.getRequestDispatcher("view/agent/tourBookedDetail.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Không tìm thấy chi tiết đặt tour với ID: " + id);
                request.getRequestDispatcher("view/agent/tourBookedDetail.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ: " + e.getMessage());
            request.getRequestDispatcher("view/agent/tourBookedDetail.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("view/agent/tourBookedDetail.jsp").forward(request, response);
        }
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
        processRequest(request, response);
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
