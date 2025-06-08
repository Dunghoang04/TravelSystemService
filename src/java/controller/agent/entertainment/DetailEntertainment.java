/**
 * Displays detailed information for a specific entertainment record in the agent module.
 * Handles GET requests to retrieve and display entertainment details by service ID, and delegates POST requests.
 * 
 * Project: TravelAgentService
 * Version: 1.0
 * Date: 2025-06-07
 * Bugs: No known issues.
 * 
 * Record of Change:
 * DATE            Version             AUTHOR                               DESCRIPTION
 * 2025-06-07      1.0                 Hoang Tuan Dung                      First implementation
 * 
 * @author ad
 */
package controller.agent.entertainment;

import dao.EntertainmentDAO;
import dao.IEntertainmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.Entertainment;

public class DetailEntertainment extends HttpServlet {
   
    /**
     * Processes requests for both HTTP GET and POST methods with a sample HTML response.
     * Intended for testing purposes only and should be overridden by specific handlers.
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
            out.println("<title>Servlet DetailEntertainment</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailEntertainment at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP GET method to display details of a specific entertainment record.
     * Retrieves entertainment data by service ID and forwards to the detail JSP page.
     *
     * @param request servlet request containing the service ID parameter
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        IEntertainmentDAO entertainmentDAO=new EntertainmentDAO();
        try {
            String idParam=request.getParameter("id");
            if(idParam==null || idParam.trim().isEmpty()){
                throw new IllegalArgumentException("ID parameter is missing or empty");
            }
            int id=Integer.parseInt(idParam);
            Entertainment entertainmentDetail=entertainmentDAO.getEntertainmentByServiceId(id);
            if(entertainmentDetail==null){
                throw new IllegalArgumentException("No entertainment with id = "+id);
            }
            request.setAttribute("entertainmentDetail", entertainmentDetail);
            request.getRequestDispatcher("view/agent/entertainment/detailEntertainment.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID format");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response); // Assume error page
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response); // Assume error page
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response); // Assume error page
        }
        
    } 

   /**
     * Handles the HTTP POST method by delegating to the processRequest method.
     * Intended for future extension to handle form submissions if needed.
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
     * Returns a short description of the DetailEntertainment servlet.<br>
     * @return a String containing servlet description<br>
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
