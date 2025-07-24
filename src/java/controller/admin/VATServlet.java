/*
 * Copyright (C) 2025, Group 6.
 * Project: TravelSystemService 
 * Description: Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-22  1.0        Hà Thị Duyên      First implementation
 * 2025-07-22  1.1        Hà Thị Duyên      Handle new VAT copy on status change
 */
package controller.admin;

import dao.VATDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.VAT;
import java.sql.SQLException;

@WebServlet(name = "VATServlet", urlPatterns = {"/VATServlet"})
public class VATServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String gmail = (String) session.getAttribute("gmail");
        if (gmail == null) {
            response.sendRedirect("LoginLogout?service=loginUser");
            return;
        }

        String service = request.getParameter("service");
        VATDAO vatDAO = new VATDAO();

        if (service == null || service.equals("listVAT")) {
            String sql = "SELECT * FROM VAT";
            try {
                vatDAO.updateExpiredVATs();
                Vector<VAT> vatList = vatDAO.getAllVAT(sql);
                request.setAttribute("vatList", vatList);
                request.setAttribute("today", new java.util.Date());
                request.getRequestDispatcher("/view/admin/listVAT.jsp").forward(request, response);
            } catch (SQLException ex) {
                request.setAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách VAT.");
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            }
        } else if (service.equals("changeVATStatus")) {
            try {
                int vatID = Integer.parseInt(request.getParameter("vatID"));
                VAT vat = vatDAO.getVATByID(vatID);
                if (vat != null) {
                    int newStatus = vat.getStatus() == 1 ? 0 : 1; // Chỉ chuyển đổi giữa 0 và 1
                    vatDAO.changeVATStatus(vatID, newStatus);
                    request.setAttribute("successMessage", "Thay đổi trạng thái VAT thành công!" + (newStatus == 0 ? " Một bản sao VAT mới đã được tạo." : ""));
                    response.sendRedirect(request.getContextPath() + "/VATServlet?service=listVAT");
                } else {
                    request.setAttribute("errorMessage", "Không tìm thấy VAT với ID: " + vatID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                request.setAttribute("errorMessage", "Lỗi khi thay đổi trạng thái VAT: " + ex.getMessage());
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            } catch (NumberFormatException ex) {
                request.setAttribute("errorMessage", "ID VAT không hợp lệ!");
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Mô tả ngắn gọn";
    }
}
