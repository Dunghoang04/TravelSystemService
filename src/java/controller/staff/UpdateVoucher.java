/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR     DESCRIPTION
 * 2025-06-07  1.0        Hưng       First implementation
 */
package controller.staff;

import dao.UserDAO;
import dao.VoucherDAO;
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
import model.User;
import model.Voucher;

/**
 * This servlet handles the logic for updating an existing voucher. It supports
 * both GET (display voucher info to edit) and POST (submit updated info).
 *
 * @author Hung
 */
public class UpdateVoucher extends HttpServlet {

    /**
     * This method is auto-generated and not used in this servlet. It can be
     * ignored or deleted if unnecessary.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // HTML output placeholder (not used in production)
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateVoucher</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateVoucher at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP GET method to show the voucher edit form.
     *
     * @param request the servlet request
     * @param response the servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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

        String id_raw = request.getParameter("voucherId"); // Lấy voucher ID từ URL

        try {
            int id = Integer.parseInt(id_raw); // Ép kiểu ID sang int
            VoucherDAO voucherDao = new VoucherDAO();
            Voucher voucher = voucherDao.getVoucherById(id); // Lấy thông tin voucher theo ID
            if (voucher == null) {
                request.setAttribute("erro", "Voucher không tồn tại!");
            } else {
                request.setAttribute("voucher", voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Chuyển đến trang chỉnh sửa voucher
        request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method to process voucher update form submission.
     *
     * @param request the servlet request
     * @param response the servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VoucherDAO voucherDao = new VoucherDAO();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("LoginLogout");
            return;
        }

        // Lấy và làm sạch dữ liệu từ form
        String id_raw = request.getParameter("voucherId").trim();
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
        Voucher voucher = null;
        try {
            int id = Integer.parseInt(id_raw); // ID của voucher cần cập nhật
            voucher = voucherDao.getVoucherById(id);
            request.setAttribute("voucher", voucher); // Đặt lại dữ liệu nếu có lỗi

            int percentDiscount = Integer.parseInt(percentDiscount_raw);
            if (voucherName.length() < 5 || voucherName.length() > 50) {
                request.setAttribute("voucherNameErro", "Tên voucher phải nằm trong 5-50 kí tự");
                setVoucher(request);
                request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
                return;
            }

            if (description.length() < 10) {
                request.setAttribute("description", "Mô tả phải từ 10 kí tự đổ lên");
                setVoucher(request);
                request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
                return;
            }

            if (percentDiscount < 1 || percentDiscount > 100) {
                request.setAttribute("percenDiscountErro", "Giảm giá không thể âm hoặc lớn hơn 100");
                setVoucher(request);
                request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
                return;
            }

            int maxDiscountAmount = (int)Integer.parseInt(maxDiscountAmount_raw);
            if (maxDiscountAmount < 0 || maxDiscountAmount > 100000000) {
                request.setAttribute("maxDiscountAmountErro", "Giảm tối đa không thể âm và lớn hơn 100.000.000");
                setVoucher(request);
                request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
                return;
            }

            int minAmountApply =(int) Integer.parseInt(minAmountApply_raw);
            if (minAmountApply < 0 || minAmountApply > 1000000000) {
                request.setAttribute("minAmountApplyErro", "Giá tiền tối thiểu không thể âm và lớn hơn 1 tỷ đồng");
                setVoucher(request);
                request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
                return;
            }

            // Kiểm tra ngày bắt đầu và kết thúc
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            Date dateNow = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date startDate = sdf.parse(startDate_raw);
            if (startDate.before(dateNow)) {
                request.setAttribute("startDateErro", "Ngày bắt đầu không được ở quá khứ");
                setVoucher(request);
                request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
                return;
            }

            Date endDate = sdf.parse(endDate_raw);
            if (endDate.before(startDate)) {
                request.setAttribute("endDateErro", "Ngày kết thúc phải sau ngày bắt đầu");
                setVoucher(request);
                request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
                return;
            }

            int quantity = Integer.parseInt(quantity_raw);
            if (quantity < 1 || quantity > 100) {
                request.setAttribute("quantityErro", "Số lượng không hợp lệ (1-100)");
                setVoucher(request);
                request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
                return;
            }

            int status = Integer.parseInt(status_raw); // Trạng thái (1 = active, 0 = deactive)

            // Cập nhật voucher nếu hợp lệ
            if (voucherDao.updateVoucher(id, loginUser.getUserID(), voucherName, description, percentDiscount,
                    maxDiscountAmount, minAmountApply, startDate, endDate, quantity, status)) {
                response.sendRedirect("listvoucher?success=1");
                return;
            }

        } catch (NumberFormatException e) {
            request.setAttribute("messErro", "Nhập không khả dụng, vui lòng nhập lại");     
            setVoucher(request);
            request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
        } catch (ParseException ex) {
            request.setAttribute("messErro", "Cập nhập thẻ khuyến mãi thất bại");
            setVoucher(request);
            request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("messErro", "Lỗi hệ thống khi cập nhật thẻ khuyến mãi. Vui lòng thử lại sau.");
            setVoucher(request);
            request.getRequestDispatcher("view/staff/updateVoucher.jsp").forward(request, response);
            Logger.getLogger(UpdateVoucher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setVoucher(HttpServletRequest request){
        String id_raw = request.getParameter("voucherId").trim();
        VoucherDAO voucherDao = new VoucherDAO();
        try {
            int id = Integer.parseInt(id_raw); // ID của voucher cần cập nhật
            Voucher voucher = voucherDao.getVoucherById(id);
            request.setAttribute("voucher", voucher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for updating voucher information";
    }

}
