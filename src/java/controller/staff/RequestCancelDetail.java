/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-14  1.0        Hung              First implementation
 */
package controller.staff;

import dao.BookTourDAO;
import dao.RequestCancelDAO;
import dao.TourDAO;
import dao.TransactionHistoryDAO;
import dao.UserDAO;
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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import model.RequestCancelFullDTO;
import model.User;

/**
 *
 * @author Hung
 */
public class RequestCancelDetail extends HttpServlet {

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
        String id_raw = request.getParameter("id");
        try {
            int id = Integer.parseInt(id_raw);
            RequestCancelDAO requestCancelDao = new RequestCancelDAO();
            RequestCancelFullDTO cancelRequest = requestCancelDao.getRequestCancelFullById(id);
            request.setAttribute("cancelRequest", cancelRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("view/staff/requestCancelDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("LoginLogout");
            return;
        }
        User user = (User) session.getAttribute("loginUser");
        String action = request.getParameter("action");
        RequestCancelDAO requestCancelDao = new RequestCancelDAO();
        TransactionHistoryDAO transactionHistoryDao = new TransactionHistoryDAO();
        WalletDAO walletDao = new WalletDAO();
        VoucherDAO voucherDao = new VoucherDAO();
        BookTourDAO bookTourDao = new BookTourDAO();
        UserDAO userDao = new UserDAO();
        TourDAO tourDao = new TourDAO();
        if ("approve".equals(action)) {
            try {
                int requestCancelID = Integer.parseInt(request.getParameter("requestCancelID"));
                RequestCancelFullDTO fullRequestCancel = requestCancelDao.getRequestCancelFullById(requestCancelID);
                double totalPrice = fullRequestCancel.getBookDetail().getTotalPrice();
                Date requestDate = fullRequestCancel.getRequestCancel().getRequestDate();
                Date bookingDate = fullRequestCancel.getBookDetail().getBookDate();
                Date startDate = fullRequestCancel.getTour().getStartDay();

                // Calculate hours since booking
                long hoursSinceBooking = TimeUnit.MILLISECONDS.toHours(requestDate.getTime() - bookingDate.getTime());
                // Calculate days to tour start
                long daysToStart = TimeUnit.MILLISECONDS.toDays(startDate.getTime() - requestDate.getTime());

                // Calculate refund amount based on cancellation terms
                double refundAmount;
                if (hoursSinceBooking <= 24) {
                    refundAmount = totalPrice; // 100% refund
                } else if (daysToStart >= 7) {
                    refundAmount = totalPrice * 0.8; // 80% refund
                } else if (daysToStart >= 3 && daysToStart < 7) {
                    refundAmount = totalPrice * 0.5; // 50% refund
                } else {
                    refundAmount = 0; // No refund
                }
                String description = "Hoàn tiền chuyến đi " + fullRequestCancel.getTour().getTourName();
                if (refundAmount > 0) {
                    // Update request status
                    double systemBalance = walletDao.getWalletByUserId(6).getBalance();
                    if (systemBalance < refundAmount) {
                        request.setAttribute("errorMessage", "Ví hệ thống không đủ tiền để hoàn cho du khách.");
                        request.setAttribute("cancelRequest", fullRequestCancel);
                        request.getRequestDispatcher("view/staff/requestCancelDetail.jsp").forward(request, response);
                        return;
                    }
                    double amountOfTourist = walletDao.getWalletByUserId(fullRequestCancel.getRequestCancel().getUserID()).getBalance();
                    if (requestCancelDao.changeStatusRequestCancel(requestCancelID, "FINISHED")
                            && transactionHistoryDao.insertTransaction(fullRequestCancel.getRequestCancel().getUserID(), refundAmount, "REFUND", description,amountOfTourist+refundAmount)
                            && walletDao.RechargeToWallet(fullRequestCancel.getRequestCancel().getUserID(), refundAmount)
                            && bookTourDao.updateBookStatusByBookCode(fullRequestCancel.getBookDetail().getBookCode(), 6)
                            && tourDao.updateQuantityAfterCancel(fullRequestCancel.getTour().getTourID(), fullRequestCancel.getBookDetail().getNumberAdult() + fullRequestCancel.getBookDetail().getNumberChildren())) {
                        if (fullRequestCancel.getBookDetail().getVoucherID() != 0) {
                            if (voucherDao.updateQuantityVoucherWhenRefund(fullRequestCancel.getBookDetail().getVoucherID())) {
                                sendApprovedRequestCancelEmail(fullRequestCancel, refundAmount, fullRequestCancel.getBookDetail().getGmail());
                                response.sendRedirect("requestcanceldetail?id=" + requestCancelID + "&approve=true");
                            } else {
                                response.sendRedirect("requestcanceldetail?id=" + requestCancelID + "&approve=false");
                            }
                        } else {
                            sendApprovedRequestCancelEmail(fullRequestCancel, refundAmount, fullRequestCancel.getBookDetail().getGmail());
                            response.sendRedirect("requestcanceldetail?id=" + requestCancelID + "&approve=true");
                        }
                    } else {
                        response.sendRedirect("requestcanceldetail?id=" + requestCancelID + "&approve=false");
                    }

                } else {
                    response.sendRedirect("requestcanceldetail?id=" + requestCancelID + "&approve=false");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("requestcanceldetail?id=" + request.getParameter("requestCancelID") + "&approve=false");
            }

        } else if ("reject".equals(action)) {
            try {
                int requestCancelID = Integer.parseInt(request.getParameter("requestCancelID"));
                RequestCancelFullDTO fullRequestCancel = requestCancelDao.getRequestCancelFullById(requestCancelID);
                String reason = request.getParameter("rejectReason");
                User user1 = userDao.getUserByID(fullRequestCancel.getRequestCancel().getUserID());
                if (requestCancelDao.changeStatusRequestCancel(requestCancelID, "REJECTED")) {
                    sendRejectRequestCancelEmail(fullRequestCancel, user1.getGmail(), reason);
                    response.sendRedirect("requestcanceldetail?id=" + requestCancelID + "&reject=true");
                } else {
                    response.sendRedirect("requestcanceldetail?id=" + requestCancelID + "&reject=false");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("requestcanceldetail?id=" + request.getParameter("requestCancelID") + "&reject=false");
            }
        }

    }

    private void sendApprovedRequestCancelEmail(RequestCancelFullDTO requestFull, double amount, String email) {
        String subject = "Xác nhận huỷ chuyến đi thành công";
        String content = "<h3>Chào " + requestFull.getBookDetail().getFirstName() + " " + requestFull.getBookDetail().getLastName() + ",</h3>"
                + "<p>Bạn đã huỷ chuyến đi: <strong>" + requestFull.getTour().getTourName() + "</strong></p>"
                + "<ul>"
                + "<li><b>Số tiền được chuyển vào ví:</b> " + (int) amount + "VNĐ</li>"
                + "</ul>"
                + "<p>Quý khách vui lòng kiểm tra giao dịch để có thể trải nghiệm dịch vụ tốt nhất</p>"
                + "<p>Bất kì thắc mắc hãy liên hệ qua đường thông tin trong sàn</p>"
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
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

    private void sendRejectRequestCancelEmail(RequestCancelFullDTO requestFull, String email, String reason) {
        String subject = "Xác nhận huỷ chuyến đi bị từ chối";
        String content = "<h3>Chào " + requestFull.getBookDetail().getFirstName() + " " + requestFull.getBookDetail().getLastName() + ",</h3>"
                + "<p>Chuyến đi của bạn bị huỷ: <strong>" + requestFull.getTour().getTourName() + "</strong></p>"
                + "<p>Lí do: <strong>" + reason + "</strong></p>"
                + "<p>Quý khách vui lòng kiểm tra giao dịch để có thể trải nghiệm dịch vụ tốt nhất</p>"
                + "<p>Bất kì thắc mắc hãy liên hệ qua đường thông tin trong sàn</p>"
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
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
