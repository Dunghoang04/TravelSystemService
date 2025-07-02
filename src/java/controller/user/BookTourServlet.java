/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dao.BookTourDAO;
import dao.TourDAO;
import dao.VATDAO;
import dao.VoucherDAO;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import model.Tour;
import model.User;
import model.VAT;
import model.Voucher;

/**
 *
 * @author Hung
 */
public class BookTourServlet extends HttpServlet {

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
            out.println("<title>Servlet BookTourServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookTourServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false); // false: không tạo session mới nếu chưa có
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("LoginLogout");
            return;
        }
        TourDAO tourDao = new TourDAO();
        VoucherDAO voucherDao = new VoucherDAO();
        VATDAO vatDao = new VATDAO();
        String id_raw = request.getParameter("tourID");
        try {
            VAT vat = vatDao.getVATActive();
            request.setAttribute("vat", vat);

            ArrayList<Voucher> voucherlist = voucherDao.getAllVoucherActive();
            request.setAttribute("voucherlist", voucherlist);

            int id = Integer.parseInt(id_raw);
            Tour tour = tourDao.searchTour(id);
            request.setAttribute("tour", tour);
            request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi nhận diện chuyến du lịch vui lòng thoát ra vào lại!.");
            request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Lỗi dữ liệu từ server");
            request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);

        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi dữ liệu IO");
            request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("LoginLogout");
            return;
        }
        
        VoucherDAO voucherDao = new VoucherDAO();
        User user = (User) session.getAttribute("loginUser");
        TourDAO tourDao = new TourDAO();
        String numberAdult_raw = request.getParameter("adult");
        String numberChildren_raw = request.getParameter("children");
        String tourId_raw = request.getParameter("tourID");
        

        try {
            ArrayList<Voucher> voucherlist = voucherDao.getAllVoucherActive();
            int tourId = Integer.parseInt(tourId_raw);
            Tour tour = tourDao.searchTour(tourId);
            // Lấy thông tin Voucher
            String voucherIdRaw = request.getParameter("voucherId");
            Integer voucherId = (voucherIdRaw != null && !voucherIdRaw.trim().isEmpty())
                    ? Integer.parseInt(voucherIdRaw)
                    : null;

            // Lấy thông tin người dùng nhập
            int numberAdult = Integer.parseInt(numberAdult_raw);
            if (numberAdult > 1000) {
                request.setAttribute("numberAdultError", "Số lượng người lớn không thể lớn hơn 1000");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }

            int numberChildren = Integer.parseInt(numberChildren_raw);
            if (numberChildren > 1000) {
                request.setAttribute("numberChildrenError", "Số lượng trẻ em không thể lớn hơn 1000");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }

            String firstName = request.getParameter("firstName").trim();
            if (firstName.length() > 25) {
                request.setAttribute("firstNameError", "Họ của bạn quá dài, vui lòng nhập dưới 25 kí tự");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }
            String lastName = request.getParameter("lastName").trim();
            if (lastName.length() > 25) {
                request.setAttribute("lastNameError", "Tên của bạn quá dài, vui lòng nhập dưới 25 kí tự");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }
            String phone = request.getParameter("phone").trim();
            if (phone == null || phone.trim().isEmpty()) {
                request.setAttribute("phoneError", "Số điện thoại không được để trống!");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            } else if (!phone.matches("^0\\d{9}$")) {
                request.setAttribute("phoneError", "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số!");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }
            String gmail = request.getParameter("email").trim();
            if (gmail == null || !gmail.toLowerCase().matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
                request.setAttribute("errorGmail", "Chỉ chấp nhận email hợp lệ như @gmail.com, @abc.edu, @company.com...");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }

            String note = request.getParameter("note").trim();
            if (note.length() > 255) {
                request.setAttribute("noteError", "Ghi chú của bạn quá dài vui lòng nhập dưới 256 kí tự");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }
            int isBookedForOther = Integer.parseInt(request.getParameter("isOther"));
            double totalPrice = Double.parseDouble(request.getParameter("finalAmount"));
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            String formattedPrice = formatter.format(totalPrice);

            // Thực hiện insert
            BookTourDAO bookTourDao = new BookTourDAO();
            boolean success = bookTourDao.insertBookDetail(
                    user.getUserID(), tourId, voucherId,
                    numberAdult, numberChildren, firstName, lastName,
                    phone, gmail, note, isBookedForOther, totalPrice, 1
            );

            if (success) {
                String subject = "Xác nhận đặt tour thành công - " + tour.getTourName();
                String content = "<h3>Chào " + firstName + " " + lastName + ",</h3>"
                        + "<p>Bạn đã đặt thành công tour: <strong>" + tour.getTourName() + "</strong></p>"
                        + "<ul>"
                        + "<li><b>Thông tin đại lí:</b> " + tour.getStartDay() + "</li>"
                        + "<li><b>Ngày khởi hành:</b> " + tour.getStartDay() + "</li>"
                        + "<li><b>Kết thúc ngày:</b> " + tour.getEndDay() + "</li>"
                        + "<li><b>Số người lớn:</b> " + numberAdult + "</li>"
                        + "<li><b>Số trẻ em:</b> " + numberChildren + "</li>"
                        + "<li><b>Tổng tiền thanh toán:</b> " + formattedPrice + " VND</li>"
                        + "</ul>"
                        + "<p>Quý khách vui lòng chú ý thời gian để có thể trải nghiệm dịch vụ tốt nhất</p>"
                        + "<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>";

                // Cấu hình thông tin email
                final String fromEmail = "goviet1901@gmail.com";       // Email của bạn
                final String password = "jmcjkgtcqiwrlfsm";           // Mật khẩu ứng dụng

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                Session sessionMail = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

                try {
                    Message message = new MimeMessage(sessionMail);
                    message.setFrom(new InternetAddress(fromEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gmail));
                    String encodedSubject;
                    try {
                        encodedSubject = MimeUtility.encodeText(subject, "UTF-8", "B");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();  // Quan trọng để xem lỗi
                        encodedSubject = subject; // fallback nếu encode lỗi
                    }
                    message.setSubject(encodedSubject);
                    message.setContent(content, "text/html; charset=utf-8");

                    Transport.send(message);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                // Sau khi gửi mail xong redirect hoặc forward
                response.sendRedirect("booktourservlet?success=true&tourID=" + tour.getTourID());
                return;
            } else {
                request.setAttribute("error", "Đặt chuyến đi thất bại. Vui lòng xem lại số dư và chắc chắc bạn đủ số dư để thanh toán");
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("exception", "Có lỗi xảy ra khi xử lý đặt chuyến đi.");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        }
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
