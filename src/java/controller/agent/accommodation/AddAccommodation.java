/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.agent.accommodation;

import dao.AccommodationDAO;
import dao.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Room;
import jakarta.servlet.annotation.MultipartConfig;


@MultipartConfig
@WebServlet(name = "AddAccommodation", urlPatterns = {"/AddAccommodation"})
public class AddAccommodation extends HttpServlet {

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
            out.println("<title>Servlet AddAccommodation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddAccommodation at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String rateStr = request.getParameter("rate");
            String checkInTime = request.getParameter("checkInTime");
            String checkOutTime = request.getParameter("checkOutTime");
            String description = request.getParameter("description");
            Part imagePart = request.getPart("image");

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

            if (checkInTime.length() == 5) {
                checkInTime += ":00";
            }
            if (checkOutTime.length() == 5) {
                checkOutTime += ":00";
            }

            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/") + "uploads" + File.separator + fileName;

            File uploadDir = new File(uploadPath).getParentFile();
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            imagePart.write(uploadPath);
            String imagePath = "uploads/" + fileName;

            AccommodationDAO accDAO = new AccommodationDAO();
            accDAO.insertAccommodation(name, imagePath, address, phone, description, rate, type, 1, checkInTime, checkOutTime);

            // Thêm các phòng (giả sử có nhiều phòng được gửi qua form với tiền tố "room_")
            RoomDAO roomDAO = new RoomDAO();
            List<Room> rooms = new ArrayList<>();
            int roomCount = 0;
            while (request.getParameter("roomTypes_" + roomCount) != null) {
                String roomTypes = request.getParameter("roomTypes_" + roomCount);
                String numberOfRoomsStr = request.getParameter("numberOfRooms_" + roomCount);
                String priceOfRoomStr = request.getParameter("priceOfRoom_" + roomCount);

                if (roomTypes != null && !roomTypes.trim().isEmpty()
                        && numberOfRoomsStr != null && !numberOfRoomsStr.trim().isEmpty()
                        && priceOfRoomStr != null && !priceOfRoomStr.trim().isEmpty()) {
                    int numberOfRooms = Integer.parseInt(numberOfRoomsStr);
                    float priceOfRoom = Float.parseFloat(priceOfRoomStr);
                    Room room = new Room(0, accDAO.getAccommodationByServiceId(1).getServiceID(), roomTypes, numberOfRooms, priceOfRoom); // Lấy serviceID tạm thời, cần điều chỉnh logic
                    rooms.add(room);
                }
                roomCount++;
            }

            for (Room room : rooms) {
                roomDAO.addRoom(room);
            }

            response.sendRedirect("ManagementAccommodation");

        } catch (Exception e) {
            e.printStackTrace();
            sendError(request, response, "Lỗi khi thêm nơi ở: " + e.getMessage());
        }
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorInput", errorMessage);
        request.getRequestDispatcher("view/agent/accommodation/addAccommodation.jsp").forward(request, response);
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
