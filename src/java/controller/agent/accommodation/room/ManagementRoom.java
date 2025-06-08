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
import java.util.List;
import model.Room;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name="ManagementRoom", urlPatterns={"/ManagementRoom"})
public class ManagementRoom extends HttpServlet {
   
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
            out.println("<title>Servlet ManagementRoom</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagementRoom at " + request.getContextPath () + "</h1>");
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
        RoomDAO dao = new RoomDAO();
        List<Room> rooms;

        try {
            String accommodationIDStr = request.getParameter("id"); // Lấy accommodationID từ request

            if (accommodationIDStr != null && !accommodationIDStr.trim().isEmpty()) {
                try {
                    int accommodationID = Integer.parseInt(accommodationIDStr);
                    rooms = dao.getRoomsByAccommodationID(accommodationID); // Lấy danh sách phòng theo accommodationID
                    if (rooms.isEmpty()) {
                        request.setAttribute("error", "Không tìm thấy phòng nào cho accommodationID: " + accommodationID);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Mã accommodationID không hợp lệ.");
                    rooms = dao.getAllRooms();
                }
            } else {
                rooms = dao.getAllRooms(); // Nếu không có accommodationID, hiển thị tất cả phòng
            }

            request.setAttribute("rooms", rooms);
            request.setAttribute("accommodationID", accommodationIDStr); // Truyền accommodationID để JSP sử dụng

        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi lấy danh sách phòng: " + e.getMessage());
            request.setAttribute("rooms", List.of());
        }

        request.getRequestDispatcher("/view/agent/accommodation/room/agentRoom.jsp").forward(request, response);
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
