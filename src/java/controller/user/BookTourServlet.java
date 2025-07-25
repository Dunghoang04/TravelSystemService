/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-18  1.0        Hung              First implementation
 */
package controller.user;

import dao.BookTourDAO;
import dao.TourDAO;
import dao.TransactionHistoryDAO;
import dao.VATDAO;
import dao.VoucherDAO;
import dao.WalletDAO;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import model.Tour;
import model.User;
import model.VAT;
import model.Voucher;

/**
 *
 * @author Hung
 */
public class BookTourServlet extends HttpServlet {

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
            Tour tour = tourDao.searchTourByID(id);
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
        VATDAO vatDao = new VATDAO();
        User user = (User) session.getAttribute("loginUser");
        TourDAO tourDao = new TourDAO();
        String numberAdult_raw = request.getParameter("adult");
        String numberChildren_raw = request.getParameter("children");
        String tourId_raw = request.getParameter("tourID");
        String paymentMethodId_raw = request.getParameter("paymentMethodID");

        try {
            ArrayList<Voucher> voucherlist = voucherDao.getAllVoucherActive();
            int tourId = Integer.parseInt(tourId_raw);
            Tour tour = tourDao.searchTourByID(tourId);
            // Lấy thông tin Voucher
            String voucherIdRaw = request.getParameter("voucherId");
            Integer voucherId = (voucherIdRaw != null && !voucherIdRaw.trim().isEmpty())
                    ? Integer.parseInt(voucherIdRaw)
                    : null;

            String firstName = request.getParameter("firstName").trim();
            if (firstName.length() > 25 || firstName.length()<4) {
                request.setAttribute("firstNameError", "Họ của bạn không hợp lệ, vui lòng nhập 4 đến 25 kí tự");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }
            String lastName = request.getParameter("lastName").trim();
            if (lastName.length() > 25 || lastName.length()<4) {
                request.setAttribute("lastNameError", "Tên của bạn không hợp lệ, vui lòng nhập 4 đến 25 kí tự");
                request.setAttribute("voucherlist", voucherlist);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                return;
            }
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
            long bookCode = generateRandomBookCode();
            String descriptionPayment = "ThanhToan" + user.getUserID() + "_" + (int) totalPrice + "_" + generateRandomAlphaNumeric(6);

            // Thực hiện insert
            BookTourDAO bookTourDao = new BookTourDAO();
            TransactionHistoryDAO transactionHistoryDao = new TransactionHistoryDAO();
            boolean success = false;
            int paymentMethodId = Integer.parseInt(paymentMethodId_raw);
            if (paymentMethodId == 1) {
                success = bookTourDao.insertBookDetail(
                        user.getUserID(), tourId, voucherId,
                        numberAdult, numberChildren, firstName, lastName,
                        phone, gmail, note, isBookedForOther, totalPrice, 1, 1, bookCode
                );
                if (success) {
                    sendBookingConfirmationEmail(firstName, lastName, gmail, tour, numberAdult, numberChildren, formattedPrice);
                    response.sendRedirect("walletpaymentsuccess?bookCode=" + bookCode);
                    return;
                }else{
                    VAT vat = vatDao.getVATActive();
                    request.setAttribute("vat", vat);
                    request.setAttribute("voucherlist", voucherlist);
                    request.setAttribute("error", "Đặt chuyến đi thất bại. Vui lòng xem lại số dư và chắc chắc bạn đủ số dư để thanh toán");
                    request.setAttribute("tour", tour);
                    request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
                }
            } else if (paymentMethodId == 2) {
                int waitPaymentStatus = 7;
                String cancelUrl = "http://localhost:9999/TravelSystemService/cancelpayment";
                String returnUrl = "http://localhost:9999/TravelSystemService/returnpayment?bookCode=" + bookCode;
                boolean check = bookTourDao.inserBookDetailPayOs(
                        user.getUserID(), tourId, voucherId,
                        numberAdult, numberChildren, firstName, lastName,
                        phone, gmail, note, isBookedForOther, totalPrice, waitPaymentStatus, 2, bookCode
                );
                if (check) {
                    String checkoutUrl = payOS(bookCode, (int) totalPrice, descriptionPayment, firstName + " " + lastName, gmail, phone, tour.getTourName(), numberAdult + numberChildren, cancelUrl, returnUrl);
                    if (checkoutUrl != null) {
                        response.sendRedirect(checkoutUrl + "?bookCode=" + bookCode);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("exception", "Có lỗi xảy ra khi xử lý đặt chuyến đi.");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response);
        }
    }

    public static long generateRandomBookCode() {
        long min = 100000000000L;             // Số tối thiểu (12 chữ số)
        long max = 9007199254740991L;         // Giới hạn tối đa theo yêu cầu PayOS
        return min + (long) (Math.random() * (max - min));
    }

    private static String generateSignature(String data, String checksumKey) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(checksumKey.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secretKey);

        byte[] hashBytes = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder hash = new StringBuilder();
        for (byte b : hashBytes) {
            hash.append(String.format("%02x", b));
        }
        return hash.toString();
    }

    private String generateRandomAlphaNumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private static String payOS(long bookCode, int totalPrice, String descriptionPayment, String name, String email, String phone, String tourName, int totalPeople, String cancelUrl, String returnUrl) {
        try {
            long expiredAt = Instant.now().getEpochSecond() + 1800;

            String data = "amount=" + totalPrice
                    + "&cancelUrl=" + cancelUrl
                    + "&description=" + descriptionPayment
                    + "&orderCode=" + bookCode
                    + "&returnUrl=" + returnUrl;

            String checksumKey = "efe3011fe6a87a5e7be4d32bcc001dd2bd6de461001ac49b6d784a65f949cddc";
            String signature = generateSignature(data, checksumKey);

            String url = "https://api-merchant.payos.vn/v2/payment-requests";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-client-id", "e9654ff3-74c1-47f3-95a3-4374182de555");
            con.setRequestProperty("x-api-key", "7f110b74-cf82-483b-b4d5-9e0725616a39");
            con.setDoOutput(true);

            String jsonInputString = "{\n"
                    + "\"orderCode\": " + bookCode + ",\n"
                    + "\"amount\": " + totalPrice + ",\n"
                    + "\"description\": \"" + descriptionPayment + "\",\n"
                    + "\"buyerName\": \"" + name + "\",\n"
                    + "\"buyerEmail\": \"" + email + "\",\n"
                    + "\"buyerPhone\": \"" + phone + "\",\n"
                    + "\"items\": [\n"
                    + "{\n"
                    + "\"name\": \"" + tourName + "\",\n"
                    + "\"quantity\": " + totalPeople + ",\n"
                    + "\"price\": " + totalPrice + "\n"
                    + "}\n"
                    + "],\n"
                    + "\"cancelUrl\": \"" + cancelUrl + "\",\n"
                    + "\"returnUrl\": \"" + returnUrl + "\",\n"
                    + "\"expiredAt\": " + expiredAt + ",\n"
                    + "\"signature\": \"" + signature + "\"\n"
                    + "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // ✅ Trích xuất "checkoutUrl"
            String jsonResponse = response.toString();
            int index = jsonResponse.indexOf("\"checkoutUrl\":\"");
            if (index != -1) {
                int start = index + 15;
                int end = jsonResponse.indexOf("\"", start);
                String checkoutUrl = jsonResponse.substring(start, end);
                return checkoutUrl;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendBookingConfirmationEmail(String firstName, String lastName, String gmail, Tour tour, int numberAdult, int numberChildren, String formattedPrice) {
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

    }


}
