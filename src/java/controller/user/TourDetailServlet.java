/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai         Refactored with ITourDAO, improved resource management and comments
 */
package controller.user;

import dao.EntertainmentDAO;
import dao.IEntertainmentDAO;
import dao.IRestaurantDAO;
import dao.ITourDAO;
import dao.ITourServiceDetailDAO;
import dao.RestaurantDAO;
import dao.TourDAO;
import dao.TourServiceDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Entertainment;
import model.Restaurant;
import model.Tour;
import model.TourServiceDetail;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "TourDetailServlet", urlPatterns = {"/TourDetailServlet"})
public class TourDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet TourDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TourDetailServlet at " + request.getContextPath() + "</h1>");
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
        ITourDAO tdao = new TourDAO();
        ITourServiceDetailDAO sdao = new TourServiceDetailDAO();
        IRestaurantDAO restaurantDAO = new RestaurantDAO();
        IEntertainmentDAO entertainmentDAO = new EntertainmentDAO();
        List<Entertainment> listOfEntertainment=new ArrayList<>();
        List<Restaurant> listOfRestaurants=new ArrayList<>();
        ITourServiceDetailDAO tourServiceDetailDAO=new TourServiceDetailDAO();
        int id = Integer.parseInt(request.getParameter("tourId"));
        Tour tour;
        try {
            List<TourServiceDetail>tourServiceDetails=tourServiceDetailDAO.getTourServiceDetails(id);
            for (TourServiceDetail detail : tourServiceDetails) {
                int serviceId=detail.getServiceID();
                
                //kiểm tra xem id đó của restaurant hay entertainment
                Restaurant restaurant=restaurantDAO.getRestaurantByServiceId(serviceId);
                if(restaurant!=null){
                    listOfRestaurants.add(restaurant);
                }
                
                Entertainment entertainment=entertainmentDAO.getEntertainmentByServiceId(serviceId);
                if(entertainment!=null){
                    listOfEntertainment.add(entertainment);
                }
            }
            tour = tdao.searchTourByID(id);
            request.setAttribute("tour", tour);
            request.setAttribute("tourId", id);
            Vector<TourServiceDetail> services = sdao.getTourServiceDetails(id);
            request.setAttribute("services", services);
            request.setAttribute("restaurantList", listOfRestaurants);
            request.setAttribute("entertainmentList", listOfEntertainment);
            request.getRequestDispatcher("view/user/tourDetail.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(TourDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
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
