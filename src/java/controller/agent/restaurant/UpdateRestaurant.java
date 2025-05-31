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
import java.io.File;
import java.nio.file.Paths;
import model.Restaurant;

/**
 *
 * @author ad
 */
@MultipartConfig
public class UpdateRestaurant extends HttpServlet {

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
            out.println("<title>Servlet UpdateRestaurant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRestaurant at " + request.getContextPath() + "</h1>");
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
        int id = Integer.parseInt(request.getParameter("id"));
        RestaurantDAO resDao = new RestaurantDAO();
        Restaurant updateRestaurant = resDao.getRestaurantByServiceId(id);
        request.setAttribute("updateRestaurant", updateRestaurant);

        request.getRequestDispatcher("view/agent/restaurant/UpdateRestaurant.jsp").forward(request, response);

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
        int id=Integer.parseInt(request.getParameter("serviceID"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String phone = request.getParameter("phone");
        int status=Integer.parseInt(request.getParameter("status"));
        String timeOpenStr = request.getParameter("timeopen");
        String timeCloseStr = request.getParameter("timeclose");
        String description = request.getParameter("description");
//        int status = Integer.parseInt(request.getParameter("status"));

        // Format lại thời gian nếu thiếu giây
        if (timeOpenStr.length() == 5) {
            timeOpenStr += ":00";
        }
        if (timeCloseStr.length() == 5) {
            timeCloseStr += ":00";
        }

        // Xử lý file ảnh
        String imagePath = request.getParameter("existingImage"); // Default to existing image
        Part filePart = request.getPart("image");

        if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null) {
            // New file uploaded
            String imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadsDirPath = getServletContext().getRealPath("/") + "uploads";
            java.io.File uploadsDir = new java.io.File(uploadsDirPath);

            if (!uploadsDir.exists()) {
                uploadsDir.mkdirs();
            }

            imagePath = "uploads/" + imageFileName;
            filePart.write(uploadsDirPath + java.io.File.separator + imageFileName);
        }

        RestaurantDAO resDao = new RestaurantDAO();

        resDao.updateRestaurant(id, name, imagePath, address, phone, description, type, status, timeOpenStr, timeCloseStr);

        // Gọi DAO
//        resDao.updateRestaurantTest(10, name);
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
