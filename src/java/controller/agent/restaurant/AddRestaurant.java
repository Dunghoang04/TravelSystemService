package controller.agent.restaurant;

import dao.RestaurantDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;

/**
 *
 * @author ad
 */
@MultipartConfig
public class AddRestaurant extends HttpServlet {

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
            out.println("<title>Servlet AddRestaurant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRestaurant at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("view/agent/restaurant/AddRestaurant.jsp").forward(request, response);
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
    String name = request.getParameter("name");
    String address = request.getParameter("address");
    String type = request.getParameter("type");
    String timeOpenStr = request.getParameter("timeopen");  // "09:49"
    String timeCloseStr = request.getParameter("timeclose");
    String description = request.getParameter("description");
    String phone=request.getParameter("phone");
    float rate=Float.parseFloat(request.getParameter("rate"));
    // Format lại thời gian nếu thiếu giây
    if (timeOpenStr.length() == 5) {
        timeOpenStr += ":00";
    }
    if (timeCloseStr.length() == 5) {
        timeCloseStr += ":00";
    }

    // Xử lý file ảnh
    Part filePart = request.getPart("image");
    String imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

    // Đường dẫn thư mục uploads
    String uploadsDirPath = getServletContext().getRealPath("/") + "uploads";
    java.io.File uploadsDir = new java.io.File(uploadsDirPath);

    if (!uploadsDir.exists()) {
        uploadsDir.mkdirs();  // <- dòng này là phần bạn cần thêm
    }

    String imagePath = "uploads/" + imageFileName;
    filePart.write(uploadsDirPath + java.io.File.separator + imageFileName);

    // Gọi DAO
    RestaurantDAO resDao = new RestaurantDAO();
    resDao.insertRestaurantFull(name, imagePath, address, phone, description, rate, type, 1, timeOpenStr, timeCloseStr);

    response.sendRedirect("managerestaurant");
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
