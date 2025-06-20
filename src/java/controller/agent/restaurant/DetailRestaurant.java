package controller.agent.restaurant;

import dao.IRestaurantDAO;
import dao.RestaurantDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.Restaurant;

/**
 *
 * @author ad
 */
public class DetailRestaurant extends HttpServlet {

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
            out.println("<title>Servlet DetailRestaurant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailRestaurant at " + request.getContextPath() + "</h1>");
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
        IRestaurantDAO resDao = new RestaurantDAO();
        try {
            String idParam =request.getParameter("id");
            if(idParam==null || idParam.trim().isEmpty()){
                throw new IllegalArgumentException("Tham số mã dịch vụ thiếu hoặc trống");
            }
            int id=Integer.parseInt(idParam);
            Restaurant restaurantDetail = resDao.getRestaurantByServiceId(id);
            if(restaurantDetail==null){
                throw new IllegalArgumentException("Không nhà hàng có mã = "+id);
            }
            request.setAttribute("restaurantDetail", restaurantDetail);
            request.getRequestDispatcher("view/agent/restaurant/detailRestaurant.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Định dạng ID không hợp lệ");
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response); // Assume error page
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response); // Assume error page
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("view/common/error.jsp").forward(request, response); // Assume error page
        }
        
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
        processRequest(request, response);
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
