/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.agent.accommodation.room;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.RoomDAO;
import model.Room;
/**
 *
 * @author Nhat Anh
 */
@WebServlet(name="AddRoom", urlPatterns={"/AddRoom"})
public class AddRoom extends HttpServlet {
   
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
            out.println("<title>Servlet AddRoom</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRoom at " + request.getContextPath () + "</h1>");
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
        int accommodationID = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("accommodationID", accommodationID);
        request.getRequestDispatcher("/view/agent/accommodation/room/addRoom.jsp").forward(request, response);
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
         try {
            int accommodationID = Integer.parseInt(request.getParameter("accommodationID"));
            String roomTypes = request.getParameter("roomTypes");
            int numberOfRooms = Integer.parseInt(request.getParameter("numberOfRooms"));
            float priceOfRoom = Float.parseFloat(request.getParameter("priceOfRoom"));

            Room room = new Room(0, accommodationID, roomTypes, numberOfRooms, priceOfRoom); // roomID sẽ tự tăng
            RoomDAO dao = new RoomDAO();
            dao.addRoom(room);

            response.sendRedirect("ManagementRoom?id=" + accommodationID); // Chuyển hướng với accommodationID
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thêm phòng: " + e.getMessage());
            request.getRequestDispatcher("/view/agent/accommodation/room/addRoom.jsp").forward(request, response);
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
