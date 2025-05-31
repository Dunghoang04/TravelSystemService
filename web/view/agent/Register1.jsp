<%-- 
    Document   : register1
    Created on : May 26, 2025, 7:46:40 PM
    Author     : Nhat Anh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng ký - Trang 1</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            body {
                background: url('${pageContext.request.contextPath}/assets/img/a3.jpg');
                background-size: cover;       /* ảnh nền sẽ bao phủ toàn bộ khung hình */
                background-repeat: no-repeat; /* không lặp lại ảnh nền */
                background-position: center;  /* căn giữa ảnh nền */
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                background-color: #f4f4f4;
            }
            .register-container {
                display: flex;                
                width: 80%;
                max-width: 800px;
                border-radius: 12px;
                overflow: hidden;
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
            
            .image-section img{
                width: 100%;
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
            

        </style>
    </head>
    <body>
        <div class="register-container">
            <div class="form-section">
                <h2 style="color: #28A745">Đăng ký</h2>

                <form action="${pageContext.request.contextPath}/RegisterTravelAgentServlet" method="post">
                    <input type="hidden" name="service" value="step1">
                    <div class="form-group" >
                        <label for="email">Email:</label>
                        <input class="input" type="email" id="email" name="email" value="${param.email}" placeholder=""  required>                      
                        
                    </div> 
                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <input class="input"  type="password" id="password" name="password" value="${param.password}" required>
                        <span class="toggle-password"><i class="fas fa-eye"></i></span>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Nhập lại mật khẩu:</label>
                        <input class="input" type="password" id="confirmPassword"  name="confirmPassword" value="${param.confirmPassword}"  required>
                        <span class="toggle-password"><i class="fas fa-eye"></i></span>
                    </div>
                    <c:if test="${not empty errorMessage}">
                        <div class="error">${errorMessage}</div>
                    </c:if>
                   
                    <div style="text-align: right; margin-top: 30px">
                        <button type="submit">Tiếp theo</button>
                    </div>
                    <p style="text-align: center"><a href="login.jsp">Đã đăng ký? Đăng nhập</a></p>
                </form>
            </div>
            <div class="image-section">
                <div style="text-align: center;">                    
                    <img src="${pageContext.request.contextPath}/assets/img/r2.png" >
                </div>
            </div>
        </div>

    </body>
    
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
    
</html>
