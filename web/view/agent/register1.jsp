<%-- 
    Document   : register1
    Created on : May 26, 2025, 7:46:40 PM
    Author     : Nhat Anh
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng ký - Trang 1</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                background-color: #f4f4f4;
            }
            .container {
                display: flex;
                width: 80%;
                max-width: 800px;
            }
            .form-section {
                flex: 1;
                padding: 20px;
                background: #fff;
            }
            .image-section {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                background: #f0f0f0;
            }
            input[type="email"], input[type="checkbox"] {
                display: block;
                margin: 10px 0;
            }
            button {
                background-color: #007BFF;
                color: white;
                padding: 10px 20px;
                border: none;
                cursor: pointer;
            }
            a {
                color: purple;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="form-section">
                <h2 style="color: #28A745">Đăng ký</h2>
                
                <form action="register2.jsp" method="post">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>                    
                    <button type="submit">Tiếp theo</button>
                    <p><a href="login.jsp">Đã đăng ký? Đăng nhập</a></p>
                </form>
            </div>
            <div class="image-section">
                <div style="text-align: center;">
                    <h2 style="color: #000;">THAM GIA ĐỘI NGŨ CHÚNG TÔI</h2>
                    <!-- Placeholder for graphic - replace with actual image or SVG -->
                    <img src="${pageContext.request.contextPath}/assets/img/HaLong.jpg" >
                </div>
            </div>
        </div>
    </body>
</html>
