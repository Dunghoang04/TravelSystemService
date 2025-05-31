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
import java.util.ArrayList;
import model.Voucher;

/**
 *
 * @author Hung
 */
public class FilterVoucherByStatus extends HttpServlet {
   
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
            out.println("<title>Servlet FilterVoucherByStatus</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterVoucherByStatus at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("view/Staff/Voucher.jsp").forward(request, response);
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
        String status_raw = request.getParameter("status");
        VoucherDAO voucherDao = new VoucherDAO();
        ArrayList<Voucher> listVoucher = new ArrayList<>();
        try {
            int status = Integer.parseInt(status_raw);
            request.setAttribute("status", status);
            if(status==1){
                listVoucher = voucherDao.getVoucherByStatus(status);
                request.setAttribute("listVoucher", listVoucher);
                request.getRequestDispatcher("view/Staff/Voucher.jsp").forward(request, response);
                return;
            }
            if(status==0){
                listVoucher = voucherDao.getVoucherByStatus(status);
                request.setAttribute("listVoucher", listVoucher);
                request.getRequestDispatcher("view/Staff/Voucher.jsp").forward(request, response);
                return;
            }
            if(status==2){
                listVoucher = voucherDao.getAllVoucher();
                request.setAttribute("listVoucher", listVoucher);
                request.getRequestDispatcher("view/Staff/Voucher.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("view/Staff/Voucher.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
