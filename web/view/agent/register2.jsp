<%-- 
    Document   : register2
    Created on : May 26, 2025, 7:47:34 PM
    Author     : Nhat Anh
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng ký - Trang 2</title>
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
            input[type="password"] {
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
        </style>
    </head>
    <body>
        <div class="container">
            <div class="image-section">
                <div style="text-align: center;">
                    <h2 style="color: #000;">THAM GIA CÙNG CHÚNG TÔI!</h2>
                    <!-- Placeholder for graphic - replace with actual image or SVG -->
                    <p>[Hình minh họa: Người cầm điện thoại, bong bóng lời nói, sao]</p>
                </div>
            </div>
            <div class="form-section">
                <h2>Đăng ký</h2>
                <form action="register3.jsp" method="post">

                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <input class="input"  type="password" id="password" name="password"  required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Nhập lại mật khẩu:</label>
                        <input class="input" type="password" id="confirmPassword" name="confirmPassword"  required>
                    </div>

                    <button type="submit">Tiếp theo</button>
                </form>
            </div>
        </div>
    </body>
</html>
