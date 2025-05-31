<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                background-image: url('<%=request.getContextPath()%>/assets/img/img_8.jpg') ; /* Hoặc màu xanh nhạt tươi */
                background-size: cover;         /* ảnh sẽ co giãn để phủ hết màn hình */
                background-repeat: no-repeat;   /* không lặp ảnh */
                background-position: center;    /* canh giữa ảnh */
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }


            .container {
                background-color: white;
                width: 850px;
                height: 500px;
                display: flex;
                border-radius: 15px;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
                overflow: hidden;
            }

            .login-container {
                width: 50%;
                padding: 50px 40px;
                display: flex;
                flex-direction: column;
                justify-content: center;
            }

            .login-container h2 {
                font-size: 28px;
                margin-bottom: 30px;
                text-align: center;
                color: #28A745;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                font-weight: bold;
                display: block;
                margin-bottom: 8px;

            }

            .form-group input {
                width: 100%;
                padding: 12px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 16px;
            }

            .button {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
            }

            .button input[type="submit"]
            {
                width: 100%;
                padding: 12px;
                font-weight: bold;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                cursor: pointer;
                transition: 0.3s;
            }

            .submit {
                background-color: #28A745;
                color: white;
            }

            .submit:hover {
                background-color: #28A745;
            }


            .link {
                text-align: center;
                margin-top: 25px;
            }

            .link a {
                color: #007bff;
                text-decoration: none;
                margin: 0 10px;
                font-weight: bold;
            }

            .link a:hover {
                text-decoration: underline;
            }

            .image-container {
                width: 50%;
                background: url('<%= request.getContextPath() %>/assets/img/img_2.jpg') no-repeat center;
                background-size: cover;
            }

            @media (max-width: 768px) {
                .container {
                    flex-direction: column;
                    width: 90%;
                    height: auto;
                }

                .login-container,
                .image-container {
                    width: 100%;
                    height: auto;
                }

                .image-container {
                    height: 200px;
                }

                .button {
                    flex-direction: column;
                }

                .button input{
                    width: 100%;
                    margin-top: 10px;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <!-- Form đăng nhập -->
            <div class="login-container">
                <h2>Đăng nhập tài khoản</h2>
                <form action="${pageContext.request.contextPath}/LoginLogout" method="post">
                    <div class="form-group">
                        <label for="gmail">Gmail:</label>
                        <input type="email" id="gmail" name="gmail" placeholder="Nhập email" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Mật khẩu:</label>
                        <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required>
                    </div>

                    <input type="hidden" name="service" value="loginUser">

                    <div class="button">
                        <input class="submit" type="submit" name="submit" value="Đăng nhập">

                    </div>
                    <c:if test="${not empty error}">
                        <div style="color: red; text-align: center; margin-bottom: 15px;">
                            ${error}
                        </div>
                    </c:if>
                </form>

                <!-- Liên kết -->
                <div class="link">
                    <a href="rePass.jsp">Quên mật khẩu?</a>
                </div>
                <div class="link">
                    <p>Chưa có tài khoản?<a href="view/user/gmail.jsp">Đăng ký</a></p>

                </div>

            </div>

            <!-- Hình ảnh -->
            <div class="image-container"></div>
        </div>
    </body>
</html>
