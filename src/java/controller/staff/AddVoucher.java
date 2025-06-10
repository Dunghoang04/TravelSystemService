/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hung              First implementation
 */
package controller.staff;

import dao.VoucherDAO;
import jakarta.mail.Session;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This servlet handles both GET and POST requests for adding a new voucher. On
 * GET: forwards to the add voucher form (addVoucher.jsp) On POST: receives
 * input, validates, and inserts a new voucher into the system if the input is
 * valid and voucher code is unique.
 *
 * URL Mapping: /AddVoucher JSP View: view/staff/addVoucher.jsp
 *
 * @author Hung
 */
public class AddVoucher extends HttpServlet {

    /**
     * This method is auto-generated and used mainly for testing basic servlet
     * rendering. Not used in production logic.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddVoucher</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddVoucher at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles HTTP GET request to display the add voucher form.
     *
     * @param request HTTP request
     * @param response HTTP response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to add voucher page
        request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST request to add a new voucher.
     *
     * @param request HTTP request containing form inputs
     * @param response HTTP response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Kiểm tra session
        HttpSession session = request.getSession(false); // false: không tạo session mới nếu chưa có
            if (session == null || session.getAttribute("gmail") == null) {
            // Chưa đăng nhập
            response.sendRedirect("LoginLogout");
            return;
        }
        
        // Create DAO instance to interact with database
        VoucherDAO voucherDao = new VoucherDAO();

        // Get form parameters from request
        String voucherCode = request.getParameter("voucherCode").trim();
        String voucherName = request.getParameter("voucherName").trim();
        String description = request.getParameter("description").trim();
        String percentDiscount_raw = request.getParameter("percentDiscount").trim();
        String maxDiscountAmount_raw = request.getParameter("maxDiscountAmount").trim();
        String minAmountApply_raw = request.getParameter("minAmountApply").trim();
        String startDate_raw = request.getParameter("startDate").trim();
        String endDate_raw = request.getParameter("endDate").trim();
        String quantity_raw = request.getParameter("quantity").trim();
        String status_raw = request.getParameter("status").trim();

        try {
            // ================= VALIDATION ===================

            // Validate voucher code: 5–25 uppercase letters/digits
            String voucherCode_regex = "^[A-Z0-9]{5,25}$";
            if (!voucherCode.matches(voucherCode_regex)) {
                request.setAttribute("voucherCodeErro", "Mã thẻ phải từ 5-25 kí tự và luôn viết hoa");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Validate voucher name: 5–50 characters
            if (voucherName.length() < 5 || voucherName.length() > 50) {
                request.setAttribute("voucherNameErro", "Tên voucher phải nằm trong 5-50 kí tự");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Validate description: at least 10 characters
            if (description.length() < 10) {
                request.setAttribute("description", "Mô tả phải từ 10 kí tự đổ lên");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Parse and validate percent discount (1%–100%)
            float percentDiscount = Float.parseFloat(percentDiscount_raw);
            if (percentDiscount < 1 || percentDiscount > 100) {
                request.setAttribute("percenDiscountErro", "Giảm giá không thể âm hoặc lớn hơn 100");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Parse and validate max discount amount (0–100,000,000)
            float maxDiscountAmount = Float.parseFloat(maxDiscountAmount_raw);
            if (maxDiscountAmount < 0 || maxDiscountAmount > 100000000) {
                request.setAttribute("maxDiscountAmountErro", "Giảm giá tối thiểu không thể âm và nhỏ hơn 10.000.000");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Parse and validate min amount apply (non-negative)
            float minAmountApply = Float.parseFloat(minAmountApply_raw);
            if (minAmountApply < 0 || minAmountApply > 1000000000) {
                request.setAttribute("minAmountApplyErro", "Giá tiền tối thiểu để áp dụng không thể âm và dưới 1 tỷ");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Convert string to Date objects
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            Date dateNow = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date startDate = sdf.parse(startDate_raw);

            // Validate start date is today or in the future
            if (startDate.before(dateNow)) {
                request.setAttribute("startDateErro", "Ngày bắt đầu không được ở quá khứ");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Validate end date must be after start date
            Date endDate = sdf.parse(endDate_raw);
            if (endDate.before(startDate)) {
                request.setAttribute("endDateErro", "Ngày kết thúc phải sau ngày bắt đầu");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Parse and validate quantity (1–100)
            int quantity = Integer.parseInt(quantity_raw);
            if (quantity < 1 || quantity > 100) {
                request.setAttribute("quantityErro", "Số lượng không thể âm và lớn hơn 100");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
                return;
            }

            // Parse status (0 = inactive, 1 = active)
            int status = Integer.parseInt(status_raw);

            // ================= INSERT INTO DATABASE ===================
            // Attempt to insert voucher into database
            if (voucherDao.addVoucher(voucherCode, voucherName, description, percentDiscount,
                    maxDiscountAmount, minAmountApply, startDate, endDate, quantity, status)) {
                // Redirect to list if success
                response.sendRedirect("listvoucher?success=2");
                return;
            } else {
                // Voucher code exists or insert failed
                request.setAttribute("errorMsg", "Mã thẻ tồn tại!");
                request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            // Handle invalid number inputs (e.g., percent/amount/quantity)
            request.setAttribute("errorMsg", "Nhập không khả dụng, vui lòng nhập lại");
            request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);

        } catch (ParseException ex) {
            // Handle invalid date format
            request.setAttribute("errorMsg", "Thêm thẻ khuyến mãi thất bại");
            request.getRequestDispatcher("view/staff/addVoucher.jsp").forward(request, response);

        } catch (SQLException ex) {
            // Handle database exception
            Logger.getLogger(AddVoucher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet
     *
     * @return String
     */
    @Override
    public String getServletInfo() {
        return "Servlet for adding new voucher by staff";
    }// </editor-fold>

}
