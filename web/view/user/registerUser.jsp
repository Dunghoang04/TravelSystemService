<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register</title>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                background-image: url('<%=request.getContextPath()%>/assets/img/img_5.png') ; /* Hoặc màu xanh nhạt tươi */
                background-size: cover;         /* ảnh sẽ co giãn để phủ hết màn hình */
                background-repeat: no-repeat;   /* không lặp ảnh */
                background-position: center;    /* canh giữa ảnh */
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .register-wrapper {
                display: flex;
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 1200px;
                overflow: hidden;
                animation: fadeIn 0.5s ease-in-out;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(-20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .image-section {
                flex: 1;
                background: url('<%=request.getContextPath()%>/assets/img/img_3.png') no-repeat center center;
                background-size: cover;
                min-height: 500px;
            }

            .register-container {
                flex: 1;
                padding: 40px;
                max-width: 600px;
            }

            h2 {
                text-align: center;
                color: #28A745;
                font-weight: 600;
                margin-bottom: 30px;
            }

            .form-container {
                display: flex;
                justify-content: space-between;
                gap: 20px;
            }

            .column {
                flex: 1;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                font-size: 16px;

                margin-bottom: 8px;
                font-weight: 400;
            }

            input, select {
                width: 100%;
                padding: 12px 15px;
                border: 1px solid #ddd;
                border-radius: 6px;
                font-size: 14px;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            input:focus, select:focus, textarea:focus {
                outline: none;
                border-color: #3498db;
                box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
            }



            input[type="date"] {
                padding: 12px 15px;
            }

            input::placeholder, textarea::placeholder {
                color: #bdc3c7;
                font-style: italic;
            }

            button {
                width: 100%;
                padding: 12px;
                background: #28A745;
                border: none;
                border-radius: 6px;
                color: #fff;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: background 0.3s ease;
            }

            button:hover {
                background: #28A745;
            }

            p {
                text-align: center;
                margin-top: 20px;
                font-size: 16px;
            }

            a {
                color: #3498db;
                text-decoration: none;
                font-weight: 600;
                font-size: 18px;

            }

            a:hover {
                text-decoration: underline;
            }

            .error, .success {
                font-size: 13px;
                margin-top: 10px;
                text-align: center;
                padding: 8px;
                border-radius: 4px;
            }

            .error {
                color: #e74c3c;
                background: #fceae9;
            }

            .success {
                color: #27ae60;
                background: #eafaf1;
            }

            
            @media (max-width: 768px) {
                .register-wrapper {
                    flex-direction: column;
                    max-width: 450px;
                }

                .image-section {
                    display: none;
                }

                .form-container {
                    flex-direction: column;
                    gap: 15px;
                }

                .register-container {
                    max-width: 100%;
                }
            }
        </style>
    </head>
    <body>
        <div class="register-wrapper">
            <div class="image-section"></div>
            <div class="register-container">
                <h2>Đăng ký tài khoản</h2>

                <form id="registerForm" action="/TravelServicesSystem/RegisterUser" method="POST">
                    <input type="hidden" name="service" value="addUser">

                    <div class="form-container">
                        <!-- Cột trái -->
                        <div class="column">
                            <div class="form-group">
                                <label for="lastName">Họ:</label>
                                <input type="text" id="lastName" name="lastName" placeholder="VD: Nguyễn" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Mật khẩu:</label>
                                <input type="password" id="password" name="password" placeholder=".........." required>
                            </div>

                            <div class="form-group">
                                <label for="gender">Giới tính:</label>
                                <select name="gender" id="gender" required>
                                    <option value="" disabled selected>-- Chọn giới tính --</option>
                                    <option value="male">Nam</option>
                                    <option value="female">Nữ</option>
                                    <option value="other">Khác</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="dob">Ngày sinh:</label>
                                <input type="date" id="dob" name="dob" required>
                            </div>
                        </div>

                        <!-- Cột phải -->
                        <div class="column">
                            <div class="form-group">
                                <label for="firstName">Tên:</label>
                                <input type="text" id="firstName" name="firstName" placeholder="VD: Hoa" required>
                            </div>
                            <div class="form-group">
                                <label for="repassword">Nhập lại mật khẩu:</label>
                                <input type="password" id="repassword" name="repassword" placeholder=".........." required>
                            </div>
                            <div class="form-group">
                                <label for="phone">Số điện thoại:</label>
                                <input type="tel" id="phone" name="phone" required placeholder="Nhập số điện thoại">
                            </div>
                            <div class="form-group">
                                <label for="address">Địa chỉ:</label>
                                <input id="address" name="address" required placeholder="Nhập địa chỉ của bạn">
                            </div>

                        </div>
                    </div>
                    
                    <button type="submit">Đăng ký</button>
                    <c:if test="${pageContext.request.method == 'POST' and not empty error}">
                        <div style="color: red; margin-top:10px;">${error}</div>
                    </c:if>

                    <c:if test="${not empty successMessage}">
                        <div class="success">${successMessage}</div>
                    </c:if>
                    <p>Bạn đã có tài khoản? <a href="${pageContext.request.contextPath}/LoginLogout">Đăng nhập</a></p>
                </form>
            </div>
        </div>
    </body>
</html>
