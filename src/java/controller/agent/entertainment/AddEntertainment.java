package controller.agent.entertainment;

import dao.EntertainmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author ad
 */
@MultipartConfig
public class AddEntertainment extends HttpServlet {

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
            out.println("<title>Servlet AddEntertainment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddEntertainment at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("view/agent/entertainment/AddEntertainment.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");  // Đảm bảo đọc tiếng Việt đúng
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Lấy dữ liệu từ form
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String rateStr = request.getParameter("rate");
            String timeOpen = request.getParameter("timeopen");
            String timeClose = request.getParameter("timeclose");
            String dayOfWeekOpen = request.getParameter("dayOfWeekOpen");
            String ticketPriceStr = request.getParameter("ticketPrice");
            String description = request.getParameter("description");

            // Xử lý giá trị số
            float rate = 0;
            if (rateStr != null && !rateStr.isEmpty()) {
                rate = Float.parseFloat(rateStr);
            }

            float ticketPrice = 0;
            if (ticketPriceStr != null && !ticketPriceStr.isEmpty()) {
                ticketPrice = Float.parseFloat(ticketPriceStr);
            }
            if (timeOpen.length() == 5) {
                timeOpen += ":00";
            }
            if (timeClose.length() == 5) {
                timeClose += ":00";
            }

            // Xử lý ảnh tải lên
            Part imagePart = request.getPart("image");
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/") + "uploads" + File.separator + fileName;

            File uploadDir = new File(uploadPath).getParentFile();
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            imagePart.write(uploadPath);
            String imagePath = "uploads/" + fileName; // Dùng đường dẫn này để lưu vào DB

            // Gọi DAO để thêm vào database
            EntertainmentDAO dao = new EntertainmentDAO();
            dao.insertEntertainmentFull(name, imagePath, address, phone, description, rate, type, 1, timeOpen, timeClose, dayOfWeekOpen, ticketPrice);

            // Chuyển hướng hoặc hiển thị thông báo
            response.sendRedirect("managementertainment"); // Ví dụ redirect về danh sách

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
        return "Short description";
    }// </editor-fold>

}
