/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.sql.Date;
import model.User;

/**
 *
 * @author Nhat Anh
 */
@WebServlet(name = "ProfileUser", urlPatterns = {"/ProfileUser"})
public class ProfileUser extends HttpServlet {

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
        HttpSession session = request.getSession();
        UserDAO udao = new UserDAO();
        String service = request.getParameter("service");

        //Nếu không có service, chuyển đến trang profile
        if (service == null) {
            request.getRequestDispatcher("view/user/profileUser.jsp").forward(request, response);
            return;
        }

        //Xử lí thông tin người dùng 
        if (service.equals("updateUser")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
            String gender = request.getParameter("gender");
            String dobStr = request.getParameter("dob");
            String address = request.getParameter("address");
            String gmail = request.getParameter("gmail");
            String phone = request.getParameter("phone");
            //Lấy thông tin từ người dùng hiện tại từ session
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser == null) {
                request.setAttribute("error", "Vui lòng đăng nhập để cập nhật thông tin!");
                request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
                return;
            }
            

            try {
                String error = validateInput(lastName, firstName, password, phone, dobStr, gender, address);
                if (error != null) {
                    request.setAttribute("error", error);
                    request.getRequestDispatcher("/view/user/profileUser.jsp").forward(request, response);
                    return;
                }

                Date dob = Date.valueOf(dobStr);
                LocalDate now = LocalDate.now();
                Date updateDate = Date.valueOf(now);

                //Cập nhật thông tin người dùng
                loginUser.setLastName(lastName);
                loginUser.setFirstName(firstName);
                loginUser.setPassword(password);
                loginUser.setGender(gender);
                loginUser.setPhone(phone);
                loginUser.setDob(dob);
                loginUser.setAddress(address);
                loginUser.setUpdateDate(updateDate);

                //Gọi DAO để cập nhật vào cơ sở dữ liệu 
                udao.updateUser(loginUser);

                //Cập nahatj lại session
                session.setAttribute("loginUser", loginUser);

                //Hiển thị cập nhật thành công
                request.setAttribute("sucsess", "Cập nhật thông tin thành công!");
                request.getRequestDispatcher("/view/user/profileUser.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Cập nhật thông tin thát bại.Vui lòng thử lại!");
                request.getRequestDispatcher("/view/user/profileUser.jsp").forward(request, response);
                return;
            }

        }
    }

    private String validateInput(String lastName, String firstName, String password,
            String phone, String dobStr, String gender, String address) {
// Kiểm tra các trường không được để trống
        if (lastName == null || lastName.trim().isEmpty()
                || firstName == null || firstName.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                
                || phone == null || phone.trim().isEmpty()
                || dobStr == null || dobStr.trim().isEmpty()
                || gender == null || gender.trim().isEmpty()
                || address == null || address.trim().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin!";
        }
        if (!lastName.matches("^[\\p{L} ]{2,50}$") || !firstName.matches("^[\\p{L} ]{2,50}$")) {
            return "Họ và tên chỉ được chứa chữ cái và khoảng trắng, độ dài từ 2 đến 50 ký tự!";
        }

        

        if (!phone.matches("^0\\d{9}$")) {
            return "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số. ";
        }
        String passwordRegex = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:\";'<>?,./]).{7,}$";
        if (!password.matches(passwordRegex)) {
            return "Mật khẩu phải bắt đầu bằng chữ hoa, chứa ít nhất 1 chữ thường, 1 số, 1 ký tự đặc biệt và có ít nhất 8 ký tự!";
        }

        try {
            LocalDate dob = LocalDate.parse(dobStr);
            if (dob.isAfter(LocalDate.now())) {
                return "Ngày sinh không được lớn hơn ngày hiện tại!";
            }
        } catch (Exception e) {
            return "Ngày sinh không hợp lệ";
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
