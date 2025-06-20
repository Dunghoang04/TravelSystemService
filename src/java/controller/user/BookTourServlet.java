/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import dao.TourDAO;
import dao.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Tour;
import model.Voucher;

/**
 *
 * @author Hung
 */
public class BookTourServlet extends HttpServlet {
   
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
            out.println("<title>Servlet BookTourServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookTourServlet at " + request.getContextPath () + "</h1>");
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
        TourDAO tourDao = new TourDAO();
        VoucherDAO voucherDao = new VoucherDAO();
        String id_raw = request.getParameter("tourID");
        try{
            ArrayList<Voucher> voucherlist = voucherDao.getAllVoucher();
            request.setAttribute("voucherlist", voucherlist);
            int id = Integer.parseInt(id_raw);
            Tour tour = tourDao.searchTourByID(id);
            request.setAttribute("tour", tour);
            request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
        }catch(NumberFormatException e){
            e.printStackTrace();
            request.setAttribute("error", "Lỗi nhận diện chuyến du lịch vui lòng thoát ra vào lại!.");
                        request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Lỗi dữ liệu từ server");
                        request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);

        }catch(IOException e){
            e.printStackTrace();
            request.setAttribute("error", "Lỗi dữ liệu IO");
                        request.getRequestDispatcher("view/user/bookTour.jsp").forward(request, response);
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
