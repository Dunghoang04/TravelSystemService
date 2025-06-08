/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.agent.accommodation.room;

import dao.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name="UpdateRoom", urlPatterns={"/UpdateRoom"})
public class UpdateRoom extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateRoom</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRoom at " + request.getContextPath () + "</h1>");
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
        int roomID = Integer.parseInt(request.getParameter("id")); // Sử dụng roomID thay vì accommodationID
        RoomDAO dao = new RoomDAO();
        Room room;
        try {
            room = dao.getRoomByRoomID(roomID); // Lấy phòng cụ thể bằng roomID
            if (room == null) {
                request.setAttribute("error", "Phòng không tồn tại.");
                response.sendRedirect("ManagementRoom");
                return;
            }
            request.setAttribute("room", room);
            request.getRequestDispatcher("/view/agent/accommodation/room/updateRoom.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateRoom.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            int roomID = Integer.parseInt(request.getParameter("roomID"));
            int accommodationID = Integer.parseInt(request.getParameter("accommodationID"));
            String roomTypes = request.getParameter("roomTypes");
            int numberOfRooms = Integer.parseInt(request.getParameter("numberOfRooms"));
            float priceOfRoom = Float.parseFloat(request.getParameter("priceOfRoom"));

            RoomDAO dao = new RoomDAO();
            // Cập nhật phòng dựa trên roomID, nhưng cần truyền accommodationID để kiểm tra
            dao.updateRoom(accommodationID, roomTypes, numberOfRooms, priceOfRoom); // Lưu ý: updateRoom hiện tại không sử dụng roomID, cần điều chỉnh DAO nếu cần

            response.sendRedirect("ManagementRoom");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi cập nhật phòng: " + e.getMessage());
            request.getRequestDispatcher("/view/agent/accommodation/room/updateRoom.jsp").forward(request, response);
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
