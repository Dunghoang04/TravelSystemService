<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên      First implementationF
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đặt lại mật khẩu</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            * { margin: 0; padding: 0; box-sizing: border-box; }
            body { background-image: url('<%=request.getContextPath()%>/assets/img/img_8.jpg'); background-size: cover; background-repeat: no-repeat; background-position: center; height: 100vh; display: flex; justify-content: center; align-items: center; }
            .container { background-color: white; width: 850px; height: 500px; display: flex; border-radius: 15px; box-shadow: 0 8px 16px rgba(0,0,0,0.2); overflow: hidden; }
            .form-container { width: 50%; padding: 50px 40px; display: flex; flex-direction: column; justify-content: center; }
            .form-container h2 { font-size: 28px; margin-bottom: 30px; text-align: center; color: #28A745; }
            .form-group { margin-bottom: 20px; position: relative; }
            .form-group label { font-weight: bold; display: block; margin-bottom: 8px; }
            .form-group input { width: 100%; padding: 12px 40px 12px 12px; border: 1px solid #ccc; border-radius: 8px; font-size: 16px; box-sizing: border-box; }
            .toggle-password { position: absolute; right: 10px; top: 38px; cursor: pointer; color: #666; }
            .toggle-password:hover { color: #28A745; }
            .button input[type="submit"] { width: 100%; padding: 12px; font-weight: bold; border: none; border-radius: 8px; font-size: 16px; cursor: pointer; background-color: #28A745; color: white; transition: 0.3s; }
            .button input[type="submit"]:hover { background-color: #218838; }
            .link { text-align: center; margin-top: 20px; }
            .link a { color: #007bff; text-decoration: none; font-weight: bold; }
            .link a:hover { text-decoration: underline; }
            .image-container { width: 50%; background: url('<%=request.getContextPath()%>/assets/img/img_2.jpg') no-repeat center; background-size: cover; }
            .message, .error { text-align: center; margin-top: 15px; }
            .message { color: green; }
            .error { color: red; }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="form-container">
                <h2>Đặt lại mật khẩu</h2>
                <form action="${pageContext.request.contextPath}/LoginLogout" method="post">
                    <input type="hidden" name="service" value="resetPassword">
                    <input type="hidden" name="gmail" value="${param.gmail}">
                    <div class="form-group">
                        <label for="newPassword">Mật khẩu mới:</label>
                        <input type="password" id="newPassword" name="newPassword" placeholder="Nhập mật khẩu mới" required>
                        <span class="toggle-password" onclick="togglePassword()">
                            <i class="fas fa-eye"></i>
                        </span>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Nhập lại mật khẩu:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
                        <span class="toggle-password" onclick="togglePassword()">
                            <i class="fas fa-eye"></i>
                        </span>
                    </div>
                    <div class="button">
                        <input type="submit" value="Đặt lại mật khẩu">
                    </div>
                </form>
                <c:if test="${not empty message}">
                    <div class="message">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <div class="link">
                    <a href="${pageContext.request.contextPath}/LoginLogout">Quay lại đăng nhập</a>
                </div>
            </div>
            <div class="image-container"></div>
        </div>

        <script>
            function togglePassword() {
                var passwordInput = document.getElementById("newPassword");
                var icon = document.querySelector(".toggle-password i");
                if (passwordInput.type === "password") {
                    passwordInput.type = "text";
                    icon.classList.remove("fa-eye");
                    icon.classList.add("fa-eye-slash");
                } else {
                    passwordInput.type = "password";
                    icon.classList.remove("fa-eye-slash");
                    icon.classList.add("fa-eye");
                }
            }
        </script>
    </body>
</html>