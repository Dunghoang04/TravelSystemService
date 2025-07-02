package controller.agent;
import dao.BookTourDAO;
import dao.IBookTour;
import dao.ITourDAO;
import dao.IVoucherDAO;
import dao.TourDAO;
import dao.VoucherDAO;
import java.io.IOException;
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
@WebServlet(name = "ManageTourBooked", urlPatterns = {"/ManageTourBooked"})
public class ManageTourBooked extends HttpServlet {

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
    }

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
        HttpSession session = request.getSession();
        TravelAgent travelAgent = (TravelAgent) session.getAttribute("agent");
        IBookTour bookTour = new BookTourDAO();
        IVoucherDAO voucherDAO = new VoucherDAO();
        ITourDAO tourDAO = new TourDAO();

        if (session == null || session.getAttribute("gmail") == null) {
            response.sendRedirect("LoginLogout?service=loginUser");
            return;
        }

        String searchName = request.getParameter("searchName") != null ? request.getParameter("searchName").trim() : "";
        String statusType = request.getParameter("statusType") != null ? request.getParameter("statusType").trim() : "";
        request.setAttribute("searchName", searchName);
        request.setAttribute("statusType", statusType);

        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int amountPerPage = 5; // Default items per page

        try {
            List<BookDetail> listBookDetail;
            if (!searchName.isEmpty() && !statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                listBookDetail = bookTour.searchByNameAndStatus(travelAgent.getUserID(), searchName, status, currentPage, amountPerPage);
                int numberPage = (int) Math.ceil(bookTour.countByNameAndStatus(travelAgent.getUserID(), searchName, status) / (double) amountPerPage);
                int startIndex = (currentPage - 1) * amountPerPage + 1;
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            } else if (!statusType.isEmpty()) {
                int status = Integer.parseInt(statusType);
                listBookDetail = bookTour.getBookDetailsByAgentAndStatus(travelAgent.getUserID(), status, currentPage, amountPerPage);
                int numberPage = (int) Math.ceil(bookTour.countByStatus(status) / (double) amountPerPage); // Updated to use countByStatus
                int startIndex = (currentPage - 1) * amountPerPage + 1;
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            } else if (!searchName.isEmpty()) {
                listBookDetail = bookTour.searchByName(travelAgent.getUserID(), searchName, currentPage, amountPerPage); // Replaced getBookDetailsByAgentAndName
                int numberPage = (int) Math.ceil(bookTour.countByName(travelAgent.getUserID(), searchName) / (double) amountPerPage);
                int startIndex = (currentPage - 1) * amountPerPage + 1;
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            } else {
                listBookDetail = bookTour.getBookDetailsByAgent(travelAgent.getUserID(), currentPage, amountPerPage); // Updated to include pagination
                int numberPage = (int) Math.ceil(bookTour.countByAgent(travelAgent.getUserID()) / (double) amountPerPage);
                int startIndex = (currentPage - 1) * amountPerPage + 1;
                request.setAttribute("numberPage", numberPage);
                request.setAttribute("startIndex", startIndex);
            }

            List<Map<String, Object>> enhancedList = new ArrayList<>();
            for (BookDetail book : listBookDetail) {
                Map<String, Object> item = new HashMap<>();
                item.put("bookID", book.getBookID());
                item.put("userID", book.getUserID());
                item.put("tourID", book.getTourID());
                item.put("voucherID", book.getVoucherID());
                item.put("bookDate", book.getBookDate());
                item.put("numberAdult", book.getNumberAdult());
                item.put("numberChildren", book.getNumberChildren());
                item.put("firstName", book.getFirstName());
                item.put("lastName", book.getLastName());
                item.put("phone", book.getPhone());
                item.put("gmail", book.getGmail());
                item.put("note", book.getNote());
                item.put("isBookedForOther", book.getIsBookedForOther());
                item.put("totalPrice", book.getTotalPrice());
                item.put("status", book.getStatus());

                // Lấy tên tour từ tourID
                Tour tour = tourDAO.searchTour(book.getTourID());
                item.put("tourName", tour != null ? tour.getTourName() : "Không xác định");

                // Lấy mã voucher từ voucherID
                Voucher voucher = voucherDAO.getVoucherById(book.getVoucherID());
                item.put("code", voucher != null ? voucher.getVoucherCode() : "không có");

                enhancedList.add(item);
            }

            request.setAttribute("currentPage", currentPage);
            request.setAttribute("listBookDetail", enhancedList);
            request.getRequestDispatcher("view/agent/tourBooked.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Giá trị trạng thái không hợp lệ");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
            return;
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
        doGet(request, response); // Redirect POST to GET for consistency
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manages booked tours for travel agents";
    }
}