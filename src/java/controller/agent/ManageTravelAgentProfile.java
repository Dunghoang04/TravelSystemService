/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.agent;

import dao.TravelAgentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.TravelAgent;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "ManageTravelAgentProfile", urlPatterns = {"/ManageTravelAgentProfile"})
public class ManageTravelAgentProfile extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String gmail = (String) session.getAttribute("gmail");
        if (gmail == null) {
            response.sendRedirect("LoginLogout?service=loginUser");
            return;
        }

        TravelAgentDAO agentDAO = new TravelAgentDAO();
        String service = request.getParameter("service");

        if (service == null || service.equals("viewProfile")) {
            TravelAgent travelAgent = agentDAO.searchByTravelAgentGmail(gmail);
            session.setAttribute("agent", travelAgent);
            request.getRequestDispatcher("view/agent/AgentProfile.jsp").forward(request, response);
        } else if (service.equals("updateProfile")) {
            TravelAgent travelAgent = agentDAO.searchByTravelAgentGmail(gmail);
            session.setAttribute("agent", travelAgent);
            request.getRequestDispatcher("view/agent/EditAgentProfile.jsp").forward(request, response);
        } else if (service.equals("save")) {
            String travelAgentEmail = request.getParameter("travelAgentEmail");
            String hotLine = request.getParameter("hotLine");
            String address = request.getParameter("address");
            String representativeFirstName = request.getParameter("representativeFirstName");
            String representativeLastName = request.getParameter("representativeLastName");
            String representativePhone = request.getParameter("representativePhone");
            String representativeAddress = request.getParameter("representativeAddress");
            String dobStr = request.getParameter("dob");
            String gender = request.getParameter("gender");
            String dateOfIssueStr = request.getParameter("dateOfIssue");
            String password = request.getParameter("password");

            String errorMessage = validateInput(travelAgentEmail, hotLine, address, representativeFirstName, representativeLastName, representativePhone,
                    representativeAddress, dobStr, gender, password, dateOfIssueStr);
            if (errorMessage != null) {
                request.setAttribute("error", errorMessage);
                request.getRequestDispatcher("view/agent/EditAgentProfile.jsp").forward(request, response);
                return;
            }
            Date dateOfIssue = Date.valueOf(dateOfIssueStr);
            Date dob = Date.valueOf(dobStr);
            TravelAgent travelAgent = agentDAO.searchByTravelAgentGmail(gmail);
            if (travelAgent == null) {
                request.setAttribute("error", "không tìm thấy đại lý");
                request.getRequestDispatcher("view/agent/EditAgentProfile.jsp").forward(request, response);
                return;
            }

            // Cập nhật thông tin
            travelAgent.setTravelAgentGmail(travelAgentEmail);
            travelAgent.setHotLine(hotLine);
            travelAgent.setTravelAgentAddress(address);
            travelAgent.setFirstName(representativeFirstName);
            travelAgent.setLastName(representativeLastName);
            travelAgent.setPhone(representativePhone);
            travelAgent.setAddress(representativeAddress);
            travelAgent.setDob(dob);
            travelAgent.setGender(gender);
            travelAgent.setPassword(password);

            agentDAO.updateTravelAgent(travelAgent);

            session.setAttribute("agent", travelAgent);
            request.setAttribute("success", "Profile updated successfully!");

            request.getRequestDispatcher("view/agent/EditAgentProfile.jsp").forward(request, response);
        }
    }

    private String validateInput(String travelAgentEmail, String hotLine, String address, String representativeFirstName, String representativeLastName,
             String representativePhone, String representativeAddress, String dobStr, String gender, String password, String dateOfIssueStr) {
        // Kiểm tra các trường không được để trống
        if (travelAgentEmail == null || travelAgentEmail.trim().isEmpty()
                || hotLine == null || hotLine.trim().isEmpty()
                || address == null || address.trim().isEmpty()
                || representativeFirstName == null || representativeFirstName.trim().isEmpty()
                || representativeLastName == null || representativeLastName.trim().isEmpty()
                || representativePhone == null || representativePhone.trim().isEmpty()
                || representativeAddress == null || representativeAddress.trim().isEmpty()
                || dobStr == null || dobStr.trim().isEmpty()
                || gender == null || gender.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || dateOfIssueStr == null || dateOfIssueStr.trim().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin!";
        }
        if (!representativeLastName.matches("^[\\p{L} ]{2,50}$") || !representativeFirstName.matches("^[\\p{L} ]{2,50}$")) {
            return "Họ và tên chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!";
        }
        
        if (representativeAddress.length() < 5 || representativeAddress.length() > 200) {
            return "Địa chỉ phải từ 5 đến 200 ký tự!";
        }

        if (!hotLine.matches("^0\\d{9}$") || !representativePhone.matches("^0\\d{9}$")) {
            return "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số. ";
        }
        String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
        if (!password.matches(passwordRegex)) {
            return "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!";
        }
        
        
        if (travelAgentEmail == null || !travelAgentEmail.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
            return "Chỉ chấp nhận email hợp lệ như @gmail.com, @abc.edu, @company.com...";
        }       
        

        try {
            Date dob = Date.valueOf(dobStr);
            
            Date currentDate = Date.valueOf(LocalDate.now());
            if (dob.after(currentDate)) {
                return "Ngày sinh phải trước ngày hiện tại!";
            }
        } catch (Exception e) {
            return "Ngày sinh không hợp lệ";
        }
        
        try {
            Date dateOfIssue = Date.valueOf(dateOfIssueStr);
            Date currentDate = Date.valueOf(LocalDate.now());
            if (dateOfIssue.after(currentDate)) {
                return "Ngày cấp phải trước ngày hiện tại!";
            }
        } catch (Exception e) {
            return "Ngày cấp không hợp lệ";
        }
        
        return null;
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
