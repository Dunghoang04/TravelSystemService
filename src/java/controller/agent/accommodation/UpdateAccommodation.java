/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.agent.accommodation;

import dao.AccommodationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import model.Accommodation;
import model.Room;
import jakarta.servlet.annotation.MultipartConfig;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name="UpdateAccommodation", urlPatterns={"/UpdateAccommodation"})
public class UpdateAccommodation extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateAccommodation</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateAccommodation at " + request.getContextPath () + "</h1>");
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
       int id = Integer.parseInt(request.getParameter("id"));
        AccommodationDAO accDAO = new AccommodationDAO();
        Accommodation accUpdate = accDAO.getAccommodationByServiceId(id);
        List<Room> rooms;
        try {
            rooms = accDAO.getRoomsByServiceId(id);
            request.setAttribute("accommodationUpdate", accUpdate);
            request.setAttribute("rooms", rooms);
            request.getRequestDispatcher("view/agent/accommodation/updateAccommodation.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateAccommodation.class.getName()).log(Level.SEVERE, null, ex);
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
            String serviceIDStr = request.getParameter("serviceID");
            if (serviceIDStr == null || serviceIDStr.trim().isEmpty()) {
                sendError(request, response, "Mã dịch vụ (serviceID) không hợp lệ.");
                return;
            }
            int id = Integer.parseInt(serviceIDStr);

            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String rateStr = request.getParameter("rate");
            String checkInTimeStr = request.getParameter("checkInTime");
            String checkOutTimeStr = request.getParameter("checkOutTime");
            String description = request.getParameter("description");
            Part filePart = request.getPart("image");

            HttpSession session = request.getSession();
            String imageFileName = (String) session.getAttribute("imageFileName");

            String uploadsDirPath = getServletContext().getRealPath("/") + "uploads";
            java.io.File uploadsDir = new java.io.File(uploadsDirPath);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdirs();
            }

            if (filePart != null && filePart.getSize() > 0) {
                imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String imagePath = "uploads/" + imageFileName;
                filePart.write(uploadsDirPath + java.io.File.separator + imageFileName);
                session.setAttribute("imageFileName", imageFileName);
            }

            AccommodationDAO accdao = new AccommodationDAO();
            Accommodation accommdationUpdate = accdao.getAccommodationByServiceId(id);
            List<Room> rooms = accdao.getRoomsByServiceId(id);
            request.setAttribute("accommodationUpdate", accommdationUpdate);
            request.setAttribute("rooms", rooms);

            if (imageFileName == null || imageFileName.trim().isEmpty()) {
                sendError(request, response, "Vui lòng chọn ảnh cho nơi ở.");
                return;
            }

            if (name == null || name.trim().isEmpty()) {
                sendError(request, response, "Tên nơi ở không được để trống.");
                return;
            }

            if (phone == null || phone.trim().isEmpty()) {
                sendError(request, response, "Số điện thoại không được để trống.");
                return;
            }
            if (phone.length() != 10) {
                sendError(request, response, "Số điện thoại phải có đúng 10 số.");
                return;
            }
            if (!phone.startsWith("0")) {
                sendError(request, response, "Số điện thoại phải bắt đầu bằng số 0.");
                return;
            }
            if (!phone.matches("^(03|05|07|09)[0-9]{8}$")) {
                sendError(request, response, "Số điện thoại phải bắt đầu bằng 03, 05, 07 hoặc 09.");
                return;
            }

            if (type == null || type.trim().isEmpty()) {
                sendError(request, response, "Loại nhà hàng không được bỏ trống.");
                return;
            }

            float rate;
            try {
                rate = Float.parseFloat(rateStr);
                if (rate < 0 || rate > 10) {
                    sendError(request, response, "Điểm đánh giá phải nằm trong khoảng 1-10.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError(request, response, "Điểm đánh giá không hợp lệ.");
                return;
            }

            if (checkInTimeStr.length() == 5) {
                checkInTimeStr += ":00";
            }
            if (checkOutTimeStr.length() == 5) {
                checkOutTimeStr += ":00";
            }

            LocalTime timeOpen, timeClose;
            try {
                timeOpen = LocalTime.parse(checkInTimeStr);
                timeClose = LocalTime.parse(checkOutTimeStr);
            } catch (Exception e) {
                sendError(request, response, "Thời gian không hợp lệ.");
                return;
            }

            if (description == null || description.trim().split("\\s+").length < 10) {
                sendError(request, response, "Vui lòng điền mô tả nơi ở từ 10 từ trở lên.");
                return;
            }

            String imagePath = "uploads/" + imageFileName;
            accdao.updateAccommodation(id, name, imagePath, address, phone, description, rate, type, 1, checkInTimeStr, checkOutTimeStr);

            response.sendRedirect("ManagementAccommodation");

        } catch (Exception e) {
            sendError(request, response, "Lỗi khi cập nhật nơi ở: " + e.getMessage());
        }
    }
    
    private void sendError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorInput", errorMessage);
        request.getRequestDispatcher("view/agent/accommodation/updateAccommodation.jsp").forward(request, response);
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
