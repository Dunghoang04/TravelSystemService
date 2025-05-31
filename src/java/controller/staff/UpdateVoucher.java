/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dao.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Voucher;

/**
 *
 * @author Hung
 */
public class UpdateVoucher extends HttpServlet {

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
            out.println("<title>Servlet UpdateVoucher</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateVoucher at " + request.getContextPath() + "</h1>");
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
        String id_raw = request.getParameter("voucherId");
        try {
            int id = Integer.parseInt(id_raw);
            VoucherDAO voucherDao = new VoucherDAO();
            Voucher voucher = voucherDao.getVoucherById(id);
            if (voucher == null) {
                request.setAttribute("erro", "Voucher không tồn tại!");
            } else {
                request.setAttribute("voucher", voucher);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("view/Staff/UpdateVoucher.jsp").forward(request, response);
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
        VoucherDAO voucherDao = new VoucherDAO();
        String id_raw = request.getParameter("voucherId");
        String voucherCode = request.getParameter("voucherCode");
        String voucherName = request.getParameter("voucherName");
        String description = request.getParameter("description");
        String percentDiscount_raw = request.getParameter("percentDiscount");
        String maxDiscountAmount_raw = request.getParameter("maxDiscountAmount");
        String minAmountApply_raw = request.getParameter("minAmountApply");
        String startDate_raw = request.getParameter("startDate");
        String endDate_raw = request.getParameter("endDate");
        String quantity_raw = request.getParameter("quantity");
        String status_raw = request.getParameter("status");

        try {
            int id = Integer.parseInt(id_raw);
            Voucher voucher = voucherDao.getVoucherById(id);
            request.setAttribute("voucher", voucher);
            float percentDiscount = Float.parseFloat(percentDiscount_raw);
            if(percentDiscount<1 || percentDiscount>100){
                request.setAttribute("percenDiscountErro", "Giảm giá không thể âm hoặc lớn hơn 100");
                request.getRequestDispatcher("view/Staff/UpdateVoucher.jsp").forward(request, response);
                return;
            }
            
            float maxDiscountAmount = Float.parseFloat(maxDiscountAmount_raw);
            if(maxDiscountAmount<0 || maxDiscountAmount >10000000){
                request.setAttribute("maxDiscountAmountErro", "Giảm giá tối thiểu không thể âm và nhỏ hơn 10.000.000");
                request.getRequestDispatcher("view/Staff/UpdateVoucher.jsp").forward(request, response);
                return;
            }
            
            float minAmountApply = Float.parseFloat(minAmountApply_raw);
            if(minAmountApply <0){
                request.setAttribute("minAmountApplyErro", "Giá tiền tối thiểu để áp dụng không thể âm");
                request.getRequestDispatcher("view/Staff/UpdateVoucher.jsp").forward(request, response);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            Date dateNow = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date startDate = sdf.parse(startDate_raw);
            if (startDate.before(dateNow)) {
                request.setAttribute("startDateErro", "Ngày bắt đầu không được ở quá khứ");
                request.getRequestDispatcher("view/Staff/UpdateVoucher.jsp").forward(request, response);
                return;
            }

            Date endDate = sdf.parse(endDate_raw);
            if (endDate.before(startDate)) {
                request.setAttribute("endDateErro", "Ngày kết thúc phải sau ngày bắt đầu");
                request.getRequestDispatcher("view/Staff/UpdateVoucher.jsp").forward(request, response);
                return;
            }
            int quantity = Integer.parseInt(quantity_raw);
            if (quantity < 1 || quantity >100) {
                request.setAttribute("quantityErro", "Số lượng không thể âm và lớn hơn 100");
                request.getRequestDispatcher("view/Staff/UpdateVoucher.jsp").forward(request, response);
                return;
            }
            int status = Integer.parseInt(status_raw);

            if (voucherDao.updateVoucher(id, voucherName, description, percentDiscount, maxDiscountAmount, minAmountApply, startDate, endDate, quantity, status)) {
                request.setAttribute("success", "Cập nhập thành công");
                
            }
            response.sendRedirect("listvoucher");
        } catch (NumberFormatException e) {
            request.setAttribute("messErro", "Nhập không khả dụng, vui lòng nhập lại");
            request.getRequestDispatcher("view/Staff/UpdateVoucher.jsp").forward(request, response);
            return;
        } catch (ParseException ex) {
            Logger.getLogger(UpdateVoucher.class.getName()).log(Level.SEVERE, null, ex);
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
