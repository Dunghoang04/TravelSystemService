/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.agent;

import dao.TravelAgentDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import model.TravelAgent;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "RegisterTravelAgentServlet", urlPatterns = {"/RegisterTravelAgentServlet"})
public class RegisterTravelAgentServlet extends HttpServlet {

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
            out.println("<title>Servlet RegisterTravelAgentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterTravelAgentServlet at " + request.getContextPath() + "</h1>");
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
    private TravelAgentDAO travelAgentDAO = new TravelAgentDAO();
    private HttpSession session; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khởi tạo session từ request
        session = request.getSession();
        request.getRequestDispatcher("view/agent/Register1.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khởi tạo session từ request
        session = request.getSession();
        String service = request.getParameter("service");

        if ("step1".equals(service)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String result = processStep1(email, password, confirmPassword);

            if (result.equals("success")) {
                response.sendRedirect("view/agent/Register2.jsp");
            } else {
                request.setAttribute("errorMessage", result);
                request.getRequestDispatcher("view/agent/Register1.jsp").forward(request, response);
            }
        } else if ("addAgent".equals(service)) {
            String travelAgentName = request.getParameter("travelAgentName");
            String travelAgentEmail = request.getParameter("travelAgentEmail");
            String hotline = request.getParameter("hotLine");
            String travelAgentAddress = request.getParameter("address");
            String establishmentDateStr = request.getParameter("establishmentDate");
            String taxCode = request.getParameter("taxCode");
            String firstName = request.getParameter("representativeFirstName");
            String lastName = request.getParameter("representativeLastName");
            String phone = request.getParameter("representativePhone");
            String address = request.getParameter("representativeAddress");
            String dobStr = request.getParameter("dob");
            String representativeIDCard = request.getParameter("representativeIDCard");
            String gender = request.getParameter("gender");
            String dateOfIssueStr = request.getParameter("dateOfIssue");
            String result = processStep2(
                    travelAgentName, travelAgentEmail, hotline, travelAgentAddress, establishmentDateStr, taxCode,
                    firstName, lastName, phone, address, dobStr, representativeIDCard, gender, dateOfIssueStr);
            if (result.equals("success")) {
                session.invalidate();
                response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("errorMessage", result);
                request.getRequestDispatcher("view/agent/Register2.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("view/agent/Register1.jsp");
        }
    }

    private String processStep1(String email, String password, String confirmPassword) {
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống!";
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
            return "Chỉ chấp nhận email hợp lệ như @gmail.com, @abc.edu, @company.com...";
        }   

        if (travelAgentDAO.isEmailExists(email)) {
            return "Email đã được sử dụng!";
        }

        if (password == null || password.trim().isEmpty() || confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }

        if (!password.equals(confirmPassword)) {
            return "Mật khẩu không khớp!";
        }

        String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
        if (!password.matches(passwordRegex)) {
            return "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!";
        }

        // Lưu vào HttpSession
        session.setAttribute("email", email);
        session.setAttribute("password", password);
        return "success";
    }
    
    
    private String processStep2(String travelAgentName, String travelAgentEmail, String hotline, String travelAgentAddress,
            String establishmentDateStr, String taxCode, String firstName, String lastName, String phone,
            String address, String dobStr, String representativeIDCard, String gender, String dateOfIssueStr) {
        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");


        if (email == null || email.trim().isEmpty()) {
            return "Email từ session bị thiếu!";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password từ session bị thiếu!";
        }
        if (travelAgentName == null || travelAgentName.trim().isEmpty()) {
            return "Tên công ty không được để trống!";
        }
        if (travelAgentEmail == null || travelAgentEmail.trim().isEmpty()) {
            return "Email công ty không được để trống!";
        }
        if (hotline == null || hotline.trim().isEmpty()) {
            return "Số hotline không được để trống!";
        }
        if (travelAgentAddress == null || travelAgentAddress.trim().isEmpty()) {
            return "Địa chỉ công ty không được để trống!";
        }
        if (establishmentDateStr == null || establishmentDateStr.trim().isEmpty()) {
            return "Ngày thành lập không được để trống!";
        }
        if (taxCode == null || taxCode.trim().isEmpty()) {
            return "Mã số thuế không được để trống!";
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            return "Họ người đại diện không được để trống!";
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return "Tên người đại diện không được để trống!";
        }
        if (phone == null || phone.trim().isEmpty()) {
            return "Số điện thoại người đại diện không được để trống!";
        }
        if (address == null || address.trim().isEmpty()) {
            return "Địa chỉ người đại diện không được để trống!";
        }
        if (dobStr == null || dobStr.trim().isEmpty()) {
            return "Ngày sinh không được để trống!";
        }
        if (representativeIDCard == null || representativeIDCard.trim().isEmpty()) {
            return "Số căn cước công dân không được để trống!";
        }
        if (gender == null || gender.trim().isEmpty()) {
            return "Giới tính không được để trống!";
        }
        
        if (dateOfIssueStr == null || dateOfIssueStr.trim().isEmpty()) {
            return "Ngày cấp căn cước không được để trống!";
        }
        if (!hotline.matches("^0\\d{9}$") || !phone.matches("^0\\d{9}$")) {
            return "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số. ";
        }   
        
                
        if (!travelAgentEmail.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|[a-zA-Z0-9.-]+\\.(com|net|org|vn|edu|edu\\.vn|ac\\.vn))$")) {
            return "Chỉ chấp nhận email hợp lệ như @gmail.com, @abc.edu, @company.com...";
        }        
        
        
        if (!taxCode.matches("\\d{10}|\\d{13}")) {
            return "Mã số thuế phải có 10 hoặc 13 chữ số!";
        }
        if (!representativeIDCard.matches("^0\\d{11}$")) {
            return "Số căn cước công dân phải bắt đầu bằng số 0 và có 12 chữ số!";
        }
        


        Date establishmentDate;
        Date dob;
        Date dateOfIssue;
        try {
            establishmentDate = Date.valueOf(establishmentDateStr);
            dob = Date.valueOf(dobStr);
            dateOfIssue = Date.valueOf(dateOfIssueStr);
        } catch (IllegalArgumentException e) {
            return "Định dạng ngày không hợp lệ!";
        }
        
        Date currentDate = Date.valueOf(LocalDate.now());

        if (establishmentDate.after(currentDate)) {
            return "Ngày thành lập phải trước ngày hiện tại!";
        }
        

        if (dob.after(currentDate)) {
            return "Ngày sinh phải trước ngày hiện tại!";
        }
        if (dateOfIssue.after(currentDate)) {
            return "Ngày cấp căn cước phải trước ngày hiện tại!";
        }


        TravelAgent travelAgent = new TravelAgent(
                travelAgentName,
                travelAgentAddress,
                travelAgentEmail,
                hotline,
                taxCode,
                establishmentDate,
                representativeIDCard,
                dateOfIssue,
                0, // userID (will be generated by the database)
                email,
                4, 
                password,
                firstName,
                lastName,
                dob,
                gender,
                address,
                phone,
                currentDate,
                currentDate,
                2 
        );


        try {
            travelAgentDAO.insertTravelAgent(travelAgent);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Đăng ký thất bại do lỗi hệ thống!";
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
