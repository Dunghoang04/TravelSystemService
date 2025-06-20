<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai          First implementation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng ký - Trang 1</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="assets/lib/animate/animate.min.css" rel="stylesheet">
        <link href="assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="assets/css/style.css" rel="stylesheet">
        <style>
            body {
                background: url('${pageContext.request.contextPath}/assets/img/a3.jpg');
                background-size: cover;
                background-repeat: no-repeat;
                background-position: center;
                font-family: Arial, sans-serif;
                margin: 0;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            .main-content {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                padding-top: 200px; /* Đảm bảo không bị đè lên header */
                padding-bottom: 50px; /* Đảm bảo không bị đè lên footer */
            }

            .register-container {
                display: flex;
                width: 80%;
                max-width: 800px;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .form-section {
                flex: 1;
                padding: 20px;
                background: #fff;
                text-align: center;
            }

            .image-section {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                background: #f0f0f0;
            }

            .form-group {
                margin-bottom: 15px;
                text-align: left;
                position: relative;
            }

            label {
                display: block;
                font-size: 14px;
                color: black;
                font-weight: bold;
                margin-bottom: 5px;
            }

            .input {
                width: 100%;
                padding: 10px;
                padding-right: 40px;
                border: 1px solid #d6c9c9;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 16px;
                background-color: #f9f8f7;
                transition: all 0.3s ease-in-out;
            }

            input:focus {
                border-color: #b89f9a;
                background-color: #fff;
                outline: none;
            }

            button {
                width: 50%;
                padding: 12px;
                background: #28A745;
                border: none;
                border-radius: 6px;
                color: white;
                font-size: 18px;
                font-weight: bold;
                cursor: pointer;
                transition: 0.3s;
            }

            button:hover {
                background-color: #218838;
            }

            .error {
                color: red;
                font-size: 14px;
                margin-top: 5px;
                text-align: center;
            }

            .success {
                color: green;
                font-size: 14px;
                text-align: center;
                margin-top: 5px;
            }

            a {
                color: purple;
                text-decoration: none;
            }

            .image-section img {
                width: 100%;
                height: auto;
            }

            .toggle-password {
                position: absolute;
                right: 10px;
                top: 65%;
                transform: translateY(-50%);
                cursor: pointer;
                font-size: 16px;
                color: #333;
            }

            .toggle-password:hover {
                color: #007BFF;
            }

            .required {
                color: red;
                margin-left: 2px;
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <%@include file="../layout/header.jsp" %>

        <!-- Main Content -->
        <div class="main-content">
            <div class="register-container">
                <div class="form-section">
                    <h2 style="color: #28A745">Đăng ký</h2>

                    <form action="${pageContext.request.contextPath}/RegisterTravelAgentServlet" method="post">
                        <input type="hidden" name="service" value="step1">
                        <div class="form-group">
                            <label for="email">Email:<span class="required">*</span></label>
                            <input class="input" type="email" id="email" name="email" value="${param.email}" placeholder="Nhập gmail của bạn" required>
                        </div> 
                        <div class="form-group">
                            <label for="password">Mật khẩu:<span class="required">*</span></label>
                            <input class="input" type="password" id="password" name="password" value="${param.password}" placeholder="Nhập mật khẩu" required>
                            <span class="toggle-password"><i class="fas fa-eye"></i></span>
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">Nhập lại mật khẩu:<span class="required">*</span></label>
                            <input class="input" type="password" id="confirmPassword" name="confirmPassword" value="${param.confirmPassword}" placeholder="Nhập lại mật khẩu" required>
                            <span class="toggle-password"><i class="fas fa-eye"></i></span>
                        </div>
                        <c:if test="${not empty errorMessage}">
                            <div class="error">${errorMessage}</div>
                        </c:if>

                        <div style="text-align: right; margin-top: 30px">
                            <button type="submit">Tiếp theo</button>
                        </div>
                        <p style="text-align: center"><a href="LoginLogout?service=loginUser">Đã đăng ký? Đăng nhập</a></p>
                    </form>
                </div>
                <div class="image-section">
                    <div style="text-align: center;">                    
                        <img src="${pageContext.request.contextPath}/assets/img/r2.png" alt="Join Our Team">
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <%@include file="../layout/footer.jsp" %>

        <script>
            // Lấy tất cả các phần tử có class toggle-password
            const togglePasswordButtons = document.querySelectorAll(".toggle-password");

            togglePasswordButtons.forEach(button => {
                button.addEventListener("click", () => {
                    // Tìm input gần nhất trong cùng một form-group
                    const passwordInput = button.previousElementSibling;
                    // Chuyển đổi giữa type="password" và type="text"
                    if (passwordInput.type === "password") {
                        passwordInput.type = "text";
                        button.innerHTML = '<i class="fas fa-eye-slash"></i>';
                    } else {
                        passwordInput.type = "password";
                        button.innerHTML = '<i class="fas fa-eye"></i>';
                    }
                });
            });
        </script>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/lib/wow/wow.min.js"></script>
        <script src="assets/lib/easing/easing.min.js"></script>
        <script src="assets/lib/waypoints/waypoints.min.js"></script>
        <script src="assets/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="assets/js/main.js"></script>
    </body>
</html>