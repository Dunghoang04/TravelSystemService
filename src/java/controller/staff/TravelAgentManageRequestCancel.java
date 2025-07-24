/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staff;

import dao.RequestCancelDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.RequestCancelFullDTO;

/**
 *
 * @author Hung
 */
public class TravelAgentManageRequestCancel extends HttpServlet {
   
    

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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("LoginLogout");
            return;
        }

        final int PAGE_SIZE = 10;
        int currentPage = 1;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword").trim() : "";
        String statusParam = request.getParameter("status");
        int status = 0; // 0 = không lọc, 1 = PENDING, 2 = FINISHED, 3=REJECTED

        if ("PENDING".equalsIgnoreCase(statusParam)) {
            status = 1;
        } else if ("FINISHED".equalsIgnoreCase(statusParam)) {
            status = 2;
        }else if("REJECTED".equalsIgnoreCase(statusParam)){
            status = 3;
        }

        try {
            RequestCancelDAO dao = new RequestCancelDAO();
            List<RequestCancelFullDTO> fullList = new ArrayList<>();

            boolean hasKeyword = !keyword.isEmpty();
            boolean hasStatus = status != 0;

            if (hasKeyword && hasStatus) {
                // Ưu tiên tìm kiếm, sau đó lọc theo status
                for (RequestCancelFullDTO dto : dao.searchTravelAgentCancelRequestByEmail(keyword)) {
                    if (dto.getRequestCancel().getStatus() == status) {
                        fullList.add(dto);
                    }
                }
            } else if (hasKeyword) {
                fullList = dao.searchTravelAgentCancelRequestByEmail(keyword);
            } else if (hasStatus) {
                fullList = dao.filterTravelAgentCancelRequestByStatus(status);
            } else {
                fullList = dao.getAllTravelAgentCancelRequests();
            }

            int totalItems = fullList.size();
            int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);

            int start = (currentPage - 1) * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, totalItems);

            List<RequestCancelFullDTO> paginatedList = fullList.subList(start, end);

            request.setAttribute("listRequest", paginatedList);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("keyword", keyword);
            request.setAttribute("status", statusParam);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("view/staff/travelAgentManageRequestCancel.jsp").forward(request, response);

    } 

    

}
