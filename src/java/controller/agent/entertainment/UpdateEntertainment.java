package controller.agent.entertainment;

import dao.EntertainmentDAO;
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
import model.Entertainment;
import model.Restaurant;

/**
 *
 * @author ad
 */
@MultipartConfig
public class UpdateEntertainment extends HttpServlet {

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
            out.println("<title>Servlet UpdateEntertainment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateEntertainment at " + request.getContextPath() + "</h1>");
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
        EntertainmentDAO entDAO = new EntertainmentDAO();
        Entertainment entertainmentUpdate = entDAO.getEntertainmentByServiceId(id);
        request.setAttribute("entertainmentUpdate", entertainmentUpdate);
        request.getRequestDispatcher("view/agent/entertainment/UpdateEntertainment.jsp").forward(request, response);
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
        try {
            // Retrieve form parameters
            int serviceID = Integer.parseInt(request.getParameter("serviceID"));
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String rateStr = request.getParameter("rate");
            String timeOpenStr = request.getParameter("timeopen");
            String timeCloseStr = request.getParameter("timeclose");
            String dayOfWeekOpen = request.getParameter("dayOfWeekOpen");
            String ticketPriceStr = request.getParameter("ticketPrice");
            String description = request.getParameter("description");
            int status = Integer.parseInt(request.getParameter("status"));

            // Parse rate and ticketPrice, handling null or empty values
            float rate = rateStr != null && !rateStr.isEmpty() ? Float.parseFloat(rateStr) : 0.0f;
            double ticketPrice = ticketPriceStr != null && !ticketPriceStr.isEmpty() ? Double.parseDouble(ticketPriceStr) : 0.0;

            // Format time fields if seconds are missing (HH:mm -> HH:mm:ss)
            if (timeOpenStr != null && timeOpenStr.length() == 5) {
                timeOpenStr += ":00";
            }
            if (timeCloseStr != null && timeCloseStr.length() == 5) {
                timeCloseStr += ":00";
            }

            // Handle image upload
            String imagePath = request.getParameter("existingImage"); // Default to existing image
            if (imagePath == null) {
                imagePath = "";
            }

            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
                // New file uploaded
                String imageFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadsDirPath = getServletContext().getRealPath("/") + "Uploads";
                File uploadsDir = new File(uploadsDirPath);

                if (!uploadsDir.exists()) {
                    uploadsDir.mkdirs();
                }

                imagePath = "Uploads/" + imageFileName;
                filePart.write(uploadsDirPath + File.separator + imageFileName);
            }

            // Call DAO to update entertainment service
            EntertainmentDAO entertainmentDAO = new EntertainmentDAO();
            entertainmentDAO.updateEntertainment(serviceID, name, imagePath, address, phone, description, rate, type, status, timeOpenStr, timeCloseStr, dayOfWeekOpen, ticketPrice);
            // Redirect to management page
            response.sendRedirect("managementertainment");
        } catch (Exception e) {
            e.printStackTrace();
            // Forward to error page or handle error
            request.setAttribute("errorMessage", "Error updating entertainment service: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
