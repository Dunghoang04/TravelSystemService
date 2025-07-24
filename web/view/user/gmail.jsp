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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Nhận Ưu Đãi Tour</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            :root {
                --main-color: #1a73e8;
                --light-bg: #f8fafc;
                --border-radius: 16px;
                --shadow: 0 6px 24px rgba(0, 0, 0, 0.1);
            }

            body {
                margin: 0;
                background-image: url('<%=request.getContextPath()%>/assets/img/img_7.png') ; /* Hoặc màu xanh nhạt tươi */
                background-size: cover;         /* ảnh sẽ co giãn để phủ hết màn hình */
                background-repeat: no-repeat;   /* không lặp ảnh */
                background-position: center;    /* canh giữa ảnh */
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
            }

            .wrapper {
                display: grid;
                grid-template-columns: 1fr 1fr;
                max-width: 1000px;
                height: 500px;
                width: 100%;
                background-color: white;
                border-radius: var(--border-radius);
                box-shadow: var(--shadow);
                overflow: hidden;
            }

            .left {
                padding: 60px 50px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                background-color: white;
            }

            .left h2 {
                font-size: 30px;
                color: #28A745;
                margin-bottom: 20px;
            }

            .input-wrapper {
                position: relative;
                margin-bottom: 20px;
            }

            .input-wrapper i {
                position: absolute;
                top: 50%;
                left: 16px;
                transform: translateY(-50%);
                color: #94a3b8;
            }

            .input-wrapper input {
                width: 90%;
                padding: 14px 14px 14px 46px;
                font-size: 15px;
                border: 1px solid #e2e8f0;
                border-radius: 12px;
                outline: none;
                background-color: #fff;
            }

            .checkbox-group {
                font-size: 14px;
                margin-bottom: 20px;
            }

            .checkbox-group input {
                margin-right: 8px;
            }

            .checkbox-group a {
                color: var(--main-color);
                text-decoration: none;
            }

            .btn {
                background-color: #28A745;
                color: white;
                padding: 14px;
                border: none;
                border-radius: 12px;
                font-size: 16px;
                cursor: pointer;
                transition: background 0.3s ease;
                width: 105%;
            }

            .btn:hover {
                background-color: #28A745;
            }

            .login {
                margin-top: 15px;
                font-size: 14px;
                text-align: center;
            }

            .login a {
                color: var(--main-color);
                text-decoration: none;
            }

            .right {
                background: url('<%=request.getContextPath() %>/assets/img/img_1.jpeg') no-repeat center center;
                background-size: cover;
                position: relative;
            }

            .right::after {
                content: '';
                position: absolute;
                inset: 0;
                background: linear-gradient(to bottom right, rgba(255,255,255,0.05), rgba(0,0,0,0.15));
            }

            @media (max-width: 768px) {
                .wrapper {
                    grid-template-columns: 1fr;
                }

                .right {
                    height: 200px;
                }

                .left {
                    padding: 40px 25px;
                }
            }
        </style>
    </head>
    <body>

        <div class="wrapper">
            <div class="left">
                <h2>Du lịch Việt Nam</h2>
                <form action="${pageContext.request.contextPath}/GmailUser" method="post">
                    <div class="input-wrapper">
                        <i class="fa-regular fa-envelope"></i>
                        <input  name="gmail" value="${param.gmail}" placeholder="Nhập gmail của bạn" required="" />
                    </div>
                    <div class="checkbox-group">
                        <label>
                            <input type="checkbox" required />
                            Tôi đồng ý với <a href="${pageContext.request.contextPath}/view/user/termAccountTourist.jsp">Điều khoản dịch vụ</a>
                        </label>
                    </div>
                    <button type="submit" class="btn">Đăng ký ngay →</button>
                </form>
                <c:if test="${pageContext.request.method == 'POST' and not empty error}">
                    <div style="color: red; margin-top:10px;">${error}</div>
                </c:if>
                <div class="login">
                    Đã có tài khoản? <a href="${pageContext.request.contextPath}/LoginLogout">Đăng nhập</a>
                </div>
            </div>
            <div class="right"></div>
        </div>

    </body>
</html>
